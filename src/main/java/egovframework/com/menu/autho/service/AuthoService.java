package egovframework.com.menu.autho.service;

import java.util.List;

import egovframework.com.cmm.ComDefaultVO;
import egovframework.com.menu.program.service.MenuFunctionInfoVO;
import egovframework.com.menu.program.service.MenuInfoVO;
import egovframework.com.uss.umt.service.FunctionAuthoInfoVO;

/**
 * 권한정보관리 서비스
 * 
 * @author Song YoungHo
 */
public interface AuthoService {
	/**
	 * 프로그램 메뉴정보 삭제
	 * 
	 * @param menuInfoVo
	 * @return
	 * @throws Exception
	 */
	public List<MenuInfoVO> selectMenuInfoList() throws Exception;
	
	/**
	 * 메뉴정보에 대한 권한 설정
	 * @param authoInfoVO
	 * @throws Exception
	 */
	public void insertAuthoInfo(AuthoInfoVO authoInfoVO) throws Exception;
	
	/**
	 * 메뉴정보에 대한 사용자 권한 설정
	 * @param authoInfoVO
	 * @throws Exception
	 */
	public void insertUserAuthoInfo(AuthoInfoVO authoInfoVO) throws Exception;
	
	/**
	 * 권한별 메뉴 권한정보 조회
	 * @param authoInfoVO
	 * @return
	 * @throws Exception
	 */
	public List<AuthoInfoVO> selectAuthoInfoList(AuthoInfoVO authoInfoVO) throws Exception;
	
	/**
	 * 사용자별 권한 선택을 위한 사용자 조회
	 * @return
	 * @throws Exception
	 */
	public List<?> selectUserList() throws Exception;
	
	/**
	 * ID 존재여부를 조회
	 * @param vo ComDefaultVO
	 * @return int
	 * @exception Exception
	 */
	public int selectUsrByPk(ComDefaultVO vo) throws Exception;
	
	/**
	 * ID에 대한 권한코드를 조회
	 * @param vo ComDefaultVO
	 * @return MenuCreatVO
	 * @exception Exception
	 */
	public AuthoInfoVO selectAuthorByUsr(ComDefaultVO vo) throws Exception;
	
	/**
	 * 메뉴생성관리 목록을 조회
	 * @param vo ComDefaultVO
	 * @return List
	 * @exception Exception
	 */
	public List<?> selectMenuCreatManagList(ComDefaultVO vo) throws Exception;
	
	/**
	 * 메뉴생성관리 총건수를 조회한다.
	 * @param vo ComDefaultVO
	 * @return int
	 * @exception Exception
	 */
	public int selectMenuCreatManagTotCnt(ComDefaultVO vo) throws Exception;
	
	/**
	 * 메뉴  조회
	 * @return
	 * @throws Exception
	 */
	public List<MenuInfoVO> selectMenuList() throws Exception;
	
	/**
	 * 메뉴에 대한 기능 조회
	 * @return
	 * @throws Exception
	 */
	public List<MenuFunctionInfoVO> selectFunctionList() throws Exception;
	
	/**
	 * 메뉴별 기능에 대한 사용자 권한정보 조회
	 * @param authoInfoVO
	 * @return
	 * @throws Exception
	 */
	public List<FunctionAuthoInfoVO> selectFunctionInfoList(FunctionAuthoInfoVO functionAuthoInfoVO) throws Exception;
	
	/**
	 * 기능별 사용자 권한 등록
	 * @param functionAuthoInfoVO
	 * @throws Exception
	 */
	public void insertUserFuntionInfo(FunctionAuthoInfoVO functionAuthoInfoVO) throws Exception;
}
