package egovframework.com.menu.service;

import java.util.List;

import org.egovframe.rte.fdl.cmmn.exception.EgovBizException;

/**
 * 메뉴 서비스
 *
 * @author Jun
 */
public interface MenuService {
	/**
	 * 메뉴 트리 생성을 위한 목록 데이터
	 * @return
	 * @throws Exception
	 */
	public List<MenuTreeVO> selectMenuTreeList() throws EgovBizException;

	/**
	 * 모든 메뉴 목록 데이터
	 * @param menuVO
	 * @return
	 * @throws Exception
	 */
	public List<MenuVO> selectAllMenuList(MenuVO menuVO) throws EgovBizException;

	/**
	 * 권한에 따른 메뉴 목록 데이터(사용자, 롤)
	 * @param menuVO
	 * @return
	 * @throws Exception
	 */
	public List<MenuVO> selectSessionMenuList(MenuVO menuVO) throws EgovBizException;

	/**
	 * URL로 권한 체크
	 * @param menuVO
	 * @return
	 * @throws Exception
	 */
	public int selectAuthoUrlCheck(MenuVO menuVO) throws EgovBizException;

	/**
	 * URL로 메뉴 정보 조회
	 * @Author : 임종호
	 * @Date : 2021. 9. 2.
	 * @Method Name : selectMenuProgram
	 * @return : MenuVO
	 */
	public MenuVO selectMenuProgram(MenuVO menuVO) throws EgovBizException;

	/**
	 * 권한 체크 대상인지 확인
	 * @param menuVO
	 * @return
	 * @throws Exception
	 */
	public int selectMenuProgramCnt(MenuVO menuVO) throws EgovBizException;

	/**
	 * URL의 상위 메뉴정보 리스트
	 * @param menuVO
	 * @return
	 * @throws Exception
	 */
	public List<MenuVO> selectMenuNaviList(MenuVO menuVO) throws EgovBizException;

	/**
	 * URL에 해당하는 메뉴 depth의 데이터 목록
	 * @param menuVO
	 * @return
	 * @throws Exception
	 */
	public List<MenuVO> selectMenuSameDepthList(MenuVO menuVO) throws EgovBizException;

	/**
	 * 사용자에 권한에 대한 기능 목록 리스트
	 * @param menuVO
	 * @return
	 * @throws Exception
	 */
	public String selectUserFunctionList(MenuVO menuVO) throws EgovBizException;
}