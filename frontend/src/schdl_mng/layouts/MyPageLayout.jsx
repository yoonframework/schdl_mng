import { Fragment } from "react";
import { Outlet, useOutletContext } from "react-router-dom";
import { HelmetProvider, Helmet } from "react-helmet-async";

import Header from "schdl_mng/common/user/Header";
import Footer from "schdl_mng/common/user/Footer";
import Menu from "schdl_mng/common/user/Menu";
import SubMenu from "schdl_mng/common/user/SubMenu";
import MyPageSubMenu from "schdl_mng/common/user/MyPageSubMenu";
import MyPageSubMenuMobile from "schdl_mng/common/user/mobile/MyPageSubMenuMobile";

/**
 * 마이페이지 레이아웃
 * 헤더 및 메뉴, 서브메뉴 그리고 푸터 위치 지정
 * 컨텐츠는 Outlet으로 관리
 * @returns
 */
function MyPageLayout() {
    const { sessionInfo, windowSize } = useOutletContext();
    const { thisMenuInfo } = sessionInfo;
    const { width } = windowSize;

    return (
        <Fragment>
            {/* CSS Load */}
            <HelmetProvider>
                <Helmet>
                    <link
                        rel="canonical"
                        type="text/css"
                        href={require("css/user/style.css").default}
                    />
                    <link
                        rel="canonical"
                        type="text/css"
                        href={require("css/user/board.css").default}
                    />
                    <link
                        rel="canonical"
                        type="text/css"
                        href={require("css/user/common.css").default}
                    />
                    <link
                        rel="canonical"
                        type="text/css"
                        href={require("css/user/responsive.css").default}
                    />
                </Helmet>
            </HelmetProvider>
            {/* CSS Load */}
            <Header />
            <Menu />
            <section className="section mpage">
                {width <= 1200 && (
                    <>
                        <MyPageSubMenu />
                        <MyPageSubMenuMobile />
                    </>
                )}
                <SubMenu />
                <div className="content mypage_wrap">
                    <div className="inner">
                        <div className="mypage">
                            {width > 1200 && <MyPageSubMenu />}
                            <div className="my_content_wrap" id="skip_content">
                                <h2>{thisMenuInfo.menuNm}</h2>
                                <Outlet />
                            </div>
                        </div>
                    </div>
                </div>
            </section>
            <Footer />
        </Fragment>
    );
}

export default MyPageLayout;
