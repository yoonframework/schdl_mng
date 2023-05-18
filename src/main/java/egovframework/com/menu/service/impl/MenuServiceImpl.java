package egovframework.com.menu.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.egovframe.rte.fdl.cmmn.exception.EgovBizException;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.menu.service.MenuService;
import egovframework.com.menu.service.MenuTreeVO;
import egovframework.com.menu.service.MenuVO;

@Service("MenuService")
public class MenuServiceImpl implements MenuService {
	@Resource(name = "MenuDAO")
	private MenuDAO menuDAO;

	@Override
	public List<MenuTreeVO> selectMenuTreeList() throws EgovBizException {
		return menuDAO.selectMenuTreeList();
	}

	@Override
	public List<MenuVO> selectAllMenuList(MenuVO menuVO) throws EgovBizException {
		return menuDAO.selectAllMenuList(menuVO);
	}

	@Override
	@Cacheable(value="sessionMenu", key = "{#menuVO.userId}")
	public List<MenuVO> selectSessionMenuList(MenuVO menuVO) throws EgovBizException {
		return menuDAO.selectSessionMenuList(menuVO);
	}

	@Override
	public int selectAuthoUrlCheck(MenuVO menuVO) throws EgovBizException {
		return menuDAO.selectAuthoUrlCheck(menuVO);
	}

	@Override
	public int selectMenuProgramCnt(MenuVO menuVO) throws EgovBizException {
		return menuDAO.selectMenuProgramCnt(menuVO);
	}

	@Override
	public List<MenuVO> selectMenuNaviList(MenuVO menuVO) throws EgovBizException {
		return menuDAO.selectMenuNaviList(menuVO);
	}

	@Override
	public List<MenuVO> selectMenuSameDepthList(MenuVO menuVO) throws EgovBizException {
		return menuDAO.selectMenuSameDepthList(menuVO);
	}

	@Override
	@Cacheable(value="functionAutho", key = "{#menuVO.userId}")
	public String selectUserFunctionList(MenuVO menuVO) throws EgovBizException {
		String result = menuDAO.selectUserFunctionList(menuVO);
		LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		if (loginVO != null && !StringUtils.isBlank(loginVO.getUniqId())) {
			String authorCode = loginVO.getAuthorCode();
			if (authorCode != null && authorCode.indexOf("ROLE_ADMIN") > -1) {
				result += ",ROLE_ADMIN";
			}
		}
		return result;
	}

	@Override
	public MenuVO selectMenuProgram(MenuVO menuVO) throws EgovBizException {
		return menuDAO.selectMenuProgram(menuVO);
	}
}
