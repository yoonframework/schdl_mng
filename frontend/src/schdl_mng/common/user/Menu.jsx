import React from "react";
import { NavLink, Link, useOutletContext, useNavigate } from "react-router-dom";
import { connect } from "react-redux";
import { persistor } from "store";

import CmmFnc from "context/common.js";
import URL from "context/url";

/**
 * 메뉴 Component
 * @param {*} loginVO   store에 저장된 로그인 정보를 가져옴
 * @returns
 */
function Menu({ loginVO }) {
    // 페이지 이동 Hook
    const navigate = useNavigate();
    // 부모 Component에서 Outlet으로 전달한 Context에서 세션 정보를 변수로 설정
    const { sessionInfo } = useOutletContext();
    // 세션 정보 내 Key 값을 변수로 지정
    const { menuList, menuNaviList, curUrl } = sessionInfo;

    // 로그아웃 핸들러
    const onClickLogoutHandler = (e) => {
        e.preventDefault();
        CmmFnc.fncRequestAxios("GET", "/uat/uia/actionLogoutAPI.do").then(
            () => {
                persistor.purge();
                window.location.href = URL.LOGIN;
            }
        );
    };

    // 관리자 메뉴로 이동
    const onLocateAdminClick = (e) => {
        e.preventDefault();

        if (
            /Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(
                navigator.userAgent
            )
        ) {
            alert("관리자는 PC 접속으로만 가능합니다.");
            return false;
        } else {
            navigate(`${URL.ROOT + URL.ADMIN}`, {});
        }
    };

    // 최상단 메뉴 Mouse Enter 시 클래스 추가
    const onGnbShowIn = () => {
        document.body.classList.add("gnbOn");
    };

    // 최상단 메뉴 Mouse Leave 시 클래스 추가
    const onGnbShowOut = () => {
        document.body.classList.remove("gnbOn");
        document.body
            .querySelectorAll(".submenu_wrap")
            .forEach((item) => (item.style.opacity = 0));
    };

    // 최상단 메뉴의 하위 메뉴 표시 이벤트
    const onDepthOneShowIn = (e) => {
        document.body
            .querySelectorAll(".submenu_wrap")
            .forEach((item) => (item.style.opacity = 0));
        document.body
            .querySelectorAll(".submenu_wrap")
            .forEach((item) => item.classList.remove("active"));
        document.body
            .querySelectorAll(".gnb li.depth1")
            .forEach((item) => item.classList.remove("on"));

        //e.target.tagName = SPAN, LI, A
        switch (e.target.tagName) {
            case "SPAN":
            case "A":
                e.target.closest("li.depth1").classList.add("on");
                if (
                    e.target
                        .closest("li.depth1")
                        .querySelector(".submenu_wrap") !== null
                ) {
                    e.target
                        .closest("li.depth1")
                        .querySelector(".submenu_wrap").style.opacity = 1;
                }

                break;
            case "LI":
                e.target.classList.add("on");
                if (e.target.querySelector(".submenu_wrap") !== null) {
                    e.target.querySelector(".submenu_wrap").style.opacity = 1;
                }

                break;
            default:
                break;
        }
    };

    // 헤더에서 Mouse Leave 이벤트가 발생될 경우
    const onHeaderOut = () => {
        document.body.classList.remove("gnbOn");
        document.body
            .querySelectorAll(".submenu_wrap")
            .forEach((item) => item.classList.remove("active"));
        document.body
            .querySelectorAll(".gnb li.depth1")
            .forEach((item) => item.classList.remove("on"));
    };

    //마지막 메뉴에서 Mouse Leave 이벤트가 발생될 경우
    const onGnbTabKeyOut = () => {
        document.body.classList.remove("gnbOn");
        document.body
            .querySelectorAll(".submenu_wrap")
            .forEach((item) => item.classList.remove("active"));
    };

    return (
        <header className="header" onMouseLeave={onHeaderOut}>
            <h1>
                <Link to={`${URL.ROOT + URL.SCHDL}`} className="logo1">
                    로고
                </Link>
            </h1>
            <div
                className="gnb"
                id="gnb_wrap"
                onMouseEnter={onGnbShowIn}
                onFocus={onGnbShowIn}
                onMouseLeave={onGnbShowOut}
                onBlur={onGnbShowOut}
            >
                <ul>
                    {menuList.length > 0 &&
                        menuList
                            ?.filter(
                                (menu, i, array) =>
                                    menu.upperMenuNo ===
                                    array
                                        ?.filter(
                                            (upper) =>
                                                upper.upperMenuNo === "ROOT" &&
                                                menu.menuGb === "U"
                                        )
                                        .pop()?.menuNo
                            )
                            ?.map((depth1, depth1Index, depth1List) => (
                                <li
                                    key={depth1.menuNo}
                                    className={`depth1${
                                        menuNaviList?.filter(
                                            (navi) =>
                                                navi.menuNo === depth1.menuNo
                                        ).length > 0
                                            ? " active"
                                            : ""
                                    }`}
                                    onMouseEnter={onDepthOneShowIn}
                                    onFocus={onDepthOneShowIn}
                                >
                                    <NavLink
                                        to={{
                                            pathname: depth1.progrUrl,
                                        }}
                                    >
                                        <span>{depth1.menuNm}</span>
                                    </NavLink>
                                    {depth1.childCnt !== 0 ? (
                                        <div className="submenu_wrap">
                                            <div className="inner">
                                                <div className="home">
                                                    <NavLink
                                                        to={{
                                                            pathname: URL.HOME,
                                                        }}
                                                    >
                                                        <em className="blind">
                                                            홈으로 이동
                                                        </em>
                                                    </NavLink>
                                                </div>
                                                <strong>{depth1.menuNm}</strong>
                                                <div className="submenu">
                                                    <ul className="depth2">
                                                        {menuList.map(
                                                            (
                                                                depth2,
                                                                depth2Index,
                                                                depth2List
                                                            ) =>
                                                                depth2.upperMenuNo ===
                                                                depth1.menuNo ? (
                                                                    <li
                                                                        key={
                                                                            depth2Index
                                                                        }
                                                                        className={
                                                                            curUrl ===
                                                                            depth2.progrUrl
                                                                                ? "active"
                                                                                : ""
                                                                        }
                                                                        onBlur={
                                                                            depth1List.length -
                                                                                1 ===
                                                                                depth1Index &&
                                                                            depth2List.length -
                                                                                1 ===
                                                                                depth2Index
                                                                                ? onGnbTabKeyOut
                                                                                : null
                                                                        }
                                                                    >
                                                                        <NavLink
                                                                            to={{
                                                                                pathname:
                                                                                    depth2.progrUrl,
                                                                            }}
                                                                        >
                                                                            <span>
                                                                                {
                                                                                    depth2.menuNm
                                                                                }
                                                                            </span>
                                                                        </NavLink>
                                                                        {depth2.childCnt !==
                                                                        0 ? (
                                                                            <div className="depth3_wrap">
                                                                                <ul className="depth3">
                                                                                    {menuList.map(
                                                                                        (
                                                                                            depth3,
                                                                                            l,
                                                                                            array
                                                                                        ) =>
                                                                                            depth3.upperMenuNo ===
                                                                                            depth2.menuNo ? (
                                                                                                <li
                                                                                                    key={
                                                                                                        l
                                                                                                    }
                                                                                                    className={
                                                                                                        curUrl ===
                                                                                                        depth3.progrUrl
                                                                                                            ? "active"
                                                                                                            : ""
                                                                                                    }
                                                                                                >
                                                                                                    <NavLink
                                                                                                        to={{
                                                                                                            pathname:
                                                                                                                depth3.progrUrl,
                                                                                                        }}
                                                                                                    >
                                                                                                        <span>
                                                                                                            {
                                                                                                                depth3.menuNm
                                                                                                            }
                                                                                                        </span>
                                                                                                    </NavLink>
                                                                                                </li>
                                                                                            ) : null
                                                                                    )}
                                                                                </ul>
                                                                            </div>
                                                                        ) : null}
                                                                    </li>
                                                                ) : null
                                                        )}
                                                    </ul>
                                                </div>
                                            </div>
                                        </div>
                                    ) : null}
                                </li>
                            ))}
                </ul>
            </div>
            <div className="mnb">
                <ul>
                    <li className="login_info">
                        <p>
                            <strong>{loginVO?.name}</strong>
                            <span>{loginVO?.ofcpsNm} 님</span>
                        </p>
                    </li>
                    <li className="gnb_logout">
                        <a href="{() => false}" onClick={onClickLogoutHandler}>
                            <em>로그아웃</em>
                        </a>
                    </li>
                    <li className="gnb_mypage">
                        <Link
                            to={{
                                pathname: `${URL.ROOT + URL.MYPAGE}`,
                            }}
                        >
                            <em>마이페이지</em>
                        </Link>
                    </li>
                    {loginVO?.authorCode?.includes("ROLE_ADMIN") && (
                        <li className="gnb_admin">
                            <a
                                href="{() => false}"
                                onClick={onLocateAdminClick}
                            >
                                <em>관리자</em>
                            </a>
                        </li>
                    )}
                </ul>
            </div>
        </header>
    );
}

function mapStateToProps(state) {
    const session = state.reducer;
    return {
        loginVO: session.loginVO,
    };
}

export default connect(mapStateToProps, null)(Menu);
