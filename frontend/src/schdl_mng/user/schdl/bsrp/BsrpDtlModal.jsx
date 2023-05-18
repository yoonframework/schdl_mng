import { connect } from "react-redux";
import { useNavigate } from "react-router-dom";
import URL from "context/url";
import moment from "moment";
/**
 *
 * @param {*} loginVO      store에 저장된 loginVO 로드
 * @param {*} item         선택된 휴가 항목
 * @param {*} callback   모달 콜백
 * @returns
 */
function BsrpDtlModal({ loginVO, item, callback }) {
    // 페이지 이동 Hook
    const navigate = useNavigate();
    // LoginVO 내 key값을 변수화
    const { authorCode, uniqId } = loginVO;

    const startDt = moment(item.startDt, "YYYYMMDDHHmmss").toDate();
    const todayDt = moment().toDate();

    // 수정 버튼 이벤트
    const onClickBsrpMdfcnView = () => {
        callback({ action: "close" });
        navigate(`${URL.ROOT + URL.SCHDL}/${URL.SCHDL_BSRP}/${URL.MDFCN}`, {
            state: { schdlInfo: item },
        });
    };

    return (
        <>
            <div className="modal_content">
                <div className="board_view row_view">
                    <ul>
                        <li>
                            <strong className="board_label width150">
                                출장자 성명
                            </strong>
                            <div className="input_wrap calc150">
                                {item.aplcntNm}
                            </div>
                        </li>
                        <li>
                            <strong className="board_label width150">
                                출장자 직위
                            </strong>
                            <div className="input_wrap calc150">
                                {item.ofcpsNm}
                            </div>
                        </li>
                        <li>
                            <strong className="board_label width150">
                                출장자 소속
                            </strong>
                            <div className="input_wrap calc150">
                                {item.deptNm}
                            </div>
                        </li>
                        <li>
                            <strong className="board_label width150">
                                시작 일시
                            </strong>
                            <div className="input_wrap calc150">
                                {moment(item.startDt, "YYYYMMDDHHmmss").format(
                                    "YYYY-MM-DD HH:mm"
                                )}
                            </div>
                        </li>
                        <li>
                            <strong className="board_label width150">
                                종료 일시
                            </strong>
                            <div className="input_wrap calc150">
                                {moment(item.endDt, "YYYYMMDDHHmmss").format(
                                    "YYYY-MM-DD HH:mm"
                                )}
                            </div>
                        </li>
                        <li>
                            <strong className="board_label width150">
                                출장 제목
                            </strong>
                            <div className="input_wrap calc150">
                                {item.bsrpTtl}
                            </div>
                        </li>
                        <li>
                            <strong className="board_label width150">
                                출장 장소
                            </strong>
                            <div className="input_wrap calc150">
                                {item.bsrpPlaceNm}
                            </div>
                        </li>
                        <li>
                            <strong className="board_label width150">
                                출장 내용
                            </strong>
                            <div className="input_wrap calc150">
                                {item.content}
                            </div>
                        </li>
                    </ul>
                </div>
                {!!item.bsrpCncdntList &&
                    Object.values(item.bsrpCncdntList).length > 0 && (
                        <>
                            <div className="space30"></div>
                            <strong className="title">출장 동행 정보</strong>
                            <div className="list_wrap">
                                <table className="table_list">
                                    <caption>출장 동행 정보</caption>
                                    <colgroup>
                                        <col className="widthauto" />
                                        <col className="widthauto" />
                                        <col className="widthauto" />
                                    </colgroup>
                                    <thead>
                                        <tr>
                                            <th scope="col">이름</th>
                                            <th scope="col">직위</th>
                                            <th scope="col">소속</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        {Object.values(item.bsrpCncdntList)
                                            .sort((target, object) => {
                                                if (
                                                    target.tgrpnOfcpsId >
                                                    object.tgrpnOfcpsId
                                                ) {
                                                    return 1;
                                                }
                                                if (
                                                    target.tgrpnOfcpsId <
                                                    object.tgrpnOfcpsId
                                                ) {
                                                    return -1;
                                                }
                                                return 0;
                                            })
                                            .map((cncdnt, index) => {
                                                return (
                                                    <tr key={index}>
                                                        <td>
                                                            {cncdnt.tgrpnNm}
                                                        </td>
                                                        <td>
                                                            {
                                                                cncdnt.tgrpnOfcpsNm
                                                            }
                                                        </td>
                                                        <td>
                                                            {cncdnt.tgrpnDeptNm}
                                                        </td>
                                                    </tr>
                                                );
                                            })}
                                    </tbody>
                                </table>
                            </div>
                        </>
                    )}
            </div>
            <div className="modal_bottom">
                <div className="fr">
                    {(authorCode === "ROLE_ADMIN" ||
                        (uniqId === item.aplcntId &&
                            startDt.getTime() > todayDt.getTime())) && (
                        <button
                            type="button"
                            className="btn_s bg_navy mr5"
                            title="수정"
                            onClick={onClickBsrpMdfcnView}
                        >
                            수정
                        </button>
                    )}
                    <button
                        type="button"
                        className="btn_s close"
                        title="닫기"
                        onClick={function (elem, e) {
                            e.preventDefault();
                            callback(elem);
                        }.bind(this, { action: "close" })}
                    >
                        닫기
                    </button>
                </div>
            </div>
        </>
    );
}

function mapStateToProps(state) {
    const session = state.reducer;
    return {
        loginVO: session.loginVO,
    };
}

export default connect(mapStateToProps, null)(BsrpDtlModal);
