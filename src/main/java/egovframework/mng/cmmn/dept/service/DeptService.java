package egovframework.mng.cmmn.dept.service;

import java.util.List;

import org.egovframe.rte.fdl.cmmn.exception.EgovBizException;
import org.egovframe.rte.fdl.cmmn.exception.FdlException;

/**
 * 부서를 처리하는 비즈니스 클래스
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
public interface DeptService {

	/**
	 * 부서정보 목록을 조회한다.
	 * 
	 * @Author : 김기윤
	 * @Date : 2022. 1. 24.
	 * @Method Name : deptSelectDept
	 * @return : List<DeptVO>
	 */
	public List<DeptVO> selectDeptList() throws EgovBizException;

	/**
	 * 부서정보를 등록한다.
	 * 
	 * @Author : 김기윤
	 * @Date : 2022. 1. 24.
	 * @Method Name : insertDept
	 * @return : DeptVO
	 */
	public DeptVO insertDept(DeptVO deptVO) throws EgovBizException, FdlException;

	/**
	 * 부서정보를 삭제한다.
	 * 
	 * @Author : 김기윤
	 * @Date : 2022. 1. 24.
	 * @Method Name : deleteDept
	 * @return : void
	 */
	public void deleteDept(DeptVO deptVO) throws EgovBizException;

	/**
	 * 부서정보 1건 수정
	 * 
	 * @Author : 김기윤
	 * @Date : 2022. 2. 17.
	 * @Method Name : updateDept
	 * @return : void
	 */
	public void updateDept(DeptVO deptVO) throws EgovBizException;

	/**
	 * 부서정보 트리노드 드래그 수정
	 * 
	 * @Author : 김기윤
	 * @Date : 2022. 2. 17.
	 * @Method Name : updateDeptNode
	 * @return : void
	 */
	public void updateDeptNode(DeptVO deptVO) throws EgovBizException;
}
