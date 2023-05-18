import { connect } from "react-redux";
import { useNavigate } from "react-router-dom";
import URL from "context/url";
import moment from "moment";

/**
 * 휴가 상세
 * @param {*} loginVO      store에 저장된 loginVO 로드
 * @param {*} item         선택된 휴가 항목
 * @param {*} callback     모달 콜백
 * @returns
 */
function VcatnDtlModal({ loginVO, item, callback }) {
    // 페이지 이동 Hook
    const navigate = useNavigate();
    // LoginVO 내 key값을 변수화
    const { authorCode, uniqId } = loginVO;

    const startDt =
        item.vcatnSe === "03"
            ? moment(item.startDt, "YYYYMMDDHHmmss").hour(14).toDate()
            : moment(item.startDt, "YYYYMMDDHHmmss").hour(9).toDate();
    const todayDt = moment().toDate();

    const dayMapperCounting =
        item.vcatnSe === "02" || item.vcatnSe === "03" ? 0.5 : 1;

    // 수정 버튼 이벤트
    const onClickVcatnMdfcnView = () => {
        callback({ action: "close" });
        navigate(`${URL.ROOT + URL.SCHDL}/${URL.SCHDL_VCATN}/${URL.MDFCN}`, {
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
                                휴가자 성명
                            </strong>
                            <div className="input_wrap calc150">
                                {item.aplcntNm}
                            </div>
                        </li>
                        <li>
                            <strong className="board_label width150">
                                휴가자 직위
                            </strong>
                            <div className="input_wrap calc150">
                                {item.ofcpsNm}
                            </div>
                        </li>
                        <li>
                            <strong className="board_label width150">
                                휴가자 소속
                            </strong>
                            <div className="input_wrap calc150">
                                {item.deptNm}
                            </div>
                        </li>
                        <li>
                            <strong className="board_label width150">
                                시작 일자
                            </strong>
                            <div className="input_wrap calc150">
                                {moment(item.startDt, "YYYYMMDDHHmmss").format(
                                    "YYYY-MM-DD"
                                )}
                            </div>
                        </li>
                        <li>
                            <strong className="board_label width150">
                                종료 일자
                            </strong>
                            <div className="input_wrap calc150">
                                {moment(item.endDt, "YYYYMMDDHHmmss").format(
                                    "YYYY-MM-DD"
                                )}
                            </div>
                        </li>
                        <li>
                            <strong className="board_label width150">
                                사용 일수
                            </strong>
                            <div className="input_wrap calc150">
                                {item.dayCnt * dayMapperCounting}
                            </div>
                        </li>
                        <li>
                            <strong className="board_label width150">
                                휴가 구분
                            </strong>
                            <div className="input_wrap calc150">
                                {item.vcatnSeNm}
                            </div>
                        </li>
                        <li>
                            <strong className="board_label width150">
                                휴가 내용
                            </strong>
                            <div className="input_wrap calc150">
                                {item.content}
                            </div>
                        </li>
                    </ul>
                </div>
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
                            onClick={onClickVcatnMdfcnView}
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

export default connect(mapStateToProps, null)(VcatnDtlModal);
