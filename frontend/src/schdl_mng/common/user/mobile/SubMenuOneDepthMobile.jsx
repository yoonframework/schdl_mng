import { useRef } from "react";
import { NavLink, useOutletContext } from "react-router-dom";
/**
 * 서브메뉴 모바일 표시 1 Depth Component
 * @returns
 */
function SubMenuOneDepthMobile() {
    // 부모 Component에서 Outlet으로 전달한 Context에서 세션 정보를 변수로 설정
    const { sessionInfo } = useOutletContext();
    // 세션 정보 내 Key 값을 변수로 지정
    const { menuList, menuNaviList } = sessionInfo;
    // Html Tag Ul 참고 Hook
    const ulSubMenu = useRef(null);

    // 반응형 처리 함수
    const getUlSubMenuWidth = () => {
        if (ulSubMenu.current !== null) {
            let total = 0;
            Object.values(ulSubMenu.current.childNodes).forEach((item) => {
                total += Number(item.offsetWidth + 1);
            });
            return total;
        } else {
            return 0;
        }
    };

    return (
        <div className="gnb_1depth_mobile overX">
            <ul ref={ulSubMenu} style={{ width: getUlSubMenuWidth() }}>
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
                        ?.map((depth1) => (
                            <li
                                key={depth1.menuNo}
                                className={`depth1${
                                    menuNaviList?.filter(
                                        (navi) => navi.menuNo === depth1.menuNo
                                    ).length > 0
                                        ? " active"
                                        : ""
                                }`}
                            >
                                <NavLink
                                    to={{
                                        pathname: depth1.progrUrl,
                                    }}
                                >
                                    <span>{depth1.menuNm}</span>
                                </NavLink>
                            </li>
                        ))}
            </ul>
        </div>
    );
}

export default SubMenuOneDepthMobile;
