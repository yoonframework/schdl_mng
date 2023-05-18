import { useEffect, useRef, useState } from "react";
import CmmFnc from "context/common";
import DatePickerComponert from "schdl_mng/user/utils/DatePickerComponert";
import Pagination from "schdl_mng/common/user/Pagination";
import BsrpDtlModal from "schdl_mng/user/schdl/bsrp/BsrpDtlModal";
import moment from "moment";

// 출장 조회
const fncBsrpList = async (searchKeyword = {}) => {
    return await CmmFnc.fncRequestAxios(
        "POST",
        "/schdl/bsrp/selectBsrpList.do",
        searchKeyword
    ).then((result) => {
        if (result.data.sttus === "success") {
            return result.data.result;
        }
    });
};

/**
 * 마이페이지 - 출장조회
 * @param {*} setModalInfo 모달 정보 setState
 * @returns
 */
function MyPageBsrp({ setModalInfo }) {
    //마이페이지 출장 목록 상태
    const [listState, setListState] = useState({
        bsrpList: [],
        form: {
            bgngDt: "",
            endDt: "",
            searchKeyword: "",
            pageIndex: "1",
        },
        pagination: {
            totalRecordCount: 0,
            currentPageNo: 0,
            recordCountPerPage: 0,
        },
    });

    //마이페이지 출장 목록 상태 내 key 값을 변수로 설정
    const { bsrpList, form, pagination } = listState;
    //마이페이지 출장 목록 상태 내 form의 key 값을 변수로 설정
    const { bgngDt, endDt, searchKeyword } = form;
    //마이페이지 출장 목록 상태 내 pagination의 key 값을 변수로 설정
    const { totalRecordCount, currentPageNo, recordCountPerPage } = pagination;
    //검색 버튼 참조 값
    const btnSearch = useRef(null);

    useEffect(() => {
        // 서버에서 세션정보를 가져오는 함수
        // async - 반환받는 값은 Promise로 반환
        const getBsrpList = async () => {
            // awit - 프라미스가 처리(settled)될 때까지 기다림
            const data = await fncBsrpList();
            if (data !== undefined) {
                setListState((prevState) => {
                    return {
                        ...prevState,
                        bsrpList: data.resultList,
                        pagination: {
                            ...prevState.pagination,
                            ...data.paginationInfo,
                        },
                    };
                });
            }
        };
        // 함수 호출
        getBsrpList();
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
        const data = await fncBsrpList(form);
        if (data !== undefined) {
            setListState((prevState) => {
                return {
                    ...prevState,
                    bsrpList: data.resultList,
                    pagination: {
                        ...prevState.pagination,
                        ...data.paginationInfo,
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
        const data = await fncBsrpList({ ...form, pageIndex: pageIndex });
        if (data !== undefined) {
            setListState((prevState) => {
                return {
                    ...prevState,
                    bsrpList: data.resultList,
                    form: {
                        ...prevState.form,
                        pageIndex: pageIndex,
                    },
                    pagination: {
                        ...prevState.pagination,
                        ...data.paginationInfo,
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
                title: "출장 상세",
                content: (
                    <BsrpDtlModal
                        item={{
                            ...item,
                            startDt: item.bgngDt,
                            endDt: item.endDt,
                            content: item.bsrpCn,
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
                            id="bgngDt"
                            name="bgngDt"
                            className="inputText width100p"
                            date={bgngDt}
                            setDate={(value) =>
                                onChangeDatePicker("bgngDt", value)
                            }
                            dateFormat="YYYYMMDD"
                        />
                        <em> ~ </em>
                        <DatePickerComponert
                            id="endDt"
                            name="endDt"
                            className="inputText width100p"
                            date={endDt}
                            setDate={(value) =>
                                onChangeDatePicker("endDt", value)
                            }
                            dateFormat="YYYYMMDD"
                        />
                    </div>
                    <div className="search_box">
                        <input
                            type="text"
                            id="searchKeyword"
                            name="searchKeyword"
                            className="inputText"
                            value={searchKeyword}
                            placeholder="출장명 또는 출장장소 또는 출장내용을 입력하세요"
                            title="출장명 또는 출장장소 또는 출장내용을 입력"
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
            <ul className="board_list detailView">
                {bsrpList?.map((item, index) => {
                    return (
                        <li id="detail" key={`bsrpList_${index}`}>
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
                                <div className="item_text bsrp">
                                    <div className="board_title">
                                        {item.bsrpTtl}
                                    </div>
                                    <div className="board_info_wrap">
                                        <ul className="board_info">
                                            <li>
                                                <em>장소명</em>
                                                <p>{item.bsrpPlaceNm}</p>
                                            </li>
                                            <li>
                                                <em>시작일자</em>
                                                {moment(
                                                    item.bgngDt,
                                                    "YYYYMMDDHHmm"
                                                ).format("YYYY-MM-DD HH:mm")}
                                            </li>
                                            <li>
                                                <em>종료일자</em>
                                                {moment(
                                                    item.endDt,
                                                    "YYYYMMDDHHmm"
                                                ).format("YYYY-MM-DD HH:mm")}
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                            </a>
                        </li>
                    );
                })}
                {bsrpList?.length === 0 && (
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

export default MyPageBsrp;
