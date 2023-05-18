import { Fragment } from "react";
import { Outlet } from "react-router-dom";
import { HelmetProvider, Helmet } from "react-helmet-async";

import Header from "schdl_mng/common/user/Header";
import Footer from "schdl_mng/common/user/Footer";
import Menu from "schdl_mng/common/user/Menu";
import SubMenu from "schdl_mng/common/user/SubMenu";

/**
 * 서브 레이아웃
 * 헤더 및 메뉴, 서브메뉴 그리고 푸터 위치 지정
 * 컨텐츠는 Outlet으로 관리
 * @returns
 */
function DefaultSubLayout() {
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
            <section className="section">
                <SubMenu />
                <Outlet />
            </section>
            <Footer />
        </Fragment>
    );
}

export default DefaultSubLayout;
