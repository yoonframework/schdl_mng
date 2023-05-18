package egovframework.mng.utl.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import egovframework.com.cmm.LoginVO;

/**
 * 로그인 세션 관리
 *
 * @since 2022. 1. 24.
 * @author 김기윤
 *
 *         <PRE>
 * -----------------------
 * 개정이력
 * 2022. 1. 24. 김기윤 : 최초작성
 *         </PRE>
 */
@WebListener
public class SessionConfig implements HttpSessionListener {
	private final Logger log = LoggerFactory.getLogger(SessionConfig.class);

	private static final Map<String, HttpSession> SESSION_MAP = new ConcurrentHashMap<>();

	@Override
	public void sessionCreated(HttpSessionEvent httpSessionEvent) {
		log.debug("SessionConfig sessionCreated");
		SESSION_MAP.put(httpSessionEvent.getSession().getId(), httpSessionEvent.getSession());
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
		log.debug("SessionConfig sessionDestroyed");
		if (SESSION_MAP.get(httpSessionEvent.getSession().getId()) != null) {
			SESSION_MAP.get(httpSessionEvent.getSession().getId()).invalidate();
			SESSION_MAP.remove(httpSessionEvent.getSession().getId());
		}
	}

	/**
	 * 중복로그인 방지
	 * 
	 * @Author : 김기윤
	 * @Date : 2022. 1. 24.
	 * @Method Name : sessionDpcnCheck
	 * @return : String
	 */
	public synchronized static String sessionDpcnCheck(String type, LoginVO loginVO) {
		String result = "";
		for (String key : SESSION_MAP.keySet()) {
			HttpSession httpSession = SESSION_MAP.get(key);
			if (httpSession != null && httpSession.getAttribute(type) != null
					&& ((LoginVO) httpSession.getAttribute(type)).getUniqId().equals(loginVO.getUniqId())) {
				result = key.toString();
			}
		}
		sessionEnfrcRemove(result);
		return result;
	}

	/**
	 * 세션 강제 삭제
	 * 
	 * @Author : 김기윤
	 * @Date : 2022. 1. 24.
	 * @Method Name : sessionEnfrcRemove
	 * @return : void
	 */
	private static void sessionEnfrcRemove(String key) {
		if (key != null && key.length() > 0) {
			SESSION_MAP.get(key).invalidate();
			SESSION_MAP.remove(key);
		}
	}
}