import { useState } from "react";
import CmmFnc from "context/common.js";

//아이디 사용가능 여부 확인(중복체크)
const fncDpcnIdntyUserId = async (checkId) => {
    return await CmmFnc.fncRequestAxios(
        "POST",
        "/uss/umt/selectMbrIdCnt.do",
        checkId
    ).then((result) => {
        if (result.data.sttus === "success") {
            return result.data.result;
        }
    });
};

/**
 * 직원 등록 - 중복 확인 모달 Component
 * @param {*} param0
 * @returns
 */
function AdminMbrSrchDpcnModal({ callback }) {
    // 중복 체크 관리 state
    const [srchDpcnResult, setSrchDpcnResult] = useState({
        checkId: "",
        isIdnty: false,
    });

    // Input 값 변경 이벤트
    const onChangeHandler = (e) => {
        const { name, value } = e.target;
        setSrchDpcnResult((prevState) => ({
            ...prevState,
            [name]: value,
            isIdnty: false,
        }));
    };

    // 사용 가능 여부 조회 버튼 클릭 이벤트
    const onDpcnIdntyUserIdHandler = async (e) => {
        if (srchDpcnResult.checkId !== "") {
            const data = await fncDpcnIdntyUserId(srchDpcnResult.checkId);
            if (parseInt(data.usedCnt) === 0) {
                alert("사용 가능한 아이디입니다.");

                setSrchDpcnResult((prevState) => ({
                    ...prevState,
                    isIdnty: true,
                }));
            } else {
                alert("이미 사용 중인 아이디입니다.");
            }
        } else {
            alert("사용하실 아이디를 입력하세요.");
        }
    };

    // 확인 버튼 클릭 이벤트(콜백함수 호출)
    const onPrgrsHandler = (e) => {
        if (
            srchDpcnResult.checkId === "" &&
            srchDpcnResult.checkId.length === 0
        ) {
            alert("사용할 아이디를 입력하세요");
        } else if (!srchDpcnResult.isIdnty) {
            alert("중복 확인 조회를 진행하세요");
        } else if (srchDpcnResult.isIdnty) {
            callback({ action: "mberId", data: srchDpcnResult.checkId });
        }
    };
    return (
        <>
            <div className="modal_content">
                <div className="board_write intitle">
                    <table
                        className="table_write"
                        summary="아이디 중복확인의 내역에 대한 목록을 출력합니다."
                    >
                        <caption>아이디 중복확인 등록</caption>
                        <colgroup>
                            <col className="width180" />
                            <col />
                        </colgroup>
                        <thead></thead>
                        <tbody>
                            <tr>
                                <th>사용할 아이디</th>
                                <td>
                                    <input
                                        type="text"
                                        name="checkId"
                                        value={srchDpcnResult.userId}
                                        onChange={onChangeHandler}
                                        maxLength="20"
                                        className="calc58 fl"
                                        title="아이디입력"
                                    />
                                    <button
                                        type="button"
                                        className="btn_m fl ml2"
                                        onClick={onDpcnIdntyUserIdHandler}
                                        title="조회 버튼"
                                    >
                                        조회
                                    </button>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
            <div className="modal_bottom">
                <div className="fr">
                    <button
                        type="button"
                        className="btn_s bg_navy"
                        title="확인"
                        onClick={onPrgrsHandler}
                    >
                        확인
                    </button>
                    <button
                        type="button"
                        className="btn_s close bg_basic"
                        title="닫기"
                        onClick={function (elem, e) {
                            e.preventDefault();
                            callback(elem);
                        }.bind(this, { action: "close" })}
                    >
                        닫기
                    </button>
                </div>
            </div>
        </>
    );
}

export default AdminMbrSrchDpcnModal;
