package egovframework.com.cmm.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.egovframe.rte.fdl.string.EgovObjectUtil;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import egovframework.com.cmm.LoginVO;

/**
 * EgovUserDetails Helper 클래스
 *
 * @author sjyoon
 * @since 2009.06.01
 * @version 1.0
 * @see
 *
 *      <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2009.03.10  sjyoon    최초 생성
 *   2011.08.31  JJY            경량환경 템플릿 커스터마이징버전 생성
 *
 *      </pre>
 */

public class EgovUserDetailsHelper {
	private static final String LOGIN_URL = "redirect:/uat/uia/egovLoginUsr.do";
	private static final String INDEX_URL = "redirect:/index.do";

	/**
	 * 인증된 사용자객체를 VO형식으로 가져온다.
	 * 
	 * @return Object - 사용자 ValueObject
	 */
	public static Object getAuthenticatedUser() {
		return (LoginVO) RequestContextHolder.getRequestAttributes().getAttribute("LoginVO",
				RequestAttributes.SCOPE_SESSION) == null ? new LoginVO()
						: (LoginVO) RequestContextHolder.getRequestAttributes().getAttribute("LoginVO",
								RequestAttributes.SCOPE_SESSION);

	}

	/**
	 * 인증된 사용자의 권한 정보를 가져온다. 예) [ROLE_ADMIN, ROLE_USER, ROLE_A, ROLE_B,
	 * ROLE_RESTRICTED, IS_AUTHENTICATED_FULLY, IS_AUTHENTICATED_REMEMBERED,
	 * IS_AUTHENTICATED_ANONYMOUSLY]
	 * 
	 * @return List - 사용자 권한정보 목록
	 */
	public static List<String> getAuthorities() {
		List<String> listAuth = new ArrayList<String>();

		if (EgovObjectUtil.isNull(
				RequestContextHolder.getRequestAttributes().getAttribute("LoginVO", RequestAttributes.SCOPE_SESSION))) {
			// log.debug("## authentication object is null!!");
			return null;
		}

		return listAuth;
	}

	/**
	 * 인증된 사용자 여부를 체크한다.
	 * 
	 * @return Boolean - 인증된 사용자 여부(TRUE / FALSE)
	 */
	public static Boolean isAuthenticated() {
		if (EgovObjectUtil.isNull(
				RequestContextHolder.getRequestAttributes().getAttribute("LoginVO", RequestAttributes.SCOPE_SESSION))) {
			// log.debug("## authentication object is null!!");
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}
	
	public static Boolean isRoleAdmin() {
		String[] roleList = {Authory.ROLE_ADMIN};
		return isRole(roleList);
	}

	/**
	 * 로그인 페이지 이동
	 * 
	 * @Author : 임종호
	 * @Date : 2021. 7. 5.
	 * @Method Name : getLoginPage
	 * @return : String
	 */
	public static String getLoginPage() {
		return LOGIN_URL;
	}

	public static String getIndexPage() {
		return INDEX_URL;
	}

	/**
	 * 특정 권한 여부를 체크한다.
	 * 
	 * @Author : 임종호
	 * @Date : 2021. 7. 5.
	 * @Method Name : isRole
	 * @return : Boolean - 특정 권한 여부 (true / false)
	 */
	public static Boolean isRole(final String strAutho) {
		if (StringUtils.isBlank(strAutho)) {
			return false;
		}
		List<String> authorList = getAuthorities();
		if (authorList != null) {
			for (String s : authorList) {
				if (s.indexOf(strAutho) != -1) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 특정 권한 배열 여부를 체크한다.
	 * 
	 * @Author : 임종호
	 * @Date : 2021. 7. 5.
	 * @Method Name : isRole
	 * @return : Boolean - 특정 권한 여부 (true / false)
	 */
	public static Boolean isRole(final String[] arrAutho) {
		if (arrAutho.length == 0) {
			return false;
		}
		List<String> authorList = getAuthorities();
		if (authorList != null) {
			for (String s : authorList) {
				for (String a : arrAutho) {
					if (s.indexOf(a) != -1) {
						return true;
					}
				}
			}
		}
		return false;
	}
}
