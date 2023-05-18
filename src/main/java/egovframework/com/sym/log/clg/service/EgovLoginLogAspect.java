package egovframework.com.sym.log.clg.service;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;

import javax.annotation.Resource;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * @Class Name : EgovLoginLogAspect.java
 * @Description : 시스템 로그 생성을 위한 ASPECT 클래스
 * @Modification Information
 *
 *    수정일         수정자         수정내용
 *    -------        -------     -------------------
 *    2009. 3. 11.   이삼섭         최초생성
 *    2011. 7. 01.   이기하         패키지 분리(sym.log -> sym.log.clg)
 *
 * @author 공통 서비스 개발팀 이삼섭
 * @since 2009. 3. 11.
 * @version
 * @see
 *
 */
@Aspect
@Component
public class EgovLoginLogAspect {

	@Resource(name="EgovLoginLogService")
	private EgovLoginLogService loginLogService;

	/**
	 * 로그인 로그정보를 생성한다.
	 * EgovLoginController.actionMain Method
	 *
	 * @param
	 * @return void
	 * @throws Exception
	 */
	@After(value = "execution(public * egovframework.com.uat.uia.web.EgovLoginAPIController.actionLogin(..))")
	public void logLogin() throws Throwable {

		String uniqId = "";
		String ip = "";

		/* Authenticated  */
        Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
    	if(isAuthenticated.booleanValue()) {
			LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
			uniqId = user.getUniqId();
			ip = user.getIp();
			LoginLog loginLog = new LoginLog();
			loginLog.setLoginId(uniqId);
			loginLog.setLoginIp(ip);
			loginLog.setLoginMthd("I"); // 로그인:I, 로그아웃:O, 비밀번호변경: C
			loginLog.setErrOccrrAt("N");
			loginLog.setErrorCode("");
			loginLogService.logInsertLoginLog(loginLog);
    	}
	}

	/**
	 * 로그아웃 로그정보를 생성한다.
	 * EgovLoginController.actionLogout Method
	 *
	 * @param
	 * @return void
	 * @throws Exception
	 */
	@Before(value = "execution(public * egovframework.com.uat.uia.web.EgovLoginAPIController.actionLogoutAPI(..))")
	public void logLogout() throws Throwable {

		String uniqId = "";
		String ip = "";

		/* Authenticated  */
        Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
    	if(isAuthenticated.booleanValue()) {
			LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
			uniqId = user.getUniqId();
			ip = user.getIp();

			LoginLog loginLog = new LoginLog();
	    	loginLog.setLoginId(uniqId);
	        loginLog.setLoginIp(ip);
	        loginLog.setLoginMthd("O"); // 로그인:I, 로그아웃:O
	        loginLog.setErrOccrrAt("N");
	        loginLog.setErrorCode("");
	        loginLogService.logInsertLoginLog(loginLog);
    	}
	}
}
