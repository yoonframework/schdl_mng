package egovframework.mng.schdl.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.egovframe.rte.fdl.cmmn.exception.EgovBizException;
import org.egovframe.rte.fdl.property.EgovPropertyService;
import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import egovframework.com.cmm.AjaxWrapper;
import egovframework.mng.cmmn.dept.service.DeptVO;
import egovframework.mng.schdl.service.SchdlService;
import egovframework.mng.schdl.service.SchdlVO;

/**
 * 일정관리 Controller
 * 
 * @since 2022. 2. 22.
 * @author 김기윤
 * 
 *         <PRE>
 * -----------------------
 * 개정이력
 * 2022. 2. 22. 김기윤 : 최초작성
 *         </PRE>
 */
@Controller
@RequestMapping("/api")
public class SchdlController {

	/** 일정관리 Service */
	@Resource(name = "SchdlService")
	private SchdlService schdlService;

	/** EgovPropertyService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;

	/**
	 * 일정관리 목록 조회
	 * 
	 * @Author : 김기윤
	 * @Date : 2022. 2. 22.
	 * @Method Name : selectSchdlList
	 * @return : String
	 */
	@RequestMapping(value = "/user/schdl/selectSchdlList.do", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.TEXT_HTML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody String selectSchdlList(@RequestBody SchdlVO schdlVO)
			throws EgovBizException {

		Map<String, Object> resultMap = new HashMap<>();
		AjaxWrapper aw;

		/** EgovPropertyService */
		schdlVO.setPageUnit(propertiesService.getInt("pageUnit"));
		schdlVO.setPageSize(propertiesService.getInt("pageSize"));

		/** pageing */
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(schdlVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(schdlVO.getPageUnit());
		paginationInfo.setPageSize(schdlVO.getPageSize());

		schdlVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		schdlVO.setLastIndex(paginationInfo.getLastRecordIndex());
		schdlVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

		List<SchdlVO> schdlList = schdlService.selectSchdlList(schdlVO);
		resultMap.put("resultList", schdlList);

		/** 부서 조회 */
		List<DeptVO> deptList = schdlService.selectDeptList();
		resultMap.put("deptList", deptList);

		int totCnt = schdlService.selectSchdlListTotCnt(schdlVO);
		paginationInfo.setTotalRecordCount(totCnt);
		resultMap.put("paginationInfo", paginationInfo);

		aw = new AjaxWrapper(AjaxWrapper.SUCCESS, resultMap);
		return new Gson().toJson(aw);
	}

	/**
	 * 일정(휴가, 출장) 등록 - 관리자 - 직원 검색 - 트리 조회
	 * 
	 * @Author : 김기윤
	 * @Date : 2022. 2. 24.
	 * @Method Name : selectMbrTreeList
	 * @return : String
	 */
	@RequestMapping(value = "/user/schdl/selectMbrTreeList.do", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.TEXT_HTML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody String selectMbrTreeList(@RequestBody SchdlVO schdlVO)
			throws EgovBizException {

		Map<String, Object> resultMap = new HashMap<>();
		AjaxWrapper aw;

		List<DeptVO> mbrTreeList = schdlService.selectMbrTreeList(schdlVO);
		resultMap.put("resultList", mbrTreeList);

		aw = new AjaxWrapper(AjaxWrapper.SUCCESS, resultMap);
		return new Gson().toJson(aw);
	}
}
