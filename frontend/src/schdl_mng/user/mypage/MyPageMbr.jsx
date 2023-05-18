import { useEffect, useState } from "react";
import { connect } from "react-redux";

import CmmFnc from "context/common.js";
import InputMask from "react-input-mask";
import DatePickerComponert from "schdl_mng/user/utils/DatePickerComponert";
import MyPageCrqfcSaveModal from "schdl_mng/user/mypage/MyPageCrqfcSaveModal";
import moment from "moment";

// 사용자 정보 조회
const fncMbrInfo = async (uniqId) => {
    return await CmmFnc.fncRequestAxios(
        "POST",
        "/uss/umt/selectMbr.do",
        uniqId
    ).then((result) => {
        if (result.data.sttus === "success") {
            return result.data.result;
        }
    });
};

/**
 * 마이페이지 - 회원정보수정
 * @param {*} loginVO      store에 담긴 login정보
 * @param {*} setModalInfo 모달 정보 변경 setState
 * @returns
 */
function MyPageMbr({ loginVO, setModalInfo }) {
    const [workStleList, setWorkStleList] = useState([]);
    const [form, setForm] = useState({
        mberId: "",
        mberNm: "",
        password: "",
        password2: "",
        brdt: "",
        jncmpYmd: "",
        deptNm: "",
        jbttlNm: "",
        ofcpsNm: "",
        moblphonNo: "",
        workStle: "",
        crqfcList: [],
    });
    const {
        mberId,
        mberNm,
        password,
        password2,
        brdt,
        jncmpYmd,
        deptNm,
        jbttlNm,
        ofcpsNm,
        moblphonNo,
        workStle,
        crqfcList,
    } = form;

    useEffect(() => {
        // 서버에서 세션정보를 가져오는 함수
        // async - 반환받는 값은 Promise로 반환
        const getMbrInfo = async () => {
            // awit - 프라미스가 처리(settled)될 때까지 기다림
            const data = await fncMbrInfo(loginVO.uniqId);
            if (data !== undefined) {
                setWorkStleList(data.workStleList);
                setForm((prevState) => {
                    return {
                        ...prevState,
                        ...data.mberManageVO,
                    };
                });
            }
        };
        // 함수 호출
        getMbrInfo();
    }, [loginVO]);

    // input Change Handler
    const onChangeHandler = (e) => {
        const { name, value } = e.target;
        setForm((prevState) => ({
            ...prevState,
            [name]: value,
        }));
    };

    // Datepicker 값 변경 이벤트
    const onChangeDatePicker = (name, value) => {
        setForm((prevState) => {
            return {
                ...prevState,
                [name]: value,
            };
        });
    };

    // 자격증 관리 추가버튼 클릭 시
    const onClickCrqfcAdit = (e) => {
        setModalInfo((prevState) => {
            return {
                ...prevState,
                showModal: true,
                isLoadingUI: false,
                width: 550,
                height: 500,
                title: "자격증 등록",
                content: (
                    <MyPageCrqfcSaveModal callback={onModalActionCallback} />
                ),
            };
        });
    };

    // modal callback 함수
    const onModalActionCallback = (element) => {
        const { data, action } = element;
        if (action !== "close") {
            setForm((prevState) => ({
                ...prevState,
                crqfcList: [
                    ...prevState.crqfcList,
                    {
                        crqfcId: "",
                        crqfcNm: data.crqfcNm,
                        crqfcIssuinst: data.crqfcIssuinst,
                        crqfcUpdtYmd: data.crqfcUpdtYmd,
                        crqfcIssuYmd: data.crqfcIssuYmd,
                        rm: data.rm,
                    },
                ],
            }));
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

    // 자격증 관리 삭제버튼 클릭 시
    const onClickCrqfcDel = (deleteItem) => {
        setForm((prevState) => ({
            ...prevState,
            crqfcList: [
                ...prevState.crqfcList.filter(
                    (item, index) => index !== deleteItem.index
                ),
            ],
        }));
    };

    // 등록버튼 클륵 시
    const onClickSave = async (e) => {
        if (window.confirm("저장하시겠습니까?")) {
            if (mberNm === "") {
                alert("이름을 입력하세요.");
                return;
            }
            if (moblphonNo === "") {
                alert("연락처를 입력하세요.");
                return;
            }
            if (brdt === "") {
                alert("생년월일을 입력하세요.");
                return;
            }
            if (
                (password !== "" || password2 !== "") &&
                password !== password2
            ) {
                alert("패스워드가 일치하지 않습니다.");
                return;
            }
            console.log(form);
            await CmmFnc.fncRequestAxios(
                "POST",
                "/uss/umt/updateMbr.do",
                form
            ).then((result) => {
                if (result.data.sttus === "success") {
                    alert("저장되었습니다.");
                    return;
                }
            });
        }
    };

    return (
        <div className="mypage_content">
            <div className="wrap">
                <div className="tit_wrap">
                    <p className="must_text">
                        <em>＊</em>는 필수 입력사항입니다.
                    </p>
                </div>
                <div className="board_write intitle">
                    <ul>
                        <li className="must">
                            <strong className="board_label">아이디</strong>
                            <div className="input_wrap">
                                <div className="text_show focus">{mberId}</div>
                            </div>
                        </li>
                        <li className="must">
                            <strong className="board_label">이름</strong>
                            <div className="input_wrap">
                                <div className="text_show focus">
                                    <input
                                        type="text"
                                        className="inputText width100p"
                                        id="mberNm"
                                        name="mberNm"
                                        value={mberNm}
                                        title="이름을 입력하세요"
                                        maxLength="50"
                                        onChange={onChangeHandler}
                                    />
                                    <label htmlFor="mberNm">
                                        이름을 입력하세요
                                    </label>
                                </div>
                            </div>
                        </li>
                        <li>
                            <strong className="board_label">직책 / 직위</strong>
                            <div className="input_wrap">
                                <div className="text_show focus">
                                    {jbttlNm === "" && ofcpsNm === ""
                                        ? "-"
                                        : jbttlNm === "" && ofcpsNm !== ""
                                        ? `- / ${ofcpsNm}`
                                        : jbttlNm !== "" && ofcpsNm === ""
                                        ? `${jbttlNm} / -`
                                        : `${jbttlNm} / ${ofcpsNm}`}
                                </div>
                            </div>
                        </li>
                        <li>
                            <strong className="board_label">부서</strong>
                            <div className="input_wrap">
                                <div className="text_show focus">{deptNm}</div>
                            </div>
                        </li>
                        <li>
                            <strong className="board_label">비밀번호</strong>
                            <div className="input_wrap">
                                <div className="text_show focus">
                                    <input
                                        type="password"
                                        className="inputText width100p"
                                        id="password"
                                        name="password"
                                        size="50"
                                        maxLength="60"
                                        title="비밀번호를 입력하세요"
                                        value={password}
                                        onChange={onChangeHandler}
                                    />
                                    <label htmlFor="password">
                                        비밀번호를 입력하세요
                                    </label>
                                </div>
                            </div>
                        </li>
                        <li>
                            <strong className="board_label">
                                비밀번호 확인
                            </strong>
                            <div className="input_wrap">
                                <div className="text_show focus">
                                    <input
                                        type="password"
                                        className="inputText width100p"
                                        id="password2"
                                        name="password2"
                                        size="50"
                                        maxLength="60"
                                        title="비밀번호를 한번 더 입력하세요"
                                        value={password2}
                                        onChange={onChangeHandler}
                                    />
                                    <label htmlFor="password2">
                                        비밀번호를 한번 더 입력하세요
                                    </label>
                                </div>
                            </div>
                        </li>
                        <li>
                            <strong className="board_label">생년월일</strong>
                            <div className="input_wrap">
                                <div className="text_show focus">
                                    <DatePickerComponert
                                        id="brdt"
                                        name="brdt"
                                        className="inputText width100p"
                                        date={brdt}
                                        setDate={(value) =>
                                            onChangeDatePicker("brdt", value)
                                        }
                                        dateFormat="YYYYMMDD"
                                        maxDate={moment().format("YYYYMMDD")}
                                    />
                                    <label htmlFor="brdt">
                                        생년월일을 입력하세요
                                    </label>
                                </div>
                            </div>
                        </li>
                        <li>
                            <strong className="board_label">입사일자</strong>
                            <div className="input_wrap">
                                <div className="text_show focus">
                                    {jncmpYmd !== ""
                                        ? `${String(jncmpYmd).substring(
                                              0,
                                              4
                                          )}년 ${String(jncmpYmd).substring(
                                              4,
                                              6
                                          )}월 ${String(jncmpYmd).substring(
                                              6,
                                              8
                                          )}일`
                                        : "-"}
                                </div>
                            </div>
                        </li>
                        <li className="must">
                            <strong className="board_label">휴대전화</strong>
                            <div className="input_wrap">
                                <div className="text_show focus">
                                    <InputMask
                                        type="text"
                                        mask={"999-9999-9999"}
                                        value={moblphonNo}
                                        onChange={onChangeHandler}
                                        name="moblphonNo"
                                        title="연락처"
                                        className={"inputText width100p"}
                                        autoComplete="off"
                                    />
                                    <label htmlFor="moblphonNo">
                                        휴대전화를 입력하세요
                                    </label>
                                </div>
                            </div>
                        </li>
                        <li className="must">
                            <strong className="board_label alignL">
                                근무 형태
                            </strong>
                            <div className="input_wrap">
                                <select
                                    className="width100 selectText"
                                    id="workStle"
                                    name="workStle"
                                    title="근무 형태"
                                    value={workStle}
                                    onChange={onChangeHandler}
                                >
                                    {workStleList?.map((item, index) => {
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
                    </ul>
                </div>
            </div>
            <div className="wrap">
                <h2>
                    자격증 관리
                    <button
                        type="button"
                        className="btn_s"
                        onClick={onClickCrqfcAdit}
                    >
                        추가
                    </button>
                </h2>
                <ul className="board_list detailView">
                    {crqfcList?.length === 0 ? (
                        <li className="nothing">
                            <p className="nothing">등록된 자격증이 없습니다.</p>
                        </li>
                    ) : (
                        crqfcList?.map((item, index) => {
                            return (
                                <li id="detail" key={`crqfcList_${index}`}>
                                    <a
                                        href="{() => false}"
                                        onClick={(e) => {
                                            e.preventDefault();
                                            return false;
                                        }}
                                        style={{ cursor: "default" }}
                                    >
                                        <div className="board_no">
                                            {index + 1}
                                        </div>
                                        <div className="item_text">
                                            <div className="board_title">
                                                {item.crqfcNm}
                                            </div>
                                            <div className="board_info_wrap">
                                                <ul className="board_info">
                                                    <li>
                                                        <em>발급기관</em>
                                                        {item.crqfcIssuinst}
                                                    </li>
                                                    <li>
                                                        <em>발급일자</em>
                                                        {item.crqfcIssuYmd !==
                                                        ""
                                                            ? `${String(
                                                                  item.crqfcIssuYmd
                                                              ).substring(
                                                                  0,
                                                                  4
                                                              )}년 ${String(
                                                                  item.crqfcIssuYmd
                                                              ).substring(
                                                                  4,
                                                                  6
                                                              )}월 ${String(
                                                                  item.crqfcIssuYmd
                                                              ).substring(
                                                                  6,
                                                                  8
                                                              )}일`
                                                            : "-"}
                                                    </li>
                                                    <li>
                                                        <em>갱신일자</em>

                                                        {item.crqfcUpdtYmd !==
                                                        ""
                                                            ? `${String(
                                                                  item.crqfcUpdtYmd
                                                              ).substring(
                                                                  0,
                                                                  4
                                                              )}년 ${String(
                                                                  item.crqfcUpdtYmd
                                                              ).substring(
                                                                  4,
                                                                  6
                                                              )}월 ${String(
                                                                  item.crqfcUpdtYmd
                                                              ).substring(
                                                                  6,
                                                                  8
                                                              )}일`
                                                            : "-"}
                                                    </li>
                                                </ul>
                                            </div>
                                        </div>
                                        <div className="condition">
                                            <button
                                                type="button"
                                                className="btn_s fr width60"
                                                onClick={function (param, e) {
                                                    onClickCrqfcDel(param);
                                                }.bind(this, { index })}
                                            >
                                                삭제
                                            </button>
                                        </div>
                                    </a>
                                </li>
                            );
                        })
                    )}
                </ul>
            </div>
            <div className="btn_group">
                <div className="fr">
                    <button
                        className="btn_round bg_navy btn_l"
                        id="btnSave"
                        title="저장"
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

export default connect(mapStateToProps, null)(MyPageMbr);
