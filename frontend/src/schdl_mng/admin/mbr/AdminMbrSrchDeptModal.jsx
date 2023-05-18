import { useEffect, useState } from "react";
import CmmFnc from "context/common.js";
import AdminMbrSrchDeptTree from "schdl_mng/admin/mbr/AdminMbrSrchDeptTree";

/**
 * 직원 등록 / 수정 - 부서 검색 Component
 * @param {*} callback    콜백함수
 * @param {*} deptInfo    부서정보
 * @returns
 */
function AdminMbrSrchDeptModal({ callback, deptInfo }) {
    //선택한 노드 값
    const [selectedNode, setSelectedNode] = useState(null);
    // 트리 데이터
    const [treeData, setTreeData] = useState([]);

    //이미 선택된 부서가 있을 시 선택된 노드로 변경 setState
    useEffect(() => {
        if (deptInfo !== null) {
            setSelectedNode((prevState) => {
                return {
                    ...prevState,
                    ...deptInfo,
                };
            });
        }
    }, [deptInfo]);

    //부서 리스트 조회 후 treeData 삽입 setState 함수
    const fncGetOrgnzt = async () => {
        const treeArray = [];
        await CmmFnc.fncRequestAxios(
            "POST",
            "/cmmn/orgcht/selectDeptList.do"
        ).then((result) => {
            if (result.data.sttus === "success") {
                result.data.result.deptList.forEach((el) => {
                    const treeObj = {
                        id: el.deptId,
                        parent: el.upDeptId,
                        ...(el.droppable === "0"
                            ? { droppable: false }
                            : { droppable: true }),
                        text: el.deptNm,
                        data: {
                            menuOrdr: el.deptSn,
                            useYn: el.useYn,
                            form: {
                                deptId: el.deptId,
                                deptNm: el.deptNm,
                                deptSn: el.deptSn,
                                upDeptId: el.upDeptId,
                                useYn: el.useYn,
                            },
                        },
                    };
                    treeArray.push(treeObj);
                });

                treeArray.sort((a, b) => {
                    if (a.data.menuOrdr < b.data.menuOrdr) return -1;
                    if (a.data.menuOrdr > b.data.menuOrdr) return 1;
                    return 0;
                });
                setTreeData(treeArray);
            }
        });
    };

    //부서 리스트 조회 함수 호출
    useEffect(() => {
        fncGetOrgnzt();
    }, []);

    // 부서 선택 후 확인 버튼 이벤트
    const onPrgrsHandler = (e) => {
        if (selectedNode !== null) {
            callback({ action: "deptInfo", data: selectedNode });
        } else {
            alert("부서를 선택하세요");
        }
    };
    return (
        <>
            <div className="modal_content">
                <div className="board_write intitle">
                    <AdminMbrSrchDeptTree
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

export default AdminMbrSrchDeptModal;
