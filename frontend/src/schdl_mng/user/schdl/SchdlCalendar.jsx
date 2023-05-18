import React, { useState, useEffect, memo } from "react";

import ArrowLeft from "images/user/arrow_left.svg";
import ArrowRight from "images/user/arrow_right.svg";

import SchdlCalendarDom from "schdl_mng/user/schdl/SchdlCalendarDom";
import moment from "moment";

// 캘린더 최초값
const InitCalendar = {
    year: 0,
    month: 0,
    dates: null,
    firstDateIndex: null,
    lastDateIndex: null,
    prevDates: [],
    nextDates: [],
    schdlData: [],
};

/**
 * 달력 헤더 부분
 * @param {*} currentViewInfo 일정 관리 정보 state
 * @param {*} onClickAbrvTab  요약 탭 클릭 핸들러
 * @param {*} calendarType    일정 종류
 * @param {*} onChangeCalendar 년, 월 수정 변경 이벤트
 * @param {*} onClickSchdlItem 상세 모달 호출
 * @returns
 */
function SchdlCalendar({
    currentViewInfo,
    onClickAbrvTab,
    calendarType,
    onChangeCalendar,
    onClickSchdlItem,
}) {
    // 일정 관리 state 내 key를 변수로 사용
    const { schdlList, form } = currentViewInfo;
    // 일정 관리 state 내 form의 key 를 변수로 사용
    const { startDt } = form;
    // 달력 모양을 관리하는 state
    const [calendar, setCalendar] = useState(InitCalendar);
    // 달력 모양 state 내 key를 변수로 사용
    const {
        year,
        month,
        dates,
        firstDateIndex,
        lastDateIndex,
        prevDates,
        nextDates,
        schdlData,
    } = calendar;

    // 달력 드로우
    useEffect(() => {
        const date = moment(startDt, "YYYYMMDD").toDate();
        const searchYear = date.getFullYear();
        const searchMonth = date.getMonth() + 1;

        const prevLast = moment(searchYear, "YYYY")
            .month(searchMonth - 2)
            .endOf("month")
            .toDate();
        const thisLast = moment(searchYear, "YYYY")
            .month(searchMonth - 1)
            .endOf("month")
            .toDate();

        const PLDate = prevLast.getDate();
        const PLDay = prevLast.getDay();

        const TLDate = thisLast.getDate();
        const TLDay = thisLast.getDay();

        const _prevDates = [];
        const thisDates = [];
        const _nextDates = [];

        Array.from(Array(TLDate), (e, i) => thisDates.push(i + 1));

        // 일 -> 토 (0 -> 6)
        //이전 마지막 달이 토요일이 아닐경우
        PLDay !== 6 &&
            Array.from(Array(PLDay + 1), (e, i) =>
                _prevDates.unshift(PLDate - i)
            );

        //검색된 달의 다음달 표시일자
        Array.from(Array(6 - TLDay), (e, i) => _nextDates.push(i + 1));

        const _dates = _prevDates.concat(thisDates, _nextDates);
        if (_dates !== null) {
            setCalendar((prevState) => {
                return {
                    ...prevState,
                    year: searchYear,
                    month: searchMonth,
                    schdlData: schdlList,
                    prevDates: _prevDates,
                    nextDates: _nextDates,
                    lastIndex: TLDate,
                    dates: _dates,
                    firstDateIndex: _dates.indexOf(1),
                    lastDateIndex: _dates.lastIndexOf(TLDate),
                };
            });
        } else {
            setCalendar((prevState) => {
                return {
                    ...prevState,
                    year: searchYear,
                    month: searchMonth,
                    schdlData: schdlList,
                    prevDates: _prevDates,
                    nextDates: _nextDates,
                    lastIndex: TLDate,
                    dates: _dates,
                };
            });
        }
    }, [schdlList, startDt]);

    // 연도 변경 핸들러
    const onChangeYear = (e, index) => {
        const localName = e.target.localName;
        if (localName === "select") {
            const selectVal = parseInt(e.target.value);
            onChangeCalendar(selectVal, month);
        } else {
            onChangeCalendar(year + index, month);
        }

        setCalendar(InitCalendar);
    };

    // 월 변경 핸들러
    const onChangeMonth = (e, index) => {
        const localName = e.target.localName;
        if (localName === "select") {
            const selectVal = parseInt(e.target.value);
            onChangeCalendar(year, selectVal);
        } else {
            if (month === 1 && index === -1) {
                onChangeCalendar(year - 1, 12);
            } else if (month === 12 && index === 1) {
                onChangeCalendar(year + 1, 1);
            } else {
                onChangeCalendar(year, month + index);
            }
        }

        setCalendar(InitCalendar);
    };

    // Today 클릭 핸들러
    const onChangeToday = (e) => {
        const today = new Date();
        onChangeCalendar(today.getFullYear(), today.getMonth() + 1);
        setCalendar(InitCalendar);
    };

    return (
        <>
            <div className="calendar_control">
                <ul>
                    <li>
                        {Object.values(calendarType).map((item) => (
                            <p
                                key={item.typeCd}
                                className={item.typeColor}
                                onClick={function (elem) {
                                    onClickAbrvTab(elem);
                                }.bind(this, item)}
                                style={{ cursor: "pointer" }}
                            >
                                <em>{item.abrvNm}</em>
                                {item.typeNm}
                            </p>
                        ))}
                        <p className="bg01">
                            <em className="birth"></em>
                            생일
                        </p>
                    </li>
                    <li className="month_info">
                        <div className="month_article">
                            <button
                                type="button"
                                className="btn_img btn_sq_m"
                                title="이전 연도 보기"
                                style={{
                                    backgroundImage: `url(${ArrowLeft})`,
                                }}
                                onClick={(e) => onChangeYear(e, -1)}
                            ></button>
                            <select
                                id="viewYear"
                                className="selectText width80"
                                title="년도선택"
                                value={year}
                                onChange={(e) => onChangeYear(e, 0)}
                            >
                                {Array.from(Array(7), (e, i) => {
                                    const selectYear = year - 5 + i;
                                    return (
                                        <option key={i} value={selectYear}>
                                            {selectYear}
                                        </option>
                                    );
                                })}
                            </select>
                            <button
                                type="button"
                                className="btn_img btn_sq_m"
                                title="다음 연도 보기"
                                style={{
                                    backgroundImage: `url(${ArrowRight})`,
                                }}
                                onClick={(e) => onChangeYear(e, 1)}
                            ></button>
                        </div>
                        <div className="month_article">
                            <button
                                type="button"
                                className="btn_img btn_sq_m"
                                title="이전 달 보기"
                                style={{
                                    backgroundImage: `url(${ArrowLeft})`,
                                }}
                                onClick={(e) => onChangeMonth(e, -1)}
                            ></button>
                            <select
                                id="viewMonth"
                                className="selectText width70"
                                title="월선택"
                                value={month}
                                onChange={(e) => onChangeMonth(e, 0)}
                            >
                                {Array.from(Array(12), (e, i) => {
                                    const selectMonth = i + 1;
                                    return (
                                        <option key={i} value={selectMonth}>
                                            {selectMonth}
                                        </option>
                                    );
                                })}
                            </select>
                            <button
                                type="button"
                                className="btn_img btn_sq_m"
                                title="다음 달 보기"
                                style={{
                                    backgroundImage: `url(${ArrowRight})`,
                                }}
                                onClick={(e) => onChangeMonth(e, 1)}
                            ></button>
                        </div>
                    </li>
                    <li>
                        <button
                            id="viewToday"
                            type="button"
                            className="btn_s bg_navy"
                            title="오늘 달력 보기"
                            onClick={(e) => onChangeToday(e)}
                        >
                            today
                        </button>
                    </li>
                </ul>
            </div>
            <ul id="calendar" className="calendar month">
                <li className="cal_header">
                    <ul className="day_wrap">
                        <li>일</li>
                        <li>월</li>
                        <li>화</li>
                        <li>수</li>
                        <li>목</li>
                        <li>금</li>
                        <li>토</li>
                    </ul>
                </li>
                <SchdlCalendarDom
                    year={year}
                    month={month}
                    dates={dates}
                    firstDateIndex={firstDateIndex}
                    lastDateIndex={lastDateIndex}
                    prevDates={prevDates}
                    nextDates={nextDates}
                    schdlData={schdlData}
                    onClickSchdlItem={onClickSchdlItem}
                />
            </ul>
        </>
    );
}

export default memo(SchdlCalendar);
