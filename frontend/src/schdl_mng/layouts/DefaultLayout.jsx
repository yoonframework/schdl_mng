import { Fragment } from "react";
import { Outlet } from "react-router-dom";

import Header from "schdl_mng/common/user/Header";
import Footer from "schdl_mng/common/user/Footer";
import Menu from "schdl_mng/common/user/Menu";

/**
 * 기본 레이아웃
 * 헤더 및 메뉴 그리고 푸터 위치 지정
 * 컨텐츠는 Outlet으로 관리
 * @returns
 */
function DefaultLayout() {
    return (
        <Fragment>
            <Header />
            <Menu />
            <Outlet />
            <Footer />
        </Fragment>
    );
}

export default DefaultLayout;
