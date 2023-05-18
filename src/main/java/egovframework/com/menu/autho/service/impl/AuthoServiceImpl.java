package egovframework.com.menu.autho.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import egovframework.com.cmm.ComDefaultVO;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.menu.autho.service.AuthoInfoVO;
import egovframework.com.menu.autho.service.AuthoService;
import egovframework.com.menu.program.service.MenuFunctionInfoVO;
import egovframework.com.menu.program.service.MenuInfoVO;
import egovframework.com.uss.umt.service.FunctionAuthoInfoVO;

@Service("authoService")
public class AuthoServiceImpl implements AuthoService {
	@Resource(name = "authoDAO")
	private AuthoDAO authoDAO;

	@Resource(name = "egovAuthoListIdGnrService")
    private EgovIdGnrService egovAuthoListIdGnrService;

	@Resource(name = "egovFunctionAuthoIdGnrService")
    private EgovIdGnrService egovFunctionAuthoIdGnrService;

	@Override
	public List<MenuInfoVO> selectMenuInfoList() throws Exception {
		// TODO Auto-generated method stub
		return authoDAO.selectMenuInfoList();
	}

	@Override
	@CacheEvict(value="sessionMenu", allEntries = true)
	public void insertAuthoInfo(AuthoInfoVO authoInfoVO) throws Exception {
		// TODO Auto-generated method stub
		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		//권한에 대한 메뉴정보 권한 삭제
		authoDAO.deleteAuthoInfo(authoInfoVO);

		if(authoInfoVO.getAuthoInfoList() != null) {
			for(AuthoInfoVO vo : authoInfoVO.getAuthoInfoList()) {
				vo.setAuthorSeq(egovAuthoListIdGnrService.getNextStringId());
				vo.setFrstRegisterId(user.getUniqId());
				authoDAO.insertAuthoInfo(vo);
			}
		}
	}

	@Override
	@CacheEvict(value="sessionMenu", allEntries = true)
	public void insertUserAuthoInfo(AuthoInfoVO authoInfoVO) throws Exception {
		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		//권한에 대한 메뉴정보 권한 삭제
		for(AuthoInfoVO list : authoInfoVO.getAuthoInfoList()) {
			authoDAO.deleteAuthoInfo(list);

			if (list.getAuthoInfoList() != null) {
				for(AuthoInfoVO vo : list.getAuthoInfoList()) {
					vo.setAuthorSeq(egovAuthoListIdGnrService.getNextStringId());
					vo.setFrstRegisterId(user.getUniqId());
					authoDAO.insertAuthoInfo(vo);
				}
			}
		}
	}

	@Override
	public List<AuthoInfoVO> selectAuthoInfoList(AuthoInfoVO authoInfoVO) throws Exception {
		// TODO Auto-generated method stub
		return authoDAO.selectAuthoInfoList(authoInfoVO);
	}

	@Override
	public List<?> selectUserList() throws Exception {
		// TODO Auto-generated method stub
		return authoDAO.selectUserList();
	}

	@Override
	public int selectUsrByPk(ComDefaultVO vo) throws Exception {
		return authoDAO.selectUsrByPk(vo);
	}

	@Override
	public AuthoInfoVO selectAuthorByUsr(ComDefaultVO vo) throws Exception {
		// TODO Auto-generated method stub
		return authoDAO.selectAuthorByUsr(vo);
	}

	/**
	 * 메뉴생성관리 목록을 조회
	 * @param vo ComDefaultVO
	 * @return List
	 * @exception Exception
	 */
	public List<?> selectMenuCreatManagList(ComDefaultVO vo) throws Exception {
		return authoDAO.selectMenuCreatManagList(vo);
	}

	/**
	 * 메뉴생성관리 총건수를 조회한다.
	 * @param vo ComDefaultVO
	 * @return int
	 * @exception Exception
	 */
	public int selectMenuCreatManagTotCnt(ComDefaultVO vo) throws Exception {
		return authoDAO.selectMenuCreatManagTotCnt(vo);
	}

	@Override
	public List<MenuInfoVO> selectMenuList() throws Exception {
		// TODO Auto-generated method stub
		return authoDAO.selectMenuList();
	}

	@Override
	public List<MenuFunctionInfoVO> selectFunctionList() throws Exception {
		// TODO Auto-generated method stub
		return authoDAO.selectFunctionList();
	}

	@Override
	public List<FunctionAuthoInfoVO> selectFunctionInfoList(FunctionAuthoInfoVO functionAuthoInfoVO) throws Exception {
		// TODO Auto-generated method stub
		return authoDAO.selectFunctionInfoList(functionAuthoInfoVO);
	}

	@Override
	@CacheEvict(value="functionAutho", allEntries = true)
	public void insertUserFuntionInfo(FunctionAuthoInfoVO functionAuthoInfoVO) throws Exception {
		//권한에 대한 메뉴정보 권한 삭제
		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		for(FunctionAuthoInfoVO list : functionAuthoInfoVO.getFunctionList()) {
			authoDAO.deleteFunctionAuthoInfo(list);
			if (list.getFunctionList() != null) {
				for(FunctionAuthoInfoVO vo : list.getFunctionList()) {
					vo.setFncAuthorSeq(egovFunctionAuthoIdGnrService.getNextStringId());
					vo.setFrstRegisterId(user.getUniqId());
					authoDAO.insertFunctionAuthoInfo(vo);
				}
			}
		}
	}
}
