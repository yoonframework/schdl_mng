import { useRef } from "react";
import { Tree } from "@minoru/react-dnd-treeview";

import CustomNode from "schdl_mng/admin/tree/CustomNode";
import CustomDragPreview from "schdl_mng/admin/tree/CustomDragPreview";
import CustomDragPlaceholder from "schdl_mng/admin/tree/CustomDragPlaceholder";

import CmmFnc from "context/common.js";

import styles from "schdl_mng/admin/dept/AdminDeptTree.module.css";

/**
 * 부서관리 트리 Component
 * @param {*} treeData         트리 데이터 state
 * @param {*} setTreeData      변경된 정보를 트리에 다시 저장 setState
 * @param {*} selectedNode     선택된 노드 정보 state
 * @param {*} setSelectedNode  선택한 노드 정보로 변경 setState
 * @param {*} onUpdateNode     노드 정보 업데이트 함수
 * @param {*} onDeleteNode     노드 삭제 함수
 * @returns
 */
function AdminDeptTree({
    treeData,
    setTreeData,
    selectedNode,
    setSelectedNode,
    onUpdateNode,
    onDeleteNode,
}) {
    // 트리 데이터
    const ref = useRef(null);

    //노드 드래그 이동 이벤트
    const handleDrop = (newTree, { dragSource, dropTarget }) => {
        //최상위 루트 이동 금지
        if (dropTarget !== undefined) {
            const changeTreeList = [];
            //출발지 소속 메뉴 순서 변경
            newTree
                .filter((item) => item.parent === dragSource.parent)
                .forEach((item, i) => {
                    item.data.form.deptSn = i + 1;
                    changeTreeList.push(item.data.form);
                });
            //목적지 소속 메뉴 순서 변경
            newTree
                .filter((item) => item.parent === dropTarget.id)
                .forEach((item, i) => {
                    item.parent = dropTarget.id;
                    item.data.form.deptSn = i + 1;
                    item.data.form.upDeptId = dropTarget.id;
                    changeTreeList.push(item.data.form);
                });
            setTreeData(newTree);
            onUpdateNode(changeTreeList);
            setSelectedNode(dragSource);
            ref.current.open(dropTarget.id);
        }
    };

    //노드 선택시 이벤트
    const handleSelect = (node) => {
        //최상위 루트 선택 금지
        node.id !== "ROOT" && setSelectedNode(node);
    };

    //노드 생성시 이벤트
    const handleCreate = async (node) => {
        //노드 생성과 동시에 DB에 저장
        const insertedData = await CmmFnc.fncRequestAxios(
            "POST",
            "/admin/dept/insertDept.do",
            {
                deptNm: "부서명",
                upDeptId: node.id,
                useYn: "Y",
            }
        ).then((result) => {
            return result.data;
        });

        //새 노드 화면에 표시할 데이터
        const newNode = {
            parent: insertedData.upDeptId,
            id: insertedData.deptId, //DB에서 받아온 ID값
            droppable: false,
            text: insertedData.deptNm,
            data: {
                form: insertedData,
            },
        };

        //상위 노드는 하위 구조를 가질 수 있는 값으로 변경
        if (!node.droppable) {
            node.droppable = true;
        }

        setTreeData([...treeData, newNode]);
        setSelectedNode(newNode);
        ref.current.open(newNode.parent);
    };

    //노드 유형 변경 이벤트(폴더 OR 항목)
    const handleChange = (node) => {
        const childNode = treeData.filter((item) => item.parent === node.id);

        // 유형이 하위 존재가 가능하고 자식 노드 수가 0보다 클 때 유형 변경 방지
        if (node.droppable && childNode.length > 0) {
            alert("하위 항목이 있을 경우 유형을 변경할 수 없습니다.");
            return;
        } else {
            node.droppable = !node.droppable;
            setTreeData([...treeData]);
        }
    };

    //노드 삭제 이벤트
    const handleDelete = (node) => {
        onDeleteNode(node);
    };

    /**
     * 
     * options.isOpen	부울	노드의 열린 상태와 닫힌 상태입니다. 그렇지 droppable  않으면 true  isOpen은 항상 false입니다.
       options.dragable	부울	이 노드를 끌 수 있는지 여부를 나타냅니다.
       options.hasChild	부울	노드에 자식이 있는지 여부를 나타내는 플래그입니다. 노드에 자식이 있으면 true이고, 그렇지 않으면 false입니다.
       options.onToggle	기능	노드의 열기/닫기 버튼에 대한 이벤트 핸들러입니다.
     * 
     */
    return (
        <div className="body_left">
            {Object.values(treeData).length !== 0 && (
                <Tree
                    // react Dom 직접 접근
                    ref={ref}
                    tree={treeData} //전체 데이터
                    rootId={"-"} //루트 아이디
                    // Tree Node 작성
                    render={
                        (node, options) => {
                            return (
                                <CustomNode
                                    node={node}
                                    {...options}
                                    onCreate={handleCreate}
                                    onDelete={handleDelete}
                                    onSelect={handleSelect}
                                    onChange={handleChange}
                                    isSelected={node.id === selectedNode?.id}
                                    isModified={true}
                                />
                            );
                        }
                        //노드 형태
                    }
                    dragPreviewRender={(monitorProps) => (
                        <CustomDragPreview monitorProps={monitorProps} />
                    )} // 드래그 미리보기
                    onDrop={handleDrop} //트리 상태가 변경될때
                    // Class 정의
                    classes={{
                        root: styles.treeRoot,
                        draggingSource: styles.draggingSource,
                        dropTarget: styles.dropTarget,
                        placeholder: styles.placeholderContainer,
                    }}
                    // 자동정렬
                    sort={false}
                    // Drag 시 첫번째로 자동 위치
                    insertDroppableFirst={false}
                    // Drop을 할 수 있는 Node
                    canDrop={(tree, { dragSource, dropTargetId }) => {
                        if (dragSource?.parent === dropTargetId) {
                            return true;
                        }
                    }}
                    dropTargetOffset={0}
                    // Drag시 가능 Node 선 작성
                    placeholderRender={(node, { depth }) => (
                        <CustomDragPlaceholder node={node} depth={depth} />
                    )}
                    // 전체 Node Open
                    initialOpen={true}
                />
            )}
        </div>
    );
}

export default AdminDeptTree;
