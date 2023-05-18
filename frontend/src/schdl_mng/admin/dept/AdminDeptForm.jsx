import React from "react";

/**
 * 부서 관리 저장 Form Component
 * @param {*} selectedNode       선택된 노드 정보
 * @param {*} setSelectedNode    선택한 노드 정보로 변경 setState
 * @returns
 */
function AdminOrgnztDeptForm({ selectedNode, setSelectedNode }) {
    // 선택한 노드 정보에서 Form 에 해당되는 Key 값을 변수로 지정
    const { deptId, deptNm, deptSn, upDeptId, useYn } = selectedNode.data.form;

    // Input 값 변경 이벤트
    const onChangeHandler = (e) => {
        const { name, value } = e.target;
        setSelectedNode((prevState) => {
            return {
                ...prevState,
                data: {
                    ...prevState.data,
                    form: {
                        ...prevState.data.form,
                        [name]: value,
                    },
                },
            };
        });
    };

    return (
        <div className="body_right">
            <p className="must_text">
                <em>＊</em>는 필수 입력사항입니다.
            </p>
            <table className="table_write">
                <caption>
                    부서정보 - 부서 아이디, 부서 명, 상위부서 아이디, 부서 순번,
                    사용유무로 구성
                </caption>
                <colgroup>
                    <col className="width200" />
                </colgroup>
                <tbody>
                    <tr>
                        <th scope="row" className="must">
                            <label htmlFor="deptId">부서 아이디</label>
                        </th>
                        <td>
                            <ul className="check_auto">
                                <li>{deptId}</li>
                            </ul>
                        </td>
                    </tr>
                    <tr>
                        <th scope="row" className="must">
                            <label htmlFor="deptNm">부서 명</label>
                        </th>
                        <td>
                            <input
                                name="deptNm"
                                type="text"
                                size="10"
                                value={deptNm}
                                maxLength="10"
                                title="부서 명"
                                className="width100p inputText"
                                onChange={onChangeHandler}
                            />
                        </td>
                    </tr>
                    <tr>
                        <th scope="row" className="must">
                            <label htmlFor="deptSn">부서순서</label>
                        </th>
                        <td>
                            <input
                                name="deptSn"
                                type="text"
                                size="10"
                                value={deptSn}
                                maxLength="10"
                                title="부서순서"
                                className="width100p inputText"
                                readOnly="readonly"
                                onChange={onChangeHandler}
                            />
                        </td>
                    </tr>
                    <tr>
                        <th scope="row" className="must">
                            <label htmlFor="upDeptId">상위부서 아이디</label>
                        </th>
                        <td>
                            <input
                                name="upDeptId"
                                type="text"
                                size="10"
                                value={upDeptId}
                                maxLength="10"
                                title="상위부서아이디"
                                className="width100p inputText"
                                readOnly="readonly"
                                onChange={onChangeHandler}
                            />
                        </td>
                    </tr>
                    <tr>
                        <th scope="row">
                            <label htmlFor="useYn">사용유무</label>
                        </th>
                        <td>
                            <ul className="check_auto">
                                <li>
                                    <input
                                        type="radio"
                                        name="useYn"
                                        id="useYn1"
                                        value="Y"
                                        className="checkbox"
                                        checked={useYn === "Y"}
                                        onChange={onChangeHandler}
                                    />
                                    <label
                                        htmlFor="useYn1"
                                        className="input-label radio"
                                    >
                                        사용
                                    </label>
                                </li>
                                <li>
                                    <input
                                        type="radio"
                                        name="useYn"
                                        id="useYn2"
                                        value="N"
                                        className="checkbox"
                                        checked={useYn === "N"}
                                        onChange={onChangeHandler}
                                    />
                                    <label
                                        htmlFor="useYn2"
                                        className="input-label radio"
                                    >
                                        미사용
                                    </label>
                                </li>
                            </ul>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
    );
}

export default AdminOrgnztDeptForm;
