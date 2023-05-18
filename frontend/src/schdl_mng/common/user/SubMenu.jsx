import React, { Fragment } from "react";
import { NavLink, useOutletContext } from "react-router-dom";
import { useRef } from "react";

import $ from "jquery";
import URL from "context/url";
import SubMenuMobile from "schdl_mng/common/user/mobile/SubMenuMobile";

/**
 * 서브 메뉴 Component
 * @returns
 */
function SubMenu() {
    // 부모 Component에서 Outlet으로 전달한 Context에서 세션 정보를 변수로 설정
    const { sessionInfo, windowSize } = useOutletContext();
    // 세션 정보 내 Key 값을 변수로 지정
    const { menuNaviList, menuDepthList, thisMenuInfo, curUrl } = sessionInfo;
    // 창크기 관리 state 내 Key 값을 변수로 지정
    const { width } = windowSize;
    // Html tag Ul 참고 Hook
    const ulLocation = useRef(null);

    // 현재 페이지 클릭 시 이벤트
    const onLocationNowClick = (e) => {
        e.preventDefault();
        if ($(e.target).next(".location").css("display") === "none") {
            $(".location").slideUp();
            $(".btn_location .now").removeClass("on");
            $(e.target).toggleClass("on");
            $(e.target).next(".location").slideToggle();
        } else {
            $(".location").slideUp();
            $(e.target).removeClass("on");
        }
    };

    // 마지막 항목에서 On Blur 이벤트 처리
    const onLocationLastItemOut = () => {
        $(".location").slideUp();
        $(".now").removeClass("on");
    };

    // 반응형 처리
    const getLocationWidth = () => {
        if (ulLocation.current !== null) {
            let total = 0;
            Object.values(ulLocation.current.childNodes).forEach((item) => {
                total += Number(item.offsetWidth + 10);
            });
            return total;
        } else {
            return "100%";
        }
    };

    return (
        <div className="sub_title">
            <div className="breadcrumb">
                <ul>
                    <li className="home">
                        <NavLink
                            to={{
                                pathname: URL.ROOT + URL.SCHDL,
                            }}
                        >
                            home
                        </NavLink>
                    </li>
                    <li className="next">
                        <img
                            src={require("images/user/arrow_right.svg").default}
                            alt="다음"
                        />
                    </li>
                    {menuNaviList.map((menuNavi, i) =>
                        menuNaviList.length - 1 > i ? (
                            <Fragment key={`${menuNavi.menuNo}`}>
                                <li>
                                    <NavLink
                                        to={{
                                            pathname: menuNavi.progrUrl,
                                        }}
                                    >
                                        {menuNavi.menuNm}
                                    </NavLink>
                                </li>
                                <li className="next">
                                    <img
                                        src={
                                            require("images/user/arrow_right.svg")
                                                .default
                                        }
                                        alt="다음"
                                    />
                                </li>
                            </Fragment>
                        ) : menuNaviList.length - 1 === i ? (
                            <li
                                key={`${menuNavi.menuNo}`}
                                className="btn_location"
                                tabIndex="0"
                            >
                                <a
                                    className="now"
                                    href="{() => false}"
                                    onClick={onLocationNowClick}
                                >
                                    <span className="blind">현재페이지</span>
                                    {menuNavi.menuNm}
                                </a>
                                <ul
                                    ref={ulLocation}
                                    className="location"
                                    style={{
                                        width:
                                            width < 701
                                                ? getLocationWidth()
                                                : "100%",
                                    }}
                                >
                                    {menuDepthList.map(
                                        (
                                            menuDepth,
                                            menuDepthIndex,
                                            allDepthList
                                        ) => {
                                            return (
                                                //class active 처리
                                                <li
                                                    key={`${menuDepth.menuNo}`}
                                                    className={
                                                        curUrl ===
                                                        menuDepth.progrUrl
                                                            ? "active"
                                                            : ""
                                                    }
                                                    onBlur={
                                                        allDepthList.length -
                                                            1 ===
                                                        menuDepthIndex
                                                            ? onLocationLastItemOut
                                                            : null
                                                    }
                                                >
                                                    {menuDepth.progrUrl ===
                                                    "dir" ? (
                                                        <a
                                                            style={{
                                                                color: "red",
                                                            }}
                                                            href="{() => false}"
                                                            title={
                                                                menuDepth.menuNm
                                                            }
                                                            onClick={(e) =>
                                                                e.preventDefault()
                                                            }
                                                        >
                                                            {menuDepth.menuNm}
                                                        </a>
                                                    ) : (
                                                        <NavLink
                                                            to={{
                                                                pathname:
                                                                    menuDepth.progrUrl,
                                                            }}
                                                        >
                                                            {menuDepth.menuNm}
                                                        </NavLink>
                                                    )}
                                                </li>
                                            );
                                        }
                                    )}
                                </ul>
                            </li>
                        ) : null
                    )}
                </ul>
            </div>
            <SubMenuMobile />
            <h2>{thisMenuInfo.menuNm}</h2>
        </div>
    );
}

export default SubMenu;
