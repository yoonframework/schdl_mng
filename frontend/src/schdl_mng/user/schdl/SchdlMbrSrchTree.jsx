import { Tree } from "@minoru/react-dnd-treeview";

import CustomNode from "schdl_mng/user/tree/CustomNode";

import styles from "schdl_mng/user/schdl/SchdlMbrSrchTree.module.css";

/**
 * @param {*} treeData        트리데이터
 * @param {*} selectedNode    선택된 노드 정보
 * @param {*} setSelectedNode  선택된 노드 정보 변경
 * @returns
 */
function SchdlMbrSrchTree({ treeData, selectedNode, setSelectedNode }) {
    //노드 선택시 이벤트
    const handleSelect = (node) => {
        !node.droppable &&
            !String(node.id).includes("DEPT") &&
            setSelectedNode(node);
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
                                isSelected={node.id === selectedNode?.id}
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

export default SchdlMbrSrchTree;
