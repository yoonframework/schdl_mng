import { useState } from "react";
import CmmFnc from "context/common";
import OrgchtMbrList from "schdl_mng/user/orgcht/OrgchtMbrList";
import OrgchtTree from "schdl_mng/user/orgcht/OrgchtTree";
import OrgchtMbrDtlModal from "schdl_mng/common/cmmn/orgcht/OrgchtMbrDtlModal";

/**
 *
 * @param {*} setModalInfo 모달 정보 setState
 * @returns
 */
function Orgcht({ setModalInfo }) {
    // 트리 데이터
    //선택한 노드 값
    const [selectedNode, setSelectedNode] = useState(null);

    //직원 선택 시 상세 모달 호출
    const onClickMbrDtl = async (e, mbr) => {
        e.preventDefault();

        const mbrInfo = await CmmFnc.fncRequestAxios(
            "POST",
            "/cmmn/orgcht/selectMbr.do",
            mbr
        ).then((result) => {
            if (result.data.sttus === "success") {
                return result.data.result.mberManageVO;
            }
        });

        setModalInfo((prevState) => {
            return {
                ...prevState,
                showModal: true,
                isLoadingUI: false,
                width: 600,
                height: 620,
                title: "직원 정보",
                content: (
                    <OrgchtMbrDtlModal
                        callback={onModalActionCallback}
                        mbrInfo={mbrInfo}
                    />
                ),
            };
        });
    };

    //모션 콜백
    const onModalActionCallback = (element) => {
        if (element.action === "close") {
            setModalInfo((prevState) => {
                return {
                    ...prevState,
                    showModal: false,
                    isLoadingUI: false,
                    width: 0,
                    height: 0,
                    title: "",
                    content: "",
                };
            });
        }
    };

    return (
        <div className="content board_wrap" id="skip_content">
            <div className="inner">
                <div className="orgcht mb0">
                    <>
                        <OrgchtTree
                            selectedNode={selectedNode}
                            setSelectedNode={setSelectedNode}
                        />
                        <OrgchtMbrList
                            selectedNode={selectedNode}
                            onClickMbrDtl={onClickMbrDtl}
                        />
                    </>
                </div>
            </div>
        </div>
    );
}

export default Orgcht;
