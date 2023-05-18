package egovframework.com.menu.program.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.com.cmm.ComDefaultVO;
import egovframework.com.cmm.service.impl.EgovComAbstractDAO;
import egovframework.com.menu.program.service.MenuFunctionInfoVO;
import egovframework.com.menu.program.service.MenuInfoVO;
import egovframework.com.menu.program.service.MenuProgramInfo;
import egovframework.com.menu.program.service.MenuProgramInfoVO;

@Repository("ProgramDAO")
public class ProgramDAO extends EgovComAbstractDAO {
	
	/**
	 * 메뉴 정보 삭제
	 * @param menuInfoVo
	 * @throws Exception
	 */
	public void deleteMenuInfo (MenuInfoVO menuInfoVO) throws Exception {
		delete ("ProgramDAO.deleteMenuInfo", menuInfoVO);
	}
	
	/**
	 * 메뉴 프로그램 정보 삭제
	 * @param menuInfoVo
	 * @throws Exception
	 */
	public void deleteMenuProgramInfo (MenuProgramInfoVO menuProgramInfoVO) throws Exception {
		delete ("ProgramDAO.deleteMenuProgramInfo", menuProgramInfoVO);
	}
	
	/**
	 * 메뉴 프로그램 이동 시 수정
	 * @param menuInfoVo
	 * @throws Exception
	 */
	public void moveMenuInfo(MenuInfoVO menuInfoVO) throws Exception {
		update ("ProgramDAO.moveMenuInfo", menuInfoVO);
	}
	
	/**
	 * 트리에서 프로그램 메뉴정보 추가 등록
	 * @param menuInfoVo
	 * @throws Exception
	 */
	public void insertTreeMenuInfo(MenuInfoVO menuInfoVO) throws Exception {
		insert ("ProgramDAO.insertTreeMenuInfo", menuInfoVO);
	}
	
	/**
	 * 트리에서 메뉴 명 변경 시 수정
	 * @param menuInfoVO
	 * @throws Exception
	 */
	public void updateNewnNameTreeMenuInfo(MenuInfoVO menuInfoVO) throws Exception {
		update ("ProgramDAO.updateNewnNameTreeMenuInfo", menuInfoVO);
	}
	
	/**
	 * 메뉴 정보 가져오기
	 * @param menuInfoVO
	 * @return
	 * @throws Exception
	 */
	public MenuInfoVO selectMenuInfo(MenuInfoVO menuInfoVO) throws Exception {
		return selectOne ("ProgramDAO.selectMenuInfo", menuInfoVO);
	}
	
	/**
	 * 프로그램 메뉴 등록
	 * @param menuInfoVO
	 * @throws Exception
	 */
	public void insertMenuInfo(MenuInfoVO menuInfoVO) throws Exception {
		insert ("ProgramDAO.insertMenuInfo", menuInfoVO);
	}
	
	/**
	 * 프로그램 Url 등록
	 * @param menuProgramInfo
	 * @throws Exception
	 */
	public void insertMenuProgramInfo(MenuProgramInfoVO menuProgramInfoVO) throws Exception {
		insert ("ProgramDAO.insertMenuProgramInfo", menuProgramInfoVO);
	}
	
	/**
	 * 프로그램 메뉴 수정
	 * @param menuInfoVO
	 * @throws Exception
	 */
	public void updateMenuInfo(MenuInfoVO menuInfoVO) throws Exception {
		update ("ProgramDAO.updateMenuInfo", menuInfoVO);
	}
	
	/**
	 * 컨텐츠 목록을 조회
	 * @param vo ComDefaultVO
	 * @return List
	 * @exception Exception
	 */

	public List<?> selectContentsList(ComDefaultVO vo) throws Exception{
		return selectList ("ProgramDAO.selectContentsList", vo);
	}
	
	/**
	 * 컨텐츠 총건수를 조회한다.
	 * @param vo ComDefaultVO
	 * @return int
	 * @exception Exception
	 */
    public int selectContentsListTotCnt(ComDefaultVO vo)  {
        return (Integer)selectOne("ProgramDAO.selectContentsListTotCnt", vo);
    }
    
    /**
	 * 게시판 목록을 조회
	 * @param vo ComDefaultVO
	 * @return List
	 * @exception Exception
	 */

	public List<?> selectBbsList(ComDefaultVO vo) throws Exception{
		return selectList ("ProgramDAO.selectBbsList", vo);
	}
	
	/**
	 * 게시판 총건수를 조회한다.
	 * @param vo ComDefaultVO
	 * @return int
	 * @exception Exception
	 */
    public int selectBbsListTotCnt(ComDefaultVO vo)  {
        return (Integer)selectOne("ProgramDAO.selectBbsListTotCnt", vo);
    }
    
    /**
     * 메뉴 기능코드 중복체크
     * @param menuFunctionInfoVO
     * @return
     */
    public int selectDuplicateFunctionCode(MenuFunctionInfoVO menuFunctionInfoVO)  {
        return (Integer)selectOne("ProgramDAO.selectDuplicateFunctionCode", menuFunctionInfoVO);
    }
    
    /**
     * 메뉴 기능코드 등록
     * @param menuFunctionInfoVO
     * @throws Exception
     */
    public void insertMenuFunctionInfo(MenuFunctionInfoVO menuFunctionInfoVO) throws Exception {
		insert ("ProgramDAO.insertMenuFunctionInfo", menuFunctionInfoVO);
	}
    
    public void deleteMenuFunctionInfo (MenuFunctionInfoVO menuFunctionInfoVO) throws Exception {
		delete ("ProgramDAO.deleteMenuFunctionInfo", menuFunctionInfoVO);
	}
}
