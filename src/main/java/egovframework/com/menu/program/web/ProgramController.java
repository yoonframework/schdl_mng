package egovframework.com.menu.program.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Resource;

import org.egovframe.rte.fdl.property.EgovPropertyService;
import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springmodules.validation.commons.DefaultBeanValidator;

import com.google.gson.Gson;

import egovframework.com.cmm.ComDefaultCodeVO;
import egovframework.com.cmm.ComDefaultVO;
import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.annotation.IncludedInfo;
import egovframework.com.cmm.service.EgovCmmUseService;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.menu.program.service.MenuFunctionInfoVO;
import egovframework.com.menu.program.service.MenuInfoVO;
import egovframework.com.menu.program.service.MenuProgramInfoVO;
import egovframework.com.menu.program.service.ProgramService;

/**
 * 메뉴목록 관리및 메뉴생성, 사이트맵 생성을 처리하는 비즈니스 구현 클래스
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
public class ProgramController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProgramController.class);

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

    @Resource(name = "programService")
	private ProgramService programService;

    /**
     * 2020.04.08 프로그램 리스트 관리 트리 추가
     */
    /**
     * 프로그램 리스트에 대한 트리를 조회한다.
     * @param searchVO ComDefaultVO
     * @return 출력페이지정보 "sym/mnu/mpm/EgovProgrmListTreeSelect"
     * @exception Exception
     */
    @IncludedInfo(name="프로그램리스트 관리", order = 1090 ,gid = 60)
    @RequestMapping(value="/menu/program/ProgrmListTreeSelect.do")
    public String selectProgrmTreeList(
    		@ModelAttribute("searchVO") ComDefaultVO searchVO,
    		@RequestParam(value="menuType", required=false) String menuType,
    		ModelMap model)
            throws Exception {
    	// 0. Spring Security 사용자권한 처리
    	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
    	if(!isAuthenticated) {
    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	return "egovframework/com/uat/uia/EgovLoginUsr";
    	}

        return "egovframework/com/menu/program/EgovProgrmListTree";
    }

    /**
     * 2020.04.08 프로그램 리스트 관리 추가
     */
    /**
     * 프로그램 리스트를 조회한다.
     * @param searchVO ComDefaultVO
     * @return 출력페이지정보 "sym/mnu/mpm/EgovMenuList"
     * @exception Exception
     */
    @RequestMapping(value="/menu/program/ProgrmListSelect.do")
    public String selectProgrmList(
    		@ModelAttribute("menuInfoVO") MenuInfoVO menuInfoVO,
    		ModelMap model)
            throws Exception {
    	String resultMsg    = "";
    	// 0. Spring Security 사용자권한 처리
    	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
    	if(!isAuthenticated) {
    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	return "egovframework/com/uat/uia/EgovLoginUsr";
    	}

    	ComDefaultCodeVO vo = new ComDefaultCodeVO();
    	vo.setCodeId("COM104");
    	List<?> codeResult = cmmUseService.selectCmmCodeDetail(vo);
    	model.addAttribute("menuGbList", codeResult);

    	model.addAttribute("menuInfoVO", menuInfoVO);

        return ".adminLayout/프로그램리스트관리/com/menu/program/EgovProgrmList";
    }

    /**
     * 메뉴리스트의 메뉴정보를 등록한다.
     * @param menuManageVO MenuManageVO
     * @return 출력페이지정보 "sym/mnu/mpm/EgovMenuList"
     * @exception Exception
     */
    @RequestMapping(value="/menu/program/MenuInfoInsert.do")
    public String insertProgrmList(
    		@ModelAttribute("menuInfoVO") MenuInfoVO menuInfoVO,
    		BindingResult bindingResult,
    		ModelMap model)
            throws Exception {
    	// 0. Spring Security 사용자권한 처리
    	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
    	if(!isAuthenticated) {
    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	return "egovframework/com/uat/uia/EgovLoginUsr";
    	}

    	programService.insertMenuInfo(menuInfoVO);

      	return "redirect:/menu/program/ProgrmListSelect.do?menuNo=" + menuInfoVO.getMenuNo();
    }

    /**
     * 메뉴정보를 삭제 한다.
     * @param menuInfoVo
     * @return
     * @exception Exception
     */
    @RequestMapping(value="/menu/program/MenuInfoDelete.do", produces="application/text; charset=utf8")
    @ResponseBody
    public String deleteMenuInfo(
    		@ModelAttribute("menuInfoVo") MenuInfoVO menuInfoVO,
    		ModelMap model)
            throws Exception {

    	// 0. Spring Security 사용자권한 처리
    	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
    	if(!isAuthenticated) {
    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	return "egovframework/com/uat/uia/EgovLoginUsr";
    	}
    	programService.deleteMenuInfo(menuInfoVO);

    	//부모 menuNo
    	MenuInfoVO upperMenuNo = new MenuInfoVO();
    	upperMenuNo.setMenuNo(menuInfoVO.getUpperMenuNo());
//    	upperMenuNo.setSysGroupCd(menuInfoVO.getSysGroupCd());;

    	//메뉴 정보 가져오기
    	upperMenuNo = programService.selectMenuInfo(upperMenuNo);

    	// 상위 메뉴가 null인 경우
    	if (Objects.isNull(upperMenuNo)) {
    		upperMenuNo = new MenuInfoVO();
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put ("result", upperMenuNo);
		Gson gson = new Gson();
		return gson.toJson (map);
    }

    /**
     * 메뉴정보 이동 시 정보 및 Sort 변경
     * @param menuInfoVo
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/menu/program/MenuInfoMove.do", produces="application/text; charset=utf8")
	@ResponseBody
	public String moveMenuInfo(
			@ModelAttribute("menuInfoVo") MenuInfoVO menuInfoVO,
			ModelMap model) throws Exception {

		// 미인증 사용자에 대한 보안처리
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated ();
		if (!isAuthenticated) {
			return "redirect:/uat/uia/egovLoginUsr.do";
		}


		for (MenuInfoVO menuInfo : menuInfoVO.getMenuInfoList()) {
			//메뉴정보 수정
			programService.moveMenuInfo(menuInfo);
		}

		//메뉴 정보 가져오기
		menuInfoVO = programService.selectMenuInfo(menuInfoVO);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put ("result", menuInfoVO);
		Gson gson = new Gson();
		return gson.toJson (map);
	}

    /**
     * Tree에서 메뉴 추가 시 등록
     * @param menuInfoVO
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/menu/program/MenuInfoTreeInsert.do", produces="application/text; charset=utf8")
	@ResponseBody
	public String inertTreeMenuInfo(
			@ModelAttribute("menuInfoVo") MenuInfoVO menuInfoVO,
			ModelMap model) throws Exception {

		// 미인증 사용자에 대한 보안처리
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated ();
		if (!isAuthenticated) {
			return "redirect:/uat/uia/egovLoginUsr.do";
		}

		//메뉴 추가 등록
		programService.insertTreeMenuInfo(menuInfoVO);

		//메뉴 정보 가져오기
		menuInfoVO = programService.selectMenuInfo(menuInfoVO);

		Map<String, Object> map = new HashMap<String, Object>();
		//메뉴번호 리턴
		map.put ("result", menuInfoVO);
		Gson gson = new Gson();
		return gson.toJson (map);
	}

    /**
     * Tree에서 메뉴 명 변경 시 수정
     * @param menuInfoVO
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/menu/program/MenuInfoTreeNewName.do", produces="application/text; charset=utf8")
	@ResponseBody
	public String updateNewnNameTreeMenuInfo(
			@ModelAttribute("menuInfoVo") MenuInfoVO menuInfoVO,
			ModelMap model) throws Exception {

		// 미인증 사용자에 대한 보안처리
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated ();
		if (!isAuthenticated) {
			return "redirect:/uat/uia/egovLoginUsr.do";
		}

		//메뉴 추가 등록
		programService.updateNewnNameTreeMenuInfo(menuInfoVO);

		//메뉴 정보 가져오기
		menuInfoVO = programService.selectMenuInfo(menuInfoVO);

		Map<String, Object> map = new HashMap<String, Object>();
		//메뉴번호 리턴
		map.put ("result", menuInfoVO);
		Gson gson = new Gson();
		return gson.toJson (map);
	}

    /**
     *  Tree 노드 클릭 시 정보 조회
     * @param menuInfoVO
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/menu/program/MenuInfoTreeSelect.do", produces="application/text; charset=utf8")
	@ResponseBody
	public String selectTreeMenuInfo(
			@ModelAttribute("menuInfoVo") MenuInfoVO menuInfoVO,
			ModelMap model) throws Exception {
    	/*Enumeration params = request.getParameterNames();
		System.out.println("----------------------------");
		while (params.hasMoreElements()){
		    String name = (String)params.nextElement();
		    System.out.println(name + " : " +request.getParameter(name));
		}
		System.out.println("----------------------------");*/
		// 미인증 사용자에 대한 보안처리
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated ();
		if (!isAuthenticated) {
			return "redirect:/uat/uia/egovLoginUsr.do";
		}

		//메뉴 정보 가져오기
		menuInfoVO = programService.selectMenuInfo(menuInfoVO);

		Map<String, Object> map = new HashMap<String, Object>();
		//메뉴번호 리턴
		map.put ("result", menuInfoVO);
		Gson gson = new Gson();
		return gson.toJson (map);
	}

    /**
     * 메뉴정보를 삭제 한다.
     * @param menuInfoVo
     * @return
     * @exception Exception
     */
    @RequestMapping(value="/menu/program/MenuProgramInfoDelete.do", produces="application/text; charset=utf8")
    @ResponseBody
    public String deleteMenuProgramInfo(
    		@ModelAttribute("menuProgramInfoVO") MenuProgramInfoVO menuProgramInfoVO,
    		ModelMap model)
            throws Exception {

    	// 0. Spring Security 사용자권한 처리
    	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
    	if(!isAuthenticated) {
    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	return "egovframework/com/uat/uia/EgovLoginUsr";
    	}
    	//프로그램 URL 삭제
    	programService.deleteMenuProgramInfo(menuProgramInfoVO);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put ("result", "SUCCESS");
		Gson gson = new Gson();
		return gson.toJson (map);
    }

    /**
     * 게시판을 조회한다.
     * @param searchVO ComDefaultVO
     * @return 출력페이지정보 "/menu/program/ProgramBbsListSearch"
     * @exception Exception
     */
    @RequestMapping(value="/menu/program/ProgramBbsListSearch.do")
    public String selectProgrmBbsListSearch(
    		@ModelAttribute("searchVO") ComDefaultVO searchVO,
    		ModelMap model)
            throws Exception {
        // 0. Spring Security 사용자권한 처리
    	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
    	if(!isAuthenticated) {
    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	return "egovframework/com/uat/uia/EgovLoginUsr";
    	}

    	// 내역 조회
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

        List<?> list_Contents = programService.selectBbsList(searchVO);
        model.addAttribute("list_Contents", list_Contents);

        int totCnt = programService.selectBbsListTotCnt(searchVO);
		paginationInfo.setTotalRecordCount(totCnt);
        model.addAttribute("paginationInfo", paginationInfo);

      	return "egovframework/com/menu/program/EgovProgramBbsListSearch";
    }

    /**
     * 메뉴 기능코드 중복체크한다.
     * @param menuFunctionInfoVO
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/menu/program/selectDuplicateFunctionCode.do", produces="application/json; charset=utf8")
    @ResponseBody
    public String selectDuplicateFunctionCode (
    		@ModelAttribute("menuFunctionInfoVO") MenuFunctionInfoVO menuFunctionInfoVO,
    		ModelMap model)
            throws Exception {

    	// 0. Spring Security 사용자권한 처리
    	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
    	if(!isAuthenticated) {
    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	return "egovframework/com/uat/uia/EgovLoginUsr";
    	}

    	int duplicateCnt = programService.selectDuplicateFunctionCode(menuFunctionInfoVO);

		Gson gson = new Gson();
		return gson.toJson (duplicateCnt);
    }
}