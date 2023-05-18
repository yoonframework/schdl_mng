package egovframework.com.menu.autho.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.egovframe.rte.fdl.property.EgovPropertyService;
import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springmodules.validation.commons.DefaultBeanValidator;

import com.google.gson.Gson;

import egovframework.com.cmm.ComDefaultVO;
import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.service.EgovCmmUseService;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.menu.autho.service.AuthoInfoVO;
import egovframework.com.menu.autho.service.AuthoService;
import egovframework.com.menu.program.service.MenuInfoVO;
import egovframework.com.uss.umt.service.EgovMberManageService;
import egovframework.com.uss.umt.service.FunctionAuthoInfoVO;

/**
 * 권한정보를 생성을 처리하는 비즈니스 구현 클래스
 * @author 개발환경 개발팀 이용
 * @since 2009.06.01
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2009.03.20  이  용          최초 생성
 *	 2011.07.01	 서준식	   	메뉴정보 삭제시 참조되고 있는 하위 메뉴가 있는지 체크하는 로직 추가
 *	 2011.07.27	 서준식	   	deleteMenuManageList() 메서드에서 메뉴 멀티 삭제 버그 수정
 *	 2011.08.26	 정진오		IncludedInfo annotation 추가
 *	 2011.10.07	 이기하		보안취약점 수정(파일 업로드시 엑셀파일만 가능하도록 추가)
 *  2015.05.28	조정국		메뉴리스트관리 선택시 "정상적으로 조회되었습니다"라는 alert창이 제일 먼저 뜨는것 수정 : 출력메시지 주석처리
 * </pre>
 */

