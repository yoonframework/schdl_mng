package egovframework.com.uss.umt.service;

import org.apache.ibatis.type.Alias;

import egovframework.com.cmm.ComDefaultVO;
import egovframework.com.menu.program.service.MenuInfoVO;
import egovframework.com.menu.program.service.MenuProgramInfoVO;

/**
 * 기능 권한 정보 객체
 * 
 * @author Jun
 */
@SuppressWarnings("serial")
@Alias("FunctionAuthoInfo")
public class FunctionAuthoInfo extends ComDefaultVO {
	/**
	 * 기능 권한 시퀀스
	 */
	private String fncAuthorSeq;
	/*
	 * 사용자 ID
	 */
	private String userId;
	/*
	 * 기능코드
	 */
	private String functionCode;
	/**
	 * 최초등록자 아이디
	 */
	private String frstRegisterId = "";
	/**
	 * 최초등록시점
	 */
	private String frstRegisterPnttm = "";
	/**
	 * 메뉴 번호
	 */
	private MenuInfoVO menuNo;
	
	public String getFncAuthorSeq() {
		return fncAuthorSeq;
	}
	public void setFncAuthorSeq(String fncAuthorSeq) {
		this.fncAuthorSeq = fncAuthorSeq;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getFunctionCode() {
		return functionCode;
	}
	public void setFunctionCode(String functionCode) {
		this.functionCode = functionCode;
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
	public MenuInfoVO getMenuNo() {
		return menuNo;
	}
	public void setMenuNo(MenuInfoVO menuNo) {
		this.menuNo = menuNo;
	}
}
