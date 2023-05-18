import React from "react";
import FolderIcon from "@mui/icons-material/Folder";
import GroupsIcon from "@mui/icons-material/Groups";
import PersonIcon from "@mui/icons-material/Person";

/**
 * 노드 타입별 아이콘 표시 Component
 * @param {*} node   노드 정보를 Props로 전달 받음
 * @returns
 */
export const TypeIcon = ({ node }) => {
    if (node.droppable) {
        return <FolderIcon />;
    } else {
        if (node?.id !== undefined && !String(node.id).includes("DEPT")) {
            return <PersonIcon />;
        } else if (node?.id !== undefined && String(node.id).includes("DEPT")) {
            return <GroupsIcon />;
        }
    }
};
