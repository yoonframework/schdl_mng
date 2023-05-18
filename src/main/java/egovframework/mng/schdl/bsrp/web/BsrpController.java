package egovframework.mng.schdl.bsrp.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.egovframe.rte.fdl.cmmn.exception.EgovBizException;
import org.egovframe.rte.fdl.cmmn.exception.FdlException;
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
import egovframework.mng.schdl.bsrp.service.BsrpService;
import egovframework.mng.schdl.bsrp.service.BsrpVO;

/**
 * 출장 Controller
 * 
 * @since 2022. 2. 24.
 * @author 김기윤
 * 
 *         <PRE>
 * -----------------------
 * 개정이력
 * 2022. 2. 24. 김기윤 : 최초작성
 *         </PRE>
 */
@Controller
@RequestMapping("/api")
public class BsrpController {

	/** 출장 Service */
	@Resource(name = "BsrpService")
	private BsrpService bsrpService;

	/** EgovPropertyService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;

	/**
	 * 출장 등록 - 동행자 정보 조회
	 * 
	 * @Author : 김기윤
	 * @Date : 2022. 2. 24.
	 * @Method Name : selectCncdntMbrTreeList
	 * @return : String
	 */
	@RequestMapping(value = "/schdl/bsrp/selectCncdntMbrTreeList.do", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.TEXT_HTML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody String selectCncdntMbrTreeList(@RequestBody BsrpVO bsrpVO)
			throws EgovBizException {

		Map<String, Object> resultMap = new HashMap<>();
		AjaxWrapper aw;

		List<DeptVO> cncdntMbrTreeList = bsrpService.selectCncdntMbrTreeList(bsrpVO);
		resultMap.put("resultList", cncdntMbrTreeList);

		aw = new AjaxWrapper(AjaxWrapper.SUCCESS, resultMap);
		return new Gson().toJson(aw);
	}

	/**
	 * 출장 등록
	 * 
	 * @Author : 김기윤
	 * @Date : 2022. 2. 24.
	 * @Method Name : insertBsrp
	 * @return : String
	 */
	@RequestMapping(value = "/schdl/bsrp/insertBsrp.do", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.TEXT_HTML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody String insertBsrp(@RequestBody BsrpVO bsrpVO)
			throws EgovBizException, FdlException {

		Map<String, Object> resultMap = new HashMap<>();
		AjaxWrapper aw;

		String insertSttus = bsrpService.insertBsrp(bsrpVO);

		aw = new AjaxWrapper(insertSttus, resultMap);
		return new Gson().toJson(aw);
	}

	/**
	 * 출장 수정
	 * 
	 * @Author : 김기윤
	 * @Date : 2022. 2. 24.
	 * @Method Name : updateBsrp
	 * @return : String
	 * @throws FdlException
	 */
	@RequestMapping(value = "/schdl/bsrp/updateBsrp.do", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.TEXT_HTML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody String updateBsrp(@RequestBody BsrpVO bsrpVO)
			throws EgovBizException, FdlException {

		Map<String, Object> resultMap = new HashMap<>();
		AjaxWrapper aw;

		String updateSttus = bsrpService.updateBsrp(bsrpVO);

		aw = new AjaxWrapper(updateSttus, resultMap);
		return new Gson().toJson(aw);
	}

	/**
	 * 출장 삭제
	 * 
	 * @Author : 김기윤
	 * @Date : 2022. 2. 24.
	 * @Method Name : deleteBsrp
	 * @return : String
	 */
	@RequestMapping(value = "/schdl/bsrp/deleteBsrp.do", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.TEXT_HTML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody String deleteBsrp(@RequestBody BsrpVO bsrpVO)
			throws EgovBizException {

		Map<String, Object> resultMap = new HashMap<>();
		AjaxWrapper aw;

		bsrpService.deleteBsrp(bsrpVO);

		aw = new AjaxWrapper(AjaxWrapper.SUCCESS, resultMap);
		return new Gson().toJson(aw);
	}

	/**
	 * 출장 목록 조회
	 * 
	 * @Author : 김기윤
	 * @Date : 2022. 2. 25.
	 * @Method Name : selectBsrpList
	 * @return : String
	 */
	@RequestMapping(value = "/schdl/bsrp/selectBsrpList.do", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.TEXT_HTML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody String selectBsrpList(@RequestBody BsrpVO bsrpVO)
			throws EgovBizException {

		Map<String, Object> resultMap = new HashMap<>();
		AjaxWrapper aw;

		/** EgovPropertyService */
		bsrpVO.setPageUnit(propertiesService.getInt("pageUnit"));
		bsrpVO.setPageSize(propertiesService.getInt("pageSize"));

		/** pageing */
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(bsrpVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(bsrpVO.getPageUnit());
		paginationInfo.setPageSize(bsrpVO.getPageSize());

		bsrpVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		bsrpVO.setLastIndex(paginationInfo.getLastRecordIndex());
		bsrpVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

		List<BsrpVO> bsrpList = bsrpService.selectBsrpList(bsrpVO);
		resultMap.put("resultList", bsrpList);

		int totCnt = bsrpService.selectBsrpListTotCnt(bsrpVO);
		paginationInfo.setTotalRecordCount(totCnt);
		resultMap.put("paginationInfo", paginationInfo);

		aw = new AjaxWrapper(AjaxWrapper.SUCCESS, resultMap);
		return new Gson().toJson(aw);
	}
}
