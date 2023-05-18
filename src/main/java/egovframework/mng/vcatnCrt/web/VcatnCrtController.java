package egovframework.mng.vcatnCrt.web;

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
import egovframework.com.uss.umt.service.MberManageVO;
import egovframework.mng.cmmn.dept.service.DeptVO;
import egovframework.mng.schdl.service.SchdlService;
import egovframework.mng.vcatnCrt.service.VcatnCrtService;
import egovframework.mng.vcatnCrt.service.VcatnCrtVO;

/**
 * 휴가생성관리 관령 요청을 비지니스 클래스로 전달하고 처리된 결과를 해당 웹 화면으로 전달하는 Controller를 정의한다.
 * 
 * @since 2022. 2. 21.
 * @author 김기윤
 * 
 *         <PRE>
 * -----------------------
 * 개정이력
 * 2022. 2. 21. 김기윤 : 최초작성
 *         </PRE>
 */
@Controller
@RequestMapping("/api")
public class VcatnCrtController {

	/**
	 * 휴가생성관리 Service
	 */
	@Resource(name = "VcatnCrtService")
	private VcatnCrtService vcatnCrtService;

	/** 일정관리 Service */
	@Resource(name = "SchdlService")
	private SchdlService schdlService;

	/**
	 * EgovPropertyService
	 */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;

	/**
	 * 휴가생성관리 목록 조회
	 * 
	 * @Author : 김기윤
	 * @Date : 2022. 2. 22.
	 * @Method Name : selectVcatnCrtList
	 * @return : String
	 */
	@RequestMapping(value = "/admin/vcatnCrt/selectVcatnCrtList.do", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.TEXT_HTML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody String selectVcatnCrtList(@RequestBody VcatnCrtVO vcatnCrtVO)
			throws EgovBizException {

		Map<String, Object> resultMap = new HashMap<>();
		AjaxWrapper aw;

		/** EgovPropertyService */
		vcatnCrtVO.setPageUnit(propertiesService.getInt("pageUnit"));
		vcatnCrtVO.setPageSize(propertiesService.getInt("pageSize"));

		/** pageing */
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(vcatnCrtVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(vcatnCrtVO.getPageUnit());
		paginationInfo.setPageSize(vcatnCrtVO.getPageSize());

		vcatnCrtVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		vcatnCrtVO.setLastIndex(paginationInfo.getLastRecordIndex());
		vcatnCrtVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

		/** 부서 조회 */
		List<DeptVO> deptList = schdlService.selectDeptList();
		resultMap.put("deptList", deptList);

		List<VcatnCrtVO> vcatnCrtList = vcatnCrtService.selectVcatnCrtList(vcatnCrtVO);
		resultMap.put("resultList", vcatnCrtList);

		int totCnt = vcatnCrtService.selectVcatnCrtListTotCnt(vcatnCrtVO);
		paginationInfo.setTotalRecordCount(totCnt);
		resultMap.put("paginationInfo", paginationInfo);

		aw = new AjaxWrapper(AjaxWrapper.SUCCESS, resultMap);
		return new Gson().toJson(aw);
	}

	/**
	 * 휴가생성관리 - 개별등록 - 트리조회
	 * 
	 * @Author : 김기윤
	 * @Date : 2022. 2. 22.
	 * @Method Name : selectVcatnCrtTreeList
	 * @return : List<DeptVO>
	 */
	@RequestMapping(value = "/admin/vcatnCrt/selectVcatnCrtTreeList.do", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.TEXT_HTML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody String selectVcatnCrtTreeList(@RequestBody VcatnCrtVO vcatnCrtVO)
			throws EgovBizException {
		Map<String, Object> resultMap = new HashMap<>();
		AjaxWrapper aw;

		List<DeptVO> vcatnCrtTreeList = vcatnCrtService.selectVcatnCrtTreeList(vcatnCrtVO);
		resultMap.put("resultList", vcatnCrtTreeList);

		aw = new AjaxWrapper(AjaxWrapper.SUCCESS, resultMap);
		return new Gson().toJson(aw);
	}

	/**
	 * 휴가생성관리 - 개별등록
	 * 
	 * @Author : 김기윤
	 * @Date : 2022. 2. 22.
	 * @Method Name : insertVcatnCrt
	 * @return : String
	 */
	@RequestMapping(value = "/admin/vcatnCrt/insertVcatnCrt.do", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.TEXT_HTML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody String insertVcatnCrt(@RequestBody VcatnCrtVO vcatnCrtVO)
			throws EgovBizException, FdlException {
		Map<String, Object> resultMap = new HashMap<>();
		AjaxWrapper aw;
		vcatnCrtService.insertVcatnCrt(vcatnCrtVO);
		aw = new AjaxWrapper(AjaxWrapper.SUCCESS, resultMap);
		return new Gson().toJson(aw);
	}

	/**
	 * 휴가생성관리 - 일괄등록 - 회원 목록 조회
	 * 
	 * @Author : 김기윤
	 * @Date : 2022. 2. 22.
	 * @Method Name : selectMbrList
	 * @return : String
	 */
	@RequestMapping(value = "/admin/vcatnCrt/selectMbrList.do", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.TEXT_HTML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody String selectMbrList(@RequestBody VcatnCrtVO vcatnCrtVO)
			throws EgovBizException {

		Map<String, Object> resultMap = new HashMap<>();
		AjaxWrapper aw;

		List<MberManageVO> mberList = vcatnCrtService.selectMberList(vcatnCrtVO);
		resultMap.put("resultList", mberList);

		aw = new AjaxWrapper(AjaxWrapper.SUCCESS, resultMap);
		return new Gson().toJson(aw);
	}

	/**
	 * 휴가생성관리 - 일괄등록
	 * 
	 * @Author : 김기윤
	 * @Date : 2022. 2. 22.
	 * @Method Name : insertBndeVcatnCrt
	 * @return : String
	 */
	@RequestMapping(value = "/admin/vcatnCrt/insertBndeVcatnCrt.do", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.TEXT_HTML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody String insertBndeVcatnCrt(@RequestBody VcatnCrtVO vcatnCrtVO)
			throws EgovBizException, FdlException {
		Map<String, Object> resultMap = new HashMap<>();
		AjaxWrapper aw;
		vcatnCrtService.insertBndeVcatnCrt(vcatnCrtVO);
		aw = new AjaxWrapper(AjaxWrapper.SUCCESS, resultMap);
		return new Gson().toJson(aw);
	}

	/**
	 * 휴가생성관리 - 삭제
	 * 
	 * @Author : 김기윤
	 * @Date : 2022. 2. 22.
	 * @Method Name : deleteVcatnCrt
	 * @return : String
	 */
	@RequestMapping(value = "/admin/vcatnCrt/deleteVcatnCrt.do", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.TEXT_HTML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody String deleteVcatnCrt(@RequestBody VcatnCrtVO vcatnCrtVO)
			throws EgovBizException {
		Map<String, Object> resultMap = new HashMap<>();
		AjaxWrapper aw;
		vcatnCrtService.deleteVcatnCrt(vcatnCrtVO);
		aw = new AjaxWrapper(AjaxWrapper.SUCCESS, resultMap);
		return new Gson().toJson(aw);
	}
}
