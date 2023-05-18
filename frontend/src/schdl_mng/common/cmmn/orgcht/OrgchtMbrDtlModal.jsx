/**
 * 조직도 - 사용자 상세 모달
 * @param {*} callback  콜백 함수
 * @param {*} mbrInfo   사용자 정보
 * @returns
 */
function OrgchtMbrDtlModal({ callback, mbrInfo }) {
    return (
        <>
            <div className="modal_content">
                <div className="board_view row_view">
                    <ul>
                        <li>
                            <strong className="board_label width150">
                                직원 아이디
                            </strong>
                            <div className="input_wrap calc150">
                                {mbrInfo.mberId}
                            </div>
                        </li>
                        <li>
                            <strong className="board_label width150">
                                직원 이름
                            </strong>
                            <div className="input_wrap calc150">
                                {mbrInfo.mberNm}
                            </div>
                        </li>
                        <li>
                            <strong className="board_label width150">
                                직책
                            </strong>
                            <div className="input_wrap calc150">
                                {mbrInfo.jbttlNm}
                            </div>
                        </li>
                        <li>
                            <strong className="board_label width150">
                                직위
                            </strong>
                            <div className="input_wrap calc150">
                                {mbrInfo.ofcpsNm}
                            </div>
                        </li>
                        <li>
                            <strong className="board_label width150">
                                부서
                            </strong>
                            <div className="input_wrap calc150">
                                {mbrInfo.deptNm}
                            </div>
                        </li>
                        <li>
                            <strong className="board_label width150">
                                연락처
                            </strong>
                            <div className="input_wrap calc150">
                                {mbrInfo.moblphonNo}
                            </div>
                        </li>
                        <li>
                            <strong className="board_label width150">
                                생년월일
                            </strong>
                            <div className="input_wrap calc150">
                                {mbrInfo.brdt !== undefined
                                    ? `${String(mbrInfo.brdt).substring(
                                          0,
                                          4
                                      )}년 ${String(mbrInfo.brdt).substring(
                                          4,
                                          6
                                      )}월 ${String(mbrInfo.brdt).substring(
                                          6,
                                          8
                                      )}일`
                                    : "-"}
                            </div>
                        </li>
                        <li>
                            <strong className="board_label width150">
                                입사일자
                            </strong>
                            <div className="input_wrap calc150">
                                {mbrInfo.jncmpYmd !== undefined
                                    ? `${String(mbrInfo.jncmpYmd).substring(
                                          0,
                                          4
                                      )}년 ${String(mbrInfo.jncmpYmd).substring(
                                          4,
                                          6
                                      )}월 ${String(mbrInfo.jncmpYmd).substring(
                                          6,
                                          8
                                      )}일`
                                    : "-"}
                            </div>
                        </li>
                    </ul>
                </div>
                <div className="space30"></div>
                <strong className="title">자격증 정보</strong>
                <div className="list_wrap" style={{ overflowX: "auto" }}>
                    <table className="table_list">
                        <caption>자격증 정보</caption>
                        <colgroup>
                            <col className="width80" />
                            <col className="width150" />
                            <col className="width150" />
                            <col className="width150" />
                            <col className="width150" />
                        </colgroup>
                        <thead>
                            <tr>
                                <th scope="col">번호</th>
                                <th scope="col">명칭</th>
                                <th scope="col">발급기관</th>
                                <th scope="col">발급일자</th>
                                <th scope="col">갱신일자</th>
                            </tr>
                        </thead>
                        <tbody>
                            {mbrInfo?.crqfcList !== undefined &&
                                Object.values(mbrInfo?.crqfcList).map(
                                    (item, index) => (
                                        <tr key={index}>
                                            <td>{index + 1}</td>
                                            <td>{item.crqfcNm}</td>
                                            <td>{item.crqfcIssuinst}</td>
                                            <td>
                                                {item.crqfcIssuYmd !==
                                                    undefined &&
                                                item.crqfcIssuYmd !== ""
                                                    ? `${String(
                                                          item.crqfcIssuYmd
                                                      ).substring(
                                                          0,
                                                          4
                                                      )}년 ${String(
                                                          item.crqfcIssuYmd
                                                      ).substring(
                                                          4,
                                                          6
                                                      )}월 ${String(
                                                          item.crqfcIssuYmd
                                                      ).substring(6, 8)}일`
                                                    : "-"}
                                            </td>
                                            <td>
                                                {item.crqfcUpdtYmd !==
                                                    undefined &&
                                                item.crqfcUpdtYmd !== ""
                                                    ? `${String(
                                                          item.crqfcUpdtYmd
                                                      ).substring(
                                                          0,
                                                          4
                                                      )}년 ${String(
                                                          item.crqfcUpdtYmd
                                                      ).substring(
                                                          4,
                                                          6
                                                      )}월 ${String(
                                                          item.crqfcUpdtYmd
                                                      ).substring(6, 8)}일`
                                                    : "-"}
                                            </td>
                                        </tr>
                                    )
                                )}
                            {Object.values(mbrInfo?.crqfcList).length === 0 && (
                                <tr>
                                    <td colSpan={5}>
                                        등록된 자격증 정보가 없습니다.
                                    </td>
                                </tr>
                            )}
                        </tbody>
                    </table>
                </div>
            </div>
            <div className="modal_bottom">
                <div className="fr">
                    <button
                        type="button"
                        className="btn_s close bg_basic"
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

export default OrgchtMbrDtlModal;
