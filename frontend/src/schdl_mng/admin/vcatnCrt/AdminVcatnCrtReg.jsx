import React, { useState } from "react";
import { useNavigate } from "react-router-dom";

import URL from "context/url";
import CmmFnc from "context/common.js";

import AdminVcatnCrtTree from "schdl_mng/admin/vcatnCrt/AdminVcatnCrtTree";
import AdminVcatnCrtForm from "schdl_mng/admin/vcatnCrt/AdminVcatnCrtForm";

/**
 * 휴가 생성 정보 등록 Component
 * @returns
 */
function AdminVcatnCrtReg() {
    //페이지 이동 Hook
    const navigate = useNavigate();
    //선택한 노드 값
    const [selectedNode, setSelectedNode] = useState(null);

    // 저장 이벤트 핸들러
    const onSaveHandler = async () => {
        if (window.confirm("저장하시겠습니까?")) {
            const { form } = selectedNode.data;
            const { vcatnCrtYr, vcatnCnt } = form;

            if (
                String(vcatnCrtYr).length === 0 ||
                String(vcatnCnt).length === 0
            ) {
                alert("휴가생성일수와 생성년도는 필수 입력입니다.");
                return;
            }

            await CmmFnc.fncRequestAxios(
                "POST",
                "/admin/vcatnCrt/insertVcatnCrt.do",
                form
            ).then((result) => {
                alert("저장되었습니다.");
                return true;
            });
        }
    };

    // 목록버튼 클릭 시
    const onClickList = (e) => {
        navigate(`${URL.ROOT + URL.ADMIN}/${URL.ADMIN_VCATN_CRT_MANAGE}`, {});
    };

    return (
        <>
            <div className="board_header">
                <h3>휴가생성관리</h3>
            </div>
            <div className="board_body pd0">
                <AdminVcatnCrtTree
                    selectedNode={selectedNode}
                    setSelectedNode={setSelectedNode}
                />
                {selectedNode !== null && (
                    <AdminVcatnCrtForm
                        selectedNode={selectedNode}
                        setSelectedNode={setSelectedNode}
                    />
                )}
            </div>

            <div className="board_footer">
                <div className="bottom_wrap fr">
                    <button
                        className="btn_s mr5"
                        type="button"
                        title="목록 버튼"
                        onClick={onClickList}
                    >
                        목록
                    </button>
                    {selectedNode !== null && (
                        <>
                            <button
                                className="btn_s"
                                type="button"
                                title="저장"
                                onClick={onSaveHandler}
                            >
                                저장
                            </button>
                        </>
                    )}
                </div>
            </div>
        </>
    );
}

export default AdminVcatnCrtReg;
