package egovframework.mng.stng.service;

import java.beans.PropertyEditorSupport;

import org.apache.commons.lang.StringUtils;

import egovframework.com.cmm.LoginVO;

public class CustomDefaultEditor extends PropertyEditorSupport {

	private final LoginVO loginVO;

	public CustomDefaultEditor(LoginVO loginVO) {
		this.loginVO = loginVO;
	}

	@Override
	public void setValue(Object value) {
		if (this.loginVO == null) {
			super.setValue(value);
		} else {
			CustomDefaultVO vo = (CustomDefaultVO) value;
			String rgtrId = vo.getRgtrId();
			String mdfrId = vo.getMdfrId();
			if (StringUtils.isBlank(rgtrId)) {
				vo.setRgtrId(loginVO.getUniqId());
			}
			if (StringUtils.isBlank(mdfrId)) {
				vo.setMdfrId(loginVO.getUniqId());
			}
			super.setValue(vo);
		}
	}

	@Override
	public String getAsText() {
		return super.getAsText();
	}
}
