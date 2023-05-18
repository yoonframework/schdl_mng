import { useState, useEffect, useRef } from "react";
import { useNavigate } from "react-router-dom";
import Pagination from "schdl_mng/common/admin/Pagination";

import URL from "context/url";
import CmmFnc from "context/common.js";

// 직원 목록 조회
const fncMbrList = async (searchData = {}) => {
    return await CmmFnc.fncRequestAxios(
        "POST",
        "/uss/umt/selectMbrList.do",
        searchData
    ).then((result) => {
        if (result.data.sttus === "success") {
            return result.data.result;
        }
    });
};

/**
 * 직원 관리 목록 Component
 * @returns
 */
function AdminMbrList() {
    // 페이지 이동 Hook
    const navigate = useNavigate();
    // 직원 관리 목록 Component 관리를 위한 state
    const [listState, setListState] = useState({
        mbrList: [],
        isAllChk: false,
        checkField: new Set(),
        form: {
            sbscrbSttus: "0",
            searchCondition: "99",
            searchKeyword: "",
            pageIndex: "1",
        },
        pagination: {
            totalRecordCount: 0,
            currentPageNo: 0,
            recordCountPerPage: 0,
        },
        code: {
            authorList: [],
            sttusList: [],
            ofcpsList: [],
            jbttlList: [],
            deptList: [],
        },
    });
    // 직원 관리 목록 Component 관리를 위한 state 의 Key 값을 변수로 지정
    const { mbrList, isAllChk, checkField, form, pagination, code } = listState;
    // form의 Key 값을 변수로 지정
    const { sbscrbSttus, searchCondition, searchKeyword } = form;
    // pagination의 Key 값을 변수로 지정
    const { totalRecordCount, currentPageNo, recordCountPerPage } = pagination;
    // code의 Key 값을 변수로 지정
    const { authorList, sttusList, deptList } = code;
    // 검색 버튼 참고 값
    const btnSearch = useRef(null);
    // 직원 조회 후 Component 관리 state에 setState
    useEffect(() => {
        // 서버에서 세션정보를 가져오는 함수
        // async - 반환받는 값은 Promise로 반환
        const getMbrList = async () => {
            // awit - 프라미스가 처리(settled)될 때까지 기다림
            const data = await fncMbrList();
            if (data !== undefined) {
                setListState((prevState) => {
                    return {
                        ...prevState,
                        mbrList: data.resultList,
                        pagination: {
                            ...prevState.pagination,
                            ...data.paginationInfo,
                        },
                        code: {
                            authorList: data.authorList,
                            sttusList: data.entrprsMberSttusResult,
                            ofcpsList: data.ofcpsList,
                            jbttlList: data.jbttlList,
                            workStleList: data.workStleList,
                            deptList: data.deptList,
                        },
                    };
                });
            }
        };
        // 함수 호출
        getMbrList();
    }, []);

    // Input, Select Change Event
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

    // All Check Change Event
    const onAllCheckChangeHandler = (e) => {
        const { checked } = e.target;
        if (checked) {
            setListState((prevState) => {
                return {
                    ...prevState,
                    isAllChk: true,
                    checkField: new Set(
                        mbrList.map(
                            ({ uniqId, userTy }) => `${userTy}:${uniqId}`
                        )
                    ),
                };
            });
        } else {
            checkField.clear();
            setListState((prevState) => {
                return {
                    ...prevState,
                    isAllChk: false,
                    checkField: checkField,
                };
            });
        }
    };

    // Check Change Event
    const onCheckChangeHandler = (e) => {
        const { value, checked } = e.target;
        if (checked) {
            // 체크
            checkField.add(value);
        } else {
            // 체크 해제
            checkField.delete(value);
        }

        setListState((prevState) => {
            return {
                ...prevState,
                isAllChk: checkField.size === mbrList.length,
                checkField: checkField,
            };
        });
    };

    // 검색 버튼 Event
    const onSearchHandler = async () => {
        const data = await fncMbrList(form);
        if (data !== undefined) {
            setListState((prevState) => {
                return {
                    ...prevState,
                    mbrList: data.resultList,
                    pagination: {
                        ...prevState.pagination,
                        ...data.paginationInfo,
                    },
                    code: {
                        authorList: data.authorList,
                        sttusList: data.entrprsMberSttusResult,
                        ofcpsList: data.ofcpsList,
                        jbttlList: data.jbttlList,
                        workStleList: data.workStleList,
                        deptList: data.deptList,
                    },
                };
            });
        }
    };

    // 검색 란 입력 후 Enter 키 입력 이벤트
    const onKeyPress = (e) => {
        if (e.key === "Enter") {
            btnSearch.current.click();
        }
    };

    // 등록 화면으로 이동
    const onClickRegViewHandler = (e) => {
        navigate(URL.REG, { state: { code } });
    };

    // 수정 화면으로 이동
    const onClickMdfcnViewHandler = (mbr) => {
        navigate(URL.MDFCN, { state: { mbr, code } });
    };

    // 삭제 처리 이벤트
    const onClickDelHandler = async (e) => {
        if (checkField.size !== 0) {
            if (window.confirm("삭제하시겠습니까?")) {
                await CmmFnc.fncRequestAxios(
                    "POST",
                    "/uss/umt/deleteMbr.do",
                    [...checkField].join(",")
                );

                const data = await fncMbrList();
                if (data !== undefined) {
                    setListState((prevState) => {
                        return {
                            ...prevState,
                            mbrList: data.resultList,
                            pagination: {
                                ...prevState.pagination,
                                ...data.paginationInfo,
                            },
                            code: {
                                authorList: data.authorList,
                                sttusList: data.entrprsMberSttusResult,
                                ofcpsList: data.ofcpsList,
                                jbttlList: data.jbttlList,
                                workStleList: data.workStleList,
                                deptList: data.deptList,
                            },
                        };
                    });
                }
            }
        } else {
            alert("선택된 직원이 없습니다.");
        }
    };

    // 현재 페이지 정보 변경 이벤트
    const onClickPageMoveHandler = async (pageIndex) => {
        const data = await fncMbrList({ ...form, pageIndex: pageIndex });
        if (data !== undefined) {
            setListState((prevState) => {
                return {
                    ...prevState,
                    mbrList: data.resultList,
                    form: {
                        ...prevState.form,
                        pageIndex: pageIndex,
                    },
                    pagination: {
                        ...prevState.pagination,
                        ...data.paginationInfo,
                    },
                    code: {
                        authorList: data.authorList,
                        sttusList: data.entrprsMberSttusResult,
                        ofcpsList: data.ofcpsList,
                        jbttlList: data.jbttlList,
                        workStleList: data.workStleList,
                        deptList: data.deptList,
                    },
                };
            });
        }
    };

    return (
        <>
            <div className="board_header">
                <h3>직원관리 목록</h3>
                <div
                    className="search_box"
                    title="이 레이아웃은 하단 정보를 대한 검색 정보로 구성되어 있습니다."
                >
                    <select
                        name="sbscrbSttus"
                        id="sbscrbSttus"
                        className="select fl"
                        title="부서 선택"
                        defaultValue={sbscrbSttus}
                        onChange={onChangeHandler}
                    >
                        <option value="0">부서(전체)</option>
                        {deptList?.map((item, index) => (
                            <option
                                key={`searchTypeCd_${index}`}
                                value={item.deptId}
                            >
                                {item.deptNm}
                            </option>
                        ))}
                    </select>
                    <select
                        name="searchCondition"
                        id="searchCondition"
                        className="select fl width170"
                        title="조회조건 선택"
                        defaultValue={searchCondition}
                        onChange={onChangeHandler}
                    >
                        <option value="99">전체(ID 또는 이름)</option>
                        <option value="0">ID</option>
                        <option value="1">이름</option>
                    </select>
                    <input
                        className="inputText"
                        name="searchKeyword"
                        type="text"
                        size="35"
                        placeholder="ID 또는 이름을 입력하세요"
                        title="검색어 입력"
                        defaultValue={searchKeyword}
                        onChange={onChangeHandler}
                        maxLength="255"
                        onKeyPress={onKeyPress}
                    />
                    <button
                        ref={btnSearch}
                        type="button"
                        className="btn_board"
                        title="조회 버튼"
                        onClick={onSearchHandler}
                    >
                        조회
                    </button>
                </div>
            </div>
            <div className="board_body">
                <div className="board_infomation">
                    <p>
                        총 : <span>{totalRecordCount}</span>건, 쪽번호 :
                        <span>{currentPageNo}</span>/
                        <span>
                            {recordCountPerPage !== 0
                                ? parseInt(
                                      (parseInt(totalRecordCount) - 1) /
                                          parseInt(recordCountPerPage)
                                  ) + 1
                                : 0}
                        </span>
                    </p>
                </div>
                <table
                    className="table_list"
                    summary="직원관리의 내역에 대한 목록을 출력합니다."
                >
                    <caption>직원관리 목록</caption>
                    <colgroup>
                        <col style={{ width: "5%" }} />
                        <col style={{ width: "5%" }} />
                        <col style={{ width: "20%" }} />
                        <col style={{ width: "20%" }} />
                        <col style={{ width: "20%" }} />
                        <col style={{ width: "20%" }} />
                    </colgroup>
                    <thead>
                        <tr>
                            <th>
                                <input
                                    type="checkbox"
                                    name="checkAll"
                                    id="checkAll"
                                    title="전체선택체크박스"
                                    value={isAllChk}
                                    checked={isAllChk}
                                    onChange={onAllCheckChangeHandler}
                                />
                                <label htmlFor="checkAll">
                                    <span></span>
                                </label>
                            </th>
                            <th>번호</th>
                            <th className="board_th_link">아이디</th>
                            <th>이름</th>
                            <th>직원 권한</th>
                            <th>가입상태</th>
                        </tr>
                    </thead>
                    <tbody className="ov">
                        {mbrList?.map((item, index) => {
                            return (
                                <tr key={index}>
                                    <td>
                                        <input
                                            name="checkField"
                                            id={`checkField_${index}`}
                                            title={`checkField_${index}`}
                                            type="checkbox"
                                            value={`${item.userTy}:${item.uniqId}`}
                                            checked={checkField.has(
                                                `${item.userTy}:${item.uniqId}`
                                            )}
                                            onChange={onCheckChangeHandler}
                                        />
                                        <label htmlFor={`checkField_${index}`}>
                                            <span></span>
                                        </label>
                                    </td>
                                    <td>
                                        {parseInt(totalRecordCount) -
                                            ((parseInt(currentPageNo) - 1) *
                                                parseInt(recordCountPerPage) +
                                                index)}
                                    </td>
                                    <td>
                                        <a
                                            href="{() => false}"
                                            onClick={(e) => {
                                                e.preventDefault();
                                                onClickMdfcnViewHandler(item);
                                                return false;
                                            }}
                                        >
                                            {item.mberId}
                                        </a>
                                    </td>
                                    <td>{item.nmplt}</td>
                                    <td>
                                        {authorList
                                            ?.filter((author) =>
                                                String(
                                                    item.authorCode
                                                ).includes(author.authorCode)
                                            )
                                            .map((author) => author.authorNm)
                                            .sort()
                                            .join(", ")}
                                    </td>
                                    <td>
                                        {sttusList
                                            ?.filter(
                                                (sttus) =>
                                                    item.mberSttus ===
                                                    sttus.code
                                            )
                                            .map((sttus) => sttus.codeNm)
                                            .sort()
                                            .join(", ")}
                                    </td>
                                </tr>
                            );
                        })}
                        {mbrList?.length === 0 && (
                            <tr>
                                <td colSpan="6">
                                    자료가 없습니다. 다른 검색조건을
                                    선택해주세요
                                </td>
                            </tr>
                        )}
                    </tbody>
                </table>
            </div>
            <div className="board_footer">
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
                <div className="bottom_wrap fr">
                    <button
                        type="button"
                        className="btn_s mr5"
                        title="삭제 버튼"
                        onClick={onClickDelHandler}
                    >
                        삭제
                    </button>
                    <button
                        type="button"
                        className="btn_s"
                        title="등록 버튼"
                        onClick={onClickRegViewHandler}
                    >
                        등록
                    </button>
                </div>
            </div>
        </>
    );
}

export default AdminMbrList;
