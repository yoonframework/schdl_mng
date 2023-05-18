import _ from "lodash";
const date = new Date();
const MIN_YEAR = 1900;
const YEAR_EXTRA = 10;
export const DATE = {
    YEAR_RANGE: _.range(MIN_YEAR, date.getFullYear() + YEAR_EXTRA, 1),
    MONTHS: [
        "1월",
        "2월",
        "3월",
        "4월",
        "5월",
        "6월",
        "7월",
        "8월",
        "9월",
        "10월",
        "11월",
        "12월",
    ],
};
