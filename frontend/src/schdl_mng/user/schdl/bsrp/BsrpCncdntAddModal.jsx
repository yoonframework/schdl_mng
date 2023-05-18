import { useEffect, useState } from "react";
import BsrpCncdntAddTree from "schdl_mng/user/schdl/bsrp/BsrpCncdntAddTree";
import CmmFnc from "context/common.js";

// 트리데이터 조회 - 조직도
const fncGetOrgnzt = async (noTargetList) => {
    return await CmmFnc.fncRequestAxios(
        "POST",
        "/schdl/bsrp/selectCncdntMbrTreeList.do",
        { bsrpCncdntList: noTargetList }
    ).then((result) => {
        if (result.data.sttus === "success") {
            return result.data.result.resultList.map((el) => {
                return {
                    id: el.deptId,
                    parent: el.upDeptId,
                    ...(el.droppable === "0"
                        ? { droppable: false }
                        : { droppable: true }),
                    text: el.deptNm,
                    data: {
                        menuOrdr: 1,
                        useYn: "Y",
                        ...(el.droppable === "0" &&
                            !String(el.deptId).includes("DEPT") && {
                                form: {
                                    tgrpnId: el.deptId,
                                    tgrpnNm: el.deptNm,
                                    tgrpnOfcpsId: el.tgrpnOfcpsId,
                                    tgrpnOfcpsNm: el.tgrpnOfcpsNm,
                                    tgrpnDeptId: el.tgrpnDeptId,
                                    tgrpnDeptNm: el.tgrpnDeptNm,
                                },
                            }),
                    },
                };
            });
        }
    });
};

/**
 * 동행자 추가 모달 이벤트
 * @param {*} callback        콜백 함수
 * @param {*} noTargetList    이미 선택된 대상이거나 현재 로그인 사용자가 관리자일 경우 신청자 정보
 * @returns
 */
function BsrpCncdntAddModal({ callback, noTargetList }) {
    //선택한 노드 값
    const [selectedNode, setSelectedNode] = useState(null);
    // 트리 데이터
    const [treeData, setTreeData] = useState([]);

    //부서정보 리스트 불러오기
    useEffect(() => {
        const loadData = async () => {
            setTreeData(await fncGetOrgnzt(noTargetList));
        };
        loadData();
    }, [noTargetList]);

    //동행자 선택 완료 이벤트(콜백 호출)
    const onPrgrsHandler = (e) => {
        if (selectedNode !== null) {
            callback({
                action: "add",
                data: [...selectedNode].filter(({ data }) => data.form),
            });
        } else {
            alert("직원을 선택하세요");
        }
    };
    return (
        <>
            <div className="modal_content">
                <div className="board_write intitle tree">
                    <BsrpCncdntAddTree
                        selectedNode={selectedNode}
                        setSelectedNode={setSelectedNode}
                        treeData={treeData}
                    />
                </div>
            </div>
            <div className="modal_bottom">
                <div className="fr">
                    <button
                        type="button"
                        className="btn_s bg_navy"
                        title="확인"
                        onClick={onPrgrsHandler}
                    >
                        확인
                    </button>
                    <button
                        type="button"
                        className="btn_s close bg_basic"
                        title="닫기"
                        onClick={function (elem, e) {
                            e.preventDefault();
                            callback(elem);
                        }.bind(this, { action: "close" })}
                    >
                        닫기
                    </button>
                </div>
            </div>
        </>
    );
}

export default BsrpCncdntAddModal;
