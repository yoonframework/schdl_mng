import { useState } from "react";
import DatePickerComponert from "schdl_mng/user/utils/DatePickerComponert";

/**
 * 자격증 등록 팝업
 * @param {*} callback 모달 콜백 함수
 * @returns
 */
function MyPageCrqfcSaveModal({ callback }) {
    // 자격증 정보 state
    const [crqfcInfo, setCrqfcInfo] = useState({
        crqfcId: "",
        crqfcNm: "",
        crqfcIssuinst: "",
        crqfcUpdtYmd: "",
        crqfcIssuYmd: "",
        rm: "",
    });

    // 자격증 정보 state 내 key 값을 변수로 설정
    const { crqfcNm, crqfcIssuinst, crqfcUpdtYmd, crqfcIssuYmd } = crqfcInfo;

    // input ChangeHandler
    const onChangeHandler = (e) => {
        const { name, value } = e.target;
        setCrqfcInfo((prevState) => ({
            ...prevState,
            [name]: value,
        }));
    };

    // DatePicker 값 변경 이벤트
    const onChangeDatePicker = (name, value) => {
        setCrqfcInfo((prevState) => {
            return {
                ...prevState,
                [name]: value,
            };
        });
    };

    // 저장 버튼 이벤트
    const onPrgrsHandler = (e) => {
        if (crqfcNm === "") {
            alert("자격증 명을 입력하세요");
            return;
        }
        if (crqfcIssuinst === "") {
            alert("자격증 발급기관을 입력하세요");
            return;
        }

        callback({
            action: "crqfcInfo",
            data: crqfcInfo,
        });
    };

    return (
        <>
            <div className="modal_content">
                <div className="board_write intitle">
                    <table
                        className="table_list"
                        summary="마이페이지에서 자격증을 입력합니다."
                    >
                        <caption>직원 자격증 입력</caption>
                        <colgroup>
                            <col className="width180" />
                            <col />
                        </colgroup>
                        <thead></thead>
                        <tbody>
                            <tr>
                                <th className="must">자격증 명</th>
                                <td>
                                    <input
                                        type="text"
                                        name="crqfcNm"
                                        value={crqfcNm}
                                        onChange={onChangeHandler}
                                        maxLength="20"
                                        className="inputText width100p"
                                        title="자격증 명"
                                    />
                                </td>
                            </tr>
                            <tr>
                                <th className="must">자격증 발급기관</th>
                                <td>
                                    <input
                                        type="text"
                                        name="crqfcIssuinst"
                                        value={crqfcIssuinst}
                                        onChange={onChangeHandler}
                                        maxLength="20"
                                        className="inputText width100p"
                                        title="자격증 명"
                                    />
                                </td>
                            </tr>
                            <tr>
                                <th>자격증 발급일자</th>
                                <td className="alignL">
                                    <DatePickerComponert
                                        id="crqfcIssuYmd"
                                        name="crqfcIssuYmd"
                                        className="inputText width100p"
                                        date={crqfcIssuYmd}
                                        setDate={(value) =>
                                            onChangeDatePicker(
                                                "crqfcIssuYmd",
                                                value
                                            )
                                        }
                                        dateFormat="YYYYMMDD"
                                    />
                                </td>
                            </tr>
                            <tr>
                                <th>자격증 갱신일자</th>
                                <td className="alignL">
                                    <DatePickerComponert
                                        id="crqfcUpdtYmd"
                                        name="crqfcUpdtYmd"
                                        className="inputText width100p"
                                        date={crqfcUpdtYmd}
                                        setDate={(value) =>
                                            onChangeDatePicker(
                                                "crqfcUpdtYmd",
                                                value
                                            )
                                        }
                                        dateFormat="YYYYMMDD"
                                    />
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

export default MyPageCrqfcSaveModal;
