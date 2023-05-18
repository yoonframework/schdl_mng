package egovframework.com.uss.umt.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.type.Alias;

/**
 * 기능 권한 정보 객체
 *
 * @author Jun
 */
@SuppressWarnings("serial")
@Alias("FunctionAuthoInfoVO")
public class FunctionAuthoInfoVO extends FunctionAuthoInfo {
	/*
	 * 기능권한정보 리스트
	 */
	private List<FunctionAuthoInfoVO> functionList;

	public List<FunctionAuthoInfoVO> getFunctionList() {
		if (this.functionList == null) {
			return null;
		} else {
			List<FunctionAuthoInfoVO> list = this.functionList;
			return list;
		}
	}

	public void setFunctionList(List<FunctionAuthoInfoVO> functionList) {
		if (functionList != null) {
			this.functionList = new ArrayList<FunctionAuthoInfoVO>();
			for (FunctionAuthoInfoVO vo : functionList) {
				this.functionList.add(vo);
			}
		}
	}
}
