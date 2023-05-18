import React, { Fragment, useEffect, useState } from "react";
import { Navigate, Route, Routes, useLocation } from "react-router-dom";
import { connect } from "react-redux";
import { debounce } from "lodash";
import { persistor } from "store";

//context
import URL from "context/url";
import CmmFnc from "context/common.js";

//layout
import RootLayout from "schdl_mng/layouts/RootLayout";
import DefaultLayout from "schdl_mng/layouts/DefaultLayout";
import DefaultSubLayout from "schdl_mng/layouts/DefaultSubLayout";
import MyPageLayout from "schdl_mng/layouts/MyPageLayout";
import AdminLayout from "schdl_mng/layouts/AdminLayout";

//common
import Modal from "schdl_mng/common/user/Modal";
import PageError from "schdl_mng/error/PageError";

//USER
import Main from "schdl_mng/user/main/Main";
import Schdl from "schdl_mng/user/schdl/Schdl";
import Login from "schdl_mng/user/login/Login";
import Test from "schdl_mng/user/test/Test";
import BsrpReg from "schdl_mng/user/schdl/bsrp/BsrpReg";
import VcatnReg from "schdl_mng/user/schdl/vcatn/VcatnReg";
import BsrpMdfcn from "schdl_mng/user/schdl/bsrp/BsrpMdfcn";
import VcatnMdfcn from "schdl_mng/user/schdl/vcatn/VcatnMdfcn";
import Orgcht from "schdl_mng/user/orgcht/Orgcht";

//마이페이지
import MyPageMbr from "schdl_mng/user/mypage/MyPageMbr";
import MyPageVcatn from "schdl_mng/user/mypage/MyPageVcatn";
import MyPageBsrp from "schdl_mng/user/mypage/MyPageBsrp";

//ADMIN
//일반회원관리
import AdminMbrList from "schdl_mng/admin/mbr/AdminMbrList";
import AdminMbrReg from "schdl_mng/admin/mbr/AdminMbrReg";
import AdminMbrMdfcn from "schdl_mng/admin/mbr/AdminMbrMdfcn";
//휴가생성관리
import AdminVcatnCrtList from "schdl_mng/admin/vcatnCrt/AdminVcatnCrtList";
import AdminVcatnCrtReg from "schdl_mng/admin/vcatnCrt/AdminVcatnCrtReg";
import AdminVcatnCrtBndeReg from "schdl_mng/admin/vcatnCrt/AdminVcatnCrtBndeReg";
//조직도
import AdminOrgcht from "schdl_mng/admin/orgcht/AdminOrgcht";
//부서관리
import AdminDept from "schdl_mng/admin/dept/AdminDept";

/**
 * 서버의 메뉴 및 세션정보를 가져오는 함수
 * @param {*} curUrl
 * @param {*} location
 * @returns
 */
const fncSessionInfo = async (curUrl, location) => {
    return await CmmFnc.fncRequestAxios(
        "POST",
        "/stng/selectSession.do",
        curUrl
    )
        .then((result) => {
            if (result.data.sttus === "success") {
                return result.data.result;
            } else {
                // 현재 URL이 로그인, ROOT가 아닐 시
                if (
                    location.pathname !== URL.LOGIN &&
                    location.pathname !== URL.ROOT &&
                    curUrl !== URL.LOGIN
                ) {
                    // 브라우저 세션스토리지에 저장되어 있는 세션정보를 삭제
                    persistor.purge();
                    const msg =
                        result.data.sttus === "sessionExpry" &&
                        "로그인 정보가 유효하지 않습니다.";
                    alert(msg);
                    // LOGIN 페이지로 이동
                    window.location.href = URL.LOGIN;
                }
            }
        })
        .then((data) => {
            // 서버에서 조회한 메뉴 및 세션정보 대입
            const session = {
                menuList: data?.menuVO.filter((menu) =>
                    curUrl.includes(URL.ADMIN)
                        ? menu.menuGb === "A"
                        : menu.menuGb === "U" || menu.menuGb === "M"
                ),
                menuNaviList: data?.menuNaviVO,
                menuDepthList: data?.menuDepthVO.filter(
                    (menu) =>
                        menu.menuGb ===
                        (curUrl.includes(URL.ADMIN)
                            ? "A"
                            : curUrl.includes(URL.MYPAGE)
                            ? "M"
                            : "U")
                ),
                functionAutho: data?.functionAutho,
                thisMenuInfo: data?.thisMenuInfo,
                curUrl: curUrl,
                loginVO: data?.loginVO,
            };

            return session;
        });
};

