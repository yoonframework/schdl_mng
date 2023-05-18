import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

import URL from "context/url";
import CmmFnc from "context/common.js";

// 선택된 해당 연도로 전체 사용자 목록 조회
const fncMbrList = async (year) => {
    return await CmmFnc.fncRequestAxios(
        "POST",
        "/admin/vcatnCrt/selectMbrList.do",
        { vcatnCrtYr: year }
    ).then((result) => {
        if (result.data.sttus === "success") {
            const mbrList = result.data.result.resultList;
            if (mbrList !== null) {
                return Object.values(mbrList).map((item) => {
                    return {
                        ...item.mberManageVO,
                        form: {
                            vcatnCrtrId: item.mberManageVO.uniqId,
                            vcatnCrtrNm: item.mberManageVO.mberNm,
                            vcatnCrtYr: item.vcatnCrtYr,
                            vcatnCnt: item.vcatnCnt,
                        },
                    };
                });
            } else {
                return [];
            }
        }
    });
};

/**
 * 휴개 생성 정보 일괄 등록 Component
 * @returns
 */
function AdminVcatnCrtBndeReg() {
    // 페이지 이동 Hook
    const navigate = useNavigate();
    // 연도 관리 State
    const [year, setYear] = useState(new Date().getFullYear());
    // 직원 목록 관리 State
    const [mbrList, setMbrList] = useState([]);

    // 직원 목록 조회
    useEffect(() => {
        // 서버에서 세션정보를 가져오는 함수
        // async - 반환받는 값은 Promise로 반환
        const getMbrList = async () => {
            // awit - 프라미스가 처리(settled)될 때까지 기다림
            const data = await fncMbrList(year);
            if (data !== undefined) {
                setMbrList(data);
            }
        };
        // 함수 호출
        getMbrList();
    }, [year]);

    // input Change Event
    const onChangeHandler = (e, index) => {
        const { name, value } = e.target;
        mbrList[index].form[name] = value;
        setMbrList([...mbrList]);
    };

    // 목록버튼 클릭 시
    const onClickList = (e) => {
        navigate(`${URL.ROOT + URL.ADMIN}/${URL.ADMIN_VCATN_CRT_MANAGE}`, {});
    };

    // 등록버튼 클륵 시
    const onClickSave = async (e) => {
        const param = {
            vcatnCrtList: mbrList
                .map((item) => item.form)
                .filter((item) => item.vcatnCnt !== 0)
                .map((item) => {
                    return {
                        ...item,
                        vcatnCnt: String(item.vcatnCnt),
                    };
                }),
        };

        await CmmFnc.fncRequestAxios(
            "POST",
            "/admin/vcatnCrt/insertBndeVcatnCrt.do",
            param
        ).then((result) => {
            if (result.data.sttus === "success") {
                navigate(
                    `${URL.ROOT + URL.ADMIN}/${URL.ADMIN_VCATN_CRT_MANAGE}`,
                    {}
                );
            }
        });
    };

    // 일괄 생성 시 당해년도의 다음 해 값 setState
    const onClickNextYear = () => {
        const nextYear = new Date().getFullYear() + 1;
        if (year !== nextYear) {
            setYear(nextYear);
        } else {
            alert("생성된 연도 입니다.");
        }
    };

    return (
        <>
            <div className="board_header">
                <h3>휴가생성관리 일괄등록</h3>
            </div>
            <div className="board_body">
                조회년도 : {year}
                <button
                    type="button"
                    className="btn_s ml10 mb10"
                    onClick={onClickNextYear}
                >
                    신규생성
                </button>
                <p className="must_text">
                    <em>＊</em>는 필수 입력사항입니다.
                </p>
                <table className="table_list">
                    <caption>
                        휴가생성관리 일괄등록 - 휴가생성년도, 이름,
                        휴가일수로구성
                    </caption>
                    <colgroup>
                        <col style={{ width: "5%" }} />
                        <col style={{ width: "20%" }} />
                        <col style={{ width: "30%" }} />
                        <col style={{ width: "22.5%" }} />
                    </colgroup>
                    <thead>
                        <tr>
                            <th>번호</th>
                            <th>부서</th>
                            <th className="board_th_link">이름</th>
                            <th className="must">휴가일수</th>
                        </tr>
                    </thead>
                    <tbody className="ov">
                        {mbrList.map((item, index) => {
                            const { deptNm } = item;
                            const { vcatnCrtrNm, vcatnCnt } = item.form;
                            return (
                                <tr key={index}>
                                    <td>{mbrList.length - index}</td>
                                    <td>{deptNm}</td>
                                    <td>{vcatnCrtrNm}</td>
                                    <td>
                                        <input
                                            type="text"
                                            className="width100p inputText"
                                            id={`vcatnCnt_${index}`}
                                            name="vcatnCnt"
                                            size="2"
                                            maxLength="2"
                                            title="휴가 생성 일수"
                                            value={vcatnCnt}
                                            onChange={(e) =>
                                                onChangeHandler(e, index)
                                            }
                                        />
                                    </td>
                                </tr>
                            );
                        })}
                    </tbody>
                </table>
            </div>
            <div className="board_footer">
                <div className="bottom_wrap fr">
                    <button
                        className="btn_s mr5"
                        type="button"
                        title="목록 버튼"
                        onClick={onClickList}
                    >
                        목록
                    </button>
                    <button
                        className="btn_s"
                        type="button"
                        title="저장 버튼"
                        onClick={onClickSave}
                    >
                        저장
                    </button>
                </div>
            </div>
        </>
    );
}

export default AdminVcatnCrtBndeReg;
