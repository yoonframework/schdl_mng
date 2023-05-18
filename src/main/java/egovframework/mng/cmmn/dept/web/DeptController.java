package egovframework.mng.cmmn.dept.web;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.egovframe.rte.fdl.cmmn.exception.EgovBizException;
import org.egovframe.rte.fdl.cmmn.exception.FdlException;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import egovframework.com.cmm.AjaxWrapper;
import egovframework.mng.cmmn.dept.service.DeptService;
import egovframework.mng.cmmn.dept.service.DeptVO;

/**
 * 부서 관련 요청을 비지니스 클래스로 전달하고 처리된 결과를 해당 웹 화면으로 전달하는 Controller를 정의한다.
 * 
 * @since 2022. 1. 24.
 * @author 김기윤
 * 
 *         <PRE>
 * -----------------------
 * 개정이력
 * 2022. 1. 24. 김기윤 : 최초작성
 *         </PRE>
 */
@Controller
@RequestMapping("/api")
public class DeptController {

	/** deptService */
	@Resource(name = "DeptService")
	private DeptService deptService;

	/**
	 * 부서정보 목록을 조회한다.
	 * 
	 * @Author : 김기윤
	 * @Date : 2022. 1. 24.
	 * @Method Name : adminDeptSelectDept
	 * @return : String
	 * @throws EgovBizException
	 */
	@RequestMapping(value = "/admin/dept/selectDeptList.do", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.TEXT_HTML_VALUE })
	public @ResponseBody List<DeptVO> selectDeptList(HttpServletRequest request, @RequestBody DeptVO deptVO)
			throws EgovBizException {

		return deptService.selectDeptList();
	}

	/**
	 * 부서정보를 등록한다.
	 * 
	 * @Author : 김기윤
	 * @Date : 2022. 1. 24.
	 * @Method Name : insertDept
	 * @return : String
	 * @throws EgovBizException
	 */
	@RequestMapping(value = "/admin/dept/insertDept.do", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.TEXT_HTML_VALUE })
	public @ResponseBody DeptVO insertDept(HttpServletRequest request, @RequestBody DeptVO deptVO)
			throws EgovBizException, FdlException {
		return deptService.insertDept(deptVO);
	}

	/**
	 * 부서정보를 삭제한다.
	 * 
	 * @Author : 김기윤
	 * @Date : 2022. 1. 24.
	 * @Method Name : deleteDept
	 * @return : String
	 * @throws EgovBizException
	 */
	@RequestMapping(value = "/admin/dept/deleteDept.do", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.TEXT_HTML_VALUE })
	public @ResponseBody String deleteDept(HttpServletRequest request, @RequestBody DeptVO deptVO)
			throws EgovBizException {
		deptService.deleteDept(deptVO);
		return AjaxWrapper.SUCCESS;
	}

	/**
	 * 부서정보를 수정한다.
	 * 
	 * @Author : 김기윤
	 * @Date : 2022. 2. 16.
	 * @Method Name : updateDept
	 * @return : String
	 */
	@RequestMapping(value = "/admin/dept/updateDept.do", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.TEXT_HTML_VALUE })
	public @ResponseBody String updateDept(HttpServletRequest request, @RequestBody DeptVO deptVO)
			throws EgovBizException {
		deptService.updateDept(deptVO);
		return AjaxWrapper.SUCCESS;
	}

	/**
	 * 부서정보 트리노드 드래그 수정
	 * 
	 * @Author : 김기윤
	 * @Date : 2022. 2. 18.
	 * @Method Name : updateDeptNode
	 * @return : String
	 */
	@RequestMapping(value = "/admin/dept/updateDeptNode.do", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.TEXT_HTML_VALUE })
	public @ResponseBody String updateDeptNode(HttpServletRequest request, @RequestBody DeptVO deptVO)
			throws EgovBizException {
		deptService.updateDeptNode(deptVO);
		return AjaxWrapper.SUCCESS;
	}
}