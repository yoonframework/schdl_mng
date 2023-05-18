import { useEffect, useRef, useState } from "react";
import { useNavigate } from "react-router-dom";

import Pagination from "schdl_mng/common/admin/Pagination";

import URL from "context/url";
import CmmFnc from "context/common.js";

// 휴가 생성 목록 조회
const fncVcatnList = async (searchData = {}) => {
    return await CmmFnc.fncRequestAxios(
        "POST",
        "/admin/vcatnCrt/selectVcatnCrtList.do",
        searchData
    ).then((result) => {
        if (result.data.sttus === "success") {
            return result.data.result;
        }
    });
};

/**
 * 휴가 생성 목록 Component
 * @returns
 */
function AdminVcatnCrtList() {
    // 페이지 이동 Hook
    const navigate = useNavigate();
    // 휴가 생성 목록 Component 관리 State
    const [listState, setListState] = useState({
        vcatnList: [],
        isAllChk: false,
        checkField: new Set(),
        form: {
            deptId: "0",
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
            deptList: [],
        },
    });
    // 휴가 생성 목록 Component 관리 State 내 Key 값을 변수로 지정
    const { vcatnList, isAllChk, checkField, form, pagination, code } =
        listState;
    // Form 내 Key 값을 변수로 지정(검색)
    const { deptId, searchCondition, searchKeyword } = form;
    // pagination 내 Key 값을 변수로 지정(페이지 정보)
    const { totalRecordCount, currentPageNo, recordCountPerPage } = pagination;
    // code의 Key 값을 변수로 지정
    const { deptList } = code;
    // Html Tag Button 참고 Hook
    const btnSearch = useRef(null);

    // 등록된 휴가 생성 정보 목록으로 조회
    useEffect(() => {
        const loadData = async () => {
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
                            deptList: data.deptList,
                        },
                    };
                });
            }
        };
        loadData();
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

    // All Checkbox Change Event
    const onAllCheckChangeHandler = (e) => {
        const { checked } = e.target;
        if (checked) {
            setListState((prevState) => {
                return {
                    ...prevState,
                    isAllChk: true,
                    checkField: new Set(
                        vcatnList.map((item) => item.vcatnCrtMngId)
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

    // Checkbox Change Event
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
                isAllChk: checkField.size === vcatnList.length,
                checkField: checkField,
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
                        deptList: data.deptList,
                    },
                };
            });
        }
    };

    // 검색란 입력 후 Enter 키 입력 시 이벤트 처리
    const onKeyPress = (e) => {
        if (e.key === "Enter") {
            btnSearch.current.click();
        }
    };

    // 휴가 생성 정보 등록 화면으로 이동
    const onClickRegViewHandler = (e) => {
        navigate(URL.REG);
    };

    // 휴가 생성 정보 일괄 등록 화면으로 이동
    const onClickBndeRegViewHandler = (e) => {
        navigate(URL.BNDE_REG);
    };

    // 휴가 생성 정보 삭제 처리
    const onClickDelHandler = async (e) => {
        if (checkField.size > 0) {
            if (window.confirm("삭제하시겠습니까?")) {
                const param = {
                    vcatnCrtList: [...checkField].map((item) => {
                        return { vcatnCrtMngId: item };
                    }),
                };

                await CmmFnc.fncRequestAxios(
                    "POST",
                    "/admin/vcatnCrt/deleteVcatnCrt.do",
                    param
                );

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
                        };
                    });
                }
            }
        } else {
            alert("선택된 항목이 없습니다.");
        }
    };

    // 휴가 생성 정보 목록 페이지 정보 변경 이벤트
    const onClickPageMoveHandler = async (pageIndex) => {
        const data = await fncVcatnList({ ...form, pageIndex: pageIndex });
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
                        deptList: data.deptList,
                    },
                };
            });
        }
    };

    return (
        <>
            <div className="board_header">
                <h3>휴가생성관리 목록</h3>
                <div
                    className="search_box"
                    title="이 레이아웃은 하단 정보를 대한 검색 정보로 구성되어 있습니다."
                >
                    <select
                        name="deptId"
                        id="deptId"
                        className="select fl"
                        title="부서 선택"
                        defaultValue={deptId}
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
                        className="select fl width180"
                        title="조회조건 선택"
                        defaultValue={searchCondition}
                        onChange={onChangeHandler}
                    >
                        <option value="99">전체(이름 또는 생성년도)</option>
                        <option value="0">이름</option>
                        <option value="1">생성년도</option>
                    </select>
                    <input
                        className="inputText"
                        name="searchKeyword"
                        type="text"
                        size="35"
                        placeholder="이름 또는 생성년도를 입력하세요"
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
                        총 : <span>{totalRecordCount}</span>
                        건, 쪽번호 :<span>{currentPageNo}</span>/
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
                    summary="휴가생성관리 내역에 대한 목록을 출력합니다."
                >
                    <caption>휴가생성관리 목록</caption>
                    <colgroup>
                        <col style={{ width: "5%" }} />
                        <col style={{ width: "5%" }} />
                        <col style={{ width: "30%" }} />
                        <col style={{ width: "30%" }} />
                        <col style={{ width: "30%" }} />
                        <col style={{ width: "30%" }} />
                        <col style={{ width: "30%" }} />
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
                            <th className="board_th_link">이름</th>
                            <th>부서</th>
                            <th>직위</th>
                            <th>생성년도</th>
                            <th>휴가일수</th>
                        </tr>
                    </thead>
                    <tbody className="ov">
                        {vcatnList?.map((item, index) => {
                            return (
                                <tr key={index}>
                                    <td>
                                        <input
                                            name="checkField"
                                            id={`checkField_${index}`}
                                            title={`checkField_${index}`}
                                            type="checkbox"
                                            value={item.vcatnCrtMngId}
                                            checked={checkField.has(
                                                item.vcatnCrtMngId
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
                                    <td>{item.vcatnCrtrNm}</td>
                                    <td>{item.mberManageVO.deptNm}</td>
                                    <td>{item.mberManageVO.ofcpsNm}</td>
                                    <td>{item.vcatnCrtYr}</td>
                                    <td>{item.vcatnCnt}</td>
                                </tr>
                            );
                        })}
                        {vcatnList?.length === 0 && (
                            <tr>
                                <td colSpan="7">
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
                        className="btn_s mr5"
                        title="등록 버튼"
                        onClick={onClickRegViewHandler}
                    >
                        등록
                    </button>
                    <button
                        type="button"
                        className="btn_s"
                        title="일괄 등록 버튼"
                        onClick={onClickBndeRegViewHandler}
                    >
                        일괄등록
                    </button>
                </div>
            </div>
        </>
    );
}

export default AdminVcatnCrtList;
