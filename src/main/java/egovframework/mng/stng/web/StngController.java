package egovframework.mng.stng.web;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.egovframe.rte.fdl.cmmn.exception.EgovBizException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import egovframework.com.cmm.AjaxWrapper;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.menu.service.MenuService;
import egovframework.com.menu.service.MenuVO;

/**
 * 일반 로그인을 처리하는 컨트롤러 클래스
 *
 * @author 공통서비스 개발팀 박지욱
 * @since 2009.03.06
 * @version 1.0
 * @see
 *
 *      <pre>
 * << 개정이력(Modification Information) >>
 *
 *  수정일      수정자      수정내용
 *  -------            --------        ---------------------------
 *  2009.03.06  박지욱     최초 생성
 *  2011.08.31  JJY            경량환경 템플릿 커스터마이징버전 생성
 *
 *      </pre>
 */
@Controller
@RequestMapping("/api")
public class StngController {

	@Resource(name = "MenuService")
	private MenuService menuService;

	/** 리다이렉트 주소 */
	@Value("${server.redirect.url}")
	private String redirectUrl;

	/**
	 * 세션 정보 조회
	 * 
	 * @Author : 김기윤
	 * @Date : 2022. 1. 25.
	 * @Method Name : selectMenuList
	 * @return : String
	 */
	@RequestMapping(value = "/stng/selectSession.do", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.TEXT_HTML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody String selectMenuList(@RequestBody String originalURL)
			throws EgovBizException {

		Map<String, Object> resultMap = new HashMap<>();
		AjaxWrapper aw;
		LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		MenuVO menuVO = new MenuVO();

		String uniqId = "";
		String authorCode = "";
		if (loginVO != null) {
			uniqId = loginVO.getUniqId();
			authorCode = loginVO.getAuthorCode();
		}

		if (!StringUtils.isBlank(uniqId)) {
			resultMap.put("loginVO", loginVO);

			menuVO.setUserId(uniqId);
			menuVO.setAuthorCode(authorCode);

			// 기능 권한 체크(기능 권한은 사용자에게만 부여)
			MenuVO functionMenuVO = new MenuVO();
			functionMenuVO.setUserId(uniqId);
			resultMap.put("FUNCTION_AUTHO", menuService.selectUserFunctionList(functionMenuVO));

			// 권한에 따른 메뉴 조회
			// 메뉴정보 세션에 저장
			resultMap.put("menuVO", menuService.selectSessionMenuList(menuVO));

			// my 네비게이션 메뉴 정보
			menuVO.setProgrUrl(originalURL);
			resultMap.put("menuNaviVO", menuService.selectMenuNaviList(menuVO));
			// 같은 depth 메뉴 정보
			resultMap.put("menuDepthVO", menuService.selectMenuSameDepthList(menuVO));

			// 현재 메뉴 정보
			MenuVO menuInfo = menuService.selectMenuProgram(menuVO);
			resultMap.put("thisMenuInfo", menuInfo);
		} else {
			aw = new AjaxWrapper(AjaxWrapper.SESSION_EXPRY, resultMap);
			return new Gson().toJson(aw);
		}

		aw = new AjaxWrapper(AjaxWrapper.SUCCESS, resultMap);
		return new Gson().toJson(aw);
	}
}