import React, { memo } from "react";
import moment from "moment";
/**
 * 달력 일자 부분
 * @param {*} year             선택된 년도
 * @param {*} month            선택된 달
 * @param {*} dates            이달 일자 목록
 * @param {*} firstDateIndex   이달 첫 일자 인덱스
 * @param {*} lastDateIndex    이달 마지막 일자 인덱스
 * @param {*} prevDates        이전 달 일자 목록
 * @param {*} nextDates        다음 달 일자 목록
 * @param {*} schdlData        일정 데이터 목록
 * @param {*} onClickSchdlItem 항목 클릭 이벤트
 * @returns
 */
function SchdlCalendarDom({
    year,
    month,
    dates,
    firstDateIndex,
    lastDateIndex,
    prevDates,
    nextDates,
    schdlData,
    onClickSchdlItem,
}) {
    const searchDate = year + String(month).padStart(2, "0");

    const today = new Date();

    const viewDate = moment(year, "YYYY")
        .month(month - 1)
        .startOf("month")
        .toDate();
    const viewYear = viewDate.getFullYear();
    const viewMonth = viewDate.getMonth() + 1;

    let dom = [];
    let dayList = [];
    let keyIndex = 0;

    dates?.forEach((date, index) => {
        let condition =
            index >= firstDateIndex && index < lastDateIndex + 1 ? "" : "empty";
        let yearMonth = searchDate;

        // 이전 달
        if (prevDates.length > index) {
            const prevMonth = new Date(
                viewDate.getFullYear(),
                viewDate.getMonth(),
                0
            );

            yearMonth =
                prevMonth.getFullYear() +
                String(prevMonth.getMonth() + 1).padStart(2, "0");
        }
        // 다음 달
        if (index >= dates.length - nextDates.length) {
            const nextMonth = new Date(
                viewDate.getFullYear(),
                viewDate.getMonth() + 2,
                0
            );
            yearMonth =
                nextMonth.getFullYear() +
                String(nextMonth.getMonth() + 1).padStart(2, "0");
        }

        let dataDay = yearMonth + String(date).padStart(2, "0");

        if (
            today.getFullYear() === viewYear &&
            today.getMonth() + 1 === viewMonth &&
            today.getDate() === date &&
            condition === ""
        ) {
            condition = "today";
        }

        dayList.push({
            dataDay: dataDay,
            condition: condition,
            date: date,
            schdlData: schdlData
                .filter((item) => {
                    const typeDateDay = moment(dataDay, "YYYYMMDDHHmmss");
                    const typeDateStartDt = moment(
                        item.startDt,
                        "YYYYMMDDHHmmss"
                    );
                    const typeDateEndDt = moment(item.endDt, "YYYYMMDDHHmmss");

                    return (
                        typeDateDay.isBetween(
                            typeDateStartDt.format("YYYYMMDD"),
                            typeDateEndDt.format("YYYYMMDD"),
                            undefined,
                            "[]"
                        ) && condition !== "empty"
                    );
                })
                .sort((target, object) => {
                    //같은 유형일 경우 날짜 비교
                    //다른 유형일 때는 1등 생일 brdt / 2등 출장 bsrp / 3등 휴가 vactn

                    if (target.typeCd === object.typeCd) {
                        const targetMoment = moment(
                            target.startDt,
                            "YYYYMMDDHHmmss"
                        );
                        const objectMoment = moment(
                            object.startDt,
                            "YYYYMMDDHHmmss"
                        );
                        if (targetMoment.isBefore(objectMoment)) {
                            return 1;
                        }
                        if (targetMoment.isAfter(objectMoment)) {
                            return -1;
                        }
                        return 0;
                    } else {
                        if (target.typeCd > object.typeCd) {
                            return 1;
                        }
                        if (target.typeCd < object.typeCd) {
                            return -1;
                        }
                        return 0;
                    }
                }),
        });

        if (index % 7 === 6) {
            dom.push(
                <li key={keyIndex} className="week_wrap">
                    <ul className="day_wrap">
                        {dayList?.map((day) => {
                            return (
                                <li
                                    key={day.dataDay}
                                    data-day={day.dataDay}
                                    className={day.condition}
                                >
                                    <em>{day.date}</em>
                                    <div className="schedule_wrap">
                                        {day.schdlData.map((info, index) => {
                                            const btnClass =
                                                info.typeCd === "vcatn"
                                                    ? "bg04 openpopup schdle_view"
                                                    : info.typeCd === "bsrp"
                                                    ? "bg03 openpopup schdle_view"
                                                    : "bg01 openpopup schdle_view";
                                            return (
                                                <button
                                                    key={`${info.schdlId}_${index}`}
                                                    type="button"
                                                    data-id={`${info.schdlId}_${index}`}
                                                    className={btnClass}
                                                    title={`${info.aplcntNm}_${info.typeNm}`}
                                                    onClick={function (
                                                        param,
                                                        e
                                                    ) {
                                                        e.preventDefault();
                                                        if (
                                                            info.typeCd !==
                                                            "brdt"
                                                        ) {
                                                            onClickSchdlItem(
                                                                param
                                                            );
                                                        }
                                                    }.bind(this, info)}
                                                >
                                                    <em
                                                        className={`${
                                                            info.typeCd ===
                                                            "brdt"
                                                                ? "birth"
                                                                : ""
                                                        }`}
                                                    >
                                                        {`${
                                                            info.typeCd ===
                                                            "brdt"
                                                                ? ""
                                                                : info.typeNm
                                                        }`}
                                                    </em>
                                                    {info.aplcntNm}
                                                    <span className="ml5">
                                                        {info.typeCd ===
                                                            "vcatn" &&
                                                            `- ${info.vcatnSeNm}`}
                                                        {info.typeCd ===
                                                            "bsrp" &&
                                                            !!info.bsrpCncdntList &&
                                                            Object.values(
                                                                info.bsrpCncdntList
                                                            ).length > 0 &&
                                                            `외 ${
                                                                Object.values(
                                                                    info.bsrpCncdntList
                                                                ).length
                                                            } 명`}
                                                    </span>
                                                </button>
                                            );
                                        })}
                                    </div>
                                </li>
                            );
                        })}
                    </ul>
                </li>
            );
            dayList = [];
            keyIndex++;
        }
    });

    return <>{dom}</>;
}

export default memo(SchdlCalendarDom);
