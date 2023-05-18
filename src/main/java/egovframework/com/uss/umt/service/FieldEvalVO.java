package egovframework.com.uss.umt.service;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

@SuppressWarnings("serial")
@Alias("FieldEvalVO")
public class FieldEvalVO extends FieldEval implements Serializable{
	private String uniqId;

	public String getUniqId() {
		return uniqId;
	}

	public void setUniqId(String uniqId) {
		this.uniqId = uniqId;
	}
}
