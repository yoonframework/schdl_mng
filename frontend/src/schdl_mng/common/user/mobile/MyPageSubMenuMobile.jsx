import { useRef } from "react";
import { NavLink, useOutletContext } from "react-router-dom";

function MyPageSubMenuMobile() {
    const { sessionInfo, windowSize } = useOutletContext();
    const { menuList, menuNaviList } = sessionInfo;
    const { width } = windowSize;
    const ulMyPage = useRef(null);

    if (menuList.length > 0) {
        const getMyPageUlWidth = () => {
            if (ulMyPage.current !== null) {
                let total = 0;
                Object.values(ulMyPage.current.childNodes).forEach((item) => {
                    total += Number(item.offsetWidth + 5);
                });
                return total;
            } else {
                return "100%";
            }
        };

        const depth1 = menuList
            ?.filter(
                (menu, i, array) =>
                    menu.upperMenuNo ===
                    array
                        ?.filter(
                            (upper) =>
                                upper.upperMenuNo === "ROOT" &&
                                menu.menuGb === "M"
                        )
                        .pop()?.menuNo
            )
            ?.pop();

        return (
            <div
                className={`mypage_submenu overX ${
                    depth1.childCnt !== 0 ? "have" : ""
                }`}
            >
                <NavLink
                    to={{
                        pathname: depth1.progrUrl,
                    }}
                >
                    {depth1.menuNm}
                </NavLink>
                {depth1.childCnt !== 0 ? (
                    <ul
                        className="mysub"
                        ref={ulMyPage}
                        style={{
                            width: width < 701 ? getMyPageUlWidth() : "0",
                        }}
                    >
                        {menuList.map((depth2, j) =>
                            depth2.upperMenuNo === depth1.menuNo ? (
                                <li
                                    key={j}
                                    className={
                                        menuNaviList?.filter(
                                            (navi) =>
                                                navi.menuNo === depth2.menuNo
                                        ).length > 0
                                            ? "active"
                                            : ""
                                    }
                                >
                                    <NavLink
                                        to={{
                                            pathname: depth2.progrUrl,
                                        }}
                                    >
                                        {depth2.menuNm}
                                    </NavLink>
                                    {depth2.childCnt !== 0 ? (
                                        <ul className="depth3">
                                            {menuList.map((depth3, l) =>
                                                depth3.upperMenuNo ===
                                                depth2.menuNo ? (
                                                    <li
                                                        key={l}
                                                        className={
                                                            menuNaviList?.filter(
                                                                (navi) =>
                                                                    navi.menuNo ===
                                                                    depth3.menuNo
                                                            ).length > 0
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
                                                            {depth3.menuNm}
                                                        </NavLink>
                                                    </li>
                                                ) : null
                                            )}
                                        </ul>
                                    ) : null}
                                </li>
                            ) : null
                        )}
                    </ul>
                ) : null}
            </div>
        );
    } else {
        return <></>;
    }
}

export default MyPageSubMenuMobile;
