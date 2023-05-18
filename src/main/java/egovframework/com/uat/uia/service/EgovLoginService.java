package egovframework.com.uat.uia.service;

import org.egovframe.rte.fdl.cmmn.exception.EgovBizException;

import egovframework.com.cmm.LoginVO;

/**
 * 일반 로그인을 처리하는 비즈니스 구현 클래스
 * @author 공통서비스 개발팀 박지욱
 * @since 2009.03.06
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2009.03.06  박지욱          최초 생성
 *  2011.08.31  JJY            경량환경 템플릿 커스터마이징버전 생성
 *
 *  </pre>
 */
public interface EgovLoginService {

	/**
     * 2011.08.26
	 * EsntlId를 이용한 로그인을 처리한다
	 * @param vo LoginVO
	 * @return LoginVO
	 * @exception Exception
	 */
    public LoginVO actionLoginByEsntlId(LoginVO vo) throws Exception;

	/**
	 * 일반 로그인을 처리한다
	 * @return LoginVO
	 *
	 * @param vo    LoginVO
	 * @exception Exception Exception
	 */
	public LoginVO actionLogin(LoginVO vo) throws Exception;

	/**
	 * 아이디를 찾는다.
	 * @return LoginVO
	 *
	 * @param vo    LoginVO
	 * @exception Exception Exception
	 */
	public LoginVO searchId(LoginVO vo) throws Exception;

	/**
	 * 비밀번호를 찾는다.
	 * @return boolean
	 *
	 * @param vo    LoginVO
	 * @exception Exception Exception
	 */
	public boolean searchPassword(LoginVO vo) throws Exception;

	/**
	 * 세션정보 갱신용
	 * @param vo LoginVO
	 * @return LoginVO
	 * @exception Exception
	 */
    LoginVO actionSessionReload(LoginVO vo) throws EgovBizException;

    /**
     * 로그인시 캐시삭제용
     * @Author : 임종호
     * @Date   : 2020. 8. 24.
     * @Method Name : loginCacheReset
     * @return : void
     */
    void loginCacheReset(LoginVO vo) throws EgovBizException;

    /**
     * 로그아웃시 캐시삭제용
     * @Author : 임종호
     * @Date   : 2020. 8. 24.
     * @Method Name : loginCacheReset
     * @return : void
     */
    void logoutCacheReset() throws EgovBizException;
}