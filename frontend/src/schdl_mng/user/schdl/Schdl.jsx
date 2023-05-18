import React, { Fragment, useEffect, useRef, useState } from "react";
import { useNavigate } from "react-router-dom";

import SchdlCalendar from "schdl_mng/user/schdl/SchdlCalendar";
import SchdlList from "schdl_mng/user/schdl/SchdlList";
import DatePickerComponert from "schdl_mng/user/utils/DatePickerComponert";
import VcatnDtlModal from "schdl_mng/user/schdl/vcatn/VcatnDtlModal";
import BsrpDtlModal from "schdl_mng/user/schdl/bsrp/BsrpDtlModal";

//context
import URL from "context/url";
import CmmFnc from "context/common.js";

import $ from "jquery";
import moment from "moment";

//일정 종류 명시
const TYPE_LIST = [
    {
        typeCd: "vcatn",
        typeNm: "휴가",
        btnRegClassName: "btn_round bg_navy btn_l",
        abrvNm: "휴",
        typeColor: "bg04",
    },
    {
        typeCd: "bsrp",
        typeNm: "출장",
        btnRegClassName: "btn_round bg_green btn_l",
        abrvNm: "출",
        typeColor: "bg03",
    },
];

//일정 화면 종류 명시
const VIEW_LIST = [
    {
        viewCd: "list",
        viewNm: "리스트",
        title: "리스트로보기",
        src: require("images/user/icon_list.svg").default,
    },
    {
        viewCd: "calendar",
        viewNm: "달력",
        title: "달력으로보기",
        src: require("images/user/icon_calendar.svg").default,
    },
];

// 일정 가져오기
const fncSchdlList = async (searchData = {}) => {
    // 스케줄 정보를 가져와야함
    return await CmmFnc.fncRequestAxios(
        "POST",
        "/user/schdl/selectSchdlList.do",
        searchData
    ).then((result) => {
        if (result.data.sttus === "success") {
            return result.data.result;
        } else {
            return [];
        }
    });
};

/**
 * 일정관리
 * @param {*} setModalInfo 모달 정보 변경 setState
 * @param {*} windowSize 창 크기 state
 * @returns
 */
