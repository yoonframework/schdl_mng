import { Tree } from "@minoru/react-dnd-treeview";

import CustomNode from "schdl_mng/user/tree/CustomNode";

import styles from "schdl_mng/user/schdl/bsrp/BsrpCncdntAddTree.module.css";

/**
 * 동행자 추가 모달의 트리
 * @param {*} treeData          트리데이터
 * @param {*} selectedNode      선택된 노드
 * @param {*} setSelectedNode   선택된 노드 상태값 변경(타 노드 선택시)
 * @returns
 */
function BsrpCncdntAddTree({ treeData, selectedNode, setSelectedNode }) {
    //노드 선택시 이벤트
    const handleSelect = (node) => {
        if (!node.droppable && !String(node.id).includes("DEPT")) {
            if (selectedNode === null) {
                setSelectedNode([node]);
            } else {
                if (
                    [...selectedNode].map((item) => item.id).includes(node.id)
                ) {
                    setSelectedNode(
                        [...selectedNode].filter((item) => item.id !== node.id)
                    );
                } else {
                    setSelectedNode([...selectedNode, node]);
                }
            }
        }
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
                    tree={treeData} //전체 데이터
                    rootId={"-"} //루트 아이디
                    // Tree Node 작성
                    render={
                        (node, options) => (
                            <CustomNode
                                node={node}
                                {...options}
                                onSelect={handleSelect}
                                isSelected={
                                    selectedNode !== null &&
                                    [...selectedNode].filter(
                                        (item) => item.id === node.id
                                    ).length > 0
                                }
                                isModified={false}
                            />
                        )
                        //노드 형태
                    }
                    // Class 정의
                    classes={{
                        root: styles.treeRoot,
                    }}
                    // 전체 Node Open
                    initialOpen={true}
                    sort={false}
                    insertDroppableFirst={false}
                />
            )}
        </div>
    );
}

export default BsrpCncdntAddTree;
