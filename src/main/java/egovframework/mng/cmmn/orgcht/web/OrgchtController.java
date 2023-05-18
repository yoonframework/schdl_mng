package egovframework.mng.cmmn.orgcht.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.egovframe.rte.fdl.cmmn.exception.EgovBizException;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import egovframework.com.cmm.AjaxWrapper;
import egovframework.com.uss.umt.service.MberManageVO;
import egovframework.mng.cmmn.dept.service.DeptVO;
import egovframework.mng.cmmn.orgcht.service.OrgchtService;

/**
 * 조직도 및 사용자맵 Controller
 * 
 * @since 2022. 2. 21.
 * @author 김기윤
 * 
 *         <PRE>
 * -----------------------
 * 개정이력
 *  2022. 2. 21. 김기윤 : 최초작성
 *         </PRE>
 */
@Controller
@RequestMapping("/api")
public class OrgchtController {

	/** 조직도 및 사용자맵 Service */
	@Resource(name = "OrgchtService")
	private OrgchtService orgchtService;

	/**
	 * 부서 목록 조회
	 * 
	 * @Author : 김기윤
	 * @Date : 2022. 2. 21.
	 * @Method Name : selectDeptList
	 * @return : String
	 */
	@RequestMapping(value = "/cmmn/orgcht/selectDeptList.do", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.TEXT_HTML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody String selectDeptList()
			throws EgovBizException {

		Map<String, Object> resultMap = new HashMap<>();
		AjaxWrapper aw;

		List<DeptVO> deptList = orgchtService.selectDeptList();
		resultMap.put("deptList", deptList);

		aw = new AjaxWrapper(AjaxWrapper.SUCCESS, resultMap);
		return new Gson().toJson(aw);
	}

	/**
	 * 부서에 속한 회원 목록 조회
	 * 
	 * @Author : 김기윤
	 * @Date : 2022. 2. 21.
	 * @Method Name : selectDeptMbrList
	 * @return : String
	 */
	@RequestMapping(value = "/cmmn/orgcht/selectDeptMbrList.do", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.TEXT_HTML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody String selectDeptMbrList(@RequestBody String deptId)
			throws EgovBizException {

		Map<String, Object> resultMap = new HashMap<>();
		AjaxWrapper aw;

		List<MberManageVO> mberList = orgchtService.selectDeptMbrList(deptId);
		resultMap.put("mberList", mberList);

		aw = new AjaxWrapper(AjaxWrapper.SUCCESS, resultMap);
		return new Gson().toJson(aw);
	}

	/**
	 * 전체 회원 목록 조회
	 * 
	 * @Author : 김기윤
	 * @Date : 2022. 2. 21.
	 * @Method Name : selectAllMbrList
	 * @return : String
	 */
	@RequestMapping(value = "/cmmn/orgcht/selectAllMbrList.do", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.TEXT_HTML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody String selectAllMbrList(@RequestBody MberManageVO mberManageVO) throws EgovBizException {

		Map<String, Object> resultMap = new HashMap<>();
		AjaxWrapper aw;

		List<MberManageVO> mberList = orgchtService.selectAllMbrList(mberManageVO);
		resultMap.put("mberList", mberList);

		aw = new AjaxWrapper(AjaxWrapper.SUCCESS, resultMap);
		return new Gson().toJson(aw);
	}

	/**
	 * 회원 상세 조회
	 * 
	 * @Author : 김기윤
	 * @Date : 2022. 2. 21.
	 * @Method Name : selectMbr
	 * @return : String
	 */
	@RequestMapping(value = "/cmmn/orgcht/selectMbr.do", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.TEXT_HTML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody String selectMbr(@RequestBody MberManageVO mberManageVO) throws EgovBizException {

		Map<String, Object> resultMap = new HashMap<>();
		AjaxWrapper aw;

		mberManageVO = orgchtService.selectMbr(mberManageVO);
		resultMap.put("mberManageVO", mberManageVO);

		aw = new AjaxWrapper(AjaxWrapper.SUCCESS, resultMap);
		return new Gson().toJson(aw);
	}
}