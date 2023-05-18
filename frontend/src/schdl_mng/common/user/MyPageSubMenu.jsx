import { useRef } from "react";
import { NavLink, useOutletContext } from "react-router-dom";

/**
 * 마이페이지 서브 메뉴 Component
 * @returns
 */
function MyPageSubMenu() {
    // 부모 Component에서 Outlet으로 전달한 Context에서 세션 정보를 변수로 설정
    const { sessionInfo, windowSize } = useOutletContext();
    // 세션 정보 내 Key 값을 변수로 지정
    const { menuList, menuNaviList } = sessionInfo;
    // 창크기 관리 state 내 Key 값을 변수로 지정
    const { width } = windowSize;
    // Html tag Ul 참고 Hook
    const ulMyPage = useRef(null);

    // 반응형 처리 함수
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

    return (
        <div className="mypage_lnb" id="myumenu">
            <h2>mypage</h2>
            <ul
                ref={ulMyPage}
                style={{
                    width: width < 701 ? getMyPageUlWidth() : "100%",
                }}
            >
                {menuList.length > 0 &&
                    menuList
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
                        ?.map((depth1) => (
                            <li
                                key={depth1.menuNo}
                                className={`my_deb1 ${
                                    depth1.childCnt !== 0 ? "have" : ""
                                } ${
                                    menuNaviList?.filter(
                                        (navi) => navi.menuNo === depth1.menuNo
                                    ).length > 0
                                        ? "active"
                                        : ""
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
                                    <ul className="mysub">
                                        {menuList.map((depth2, j) =>
                                            depth2.upperMenuNo ===
                                            depth1.menuNo ? (
                                                <li
                                                    key={j}
                                                    className={
                                                        menuNaviList?.filter(
                                                            (navi) =>
                                                                navi.menuNo ===
                                                                depth2.menuNo
                                                        ).length > 0
                                                            ? "active"
                                                            : ""
                                                    }
                                                >
                                                    <NavLink
                                                        to={{
                                                            pathname:
                                                                depth2.progrUrl,
                                                        }}
                                                    >
                                                        {depth2.menuNm}
                                                    </NavLink>
                                                    {depth2.childCnt !== 0 ? (
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
                                                                                menuNaviList?.filter(
                                                                                    (
                                                                                        navi
                                                                                    ) =>
                                                                                        navi.menuNo ===
                                                                                        depth3.menuNo
                                                                                )
                                                                                    .length >
                                                                                0
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
                                                                                {
                                                                                    depth3.menuNm
                                                                                }
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
                            </li>
                        ))}
            </ul>
        </div>
    );
}

export default MyPageSubMenu;
