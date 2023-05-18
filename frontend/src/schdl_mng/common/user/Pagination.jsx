import React from "react";

/**
 * Pagination Component
 * @param {*} pagination    현재 페이지 정보
 * @param {*} moveToPage    페이지 이동 함수
 * @returns
 */
function Pagination({ pagination, moveToPage }) {
    let paginationTag = [];

    if (pagination === undefined) {
        paginationTag = "-";
    } else {
        const currentPageNo = pagination.currentPageNo;
        const pageSize = pagination.pageSize;
        const totalRecordCount = pagination.totalRecordCount;
        const recordCountPerPage = pagination.recordCountPerPage;

        const totalPageCount = Math.ceil(totalRecordCount / recordCountPerPage);
        const currentFirstPage =
            Math.floor((currentPageNo - 1) / pageSize) * pageSize + 1;
        let currentLastPage = currentFirstPage + pageSize - 1;
        currentLastPage =
            currentLastPage > totalPageCount ? totalPageCount : currentLastPage;

        if (totalPageCount > pageSize) {
            // 첫 페이지 이동
            const firstPageTag = (
                <li key="fp" className="first">
                    <a
                        href="{() => false}"
                        onClick={(e) => {
                            e.preventDefault();
                            moveToPage(1);
                            return false;
                        }}
                        className="first"
                    >
                        처음
                    </a>
                </li>
            );
            paginationTag.push(firstPageTag);

            // 이전 페이지 이동
            const prevPageIndex = currentPageNo - 1 > 0 ? currentPageNo - 1 : 1;
            const previousPageTag = (
                <li key="pp" className="prev">
                    <a
                        href="{() => false}"
                        onClick={(e) => {
                            e.preventDefault();
                            moveToPage(prevPageIndex);
                            return false;
                        }}
                        className="prev"
                    >
                        이전
                    </a>
                </li>
            );
            paginationTag.push(previousPageTag);
        }

        for (let i = currentFirstPage; i <= currentLastPage; i++) {
            if (i === currentPageNo) {
                // 현재 페이지
                const currentPage = (
                    <li key={i} className="active">
                        <a
                            href="{() => false}"
                            onClick={(e) => {
                                e.preventDefault();
                                return false;
                            }}
                        >
                            {i}
                        </a>
                    </li>
                );
                paginationTag.push(currentPage);
            } else {
                // 다른 페이지
                const otherPage = (
                    <li key={i}>
                        <a
                            href="{() => false}"
                            onClick={(e) => {
                                e.preventDefault();
                                moveToPage(i);
                                return false;
                            }}
                        >
                            {i}
                        </a>
                    </li>
                );
                paginationTag.push(otherPage);
            }
        }
        if (totalPageCount > pageSize) {
            // 다음 페이지 이동
            const nextPageIndex =
                currentLastPage + 1 < totalPageCount
                    ? currentLastPage + 1
                    : totalPageCount;
            const nextPageTag = (
                <li key="np" className="next">
                    <a
                        href="{() => false}"
                        onClick={(e) => {
                            e.preventDefault();
                            moveToPage(nextPageIndex);
                            return false;
                        }}
                        className="next"
                    >
                        다음
                    </a>
                </li>
            );
            paginationTag.push(nextPageTag);

            // 마지막 페이지 이동
            const lastPageTag = (
                <li key="lp" className="last">
                    <a
                        href="{() => false}"
                        onClick={(e) => {
                            e.preventDefault();
                            moveToPage(totalPageCount);
                            return false;
                        }}
                        className="last"
                    >
                        마지막
                    </a>
                </li>
            );
            paginationTag.push(lastPageTag);
        }
    }

    return (
        <div className="pagination">
            <ul>{paginationTag}</ul>
        </div>
    );
}

export default Pagination;
