package egovframework.com.uss.umt.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.egovframe.rte.fdl.cmmn.exception.EgovBizException;
import org.egovframe.rte.fdl.cmmn.exception.FdlException;
import org.egovframe.rte.fdl.property.EgovPropertyService;
import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springmodules.validation.commons.DefaultBeanValidator;

import com.google.gson.Gson;

import egovframework.com.cmm.AjaxWrapper;
import egovframework.com.cmm.ComDefaultCodeVO;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.annotation.IncludedInfo;
import egovframework.com.cmm.service.EgovCmmUseService;
import egovframework.com.cmm.service.EgovFileMngService;
import egovframework.com.cmm.service.EgovFileMngUtil;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.menu.program.service.MenuFunctionInfoVO;
import egovframework.com.sec.ram.service.AuthorManageVO;
import egovframework.com.sec.ram.service.EgovAuthorManageService;
import egovframework.com.sec.rgm.service.AuthorGroupVO;
import egovframework.com.sec.rgm.service.EgovAuthorGroupService;
import egovframework.com.uat.uia.service.EgovLoginService;
import egovframework.com.uss.umt.service.EgovMberManageService;
import egovframework.com.uss.umt.service.EgovUserManageService;
import egovframework.com.uss.umt.service.MberManageVO;
import egovframework.com.uss.umt.service.UserDefaultVO;
import egovframework.com.utl.sim.service.EgovFileScrty;
import egovframework.mng.cmmn.dept.service.DeptVO;
import egovframework.mng.schdl.service.SchdlService;

/**
 * 일반회원관련 요청을 비지니스 클래스로 전달하고 처리된결과를 해당 웹 화면으로 전달하는 Controller를 정의한다
 * 
 * @author 공통서비스 개발팀 조재영
 * @since 2009.04.10
 * @version 1.0
 * @see
 *
 *      <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2009.04.10  조재영          최초 생성
 *   2011.08.26	 정진오			IncludedInfo annotation 추가
 *   2014.12.08	 이기하			암호화방식 변경(EgovFileScrty.encryptPassword)
 *   2015.06.16	 조정국			수정시 유효성체크 후 에러발생 시 목록으로 이동하여 에러메시지 표시
 *   2015.06.19	 조정국			미인증 사용자에 대한 보안처리 기준 수정 (!isAuthenticated)
 *
 *      </pre>
 */

@Controller
@RequestMapping("/api")
public class EgovMberManageController {

	@Resource(name = "loginService")
	private EgovLoginService loginService;

	/** mberManageService */
	@Resource(name = "mberManageService")
	private EgovMberManageService mberManageService;

	/** cmmUseService */
	@Resource(name = "EgovCmmUseService")
	private EgovCmmUseService cmmUseService;

	/** EgovPropertyService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;

	@Resource(name = "EgovFileMngService")
	private EgovFileMngService fileMngService;

	@Resource(name = "EgovFileMngUtil")
	private EgovFileMngUtil fileUtil;

	@Resource(name = "egovAuthorManageService")
	private EgovAuthorManageService egovAuthorManageService;

	@Resource(name = "egovAuthorGroupService")
	private EgovAuthorGroupService egovAuthorGroupService;

	/** userManageService */
	@Resource(name = "userManageService")
	private EgovUserManageService userManageService;

	/** 일정관리 Service */
	@Resource(name = "SchdlService")
	private SchdlService schdlService;

	/** DefaultBeanValidator beanValidator */
	@Autowired
	private DefaultBeanValidator beanValidator;

