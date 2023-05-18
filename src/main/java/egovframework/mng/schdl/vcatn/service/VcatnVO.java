package egovframework.mng.schdl.vcatn.service;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

/**
 * 휴가 VO
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
@Alias("VcatnVO")
public class VcatnVO extends Vcatn implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 휴가구분 명
	 */
	private String vcatnSeNm;

	/**
	 * 신청자 이름
	 */
	private String aplcntNm;

	/**
	 * 신청자 부서
	 */
	private String deptId;
	/**
	 * 신청자 부서명
	 */
	private String deptNm;
	/**
	 * 신청자 직위
	 */
	private String ofcpsId;
	/**
	 * 신청자 직위명
	 */
	private String ofcpsNm;

	/**
	 * 신청자의 휴가 남은 일수
	 */
	private String rmndrDayCnt;

	/**
	 * 신청자 휴가 일수
	 */
	private String dayCnt;

	public String getVcatnSeNm() {
		return vcatnSeNm;
	}

	public void setVcatnSeNm(String vcatnSeNm) {
		this.vcatnSeNm = vcatnSeNm;
	}

	public String getAplcntNm() {
		return aplcntNm;
	}

	public void setAplcntNm(String aplcntNm) {
		this.aplcntNm = aplcntNm;
	}

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

	public String getOfcpsId() {
		return ofcpsId;
	}

	public void setOfcpsId(String ofcpsId) {
		this.ofcpsId = ofcpsId;
	}

	public String getOfcpsNm() {
		return ofcpsNm;
	}

	public void setOfcpsNm(String ofcpsNm) {
		this.ofcpsNm = ofcpsNm;
	}

	public String getRmndrDayCnt() {
		return rmndrDayCnt;
	}

	public void setRmndrDayCnt(String rmndrDayCnt) {
		this.rmndrDayCnt = rmndrDayCnt;
	}

	public String getDayCnt() {
		return dayCnt;
	}

	public void setDayCnt(String dayCnt) {
		this.dayCnt = dayCnt;
	}

}
