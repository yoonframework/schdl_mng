package egovframework.mng.schdl.bsrp.service;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

/**
 * 출장 동행 정보 VO
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
@Alias("BsrpCncdntVO")
public class BsrpCncdntVO extends BsrpCncdnt implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 동행인 이름
	 */
	private String tgrpnNm;

	/**
	 * 동행인 부서 ID
	 */
	private String tgrpnDeptId;

	/**
	 * 동행인 부서 명
	 */
	private String tgrpnDeptNm;

	/**
	 * 동행인 직위 ID
	 */
	private String tgrpnOfcpsId;

	/**
	 * 동행인 직위 명
	 */
	private String tgrpnOfcpsNm;

	public String getTgrpnNm() {
		return tgrpnNm;
	}

	public void setTgrpnNm(String tgrpnNm) {
		this.tgrpnNm = tgrpnNm;
	}

	public String getTgrpnDeptId() {
		return tgrpnDeptId;
	}

	public void setTgrpnDeptId(String tgrpnDeptId) {
		this.tgrpnDeptId = tgrpnDeptId;
	}

	public String getTgrpnDeptNm() {
		return tgrpnDeptNm;
	}

	public void setTgrpnDeptNm(String tgrpnDeptNm) {
		this.tgrpnDeptNm = tgrpnDeptNm;
	}

	public String getTgrpnOfcpsId() {
		return tgrpnOfcpsId;
	}

	public void setTgrpnOfcpsId(String tgrpnOfcpsId) {
		this.tgrpnOfcpsId = tgrpnOfcpsId;
	}

	public String getTgrpnOfcpsNm() {
		return tgrpnOfcpsNm;
	}

	public void setTgrpnOfcpsNm(String tgrpnOfcpsNm) {
		this.tgrpnOfcpsNm = tgrpnOfcpsNm;
	}

}
