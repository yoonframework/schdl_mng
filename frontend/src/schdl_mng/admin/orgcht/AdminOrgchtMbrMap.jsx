import { useEffect, useState } from "react";
import CmmFnc from "context/common";

/**
 * 사용자맵
 * @param {*} onClickMbrDtl    직원 상세 모달 호출 함수
 * @returns
 */
function AdminOrgchtMbrMap({ selectedDeptId, onClickMbrDtl }) {
    // 직원 목록 상태관리 state
    const [mberList, setMberList] = useState([]);
    // 직원 목록 조회 후 setState
    useEffect(() => {
        const getMberList = async () => {
            // awit - 프라미스가 처리(settled)될 때까지 기다림
            const data = await CmmFnc.fncRequestAxios(
                "POST",
                "/cmmn/orgcht/selectAllMbrList.do",
                { deptId: selectedDeptId }
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
    }, [selectedDeptId]);

    // mber 이 length가 0일때 해야함
    return (
        <div className="member_org">
            <ul>
                {mberList.map((item, index) => {
                    return (
                        <li key={index}>
                            <div className="face">
                                <a
                                    href="{() => false}"
                                    onClick={(e) => onClickMbrDtl(e, item)}
                                    className="blank_w"
                                    title="직원 상세보기"
                                >
                                    {item.mberNm}
                                </a>
                            </div>
                            <ul>
                                <li>
                                    <p>부서</p>
                                    <strong>{item.deptNm}</strong>
                                </li>
                                <li>
                                    <p>직위</p>
                                    <strong>{item.ofcpsNm}</strong>
                                </li>
                                <li>
                                    <p>연락처</p>
                                    <strong>{item.moblphonNo}</strong>
                                </li>
                                <li>
                                    <p>휴가관리</p>
                                    <strong>
                                        생성일수 : {item.vcatnCrtCnt} / 사용일수
                                        : {item.vcatnUsgqtyCnt} / 잔여일수 :{" "}
                                        {item.rmndrDayCnt}
                                    </strong>
                                </li>
                                <li>
                                    <p>최근접속</p>
                                    <strong>{item.recentCntnDt}</strong>
                                </li>
                                <li>
                                    <p>상태</p>
                                    <strong>{item.schdlSttus}</strong>
                                </li>
                            </ul>
                        </li>
                    );
                })}
            </ul>
        </div>
    );
}

export default AdminOrgchtMbrMap;
