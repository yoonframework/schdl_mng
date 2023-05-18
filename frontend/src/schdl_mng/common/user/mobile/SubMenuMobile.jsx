import { useOutletContext } from "react-router-dom";
import SubMenuOneDepthMobile from "schdl_mng/common/user/mobile/SubMenuOneDepthMobile";
import SubMenuTwoDepthMobile from "schdl_mng/common/user/mobile/SubMenuTwoDepthMobile";
import SubMenuThreeDepthMobile from "schdl_mng/common/user/mobile/SubMenuThreeDepthMobile";

/**
 * 서브 메뉴 모바일 Layout Component
 * @returns
 */
function SubMenuMobile() {
    // 부모 Component에서 Outlet으로 전달한 Context에서 세션 정보를 변수로 설정
    const { sessionInfo } = useOutletContext();
    // 세션 정보 내 Key 값을 변수로 지정
    const { menuList, menuNaviList, curUrl } = sessionInfo;
    // 선택된 메뉴의 최상단 메뉴
    const menuDepth1 = menuList
        ?.filter(
            (menu, i, array) =>
                menu.upperMenuNo ===
                    array
                        ?.filter(
                            (upper) =>
                                upper.upperMenuNo === "ROOT" &&
                                menu.menuGb === "U"
                        )
                        .pop()?.menuNo &&
                menuNaviList?.filter((navi) => navi.menuNo === menu.menuNo)
                    .length > 0
        )
        ?.pop();

    // 선택된 메뉴의 부모 메뉴
    const menuDepth2 =
        menuDepth1?.menuNo !== undefined
            ? menuList
                  ?.filter(
                      (menu) =>
                          menu.upperMenuNo === menuDepth1.menuNo &&
                          curUrl === menu.progrUrl
                  )
                  ?.pop()
            : undefined;

    return (
        <div className="gnb_mobile">
            <SubMenuOneDepthMobile />
            {menuDepth1?.menuNo !== undefined && (
                <SubMenuTwoDepthMobile depth1={menuDepth1} />
            )}
            {menuDepth1?.menuNo !== undefined &&
                menuDepth2?.menuNo !== undefined && (
                    <SubMenuThreeDepthMobile depth2={menuDepth2} />
                )}
        </div>
    );
}

export default SubMenuMobile;
