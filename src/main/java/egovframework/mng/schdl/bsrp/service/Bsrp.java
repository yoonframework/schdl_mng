package egovframework.mng.schdl.bsrp.service;

import java.io.Serializable;

import egovframework.mng.stng.service.CustomDefaultVO;

/**
 * 출장
 *
 * @since 2022. 2. 24.
 * @author 김기윤
 *
 *         <PRE>
 * -----------------------
 * 개정이력
 * 2022. 2. 24. 김기윤 : 최초작성
 *         </PRE>
 */
public class Bsrp extends CustomDefaultVO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 출장 아이디
	 */
	private String bsrpId;

	/**
	 * 신청자
	 */
	private String aplcntId;

	/**
	 * 출장 제목
	 */
	private String bsrpTtl;

	/**
	 * 출장 장소 명
	 */
	private String bsrpPlaceNm;

	/**
	 * 출장 내용
	 */
	private String bsrpCn;

	/**
	 * 출장시작 일시
	 */
	private String bgngDt;

	/**
	 * 출장종료 일시
	 */
	private String endDt;

	public String getBsrpId() {
		return bsrpId;
	}

	public void setBsrpId(String bsrpId) {
		this.bsrpId = bsrpId;
	}

	public String getAplcntId() {
		return aplcntId;
	}

	public void setAplcntId(String aplcntId) {
		this.aplcntId = aplcntId;
	}

	public String getBsrpTtl() {
		return bsrpTtl;
	}

	public void setBsrpTtl(String bsrpTtl) {
		this.bsrpTtl = bsrpTtl;
	}

	public String getBsrpPlaceNm() {
		return bsrpPlaceNm;
	}

	public void setBsrpPlaceNm(String bsrpPlaceNm) {
		this.bsrpPlaceNm = bsrpPlaceNm;
	}

	public String getBsrpCn() {
		return bsrpCn;
	}

	public void setBsrpCn(String bsrpCn) {
		this.bsrpCn = bsrpCn;
	}

	public String getBgngDt() {
		return bgngDt;
	}

	public void setBgngDt(String bgngDt) {
		this.bgngDt = bgngDt;
	}

	public String getEndDt() {
		return endDt;
	}

	public void setEndDt(String endDt) {
		this.endDt = endDt;
	}
}
