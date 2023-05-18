import { Fragment, useEffect } from "react";
import { Helmet, HelmetProvider } from "react-helmet-async";

import Menu from "schdl_mng/common/admin/Menu";
import { Outlet } from "react-router-dom";
import { connect } from "react-redux";
import URL from "context/url";

/**
 * 관리자 레이아웃
 * @param {*} loginVO    store에서 로그인 정보를 가져옴
 * @returns
 */
function AdminLayout({ loginVO }) {
    // 로그인 정보를 확인하여 로그인한 사용자가 관리자가 아닐경우 메인페이지(현재 일정관리)로 이동
    useEffect(() => {
        if (loginVO !== undefined) {
            if (
                typeof loginVO?.authorCode !== "undefined" &&
                loginVO?.authorCode !== "ROLE_ADMIN"
            ) {
                alert("권한이 없습니다.");
                window.location.href = URL.ROOT + URL.SCHDL;
            }
        }
    }, [loginVO]);

    return (
        <Fragment>
            {/* CSS Load */}
            <HelmetProvider>
                <Helmet>
                    <link
                        rel="canonical"
                        type="text/css"
                        href={require("css/admin/style.css").default}
                    />
                    <link
                        rel="canonical"
                        type="text/css"
                        href={require("css/admin/board.css").default}
                    />
                    <link
                        rel="canonical"
                        type="text/css"
                        href={require("css/admin/admin.css").default}
                    />
                    <script
                        type="text/javascript"
                        src={require("js/admin/ui").default}
                    ></script>
                    <title>일정관리 - 관리자</title>
                </Helmet>
            </HelmetProvider>
            {/* CSS Load */}
            <div className="content_wrap">
                <Menu />
                <div className="content">
                    <div className="control">사이즈컨트롤</div>
                    <Outlet />
                </div>
                <div className="clear" />
            </div>
        </Fragment>
    );
}

function mapStateToProps(state) {
    const session = state.reducer;
    return {
        loginVO: session.loginVO,
    };
}

export default connect(mapStateToProps, null)(AdminLayout);
