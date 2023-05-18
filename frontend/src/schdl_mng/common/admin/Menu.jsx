import React from "react";
import { Link, useOutletContext } from "react-router-dom";
import { connect } from "react-redux";
import { persistor } from "store";

import CmmFnc from "context/common.js";
import URL from "context/url";

// Header Menu
function fncAddMenuListTag(menuList, menuNaviList, curUrl) {
    const tagList = [];
    menuList
        ?.filter(
            (menu, i, array) =>
                menu.menuNo === "210000000" &&
                menu.upperMenuNo ===
                    array?.filter((upper) => upper.upperMenuNo === "ROOT").pop()
                        ?.menuNo
        )
        .forEach((depth1, i) => {
            const tag = (
                <li
                    key={i}
                    className={`menuChk${
                        menuNaviList?.filter(
                            (navi) => navi.menuNo === depth1.menuNo
                        ).length > 0
                            ? " active"
                            : ""
                    }`}
                >
                    <Link
                        to={{
                            pathname: depth1.progrUrl,
                        }}
                    >
                        {depth1.menuNm}
                    </Link>

                    {depth1.childCnt !== 0 && (
                        <ul className="subMenu">
                            {menuList?.map(
                                (depth2) =>
                                    depth2.upperMenuNo === depth1.menuNo && (
                                        <li
                                            key={`depth2_${depth2.menuNo}`}
                                            className={
                                                curUrl === depth2.progrUrl
                                                    ? "active"
                                                    : ""
                                            }
                                        >
                                            <Link
                                                to={{
                                                    pathname: depth2.progrUrl,
                                                }}
                                            >
                                                {depth2.menuNm}
                                            </Link>
                                            {depth2.childCnt !== 0 && (
                                                <ul className="depth3">
                                                    {menuList?.map(
                                                        (depth3) =>
                                                            depth3.upperMenuNo ===
                                                                depth2.menuNo && (
                                                                <li
                                                                    key={`depth3_${depth3.menuNo}`}
                                                                    className={
                                                                        curUrl ===
                                                                        depth3.progrUrl
                                                                            ? "active"
                                                                            : ""
                                                                    }
                                                                >
                                                                    <Link
                                                                        to={{
                                                                            pathname:
                                                                                depth3.progrUrl,
                                                                        }}
                                                                    >
                                                                        {
                                                                            depth3.menuNm
                                                                        }
                                                                    </Link>
                                                                </li>
                                                            )
                                                    )}
                                                </ul>
                                            )}
                                        </li>
                                    )
                            )}
                        </ul>
                    )}
                </li>
            );
            tagList.push(tag);
        });
    return tagList;
}

//Left Menu
function addSubMenuList(menuList, menuNaviList, curUrl) {
    const subTagList = [];
    menuList
        ?.filter((menu) => menu.upperMenuNo === menuNaviList[0].menuNo)
        .forEach((depth2, i) => {
            const subTag = (
                <li
                    key={`left_${i}`}
                    className={curUrl === depth2.progrUrl ? "active" : ""}
                >
                    <Link
                        to={{
                            pathname: depth2.progrUrl,
                        }}
                    >
                        <p>{depth2.menuNm}</p>
                    </Link>
                    {depth2.childCnt > 0 && (
                        <ul className="depth3">
                            {menuList?.map(
                                (depth3) =>
                                    depth3.upperMenuNo === depth2.menuNo && (
                                        <li
                                            key={`left_depth3_${depth3.menuNo}`}
                                            className={
                                                curUrl === depth3.progrUrl
                                                    ? "active"
                                                    : ""
                                            }
                                        >
                                            <Link
                                                to={{
                                                    pathname: depth3.progrUrl,
                                                }}
                                            >
                                                <p>{depth3.menuNm}</p>
                                            </Link>
                                        </li>
                                    )
                            )}
                        </ul>
                    )}
                </li>
            );
            subTagList.push(subTag);
        });
    return subTagList;
}

/**
 * 메뉴 Component
 * @param {*} loginVO   store에 저장된 로그인 정보를 가져옴
 * @returns
 */
function Menu({ loginVO }) {
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

    return (
        <>
            <div className="topBar overX">
                <ul className="gnb">
                    {fncAddMenuListTag(menuList, menuNaviList, curUrl)}
                </ul>
                <ul className="member">
                    <li>
                        <span>
                            <b>{loginVO?.name}</b>
                        </span>
                        님 환영합니다.
                    </li>
                    <li>
                        <a href={URL.ROOT + URL.SCHDL}>
                            <em>home</em>
                        </a>
                    </li>
                    <li>
                        <a href="{() => false}" onClick={onClickLogoutHandler}>
                            <em>로그아웃</em>
                        </a>
                    </li>
                </ul>
            </div>
            <div className="left_menu">
                <h1 className="logo">
                    <Link
                        to={{
                            pathname: `${URL.ROOT + URL.ADMIN}`,
                        }}
                    >
                        로고
                    </Link>
                </h1>
                <div className="submenu_wrap">
                    <h2>
                        {typeof menuNaviList !== "undefined" &&
                            menuNaviList[0]?.menuNm}
                    </h2>
                    <ul className="sub_menu">
                        {addSubMenuList(menuList, menuNaviList, curUrl)}
                    </ul>
                </div>

                <p className="footer"></p>
            </div>
        </>
    );
}

function mapStateToProps(state) {
    const session = state.reducer;
    return {
        loginVO: session.loginVO,
    };
}

// export default Menu;
export default connect(mapStateToProps, null)(Menu);
