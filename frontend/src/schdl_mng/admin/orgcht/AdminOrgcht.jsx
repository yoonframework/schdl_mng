import { Fragment, useEffect, useState } from "react";

import AdminOrgchtTree from "schdl_mng/admin/orgcht/AdminOrgchtTree";
import AdminOrgchtMbrMap from "schdl_mng/admin/orgcht/AdminOrgchtMbrMap";
import AdminOrgchtMbrList from "schdl_mng/admin/orgcht/AdminOrgchtMbrList";
import OrgchtMbrDtlModal from "schdl_mng/common/cmmn/orgcht/OrgchtMbrDtlModal";
import CmmFnc from "context/common";

// 조직도 뷰 타입 명시
const ORGCHT_VIEW = [
    {
        idx: 0,
        name: "조직도",
        type: "treeList",
    },
    {
        idx: 1,
        name: "사용자맵",
        type: "map",
    },
];

//부서정보 리스트 불러오기
const fncGetOrgnzt = async () => {
    return await CmmFnc.fncRequestAxios(
        "POST",
        "/cmmn/orgcht/selectDeptList.do"
    ).then((result) => {
        if (result.data.sttus === "success") {
            return result.data.result.deptList;
        }
    });
};

/**
 * 조직도 Layout
 * @param {*} setModalInfo    모달 정보 변경 setState
 * @returns
 */
function AdminOrgcht({ setModalInfo }) {
    //선택한 노드 값
    const [selectedNode, setSelectedNode] = useState(null);
    //현재 뷰 타입을 상태값으로 저장(최초는 조직도)
    const [currentView, setCurrentView] = useState(ORGCHT_VIEW[0]);
    //부서 목록
    const [deptList, setDeptList] = useState([]);
    //선택된 부서 ID
    const [selectedDeptId, setSelectedDeptId] = useState("");

    // 직원 목록 함수 호출
    useEffect(() => {
        const getDeptList = async () => {
            const list = await fncGetOrgnzt();
            if (list !== undefined && Object.values(list).length > 0) {
                setDeptList(list);
            }
        };
        // 함수 호출
        getDeptList();
    }, []);

    // 뷰 타입 변경 이벤트 setState
    const onViewChangeHandler = (e, item) => {
        setCurrentView(item);
    };
    // 직원 상세 정보 모달 호출
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

    //모달 콜백 함수
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

    //부서 선택시
    const onChangeDeptId = (e) => {
        setSelectedDeptId(e.target.value);
    };

    return (
        <>
            <div className="board_header">
                <h3>{currentView.name}</h3>
                {ORGCHT_VIEW?.filter(
                    (item) => item.idx !== currentView.idx
                )?.map((item) => (
                    <Fragment key={item.idx}>
                        <button
                            type="button"
                            className="btn_m fr"
                            onClick={(e) => onViewChangeHandler(e, item)}
                        >
                            {item.name}
                        </button>
                        {currentView.type === "map" && (
                            <select
                                name="deptId"
                                id="deptId"
                                className="select fr mr20"
                                title="부서 선택"
                                defaultValue={selectedDeptId}
                                onChange={onChangeDeptId}
                            >
                                <option value={""}>전체</option>
                                {deptList.map((item) => (
                                    <option
                                        key={item.deptId}
                                        value={item.deptId}
                                    >
                                        {item.deptNm}
                                    </option>
                                ))}
                            </select>
                        )}
                    </Fragment>
                ))}
            </div>
            <div className="board_body pd0">
                {currentView.type === "treeList" && (
                    <>
                        <AdminOrgchtTree
                            selectedNode={selectedNode}
                            setSelectedNode={setSelectedNode}
                        />
                        <AdminOrgchtMbrList
                            selectedNode={selectedNode}
                            onClickMbrDtl={onClickMbrDtl}
                        />
                    </>
                )}
                {currentView.type === "map" && (
                    <AdminOrgchtMbrMap
                        selectedDeptId={selectedDeptId}
                        onClickMbrDtl={onClickMbrDtl}
                    />
                )}
            </div>
        </>
    );
}

export default AdminOrgcht;
