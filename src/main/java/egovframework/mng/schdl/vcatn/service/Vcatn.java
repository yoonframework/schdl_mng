package egovframework.mng.schdl.vcatn.service;

import java.io.Serializable;

import egovframework.mng.stng.service.CustomDefaultVO;

/**
 * 휴가
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
public class Vcatn extends CustomDefaultVO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 휴가 ID
	 */
	private String vcatnId;

	/**
	 * 신청자 아이디
	 */
	private String aplcntId;

	/**
	 * 시작일
	 */
	private String bgngYmd;

	/**
	 * 휴가 종료일
	 */
	private String endYmd;

	/**
	 * 휴가구분
	 */
	private String vcatnSe;

	/**
	 * 휴가내용
	 */
	private String vcatnCn;

	public String getVcatnId() {
		return vcatnId;
	}

	public void setVcatnId(String vcatnId) {
		this.vcatnId = vcatnId;
	}

	public String getAplcntId() {
		return aplcntId;
	}

	public void setAplcntId(String aplcntId) {
		this.aplcntId = aplcntId;
	}

	public String getBgngYmd() {
		return bgngYmd;
	}

	public void setBgngYmd(String bgngYmd) {
		this.bgngYmd = bgngYmd;
	}

	public String getEndYmd() {
		return endYmd;
	}

	public void setEndYmd(String endYmd) {
		this.endYmd = endYmd;
	}

	public String getVcatnSe() {
		return vcatnSe;
	}

	public void setVcatnSe(String vcatnSe) {
		this.vcatnSe = vcatnSe;
	}

	public String getVcatnCn() {
		return vcatnCn;
	}

	public void setVcatnCn(String vcatnCn) {
		this.vcatnCn = vcatnCn;
	}
}
