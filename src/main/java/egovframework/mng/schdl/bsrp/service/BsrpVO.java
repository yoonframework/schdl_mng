package egovframework.mng.schdl.bsrp.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.type.Alias;

/**
 * 출장 VO
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
@Alias("BsrpVO")
public class BsrpVO extends Bsrp implements Serializable {

	private static final long serialVersionUID = 1L;

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
	 * 출장 동행 정보 리스트
	 */
	private List<BsrpCncdntVO> bsrpCncdntList;

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

	public List<BsrpCncdntVO> getBsrpCncdntList() {
		if (this.bsrpCncdntList == null) {
			return null;
		} else {
			List<BsrpCncdntVO> bsrpCncdntList = this.bsrpCncdntList;
			return bsrpCncdntList;
		}
	}

	public void setBsrpCncdntList(List<BsrpCncdntVO> bsrpCncdntList) {
		if (bsrpCncdntList != null) {
			this.bsrpCncdntList = new ArrayList<BsrpCncdntVO>();
			for (BsrpCncdntVO vo : bsrpCncdntList) {
				this.bsrpCncdntList.add(vo);
			}
		}
	}
}
