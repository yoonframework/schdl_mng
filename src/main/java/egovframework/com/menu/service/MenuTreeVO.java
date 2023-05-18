package egovframework.com.menu.service;

import org.apache.ibatis.type.Alias;

import egovframework.mng.stng.service.CustomDefaultVO;

/**
 * 메뉴트리VO
 * 메뉴트리에 사용
 * 
 * @author ljh
 *
 */
@SuppressWarnings("serial")
@Alias("MenuTreeVO")
public class MenuTreeVO extends CustomDefaultVO {
	/**
	 * 메뉴번호 treeId
	 */
	private String id;

	/**
	 * 상위메뉴번호 treePid
	 */
	private String pId;

	/**
	 * 메뉴명
	 */
	private String name;

	/**
	 * 메뉴순서
	 */
	private Long menuOrdr;

	/**
	 * 메뉴설정
	 */
	private String menuDc;

	/**
	 * 관련이미지
	 */
	private String relateImage;

	/**
	 * 프로그램구분 C:컨텐츠 P:프로그램 B:게시판 D:폴더
	 */
	private String progrGb;

	/**
	 * 대표URL
	 */
	private String progrUrl;

	/**
	 * 메뉴구분 U:사용자 A:관리자 M:마이페이지
	 */
	private String menuGb;

	/**
	 * 메뉴번호
	 */
	private String menuNo;

	/**
	 * 상위메뉴번호
	 */
	private String upperMenuNo;

	/**
	 * 시스템그룹 명
	 */
	private String sysGroupNm;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getMenuOrdr() {
		return menuOrdr;
	}

	public void setMenuOrdr(Long menuOrdr) {
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

	public String getProgrUrl() {
		return progrUrl;
	}

	public void setProgrUrl(String progrUrl) {
		this.progrUrl = progrUrl;
	}

	public String getMenuGb() {
		return menuGb;
	}

	public void setMenuGb(String menuGb) {
		this.menuGb = menuGb;
	}

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

	public String getSysGroupNm() {
		return sysGroupNm;
	}

	public void setSysGroupNm(String sysGroupNm) {
		this.sysGroupNm = sysGroupNm;
	}
}