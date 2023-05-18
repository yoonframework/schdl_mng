package egovframework.com.menu.program.service;

import java.util.List;

import egovframework.com.cmm.ComDefaultVO;

/**
 * 메뉴정보관리 서비스
 * 
 * @author Song YoungHo
 */
public interface ProgramService {
	/**
	 * 프로그램 메뉴정보 삭제
	 * 
	 * @param menuInfoVo
	 * @return
	 * @throws Exception
	 */
	public void deleteMenuInfo(MenuInfoVO menuInfoVO) throws Exception;
	
	/**
	 * 프로그램 이동 시 수정
	 * @param menuInfoVo
	 * @throws Exception
	 */
	public void moveMenuInfo(MenuInfoVO menuInfoVO) throws Exception;
	
	/**
	 * 트리에서 프로그램 메뉴정보 추가 등록
	 * @param menuInfoVo
	 * @throws Exception
	 */
	public void insertTreeMenuInfo(MenuInfoVO menuInfoVO) throws Exception;
	
	/**
	 * 트리에서 메뉴 명 변경 시 수정
	 * @param menuInfoVo
	 * @throws Exception
	 */
	public void updateNewnNameTreeMenuInfo(MenuInfoVO menuInfoVO) throws Exception;
	
	/**
	 * 메뉴 정보 가져오기
	 * @param menuInfoVo
	 * @return
	 * @throws Exception
	 */
	public MenuInfoVO selectMenuInfo(MenuInfoVO menuInfoVO) throws Exception;
	
	/**
	 * 프로그램 메뉴 등록
	 * @param menuInfoVO
	 * @throws Exception
	 */
	public void insertMenuInfo(MenuInfoVO menuInfoVO) throws Exception;
	
	/**
	 * 프로그램 URL 삭제
	 * @param menuInfoVO
	 * @throws Exception
	 */
	public void deleteMenuProgramInfo(MenuProgramInfoVO menuProgramInfoVO) throws Exception;
	
	/**
	 * 컨텐츠 게시물 목록을 조회
	 * @param vo ComDefaultVO
	 * @return List
	 * @exception Exception
	 */
	public List<?> selectContentsList(ComDefaultVO vo) throws Exception;
	
	/**
	 * 컨텐츠 게시물 목록 수를 조회한다.
	 * @param vo ComDefaultVO
	 * @return int
	 * @exception Exception
	 */
	public int selectContentsListTotCnt(ComDefaultVO vo) throws Exception;
	
	/**
	 * 게시판 목록을 조회
	 * @param vo ComDefaultVO
	 * @return List
	 * @exception Exception
	 */
	public List<?> selectBbsList(ComDefaultVO vo) throws Exception;
	
	/**
	 * 게시판 목록 수를 조회한다.
	 * @param vo ComDefaultVO
	 * @return int
	 * @exception Exception
	 */
	public int selectBbsListTotCnt(ComDefaultVO vo) throws Exception;
	
	/**
	 * 메뉴 기능코드 중복체크
	 * @param menuFunctionInfoVO
	 * @return
	 * @throws Exception
	 */
	public int selectDuplicateFunctionCode(MenuFunctionInfoVO menuFunctionInfoVO) throws Exception;
}
