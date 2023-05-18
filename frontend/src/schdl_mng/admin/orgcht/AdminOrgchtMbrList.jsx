import { useEffect, useState } from "react";
import CmmFnc from "context/common";

/**
 * 조직도 목록 Component
 * @param {*} selectedNode     선택된 노드 정보 state
 * @param {*} onClickMbrDtl    직원 상세 모달 호출 함수
 * @returns
 */
function AdminOrgchtMbrList({ selectedNode, onClickMbrDtl }) {
    // 직원 목록 상태관리 state
    const [mberList, setMberList] = useState([]);
    //직원 목록 조회 후 setState
    useEffect(() => {
        if (selectedNode !== null) {
            const getMberList = async () => {
                // awit - 프라미스가 처리(settled)될 때까지 기다림
                const data = await CmmFnc.fncRequestAxios(
                    "POST",
                    "/cmmn/orgcht/selectDeptMbrList.do",
                    selectedNode.id
                ).then((result) => {
                    if (result.data.sttus === "success") {
                        return result.data.result.mberList;
                    } else {
                        return [];
                    }
                });

                setMberList(data);
            };
            // 함수 호출
            getMberList();
        }
    }, [selectedNode]);

    return (
        <div className="body_right">
            <table
                className="table_list"
                summary="조직도 내역에 대한 목록을 출력합니다."
            >
                <caption>조직도 목록</caption>
                <colgroup>
                    <col style={{ width: "10%" }} />
                    <col style={{ width: "10%" }} />
                    <col style={{ width: "10%" }} />
                    <col style={{ width: "10%" }} />
                    <col style={{ width: "10%" }} />
                    <col style={{ width: "30%" }} />
                    <col style={{ width: "10%" }} />
                </colgroup>
                <thead>
                    <tr>
                        <th>번호</th>
                        <th className="board_th_link">아이디</th>
                        <th>이름</th>
                        <th>직위</th>
                        <th>연락처</th>
                        <th>휴가관리</th>
                        <th>상태</th>
                    </tr>
                </thead>
                <tbody className="ov">
                    {mberList.length > 0 &&
                        mberList.map((mber, index) => (
                            <tr key={index}>
                                <td>{index + 1}</td>
                                <td>{mber.mberId}</td>
                                <td>
                                    <a
                                        href="{() => false}"
                                        onClick={(e) => onClickMbrDtl(e, mber)}
                                        title="직원 상세보기"
                                    >
                                        {mber.mberNm}
                                    </a>
                                </td>
                                <td>{mber.ofcpsNm}</td>
                                <td>{mber.moblphonNo}</td>
                                <td>{`생성일수 : ${mber.vcatnCrtCnt} / 사용일수
                                        : ${mber.vcatnUsgqtyCnt} / 잔여일수 :
                                        ${mber.rmndrDayCnt}`}</td>
                                <td>{mber.schdlSttus}</td>
                            </tr>
                        ))}
                    {mberList.length === 0 && (
                        <tr>
                            <td colSpan={7}>
                                해당 부서에 등록된 직원이 없습니다.
                            </td>
                        </tr>
                    )}
                </tbody>
            </table>
        </div>
    );
}

export default AdminOrgchtMbrList;
