package egovframework.com.menu.service.impl;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import egovframework.com.cmm.service.impl.EgovComAbstractDAO;
import egovframework.com.menu.service.MenuTreeVO;
import egovframework.com.menu.service.MenuVO;

@Repository("MenuDAO")
public class MenuDAO extends EgovComAbstractDAO {
	/**
	 * 메뉴 트리 생성을 위한 목록 데이터
	 * @return
	 * @throws Exception
	 */
	public List<MenuTreeVO> selectMenuTreeList() throws DataAccessException {
		return selectList("MenuDAO.selectMenuTreeList");
	}

	/**
	 * 모든 메뉴 목록 데이터
	 * @param menuVO
	 * @return
	 * @throws Exception
	 */
	public List<MenuVO> selectAllMenuList(MenuVO menuVO) throws DataAccessException {
		return selectList("MenuDAO.selectAllMenuList", menuVO);
	}

	/**
	 * 권한에 따른 메뉴 목록 데이터(사용자, 롤)
	 * @param menuVO
	 * @return
	 * @throws Exception
	 */
	public List<MenuVO> selectSessionMenuList(MenuVO menuVO) throws DataAccessException {
		return selectList("MenuDAO.selectSessionMenuList", menuVO);
	}

	/**
	 * URL로 권한 체크
	 * @param menuVO
	 * @return
	 * @throws Exception
	 */
	public int selectAuthoUrlCheck(MenuVO menuVO) throws DataAccessException {
		return selectOne("MenuDAO.selectAuthoUrlCheck", menuVO);
	}

	/**
	 * 권한 체크 대상인지 확인
	 * @param menuVO
	 * @return
	 * @throws Exception
	 */
	public int selectMenuProgramCnt(MenuVO menuVO) throws DataAccessException {
		return selectOne("MenuDAO.selectMenuProgramCnt", menuVO);
	}

	/**
	 * URL로 메뉴 정보 조회
	 *
	 * @Author : 임종호
	 * @Date : 2021. 9. 2.
	 * @Method Name : selectMenuProgram
	 * @return : MenuVO
	 */
	public MenuVO selectMenuProgram(MenuVO menuVO) throws DataAccessException {
		return selectOne("MenuDAO.selectMenuProgram", menuVO);
	}

	/**
	 * URL의 상위 메뉴정보 리스트
	 * @param menuVO
	 * @return
	 * @throws Exception
	 */
	public List<MenuVO> selectMenuNaviList(MenuVO menuVO) throws DataAccessException {
		return selectList("MenuDAO.selectMenuNaviList", menuVO);
	}

	/**
	 * URL에 해당하는 메뉴 depth의 데이터 목록
	 * @param menuVO
	 * @return
	 * @throws Exception
	 */
	public List<MenuVO> selectMenuSameDepthList(MenuVO menuVO) throws DataAccessException {
		return selectList("MenuDAO.selectMenuSameDepthList", menuVO);
	}

	/**
	 * 사용자에 권한에 대한 기능 목록 리스트
	 * @param menuVO
	 * @return
	 * @throws Exception
	 */
	public String selectUserFunctionList(MenuVO menuVO) throws DataAccessException {
		return selectOne("MenuDAO.selectUserFunctionList", menuVO);
	}
}
