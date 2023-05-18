import { useState } from "react";
import { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { connect } from "react-redux";
import DatePickerComponert from "schdl_mng/user/utils/DatePickerComponert";
import SchdlMbrSrchModal from "schdl_mng/user/schdl/SchdlMbrSrchModal";
import moment from "moment";
import URL from "context/url";
import CmmFnc from "context/common.js";

// 코드 정보 조회
const fncVcatnRegCode = async () => {
    return await CmmFnc.fncRequestAxios(
        "POST",
        "/schdl/vcatn/selectCodeList.do"
    ).then((result) => {
        if (result.data.sttus === "success") {
            return result.data.result;
        }
    });
};

/**
 * 휴가 등록
 * @param {*} loginVO  store에 저장된 loginVO 로드
 * @param {*} setModalInfo 모달 정보 변경 setState
 * @returns
 */
function VcatnReg({ loginVO, setModalInfo }) {
    // 페이지 이동 Hook
    const navigate = useNavigate();
    // loginVO의 키값을 변수로 설정
    const { authorCode } = loginVO;
    // 등록 Form state 설정
    const [regState, setRegState] = useState({
        form: {
            bgngYmd: "",
            endYmd: "",
            aplcntId: "",
            aplcntNm: "",
            vcatnSe: "",
            vcatnCn: "",
            rmndrDayCnt: "",
        },
        code: {
            vcatnSeList: [],
        },
    });
    // 등록 Form state 내 key 값을 변수로 설정
    const { form, code } = regState;
    const {
        bgngYmd,
        endYmd,
        aplcntId,
        aplcntNm,
        vcatnSe,
        vcatnCn,
        rmndrDayCnt,
    } = form;
    const { vcatnSeList } = code;

    useEffect(() => {
        // 서버에서 세션정보를 가져오는 함수
        // async - 반환받는 값은 Promise로 반환
        const getCodeList = async () => {
            // awit - 프라미스가 처리(settled)될 때까지 기다림
            const data = await fncVcatnRegCode();
            if (data !== undefined) {
                setRegState((prevState) => {
                    return {
                        ...prevState,
                        form: {
                            ...prevState.form,
                            rmndrDayCnt: data.rmndrDayCnt,
                        },
                        code: {
                            vcatnSeList: data.vcatnSeList,
                        },
                    };
                });
            }
        };
        // 함수 호출
        getCodeList();
    }, []);

    //DatePicker 변경 이벤트
    const onChangeDatePicker = (name, item) => {
        setRegState((prevState) => {
            return {
                ...prevState,
                form: {
                    ...form,
                    [name]: item,
                },
            };
        });
    };

    // Input 값 변경 이벤트
    const onChangeHandler = (e) => {
        const { name, value } = e.target;

        setRegState((prevState) => {
            return {
                ...prevState,
                form: {
                    ...form,
                    [name]: value,
                },
            };
        });
    };

    // 신청자 조회 팝업 호출
    const onClickAplcnt = (e) => {
        setModalInfo((prevState) => {
            return {
                ...prevState,
                showModal: true,
                isLoadingUI: false,
                width: 400,
                height: 600,
                title: "직원 검색",
                content: (
                    <SchdlMbrSrchModal
                        callback={onModalActionCallback}
                        aplcntInfo={{ aplcntId, aplcntNm }}
                    />
                ),
            };
        });
    };

    // modal callback 함수
    const onModalActionCallback = (element) => {
        if (element.action !== "close") {
            setRegState((prevState) => {
                return {
                    ...prevState,
                    form: {
                        ...form,
                        aplcntId: element.data.aplcntId,
                        aplcntNm: element.data.aplcntNm,
                        rmndrDayCnt: element.data.rmndrDayCnt,
                    },
                };
            });
        }

        setModalInfo((prevState) => {
            return {
                ...prevState,
                showModal: false,
                isLoadingUI: false,
                width: 0,
                height: 0,
                title: "",
                content: "",
            };
        });
    };

    // 목록 버튼 클릭 이벤트
    const onClickList = (e) => {
        navigate(`${URL.ROOT + URL.SCHDL}`);
    };

    // 저장 버튼 클릭 이벤트
    const onClickSave = async (e) => {
        if (window.confirm("저장하시겠습니까?")) {
            if (rmndrDayCnt === undefined) {
                alert("올해 생성된 휴가가 없습니다. 관리자에게 문의하십시오");
                return;
            }

            if (Math.round(parseFloat(rmndrDayCnt)) === 0) {
                alert("남은 휴가가 없습니다.");
                return;
            }

            if (bgngYmd === "") {
                alert("시작일자는 필수 입력입니다.");
                return;
            }

            if (endYmd === "") {
                alert("종료일자는 필수 입력입니다.");
                return;
            }

            if (authorCode === "ROLE_ADMIN" && aplcntId === "") {
                alert("신청자 정보는 필수 입력입니다.");
                return;
            }

            if (vcatnSe === "") {
                alert("휴가 구분은 필수 입력입니다.");
                return;
            }

            if (vcatnCn === "") {
                alert("휴가 내용은 필수 입력입니다.");
                return;
            }

            const bgng =
                vcatnSe === "03"
                    ? moment(bgngYmd, "YYYYMMDD").hour(14).toDate()
                    : moment(bgngYmd, "YYYYMMDD").hour(9).toDate();

            // 관리자가 아닐 경우 등록 일정 유효성 확인
            if (authorCode !== "ROLE_ADMIN") {
                const todayDt = moment().toDate();
                if (todayDt.getTime() > bgng.getTime()) {
                    alert("이미 지나간 일정에는 등록할 수 없습니다.");
                    return;
                }
            }

            const end = moment(endYmd, "YYYYMMDD").toDate();
            const elapsedMSec = end.getTime() - bgng.getTime();
            const elapsedDay = elapsedMSec / 1000 / 60 / 60 / 24;
            const dayMapperCounting = vcatnSe === "01" ? 1 : 0.5;
            const dayCnt = (Math.round(elapsedDay) + 1) * dayMapperCounting;

            if (parseFloat(rmndrDayCnt) < dayCnt) {
                alert("현재 휴가 남은 일수보다 지정한 일수가 더 많습니다.");
                return;
            }

            const param = {
                bgngYmd: bgngYmd,
                endYmd: endYmd,
                aplcntId: aplcntId,
                vcatnSe: vcatnSe,
                vcatnCn: vcatnCn,
            };

            await CmmFnc.fncRequestAxios(
                "POST",
                "/schdl/vcatn/insertVcatn.do",
                param
            ).then((result) => {
                if (result.data.sttus === "success") {
                    navigate(`${URL.ROOT + URL.SCHDL}`);
                } else {
                    alert("지정하신 일정에 이미 등록된 내용이 있습니다.");
                    return;
                }
            });
        }
    };

    return (
        <div className="content board_wrap">
            <div className="inner">
                <div className="wrap">
                    <div className="tit_wrap">
                        <h3 className="tit">휴가 등록</h3>
                        <p className="must_text">
                            <em>＊</em>는 필수 입력사항입니다.
                        </p>
                    </div>
                    <div className="board_write intitle">
                        <ul>
                            <li className="must">
                                <strong className="board_label">
                                    시작 일자
                                </strong>
                                <div className="input_wrap">
                                    <DatePickerComponert
                                        id="bgngYmd"
                                        name="bgngYmd"
                                        className="inputText width100p"
                                        date={bgngYmd}
                                        setDate={(value) =>
                                            onChangeDatePicker("bgngYmd", value)
                                        }
                                        dateFormat="YYYYMMDD"
                                        minDate={
                                            authorCode !== "ROLE_ADMIN"
                                                ? moment().format("YYYYMMDD")
                                                : null
                                        }
                                        maxDate={
                                            authorCode !== "ROLE_ADMIN" &&
                                            endYmd !== ""
                                                ? endYmd
                                                : null
                                        }
                                    />
                                </div>
                            </li>
                            <li className="must">
                                <strong className="board_label">
                                    종료 일자
                                </strong>
                                <div className="input_wrap">
                                    <DatePickerComponert
                                        id="endYmd"
                                        name="endYmd"
                                        className="inputText width100p"
                                        date={endYmd}
                                        setDate={(value) =>
                                            onChangeDatePicker("endYmd", value)
                                        }
                                        dateFormat="YYYYMMDD"
                                        minDate={
                                            authorCode !== "ROLE_ADMIN"
                                                ? bgngYmd !== ""
                                                    ? bgngYmd
                                                    : moment().format(
                                                          "YYYYMMDD"
                                                      )
                                                : null
                                        }
                                    />
                                </div>
                            </li>
                            {authorCode === "ROLE_ADMIN" && (
                                <>
                                    <li className="must">
                                        <strong className="board_label">
                                            신청자 정보
                                        </strong>
                                        <div className="input_wrap">
                                            <button
                                                type="button"
                                                className="btn_m mr2"
                                                onClick={onClickAplcnt}
                                            >
                                                추가
                                            </button>
                                            <input
                                                type="text"
                                                className="inputText width200"
                                                id="aplcntNm"
                                                name="aplcntNm"
                                                title="신청자 명"
                                                value={aplcntNm}
                                                readOnly={true}
                                            />
                                        </div>
                                    </li>
                                    <li>
                                        <strong className="board_label alignL">
                                            휴가 남은 일수
                                        </strong>
                                        <div className="input_wrap">
                                            <input
                                                type="text"
                                                className="inputText width200"
                                                id="remndrDayCnt"
                                                name="remndrDayCnt"
                                                title="휴가 남은 일수 입력"
                                                value={rmndrDayCnt}
                                                readOnly={true}
                                            />
                                        </div>
                                    </li>
                                </>
                            )}
                            {authorCode === "ROLE_USER" && (
                                <li>
                                    <strong className="board_label alignL">
                                        휴가 남은 일수
                                    </strong>
                                    <div className="input_wrap">
                                        <input
                                            type="text"
                                            className="inputText width200"
                                            id="remndrDayCnt"
                                            name="remndrDayCnt"
                                            title="휴가 남은 일수 입력"
                                            value={rmndrDayCnt}
                                            readOnly={true}
                                        />
                                    </div>
                                </li>
                            )}
                            <li className="must">
                                <strong className="board_label alignL">
                                    휴가 구분
                                </strong>
                                <div className="input_wrap">
                                    <select
                                        className="width100 selectText"
                                        id="vcatnSe"
                                        name="vcatnSe"
                                        title="휴가 구분"
                                        value={vcatnSe}
                                        onChange={onChangeHandler}
                                    >
                                        <option value="">선택</option>
                                        {vcatnSeList?.map((item, index) => {
                                            return (
                                                <option
                                                    key={index}
                                                    value={item.code}
                                                >
                                                    {item.codeNm}
                                                </option>
                                            );
                                        })}
                                    </select>
                                </div>
                            </li>
                            <li className="must">
                                <strong className="board_label alignL">
                                    휴가 내용
                                </strong>
                                <div className="input_wrap">
                                    <div className="text_show">
                                        <textarea
                                            id="vcatnCn"
                                            name="vcatnCn"
                                            className="textArea height100 width100p"
                                            cols="45"
                                            rows="8"
                                            title="휴가 내용"
                                            maxLength="2000"
                                            value={vcatnCn}
                                            onChange={onChangeHandler}
                                        />
                                    </div>
                                </div>
                            </li>
                        </ul>
                    </div>
                </div>
                <div className="btn_group btn_same">
                    <button
                        type="button"
                        className="btn_round bg_basic btn_l"
                        title="목록"
                        id="btnList"
                        onClick={onClickList}
                    >
                        목록
                    </button>
                    <button
                        type="button"
                        className="btn_round bg_navy btn_l"
                        title="저장"
                        id="btnSave"
                        onClick={onClickSave}
                    >
                        저장
                    </button>
                </div>
            </div>
        </div>
    );
}

function mapStateToProps(state) {
    const session = state.reducer;
    return {
        loginVO: session.loginVO,
    };
}

export default connect(mapStateToProps, null)(VcatnReg);
