package egovframework.com.uat.uia.web;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.egovframe.rte.fdl.cmmn.trace.LeaveaTrace;
import org.egovframe.rte.fdl.property.EgovPropertyService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.google.gson.Gson;

import egovframework.com.cmm.AjaxWrapper;
import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.ResponseCode;
import egovframework.com.cmm.service.ResultVO;
import egovframework.com.uat.uia.service.EgovLoginService;
import egovframework.mng.utl.service.SessionConfig;

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
public class EgovLoginAPIController {

	/** EgovLoginService */
	@Resource(name = "loginService")
	private EgovLoginService loginService;

	/** EgovMessageSource */
	@Resource(name = "egovMessageSource")
	EgovMessageSource egovMessageSource;

	/** EgovPropertyService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;

	/** TRACE */
	@Resource(name = "leaveaTrace")
	LeaveaTrace leaveaTrace;

	/** 세션 시간 */
	@Value("${server.servlet.session.timeout}")
	private int sessionTime;

	/**
	 * 일반 로그인을 처리한다
	 *
	 * @param vo      - 아이디, 비밀번호가 담긴 LoginVO
	 * @param request - 세션처리를 위한 HttpServletRequest
	 * @return result - 로그인결과(세션정보)
	 * @exception Exception
	 */
	@RequestMapping(value = "/uat/uia/actionLoginAPI.do", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.TEXT_HTML_VALUE })
	public @ResponseBody String actionLogin(@RequestBody LoginVO loginVO, HttpServletRequest request,
			HttpSession httpSession) throws Exception {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		AjaxWrapper aw;
		// 1. 일반 로그인 처리
		LoginVO resultVO = loginService.actionLogin(loginVO);
		if (resultVO != null && resultVO.getId() != null && !resultVO.getId().equals("")) {
			if (!resultVO.getMberSttus().equals("P")) {
				// 회원 비승인
				resultMap.put("resultVO", resultVO);
				resultMap.put("resultCode", "300");
				resultMap.put("resultMessage", egovMessageSource.getMessage("fail.common.login.confirm"));
				aw = new AjaxWrapper(AjaxWrapper.SUCCESS, resultMap);
			} else {
				// 2-1. 로그인 정보를 세션에 저장
				String clientIp = request.getHeader("X-Forwarded-For");
				if (clientIp == null || clientIp.length() == 0 || "unknown".equalsIgnoreCase(clientIp)) {
					clientIp = request.getHeader("Proxy-Client-clientIp");
				}
				if (clientIp == null || clientIp.length() == 0 || "unknown".equalsIgnoreCase(clientIp)) {
					clientIp = request.getHeader("WL-Proxy-Client-clientIp");
				}
				if (clientIp == null || clientIp.length() == 0 || "unknown".equalsIgnoreCase(clientIp)) {
					clientIp = request.getHeader("HTTP_CLIENT_clientIp");
				}
				if (clientIp == null || clientIp.length() == 0 || "unknown".equalsIgnoreCase(clientIp)) {
					clientIp = request.getHeader("HTTP_X_FORWARDED_FOR");
				}
				if (clientIp == null || clientIp.length() == 0 || "unknown".equalsIgnoreCase(clientIp)) {
					clientIp = request.getRemoteAddr();
				}
				resultVO.setIp(clientIp);

				String delSessId = SessionConfig.sessionDpcnCheck("LoginVO", resultVO);
				if (!StringUtils.isBlank(delSessId)) {
					httpSession = request.getSession();
				}

				httpSession.setAttribute("LoginVO", resultVO);
				httpSession.setMaxInactiveInterval(sessionTime);

				// 기존 메뉴 캐시삭제
				// loginService.loginCacheReset(resultVO);
				resultMap.put("resultVO", resultVO);
				resultMap.put("resultCode", "200");
				resultMap.put("resultMessage", "성공 !!!");
				aw = new AjaxWrapper(AjaxWrapper.SUCCESS, resultMap);
			}
		} else {
			resultMap.put("resultVO", resultVO);
			resultMap.put("resultCode", "300");
			resultMap.put("resultMessage", egovMessageSource.getMessage("fail.common.login"));
			aw = new AjaxWrapper(AjaxWrapper.FAIL, resultMap);
		}

		Gson gson = new Gson();
		return gson.toJson(aw);

	}

	@RequestMapping(value = "/uat/uia/actionLoginJWT.do")
	public @ResponseBody HashMap<String, Object> actionLoginJWT(@RequestBody LoginVO loginVO,
			HttpServletRequest request, ModelMap model) throws Exception {
		// 1. 일반 로그인 처리
		LoginVO resultVO = loginService.actionLogin(loginVO);

		boolean loginPolicyYn = true;
		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		if (resultVO != null && resultVO.getId() != null && !resultVO.getId().equals("") && loginPolicyYn) {

			request.getSession().setAttribute("LoginVO", resultVO);
			resultMap.put("resultVO", resultVO);
			resultMap.put("resultCode", "0");
			resultMap.put("resultMessage", "성공 !!!");
		} else {
			resultMap.put("resultVO", resultVO);
			resultMap.put("resultCode", "100");
			resultMap.put("resultMessage", egovMessageSource.getMessage("fail.common.login"));
		}
		return resultMap;
	}

	/**
	 * 로그아웃한다.
	 *
	 * @return String
	 * @exception Exception
	 */
	@RequestMapping(value = "/uat/uia/actionLogoutAPI.do", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.TEXT_HTML_VALUE })
	public @ResponseBody ResultVO actionLogoutAPI(HttpServletRequest request) throws Exception {
		ResultVO resultVO = new ResultVO();

		RequestContextHolder.getRequestAttributes().removeAttribute("LoginVO", RequestAttributes.SCOPE_SESSION);

		resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
		resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());

		return resultVO;
	}
}