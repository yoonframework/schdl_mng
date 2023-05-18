package egovframework.com.menu.program.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.springframework.stereotype.Service;

import egovframework.com.cmm.ComDefaultVO;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.menu.program.service.MenuFunctionInfoVO;
import egovframework.com.menu.program.service.MenuInfoVO;
import egovframework.com.menu.program.service.MenuProgramInfoVO;
import egovframework.com.menu.program.service.ProgramService;

@Service("programService")
public class ProgramServiceImpl implements ProgramService {
	@Resource(name = "ProgramDAO")
	private ProgramDAO programDAO;

	@Resource(name = "egovProgrListIdGnrService")
    private EgovIdGnrService egovProgrListIdGnrService;

	@Override
	public void deleteMenuInfo (MenuInfoVO menuInfoVO) throws Exception {
		//메뉴 프로그램 삭제
		MenuProgramInfoVO menuProgramInfoVO = new MenuProgramInfoVO();
		menuProgramInfoVO.setMenuNo(menuInfoVO.getMenuNo());
//		menuProgramInfoVO.setSysGroupCd(menuInfoVO.getSysGroupCd());
		programDAO.deleteMenuProgramInfo(menuProgramInfoVO);
		//메뉴 정보 삭제
		programDAO.deleteMenuInfo(menuInfoVO);
	}

	@Override
	public void moveMenuInfo(MenuInfoVO menuInfoVO) throws Exception {
		// TODO Auto-generated method stub
		programDAO.moveMenuInfo(menuInfoVO);
	}

	@Override
	public void insertTreeMenuInfo(MenuInfoVO menuInfoVO) throws Exception {
		// TODO Auto-generated method stub
		menuInfoVO.setMenuNo(egovProgrListIdGnrService.getNextStringId());
		programDAO.insertTreeMenuInfo(menuInfoVO);
	}

	@Override
	public void updateNewnNameTreeMenuInfo(MenuInfoVO menuInfoVO) throws Exception {
		// TODO Auto-generated method stub
		programDAO.updateNewnNameTreeMenuInfo(menuInfoVO);
	}

	@Override
	public MenuInfoVO selectMenuInfo(MenuInfoVO menuInfoVO) throws Exception {
		// TODO Auto-generated method stub
		return programDAO.selectMenuInfo(menuInfoVO);
	}

	@Override
	public void insertMenuInfo(MenuInfoVO menuInfoVO) throws Exception {
		// TODO Auto-generated method stub
		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

		if (menuInfoVO.getMenuNo() == null || menuInfoVO.getMenuNo().equals("")) {
			//menuNo 생성
			menuInfoVO.setMenuNo(egovProgrListIdGnrService.getNextStringId());
			menuInfoVO.setFrstRegisterId(user.getUniqId());
			menuInfoVO.setLastUpdusrId(user.getUniqId());
			//프로그램 메뉴 등록
			programDAO.insertMenuInfo(menuInfoVO);

			//프로그램 메뉴 Url 등록
//			for(MenuProgramInfo menuProgramInfo : menuInfoVO.getMenuProgramList()) {
//				programDAO.insertMenuProgramInfo(menuProgramInfo);
//			}
		} else {
			//수정
			menuInfoVO.setLastUpdusrId(user.getUniqId());
			programDAO.updateMenuInfo(menuInfoVO);
			//프로그램 메뉴 삭제
//			programDAO.deleteMenuProgramInfo(menuInfoVO);
			//프로그램 메뉴 Url 등록
//			for(MenuProgramInfo menuProgramInfo : menuInfoVO.getMenuProgramList()) {
//				menuProgramInfo.setMenuNo(menuInfoVO.getMenuNo());
//				programDAO.insertMenuProgramInfo(menuProgramInfo);
//			}
		}

		if (menuInfoVO.getMenuProgramList() != null) {
			for(MenuProgramInfoVO vo : menuInfoVO.getMenuProgramList()) {
				if (!vo.getProgrUrl().equals("") && vo.getProgrUrl() != null) {
					vo.setMenuNo(menuInfoVO.getMenuNo());
//					vo.setSysGroupCd(menuInfoVO.getSysGroupCd());
					vo.setFrstRegisterId(user.getUniqId());
					vo.setLastUpdusrId(user.getUniqId());
					programDAO.insertMenuProgramInfo(vo);
				}
			}
		}

		if (menuInfoVO.getDeleteProgramList() != null) {
			for(MenuProgramInfoVO vo : menuInfoVO.getDeleteProgramList()) {
				vo.setMenuNo(menuInfoVO.getMenuNo());
//				vo.setSysGroupCd(menuInfoVO.getSysGroupCd());
				programDAO.deleteMenuProgramInfo(vo);
			}
		}

		if (menuInfoVO.getMenuFunctionList() != null) {
			for(MenuFunctionInfoVO vo : menuInfoVO.getMenuFunctionList()) {
				if (!vo.getFunctionCode().equals("") && vo.getFunctionCode() != null) {
					vo.setMenuNo(menuInfoVO.getMenuNo());
//					vo.setSysGroupCd(menuInfoVO.getSysGroupCd());
					vo.setFrstRegisterId(user.getUniqId());
					vo.setLastUpdusrId(user.getUniqId());
					programDAO.insertMenuFunctionInfo(vo);
				}
			}
		}

		if (menuInfoVO.getDeleteFunctionList() != null) {
			for(MenuFunctionInfoVO vo : menuInfoVO.getDeleteFunctionList()) {
				vo.setMenuNo(menuInfoVO.getMenuNo());
//				vo.setSysGroupCd(menuInfoVO.getSysGroupCd());
				programDAO.deleteMenuFunctionInfo(vo);
			}
		}
	}

	@Override
	public void deleteMenuProgramInfo(MenuProgramInfoVO menuProgramInfoVO) throws Exception {
		// TODO Auto-generated method stub
		programDAO.deleteMenuProgramInfo(menuProgramInfoVO);
	}

	/**
	 * 컨텐츠 목록을 조회
	 * @param vo ComDefaultVO
	 * @return List
	 * @exception Exception
	 */
	@Override
	public List<?> selectContentsList(ComDefaultVO vo) throws Exception {
   		return programDAO.selectContentsList(vo);
	}

	/**
	 * 컨텐츠 목록 수를 조회한다.
	 * @param vo  ComDefaultVO
	 * @return Integer
	 * @exception Exception
	 */
    @Override
	public int selectContentsListTotCnt(ComDefaultVO vo) throws Exception {
        return programDAO.selectContentsListTotCnt(vo);
	}

    /**
	 * 게시판 목록을 조회
	 * @param vo ComDefaultVO
	 * @return List
	 * @exception Exception
	 */
	@Override
	public List<?> selectBbsList(ComDefaultVO vo) throws Exception {
   		return programDAO.selectBbsList(vo);
	}

	/**
	 * 게시판 목록 수를 조회한다.
	 * @param vo  ComDefaultVO
	 * @return Integer
	 * @exception Exception
	 */
    @Override
	public int selectBbsListTotCnt(ComDefaultVO vo) throws Exception {
        return programDAO.selectBbsListTotCnt(vo);
	}

    /**
     * 메뉴 기능코드 중복체크
     */
	@Override
	public int selectDuplicateFunctionCode(MenuFunctionInfoVO menuFunctionInfoVO) throws Exception {
		// TODO Auto-generated method stub
		return programDAO.selectDuplicateFunctionCode(menuFunctionInfoVO);
	}
}
