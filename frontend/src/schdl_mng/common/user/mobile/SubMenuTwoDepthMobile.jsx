import { useRef } from "react";
import { NavLink, useOutletContext } from "react-router-dom";

/**
 * 서브메뉴 모바일 표시 2 Depth Component
 * @param {*} depth1  선택된 메뉴의 최상단 메뉴
 * @returns
 */
function SubMenuTwoDepthMobile({ depth1 }) {
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
                total += Number(item.offsetWidth + 10);
            });
            return total;
        } else {
            return 0;
        }
    };

    return (
        <div className="gnb_2depth_mobile overX">
            <ul
                className="depth2"
                ref={ulSubMenu}
                style={{ width: getUlSubMenuWidth() }}
            >
                {menuList.map((depth2, depth2Index) =>
                    depth2.upperMenuNo === depth1.menuNo ? (
                        <li
                            key={depth2Index}
                            className={
                                curUrl === depth2.progrUrl ? "active" : ""
                            }
                        >
                            <NavLink
                                to={{
                                    pathname: depth2.progrUrl,
                                }}
                            >
                                <span>{depth2.menuNm}</span>
                            </NavLink>
                        </li>
                    ) : null
                )}
            </ul>
        </div>
    );
}

export default SubMenuTwoDepthMobile;
