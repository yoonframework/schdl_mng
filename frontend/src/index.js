import "core-js/stable";
import "regenerator-runtime/runtime";
import "react-app-polyfill/ie11";
import "react-app-polyfill/stable";
import React from "react";
import ReactDOM from "react-dom";
import { BrowserRouter } from "react-router-dom";
import { Provider } from "react-redux";
import { PersistGate } from "redux-persist/integration/react";
import reportWebVitals from "./reportWebVitals";
import App from "./App";
import { store, persistor } from "./store";

ReactDOM.render(
    // StrictMode는 애플리케이션 내의 잠재적인 문제를 알아내기 위한 도구
    <React.StrictMode>
        {/* redux 라이브러리 리액트 앱에 store 를 손쉽게 연동 */}
        <Provider store={store}>
            {/* redux 라이브러리 리액트 앱에 store 를 손쉽게 연동 */}
            {/* react로 개발하는 경우 App 컴포넌트를 PersistGate로 매핑합니다.
            persist store가 redux에 저장될 때까지 앱 UI 렌더링이 지연됩니다. */}
            <PersistGate loading={null} persistor={persistor}>
                {/* BrowserRouter는 CSR(Client Side Rendering) 시 사용, history 값은 window.history를 따라감 */}
                {/* StaticRouter는 SSR(Server Side Rendering) 시 사용, 전달받은 location prop을 바탕으로 자체적으로 history를 생성 */}
                <BrowserRouter>
                    <App />
                </BrowserRouter>
            </PersistGate>
        </Provider>
    </React.StrictMode>,
    document.getElementById("root")
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
