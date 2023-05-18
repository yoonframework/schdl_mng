package egovframework.com.cmm;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import egovframework.mng.emp.service.CrqfcMngVO;

/**
 * @Class Name : LoginVO.java
 * @Description : Login VO class
 * @Modification Information
 * @ @ 수정일 수정자 수정내용 @ ------- -------- --------------------------- @ 2009.03.03
 *   박지욱 최초 생성
 *
 * @author 공통서비스 개발팀 박지욱
 * @since 2009.03.03
 * @version 1.0
 * @see
 *
 */
public class LoginVO implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -8274004534207618049L;

	/** 아이디 */
	private String id;
	/** 이름 */
	private String name;
	/** 주민등록번호 */
	private String ihidNum;
	/** 이메일주소 */
	private String email;
	/** 비밀번호 */
	private String password;
	/** 비밀번호 힌트 */
	private String passwordHint;
	/** 비밀번호 정답 */
	private String passwordCnsr;
	/** 사용자구분 */
	private String userSe;
	/** 조직(부서)ID */
	private String orgnztId;
	/** 조직(부서)명 */
	private String orgnztNm;
	/** 고유아이디 */
	private String uniqId;
	/** 로그인 후 이동할 페이지 */
	private String url;
	/** 사용자 IP정보 */
	private String ip;
	/** GPKI인증 DN */
	private String dn;
	/* 권한정보설정 */
	/** 권한코드 */
	private String authorCode;
	private String[] authorCodeList;
	/** 권한명 */
	private String authorCodeNm;
	/** 권한설명 */
	private String authorDc;
	/** 권한생성일자 */
	private String authorCreatDe;

	/** 소속기관 */
	private String agency;

	/** 연락처(일반전화) */
	private String telNo;

	/** 핸드폰 번호 */
	private String mbtlNum;

	/** 회원승인상태 **/
	private String mberSttus;

	/** 회사ID **/
	private String cmpnyId;

	/** 회사명 **/
	private String cmpnyNm;

	/** 부서ID **/
	private String deptId;

	/** 부서명 **/
	private String deptNm;

	/**
	 * 직위아이디
	 */
	private String ofcpsId;

	/**
	 * 직위명
	 */
	private String ofcpsNm;

	/**
	 * 직책아이디
	 */
	private String jbttlId;

	/**
	 * 직책명
	 */
	private String jbttlNm;

	/**
	 * 생년월일
	 */
	private String brdt;

	/**
	 * 입사일자
	 */
	private String jncmpYmd;

	/**
	 * id attribute 를 리턴한다.
	 *
	 * @return String
	 */
	public String getId() {
		return id;
	}

	/**
	 * id attribute 값을 설정한다.
	 *
	 * @param id String
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * name attribute 를 리턴한다.
	 *
	 * @return String
	 */
	public String getName() {
		return name;
	}

	/**
	 * name attribute 값을 설정한다.
	 *
	 * @param name String
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * ihidNum attribute 를 리턴한다.
	 *
	 * @return String
	 */
	public String getIhidNum() {
		return ihidNum;
	}

	/**
	 * ihidNum attribute 값을 설정한다.
	 *
	 * @param ihidNum String
	 */
	public void setIhidNum(String ihidNum) {
		this.ihidNum = ihidNum;
	}

	/**
	 * email attribute 를 리턴한다.
	 *
	 * @return String
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * email attribute 값을 설정한다.
	 *
	 * @param email String
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * password attribute 를 리턴한다.
	 *
	 * @return String
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * password attribute 값을 설정한다.
	 *
	 * @param password String
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * passwordHint attribute 를 리턴한다.
	 *
	 * @return String
	 */
	public String getPasswordHint() {
		return passwordHint;
	}

	/**
	 * passwordHint attribute 값을 설정한다.
	 *
	 * @param passwordHint String
	 */
	public void setPasswordHint(String passwordHint) {
		this.passwordHint = passwordHint;
	}

	/**
	 * passwordCnsr attribute 를 리턴한다.
	 *
	 * @return String
	 */
	public String getPasswordCnsr() {
		return passwordCnsr;
	}

	/**
	 * passwordCnsr attribute 값을 설정한다.
	 *
	 * @param passwordCnsr String
	 */
	public void setPasswordCnsr(String passwordCnsr) {
		this.passwordCnsr = passwordCnsr;
	}

	/**
	 * userSe attribute 를 리턴한다.
	 *
	 * @return String
	 */
	public String getUserSe() {
		return userSe;
	}

	/**
	 * userSe attribute 값을 설정한다.
	 *
	 * @param userSe String
	 */
	public void setUserSe(String userSe) {
		this.userSe = userSe;
	}

	/**
	 * orgnztId attribute 를 리턴한다.
	 *
	 * @return String
	 */
	public String getOrgnztId() {
		return orgnztId;
	}

	/**
	 * orgnztId attribute 값을 설정한다.
	 *
	 * @param orgnztId String
	 */
	public void setOrgnztId(String orgnztId) {
		this.orgnztId = orgnztId;
	}

	/**
	 * uniqId attribute 를 리턴한다.
	 *
	 * @return String
	 */
	public String getUniqId() {
		return uniqId;
	}

	/**
	 * uniqId attribute 값을 설정한다.
	 *
	 * @param uniqId String
	 */
	public void setUniqId(String uniqId) {
		this.uniqId = uniqId;
	}

	/**
	 * url attribute 를 리턴한다.
	 *
	 * @return String
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * url attribute 값을 설정한다.
	 *
	 * @param url String
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * ip attribute 를 리턴한다.
	 *
	 * @return String
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * ip attribute 값을 설정한다.
	 *
	 * @param ip String
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * dn attribute 를 리턴한다.
	 *
	 * @return String
	 */
	public String getDn() {
		return dn;
	}

	/**
	 * dn attribute 값을 설정한다.
	 *
	 * @param dn String
	 */
	public void setDn(String dn) {
		this.dn = dn;
	}

	/**
	 * @return the orgnztNm
	 */
	public String getOrgnztNm() {
		return orgnztNm;
	}

	/**
	 * @param orgnztNm the orgnztNm to set
	 */
	public void setOrgnztNm(String orgnztNm) {
		this.orgnztNm = orgnztNm;
	}

	public String getAuthorCode() {
		return authorCode;
	}

	public void setAuthorCode(String authorCode) {
		this.authorCode = authorCode;
	}

	public String[] getAuthorCodeList() {
		return authorCodeList;
	}

	public void setAuthorCodeList(String[] authorCodeList) {
		this.authorCodeList = authorCodeList;
	}

	public String getAuthorCodeNm() {
		return authorCodeNm;
	}

	public void setAuthorCodeNm(String authorCodeNm) {
		this.authorCodeNm = authorCodeNm;
	}

	public String getAuthorDc() {
		return authorDc;
	}

	public void setAuthorDc(String authorDc) {
		this.authorDc = authorDc;
	}

	public String getAuthorCreatDe() {
		return authorCreatDe;
	}

	public void setAuthorCreatDe(String authorCreatDe) {
		this.authorCreatDe = authorCreatDe;
	}

	public String getAgency() {
		return agency;
	}

	public void setAgency(String agency) {
		this.agency = agency;
	}

	public String getTelNo() {
		return telNo;
	}

	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}

	public String getMbtlNum() {
		return mbtlNum;
	}

	public void setMbtlNum(String mbtlNum) {
		this.mbtlNum = mbtlNum;
	}

	public String getMberSttus() {
		return mberSttus;
	}

	public void setMberSttus(String mberSttus) {
		this.mberSttus = mberSttus;
	}

	public String getCmpnyId() {
		return cmpnyId;
	}

	public void setCmpnyId(String cmpnyId) {
		this.cmpnyId = cmpnyId;
	}

	public String getCmpnyNm() {
		return cmpnyNm;
	}

	public void setCmpnyNm(String cmpnyNm) {
		this.cmpnyNm = cmpnyNm;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getDeptNm() {
		return deptNm;
	}

	public void setDeptNm(String deptNm) {
		this.deptNm = deptNm;
	}

	public String getOfcpsId() {
		return ofcpsId;
	}

	public void setOfcpsId(String ofcpsId) {
		this.ofcpsId = ofcpsId;
	}

	public String getOfcpsNm() {
		return ofcpsNm;
	}

	public void setOfcpsNm(String ofcpsNm) {
		this.ofcpsNm = ofcpsNm;
	}

	public String getJbttlId() {
		return jbttlId;
	}

	public void setJbttlId(String jbttlId) {
		this.jbttlId = jbttlId;
	}

	public String getJbttlNm() {
		return jbttlNm;
	}

	public void setJbttlNm(String jbttlNm) {
		this.jbttlNm = jbttlNm;
	}

	public String getBrdt() {
		return brdt;
	}

	public void setBrdt(String brdt) {
		this.brdt = brdt;
	}

	public String getJncmpYmd() {
		return jncmpYmd;
	}

	public void setJncmpYmd(String jncmpYmd) {
		this.jncmpYmd = jncmpYmd;
	}

}
