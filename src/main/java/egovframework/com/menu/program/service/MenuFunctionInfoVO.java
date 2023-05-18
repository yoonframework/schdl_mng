package egovframework.com.menu.program.service;

import org.apache.ibatis.type.Alias;

import egovframework.com.cmm.ComDefaultVO;

/**
 * 메뉴 기능 정보 객체
 * 
 * @author Jun
 */
@SuppressWarnings("serial")
@Alias("MenuFunctionInfoVO")
public class MenuFunctionInfoVO extends MenuFunctionInfo  {
	/*
	 * 메뉴 명
	 */
	private String menuNm;
	
	/**
	 * 트리 기능 ID
	 */
	private String funcId;

	public String getMenuNm() {
		return menuNm;
	}

	public void setMenuNm(String menuNm) {
		this.menuNm = menuNm;
	}

	public String getFuncId() {
		return funcId;
	}

	public void setFuncId(String funcId) {
		this.funcId = funcId;
	}
}