/**
 *
 * @param {*} loginVO - Store로부터 LoginVO객체를 Load
 * @param {*} onSessionUpdated - Store로 전달한 Dispatch를 호출하는 함수
 * @returns
 */
function App({ loginVO, onSessionUpdated }) {
    // 현재의 URL을 대표하는 Location 객체를 반환
    const location = useLocation();
    // useState - 컴포넌트에서 동적인 값을 상태(State)로 관리할 수 있는 Hook
    // 세션정보를 관리하는 State
    const [sessionInfo, setSessionInfo] = useState({
        menuList: [],
        menuNaviList: [],
        menuDepthList: [],
        functionAutho: "",
        thisMenuInfo: "",
        curUrl: "",
        loginVO: {},
    });
    // 창 크기 state
    const [windowSize, setWindowSize] = useState({
        width: window.innerWidth,
        height: window.innerHeight,
    });
    // 모덜 상태를 관리하는 state
    const [modalInfo, setModalInfo] = useState({
        showModal: false,
        width: 0,
        height: 0,
        title: "",
        content: "",
    });

    // 반응형 - 윈도우 사이즈 변경 시 상태값 변경
    // debounce - 바로 렌더링이 되면 부하가 발생할 요지가 있으므로 delay 시간 설정
    // handleResize 함수를 debounce로 감싸고, 시간을 설정한다
    // 1000ms = 1sec
    useEffect(() => {
        const handleResize = debounce(() => {
            setWindowSize({
                width: window.innerWidth,
                height: window.innerHeight,
            });
        }, 500);

        window.addEventListener("resize", handleResize);
        return () => {
            // cleanup
            window.removeEventListener("resize", handleResize);
        };
    }, []);

    // useEffect - 리액트 컴포넌트가 렌더링 될 때마다 특정 작업을 실행할 수 있도록 하는 Hook
    // url 정보가 변경되어 location 객체가 갱신할 때 실행되는 함수
    useEffect(() => {
        // 현재 URL 정보
        const curUrl = window.location.pathname;
        if (!curUrl.includes("error")) {
            // 서버에서 세션정보를 가져오는 함수
            // async - 반환받는 값은 Promise로 반환
            const getSessionInfo = async () => {
                // awit - 프라미스가 처리(settled)될 때까지 기다림
                const data = await fncSessionInfo(curUrl, location);
                if (data.loginVO !== undefined) {
                    setSessionInfo(data);
                }
            };
            // 함수 호출
            getSessionInfo();
        }

        if (curUrl.includes("admin")) {
            document.body.classList.add("admin");
        } else if (curUrl.includes("error")) {
            document.body.classList.add("error");
        } else {
            document.body.classList.remove("admin");
        }
    }, [location]);

    // url 정보가 변경되어 sessionInfo, onSessionUpdated, loginVO 객체가 갱신할 때 실행되는 함수
    useEffect(() => {
        // onSessionUpdated 함수 호출
        onSessionUpdated(sessionInfo.loginVO);
    }, [sessionInfo, onSessionUpdated, loginVO]);
    return (
        // DOM에 별도의 노드를 추가하지 않고 여러 자식을 그룹화
        <Fragment>
            {/* route들을 구성하는 부모 요소 */}
            <Routes>
                {/* 컴포넌트의 속성에 설정된 URL과 현재 경로가 일치하면 해당하는 컴포넌트, 함수를 렌더링
                    path - 매칭 url
                    element - 호출할 컴포넌트 */}
                <Route
                    path={`${URL.ROOT}`}
                    element={
                        <RootLayout
                            sessionInfo={sessionInfo}
                            windowSize={windowSize}
                        />
                    }
                >
                    <Route path={URL.HOME} element={<DefaultLayout />}>
                        <Route index element={<Main />} />
                    </Route>
                    <Route path={URL.SCHDL} element={<DefaultSubLayout />}>
                        <Route
                            path={`${URL.SCHDL_VCATN}/${URL.REG}`}
                            element={<VcatnReg setModalInfo={setModalInfo} />}
                        />
                        <Route
                            path={`${URL.SCHDL_BSRP}/${URL.REG}`}
                            element={<BsrpReg setModalInfo={setModalInfo} />}
                        />
                        <Route
                            path={`${URL.SCHDL_VCATN}/${URL.MDFCN}`}
                            element={<VcatnMdfcn />}
                        />
                        <Route
                            path={`${URL.SCHDL_BSRP}/${URL.MDFCN}`}
                            element={<BsrpMdfcn setModalInfo={setModalInfo} />}
                        />
                        <Route
                            index
                            element={
                                <Schdl
                                    setModalInfo={setModalInfo}
                                    windowSize={windowSize}
                                />
                            }
                        />
                    </Route>
                    <Route path={URL.ORGCHT} element={<DefaultSubLayout />}>
                        <Route
                            index
                            element={<Orgcht setModalInfo={setModalInfo} />}
                        />
                    </Route>
                    <Route path={URL.MYPAGE} element={<MyPageLayout />}>
                        <Route
                            path={URL.MYPAGE_VCATN}
                            element={
                                <MyPageVcatn setModalInfo={setModalInfo} />
                            }
                        />
                        <Route
                            path={URL.MYPAGE_BSRP}
                            element={<MyPageBsrp setModalInfo={setModalInfo} />}
                        />
                        <Route
                            index
                            element={<MyPageMbr setModalInfo={setModalInfo} />}
                        />
                    </Route>
                    <Route path="test" element={<DefaultSubLayout />}>
                        <Route path="test4" element={<Test />} />
                        <Route path="test3" element={<Test />} />
                        <Route path="test2" element={<Test />} />
                        <Route path="test1" element={<Test />} />
                    </Route>
                    <Route
                        index
                        element={<Navigate replace to={URL.LOGIN} />}
                    />
                    {/* 관리자 */}
                    <Route path={URL.ADMIN} element={<AdminLayout />}>
                        <Route path={URL.ADMIN_MBER_MANAGE}>
                            <Route
                                path={URL.REG}
                                element={
                                    <AdminMbrReg setModalInfo={setModalInfo} />
                                }
                            />
                            <Route
                                path={URL.MDFCN}
                                element={
                                    <AdminMbrMdfcn
                                        setModalInfo={setModalInfo}
                                    />
                                }
                            />
                            <Route index element={<AdminMbrList />} />
                        </Route>
                        <Route path={URL.ADMIN_VCATN_CRT_MANAGE}>
                            <Route
                                path={URL.REG}
                                element={<AdminVcatnCrtReg />}
                            />
                            <Route
                                path={URL.BNDE_REG}
                                element={<AdminVcatnCrtBndeReg />}
                            />
                            <Route index element={<AdminVcatnCrtList />} />
                        </Route>
                        <Route
                            path={URL.ADMIN_ORGCHT}
                            element={
                                <AdminOrgcht setModalInfo={setModalInfo} />
                            }
                        ></Route>
                        <Route
                            path={URL.ADMIN_DEPT}
                            element={<AdminDept />}
                        ></Route>
                        <Route
                            index
                            element={
                                <Navigate replace to={URL.ADMIN_MBER_MANAGE} />
                            }
                        />
                    </Route>
                    {/* 관리자 */}
                </Route>
                <Route path={URL.LOGIN} element={<Login />} />
                <Route path={"/error/:status"} element={<PageError />} />
                <Route
                    path={"*"}
                    element={<Navigate replace to={`${URL.ERROR}/404`} />}
                />
            </Routes>
            {modalInfo.showModal && (
                <Modal modalInfo={modalInfo} setModalInfo={setModalInfo} />
            )}
        </Fragment>
    );
}

/*
 * connect          - 컴포넌트가 store에 접근할 권한이 있을 때 store에 접근
 * mapStateToProps  - store로부터 state를 가져와서 컴포넌트의 props로 보내게 해준다
 * mapDispatchToProps - dispatch를 props로 store로 보냄
 */
function mapStateToProps(state) {
    const session = state.reducer;
    return {
        loginVO: session.loginVO,
    };
}

function mapDispatchToProps(dispatch) {
    return {
        onSessionUpdated: (loginVO) => {
            dispatch({ type: "LOGIN_SESSION", loginVO });
        },
    };
}

export default connect(mapStateToProps, mapDispatchToProps)(App);
