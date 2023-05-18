package egovframework.mng.vcatnCrt.service;

import java.io.Serializable;

import egovframework.mng.stng.service.CustomDefaultVO;

public class VcatnCrt extends CustomDefaultVO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 휴가생성정보아이디
	 */
	private String vcatnCrtMngId;
	/**
	 * 휴가 생성년도
	 */
	private String vcatnCrtYr;
	/**
	 * 휴가 생성자 아이디
	 */
	private String vcatnCrtrId;
	/**
	 * 휴가 일수
	 */
	private int vcatnCnt;

	public String getVcatnCrtMngId() {
		return vcatnCrtMngId;
	}

	public void setVcatnCrtMngId(String vcatnCrtMngId) {
		this.vcatnCrtMngId = vcatnCrtMngId;
	}

	public String getVcatnCrtYr() {
		return vcatnCrtYr;
	}

	public void setVcatnCrtYr(String vcatnCrtYr) {
		this.vcatnCrtYr = vcatnCrtYr;
	}

	public String getVcatnCrtrId() {
		return vcatnCrtrId;
	}

	public void setVcatnCrtrId(String vcatnCrtrId) {
		this.vcatnCrtrId = vcatnCrtrId;
	}

	public int getVcatnCnt() {
		return vcatnCnt;
	}

	public void setVcatnCnt(int vcatnCnt) {
		this.vcatnCnt = vcatnCnt;
	}

}
