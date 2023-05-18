import React, { Fragment, useEffect, useRef, useState } from "react";
import { Link } from "react-router-dom";
import { HelmetProvider, Helmet } from "react-helmet-async";

import CODE from "context/code";

import CmmFnc from "context/common.js";
import URL from "context/url";
import { connect } from "react-redux";

/**
 *
 * @param {*} onActionLogin - store로 전달한 dispath를 호출하는 함수
 * @returns
 */
function Login({ loginVO, onActionLogin }) {
    const btnLogin = useRef(null);

    // 로그인 할 사용자의 정보를 관리하는 state
    const [userInfo, setUserInfo] = useState({
        id: "default",
        password: "default",
        userSe: "GNR",
    });

    // loginVO 객체가 갱신될 때 실행되는 함수
    useEffect(() => {
        // loginVO의 권한이 있을 시 해당 url로 이동
        if (typeof loginVO?.authorCode !== "undefined") {
            window.location.href = URL.ROOT + URL.SCHDL;
        }
    }, [loginVO]);

    const onKeyPress = (e) => {
        if (e.key === "Enter") {
            btnLogin.current.click();
        }
    };

    // 로그인 버튼 핸들러
    const submitFormHandler = async (e) => {
        CmmFnc.fncRequestAxios("POST", "/uat/uia/actionLoginAPI.do", userInfo)
            .then((result) => {
                return result.data.result;
            })
            .then((data) => {
                if (Number(data.resultCode) === Number(CODE.RCV_SUCCESS)) {
                    onActionLogin(data.resultVO);
                    window.location.href = URL.ROOT + URL.SCHDL;
                } else {
                    alert(data.resultMessage);
                }
            });
    };

    return (
        <Fragment>
            <HelmetProvider>
                <Helmet>
                    <link
                        rel="canonical"
                        href={require("css/user/style.css").default}
                    />
                    <link
                        rel="canonical"
                        href={require("css/user/login.css").default}
                    />
                </Helmet>
            </HelmetProvider>
            <div className="login_wrap">
                <fieldset className="login_form">
                    <div className="login_box">
                        <h1>
                            <Link to={URL.ROOT}>로고</Link>
                        </h1>
                        <p className="alignL loginbox_title">일정관리</p>
                        <div className="space10"></div>
                        <div className="login_input">
                            <ul>
                                <li>
                                    <label htmlFor="id">아이디</label>
                                    <input
                                        type="text"
                                        name="id"
                                        id="id"
                                        maxLength="20"
                                        placeholder="아이디를 입력하세요."
                                        onChange={(e) =>
                                            setUserInfo({
                                                ...userInfo,
                                                id: e.target.value,
                                            })
                                        }
                                        onKeyPress={onKeyPress}
                                    />
                                </li>
                                <li>
                                    <label htmlFor="password">비밀번호</label>
                                    <input
                                        type="password"
                                        name="password"
                                        id="password"
                                        placeholder="비밀번호를 입력하세요."
                                        onChange={(e) =>
                                            setUserInfo({
                                                ...userInfo,
                                                password: e.target.value,
                                            })
                                        }
                                        onKeyPress={onKeyPress}
                                    />
                                </li>
                                <li>
                                    <input
                                        ref={btnLogin}
                                        type="button"
                                        className="btn_login"
                                        value="로그인"
                                        onClick={submitFormHandler}
                                    />
                                </li>
                            </ul>
                        </div>
                    </div>
                </fieldset>
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

function mapDispatchToProps(dispatch) {
    return {
        onActionLogin: (loginVO) => {
            dispatch({ type: "LOGIN_SESSION", loginVO });
        },
    };
}

export default connect(mapStateToProps, mapDispatchToProps)(Login);
