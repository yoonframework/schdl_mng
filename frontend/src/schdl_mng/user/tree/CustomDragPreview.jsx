import React from "react";
import { TypeIcon } from "schdl_mng/admin/tree/TypeIcon";
import styles from "schdl_mng/admin/tree/CustomDragPreview.module.css";

/**
 * 드래그 이동 미리보기를 표시하는 Component
 * @param {*} props    트리 내 노드 옵션 값을 Props로 받음
 * @returns
 */
const CustomDragPreview = (props) => {
    const item = props.monitorProps.item;

    return (
        <div className={styles.root}>
            <div className={styles.icon}>
                <TypeIcon node={item} />
            </div>
            <div className={styles.label}>{item.text}</div>
        </div>
    );
};

export default CustomDragPreview;
