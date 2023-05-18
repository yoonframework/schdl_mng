import { Fragment } from "react";
import Pagination from "schdl_mng/common/user/Pagination";
import moment from "moment";

/**
 * 일정관리 목록
 * @param {*} currentViewInfo 모달 정보 변경 setState
 * @param {*} onClickPageMoveHandler 창 크기 state
 * @param {*} onClickSchdlItem 창 크기 state
 * @returns
 */
function SchdlList({
    currentViewInfo,
    onClickPageMoveHandler,
    onClickSchdlItem,
}) {
    // 일정 관리 state 내 key 를 변수로 사용
    const { schdlList, pagination } = currentViewInfo;
    // 일정 관리 state 내 pagination의 key 를 변수로 사용
    const { totalRecordCount } = pagination;

    return (
        <>
            <div className="sorting_wrap">
                <div className="total">
                    전체<em className="count">{totalRecordCount}</em>건
                </div>
            </div>
            <ul className="recruilt_list">
                {/* 반복문 실행 */}
                {schdlList?.map((item, index) => (
                    <li key={`schdl_${index}`}>
                        <a
                            href="{() => false}"
                            onClick={(e) => {
                                e.preventDefault();
                                onClickSchdlItem(item);
                            }}
                        >
                            <div className="company_info_wrap">
                                <div className="company">{item.deptNm}</div>
                                <div className="company_info">
                                    <strong>
                                        {item.typeNm}[{item.aplcntNm}]
                                    </strong>
                                    <ul>
                                        <li>
                                            <em>일수</em>
                                            {item.dayCnt}
                                        </li>
                                        {item.typeCd === "bsrp" && (
                                            <>
                                                <li>
                                                    <em>제목</em>
                                                    {item.bsrpTtl}
                                                </li>
                                                <li>
                                                    <em>장소</em>
                                                    {item.bsrpPlaceNm}
                                                </li>
                                                <li>
                                                    <em>기간</em>
                                                    {`${moment(
                                                        item.startDt,
                                                        "YYYYMMDDHHmm"
                                                    ).format(
                                                        "YYYY-MM-DD HH:mm"
                                                    )} ~ ${moment(
                                                        item.endDt,
                                                        "YYYYMMDDHHmm"
                                                    ).format(
                                                        "YYYY-MM-DD HH:mm"
                                                    )}`}
                                                </li>
                                            </>
                                        )}
                                        {item.typeCd === "vcatn" && (
                                            <>
                                                <li>
                                                    <em>구분</em>
                                                    {item.vcatnSeNm}
                                                </li>
                                                <li>
                                                    <em>요약</em>
                                                    {item.content}
                                                </li>
                                                <li>
                                                    <em>기간</em>
                                                    {`${moment(
                                                        item.startDt,
                                                        "YYYYMMDD"
                                                    ).format(
                                                        "YYYY-MM-DD"
                                                    )} ~ ${moment(
                                                        item.endDt,
                                                        "YYYYMMDD"
                                                    ).format("YYYY-MM-DD")}`}
                                                </li>
                                            </>
                                        )}
                                    </ul>
                                    {item.typeCd === "bsrp" &&
                                        !!item.bsrpCncdntList &&
                                        Object.values(item.bsrpCncdntList)
                                            .length > 0 && (
                                            <ul>
                                                <li>
                                                    <em>동승자</em>
                                                    {Object.values(
                                                        item.bsrpCncdntList
                                                    )
                                                        .sort(
                                                            (
                                                                target,
                                                                object
                                                            ) => {
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
                                                            }
                                                        )
                                                        .map(
                                                            (
                                                                cncdnt,
                                                                i,
                                                                array
                                                            ) => {
                                                                return (
                                                                    <Fragment
                                                                        key={
                                                                            cncdnt.tgrpnId
                                                                        }
                                                                    >
                                                                        {`${
                                                                            cncdnt.tgrpnNm
                                                                        } ${
                                                                            cncdnt.tgrpnOfcpsNm
                                                                        }${
                                                                            array.length !==
                                                                            i +
                                                                                1
                                                                                ? ", "
                                                                                : ""
                                                                        }`}
                                                                    </Fragment>
                                                                );
                                                            }
                                                        )}
                                                </li>
                                            </ul>
                                        )}
                                </div>
                            </div>
                        </a>
                    </li>
                ))}
                {Object.values(schdlList).length === 0 && (
                    <li className="nothing">
                        <p className="nothing">조회된 내용이 없습니다.</p>
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
        </>
    );
}

export default SchdlList;
