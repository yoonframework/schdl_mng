import React, { Fragment } from "react";
import { useNavigate, useParams } from "react-router-dom";
import URL from "context/url";
import { Helmet, HelmetProvider } from "react-helmet-async";

/**
 * 에러 페이지 Component
 * @returns
 */
function PageError() {
    const navigate = useNavigate();
    const params = useParams();
    const errorCode = params.status;
    let errorMessage = "";
    switch (errorCode) {
        case "400":
            errorMessage = "Bad Request (잘못된 요청)";
            break;
        case "403":
            errorMessage = "Forbidden (거부됨)";
            break;
        case "404":
            errorMessage = "Not Found (찾을 수 없음)";
            break;
        case "500":
            errorMessage = "Internal Server Error (내부 서버 에러)";
            break;
        case "502":
            errorMessage = "Bad Gateway (불량 게이트웨이)";
            break;
        case "503":
            errorMessage = "Service Unavailable (일시적으로 이용할 수 없음)";
            break;
        case "504":
            errorMessage = "Gateway Timeout (게이트웨이 시간 초과)";
            break;
        default:
            errorMessage = "내부서비스 에러";
    }
    const errorStatus = {
        code: params.status,
        message: errorMessage,
    };

    const onClickHandler = () => {
        document.body.classList.remove("error");
        if (errorStatus.code === "404") {
            navigate(-1);
        } else {
            navigate(URL.ROOT);
        }
    };

    return (
        <Fragment>
            {/* CSS Load */}
            <HelmetProvider>
                <Helmet>
                    <link
                        rel="canonical"
                        type="text/css"
                        href={require("css/error/error.css").default}
                    />
                </Helmet>
            </HelmetProvider>
            <div className="error">
                <div className="error_box">
                    <div className="text_wrap">
                        <div className="title">
                            <strong>{errorStatus.code}</strong>
                            <p>{errorStatus.message}</p>
                        </div>
                        <div className="text">
                            <p>
                                죄송합니다. 오류가 발생했습니다.
                                <br />
                                관리자에 문의해주시기 바랍니다.
                            </p>
                            <button type="button" onClick={onClickHandler}>
                                이전페이지로 가기
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </Fragment>
    );
}

export default PageError;
