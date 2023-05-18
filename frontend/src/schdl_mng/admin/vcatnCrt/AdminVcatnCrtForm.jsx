import React from "react";
import InputMask from "react-input-mask";

/**
 * 휴가 생성 등록 Component
 * @param {*} selectedNode      선택된 노드 state
 * @param {*} setSelectedNode   선택한 노드 정보로 변경 setState
 * @returns
 */
function AdminVcatnCrtForm({ selectedNode, setSelectedNode }) {
    // 선택된 노드 정보의 Form
    const { vcatnCrtrNm, vcatnCrtYr, vcatnCnt } = selectedNode.data.form;
    // Input value Change Event
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
                    휴가생성관리 - 휴가생성년도, 휴가 생성자 아이디, 휴가 일수로
                    구성
                </caption>
                <colgroup>
                    <col className="width200" />
                </colgroup>
                <tbody>
                    <tr>
                        <th scope="row" className="must">
                            <label htmlFor="vcatnCnt">휴가 생성 일수</label>
                        </th>
                        <td>
                            <input
                                type="text"
                                className="width100p inputText"
                                id="vcatnCnt"
                                name="vcatnCnt"
                                size="2"
                                maxLength="2"
                                title="휴가 생성 일수"
                                value={vcatnCnt}
                                onChange={onChangeHandler}
                            />
                        </td>
                    </tr>
                    <tr>
                        <th scope="row" className="must">
                            <label>이름</label>
                        </th>
                        <td>
                            <ul className="check_auto">
                                <li>{vcatnCrtrNm}</li>
                            </ul>
                        </td>
                    </tr>
                    <tr>
                        <th scope="row" className="must">
                            <label htmlFor="vcatnCrtYr">생성년도</label>
                        </th>
                        <td>
                            <InputMask
                                type="text"
                                mask={"9999"}
                                value={vcatnCrtYr}
                                onChange={onChangeHandler}
                                id={"vcatnCrtYr"}
                                name={"vcatnCrtYr"}
                                className={"width100p inputText"}
                                autoComplete="off"
                            />
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
    );
}

export default AdminVcatnCrtForm;
