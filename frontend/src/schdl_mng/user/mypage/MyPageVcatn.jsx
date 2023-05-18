import { useEffect, useRef, useState } from "react";
import CmmFnc from "context/common";
import DatePickerComponert from "schdl_mng/user/utils/DatePickerComponert";
import Pagination from "schdl_mng/common/user/Pagination";
import VcatnDtlModal from "schdl_mng/user/schdl/vcatn/VcatnDtlModal";
import moment from "moment";

// 휴가 조회
const fncVcatnList = async (searchWrd = {}) => {
    return await CmmFnc.fncRequestAxios(
        "POST",
        "/schdl/vcatn/selectVcatnList.do",
        searchWrd
    ).then((result) => {
        if (result.data.sttus === "success") {
            return result.data.result;
        }
    });
};

/**
 * 마이페이지 - 휴가조회
 * @param {*} setModalInfo 모달 정보 setState
 * @returns
 */
function MyPageVcatn({ setModalInfo }) {
    //마이페이지 휴가 목록 상태
    const [listState, setListState] = useState({
        vcatnList: [],
        form: {
            bgngYmd: "",
            endYmd: "",
            vcatnSe: "",
            vcatnCn: "",
            pageIndex: "1",
        },
        pagination: {
            totalRecordCount: 0,
            currentPageNo: 0,
            recordCountPerPage: 0,
        },
        code: {
            vcatnSeList: [],
        },
    });

    //마이페이지 휴가 목록 상태 내 key 값을 변수로 설정
    const { vcatnList, form, pagination, code } = listState;
    //마이페이지 휴가 목록 상태 내 form의 key 값을 변수로 설정
    const { bgngYmd, endYmd, vcatnSe, vcatnCn } = form;
    //마이페이지 휴가 목록 상태 내 pagination의 key 값을 변수로 설정
    const { totalRecordCount, currentPageNo, recordCountPerPage } = pagination;
    //마이페이지 휴가 목록 상태 내 code의 key 값을 변수로 설정
    const { vcatnSeList } = code;

    //검색 버튼 참조 값
    const btnSearch = useRef(null);

    useEffect(() => {
        // 서버에서 세션정보를 가져오는 함수
        // async - 반환받는 값은 Promise로 반환
        const getVcatnList = async () => {
            // awit - 프라미스가 처리(settled)될 때까지 기다림
            const data = await fncVcatnList();
            if (data !== undefined) {
                setListState((prevState) => {
                    return {
                        ...prevState,
                        vcatnList: data.resultList,
                        pagination: {
                            ...prevState.pagination,
                            ...data.paginationInfo,
                        },
                        code: {
                            vcatnSeList: data.vcatnSeList,
                        },
                    };
                });
            }
        };
        // 함수 호출
        getVcatnList();
    }, []);

    // Datepicker 날짜 변경 값
    const onChangeDatePicker = (name, item) => {
        setListState((prevState) => {
            return {
                ...prevState,
                form: {
                    ...prevState.form,
                    [name]: item,
                },
            };
        });
    };

    // Datepicker 날짜 변경 이벤트
    const onChangeHandler = (e) => {
        const { name, value } = e.target;

        setListState((prevState) => {
            return {
                ...prevState,
                form: {
                    ...prevState.form,
                    [name]: value,
                },
            };
        });
    };

    // 검색 버튼 이벤트
    const onSearchHandler = async () => {
        const data = await fncVcatnList(form);
        if (data !== undefined) {
            setListState((prevState) => {
                return {
                    ...prevState,
                    vcatnList: data.resultList,
                    pagination: {
                        ...prevState.pagination,
                        ...data.paginationInfo,
                    },
                    code: {
                        vcatnSeList: data.vcatnSeList,
                    },
                };
            });
        }
    };

    // Enter 키 입력 시 이벤트
    const onKeyPress = (e) => {
        if (e.key === "Enter") {
            btnSearch.current.click();
        }
    };

    // Pagination(페이지 이동) 변경 이벤트
    const onClickPageMoveHandler = async (pageIndex) => {
        const data = await fncVcatnList({ ...form, pageIndex: pageIndex });
        if (data !== undefined) {
            setListState((prevState) => {
                return {
                    ...prevState,
                    vcatnList: data.resultList,
                    form: {
                        ...prevState.form,
                        pageIndex: pageIndex,
                    },
                    pagination: {
                        ...prevState.pagination,
                        ...data.paginationInfo,
                    },
                    code: {
                        vcatnSeList: data.vcatnSeList,
                    },
                };
            });
        }
    };

    // 항목 클릭 시 상세 모달 호출
    const onClickSchdlItem = (item) => {
        setModalInfo((prevState) => {
            return {
                ...prevState,
                showModal: true,
                isLoadingUI: false,
                width: 600,
                height: 620,
                title: "휴가 상세",
                content: (
                    <VcatnDtlModal
                        item={{
                            ...item,
                            startDt: item.bgngYmd,
                            endDt: item.endYmd,
                            content: item.vcatnCn,
                        }}
                        callback={onModalActionCallback}
                    />
                ),
            };
        });
    };

    // 모달 콜백
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

    return (
        <div className="mypage_content">
            <div className="sorting_wrap sel1">
                <div className="total">
                    전체 <em className="count">{totalRecordCount}</em>건
                </div>
                <div className="search_box_wrap">
                    <div className="date_picker_wrap mr10">
                        <DatePickerComponert
                            id="bgngYmd"
                            name="bgngYmd"
                            className="inputText width100p"
                            date={bgngYmd}
                            setDate={(value) =>
                                onChangeDatePicker("bgngYmd", value)
                            }
                            dateFormat="YYYYMMDD"
                        />
                        <em> ~ </em>
                        <DatePickerComponert
                            id="endYmd"
                            name="endYmd"
                            className="inputText width100p"
                            date={endYmd}
                            setDate={(value) =>
                                onChangeDatePicker("endYmd", value)
                            }
                            dateFormat="YYYYMMDD"
                        />
                    </div>
                    <div className="select_input_wrap">
                        <div className="select_wrap mr10">
                            <select
                                className="width130 selectText"
                                id="vcatnSe"
                                name="vcatnSe"
                                title="유형"
                                value={vcatnSe}
                                onChange={onChangeHandler}
                            >
                                <option value="">전체(휴가구분)</option>
                                {vcatnSeList?.map((item, index) => (
                                    <option
                                        key={`vcatnSe_${index}`}
                                        value={item.code}
                                    >
                                        {item.codeNm}
                                    </option>
                                ))}
                            </select>
                        </div>
                        <div className="search_box">
                            <input
                                type="text"
                                id="vcatnCn"
                                name="vcatnCn"
                                className="inputText"
                                value={vcatnCn}
                                placeholder="휴가 내용을 입력하세요"
                                title="휴가 내용 입력"
                                onChange={onChangeHandler}
                                onKeyPress={onKeyPress}
                            />
                            <button
                                ref={btnSearch}
                                type="button"
                                className="btn_search"
                                title="검색버튼"
                                onClick={onSearchHandler}
                            >
                                <span className="blind">검색</span>
                            </button>
                        </div>
                    </div>
                </div>
            </div>
            <ul className="board_list detailView">
                {vcatnList?.map((item, index) => {
                    return (
                        <li id="detail" key={`vcatnList_${index}`}>
                            <a
                                href="{() => false}"
                                onClick={(e) => {
                                    e.preventDefault();
                                    onClickSchdlItem(item);
                                    return false;
                                }}
                            >
                                <div className="board_no">
                                    {parseInt(totalRecordCount) -
                                        ((parseInt(currentPageNo) - 1) *
                                            parseInt(recordCountPerPage) +
                                            index)}
                                </div>
                                <div className="item_text">
                                    <div className="board_title">
                                        {item.vcatnCn}
                                    </div>
                                    <div className="board_info_wrap">
                                        <ul className="board_info">
                                            <li>
                                                <em>시작일자</em>
                                                {moment(
                                                    item.bgngYmd,
                                                    "YYYYMMDD"
                                                ).format("YYYY-MM-DD")}
                                            </li>
                                            <li>
                                                <em>종료일자</em>
                                                {moment(
                                                    item.endYmd,
                                                    "YYYYMMDD"
                                                ).format("YYYY-MM-DD")}
                                            </li>
                                        </ul>
                                    </div>
                                </div>

                                <div className="condition">
                                    <span className="condit1">
                                        {item.vcatnSeNm}
                                    </span>
                                </div>
                            </a>
                        </li>
                    );
                })}
                {vcatnList?.length === 0 && (
                    <li className="nothing">
                        <p className="nothing">등록된 내용이 없습니다.</p>
                    </li>
                )}
            </ul>
            <div className="btn_group">
                {/* 페이징
                 * param - pagination : 페이징 정보
                 * param - moveToPage : 클릭한 페이지 정보를 받아 검색조건 수정
                 */}
                <Pagination
                    pagination={pagination}
                    moveToPage={(passedPage) => {
                        onClickPageMoveHandler(passedPage);
                    }}
                />
            </div>
        </div>
    );
}

export default MyPageVcatn;
