import axios from "axios";
import { SERVER_URL } from "context/config";

axios.interceptors.response.use(
    function (response) {
        // 응답 데이터를 가공
        // ...
        return response;
    },
    function (error) {
        if (axios.isAxiosError(error)) {
            if (error.response) {
                // 오류 응답을 처리
                window.location.replace(`/error/${error.response.status}`);
            }
        }
        return Promise.reject(error);
    }
);

const CmmFnc = {
    fncRequestAxios: (method, url, data = {}) => {
        return axios({
            method: method,
            url: SERVER_URL + url,
            data: data,
            withCredentials: true,
            origin: SERVER_URL,
            headers: {
                "Content-Type": "application/json;charset=UTF-8",
            },
        });
    }, // 서버 비동기 통신 함수
};

export default CmmFnc;
