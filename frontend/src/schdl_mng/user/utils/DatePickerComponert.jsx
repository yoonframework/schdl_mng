import React, { Fragment, createRef } from "react";
import DatePicker from "react-datepicker";
import { ko } from "date-fns/esm/locale";
import { Helmet, HelmetProvider } from "react-helmet-async";
import moment from "moment";
import { DATE } from "context/date";
import DatePickerInput from "schdl_mng/user/utils/DatePickerInput";

/**
 * DatePicker
 * @param {*} id - html attribute id
 * @param {*} name - html attribute name
 * @param {*} className - html attribute class
 * @param {*} date - 해당 입력란에서 사용되는 날짜 형태 입력 값(예 : 부모 컴포넌트에서 날짜 입력에 해당되는 state 값)
 * @param {*} setDate - 날짜 선택 시 callback 받을 함수(state 관련)
 * @param {*} isSelectTime - 시간 선택 여부
 * @param {*} minDate - 날짜 최소값 지정 여부
 * @param {*} maxDate - 날짜 최대값 지정 여부
 * @returns
 */
function DatePickerComponert({
    id,
    name,
    className,
    date,
    setDate,
    dateFormat = "YYYYMMDD",
    isSelectTime = false,
    minDate = null,
    maxDate = null,
}) {
    const inputRef = createRef();

    return (
        <Fragment>
            <HelmetProvider>
                <Helmet>
                    <link
                        rel="canonical"
                        type="text/css"
                        href={
                            require("react-datepicker/dist/react-datepicker.css")
                                .default
                        }
                    />
                    <link
                        rel="canonical"
                        type="text/css"
                        href={require("css/user/datepicker.css").default}
                    />
                </Helmet>
            </HelmetProvider>
            <DatePicker
                renderCustomHeader={({
                    date,
                    changeYear,
                    changeMonth,
                    decreaseMonth,
                    increaseMonth,
                    prevMonthButtonDisabled,
                    nextMonthButtonDisabled,
                }) => (
                    <div
                        style={{
                            margin: 10,
                            display: "flex",
                            justifyContent: "center",
                        }}
                    >
                        <button
                            onClick={decreaseMonth}
                            disabled={prevMonthButtonDisabled}
                        >
                            {"<"}
                        </button>
                        <select
                            value={date.getFullYear()}
                            onChange={({ target: { value } }) =>
                                changeYear(value)
                            }
                        >
                            {DATE.YEAR_RANGE.map((option) => (
                                <option key={option} value={option}>
                                    {option}
                                </option>
                            ))}
                        </select>

                        <select
                            value={DATE.MONTHS[date.getMonth()]}
                            onChange={({ target: { value } }) =>
                                changeMonth(DATE.MONTHS.indexOf(value))
                            }
                        >
                            {DATE.MONTHS.map((option) => (
                                <option key={option} value={option}>
                                    {option}
                                </option>
                            ))}
                        </select>

                        <button
                            onClick={increaseMonth}
                            disabled={nextMonthButtonDisabled}
                        >
                            {">"}
                        </button>
                    </div>
                )}
                dateFormat={isSelectTime ? "yyyy-MM-dd HH:mm" : "yyyy-MM-dd"}
                selected={
                    date !== "" ? moment(date, dateFormat).toDate() : date
                }
                locale={ko}
                disabledKeyboardNavigation //다른 월의 같은 날짜시 자동 selected 되는 현상 방지
                className="input-datepicker"
                closeOnScroll={false}
                // shouldCloseOnSelect={false}
                customInput={
                    <DatePickerInput
                        ref={inputRef}
                        id={id}
                        name={name}
                        className={className}
                        setDate={(value) => {
                            setDate(value);
                        }}
                        dateFormat={dateFormat}
                        isSelectTime={isSelectTime}
                        minDate={minDate}
                        maxDate={maxDate}
                    />
                }
                timeCaption={"시간"}
                onChange={(date) => setDate(moment(date).format(dateFormat))}
                showTimeSelect={isSelectTime}
                minDate={moment(minDate, dateFormat).toDate()}
                maxDate={moment(maxDate, dateFormat).toDate()}
            />
        </Fragment>
    );
}

export default DatePickerComponert;
