import { useEffect, useState } from "react";
import SchdlMbrSrchTree from "schdl_mng/user/schdl/SchdlMbrSrchTree";
import CmmFnc from "context/common.js";

// 회원 조회
const fncGetOrgnzt = async () => {
    return await CmmFnc.fncRequestAxios(
        "POST",
        "/user/schdl/selectMbrTreeList.do"
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
                                    aplcntId: el.deptId,
                                    aplcntNm: el.deptNm,
                                    rmndrDayCnt: el.rmndrDayCnt,
                                },
                            }),
                    },
                };
            });
        }
    });
};

/**
 * 일정관리 등록 시 신청자 정보 조회 모달(관리자일경우 사용가능)
 * @param {*} callback 모달 callback
 * @param {*} aplcntInfo 선택된 회원 정보
 * @returns
 */
function SchdlMbrSrchModal({ callback, aplcntInfo }) {
    //선택한 노드 값
    const [selectedNode, setSelectedNode] = useState(null);
    // 트리 데이터
    const [treeData, setTreeData] = useState([]);

    //부서정보 리스트 불러오기
    useEffect(() => {
        const loadData = async () => {
            setTreeData(await fncGetOrgnzt());
        };
        loadData();
    }, []);

    //등록시 이미 신청자에 직원이 존재할 경우 상테값 변경
    useEffect(() => {
        if (aplcntInfo.aplcntId !== undefined) {
            setSelectedNode((prevState) => {
                return {
                    ...prevState,
                    id: aplcntInfo.aplcntId,
                    text: aplcntInfo.aplcntNm,
                };
            });
        }
    }, [aplcntInfo]);

    //직원 선택시 callback
    const onPrgrsHandler = (e) => {
        if (selectedNode !== null) {
            callback({ action: "aplcntInfo", data: selectedNode.data.form });
        } else {
            alert("직원을 선택하세요");
        }
    };
    return (
        <>
            <div className="modal_content">
                <div className="board_write intitle tree">
                    <SchdlMbrSrchTree
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

export default SchdlMbrSrchModal;