	/**
	 * 일반회원목록을 조회한다. (pageing)
	 * 
	 * @param userSearchVO 검색조건정보
	 * @param model        화면모델
	 * @return uss/umt/EgovMberManage
	 * @throws Exception
	 */
	@IncludedInfo(name = "일반회원관리", order = 470, gid = 50)
	@RequestMapping(value = "/uss/umt/EgovMberManage.do")
	public String selectMberList(@ModelAttribute("userSearchVO") UserDefaultVO userSearchVO, ModelMap model)
			throws Exception {

		// 미인증 사용자에 대한 보안처리
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		if (!isAuthenticated) {
			// return "redirect:/egovDevIndex.jsp";
			// return "/EgovContent.do";
			return "index";
		}

		/** EgovPropertyService */
		userSearchVO.setPageUnit(propertiesService.getInt("pageUnit"));
		userSearchVO.setPageSize(propertiesService.getInt("pageSize"));

		/** pageing */
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(userSearchVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(userSearchVO.getPageUnit());
		paginationInfo.setPageSize(userSearchVO.getPageSize());

		userSearchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		userSearchVO.setLastIndex(paginationInfo.getLastRecordIndex());
		userSearchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

		List<?> mberList = mberManageService.selectMberList(userSearchVO);
		model.addAttribute("resultList", mberList);

		int totCnt = mberManageService.selectMberListTotCnt(userSearchVO);
		paginationInfo.setTotalRecordCount(totCnt);
		model.addAttribute("paginationInfo", paginationInfo);

		// 일반회원 상태코드를 코드정보로부터 조회
		ComDefaultCodeVO vo = new ComDefaultCodeVO();
		vo.setCodeId("COM013");
		List<?> mberSttus_result = cmmUseService.selectCmmCodeDetail(vo);
		model.addAttribute("entrprsMberSttus_result", mberSttus_result);// 기업회원상태코드목록

		// 사용자 등록 권한을 위한 권한 리스트를 가지고 온다
		AuthorManageVO authorManageVO = new AuthorManageVO();
		authorManageVO.setPagingAt(false);
		authorManageVO.setSearchCondition("999");
		authorManageVO.setAuthorManageList(egovAuthorManageService.selectAuthorList(authorManageVO));
		model.addAttribute("authorList", authorManageVO.getAuthorManageList());

		return ".adminLayout/일반회원관리/com/uss/umt/EgovMberManage";
	}

	/**
	 * 일반회원등록화면으로 이동한다.
	 * 
	 * @param userSearchVO 검색조건정보
	 * @param mberManageVO 일반회원초기화정보
	 * @param model        화면모델
	 * @return uss/umt/EgovMberInsert
	 * @throws Exception
	 */
	@RequestMapping("/uss/umt/EgovMberInsertView.do")
	public String insertMberView(@ModelAttribute("userSearchVO") UserDefaultVO userSearchVO,
			@ModelAttribute("mberManageVO") MberManageVO mberManageVO, Model model)
			throws Exception {

		// 미인증 사용자에 대한 보안처리
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		if (!isAuthenticated) {
			return "index";
		}

		ComDefaultCodeVO vo = new ComDefaultCodeVO();

		// 패스워드힌트목록을 코드정보로부터 조회
		vo.setCodeId("COM022");
		List<?> passwordHint_result = cmmUseService.selectCmmCodeDetail(vo);
		// 성별구분코드를 코드정보로부터 조회
		vo.setCodeId("COM014");
		List<?> sexdstnCode_result = cmmUseService.selectCmmCodeDetail(vo);
		// 사용자상태코드를 코드정보로부터 조회
		vo.setCodeId("COM013");
		List<?> mberSttus_result = cmmUseService.selectCmmCodeDetail(vo);
		// 그룹정보를 조회 - GROUP_ID정보
		vo.setTableNm("COMTNORGNZTINFO");
		List<?> groupId_result = cmmUseService.selectGroupIdDetail(vo);

		// 직위구분코드를 코드정보로부터 조회
		vo.setCodeId("COM105");
		List<?> ofcpsList = cmmUseService.selectCmmCodeDetail(vo);

		model.addAttribute("passwordHint_result", passwordHint_result); // 패스워트힌트목록
		model.addAttribute("sexdstnCode_result", sexdstnCode_result); // 성별구분코드목록
		model.addAttribute("mberSttus_result", mberSttus_result); // 사용자상태코드목록
		model.addAttribute("groupId_result", groupId_result); // 그룹정보 목록
		model.addAttribute("ofcpsList", ofcpsList); // 직위정보 목록

		// 사용자 등록 권한을 위한 권한 리스트를 가지고 온다
		AuthorManageVO authorManageVO = new AuthorManageVO();
		authorManageVO.setPagingAt(false);
		authorManageVO.setSearchCondition("999");
		authorManageVO.setAuthorManageList(egovAuthorManageService.selectAuthorList(authorManageVO));
		model.addAttribute("authorList", authorManageVO.getAuthorManageList());

		// 사용자 메뉴에 대한 기능 정보 조회
		List<MenuFunctionInfoVO> menuFunctionList = mberManageService.selectMenuFunctionInfo();
		model.addAttribute("menuFunctionList", menuFunctionList);

		return ".adminLayout/일반회원관리/com/uss/umt/EgovMberInsert";
	}

	/**
	 * 일반회원등록처리후 목록화면으로 이동한다.
	 * 
	 * @param mberManageVO  일반회원등록정보
	 * @param bindingResult 입력값검증용 bindingResult
	 * @param model         화면모델
	 * @return redirect:/uss/umt/EgovMberManage.do
	 * @throws Exception
	 */
	@RequestMapping("/uss/umt/EgovMberInsert.do")
	public String insertMber(@ModelAttribute("mberManageVO") MberManageVO mberManageVO, BindingResult bindingResult,
			Model model, final MultipartHttpServletRequest multiRequest) throws Exception {

		// 미인증 사용자에 대한 보안처리
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		if (!isAuthenticated) {
			return "index";
		}

		beanValidator.validate(mberManageVO, bindingResult);
		if (bindingResult.hasErrors()) {

			ComDefaultCodeVO vo = new ComDefaultCodeVO();

			// 패스워드힌트목록을 코드정보로부터 조회
			vo.setCodeId("COM022");
			List<?> passwordHint_result = cmmUseService.selectCmmCodeDetail(vo);
			// 성별구분코드를 코드정보로부터 조회
			vo.setCodeId("COM014");
			List<?> sexdstnCode_result = cmmUseService.selectCmmCodeDetail(vo);
			// 사용자상태코드를 코드정보로부터 조회
			vo.setCodeId("COM013");
			List<?> mberSttus_result = cmmUseService.selectCmmCodeDetail(vo);
			// 그룹정보를 조회 - GROUP_ID정보
			vo.setTableNm("COMTNORGNZTINFO");
			List<?> groupId_result = cmmUseService.selectGroupIdDetail(vo);

			model.addAttribute("passwordHint_result", passwordHint_result); // 패스워트힌트목록
			model.addAttribute("sexdstnCode_result", sexdstnCode_result); // 성별구분코드목록
			model.addAttribute("mberSttus_result", mberSttus_result); // 사용자상태코드목록
			model.addAttribute("groupId_result", groupId_result); // 그룹정보 목록

			return "egovframework/com/uss/umt/EgovMberInsert";
		} else {
			// if (mberManageVO.getGroupId().equals("")) {
			// mberManageVO.setGroupId(null);
			// }
			mberManageService.insertMber(mberManageVO);

			// 등록 된 사용자 권한 등록
			AuthorGroupVO authorGroup = new AuthorGroupVO();
			authorGroup.setUniqId(mberManageVO.getUniqId());
			authorGroup.setMberTyCode("USR01");
			authorGroup.setAuthorCode(mberManageVO.getAuthorCode());
			egovAuthorGroupService.insertAuthorGroup(authorGroup);
			// Exception 없이 진행시 등록 성공메시지
			// model.addAttribute("resultMsg", "success.common.insert");
		}
		return "redirect:/uss/umt/EgovMberManage.do";
	}

	/**
	 * 일반회원정보 수정을 위해 일반회원정보를 상세조회한다.
	 * 
	 * @param mberId       상세조회대상 일반회원아이디
	 * @param userSearchVO 검색조건
	 * @param model        화면모델
	 * @return uss/umt/EgovMberSelectUpdt
	 * @throws Exception
	 */
	@RequestMapping("/uss/umt/EgovMberSelectUpdtView.do")
	public String updateMberView(@RequestParam("selectedId") String mberId,
			@ModelAttribute("searchVO") UserDefaultVO userSearchVO, Model model) throws Exception {
		// 미인증 사용자에 대한 보안처리
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		if (!isAuthenticated) {
			return "index";
		}

		// 관리자 또는 마이페이지의 회원 정보 수정 URL 구분
		String url = "";

		if (mberId.equals("mypage")) {
			LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
			mberId = user.getUniqId();
			url = ".mypageLayout/나의정보/com/uss/umt/UserMberSelectUpdt";
		} else {
			model.addAttribute("userSearchVO", userSearchVO);
			url = ".adminLayout/일반회원관리/com/uss/umt/EgovMberSelectUpdt";
		}

		ComDefaultCodeVO vo = new ComDefaultCodeVO();

		// 패스워드힌트목록을 코드정보로부터 조회
		vo.setCodeId("COM022");
		List<?> passwordHint_result = cmmUseService.selectCmmCodeDetail(vo);

		// 성별구분코드를 코드정보로부터 조회
		vo.setCodeId("COM014");
		List<?> sexdstnCode_result = cmmUseService.selectCmmCodeDetail(vo);

		// 사용자상태코드를 코드정보로부터 조회
		vo.setCodeId("COM013");
		List<?> mberSttus_result = cmmUseService.selectCmmCodeDetail(vo);

		// 그룹정보를 조회 - GROUP_ID정보
		vo.setTableNm("COMTNORGNZTINFO");
		List<?> groupId_result = cmmUseService.selectGroupIdDetail(vo);

		model.addAttribute("passwordHint_result", passwordHint_result); // 패스워트힌트목록
		model.addAttribute("sexdstnCode_result", sexdstnCode_result); // 성별구분코드목록
		model.addAttribute("mberSttus_result", mberSttus_result); // 사용자상태코드목록
		model.addAttribute("groupId_result", groupId_result); // 그룹정보 목록

		MberManageVO mberManageVO = mberManageService.selectMber(mberId);
		model.addAttribute("mberManageVO", mberManageVO);

		// 등록 된 사용자의 권한 조회
		AuthorGroupVO userAutho = new AuthorGroupVO();
		userAutho = egovAuthorGroupService.selectMemberAuthor(mberManageVO);

		if (userAutho != null) {
			model.addAttribute("userAutho", userAutho.getAuthorCode());
		}

		// 사용자 메뉴에 대한 기능 정보 조회
		List<MenuFunctionInfoVO> menuFunctionList = mberManageService.selectMenuFunctionInfo();
		model.addAttribute("menuFunctionList", menuFunctionList);

		// 사용자 등록 권한을 위한 권한 리스트를 가지고 온다
		AuthorManageVO authorManageVO = new AuthorManageVO();
		authorManageVO.setPagingAt(false);
		authorManageVO.setSearchCondition("999");
		authorManageVO.setAuthorManageList(egovAuthorManageService.selectAuthorList(authorManageVO));
		model.addAttribute("authorList", authorManageVO.getAuthorManageList());

		// 직위구분코드를 코드정보로부터 조회
		vo.setCodeId("COM105");
		List<?> ofcpsList = cmmUseService.selectCmmCodeDetail(vo);
		model.addAttribute("ofcpsList", ofcpsList); // 직위정보 목록

		return url;
		// 원본 소스
		// model.addAttribute("userSearchVO", userSearchVO);
		// return ".adminLayout/일반회원관리/com/uss/umt/EgovMberSelectUpdt";
	}

	/**
	 * 일반회원정보 수정후 목록조회 화면으로 이동한다.
	 * 
	 * @param mberManageVO  일반회원수정정보
	 * @param bindingResult 입력값검증용 bindingResult
	 * @param model         화면모델
	 * @return redirect:/uss/umt/EgovMberManage.do
	 * @throws Exception
	 */
	@RequestMapping("/uss/umt/EgovMberSelectUpdt.do")
	public String updateMber(@ModelAttribute("mberManageVO") MberManageVO mberManageVO, BindingResult bindingResult,
			Model model, final MultipartHttpServletRequest multiRequest, HttpSession session) throws Exception {

		// 미인증 사용자에 대한 보안처리
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		if (!isAuthenticated) {
			return "index";
		}

		// 관리자 또는 mypage 리턴 url 셋팅
		String url = "";

		beanValidator.validate(mberManageVO, bindingResult);
		if (bindingResult.hasErrors()) {
			model.addAttribute("resultMsg", bindingResult.getAllErrors().get(0).getDefaultMessage());

			ComDefaultCodeVO vo = new ComDefaultCodeVO();

			// 패스워드힌트목록을 코드정보로부터 조회
			vo.setCodeId("COM022");
			List<?> passwordHint_result = cmmUseService.selectCmmCodeDetail(vo);

			// 성별구분코드를 코드정보로부터 조회
			vo.setCodeId("COM014");
			List<?> sexdstnCode_result = cmmUseService.selectCmmCodeDetail(vo);

			// 사용자상태코드를 코드정보로부터 조회
			vo.setCodeId("COM013");
			List<?> mberSttus_result = cmmUseService.selectCmmCodeDetail(vo);

			// 그룹정보를 조회 - GROUP_ID정보
			vo.setTableNm("COMTNORGNZTINFO");
			List<?> groupId_result = cmmUseService.selectGroupIdDetail(vo);

			model.addAttribute("passwordHint_result", passwordHint_result); // 패스워트힌트목록
			model.addAttribute("sexdstnCode_result", sexdstnCode_result); // 성별구분코드목록
			model.addAttribute("mberSttus_result", mberSttus_result); // 사용자상태코드목록
			model.addAttribute("groupId_result", groupId_result); // 그룹정보 목록

			// =====================================
			// 수영 수정 시작
			// =====================================
			if (mberManageVO.getMyPage().equals("myPage")) {
				return ".mypageLayout/나의정보/com/uss/umt/UserMberSelectUpdt";
			} else {
				return "redirect:/uss/umt/EgovMberManage.do";
			}
			// =====================================
			// 수영 수정 종료
			// =====================================
		} else {
			// =====================================
			// 수영 주석
			// =====================================

			// if (mberManageVO.getGroupId().equals("")) {
			// mberManageVO.setGroupId(null);
			// }

			mberManageService.updateMber(mberManageVO);

			LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
			if (user.getUniqId().equals(mberManageVO.getUniqId())) {
				LoginVO vo = new LoginVO();
				vo.setUniqId(mberManageVO.getUniqId());
				vo.setUserSe("RE");
				LoginVO loginVO = loginService.actionSessionReload(vo);
				session.setAttribute("LoginVO", loginVO);
			}

			// Exception 없이 진행시 수정성공메시지
			// model.addAttribute("resultMsg", "success.common.update");

			// =====================================
			// 수영 수정 시작
			// =====================================
			if (mberManageVO.getMyPage().equals("myPage")) {
				// url = ".mypageLayout/나의정보/com/uss/umt/UserMberSelectUpdt";
				url = "redirect:/uss/umt/EgovMberSelectUpdtView.do?selectedId=mypage";
			} else {
				// 등록 된 사용자의 권한 조회 및 등록
				AuthorGroupVO userAutho = new AuthorGroupVO();
				userAutho = egovAuthorGroupService.selectMemberAuthor(mberManageVO);

				AuthorGroupVO authorGroup = new AuthorGroupVO();
				authorGroup.setUniqId(mberManageVO.getUniqId());
				authorGroup.setMberTyCode("USR01");
				authorGroup.setAuthorCode(mberManageVO.getAuthorCode());

				if (userAutho != null && userAutho.getAuthorCode() != null && !(userAutho.getAuthorCode().equals(""))) {
					egovAuthorGroupService.updateAuthorGroup(authorGroup);
				} else {
					egovAuthorGroupService.insertAuthorGroup(authorGroup);
				}
				url = "redirect:/uss/umt/EgovMberManage.do";
			}
			return url;
			// =====================================
			// 수영 수정 종료
			// =====================================

			// 원본 소스
			// return "forward:/uss/umt/EgovMberManage.do";
		}
	}

	/**
	 * 일반회원정보삭제후 목록조회 화면으로 이동한다.
	 * 
	 * @param checkedIdForDel 삭제대상 아이디 정보
	 * @param userSearchVO    검색조건정보
	 * @param model           화면모델
	 * @return redirect:/uss/umt/EgovMberManage.do
	 * @throws Exception
	 */
	@RequestMapping("/uss/umt/EgovMberDelete.do")
	public String deleteMber(@RequestParam("checkedIdForDel") String checkedIdForDel,
			@ModelAttribute("searchVO") UserDefaultVO userSearchVO, Model model) throws Exception {

		// 미인증 사용자에 대한 보안처리
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		if (!isAuthenticated) {
			return "index";
		}

		mberManageService.deleteMber(checkedIdForDel);
		// Exception 없이 진행시 삭제성공메시지
		model.addAttribute("resultMsg", "success.common.delete");
		return "redirect:/uss/umt/EgovMberManage.do";
	}

	// 탈퇴 처리 기능에 대한 예시
	@RequestMapping("/uss/umt/EgovMberWithdraw.do")
	public String withdrawMber(Model model) throws Exception {
		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

		String returnPage = "/"; // 탈퇴 처리 후 화면 지정

		if (!isAuthenticated) {
			model.addAttribute("resultMsg", "fail.common.delete");

			return "redirect:" + returnPage;
		}

		mberManageService.deleteMber(user.getUniqId());
		// Exception 없이 진행시 삭제성공메시지
		model.addAttribute("resultMsg", "success.common.delete");

		return "redirect:" + returnPage;
	}

	/**
	 * 일반회원가입신청 등록화면으로 이동한다.
	 * 
	 * @param userSearchVO 검색조건
	 * @param mberManageVO 일반회원가입신청정보
	 * @param commandMap   파라메터전달용 commandMap
	 * @param model        화면모델
	 * @return uss/umt/EgovMberSbscrb
	 * @throws Exception
	 */
	@RequestMapping("/uss/umt/EgovMberSbscrbView.do")
	public String sbscrbMberView(@ModelAttribute("userSearchVO") UserDefaultVO userSearchVO,
			@ModelAttribute("mberManageVO") MberManageVO mberManageVO,
			@RequestParam Map<String, Object> commandMap, Model model) throws Exception {

		ComDefaultCodeVO vo = new ComDefaultCodeVO();

		// 패스워드힌트목록을 코드정보로부터 조회
		vo.setCodeId("COM022");
		List<?> passwordHint_result = cmmUseService.selectCmmCodeDetail(vo);
		// 성별구분코드를 코드정보로부터 조회
		vo.setCodeId("COM014");
		List<?> sexdstnCode_result = cmmUseService.selectCmmCodeDetail(vo);

		model.addAttribute("passwordHint_result", passwordHint_result); // 패스워트힌트목록
		model.addAttribute("sexdstnCode_result", sexdstnCode_result); // 성별구분코드목록
		if (!"".equals(commandMap.get("realname"))) {
			model.addAttribute("mberNm", commandMap.get("realname")); // 실명인증된 이름 - 주민번호 인증
			model.addAttribute("ihidnum", commandMap.get("ihidnum")); // 실명인증된 주민등록번호 - 주민번호 인증
		}
		if (!"".equals(commandMap.get("realName"))) {
			model.addAttribute("mberNm", commandMap.get("realName")); // 실명인증된 이름 - ipin인증
		}

		mberManageVO.setMberSttus("DEFAULT");

		// ==========================================
		// 회원 공통코드 조회 시작 - 수영추가
		// ==========================================
		// 회원가입 소분류코드(RSCHER : 연구자, PANEL : 패널, RATER : 평가자)
		vo.setCodeId("COM708");
		List<?> smallDivisionCodeList = cmmUseService.selectCmmCodeDetail(vo);
		model.addAttribute("smallDivisionCodeList", smallDivisionCodeList);

		// 연구자 직위 코드
		vo.setCodeId("COM702");
		List<?> researchCodeList = cmmUseService.selectCmmCodeDetail(vo);
		model.addAttribute("researchCodeList", researchCodeList);

		// 일반세부과제명 코드
		vo.setCodeId("COM703");
		List<?> detailCodeList = cmmUseService.selectCmmCodeDetail(vo);
		model.addAttribute("detailCodeList", detailCodeList);

		// 패널 코드
		vo.setCodeId("COM704");
		List<?> panelCodeList = cmmUseService.selectCmmCodeDetail(vo);
		model.addAttribute("panelCodeList", panelCodeList);

		// 연구자 구분 코드
		vo.setCodeId("COM705");
		List<?> rschDivisionCodeList = cmmUseService.selectCmmCodeDetail(vo);
		model.addAttribute("rschDivisionCodeList", rschDivisionCodeList);

		// 평가가능분야(대분류) 코드
		vo.setCodeId("COM706");
		List<?> frstRschCodeList = cmmUseService.selectCmmCodeDetail(vo);
		model.addAttribute("frstRschCodeList", frstRschCodeList);

		// 평가가능분야(소분류) 코드
		vo.setCodeId("COM707");
		List<?> scndRschCodeList = cmmUseService.selectCmmCodeDetail(vo);
		model.addAttribute("scndRschCodeList", scndRschCodeList);

		// ==========================================
		// 회원 공통코드 조회 종료
		// ==========================================
		return ".defaultLayout/회원가입/com/uss/umt/EgovMberSbscrb";
		// return "egovframework/com/uss/umt/EgovMberSbscrb";
	}

	/**
	 * 일반회원가입신청등록처리후로그인화면으로 이동한다.
	 * 
	 * @param mberManageVO 일반회원가입신청정보
	 * @return redirect:/uat/uia/egovLoginUsr.do
	 * @throws Exception
	 */
	@RequestMapping("/uss/umt/EgovMberSbscrb.do")
	public String sbscrbMber(@ModelAttribute("mberManageVO") MberManageVO mberManageVO,
			final MultipartHttpServletRequest multiRequest) throws Exception {

		// 가입상태 초기화
		mberManageVO.setMberSttus("A");
		// 그룹정보 초기화
		// mberManageVO.setGroupId("1");
		// 일반회원가입신청 등록시 일반회원등록기능을 사용하여 등록한다.
		mberManageService.insertMber(mberManageVO);
		return "redirect:/uat/uia/egovLoginUsr.do";
	}

	/**
	 * 일반회원 약관확인
	 * 
	 * @param model 화면모델
	 * @return uss/umt/EgovStplatCnfirm
	 * @throws Exception
	 */
	@RequestMapping("/uss/umt/EgovStplatCnfirmMber.do")
	public String sbscrbEntrprsMber(Model model) throws Exception {
		// 회원 약관 설정
		String stplatId = "STPLAT_0000000000001";
		String sbscrbTy = "USR01";

		// 약관정보 조회
		List<?> stplatList = mberManageService.selectStplat(stplatId);
		model.addAttribute("stplatList", stplatList); // 약관정보 포함
		model.addAttribute("sbscrbTy", sbscrbTy); // 회원가입유형 포함

		/*
		 * 기존 회원가입 소스(최상위 주석만 풀면 됨)
		 * // 미인증 사용자에 대한 보안처리
		 * //Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		 * //if (!isAuthenticated) {
		 * // return "index";
		 * //}
		 * 
		 * //일반회원용 약관 아이디 설정
		 * String stplatId = "STPLAT_0000000000001";
		 * //회원가입유형 설정-일반회원
		 * String sbscrbTy = "USR01";
		 * //약관정보 조회
		 * List<?> stplatList = mberManageService.selectStplat(stplatId);
		 * model.addAttribute("stplatList", stplatList); //약관정보 포함
		 * model.addAttribute("sbscrbTy", sbscrbTy); //회원가입유형 포함
		 */
		return ".defaultLayout/약관확인/com/uss/umt/EgovStplatCnfirm";
		// return "egovframework/com/uss/umt/EgovStplatCnfirm";
	}

	/**
	 * @param model        화면모델
	 * @param commandMap   파라메터전달용 commandMap
	 * @param userSearchVO 검색조건
	 * @param mberManageVO 일반회원수정정보(비밀번호)
	 * @return uss/umt/EgovMberPasswordUpdt
	 * @throws Exception
	 */
	@RequestMapping(value = "/uss/umt/EgovMberPasswordUpdt.do")
	public String updatePassword(ModelMap model, @RequestParam Map<String, Object> commandMap,
			@ModelAttribute("searchVO") UserDefaultVO userSearchVO,
			@ModelAttribute("mberManageVO") MberManageVO mberManageVO, RedirectAttributes redirectAttr)
			throws Exception {

		// 미인증 사용자에 대한 보안처리
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		if (!isAuthenticated) {
			return "index";
		}

		String oldPassword = (String) commandMap.get("oldPassword");
		String newPassword = (String) commandMap.get("newPassword");
		String newPassword2 = (String) commandMap.get("newPassword2");
		String uniqId = (String) commandMap.get("uniqId");

		boolean isCorrectPassword = false;
		MberManageVO resultVO = new MberManageVO();
		mberManageVO.setPassword(newPassword);
		mberManageVO.setOldPassword(oldPassword);
		mberManageVO.setUniqId(uniqId);

		// 관리자는 기존비밀번호 체크 안하도록 수정(임종호)
		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		String resultMsg = "";
		resultVO = mberManageService.selectPassword(mberManageVO);
		// 패스워드 암호화
		String encryptPass = EgovFileScrty.encryptPassword(oldPassword, mberManageVO.getMberId());
		if (user.getAuthorCode().contains("ROLE_ADMIN")) {
			isCorrectPassword = true;
		}
		if (user.getAuthorCode().contains("ROLE_ADMIN") || encryptPass.equals(resultVO.getPassword())) {
			if (newPassword.equals(newPassword2)) {
				isCorrectPassword = true;
			} else {
				isCorrectPassword = false;
				resultMsg = "fail.user.passwordUpdate2";
			}
		} else {
			isCorrectPassword = false;
			resultMsg = "fail.user.passwordUpdate1";
		}

		/*
		 * ==========================================
		 * 수영 수정
		 * ==========================================
		 */
		String url = "";

		if (isCorrectPassword) {
			mberManageVO.setPassword(EgovFileScrty.encryptPassword(newPassword, mberManageVO.getMberId()));
			mberManageService.updatePassword(mberManageVO);
			resultMsg = "success.common.update";
			redirectAttr.addFlashAttribute("mberManageVO", mberManageVO);

			// 마이페이지에서 넘어온건지 아닌지 비교해서 값 넘겨줌
			if (userSearchVO.getMyPage().equals("myPage")) {
				url = "redirect:/uss/umt/EgovMberSelectUpdtView.do?selectedId=mypage";
			} else {
				url = "redirect:/uss/umt/EgovMberSelectUpdtView.do?selectedId=" + uniqId;
			}
		} else {
			redirectAttr.addFlashAttribute("mberManageVO", mberManageVO);
			// 마이페이지에서 넘어온건지 아닌지 비교해서 값 넘겨줌
			if (userSearchVO.getMyPage().equals("myPage")) {
				redirectAttr.addFlashAttribute("searchVO", userSearchVO);
			}
			url = "redirect:/uss/umt/EgovMberPasswordUpdtView.do";
		}

		redirectAttr.addFlashAttribute("resultMsg", resultMsg);

		return url;
		/*
		 * 원본소스
		 * if (isCorrectPassword) {
		 * mberManageVO.setPassword(EgovFileScrty.encryptPassword(newPassword,
		 * mberManageVO.getMberId()));
		 * mberManageService.updatePassword(mberManageVO);
		 * model.addAttribute("mberManageVO", mberManageVO);
		 * resultMsg = "success.common.update";
		 * } else {
		 * model.addAttribute("mberManageVO", mberManageVO);
		 * }
		 * model.addAttribute("userSearchVO", userSearchVO);
		 * model.addAttribute("resultMsg", resultMsg);
		 * 
		 * return "egovframework/com/uss/umt/EgovMberPasswordUpdt";
		 */
	}

	/**
	 * 일반회원 암호 수정 화면 이동
	 * 
	 * @param model        화면모델
	 * @param commandMap   파라메터전달용 commandMap
	 * @param userSearchVO 검색조건
	 * @param mberManageVO 일반회원수정정보(비밀번호)
	 * @return uss/umt/EgovMberPasswordUpdt
	 * @throws Exception
	 */
	@RequestMapping(value = "/uss/umt/EgovMberPasswordUpdtView.do")
	public String updatePasswordView(ModelMap model, @RequestParam Map<String, Object> commandMap,
			@ModelAttribute("searchVO") UserDefaultVO userSearchVO,
			@ModelAttribute("mberManageVO") MberManageVO mberManageVO) throws Exception {

		// 미인증 사용자에 대한 보안처리
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		if (!isAuthenticated) {
			return "index";
		}

		// mypage 또는 관리자에서 들어올 경우 return URL 셋팅
		String url = "";

		if (userSearchVO.getMyPage().equals("myPage")) {
			url = ".mypageLayout/비밀번호 변경/com/uss/umt/UserMberPasswordUpdt";
		} else {
			url = ".adminLayout/일반회원관리 비밀번호 변경/com/uss/umt/EgovMberPasswordUpdt";
		}

		String userTyForPassword = (String) commandMap.get("userTyForPassword");
		mberManageVO.setUserTy(userTyForPassword);

		model.addAttribute("userSearchVO", userSearchVO);
		model.addAttribute("mberManageVO", mberManageVO);

		return url;
		// 원본 소스
		// return ".adminLayout/일반회원관리 비밀번호 변경/com/uss/umt/EgovMberPasswordUpdt";
	}

	/**
	 * 마이페이지 - 회원정보 수정에서 회원 탈퇴 처리
	 * 
	 * @param model
	 * @param commandMap
	 * @param userSearchVO
	 * @param mberManageVO
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/uss/umt/EgovMberQuit.do")
	public String updateMberSttus(ModelMap model, @RequestParam Map<String, Object> commandMap,
			@ModelAttribute("mberManageVO") MberManageVO mberManageVO) throws Exception {

		// 미인증 사용자에 대한 보안처리
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		if (!isAuthenticated) {
			return "index";
		}

		mberManageService.mberSttusUpdate(mberManageVO);

		return "redirect:/uat/uia/actionLogout.do";
		// 원본 소스
		// return ".adminLayout/일반회원관리 비밀번호 변경/com/uss/umt/EgovMberPasswordUpdt";
	}

	/**
	 * 수영 추가
	 * 회원가입 - 회원 구분 선택(일반, 한의사, 한의대생)
	 * 
	 * @param model
	 * @param commandMap
	 * @param userSearchVO
	 * @param mberManageVO
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/uss/umt/EgovMberDivisionView.do")
	public String mberDivisionView(ModelMap model, @ModelAttribute("mberManageVO") MberManageVO mberManageVO)
			throws Exception {

		return ".defaultLayout/회원가입/com/uss/umt/EgovMberDivisionView";
		// 원본 소스
		// return "egovframework/com/uss/umt/EgovMberPasswordUpdt";
	}

	/**
	 * 회원 목록 조회
	 * 
	 * @Author : 김기윤
	 * @Date : 2022. 2. 16.
	 * @Method Name : selectMbrList
	 * @return : String
	 */
	@RequestMapping(value = "/uss/umt/selectMbrList.do", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.TEXT_HTML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody String selectMbrList(@RequestBody UserDefaultVO userSearchVO)
			throws EgovBizException {

		Map<String, Object> resultMap = new HashMap<>();
		AjaxWrapper aw;

		/** EgovPropertyService */
		userSearchVO.setPageUnit(propertiesService.getInt("pageUnit"));
		userSearchVO.setPageSize(propertiesService.getInt("pageSize"));

		/** pageing */
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(userSearchVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(userSearchVO.getPageUnit());
		paginationInfo.setPageSize(userSearchVO.getPageSize());

		userSearchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		userSearchVO.setLastIndex(paginationInfo.getLastRecordIndex());
		userSearchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

		List<?> mberList = mberManageService.selectMberList(userSearchVO);
		resultMap.put("resultList", mberList);

		int totCnt = mberManageService.selectMberListTotCnt(userSearchVO);
		paginationInfo.setTotalRecordCount(totCnt);
		resultMap.put("paginationInfo", paginationInfo);

		// 코드정보
		ComDefaultCodeVO vo = new ComDefaultCodeVO();
		// 일반회원 상태코드
		vo.setCodeId("COM013");
		List<?> mberSttusResult = cmmUseService.selectCmmCodeDetail(vo);
		resultMap.put("entrprsMberSttusResult", mberSttusResult);
		// 직위구분코드
		vo.setCodeId("COM105");
		List<?> ofcpsList = cmmUseService.selectCmmCodeDetail(vo);
		resultMap.put("ofcpsList", ofcpsList);
		// 직책구분코드
		vo.setCodeId("schdl_mng001");
		List<?> jbttlList = cmmUseService.selectCmmCodeDetail(vo);
		resultMap.put("jbttlList", jbttlList);
		// 근무형태코드
		vo.setCodeId("schdl_mng002");
		List<?> workStleList = cmmUseService.selectCmmCodeDetail(vo);
		resultMap.put("workStleList", workStleList);

		/** 부서 조회 */
		List<DeptVO> deptList = schdlService.selectDeptList();
		resultMap.put("deptList", deptList);

		// 사용자 등록 권한을 위한 권한 리스트를 가지고 온다
		AuthorManageVO authorManageVO = new AuthorManageVO();
		authorManageVO.setPagingAt(false);
		authorManageVO.setSearchCondition("999");
		authorManageVO.setAuthorManageList(egovAuthorManageService.selectAuthorList(authorManageVO));
		resultMap.put("authorList", authorManageVO.getAuthorManageList());

		aw = new AjaxWrapper(AjaxWrapper.SUCCESS, resultMap);
		return new Gson().toJson(aw);
	}

	/**
	 * 회원 등록
	 * 
	 * @Author : 김기윤
	 * @Date : 2022. 2. 18.
	 * @Method Name : insertMbr
	 * @return : String
	 */
	@RequestMapping(value = "/uss/umt/insertMbr.do", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.TEXT_HTML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody String insertMbr(@RequestBody MberManageVO mberManageVO)
			throws EgovBizException, FdlException {

		Map<String, Object> resultMap = new HashMap<>();
		AjaxWrapper aw;

		mberManageService.insertMber(mberManageVO);

		// 등록 된 사용자 권한 등록
		AuthorGroupVO authorGroup = new AuthorGroupVO();
		authorGroup.setUniqId(mberManageVO.getUniqId());
		authorGroup.setMberTyCode("USR01");
		authorGroup.setAuthorCode(mberManageVO.getAuthorCode());
		egovAuthorGroupService.insertAuthorGroup(authorGroup);

		aw = new AjaxWrapper(AjaxWrapper.SUCCESS, resultMap);
		return new Gson().toJson(aw);
	}

	/**
	 * 회원 수정
	 * 
	 * @Author : 김기윤
	 * @Date : 2022. 2. 18.
	 * @Method Name : insertMbr
	 * @return : String
	 */
	@RequestMapping(value = "/uss/umt/updateMbr.do", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.TEXT_HTML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody String updateMbr(@RequestBody MberManageVO mberManageVO, HttpSession session)
			throws EgovBizException, FdlException {

		Map<String, Object> resultMap = new HashMap<>();
		AjaxWrapper aw;

		mberManageService.updateMber(mberManageVO);

		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		if (user.getUniqId().equals(mberManageVO.getUniqId())) {
			LoginVO vo = new LoginVO();
			vo.setUniqId(mberManageVO.getUniqId());
			vo.setUserSe("RE");
			LoginVO loginVO = loginService.actionSessionReload(vo);
			session.setAttribute("LoginVO", loginVO);
		}

		// 관리자만
		if (user.getAuthorCode().contains("ROLE_ADMIN")) {
			// 등록 된 사용자의 권한 조회 및 등록
			AuthorGroupVO userAutho = new AuthorGroupVO();
			userAutho = egovAuthorGroupService.selectMemberAuthor(mberManageVO);

			AuthorGroupVO authorGroup = new AuthorGroupVO();
			authorGroup.setUniqId(mberManageVO.getUniqId());
			authorGroup.setMberTyCode(mberManageVO.getUserTy());
			authorGroup.setAuthorCode(mberManageVO.getAuthorCode());

			if (userAutho != null && userAutho.getAuthorCode() != null && !(userAutho.getAuthorCode().equals(""))) {
				egovAuthorGroupService.updateAuthorGroup(authorGroup);
			} else {
				egovAuthorGroupService.insertAuthorGroup(authorGroup);
			}
		}

		aw = new AjaxWrapper(AjaxWrapper.SUCCESS, resultMap);
		return new Gson().toJson(aw);
	}

	/**
	 * 회원 삭제
	 * 
	 * @Author : 김기윤
	 * @Date : 2022. 2. 18.
	 * @Method Name : selectMbrList
	 * @return : String
	 */
	@RequestMapping(value = "/uss/umt/deleteMbr.do", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.TEXT_HTML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody String selectMbrList(@RequestBody String checkedIdForDel) throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		AjaxWrapper aw;

		mberManageService.deleteMber(checkedIdForDel);

		aw = new AjaxWrapper(AjaxWrapper.SUCCESS, resultMap);
		return new Gson().toJson(aw);
	}

	/**
	 * 아이디 중복 확인
	 * 
	 * @Author : 김기윤
	 * @Date : 2022. 2. 17.
	 * @Method Name : selectMbrIdCnt
	 * @return : String
	 */
	@RequestMapping(value = "/uss/umt/selectMbrIdCnt.do", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.TEXT_HTML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody String selectMbrIdCnt(@RequestBody String checkId) throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		AjaxWrapper aw;

		int usedCnt = userManageService.checkIdDplct(checkId);
		resultMap.put("usedCnt", Integer.toString(usedCnt));

		aw = new AjaxWrapper(AjaxWrapper.SUCCESS, resultMap);
		return new Gson().toJson(aw);
	}

	/**
	 * 회원 정보 조회
	 * 
	 * @Author : 김기윤
	 * @Date : 2022. 3. 31.
	 * @Method Name : selectMbr
	 * @return : String
	 */
	@RequestMapping(value = "/uss/umt/selectMbr.do", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.TEXT_HTML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody String selectMbr(@RequestBody String uniqId) throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		AjaxWrapper aw;

		MberManageVO mberManageVO = mberManageService.selectMber(uniqId);
		resultMap.put("mberManageVO", mberManageVO);

		// 코드정보
		ComDefaultCodeVO vo = new ComDefaultCodeVO();
		// 근무형태코드
		vo.setCodeId("schdl_mng002");
		List<?> workStleList = cmmUseService.selectCmmCodeDetail(vo);
		resultMap.put("workStleList", workStleList);

		aw = new AjaxWrapper(AjaxWrapper.SUCCESS, resultMap);
		return new Gson().toJson(aw);
	}
}