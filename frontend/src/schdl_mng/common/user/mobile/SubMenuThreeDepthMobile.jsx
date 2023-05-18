import { useRef } from "react";
import { NavLink, useOutletContext } from "react-router-dom";

/**
 * 서브메뉴 모바일 표시 3 Depth Component
 * @param {*} depth2   선택된 메뉴의 부모 메뉴
 * @returns
 */
function SubMenuThreeDepthMobile({ depth2 }) {
    // 부모 Component에서 Outlet으로 전달한 Context에서 세션 정보를 변수로 설정
    const { sessionInfo } = useOutletContext();
    // 세션 정보 내 Key 값을 변수로 지정
    const { menuList, curUrl } = sessionInfo;
    // Html Tag Ul 참고 Hook
    const ulSubMenu = useRef(null);

    // 반응형 처리 함수
    const getUlSubMenuWidth = () => {
        if (ulSubMenu.current !== null) {
            let total = 0;
            Object.values(ulSubMenu.current.childNodes).forEach((item) => {
                total += Number(item.offsetWidth + 30);
            });
            return total;
        } else {
            return 0;
        }
    };

    return (
        <div
            className={`gnb_3depth_mobile overX ${
                menuList.filter((item) => item.upperMenuNo === depth2.menuNo)
                    .length > 0 && "have"
            }`}
        >
            <ul
                className="depth3"
                ref={ulSubMenu}
                style={{ width: getUlSubMenuWidth() }}
            >
                {menuList.map((depth3, l) =>
                    depth3.upperMenuNo === depth2.menuNo ? (
                        <li
                            key={l}
                            className={
                                curUrl === depth3.progrUrl ? "active" : ""
                            }
                        >
                            <NavLink
                                to={{
                                    pathname: depth3.progrUrl,
                                }}
                            >
                                <span>{depth3.menuNm}</span>
                            </NavLink>
                        </li>
                    ) : null
                )}
            </ul>
        </div>
    );
}

export default SubMenuThreeDepthMobile;
