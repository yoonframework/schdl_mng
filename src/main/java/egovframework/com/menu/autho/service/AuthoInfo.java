package egovframework.com.menu.autho.service;

import org.apache.ibatis.type.Alias;

import egovframework.com.cmm.ComDefaultVO;
import egovframework.com.menu.program.service.MenuInfoVO;
import egovframework.com.menu.program.service.MenuProgramInfoVO;

/**
 * 메뉴 권한 정보 객체
 * 
 * @author Jun
 */
@SuppressWarnings("serial")
@Alias("AuthoInfo")
public class AuthoInfo extends ComDefaultVO {
	/**
	 * 메뉴 권한 시퀀스
	 */
	private String authorSeq;
	/**
	 * 권한 코드
	 */
	private String authorCode;
	/**
	 * 권한 사용자 ID
	 */
	private String userId;
	/**
	 * 메뉴 번호
	 */
	private MenuInfoVO menuNo;
	/**
	 * 프로그램 URL
	 */
	private MenuProgramInfoVO progrUrl;
	/**
	 * 최초등록자 아이디
	 */
	private String frstRegisterId = "";
	/**
	 * 최초등록시점
	 */
	private String frstRegisterPnttm = "";
	
	public String getAuthorSeq() {
		return authorSeq;
	}
	public void setAuthorSeq(String authorSeq) {
		this.authorSeq = authorSeq;
	}
	public String getAuthorCode() {
		return authorCode;
	}
	public void setAuthorCode(String authorCode) {
		this.authorCode = authorCode;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public MenuInfoVO getMenuNo() {
		return menuNo;
	}
	public void setMenuNo(MenuInfoVO menuNo) {
		this.menuNo = menuNo;
	}
	public MenuProgramInfoVO getProgrUrl() {
		return progrUrl;
	}
	public void setProgrUrl(MenuProgramInfoVO progrUrl) {
		this.progrUrl = progrUrl;
	}
	public String getFrstRegisterId() {
		return frstRegisterId;
	}
	public void setFrstRegisterId(String frstRegisterId) {
		this.frstRegisterId = frstRegisterId;
	}
	public String getFrstRegisterPnttm() {
		return frstRegisterPnttm;
	}
	public void setFrstRegisterPnttm(String frstRegisterPnttm) {
		this.frstRegisterPnttm = frstRegisterPnttm;
	}
}
