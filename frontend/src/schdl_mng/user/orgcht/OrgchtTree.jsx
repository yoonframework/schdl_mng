import { useEffect, useRef, useState } from "react";
import { Tree } from "@minoru/react-dnd-treeview";

import CustomNode from "schdl_mng/user/tree/CustomNode";
import styles from "schdl_mng/user/orgcht/OrgchtTree.module.css";
import CmmFnc from "context/common";

/**
 * 마이페이지 - 조직도 - 트리
 * @param {*} selectedNode     선택된 노드 state
 * @param {*} setSelectedNode  선택한 노드 setState
 * @returns
 */
function OrgchtTree({ selectedNode, setSelectedNode }) {
    // 트리 데이터
    const [treeData, setTreeData] = useState([]);
    const treeListRef = useRef(null);

    // useEffect(() => {
    //     let startY;

    //     const onDragStart = (e) => {
    //         if (
    //             !!e.changedTouches &&
    //             !!e.changedTouches[0] &&
    //             !!e.changedTouches[0].pageY
    //         ) {
    //             startY = e.changedTouches[0].pageY;
    //         }
    //     };

    //     const onDragMove = (e) => {
    //         window.scrollTo({
    //             top: startY - e.changedTouches[0].pageY,
    //             left: window.scrollX,
    //             behavior: "smooth",
    //         });
    //     };

    //     const list = treeListRef.current;
    //     if (list) {
    //         list.addEventListener("touchstart", onDragStart);
    //         list.addEventListener("touchmove", onDragMove);
    //     }

    //     return () => {
    //         if (list) {
    //             list.removeEventListener("touchstart", onDragStart);
    //             list.removeEventListener("touchmove", onDragMove);
    //         }
    //     };
    // });

    //부서정보 리스트 불러오기
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

    useEffect(() => {
        fncGetOrgnzt();
    }, []);

    const handleSelect = (node) => {
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
        <div className="tree_list" ref={treeListRef}>
            {Object.values(treeData).length !== 0 && (
                <Tree
                    tree={treeData} //전체 데이터
                    rootId={"-"} //루트 아이디
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
                    classes={{
                        root: styles.treeRoot,
                    }}
                    // 전체 Node Open
                    initialOpen={true}
                    sort={false}
                    insertDroppableFirst={false}
                    onDrop={() => null}
                />
            )}
        </div>
    );
}

export default OrgchtTree;
