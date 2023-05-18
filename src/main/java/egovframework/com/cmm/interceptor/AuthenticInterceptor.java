package egovframework.com.cmm.interceptor;

import java.util.Collections;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;
import org.springframework.web.servlet.mvc.WebContentInterceptor;
import org.springframework.web.util.UrlPathHelper;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.menu.service.MenuService;
import egovframework.com.menu.service.MenuVO;
import egovframework.com.menu.web.AlertView;

/**
 * 인증여부 체크 인터셉터
 * @author 공통서비스 개발팀 서준식
 * @since 2011.07.01
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2011.07.01  서준식          최초 생성
 *  2011.09.07  서준식          인증이 필요없는 URL을 패스하는 로직 추가
 *  2014.06.11  이기하          인증이 필요없는 URL을 패스하는 로직 삭제(xml로 대체)
 *  </pre>
 */

public class AuthenticInterceptor extends WebContentInterceptor {

	@Resource(name = "MenuService")
	private MenuService menuService;

	/** 리다이렉트 URL */
	@Value("${server.redirect.url}")
	private String redirectUrl;

	/**
	 * 세션에 계정정보(LoginVO)가 있는지 여부로 인증 여부를 체크한다.
	 * 계정정보(LoginVO)가 없다면, 로그인 페이지로 이동한다.
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException {

		// submit 요청인지 ajax 요청인지 구분
		String header = request.getHeader("x-requested-with");
		boolean isPreUrl = false;
		if( header != null ) {
			isPreUrl = header.matches("XMLHttpRequest");
		}

		UrlPathHelper urlPathHelper = new UrlPathHelper();
		String originalURL = urlPathHelper.getOriginatingRequestUri(request);

		// 사용자정보 조회
		LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

		// 로그인 여부 체크
		MenuVO checkMenuVO = new MenuVO();
		if (loginVO == null || (loginVO != null && StringUtils.isBlank(loginVO.getUniqId()))) {
			// 이전 페이지 저장
			String prevUrl = "/";
			if( !isPreUrl ) {
				prevUrl = originalURL;
				String param = "";
				for (String name : Collections.<String>list(request.getParameterNames())) {
					String value = request.getParameter(name);
					param += name + "=" + value + "&";
				}
				if( param != "" ) {
					prevUrl = prevUrl + "?" + param;
				}
			}
			request.getSession().setAttribute("prevPage", prevUrl);

			ModelAndView modelAndView = new ModelAndView("redirect:" + redirectUrl + "/login");
			throw new ModelAndViewDefiningException(modelAndView);
		} else {
			checkMenuVO.setUserId(loginVO.getUniqId());
			checkMenuVO.setAuthorCode(loginVO.getAuthorCode());
		}

		checkMenuVO.setProgrUrl(originalURL);
		// 등록된 URL만 체크
		int cnt = 0;
		try {
			cnt = menuService.selectMenuProgramCnt(checkMenuVO);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if( cnt > 0 ) {
			// 권한체크
			int authoCheck = 0;
			try {
				authoCheck = menuService.selectAuthoUrlCheck(checkMenuVO);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if( authoCheck == 0 && Boolean.FALSE.equals(EgovUserDetailsHelper.isRoleAdmin())) {
				// Alert 뷰 생성
				AlertView view = new AlertView();
				view.addStaticAttribute("message", "비정상적인 접근입니다.");
				view.addStaticAttribute("returnUrl", redirectUrl);
				ModelAndView modelAndView = new ModelAndView();
				modelAndView.setView(view);
				throw new ModelAndViewDefiningException(modelAndView);
			}
		}

		return super.preHandle(request, response, handler);
	}

}
