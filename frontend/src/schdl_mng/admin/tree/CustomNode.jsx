import React, { useState } from "react";
import { Typography, IconButton } from "@mui/material";
import { Create, Delete, Autorenew } from "@material-ui/icons";
import { useDragOver } from "@minoru/react-dnd-treeview";

import { TypeIcon } from "schdl_mng/admin/tree/TypeIcon";
import styles from "schdl_mng/admin/tree/CustomNode.module.css";

/**
 * 노드 정보
 * @param {*} props     트리 내 노드 옵션 값을 Props로 받음
 * @returns
 */
const CustomNode = (props) => {
    // 마우스 hover 상태값 표시하기 위한 state
    const [hover, setHover] = useState(false);
    // Props 내 Key 값을 변수화
    const {
        node,
        depth,
        isModified,
        isOpen,
        isSelected,
        onToggle,
        onSelect,
        onCreate,
        onDelete,
        onChange,
        hasChild,
    } = props;
    // Depth에 따라 들여쓰기 위한 패딩값 변수
    const { id, text } = node;
    // 노드의 여닫기 이벤트
    const indent = depth * 24;
    // 노드 선택 이벤트
    const handleToggle = (e) => {
        e.stopPropagation();
        onToggle(id);
    };
    // 노드 선택 이벤트
    const handleSelect = () => onSelect(node);
    // 노드 드래그 Hook
    const dragOverProps = useDragOver(id, isOpen, onToggle);
    const drapRelProps = isModified
        ? dragOverProps
        : {
              onDragEnter: null,
              onDragLeave: null,
              onDrop: null,
          };

    return (
        <div
            className={`tree-node ${styles.root} ${
                isSelected ? styles.isSelected : ""
            } ${hover ? styles.isHovered : ""}`}
            style={{ marginLeft: indent }}
            {...drapRelProps}
            onMouseEnter={() => setHover(true)}
            onMouseLeave={() => setHover(false)}
        >
            <div
                className={`${styles.expandIconWrapper} ${
                    isOpen ? styles.isOpen : ""
                }`}
            >
                {hasChild && <div onClick={handleToggle}></div>}
            </div>
            <div>
                <TypeIcon node={node} />
            </div>
            <div className={styles.labelGridItem} onClick={handleSelect}>
                <Typography variant="body2">{text}</Typography>
            </div>
            {hover && isModified && (
                <div className={styles.actionDiv}>
                    {id !== "ROOT" && (
                        <div className={styles.actionButton}>
                            <IconButton
                                size="small"
                                title="유형 변경"
                                onClick={() => onChange(node)}
                            >
                                <Autorenew fontSize="small" />
                            </IconButton>
                        </div>
                    )}
                    <div className={styles.actionButton}>
                        <IconButton
                            size="small"
                            title="추가"
                            onClick={() => onCreate(node)}
                        >
                            <Create fontSize="small" />
                        </IconButton>
                    </div>
                    {id !== "ROOT" && (
                        <div className={styles.actionButton}>
                            <IconButton
                                size="small"
                                title="삭제"
                                onClick={() => onDelete(node)}
                            >
                                <Delete fontSize="small" />
                            </IconButton>
                        </div>
                    )}
                </div>
            )}
        </div>
    );
};

export default CustomNode;