@Controller
public class AuthoController {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthoController.class);

	/* Validator */
	@Autowired
	private DefaultBeanValidator beanValidator;
	/** EgovPropertyService */
    @Resource(name = "propertiesService")
    protected EgovPropertyService propertiesService;

	/** EgovMessageSource */
    @Resource(name="egovMessageSource")
    EgovMessageSource egovMessageSource;

    @Resource(name = "EgovCmmUseService")
    private EgovCmmUseService cmmUseService;

    @Resource(name = "authoService")
	private AuthoService authoService;

	/** mberManageService */
	@Resource(name = "mberManageService")
	private EgovMberManageService mberManageService;

    /**
	 * *메뉴생성목록을 조회한다.
	 *
	 * @param searchVO
	 *            ComDefaultVO
	 * @return 출력페이지정보 "/menu/autho/EgovMenuCreatManageSelect.do"
	 * @exception Exception
	 */
	@RequestMapping(value = "/menu/autho/EgovMenuCreatManageSelect.do")
	public String selectMenuCreatManagList(@ModelAttribute("searchVO") ComDefaultVO searchVO, ModelMap model) throws Exception {
		String resultMsg = "";
		// 0. Spring Security 사용자권한 처리
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		if (!isAuthenticated) {
			model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
			return "egovframework/com/uat/uia/EgovLoginUsr";
		}
		// 내역 조회
		/** EgovPropertyService.sample */
		searchVO.setPageUnit(propertiesService.getInt("pageUnit"));
		searchVO.setPageSize(propertiesService.getInt("pageSize"));

		/** pageing */
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
		paginationInfo.setPageSize(searchVO.getPageSize());

		searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		if (searchVO.getSearchKeyword() != null && !searchVO.getSearchKeyword().equals("")) {
			int IDcnt = authoService.selectUsrByPk(searchVO);
			if (IDcnt == 0) {
				resultMsg = egovMessageSource.getMessage("info.nodata.msg");
			} else {
				/* AuthorCode 검색 */
				AuthoInfoVO vo = new AuthoInfoVO();
				vo = authoService.selectAuthorByUsr(searchVO);
				searchVO.setSearchKeyword(vo.getAuthorCode());
			}
		}
		List<?> list_menumanage = authoService.selectMenuCreatManagList(searchVO);
		model.addAttribute("list_menumanage", list_menumanage);

		int totCnt = authoService.selectMenuCreatManagTotCnt(searchVO);
		paginationInfo.setTotalRecordCount(totCnt);
		model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("resultMsg", resultMsg);
		return ".adminLayout/메뉴권한관리/com/menu/autho/EgovMenuCreatManage";
	}

    /**
     * 프로그램 리스트를 조회한다.
     * @param searchVO ComDefaultVO
     * @return 출력페이지정보 "/menu/autho/authoManage"
     * @exception Exception
     */
    @RequestMapping(value="/menu/autho/authoManage.do")
    public String selectProgrmList(
    		@ModelAttribute("authoInfoVO") AuthoInfoVO authoInfoVO,
    		ModelMap model)
            throws Exception {
    	// 0. Spring Security 사용자권한 처리
    	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
    	if(!isAuthenticated) {
    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	return "egovframework/com/uat/uia/EgovLoginUsr";
    	}

    	List<MenuInfoVO> menuInfoList = authoService.selectMenuInfoList();
    	model.addAttribute("menuInfoList", menuInfoList);

    	List<AuthoInfoVO> authoInfoList = authoService.selectAuthoInfoList(authoInfoVO);
    	model.addAttribute("authoInfoList", authoInfoList);

    	model.addAttribute("resultVO", authoInfoVO);

    	ComDefaultVO vo = new ComDefaultVO();
    	vo.setPagingAt(false);
    	List<?> list_menumanage = authoService.selectMenuCreatManagList(vo);
		model.addAttribute("authoList", list_menumanage);

        return ".adminLayout/메뉴권한관리/com/menu/autho/EgovMenuAutoCreat";
    }

    /**
     * 메뉴정보에 대한 권한 설정
     * @param menuInfoVO
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/menu/autho/insertAuthoInfo.do", produces="application/text; charset=utf8")
	@ResponseBody
	public String insertAuthoInfo(
			@ModelAttribute("authoInfoVO") AuthoInfoVO authoInfoVO,
			ModelMap model) throws Exception {

		// 미인증 사용자에 대한 보안처리
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated ();
		if (!isAuthenticated) {
			return "redirect:/uat/uia/egovLoginUsr.do";
		}

		//메뉴 추가 등록
		authoService.insertAuthoInfo(authoInfoVO);

		Map<String, Object> map = new HashMap<String, Object>();
		//메뉴번호 리턴
		map.put ("result", "SUCCESS");
		Gson gson = new Gson();
		return gson.toJson (map);
	}

    /**
     * 메뉴정보에 대한 사용자 권한 설정
     * @param menuInfoVO
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/menu/autho/insertUserAuthoInfo.do", produces="application/text; charset=utf8")
    @ResponseBody
    public String insertUserAuthoInfo(
    		@ModelAttribute("authoInfoVO") AuthoInfoVO authoInfoVO,
    		ModelMap model) throws Exception {

    	// 미인증 사용자에 대한 보안처리
    	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated ();
    	if (!isAuthenticated) {
    		return "redirect:/uat/uia/egovLoginUsr.do";
    	}

    	//메뉴 추가 등록
    	authoService.insertUserAuthoInfo(authoInfoVO);

    	Map<String, Object> map = new HashMap<String, Object>();
    	//메뉴번호 리턴
    	map.put ("result", "SUCCESS");
    	Gson gson = new Gson();
    	return gson.toJson (map);
    }

    /**
     * 프로그램 리스트를 조회한다.
     * @param searchVO ComDefaultVO
     * @return 출력페이지정보 "/menu/autho/userAuthoManage"
     * @exception Exception
     */
    @RequestMapping(value="/menu/autho/userAuthoManage.do")
    public String selectUserProgrmList(
    		@ModelAttribute("authoInfoVO") AuthoInfoVO authoInfoVO,
    		ModelMap model)
            throws Exception {
    	// 0. Spring Security 사용자권한 처리
    	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
    	if(!isAuthenticated) {
    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	return "egovframework/com/uat/uia/EgovLoginUsr";
    	}

    	List<MenuInfoVO> menuInfoList = authoService.selectMenuInfoList();
    	model.addAttribute("menuInfoList", menuInfoList);

    	model.addAttribute("resultVO", authoInfoVO);

        return ".adminLayout/메뉴권한관리/com/menu/autho/EgovMenuUserCreat";
    }

    /**
     * 메뉴권한에 대한 사용자 정보 가져오기
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/menu/autho/selectUserInfo.do", produces="application/json; charset=utf8")
	@ResponseBody
	public String insertAuthoInfo(
			ModelMap model) throws Exception {

		// 미인증 사용자에 대한 보안처리
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated ();
		if (!isAuthenticated) {
			return "redirect:/uat/uia/egovLoginUsr.do";
		}

		//메뉴 추가 등록
		List<?> userList = authoService.selectUserList();

		//메뉴번호 리턴
		Gson gson = new Gson();
		return gson.toJson (userList);
	}

    /**
     * 권한별 메뉴 권한정보 조회
     * @param authoInfoVO
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/menu/autho/selectUserAuthoInfo.do", produces="application/text; charset=utf8")
	@ResponseBody
	public String selectUserAuthoInfo(
			@ModelAttribute("authoInfoVO") AuthoInfoVO authoInfoVO,
			ModelMap model) throws Exception {

		// 미인증 사용자에 대한 보안처리
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated ();
		if (!isAuthenticated) {
			return "redirect:/uat/uia/egovLoginUsr.do";
		}

		//메뉴 추가 등록
		List<AuthoInfoVO> authoInfoList = authoService.selectAuthoInfoList(authoInfoVO);

		//메뉴번호 리턴
		Gson gson = new Gson();
		return gson.toJson (authoInfoList);
	}

    /**
     * 메뉴별 기능 조회
     * @param functionAuthoInfoVO
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/menu/autho/userFunctionManage.do")
    public String userFunctionManage(
    		@ModelAttribute("functionAuthoInfoVO") FunctionAuthoInfoVO functionAuthoInfoVO,
    		ModelMap model)
            throws Exception {
    	// 0. Spring Security 사용자권한 처리
    	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
    	if(!isAuthenticated) {
    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	return "egovframework/com/uat/uia/EgovLoginUsr";
    	}

    	//메뉴 정보
    	List<MenuInfoVO> menuInfoList = authoService.selectMenuList();
    	model.addAttribute("menuInfoList", menuInfoList);

        return ".adminLayout/메뉴권한관리/com/menu/autho/EgovFunctionUserCreat";
    }

    /**
     * 메뉴의 기능에 따른 사용자 권한정보 조회
     * @param functionAuthoInfoVO
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/menu/autho/selectUserFunctionInfo.do", produces="application/text; charset=utf8")
	@ResponseBody
	public String selectUserFunctionInfo(
			@ModelAttribute("functionAuthoInfoVO") FunctionAuthoInfoVO functionAuthoInfoVO,
			ModelMap model) throws Exception {

		// 미인증 사용자에 대한 보안처리
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated ();
		if (!isAuthenticated) {
			return "redirect:/uat/uia/egovLoginUsr.do";
		}

		//메뉴 추가 등록
		List<FunctionAuthoInfoVO> authoInfoList = authoService.selectFunctionInfoList(functionAuthoInfoVO);

		//메뉴번호 리턴
		Gson gson = new Gson();
		return gson.toJson (authoInfoList);
	}

    /**
     * 메뉴 기능정보 대한 사용자 권한 설정
     * @param menuInfoVO
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/menu/autho/insertUserFuntionInfo.do", produces="application/text; charset=utf8")
    @ResponseBody
    public String insertUserFuntionInfo(
    		@ModelAttribute("functionAuthoInfoVO") FunctionAuthoInfoVO functionAuthoInfoVO,
    		ModelMap model) throws Exception {

    	// 미인증 사용자에 대한 보안처리
    	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated ();
    	if (!isAuthenticated) {
    		return "redirect:/uat/uia/egovLoginUsr.do";
    	}

    	//기능별 사용자 권한 등록
    	authoService.insertUserFuntionInfo(functionAuthoInfoVO);

    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put ("result", "SUCCESS");
    	Gson gson = new Gson();
    	return gson.toJson (map);
    }
}