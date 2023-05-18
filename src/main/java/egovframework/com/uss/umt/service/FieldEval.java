package egovframework.com.uss.umt.service;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

@SuppressWarnings("serial")
@Alias("FieldEval")
public class FieldEval implements Serializable{
	//회원 Uniq아이디
	private String mberId;
	
	// 평가 전문분야 대분류 코드
	private String frstCodeId;
	
	// 평가 전문분야 대분류명
	private String frstCodeNm;
	
	// 평가 전문분야 소분류 코드
	private String scndCodeId;
	
	// 평가 전문분야 소분류명
	private String scndCodeNm;
	
	// 평가 전문분야 대분류 기타
	private String frstCodeEtc;
	
	// 평가 전문분야 소분류 기타
	private String scndCodeEtc;

	public String getMberId() {
		return mberId;
	}

	public void setMberId(String mberId) {
		this.mberId = mberId;
	}

	public String getFrstCodeId() {
		return frstCodeId;
	}

	public void setFrstCodeId(String frstCodeId) {
		this.frstCodeId = frstCodeId;
	}

	public String getFrstCodeNm() {
		return frstCodeNm;
	}

	public void setFrstCodeNm(String frstCodeNm) {
		this.frstCodeNm = frstCodeNm;
	}

	public String getScndCodeId() {
		return scndCodeId;
	}

	public void setScndCodeId(String scndCodeId) {
		this.scndCodeId = scndCodeId;
	}

	public String getScndCodeNm() {
		return scndCodeNm;
	}

	public void setScndCodeNm(String scndCodeNm) {
		this.scndCodeNm = scndCodeNm;
	}

	public String getFrstCodeEtc() {
		return frstCodeEtc;
	}

	public void setFrstCodeEtc(String frstCodeEtc) {
		this.frstCodeEtc = frstCodeEtc;
	}

	public String getScndCodeEtc() {
		return scndCodeEtc;
	}

	public void setScndCodeEtc(String scndCodeEtc) {
		this.scndCodeEtc = scndCodeEtc;
	}
}
