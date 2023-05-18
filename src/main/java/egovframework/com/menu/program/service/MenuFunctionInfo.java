package egovframework.com.menu.program.service;

import org.apache.ibatis.type.Alias;

/**
 * 메뉴 프로그램 정보 객체
 * 
 * @author Jun
 */
@SuppressWarnings("serial")
@Alias("MenuFunctionInfo")
public class MenuFunctionInfo {
	/**
	 * 메뉴번호
	 */
	private String menuNo;
	/*
	 * 기능코드
	 */
	private String functionCode;
	/*
	 * 기능명
	 */
	private String functionNm;
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
	/**
	 * 시스템 그룹 코드
	 */
	private String sysGroupCd;
	
	public String getMenuNo() {
		return menuNo;
	}
	public void setMenuNo(String menuNo) {
		this.menuNo = menuNo;
	}
	public String getFunctionCode() {
		return functionCode;
	}
	public void setFunctionCode(String functionCode) {
		this.functionCode = functionCode;
	}
	public String getFunctionNm() {
		return functionNm;
	}
	public void setFunctionNm(String functionNm) {
		this.functionNm = functionNm;
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
	public String getSysGroupCd() {
		return sysGroupCd;
	}
	public void setSysGroupCd(String sysGroupCd) {
		this.sysGroupCd = sysGroupCd;
	}
}
