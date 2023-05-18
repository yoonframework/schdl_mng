package egovframework.com.menu.program.service;

import org.apache.ibatis.type.Alias;

/**
 * 메뉴 프로그램 정보 객체
 * 
 * @author Jun
 */
@SuppressWarnings("serial")
@Alias("MenuProgramInfo")
public class MenuProgramInfo {
	/**
	 * 메뉴번호
	 */
	private String menuNo;
	
	/**
	 * 프로그램 Url
	 */
	private String progrUrl;
	
	/**
	 * 프로그램 Url 명
	 */
	private String progrUrlNm;
	
	/**
	 * 대표 Url 설정
	 */
	private String defaultYn = "N";
	
	/**
	 * 시스템 그룹코드
	 */
	private String sysGroupCd;
	
	/**
	 * 최초등록자 아이디
	 */
	private String frstRegisterId = "";
	/**
	 * 최초등록시점
	 */
	private String frstRegisterPnttm = "";
	/**
	 * 최종수정자 아이디
	 */
	private String lastUpdusrId = "";
	/**
	 * 최종수정시점
	 */
	private String lastUpdusrPnttm = "";

	public String getMenuNo() {
		return menuNo;
	}

	public void setMenuNo(String menuNo) {
		this.menuNo = menuNo;
	}

	public String getProgrUrl() {
		return progrUrl;
	}

	public void setProgrUrl(String progrUrl) {
		this.progrUrl = progrUrl;
	}

	public String getProgrUrlNm() {
		return progrUrlNm;
	}

	public void setProgrUrlNm(String progrUrlNm) {
		this.progrUrlNm = progrUrlNm;
	}

	public String getDefaultYn() {
		return defaultYn;
	}

	public void setDefaultYn(String defaultYn) {
		this.defaultYn = defaultYn;
	}

	public String getSysGroupCd() {
		return sysGroupCd;
	}

	public void setSysGroupCd(String sysGroupCd) {
		this.sysGroupCd = sysGroupCd;
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

	public String getLastUpdusrId() {
		return lastUpdusrId;
	}

	public void setLastUpdusrId(String lastUpdusrId) {
		this.lastUpdusrId = lastUpdusrId;
	}

	public String getLastUpdusrPnttm() {
		return lastUpdusrPnttm;
	}

	public void setLastUpdusrPnttm(String lastUpdusrPnttm) {
		this.lastUpdusrPnttm = lastUpdusrPnttm;
	}
}
