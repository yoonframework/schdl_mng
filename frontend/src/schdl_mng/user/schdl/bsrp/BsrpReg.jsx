import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { connect } from "react-redux";

import DatePickerComponert from "schdl_mng/user/utils/DatePickerComponert";
import SchdlMbrSrchModal from "schdl_mng/user/schdl/SchdlMbrSrchModal";
import BsrpCncdntAddModal from "schdl_mng/user/schdl/bsrp/BsrpCncdntAddModal";
import moment from "moment";
import URL from "context/url";
import CmmFnc from "context/common.js";

/**
 * 출장 등록
 * @param {*} loginVO store에 저장된 loginVO 로드
 * @param {*} setModalInfo 모달 정보 변경 setState
 * @returns
 */
function BsrpReg({ loginVO, setModalInfo }) {
    // 페이지 이동 Hook
    const navigate = useNavigate();
    // loginVO의 키값을 변수로 설정
    const { authorCode } = loginVO;
    // 등록 Form state 설정
    const [form, setForm] = useState({
        aplcntId: "",
        aplcntNm: "",
        bsrpTtl: "",
        bsrpPlaceNm: "",
        bsrpCn: "",
        bgngDt: "",
        endDt: "",
        bsrpCncdntList: [],
    });

    // 등록 Form state 내 key 값을 변수로 설정
    const {
        aplcntId,
        aplcntNm,
        bsrpTtl,
        bsrpPlaceNm,
        bsrpCn,
        bgngDt,
        endDt,
        bsrpCncdntList,
    } = form;

    //DatePicker 값 변경 이벤트
    const onChangeDatePicker = (name, item) => {
        setForm((prevState) => {
            return {
                ...prevState,
                [name]: item,
            };
        });
    };

    //Input 값 변경 이벤트
    const onChangeHandler = (e) => {
        const { name, value } = e.target;
        setForm((prevState) => ({
            ...prevState,
            [name]: value,
        }));
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

    //동행자 선택 모달 호출 이벤트
    const onClickAddCncdnt = (e) => {
        if (aplcntId === "" && authorCode === "ROLE_ADMIN") {
            alert("신청자 정보를 먼저 선택해주세요.");
            return;
        }

        const noTargetList =
            bsrpCncdntList.length > 0
                ? bsrpCncdntList.map((item) => {
                      return { tgrpnId: item.tgrpnId };
                  })
                : [];

        setModalInfo((prevState) => {
            return {
                ...prevState,
                showModal: true,
                isLoadingUI: false,
                width: 400,
                height: 600,
                title: "동행 정보 추가",
                content: (
                    <BsrpCncdntAddModal
                        callback={onModalActionCallback}
                        noTargetList={[...noTargetList, { tgrpnId: aplcntId }]}
                    />
                ),
            };
        });
    };

    // 모달 콜백
    const onModalActionCallback = (element) => {
        if (element.action === "add") {
            setForm((prevState) => {
                return {
                    ...prevState,
                    bsrpCncdntList: [
                        ...bsrpCncdntList,
                        ...element.data.map(({ data }) => data.form),
                    ],
                };
            });
        } else if (element.action === "aplcntInfo") {
            setForm((prevState) => {
                return {
                    ...prevState,
                    aplcntId: element.data.aplcntId,
                    aplcntNm: element.data.aplcntNm,
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

    // 동행자 삭제 이벤트
    const onDeleteCncdntInfo = (cncdntInfo, e) => {
        setForm((prevState) => {
            return {
                ...prevState,
                bsrpCncdntList: prevState.bsrpCncdntList.filter(
                    (item) => item.tgrpnId !== cncdntInfo.tgrpnId
                ),
            };
        });
    };

    // 목록 클릭 이벤트
    const onClickList = (e) => {
        navigate(`${URL.ROOT + URL.SCHDL}`);
    };

    // 저장 이벤트
    const onClickSave = async (e) => {
        if (window.confirm("저장하시겠습니까?")) {
            if (bgngDt === "") {
                alert("시작일시는 필수 입력입니다.");
                return;
            }

            if (endDt === "") {
                alert("종료일시는 필수 입력입니다.");
                return;
            }

            if (authorCode === "ROLE_ADMIN" && aplcntId === "") {
                alert("신청자 정보는 필수 입력입니다.");
                return;
            }

            if (bsrpTtl === "") {
                alert("출장 제목은 필수 입력입니다.");
                return;
            }

            if (bsrpPlaceNm === "") {
                alert("출장 장소명은 필수 입력입니다.");
                return;
            }

            if (bsrpCn === "") {
                alert("출장 내용은 필수 입력입니다.");
                return;
            }

            // 관리자가 아닐 경우 등록 일정 유효성 확인
            if (authorCode !== "ROLE_ADMIN") {
                const bgng = moment(bgngDt, "YYYYMMDDHHmm").toDate();
                const todayDt = moment().toDate();
                if (todayDt.getTime() > bgng.getTime()) {
                    alert("이미 지나간 일정에는 등록할 수 없습니다.");
                    return;
                }
            }

            const param = {
                aplcntId: aplcntId,
                bsrpTtl: bsrpTtl,
                bsrpPlaceNm: bsrpPlaceNm,
                bsrpCn: bsrpCn,
                bgngDt: bgngDt,
                endDt: endDt,
                bsrpCncdntList: bsrpCncdntList,
            };

            await CmmFnc.fncRequestAxios(
                "POST",
                "/schdl/bsrp/insertBsrp.do",
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
                        <h3 className="tit">출장 등록</h3>
                        <p className="must_text">
                            <em>＊</em>는 필수 입력사항입니다.
                        </p>
                    </div>
                    <div className="board_write intitle">
                        <ul>
                            <li className="must">
                                <strong className="board_label">
                                    시작 일시
                                </strong>
                                <div className="input_wrap">
                                    <DatePickerComponert
                                        id="bgngDt"
                                        name="bgngDt"
                                        className="inputText width100p"
                                        isSelectTime={true}
                                        date={bgngDt}
                                        setDate={(value) =>
                                            onChangeDatePicker("bgngDt", value)
                                        }
                                        dateFormat="YYYYMMDDHHmm"
                                        minDate={
                                            authorCode !== "ROLE_ADMIN"
                                                ? moment().format(
                                                      "YYYYMMDDHHmm"
                                                  )
                                                : null
                                        }
                                        maxDate={
                                            authorCode !== "ROLE_ADMIN" &&
                                            endDt !== ""
                                                ? endDt
                                                : null
                                        }
                                    />
                                </div>
                            </li>
                            <li className="must">
                                <strong className="board_label">
                                    종료 일시
                                </strong>
                                <div className="input_wrap">
                                    <DatePickerComponert
                                        id="endDt"
                                        name="endDt"
                                        className="inputText width100p"
                                        isSelectTime={true}
                                        date={endDt}
                                        setDate={(value) =>
                                            onChangeDatePicker("endDt", value)
                                        }
                                        dateFormat="YYYYMMDDHHmm"
                                        minDate={
                                            authorCode !== "ROLE_ADMIN"
                                                ? bgngDt !== ""
                                                    ? bgngDt
                                                    : moment().format(
                                                          "YYYYMMDDHHmm"
                                                      )
                                                : null
                                        }
                                    />
                                </div>
                            </li>
                            {authorCode === "ROLE_ADMIN" && (
                                <li className="must">
                                    <strong className="board_label">
                                        신청자 정보
                                    </strong>
                                    <div className="input_wrap">
                                        <button
                                            type="button"
                                            className="btn_m bg_basic mr3"
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
                            )}
                            <li className="must">
                                <strong className="board_label">
                                    출장 제목
                                </strong>
                                <div className="input_wrap">
                                    <div className="text_show">
                                        <input
                                            type="text"
                                            className="inputText width100p"
                                            title="출장 제목 입력"
                                            id="bsrpTtl"
                                            name="bsrpTtl"
                                            maxLength="200"
                                            placeholder="출장 제목을 입력하세요"
                                            value={bsrpTtl}
                                            onChange={onChangeHandler}
                                        />
                                    </div>
                                </div>
                            </li>
                            <li className="must">
                                <strong className="board_label">
                                    출장 장소명
                                </strong>
                                <div className="input_wrap">
                                    <div className="text_show">
                                        <input
                                            type="text"
                                            className="inputText width100p applcntMbtlnum"
                                            id="bsrpPlaceNm"
                                            name="bsrpPlaceNm"
                                            title="출장 장소명 입력"
                                            maxLength="100"
                                            placeholder="출장 장소명을 입력하세요"
                                            value={bsrpPlaceNm}
                                            onChange={onChangeHandler}
                                        />
                                    </div>
                                </div>
                            </li>
                            <li className="must">
                                <strong className="board_label alignL">
                                    출장 내용
                                </strong>
                                <div className="input_wrap">
                                    <div className="text_show">
                                        <textarea
                                            id="bsrpCn"
                                            name="bsrpCn"
                                            className="textArea height100 width100p"
                                            cols="45"
                                            rows="8"
                                            title="출장 내용"
                                            maxLength="2000"
                                            value={bsrpCn}
                                            onChange={onChangeHandler}
                                        />
                                    </div>
                                </div>
                            </li>
                            <li>
                                <strong className="board_label">
                                    동행 정보
                                </strong>
                                <div className="input_wrap">
                                    <button
                                        type="button"
                                        className="btn_m bg_basic mb10"
                                        onClick={onClickAddCncdnt}
                                    >
                                        추가
                                    </button>
                                    <div className="estimate_edit">
                                        <div className="total_srch_wrap tab_wrap">
                                            <div className="tabcontainer">
                                                <div
                                                    className="tab_content"
                                                    style={{
                                                        display: "block",
                                                    }}
                                                >
                                                    <div className="total_srch">
                                                        <ul>
                                                            {bsrpCncdntList.length ===
                                                            0 ? (
                                                                <li className="width100p nothing">
                                                                    <p>
                                                                        등록된
                                                                        동행
                                                                        정보가
                                                                        없습니다.
                                                                    </p>
                                                                </li>
                                                            ) : (
                                                                bsrpCncdntList.map(
                                                                    (
                                                                        cncdntInfo,
                                                                        index
                                                                    ) => {
                                                                        return (
                                                                            <li
                                                                                key={
                                                                                    index
                                                                                }
                                                                            >
                                                                                <div className="item_text">
                                                                                    <ul className="board_info">
                                                                                        <li>
                                                                                            {
                                                                                                cncdntInfo.tgrpnDeptNm
                                                                                            }
                                                                                            <br />
                                                                                            {
                                                                                                cncdntInfo.tgrpnOfcpsNm
                                                                                            }
                                                                                        </li>
                                                                                    </ul>
                                                                                    <div className="board_title">
                                                                                        <div className="title_info"></div>
                                                                                        <p className="textover">
                                                                                            {
                                                                                                cncdntInfo.tgrpnNm
                                                                                            }
                                                                                        </p>
                                                                                    </div>
                                                                                    <button
                                                                                        type="button"
                                                                                        className="btn_sq_m btn_close addIem"
                                                                                        onClick={(
                                                                                            e
                                                                                        ) =>
                                                                                            onDeleteCncdntInfo(
                                                                                                cncdntInfo,
                                                                                                e
                                                                                            )
                                                                                        }
                                                                                        title="삭제"
                                                                                    ></button>
                                                                                </div>
                                                                            </li>
                                                                        );
                                                                    }
                                                                )
                                                            )}
                                                        </ul>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
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

export default connect(mapStateToProps, null)(BsrpReg);
