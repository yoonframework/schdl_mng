package egovframework.com.menu.program.service;

import org.apache.ibatis.type.Alias;

import egovframework.mng.stng.service.CustomDefaultVO;

/**
 * 메뉴 정보 객체
 *
 * @author Jun
 */
@SuppressWarnings("serial")
@Alias("MenuInfo")
public class MenuInfo extends CustomDefaultVO {
	/**
	 * 메뉴번호
	 */
	private String menuNo;

	/**
	 * 상위메뉴번호
	 */
	private String upperMenuNo;

	/**
	 * 메뉴 명
	 */
	private String menuNm;

	/**
	 * 메뉴순서
	 */
	private String menuOrdr;

	/**
	 * 메뉴 설명
	 */
	private String menuDc;

	/**
	 * 관련 이미지
	 */
	private String relateImage;

	/**
	 * 프로그램 구분
	 */
	private String progrGb;

	/**
	 * 메뉴구분 U:사용자 A:관리자 M:마이페이지
	 */
	private String menuGb;

	/**
	 * 사용유무
	 */
	private String useYn;

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

	public String getUpperMenuNo() {
		return upperMenuNo;
	}

	public void setUpperMenuNo(String upperMenuNo) {
		this.upperMenuNo = upperMenuNo;
	}

	public String getMenuNm() {
		return menuNm;
	}

	public void setMenuNm(String menuNm) {
		this.menuNm = menuNm;
	}

	public String getMenuOrdr() {
		return menuOrdr;
	}

	public void setMenuOrdr(String menuOrdr) {
		this.menuOrdr = menuOrdr;
	}

	public String getMenuDc() {
		return menuDc;
	}

	public void setMenuDc(String menuDc) {
		this.menuDc = menuDc;
	}

	public String getRelateImage() {
		return relateImage;
	}

	public void setRelateImage(String relateImage) {
		this.relateImage = relateImage;
	}

	public String getProgrGb() {
		return progrGb;
	}

	public void setProgrGb(String progrGb) {
		this.progrGb = progrGb;
	}

	public String getMenuGb() {
		return menuGb;
	}

	public void setMenuGb(String menuGb) {
		this.menuGb = menuGb;
	}

	public String getUseYn() {
		return useYn;
	}

	public void setUseYn(String useYn) {
		this.useYn = useYn;
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
