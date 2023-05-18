package egovframework.com.menu.service;

import org.apache.ibatis.type.Alias;

import egovframework.com.cmm.ComDefaultVO;

/**
 * 메뉴VO TOP메뉴에 사용
 *
 * @author ljh
 *
 */
@SuppressWarnings("serial")
@Alias("MenuVO")
public class MenuVO extends ComDefaultVO {
	/**
	 * 메뉴번호
	 */
	private String menuNo;

	/**
	 * 상위메뉴번호
	 */
	private String upperMenuNo;

	/**
	 * 메뉴명
	 */
	private String menuNm;

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
	 * 권한체크에 사용되는 권한코드
	 */
	private String authorCode;

	/**
	 * 권한체크에 사용되는 권한코드목록
	 */
	private String[] authorCodeList;

	/**
	 * 권한체크에 사용되는 사용자ID
	 */
	private String userId;

	/**
	 * 하위메뉴 개수
	 */
	private int childCnt;

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

	public String getAuthorCode() {
		return authorCode;
	}

	public void setAuthorCode(String authorCode) {
		this.authorCode = authorCode;
	}

	public String[] getAuthorCodeList() {
		if (authorCode != null) {
			authorCodeList = authorCode.split(",");
		}
		if (authorCodeList == null) {
			return null;
		} else {
			int cnt = authorCodeList.length;
			String[] list = new String[cnt];
			for (int i = 0; i < cnt; i++) {
				list[i] = authorCodeList[i];
			}
			return list;
		}
	}

	public void setAuthorCodeList(String[] authorCodeList) {
		if (authorCodeList != null) {
			int cnt = authorCodeList.length;
			this.authorCodeList = new String[cnt];
			for (int i = 0; i < cnt; i++) {
				this.authorCodeList[i] = authorCodeList[i];
			}
		}
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getChildCnt() {
		return childCnt;
	}

	public void setChildCnt(int childCnt) {
		this.childCnt = childCnt;
	}
}