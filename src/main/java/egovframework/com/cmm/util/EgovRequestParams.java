package egovframework.com.cmm.util;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

/**
 * Request의 파라메터를 확인하는 클래스
 *
 * @since 2021. 7. 14.
 * @author 임종호
 *
 *         <PRE>
 * -----------------------
 * 개정이력
 * 2021. 7. 14. 임종호 : 최초작성
 *         </PRE>
 */
public class EgovRequestParams {

	private EgovRequestParams() {
		throw new IllegalStateException("EgovRequestParams Util class");
	}

	/**
	 * Request의 파라메터를 확인
	 *
	 * @Author : 임종호
	 * @Date : 2021. 7. 14.
	 * @Method Name : info
	 * @return : void
	 */
	public static void info(HttpServletRequest request) {
		Enumeration<String> params = request.getParameterNames();
		EgovBasicLogger.ignore("----------------------------------------------------");
		while (params.hasMoreElements()) {
			String name = params.nextElement();
			EgovBasicLogger.ignore(name + " : " + request.getParameter(name));
		}
		EgovBasicLogger.ignore("----------------------------------------------------");
	}
}
