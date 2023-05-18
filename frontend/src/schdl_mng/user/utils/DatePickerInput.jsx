import React, { forwardRef, useEffect, useState } from "react";
import InputMask from "react-input-mask";
import moment from "moment";
import { DATE } from "context/date";

const fnCmpltDate = (dateString, dateFormat, minDate, maxDate) => {
    let momentObj = moment(dateString.replace(/[^0-9]/g, ""), dateFormat);
    //30분단위
    momentObj = momentObj.minutes(
        momentObj.minute() === 0 || momentObj.minute() < 30 ? 0 : 30
    );

    const startDate =
        minDate === null
            ? moment(DATE.YEAR_RANGE[0], "YYYY")
            : moment(minDate, dateFormat);
    const endDate =
        maxDate === null
            ? moment(DATE.YEAR_RANGE[DATE.YEAR_RANGE.length - 1], "YYYY").endOf(
                  "years"
              )
            : moment(maxDate, dateFormat);

    if (momentObj.isValid()) {
        if (momentObj.isBefore(startDate)) {
            return startDate.format(dateFormat);
        }
        if (momentObj.isAfter(endDate)) {
            return endDate.format(dateFormat);
        }

        return momentObj.format(dateFormat);
    } else {
        return "";
    }
};

const DatePickerInput = forwardRef(
    (
        {
            value,
            onClick,
            id,
            name,
            className,
            setDate,
            dateFormat,
            isSelectTime,
            minDate,
            maxDate,
        },
        ref
    ) => {
        const [inputVal, setInputVal] = useState("");

        useEffect(() => {
            setInputVal(value);
        }, [value]);

        const onChangeDateInput = (e) => {
            setInputVal(e.target.value);
        };

        // Input Focus out 이벤트
        const onBlurDateInput = (e) => {
            if (e.target.value !== "") {
                //날짜 형태가 타당할 때
                const resultVal = fnCmpltDate(
                    e.target.value,
                    dateFormat,
                    minDate,
                    maxDate
                );
                if (resultVal === "") {
                    alert("날짜 형식이 올바르지 않습니다.");
                }
                setInputVal(resultVal);
                setDate(resultVal);
            } else {
                //빈 값일 경우 그대로 리턴
                setInputVal(e.target.value);
                setDate(e.target.value);
            }
        };

        return (
            <>
                <div className="date_picker">
                    <div className="date_pick">
                        <InputMask
                            type="text"
                            id={id}
                            ref={ref}
                            className={className}
                            name={name}
                            value={inputVal}
                            onClick={onClick}
                            onChange={onChangeDateInput}
                            onBlur={onBlurDateInput}
                            mask={
                                isSelectTime ? "9999-99-99 99:99" : "9999-99-99"
                            }
                            autoComplete="off"
                        />
                    </div>
                </div>
            </>
        );
    }
);

export default DatePickerInput;
