import { useEffect, useState } from "react";
import CmmFnc from "context/common";

/**
 * 마이페이지 - 조직도 - 회원 목록
 * @param {*} selectedNode    선택된 노드 state
 * @param {*} onClickMbrDtl   직원 상세 팝업 호출 이벤트
 * @returns
 */
function OrgchtMbrList({ selectedNode, onClickMbrDtl }) {
    const [mberList, setMberList] = useState([]);

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
        <div className="member_list">
            <table
                className="table_list"
                summary="조직도 내역에 대한 목록을 출력합니다."
            >
                <caption>조직도 목록</caption>
                <colgroup>
                    <col className="width80" />
                    <col className="width150" />
                    <col className="width150" />
                    <col className="width150" />
                    <col className="width150" />
                </colgroup>
                <thead>
                    <tr>
                        <th>번호</th>
                        <th className="board_th_link">상태</th>
                        <th>이름</th>
                        <th>직위</th>
                        <th>연락처</th>
                    </tr>
                </thead>
                <tbody className="ov">
                    {mberList.length > 0 &&
                        mberList.map((mber, index) => (
                            <tr key={index}>
                                <td>{index + 1}</td>
                                <td>{mber.schdlSttus}</td>
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
                            </tr>
                        ))}
                    {mberList.length === 0 && (
                        <tr>
                            <td colSpan={5}>
                                해당 부서에 등록된 직원이 없습니다.
                            </td>
                        </tr>
                    )}
                </tbody>
            </table>
        </div>
    );
}

export default OrgchtMbrList;
