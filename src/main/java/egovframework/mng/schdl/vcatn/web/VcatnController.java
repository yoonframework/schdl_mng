package egovframework.mng.schdl.vcatn.web;

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
import egovframework.com.cmm.ComDefaultCodeVO;
import egovframework.com.cmm.service.EgovCmmUseService;
import egovframework.mng.schdl.vcatn.service.VcatnService;
import egovframework.mng.schdl.vcatn.service.VcatnVO;

/**
 * 휴가 Controller
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
public class VcatnController {

	/** 휴가 Service */
	@Resource(name = "VcatnService")
	private VcatnService vcatnService;

	/** cmmUseService */
	@Resource(name = "EgovCmmUseService")
	private EgovCmmUseService cmmUseService;

	/** EgovPropertyService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;

	/**
	 * 휴가 등록 - 코드 정보 조회
	 * 
	 * @Author : 김기윤
	 * @Date : 2022. 2. 24.
	 * @Method Name : selectCodeList
	 * @return : String
	 */
	@RequestMapping(value = "/schdl/vcatn/selectCodeList.do", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.TEXT_HTML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody String selectCodeList()
			throws EgovBizException {

		Map<String, Object> resultMap = new HashMap<>();
		AjaxWrapper aw;

		// 휴가 남은 일수 조회
		String rmndrDayCnt = vcatnService.selectRmndrDayCnt();
		resultMap.put("rmndrDayCnt", rmndrDayCnt);

		// 코드정보
		ComDefaultCodeVO vo = new ComDefaultCodeVO();
		// 휴가 코드
		vo.setCodeId("COM056");
		List<?> vcatnSeList = cmmUseService.selectCmmCodeDetail(vo);
		resultMap.put("vcatnSeList", vcatnSeList);

		aw = new AjaxWrapper(AjaxWrapper.SUCCESS, resultMap);
		return new Gson().toJson(aw);
	}

	/**
	 * 휴가 등록
	 * 
	 * @Author : 김기윤
	 * @Date : 2022. 2. 24.
	 * @Method Name : insertVcatn
	 * @return : String
	 */
	@RequestMapping(value = "/schdl/vcatn/insertVcatn.do", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.TEXT_HTML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody String insertVcatn(@RequestBody VcatnVO vcatnVO)
			throws EgovBizException, FdlException {

		Map<String, Object> resultMap = new HashMap<>();
		AjaxWrapper aw;

		String insertSttus = vcatnService.insertVcatn(vcatnVO);

		aw = new AjaxWrapper(insertSttus, resultMap);
		return new Gson().toJson(aw);
	}

	/**
	 * 휴가 수정
	 * 
	 * @Author : 김기윤
	 * @Date : 2022. 2. 24.
	 * @Method Name : updateVcatn
	 * @return : String
	 */
	@RequestMapping(value = "/schdl/vcatn/updateVcatn.do", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.TEXT_HTML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody String updateVcatn(@RequestBody VcatnVO vcatnVO)
			throws EgovBizException {

		Map<String, Object> resultMap = new HashMap<>();
		AjaxWrapper aw;

		vcatnService.updateVcatn(vcatnVO);

		aw = new AjaxWrapper(AjaxWrapper.SUCCESS, resultMap);
		return new Gson().toJson(aw);
	}

	/**
	 * 휴가 삭제
	 * 
	 * @Author : 김기윤
	 * @Date : 2022. 2. 24.
	 * @Method Name : deleteVcatn
	 * @return : String
	 */
	@RequestMapping(value = "/schdl/vcatn/deleteVcatn.do", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.TEXT_HTML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody String deleteVcatn(@RequestBody VcatnVO vcatnVO)
			throws EgovBizException {

		Map<String, Object> resultMap = new HashMap<>();
		AjaxWrapper aw;

		vcatnService.deleteVcatn(vcatnVO);

		aw = new AjaxWrapper(AjaxWrapper.SUCCESS, resultMap);
		return new Gson().toJson(aw);
	}

	/**
	 * 휴가 목록 조회
	 * 
	 * @Author : 김기윤
	 * @Date : 2022. 2. 25.
	 * @Method Name : selectVcatnList
	 * @return : String
	 */
	@RequestMapping(value = "/schdl/vcatn/selectVcatnList.do", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.TEXT_HTML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody String selectVcatnList(@RequestBody VcatnVO vcatnVO)
			throws EgovBizException {

		Map<String, Object> resultMap = new HashMap<>();
		AjaxWrapper aw;

		/** EgovPropertyService */
		vcatnVO.setPageUnit(propertiesService.getInt("pageUnit"));
		vcatnVO.setPageSize(propertiesService.getInt("pageSize"));

		/** pageing */
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(vcatnVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(vcatnVO.getPageUnit());
		paginationInfo.setPageSize(vcatnVO.getPageSize());

		vcatnVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		vcatnVO.setLastIndex(paginationInfo.getLastRecordIndex());
		vcatnVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

		List<VcatnVO> vcatnList = vcatnService.selectVcatnList(vcatnVO);
		resultMap.put("resultList", vcatnList);

		int totCnt = vcatnService.selectVcatnListTotCnt(vcatnVO);
		paginationInfo.setTotalRecordCount(totCnt);
		resultMap.put("paginationInfo", paginationInfo);

		// 코드정보
		ComDefaultCodeVO vo = new ComDefaultCodeVO();
		// 휴가 코드
		vo.setCodeId("COM056");
		List<?> vcatnSeList = cmmUseService.selectCmmCodeDetail(vo);
		resultMap.put("vcatnSeList", vcatnSeList);

		aw = new AjaxWrapper(AjaxWrapper.SUCCESS, resultMap);
		return new Gson().toJson(aw);
	}
}