function Schdl({ setModalInfo, windowSize }) {
    // 페이지 이동 Hook
    const navigate = useNavigate();
    // 창 가로 크기
    const { width } = windowSize;
    // 일정 관리 정보 state
    const [currentViewInfo, setCurrentViewInfo] = useState({
        schdlList: [],
        form: {
            viewCd: "",
            typeCd: "",
            deptId: "",
            aplcntNm: "",
            //아래 두개의 값은 달력일 경우 초기값 세팅
            // 최초 화면이 리스트 일경우 빈값 변경 필요, 탭 변경 이벤트도 변경 필요
            startDt: "",
            endDt: "",
            pageIndex: "1",
        },
        pagination: {
            totalRecordCount: 0,
            currentPageNo: 0,
            recordCountPerPage: 0,
        },
        code: {
            deptList: [],
        },
    });

    // 일정 관리 state 내 key 를 변수로 사용
    const { form, code } = currentViewInfo;
    // 일정 관리 state 내 form의 key 를 변수로 사용
    const { viewCd, startDt, endDt, typeCd, deptId, aplcntNm } = form;
    // 일정 관리 state 내 code의 key 를 변수로 사용
    const { deptList } = code;

    // 검색 버튼 참조 값
    const btnSearch = useRef(null);

    useEffect(() => {
        // 서버에서 세션정보를 가져오는 함수
        // async - 반환받는 값은 Promise로 반환
        const getSchdlList = async () => {
            const initStartDt = moment().startOf("month").format("YYYYMMDD");
            const initEndDt = moment().endOf("month").format("YYYYMMDD");
            // awit - 프라미스가 처리(settled)될 때까지 기다림
            const init = {
                viewCd: "calendar",
                typeCd: "",
                deptId: "",
                aplcntNm: "",
                startDt: initStartDt,
                endDt: initEndDt,
                pageIndex: "1",
            };
            const data = await fncSchdlList(init);
            if (data !== undefined) {
                setCurrentViewInfo((prevState) => {
                    return {
                        ...prevState,
                        schdlList: data.resultList,
                        form: {
                            ...prevState.form,
                            ...init,
                        },
                        pagination: {
                            ...prevState.pagination,
                            ...data.paginationInfo,
                        },
                        code: {
                            deptList: data.deptList,
                        },
                    };
                });
            }
        };
        // 함수 호출
        getSchdlList();
    }, []);

    // input 값 수정 이벤트
    const onChangeHandler = (e) => {
        const { name, value } = e.target;

        setCurrentViewInfo((prevState) => {
            return {
                ...prevState,
                form: {
                    ...prevState.form,
                    [name]: value,
                },
            };
        });
    };

    // Enter 버튼 입력 시 핸들러
    const onKeyPress = (e) => {
        if (e.key === "Enter") {
            btnSearch.current.click();
        }
    };

    // 검색 이벤트
    const onSearchHandler = async () => {
        const data = await fncSchdlList(form);
        if (data !== undefined) {
            setCurrentViewInfo((prevState) => {
                return {
                    ...prevState,
                    schdlList: data.resultList,
                    pagination: {
                        ...prevState.pagination,
                        ...data.paginationInfo,
                    },
                    code: {
                        deptList: data.deptList,
                    },
                };
            });
        }
    };

    // 초기화 이벤트
    const onResetHandler = async () => {
        const resetForm = {
            ...form,
            typeCd: "",
            deptId: "",
            aplcntNm: "",
            pageIndex: "1",
            startDt:
                viewCd === "calendar"
                    ? moment().startOf("month").format("YYYYMMDD")
                    : "",
            endDt:
                viewCd === "calendar"
                    ? moment().endOf("month").format("YYYYMMDD")
                    : "",
        };

        const data = await fncSchdlList(resetForm);
        if (data !== undefined) {
            setCurrentViewInfo((prevState) => {
                return {
                    ...prevState,
                    schdlList: data.resultList,
                    form: {
                        ...prevState.form,
                        ...resetForm,
                    },
                    pagination: {
                        ...prevState.pagination,
                        ...data.paginationInfo,
                    },
                    code: {
                        deptList: data.deptList,
                    },
                };
            });
        }
    };

    // Tab Toggle 이벤트
    const onClickTabHandler = async (e) => {
        e.preventDefault();
        let tab = null;
        if (e.target.tagName === "IMG") {
            tab = e.target.closest("a").dataset.tab;
        } else {
            tab = e.target.dataset.tab;
        }

        const changeForm = {
            viewCd: tab,
            typeCd: "",
            deptId: "",
            aplcntNm: "",
            pageIndex: "1",
            startDt:
                tab === "calendar"
                    ? moment().startOf("month").format("YYYYMMDD")
                    : "",
            endDt:
                tab === "calendar"
                    ? moment().endOf("month").format("YYYYMMDD")
                    : "",
        };

        const data = await fncSchdlList(changeForm);
        if (data !== undefined) {
            setCurrentViewInfo((prevState) => {
                return {
                    ...prevState,
                    schdlList: data.resultList,
                    form: {
                        ...prevState.form,
                        ...changeForm,
                    },
                    pagination: {
                        ...prevState.pagination,
                        ...data.paginationInfo,
                    },
                    code: {
                        deptList: data.deptList,
                    },
                };
            });
        }
    };

    // Datepicker 변경 이벤트
    const onChangeDatePicker = (name, item) => {
        setCurrentViewInfo((prevState) => {
            return {
                ...prevState,
                form: {
                    ...prevState.form,
                    [name]: item,
                },
            };
        });
    };

    // 일정 항목 선택 시 일정 타입에 맞게 모달 호출
    const onClickSchdlItem = (item) => {
        const modalContent =
            item.typeCd === "vcatn" ? (
                <VcatnDtlModal item={item} callback={onModalActionCallback} />
            ) : (
                <BsrpDtlModal item={item} callback={onModalActionCallback} />
            );

        setModalInfo((prevState) => {
            return {
                ...prevState,
                showModal: true,
                isLoadingUI: false,
                width: 600,
                height: 620,
                title: item.typeNm + " 상세",
                content: modalContent,
            };
        });
    };

    // 모달 콜백 함수
    const onModalActionCallback = (element) => {
        if (element.action === "close") {
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
        }
    };

    // 등록 화면 호출
    const onClickRegViewHandler = (e) => {
        if (e.target.name === "vcatn") {
            navigate(`${URL.SCHDL_VCATN}/${URL.REG}`);
        } else if (e.target.name === "bsrp") {
            navigate(`${URL.SCHDL_BSRP}/${URL.REG}`);
        }
    };

    // 일정 타입이 리스트일 경우 Pagination 클릭 이벤트 처리
    const onClickPageMoveHandler = async (pageIndex) => {
        const data = await fncSchdlList({ ...form, pageIndex: pageIndex });
        if (data !== undefined) {
            setCurrentViewInfo((prevState) => {
                return {
                    ...prevState,
                    schdlList: data.resultList,
                    pagination: {
                        ...prevState.pagination,
                        ...data.paginationInfo,
                    },
                    code: {
                        deptList: data.deptList,
                    },
                };
            });
        }
    };

    // 일정 타입이 달력일 경우 년, 월 수정 변경 이벤트
    const onChangeCalendar = async (year, month) => {
        const changeStartDt = moment()
            .year(year)
            .month(month - 1)
            .startOf("month")
            .format("YYYYMMDD");
        const changeEndDt = moment()
            .year(year)
            .month(month - 1)
            .endOf("month")
            .format("YYYYMMDD");
        const changeForm = {
            ...form,
            startDt: changeStartDt,
            endDt: changeEndDt,
        };

        const data = await fncSchdlList(changeForm);
        if (data !== undefined) {
            setCurrentViewInfo((prevState) => {
                return {
                    ...prevState,
                    schdlList: data.resultList,
                    form: {
                        ...prevState.form,
                        ...changeForm,
                    },
                    pagination: {
                        ...prevState.pagination,
                        ...data.paginationInfo,
                    },
                    code: {
                        deptList: data.deptList,
                    },
                };
            });
        }
    };

    // html 태그 안 html이 인식될 경우를 방지하기 위하여 tag명으로 switch
    // 모바일 화면 시 검색 조건 슬라이드 이벤트
    const onSchdlSearchViewClick = (e) => {
        switch (e.target.tagName) {
            case "H3":
                onToggleHandler(e.target.closest("div.tit_wrap"));
                break;
            case "DIV":
                onToggleHandler(e.target);
                break;
            default:
                break;
        }
    };

    // 대상 elem 슬라이드 효과
    const onToggleHandler = (elem) => {
        if (width <= 500) {
            $(elem).toggleClass("on");
            $(elem).next(".toggle_content").slideToggle();
        } else {
            $(elem).removeClass("on");
            $(elem).next(".toggle_content").slideDown();
        }
    };

    //요약 탭 클릭 핸들러
    const onClickAbrvTab = async (item) => {
        if (form.typeCd === item.typeCd) {
            const data = await fncSchdlList({ ...form, typeCd: "" });
            if (data !== undefined) {
                setCurrentViewInfo((prevState) => {
                    return {
                        ...prevState,
                        schdlList: data.resultList,
                        form: {
                            ...prevState.form,
                            typeCd: "",
                        },
                    };
                });
            }
        } else {
            const data = await fncSchdlList({ ...form, typeCd: item.typeCd });
            if (data !== undefined) {
                setCurrentViewInfo((prevState) => {
                    return {
                        ...prevState,
                        schdlList: data.resultList,
                        form: {
                            ...prevState.form,
                            typeCd: item.typeCd,
                        },
                    };
                });
            }
        }
    };

    return (
        <div className="content board_wrap" id="skip_content">
            <div className="inner">
                <div className={`wrap toggle ${width < 500 ? "mobile" : ""}`}>
                    <div className="tit_wrap" onClick={onSchdlSearchViewClick}>
                        <h3 className="tit">일정 상세검색</h3>
                    </div>
                    <div
                        className="toggle_content"
                        style={{ display: width < 500 ? "none" : "block" }}
                    >
                        <div id="searchArea" className="board_write intitle">
                            <ul>
                                {viewCd === "list" && (
                                    <li>
                                        <strong className="board_label">
                                            기간
                                        </strong>
                                        <div className="input_wrap">
                                            <DatePickerComponert
                                                id="startDt"
                                                name="startDt"
                                                className="inputText width100p"
                                                date={startDt}
                                                dateFormat="YYYYMMDD"
                                                setDate={(value) =>
                                                    onChangeDatePicker(
                                                        "startDt",
                                                        value
                                                    )
                                                }
                                            />
                                            <em> ~ </em>
                                            <DatePickerComponert
                                                id="endDt"
                                                name="endDt"
                                                className="inputText width100p"
                                                date={endDt}
                                                dateFormat="YYYYMMDD"
                                                setDate={(value) =>
                                                    onChangeDatePicker(
                                                        "endDt",
                                                        value
                                                    )
                                                }
                                            />
                                        </div>
                                    </li>
                                )}
                                <li>
                                    <strong className="board_label">
                                        유형
                                    </strong>
                                    <div className="input_wrap">
                                        <select
                                            className="width300 selectText"
                                            id="typeCd"
                                            name="typeCd"
                                            title="유형"
                                            value={typeCd}
                                            onChange={onChangeHandler}
                                        >
                                            <option value="">전체</option>
                                            {TYPE_LIST?.map((item, index) => (
                                                <option
                                                    key={`searchTypeCd_${index}`}
                                                    value={item.typeCd}
                                                >
                                                    {item.typeNm}
                                                </option>
                                            ))}
                                        </select>
                                    </div>
                                </li>
                                <li>
                                    <strong className="board_label">
                                        부서
                                    </strong>
                                    <div className="input_wrap">
                                        <select
                                            className="width300 selectText"
                                            id="deptId"
                                            name="deptId"
                                            title="부서"
                                            value={deptId}
                                            onChange={onChangeHandler}
                                        >
                                            <option value="">전체</option>
                                            {deptList?.map((item, index) => (
                                                <option
                                                    key={`searchDeptId_${index}`}
                                                    value={item.deptId}
                                                >
                                                    {item.deptNm}
                                                </option>
                                            ))}
                                        </select>
                                    </div>
                                </li>
                                <li>
                                    <strong className="board_label">
                                        직원명
                                    </strong>
                                    <div className="input_wrap">
                                        <div className="text_show">
                                            <input
                                                type="text"
                                                className="inputText width50p"
                                                id="aplcntNm"
                                                name="aplcntNm"
                                                autoComplete="off"
                                                placeholder="직원명을 입력하세요"
                                                value={aplcntNm}
                                                onChange={onChangeHandler}
                                                onKeyPress={onKeyPress}
                                            />
                                        </div>
                                    </div>
                                </li>
                            </ul>
                        </div>
                        <div
                            id="searchArea"
                            className="board_write intitle"
                        ></div>
                        <div className="btn_group btn_same alignC">
                            <button
                                type="button"
                                id="btnReset"
                                className="btn_round bg_basic btn_l"
                                onClick={onResetHandler}
                                title="검색조건 초기화"
                            >
                                검색조건 초기화
                            </button>
                            <button
                                ref={btnSearch}
                                type="button"
                                id="btnSearch"
                                className="btn_round bg_navy btn_l"
                                onClick={onSearchHandler}
                                title="검색"
                            >
                                검색
                            </button>
                        </div>
                    </div>
                </div>
                <div className="tab_wrap">
                    <div className="switch_tab_wrap">
                        <strong className="mr10">목록/달력 전환</strong>
                        <ul className="tabs switch_tab">
                            {VIEW_LIST.map((item, index) => (
                                <li
                                    key={`viewCd_${index}`}
                                    className={
                                        viewCd === item.viewCd ? "active" : ""
                                    }
                                >
                                    <a
                                        href="{() => false}"
                                        title={item.title}
                                        onClick={onClickTabHandler}
                                        data-tab={item.viewCd}
                                    >
                                        <img
                                            src={item.src}
                                            alt={`${item.viewNm} 아이콘`}
                                        />
                                        {item.title}
                                    </a>
                                </li>
                            ))}
                        </ul>
                    </div>
                    <div className="tab_container pt0">
                        {viewCd === "list" ? (
                            <div className="tab_content relative" id="tabList">
                                <SchdlList
                                    currentViewInfo={currentViewInfo}
                                    onClickPageMoveHandler={
                                        onClickPageMoveHandler
                                    }
                                    onClickSchdlItem={onClickSchdlItem}
                                />
                            </div>
                        ) : viewCd === "calendar" ? (
                            <div className="tab_content" id="tabCalendar">
                                <SchdlCalendar
                                    currentViewInfo={currentViewInfo}
                                    onClickAbrvTab={onClickAbrvTab}
                                    calendarType={TYPE_LIST}
                                    onChangeCalendar={onChangeCalendar}
                                    onClickSchdlItem={onClickSchdlItem}
                                />
                            </div>
                        ) : null}
                    </div>
                    <div className="btn_group btn_same">
                        {TYPE_LIST.map((item, index) => (
                            <Fragment key={`typeCd_${index}`}>
                                <button
                                    type="button"
                                    name={item.typeCd}
                                    className={item.btnRegClassName}
                                    title={`${item.typeNm} 등록`}
                                    onClick={onClickRegViewHandler}
                                >
                                    {`${item.typeNm} 등록`}
                                </button>
                            </Fragment>
                        ))}
                    </div>
                </div>
            </div>
        </div>
    );
}

export default Schdl;
