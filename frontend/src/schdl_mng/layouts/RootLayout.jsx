import React, { Fragment } from "react";
import { Outlet } from "react-router-dom";

/**
 * 최상단 레이아웃(App.js에서 생성한 state 값을 하위 Component에게 Context로 전달)
 * 세션정보나 창 정보는 리덕스 스토어로 관리하는 것을 추천
 * 세션정보의 경우 메뉴정보를 담고 있기 때문에 세션 스토리지에 담는 것은 무리
 * 토큰 방식이 가장 흔히 사용되며(세션값으로 주고 받을 경우 보안 상 위험) 클라이언트에서 로그인한 계정 정보가 있을경우
 * 토큰 요청 후 그 토큰으로 세션 정보를 가져옴
 * @param {*} sessionInfo     세션정보 state
 * @param {*} windowSize      창 크기 state
 * @returns
 */
function RootLayout({ sessionInfo, windowSize }) {
    return (
        <Fragment>
            <Outlet context={{ sessionInfo, windowSize }} />
        </Fragment>
    );
}

export default RootLayout;
