package egovframework.mng.stng.service;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * 기본 VO
 *
 * @since 2020. 6. 17.
 * @author 김기윤
 *
 *         <PRE>
 * -----------------------
 * 개정이력
 * 2020. 6. 17. 김기윤 : 최초작성
 *         </PRE>
 */

public class CustomDefaultVO implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 등록자 */
	String rgtrId;

	/** 등록일시 */
	String regDt;

	/** 수정자 */
	String mdfrId;

	/** 수정일시 */
	String mdfcnDt;

	/** 검색조건 */
	private String searchCondition = "";

	/** 검색Keyword */
	private String searchKeyword = "";

	/** 검색사용여부 */
	private String searchUseYn = "";

	/** 검색시작날짜 */
	private String searchBgnPnttm;

	/** 검색종료날짜 */
	private String searchEndPnttm;

	/** 페이징 사용 유무 */
	private boolean pagingAt = true;

	/** 현재페이지 */
	private int pageIndex = 1;

	/** 한 페이지 갯수 */
	private int pageUnit = 10;

	/** 페이지사이즈 */
	private int pageSize = 10;

	/** firstIndex */
	private int firstIndex = 1;

	/** lastIndex */
	private int lastIndex = 1;

	/** recordCountPerPage */
	private int recordCountPerPage = 10;

	/** 검색KeywordFrom */
	private String searchKeywordFrom = "";

	/** 검색KeywordTo */
	private String searchKeywordTo = "";

	/**
	 * 리다이렉트 시 검색 조건 get으로 붙이기
	 *
	 * @Author : 김기윤
	 * @Date : 2021. 5. 31.
	 * @Method Name : searchParam
	 * @return : String
	 * @throws UnsupportedEncodingException
	 */
	public String searchParam(int i) {
		String search = "";
		try {
			switch (i) {
				case 1:
					search += "pageIndex=" + this.getPageIndex();
					break;
				case 2:
					search += "pageIndex=" + this.getPageIndex();

					search += "&searchKeyword="
							+ URLEncoder.encode(this.getSearchKeyword(), StandardCharsets.UTF_8.name());
					break;
				case 3:
					search += "pageIndex=" + this.getPageIndex();
					search += "&searchKeyword="
							+ URLEncoder.encode(this.getSearchKeyword(), StandardCharsets.UTF_8.name());
					search += "&searchCondition="
							+ URLEncoder.encode(this.getSearchCondition(), StandardCharsets.UTF_8.name());
					break;
				default:
					break;
			}
		} catch (UnsupportedEncodingException e) {
			e.getCause().getMessage();
		}
		return search;
	}

	public String getRgtrId() {
		return rgtrId;
	}

	public void setRgtrId(String rgtrId) {
		this.rgtrId = rgtrId;
	}

	public String getRegDt() {
		return regDt;
	}

	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}

	public String getMdfrId() {
		return mdfrId;
	}

	public void setMdfrId(String mdfrId) {
		this.mdfrId = mdfrId;
	}

	public String getMdfcnDt() {
		return mdfcnDt;
	}

	public void setMdfcnDt(String mdfcnDt) {
		this.mdfcnDt = mdfcnDt;
	}

	public String getSearchCondition() {
		return searchCondition;
	}

	public void setSearchCondition(String searchCondition) {
		this.searchCondition = searchCondition;
	}

	public String getSearchKeyword() {
		return searchKeyword;
	}

	public void setSearchKeyword(String searchKeyword) {
		this.searchKeyword = searchKeyword;
	}

	public String getSearchUseYn() {
		return searchUseYn;
	}

	public void setSearchUseYn(String searchUseYn) {
		this.searchUseYn = searchUseYn;
	}

	public boolean isPagingAt() {
		return pagingAt;
	}

	public void setPagingAt(boolean pagingAt) {
		this.pagingAt = pagingAt;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPageUnit() {
		return pageUnit;
	}

	public void setPageUnit(int pageUnit) {
		this.pageUnit = pageUnit;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getFirstIndex() {
		return firstIndex;
	}

	public void setFirstIndex(int firstIndex) {
		this.firstIndex = firstIndex;
	}

	public int getLastIndex() {
		return lastIndex;
	}

	public void setLastIndex(int lastIndex) {
		this.lastIndex = lastIndex;
	}

	public int getRecordCountPerPage() {
		return recordCountPerPage;
	}

	public void setRecordCountPerPage(int recordCountPerPage) {
		this.recordCountPerPage = recordCountPerPage;
	}

	public String getSearchKeywordFrom() {
		return searchKeywordFrom;
	}

	public void setSearchKeywordFrom(String searchKeywordFrom) {
		this.searchKeywordFrom = searchKeywordFrom;
	}

	public String getSearchKeywordTo() {
		return searchKeywordTo;
	}

	public void setSearchKeywordTo(String searchKeywordTo) {
		this.searchKeywordTo = searchKeywordTo;
	}

	public String getSearchBgnPnttm() {
		return searchBgnPnttm;
	}

	public void setSearchBgnPnttm(String searchBgnPnttm) {
		this.searchBgnPnttm = searchBgnPnttm;
	}

	public String getSearchEndPnttm() {
		return searchEndPnttm;
	}

	public void setSearchEndPnttm(String searchEndPnttm) {
		this.searchEndPnttm = searchEndPnttm;
	}

}