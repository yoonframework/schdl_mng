package egovframework.mng.schdl.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.type.Alias;

import egovframework.mng.schdl.bsrp.service.BsrpCncdntVO;
import egovframework.mng.stng.service.CustomDefaultVO;

/**
 * 일정관리 유형(출장, 휴가) VO
 *
 * @since 2022. 2. 22.
 * @author 김기윤
 *
 *         <PRE>
 * -----------------------
 * 개정이력
 * 2022. 2. 22. 김기윤 : 최초작성
 *         </PRE>
 */
@SuppressWarnings("serial")
@Alias("SchdlVO")
public class SchdlVO extends CustomDefaultVO {

	/** ##### 공통 ##### */
	/**
	 * 일정 뷰 형식
	 */
	private String viewCd;
	/**
	 * 일정 유형 코드
	 */
	private String typeCd;
	/**
	 * 일정 유형 코드명
	 */
	private String typeNm;
	/**
	 * 일정 아이디
	 */
	private String schdlId;
	/**
	 * 신청자 아이디
	 */
	private String aplcntId;
	/**
	 * 신청자 이름
	 */
	private String aplcntNm;
	/**
	 * 신청자 부서
	 */
	private String deptId;
	/**
	 * 신청자 부서명
	 */
	private String deptNm;
	/**
	 * 신청자 직위
	 */
	private String ofcpsId;
	/**
	 * 신청자 직위명
	 */
	private String ofcpsNm;
	/**
	 * 시작일자
	 */
	private String startDt;
	/**
	 * 시작일자
	 */
	private String endDt;
	/**
	 * 내용
	 */
	private String content;
	/**
	 * 일자수
	 */
	private String dayCnt;

	/** ##### 휴가 ##### */
	/**
	 * 휴가구분
	 */
	private String vcatnSe;
	/**
	 * 휴가구분 명
	 */
	private String vcatnSeNm;
	/**
	 * 신청자의 휴가 남은 일수
	 */
	private String rmndrDayCnt;

	/** ##### 출장 ##### */
	/**
	 * 출장제목
	 */
	private String bsrpTtl;
	/**
	 * 출장장소
	 */
	private String bsrpPlaceNm;
	/**
	 * 출장 동행 정보 리스트
	 */
	private List<BsrpCncdntVO> bsrpCncdntList;

	public String getViewCd() {
		return viewCd;
	}

	public void setViewCd(String viewCd) {
		this.viewCd = viewCd;
	}

	public String getTypeCd() {
		return typeCd;
	}

	public void setTypeCd(String typeCd) {
		this.typeCd = typeCd;
	}

	public String getTypeNm() {
		return typeNm;
	}

	public void setTypeNm(String typeNm) {
		this.typeNm = typeNm;
	}

	public String getSchdlId() {
		return schdlId;
	}

	public void setSchdlId(String schdlId) {
		this.schdlId = schdlId;
	}

	public String getAplcntId() {
		return aplcntId;
	}

	public void setAplcntId(String aplcntId) {
		this.aplcntId = aplcntId;
	}

	public String getAplcntNm() {
		return aplcntNm;
	}

	public void setAplcntNm(String aplcntNm) {
		this.aplcntNm = aplcntNm;
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

	public String getStartDt() {
		return startDt;
	}

	public void setStartDt(String startDt) {
		this.startDt = startDt;
	}

	public String getEndDt() {
		return endDt;
	}

	public void setEndDt(String endDt) {
		this.endDt = endDt;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDayCnt() {
		return dayCnt;
	}

	public void setDayCnt(String dayCnt) {
		this.dayCnt = dayCnt;
	}

	public String getVcatnSe() {
		return vcatnSe;
	}

	public void setVcatnSe(String vcatnSe) {
		this.vcatnSe = vcatnSe;
	}

	public String getVcatnSeNm() {
		return vcatnSeNm;
	}

	public void setVcatnSeNm(String vcatnSeNm) {
		this.vcatnSeNm = vcatnSeNm;
	}

	public String getRmndrDayCnt() {
		return rmndrDayCnt;
	}

	public void setRmndrDayCnt(String rmndrDayCnt) {
		this.rmndrDayCnt = rmndrDayCnt;
	}

	public String getBsrpTtl() {
		return bsrpTtl;
	}

	public void setBsrpTtl(String bsrpTtl) {
		this.bsrpTtl = bsrpTtl;
	}

	public String getBsrpPlaceNm() {
		return bsrpPlaceNm;
	}

	public void setBsrpPlaceNm(String bsrpPlaceNm) {
		this.bsrpPlaceNm = bsrpPlaceNm;
	}

	public List<BsrpCncdntVO> getBsrpCncdntList() {
		if (this.bsrpCncdntList == null) {
			return null;
		} else {
			List<BsrpCncdntVO> bsrpCncdntList = this.bsrpCncdntList;
			return bsrpCncdntList;
		}
	}

	public void setBsrpCncdntList(List<BsrpCncdntVO> bsrpCncdntList) {
		if (bsrpCncdntList != null) {
			this.bsrpCncdntList = new ArrayList<BsrpCncdntVO>();
			for (BsrpCncdntVO vo : bsrpCncdntList) {
				this.bsrpCncdntList.add(vo);
			}
		}
	}

}
