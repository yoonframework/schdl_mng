package egovframework.com.cmm.web;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.mng.stng.service.CustomDefaultEditor;
import egovframework.mng.stng.service.CustomDefaultVO;

public class EgovBindingInitializer implements WebBindingInitializer {

	@Override
	public void initBinder(WebDataBinder binder) {
		binder.setAutoGrowNestedPaths(true); // insert this line
		binder.setAutoGrowCollectionLimit(5000); // insert thist line

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(false));

		LoginVO vo = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		binder.registerCustomEditor(CustomDefaultVO.class, new CustomDefaultEditor(vo));
	}

}
