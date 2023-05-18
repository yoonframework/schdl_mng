import { Fragment, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";

import URL from "context/url";
import CmmFnc from "context/common.js";

import DatePickerComponert from "schdl_mng/user/utils/DatePickerComponert";
import AdminMbrSrchDeptModal from "schdl_mng/admin/mbr/AdminMbrSrchDeptModal";
import InputMask from "react-input-mask";

/**
 * 직원 정보 수정 Component
 * @param {*} setModalInfo  모달 정보 변경 이벤트 setState
 * @returns
 */
function AdminMbrMdfcn({ setModalInfo }) {
    // URL 위치 및 상태 정보 Hook
    const location = useLocation();
    // 페이지 정보 Hook
    const navigate = useNavigate();
    // 직원 목록에서 수정 페이지로 이동할 때 전달받은 state 값 (선택한 회원정보)
    const { mbr } = location.state;
    // 직원 목록에서 수정 페이지로 이동할 때 전달받은 state 값 (코드 정보)
    const { authorList, sttusList, ofcpsList, jbttlList, workStleList } =
        location.state.code;
    // 직원 정보 Form State
    const [form, setForm] = useState({
        mberId: "",
        jbttlId: "",
        ofcpsId: "",
        password: "",
        password2: "",
        brdt: "",
        jncmpYmd: "",
        mberNm: "",
        mberSttus: "",
        authorCode: "",
        deptId: "",
        deptNm: "",
        moblphonNo: "",
        workStle: "",
        crqfcList: [],
        ...mbr,
    });
    // Form 내 Key 값을 변수로 지정
    const {
        uniqId,
        userTy,
        mberId,
        jbttlId,
        ofcpsId,
        password,
        password2,
        brdt,
        jncmpYmd,
        mberNm,
        mberSttus,
        authorCode,
        deptId,
        deptNm,
        moblphonNo,
        workStle,
        crqfcList,
    } = form;

    // 부서 검색 Modal 함수
    const onClickSrchDept = (e) => {
        setModalInfo((prevState) => {
            return {
                ...prevState,
                showModal: true,
                isLoadingUI: false,
                width: 400,
                height: 600,
                title: "부서검색",
                content: (
                    <AdminMbrSrchDeptModal
                        callback={onModalActionCallback}
                        deptInfo={{ id: deptId, text: deptNm }}
                    />
                ),
            };
        });
    };

    // modal callback 함수
    const onModalActionCallback = (element) => {
        if (element.action === "deptInfo") {
            setForm((prevState) => {
                return {
                    ...prevState,
                    deptId: element.data.id,
                    deptNm: element.data.text,
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

    // input ChangeHandler
    const onChangeHandler = (e) => {
        const { name, value } = e.target;
        setForm((prevState) => ({
            ...prevState,
            [name]: value,
        }));
    };

    // 권한 체크 클릭 시
    const onCheckChangeHandler = (e) => {
        const { name, value, checked } = e.target;
        if (checked) {
            // 체크
            setForm((prevState) => {
                if (prevState[name] !== "") {
                    const arr = Array(prevState[name]);
                    arr.push(value);
                    return {
                        ...prevState,
                        [name]: arr.join(","),
                    };
                } else {
                    return {
                        ...prevState,
                        [name]: value,
                    };
                }
            });
        } else {
            // 체크 해제
            setForm((prevState) => {
                const arr = String(prevState[name]).split(",");
                return {
                    ...prevState,
                    [name]: arr.filter((item) => item !== value).join(","),
                };
            });
        }
    };

    // 목록버튼 클릭 시
    const onClickList = (e) => {
        navigate(`${URL.ROOT + URL.ADMIN}/${URL.ADMIN_MBER_MANAGE}`, {});
    };

    // 삭제버튼 클릭 시
    const onClickDelete = async (e) => {
        if (window.confirm("삭제하시겠습니까?")) {
            await CmmFnc.fncRequestAxios(
                "POST",
                "/uss/umt/deleteMbr.do",
                userTy + ":" + uniqId
            ).then((result) => {
                if (result.data.sttus === "success") {
                    navigate(
                        `${URL.ROOT + URL.ADMIN}/${URL.ADMIN_MBER_MANAGE}`,
                        {}
                    );
                }
            });
        }
    };

    // 등록버튼 클륵 시
    const onClickSave = async (e) => {
        if (window.confirm("저장하시겠습니까?")) {
            if (mberId === "") {
                alert("아이디를 입력하세요.");
                return;
            }
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
            if (jncmpYmd === "") {
                alert("입사일을 입력하세요.");
                return;
            }
            if (jbttlId === "") {
                alert("직책을 선택하세요.");
                return;
            }
            if (ofcpsId === "") {
                alert("직위를 선택하세요.");
                return;
            }
            if (deptId === "") {
                alert("부서를 선택하세요.");
                return;
            }
            if (mberSttus === "") {
                alert("직원상태를 선택하세요.");
                return;
            }
            if (authorCode === "") {
                alert("직원권한을 선택하세요.");
                return;
            }
            if (
                (password !== "" || password2 !== "") &&
                password !== password2
            ) {
                alert("패스워드가 일치하지 않습니다.");
                return;
            }

            if (crqfcList.length > 0) {
                let listChk = false;
                crqfcList.forEach((item) => {
                    if (item.crqfcNm === "" || item.crqfcIssuinst === "") {
                        alert(
                            "자격증 명과 자격증 발급기관 중 입력되지 않은 항목이 있습니다."
                        );
                        listChk = true;
                        return;
                    }
                });

                if (listChk) {
                    return;
                }
            }

            const param = {
                ...form,
                crqfcList: crqfcList.filter(
                    (item) => item.crqfcNm !== "" && item.crqfcIssuinst !== ""
                ),
            };

            await CmmFnc.fncRequestAxios(
                "POST",
                "/uss/umt/updateMbr.do",
                param
            ).then((result) => {
                if (result.data.sttus === "success") {
                    navigate(
                        `${URL.ROOT + URL.ADMIN}/${URL.ADMIN_MBER_MANAGE}`,
                        {}
                    );
                }
            });
        }
    };

    // 자격증 관리 추가버튼 클릭 시
    const onClickCrqfcAdit = (e) => {
        setForm((prevState) => ({
            ...prevState,
            crqfcList: [
                ...prevState.crqfcList,
                {
                    crqfcId: "",
                    crqfcNm: "",
                    crqfcIssuinst: "",
                    crqfcUpdtYmd: "",
                    crqfcIssuYmd: "",
                    rm: "",
                },
            ],
        }));
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

    // DatePicker 변경 이벤트
    const onChangeDatePicker = (name, value) => {
        setForm((prevState) => {
            return {
                ...prevState,
                [name]: value,
            };
        });
    };

    // 자격증 관리 DatePicker Handler
    const onChangeCrqfcDatePicker = (name, value, index) => {
        crqfcList[index][name] = value;
        setForm((prevState) => ({
            ...prevState,
            crqfcList: crqfcList,
        }));
    };

    // 자격증 관리 Input ChangeHandler
    const onChangeCrqfcHandler = (item, e) => {
        const { index } = item;
        const { name, value } = e.target;
        crqfcList[index][name] = value;
        setForm((prevState) => ({
            ...prevState,
            crqfcList: crqfcList,
        }));
    };

    return (
        <>
            <div className="board_header">
                <h3>직원관리 수정</h3>
            </div>
            <div className="board_body">
                <h4>직원 기본 정보</h4>
                <p className="must_text">
                    <em>＊</em>는 필수 입력사항입니다.
                </p>
                <table
                    className="table_write"
                    summary="직원관리의 내역에 대한 목록을 출력합니다."
                >
                    <caption>
                        직원등록 - 아이디, 직위, 비밀번호, 비밀번호 확인,
                        이름으로 구성
                    </caption>
                    <colgroup>
                        <col className="width200" />
                        <col />
                    </colgroup>
                    <tbody>
                        <tr>
                            <th scope="row" className="must">
                                <label htmlFor="mberId">아이디</label>
                            </th>
                            <td>
                                <input
                                    type="text"
                                    name="mberId"
                                    id="mberId"
                                    title="아이디 입력"
                                    size="20"
                                    readOnly={true}
                                    value={mberId}
                                    maxLength="20"
                                    className="inputText fl width20p"
                                />
                            </td>
                        </tr>
                        <tr>
                            <th scope="row" className="must">
                                <label htmlFor="mberNm">이름</label>
                            </th>
                            <td>
                                <input
                                    type="text"
                                    name="mberNm"
                                    title="이름 입력"
                                    size="50"
                                    maxLength="60"
                                    className="inputText width20p"
                                    value={mberNm}
                                    onChange={onChangeHandler}
                                />
                            </td>
                        </tr>
                        <tr>
                            <th scope="row">
                                <label htmlFor="password">비밀번호</label>
                            </th>
                            <td>
                                <input
                                    type="password"
                                    id="password"
                                    name="password"
                                    title="비밀번호 입력"
                                    size="50"
                                    maxLength="60"
                                    className="inputText width20p"
                                    value={password}
                                    onChange={onChangeHandler}
                                />
                            </td>
                        </tr>
                        <tr>
                            <th scope="row">
                                <label htmlFor="password2">비밀번호확인</label>
                            </th>
                            <td>
                                <input
                                    type="password"
                                    name="password2"
                                    title="비밀번호확인 입력"
                                    size="50"
                                    maxLength="20"
                                    className="inputText width20p"
                                    value={password2}
                                    onChange={onChangeHandler}
                                />
                            </td>
                        </tr>
                        <tr>
                            <th scope="row" className="must">
                                <label htmlFor="moblphonNo">연락처</label>
                            </th>
                            <td>
                                <InputMask
                                    type="text"
                                    mask={"999-9999-9999"}
                                    value={moblphonNo}
                                    onChange={onChangeHandler}
                                    name="moblphonNo"
                                    title="연락처"
                                    className={"inputText width20p"}
                                    autoComplete="off"
                                />
                            </td>
                        </tr>
                        <tr>
                            <th scope="row" className="must">
                                <label htmlFor="brdt">생년월일</label>
                            </th>
                            <td>
                                <DatePickerComponert
                                    id="brdt"
                                    name="brdt"
                                    className="inputText width100p"
                                    date={brdt}
                                    setDate={(value) =>
                                        onChangeDatePicker("brdt", value)
                                    }
                                    dateFormat="YYYYMMDD"
                                />
                            </td>
                        </tr>
                        <tr>
                            <th scope="row" className="must">
                                <label htmlFor="jncmpYmd">입사일자</label>
                            </th>
                            <td>
                                <DatePickerComponert
                                    id="jncmpYmd"
                                    name="jncmpYmd"
                                    className="inputText width100p"
                                    date={jncmpYmd}
                                    setDate={(value) =>
                                        onChangeDatePicker("jncmpYmd", value)
                                    }
                                    dateFormat="YYYYMMDD"
                                />
                            </td>
                        </tr>
                        <tr>
                            <th scope="row" className="must">
                                <label htmlFor="jbttlId">직책</label>
                            </th>
                            <td>
                                <select
                                    name="jbttlId"
                                    className="width180 select"
                                    id="jbttlId"
                                    title="직책"
                                    value={jbttlId}
                                    onChange={onChangeHandler}
                                >
                                    <option value="">선택</option>
                                    {jbttlList?.map((item, index) => {
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
                            </td>
                        </tr>
                        <tr>
                            <th scope="row" className="must">
                                <label htmlFor="ofcpsId">직위</label>
                            </th>
                            <td>
                                <select
                                    name="ofcpsId"
                                    className="width180 select"
                                    id="ofcpsId"
                                    title="직위"
                                    value={ofcpsId}
                                    onChange={onChangeHandler}
                                >
                                    <option value="">선택</option>
                                    {ofcpsList?.map((item, index) => {
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
                            </td>
                        </tr>
                        <tr>
                            <th scope="row" className="must">
                                <label htmlFor="deptNm">부서</label>
                            </th>
                            <td>
                                <input
                                    type="text"
                                    name="deptNm"
                                    id="deptNm"
                                    title="부서입력 입력"
                                    size="20"
                                    readOnly={true}
                                    value={deptNm}
                                    maxLength="20"
                                    className="inputText fl width20p"
                                />
                                <button
                                    id="btnDeptNm"
                                    className="btn_m fl ml2"
                                    type="button"
                                    onClick={onClickSrchDept}
                                    title="부서 검색"
                                >
                                    부서 검색
                                </button>
                            </td>
                        </tr>
                        <tr>
                            <th scope="row" className="must">
                                <label htmlFor="workStle">근무형태</label>
                            </th>
                            <td>
                                <select
                                    name="workStle"
                                    className="width180 select"
                                    id="workStle"
                                    title="근무형태"
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
                            </td>
                        </tr>
                    </tbody>
                </table>
                <div className="space40"></div>

                <h4>자격증 관리</h4>
                <button
                    type="button"
                    className="btn_m fr mb10"
                    onClick={onClickCrqfcAdit}
                >
                    추가
                </button>
                <table
                    className="table_write"
                    summary="직원관리의 내역에 대한 목록을 출력합니다."
                >
                    <caption>
                        직원등록 - 아이디, 직위, 비밀번호, 비밀번호 확인,
                        이름으로 구성
                    </caption>
                    <colgroup>
                        <col className="width10%" />
                        <col />
                        <col className="width10%" />
                        <col />
                        <col className="width10%" />
                        <col className="width250" />
                        <col className="width10%" />
                        <col className="width250" />
                        <col className="width5%" />
                    </colgroup>
                    <tbody>
                        {crqfcList?.map((item, index) => (
                            <tr key={index}>
                                <th scope="row" className="must">
                                    <label htmlFor="mberId">자격증 명</label>
                                </th>
                                <td>
                                    <input
                                        type="text"
                                        name="crqfcNm"
                                        title="자격증 명 입력"
                                        size="20"
                                        value={item.crqfcNm}
                                        maxLength="20"
                                        className="inputText width100p"
                                        onChange={function (param, e) {
                                            onChangeCrqfcHandler(param, e);
                                        }.bind(this, { index })}
                                    />
                                </td>
                                <th scope="row" className="must">
                                    <label htmlFor="mberId">
                                        자격증 발급기관
                                    </label>
                                </th>
                                <td>
                                    <input
                                        type="text"
                                        name="crqfcIssuinst"
                                        title="자격증 발급기관 입력"
                                        size="20"
                                        value={item.crqfcIssuinst}
                                        maxLength="20"
                                        className="inputText width100p"
                                        onChange={function (param, e) {
                                            onChangeCrqfcHandler(param, e);
                                        }.bind(this, { index })}
                                    />
                                </td>
                                <th scope="row">
                                    <label htmlFor="mberId">
                                        자격증 갱신일자
                                    </label>
                                </th>
                                <td>
                                    <DatePickerComponert
                                        id={`crqfcUpdtYmd_${index}`}
                                        name="crqfcUpdtYmd"
                                        className="inputText width100p"
                                        date={item.crqfcUpdtYmd}
                                        setDate={(value) =>
                                            onChangeCrqfcDatePicker(
                                                "crqfcUpdtYmd",
                                                value,
                                                index
                                            )
                                        }
                                        dateFormat="YYYYMMDD"
                                    />
                                </td>
                                <th scope="row">
                                    <label htmlFor="mberId">
                                        자격증 발급일자
                                    </label>
                                </th>
                                <td>
                                    <DatePickerComponert
                                        id={`crqfcIssuYmd_${index}`}
                                        name="crqfcIssuYmd"
                                        className="inputText width100p"
                                        date={item.crqfcIssuYmd}
                                        setDate={(value) =>
                                            onChangeCrqfcDatePicker(
                                                "crqfcIssuYmd",
                                                value,
                                                index
                                            )
                                        }
                                        dateFormat="YYYYMMDD"
                                    />
                                </td>
                                <td>
                                    <button
                                        type="button"
                                        className="btn_m fr mb10"
                                        onClick={function (param) {
                                            onClickCrqfcDel(param);
                                        }.bind(this, { index })}
                                    >
                                        삭제
                                    </button>
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
                <div className="space40"></div>

                <h4>직원 승인 및 권한 정보</h4>
                <table
                    className="table_write"
                    arguments="직원관리의 내역에 대한 목록을 출력합니다."
                >
                    <caption>
                        권한 및 승인정보 - 직원상태코드, 직원권한 코드로 구성
                    </caption>
                    <colgroup>
                        <col style={{ width: "15%" }} />
                        <col />
                    </colgroup>
                    <tbody>
                        <tr>
                            <th scope="row" className="must">
                                <label htmlFor="mberSttus">직원상태코드</label>
                            </th>
                            <td>
                                <select
                                    name="mberSttus"
                                    className="width180 select"
                                    id="mberSttus"
                                    title="직원상태코드 입력"
                                    value={mberSttus}
                                    onChange={onChangeHandler}
                                >
                                    <option value="">선택</option>
                                    {sttusList?.map((item, index) => {
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
                            </td>
                        </tr>
                        <tr>
                            <th scope="row" className="must">
                                <label htmlFor="mberSttus">직원 권한</label>
                            </th>
                            <td className="memberCode">
                                {authorList?.map((item, index) => {
                                    return (
                                        <Fragment key={index}>
                                            <input
                                                type="checkbox"
                                                name="authorCode"
                                                id={`authorCode_${index}`}
                                                value={item.authorCode}
                                                onChange={onCheckChangeHandler}
                                                checked={authorCode.includes(
                                                    item.authorCode
                                                )}
                                            />
                                            <label
                                                htmlFor={`authorCode_${index}`}
                                                className="mr20"
                                            >
                                                <span></span> {item.authorNm}
                                            </label>
                                        </Fragment>
                                    );
                                })}
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>

            <div className="board_footer">
                <div className="bottom_wrap fr">
                    <button
                        className="btn_s mr5"
                        type="button"
                        title="목록 버튼"
                        onClick={onClickList}
                    >
                        목록
                    </button>
                    <button
                        className="btn_s mr5"
                        type="button"
                        title="삭제 버튼"
                        onClick={onClickDelete}
                    >
                        삭제
                    </button>
                    <button
                        className="btn_s"
                        type="button"
                        title="저장 버튼"
                        onClick={onClickSave}
                    >
                        저장
                    </button>
                </div>
            </div>
        </>
    );
}

export default AdminMbrMdfcn;
