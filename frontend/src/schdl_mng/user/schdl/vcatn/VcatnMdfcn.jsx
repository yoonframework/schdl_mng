import { useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import { connect } from "react-redux";

import URL from "context/url";
import CmmFnc from "context/common.js";
import moment from "moment";

/**
 * 휴가 수정
 * @param {*} loginVO  store에 저장된 loginVO 로드
 * @returns
 */
function VcatnMdfcn({ loginVO }) {
    // 위치 관련 Hook
    const location = useLocation();
    // 페이지 이동 Hook
    const navigate = useNavigate();
    // LoginVO 내 key값을 변수화
    const { authorCode } = loginVO;

    // 페이지 이동 시 넘겨받은 데이터 값 변수로 설정
    const { schdlInfo } = location.state;
    // 수정 Form state 설정
    const [mdfcnState, setMdfcnState] = useState({
        form: {
            vcatnId: schdlInfo?.schdlId,
            bgngYmd: schdlInfo?.startDt,
            endYmd: schdlInfo?.endDt,
            vcatnCn: schdlInfo?.content,
            ...schdlInfo,
        },
    });

    const { form } = mdfcnState;
    const { bgngYmd, endYmd, aplcntNm, vcatnCn, rmndrDayCnt, vcatnSeNm } = form;

    // input 값 변경 이벤트
    const onChangeHandler = (e) => {
        const { name, value } = e.target;

        setMdfcnState((prevState) => {
            return {
                ...prevState,
                form: {
                    ...form,
                    [name]: value,
                },
            };
        });
    };

    // 목록 클릭 이벤트
    const onClickList = (e) => {
        navigate(`${URL.ROOT + URL.SCHDL}`);
    };

    // 삭제 클릭 이벤트
    const onClickDelete = async (e) => {
        if (window.confirm("삭제하시겠습니까?")) {
            await CmmFnc.fncRequestAxios(
                "POST",
                "/schdl/vcatn/deleteVcatn.do",
                form
            ).then((result) => {
                if (result.data.sttus === "success") {
                    navigate(`${URL.ROOT + URL.SCHDL}`);
                }
            });
        }
    };

    // 저장 클릭 이벤트
    const onClickSave = async (e) => {
        if (window.confirm("저장하시겠습니까?")) {
            if (vcatnCn === "") {
                alert("휴가 내용은 필수 입력입니다.");
                return;
            }

            await CmmFnc.fncRequestAxios(
                "POST",
                "/schdl/vcatn/updateVcatn.do",
                form
            ).then((result) => {
                if (result.data.sttus === "success") {
                    navigate(`${URL.ROOT + URL.SCHDL}`);
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
                                    <input
                                        type="text"
                                        className="inputText width200"
                                        id="bgngYmd"
                                        name="bgngYmd"
                                        value={moment(
                                            bgngYmd,
                                            "YYYYMMDD"
                                        ).format("YYYY-MM-DD")}
                                        readOnly={true}
                                    />
                                </div>
                            </li>
                            <li className="must">
                                <strong className="board_label">
                                    종료 일자
                                </strong>
                                <div className="input_wrap">
                                    <input
                                        type="text"
                                        className="inputText width200"
                                        id="endYmd"
                                        name="endYmd"
                                        value={moment(
                                            endYmd,
                                            "YYYYMMDD"
                                        ).format("YYYY-MM-DD")}
                                        readOnly={true}
                                    />
                                </div>
                            </li>
                            {authorCode === "ROLE_ADMIN" && (
                                <li className="must">
                                    <strong className="board_label alignL">
                                        신청자 정보
                                    </strong>
                                    <div className="input_wrap">
                                        <input
                                            type="text"
                                            className="inputText width200"
                                            id="aplcntNm"
                                            name="aplcntNm"
                                            title="신청자 정보"
                                            value={aplcntNm}
                                            readOnly={true}
                                        />
                                    </div>
                                </li>
                            )}
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
                            <li className="must">
                                <strong className="board_label alignL">
                                    휴가 구분
                                </strong>
                                <div className="input_wrap">
                                    <input
                                        type="text"
                                        className="inputText width200"
                                        id="vcatnSeNm"
                                        name="vcatnSeNm"
                                        title="휴가 구분"
                                        value={vcatnSeNm}
                                        readOnly={true}
                                    />
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
                        className="btn_round bg_basic btn_l"
                        title="삭제"
                        id="btnDelete"
                        onClick={onClickDelete}
                    >
                        삭제
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

export default connect(mapStateToProps, null)(VcatnMdfcn);
