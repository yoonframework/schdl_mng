package egovframework.com.menu.autho.service.impl;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Repository;

import egovframework.com.cmm.ComDefaultVO;
import egovframework.com.cmm.service.impl.EgovComAbstractDAO;
import egovframework.com.menu.autho.service.AuthoInfoVO;
import egovframework.com.menu.program.service.MenuFunctionInfoVO;
import egovframework.com.menu.program.service.MenuInfoVO;
import egovframework.com.uss.umt.service.FunctionAuthoInfoVO;

@Repository("authoDAO")
public class AuthoDAO extends EgovComAbstractDAO {
	
	/**
	 * 메뉴 정보 삭제
	 * @param menuInfoVo
	 * @throws Exception
	 */
	public List<MenuInfoVO> selectMenuInfoList () throws Exception {
		return selectList ("MenuAuthoDAO.selectMenuInfo");
	}
	
	/**
	 * 권한별 메뉴정보 권한 삭제
	 * @param authoInfoVO
	 * @throws Exception
	 */
	public void deleteAuthoInfo (AuthoInfoVO authoInfoVO) throws Exception {
		delete ("MenuAuthoDAO.deleteAuthoInfo", authoInfoVO);
	}
	
	/**
	 * 권한별 메뉴정보 권한 등록
	 * @param authoInfoVO
	 * @throws Exception
	 */
	public void insertAuthoInfo (AuthoInfoVO authoInfoVO) throws Exception {
		insert ("MenuAuthoDAO.insertAuthoInfo", authoInfoVO);
	}
	
	/**
	 * 권한별 메뉴 권한정보 조회
	 * @param authoInfoVO
	 * @return
	 * @throws Exception
	 */
	public List<AuthoInfoVO> selectAuthoInfoList (AuthoInfoVO authoInfoVO) throws Exception {
		return selectList ("MenuAuthoDAO.selectAuthoInfoList", authoInfoVO);
	}
	
	/**
	 * 사용자별 권한 선택을 위한 사용자 조회
	 * @return
	 * @throws Exception
	 */
	public List<?> selectUserList () throws Exception {
		return selectList ("MenuAuthoDAO.selectUserList");
	}
	
	/**
	 * ID 존재여부를 조회
	 * @param vo MenuManageVO
	 * @return int
	 * @exception Exception
	 */
	public int selectUsrByPk(ComDefaultVO vo) throws Exception{
		return (Integer)selectOne("MenuAuthoDAO.selectUsrByPk", vo);
	}
	
	/**
	 * ID에 대한 권한코드를 조회
	 * @param vo MenuCreatVO
	 * @return AuthoInfoVO
	 * @exception Exception
	 */
	public AuthoInfoVO selectAuthorByUsr(ComDefaultVO vo) throws Exception{
		return selectOne("MenuAuthoDAO.selectAuthorByUsr", vo);
	}
	
	/**
	 * 메뉴생성관리 내역을 조회
	 * @param vo ComDefaultVO
	 * @return List
	 * @exception Exception
	 */
	public List<?> selectMenuCreatManagList(ComDefaultVO vo) throws Exception{
		return selectList ("MenuAuthoDAO.selectMenuCreatManageList_D", vo);
	}
	
	/**
	 * 메뉴생성관리 총건수를 조회한다.
	 * @param vo ComDefaultVO
	 * @return int
	 * @exception Exception
	 */
    public int selectMenuCreatManagTotCnt(ComDefaultVO vo) {
        return (Integer)selectOne("MenuAuthoDAO.selectMenuCreatManageTotCnt_S", vo);
    }
    
    /**
     * 메뉴 조회
     * @return
     * @throws Exception
     */
    public List<MenuInfoVO> selectMenuList () throws Exception {
		return selectList ("MenuAuthoDAO.selectMenuList");
	}
    
    /**
     * 메뉴에 대한 기능 조회
     * @return
     * @throws Exception
     */
    public List<MenuFunctionInfoVO> selectFunctionList () throws Exception {
		return selectList ("MenuAuthoDAO.selectFunctionList");
	}
    
    /**
     * 메뉴별 기능에 대한 사용자 권한정보 조회
     * @param functionAuthoInfoVO
     * @return
     * @throws Exception
     */
    public List<FunctionAuthoInfoVO> selectFunctionInfoList (FunctionAuthoInfoVO functionAuthoInfoVO) throws Exception {
		return selectList ("MenuAuthoDAO.selectFunctionInfoList", functionAuthoInfoVO);
	}
    
    /**
     * 기능별 사용자 권한 삭제
     * @param functionAuthoInfoVO
     * @throws Exception
     */
    public void deleteFunctionAuthoInfo (FunctionAuthoInfoVO functionAuthoInfoVO) throws Exception {
		delete ("MenuAuthoDAO.deleteFunctionAuthoInfo", functionAuthoInfoVO);
	}
    
    /**
     * 기능별 사용자 권한 등록
     * @param functionAuthoInfoVO
     * @throws Exception
     */
    public void insertFunctionAuthoInfo (FunctionAuthoInfoVO functionAuthoInfoVO) throws Exception {
		insert ("MenuAuthoDAO.insertFunctionAuthoInfo", functionAuthoInfoVO);
	}
}
