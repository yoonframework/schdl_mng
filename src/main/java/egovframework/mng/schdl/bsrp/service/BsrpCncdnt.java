package egovframework.mng.schdl.bsrp.service;

import java.io.Serializable;

import egovframework.mng.stng.service.CustomDefaultVO;

/**
 * 출장 동행 정보
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
public class BsrpCncdnt extends CustomDefaultVO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 출장 동행정보 아이디
	 */
	private String bsrpCncdntId;

	/**
	 * 동행인 아이디
	 */
	private String tgrpnId;

	/**
	 * 출장 아이디
	 */
	private String bsrpId;

	public String getBsrpCncdntId() {
		return bsrpCncdntId;
	}

	public void setBsrpCncdntId(String bsrpCncdntId) {
		this.bsrpCncdntId = bsrpCncdntId;
	}

	public String getTgrpnId() {
		return tgrpnId;
	}

	public void setTgrpnId(String tgrpnId) {
		this.tgrpnId = tgrpnId;
	}

	public String getBsrpId() {
		return bsrpId;
	}

	public void setBsrpId(String bsrpId) {
		this.bsrpId = bsrpId;
	}
}
