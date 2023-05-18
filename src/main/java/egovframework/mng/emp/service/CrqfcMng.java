package egovframework.mng.emp.service;

import java.io.Serializable;

import egovframework.mng.stng.service.CustomDefaultVO;

/**
 * 직원 자격증 관리
 * 
 * @since 2022. 2. 23.
 * @author 김기윤
 * 
 *         <PRE>
 * -----------------------
 * 개정이력
 * 2022. 2. 23. 김기윤 : 최초작성
 *         </PRE>
 */
@SuppressWarnings("serial")
public class CrqfcMng extends CustomDefaultVO implements Serializable {

	/**
	 * 자격증 아이디
	 */
	private String crqfcId;

	/**
	 * 자격증 명
	 */
	private String crqfcNm;

	/**
	 * 자격증 발급기관
	 */
	private String crqfcIssuinst;

	/**
	 * 자격증 갱신일자
	 */
	private String crqfcUpdtYmd;

	/**
	 * 자격증 발급일자
	 */
	private String crqfcIssuYmd;

	/**
	 * 비고
	 */
	private String rm;

	/**
	 * 직원 아이디
	 */
	private String empId;

	public String getCrqfcId() {
		return crqfcId;
	}

	public void setCrqfcId(String crqfcId) {
		this.crqfcId = crqfcId;
	}

	public String getCrqfcNm() {
		return crqfcNm;
	}

	public void setCrqfcNm(String crqfcNm) {
		this.crqfcNm = crqfcNm;
	}

	public String getCrqfcIssuinst() {
		return crqfcIssuinst;
	}

	public void setCrqfcIssuinst(String crqfcIssuinst) {
		this.crqfcIssuinst = crqfcIssuinst;
	}

	public String getCrqfcUpdtYmd() {
		return crqfcUpdtYmd;
	}

	public void setCrqfcUpdtYmd(String crqfcUpdtYmd) {
		this.crqfcUpdtYmd = crqfcUpdtYmd;
	}

	public String getCrqfcIssuYmd() {
		return crqfcIssuYmd;
	}

	public void setCrqfcIssuYmd(String crqfcIssuYmd) {
		this.crqfcIssuYmd = crqfcIssuYmd;
	}

	public String getRm() {
		return rm;
	}

	public void setRm(String rm) {
		this.rm = rm;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

}
