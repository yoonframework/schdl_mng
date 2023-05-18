package egovframework.mng.cmmn.dept.service;

import egovframework.mng.stng.service.CustomDefaultVO;

public class Dept extends CustomDefaultVO {

	private static final long serialVersionUID = 1L;

	/**
	 * 부서 아이디
	 */
	private String deptId;

	/**
	 * 부서명
	 */
	private String deptNm;

	/**
	 * 상위 부서 아이디
	 */
	private String upDeptId;

	/**
	 * 순번
	 */
	private int deptSn;

	/**
	 * 사용여부
	 */
	private String useYn;

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getDeptNm() {
		return deptNm;
	}

	public void setDeptNm(String deptNm) {
		this.deptNm = deptNm;
	}

	public String getUpDeptId() {
		return upDeptId;
	}

	public void setUpDeptId(String upDeptId) {
		this.upDeptId = upDeptId;
	}

	public int getDeptSn() {
		return deptSn;
	}

	public void setDeptSn(int deptSn) {
		this.deptSn = deptSn;
	}

	public String getUseYn() {
		return useYn;
	}

	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}

}
