import CmmFnc from "context/common.js";
import AdminDeptForm from "schdl_mng/admin/dept/AdminDeptForm";
import AdminDeptTree from "schdl_mng/admin/dept/AdminDeptTree";
import React, { useEffect, useState } from "react";

/**
 * 부서관리 Component
 * @returns
 */
function AdminDept() {
    //선택한 노드 값
    const [selectedNode, setSelectedNode] = useState(null);
    // 트리 데이터
    const [treeData, setTreeData] = useState([]);

    //부서정보 리스트 조회 함수 호출
    useEffect(() => {
        //부서정보 리스트 조회 함수
        const fncGetOrgnzt = async () => {
            const treeArray = [];
            await CmmFnc.fncRequestAxios(
                "POST",
                "/admin/dept/selectDeptList.do"
            ).then((result) => {
                result.data.forEach((el) => {
                    const treeObj = {
                        id: el.deptId,
                        parent: el.upDeptId,
                        ...(el.droppable === "0"
                            ? { droppable: false }
                            : { droppable: true }),
                        text: el.deptNm,
                        data: {
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
            });

            treeArray.sort((a, b) => {
                if (a.data.form.deptSn < b.data.form.deptSn) return -1;
                if (a.data.form.deptSn > b.data.form.deptSn) return 1;
                return 0;
            });
            setTreeData(treeArray);
        };

        fncGetOrgnzt();
    }, []);

    // 저장 버튼 이벤트 핸들러
    const onSaveHandler = async () => {
        if (window.confirm("저장하시겠습니까?")) {
            await CmmFnc.fncRequestAxios(
                "POST",
                "/admin/dept/updateDept.do",
                selectedNode.data.form
            ).then((result) => {
                setTreeData((prevState) =>
                    [...prevState].map((data) => {
                        if (data.id === selectedNode.data.form.deptId) {
                            data.text = selectedNode.data.form.deptNm;
                            data.data.form.deptNm =
                                selectedNode.data.form.deptNm;
                        }
                        return data;
                    })
                );
            });
        }
    };

    //노드 드래그 이동 시 수정
    const onUpdateHandler = async (changeTreeList) => {
        await CmmFnc.fncRequestAxios("POST", "/admin/dept/updateDeptNode.do", {
            deptList: changeTreeList,
        });
    };

    // 하단 삭제 버튼 이벤트 핸들러
    const onDeleteHandler = async (node) => {
        if (window.confirm("삭제하시겠습니까?")) {
            const targetNode = node === undefined ? selectedNode : node;
            const childNode = treeData.filter(
                (item) => item.parent === targetNode.id
            );

            // 유형이 하위 존재가 가능하고 자식 노드 수가 0보다 클 때 삭제 방지
            if (targetNode.droppable && childNode.length > 0) {
                alert("하위 항목이 있을 경우 삭제할 수 없습니다.");
                return;
            } else {
                await CmmFnc.fncRequestAxios(
                    "POST",
                    "/admin/dept/deleteDept.do",
                    { deptId: targetNode.id }
                ).then((result) => {
                    let index = 0;
                    const changeTreeList = treeData
                        .filter((item) => item.id !== targetNode.id)
                        .map((item) => {
                            if (item.parent === targetNode.parent) {
                                item.data.form.deptSn = ++index;
                            }
                            return item;
                        });
                    onUpdateHandler(
                        changeTreeList
                            .filter((item) => item.parent === targetNode.parent)
                            .map((item) => item.data.form)
                    );
                    setTreeData(changeTreeList);
                    setSelectedNode(null);
                });
            }
        }
    };

    return (
        <>
            <div className="board_header">
                <h3>부서관리</h3>
            </div>
            <div className="board_body pd0">
                <AdminDeptTree
                    selectedNode={selectedNode}
                    setSelectedNode={setSelectedNode}
                    treeData={treeData}
                    setTreeData={setTreeData}
                    onUpdateNode={onUpdateHandler}
                    onDeleteNode={onDeleteHandler}
                />
                {selectedNode !== null && (
                    <AdminDeptForm
                        selectedNode={selectedNode}
                        setSelectedNode={setSelectedNode}
                    />
                )}
            </div>

            {selectedNode !== null && (
                <div className="board_footer">
                    <div className="bottom_wrap fr">
                        <button
                            className="btn_s mr5"
                            type="button"
                            title="삭제"
                            onClick={() => onDeleteHandler()}
                        >
                            삭제
                        </button>
                        <button
                            className="btn_s"
                            type="button"
                            title="저장"
                            onClick={onSaveHandler}
                        >
                            저장
                        </button>
                    </div>
                </div>
            )}
        </>
    );
}

export default AdminDept;
