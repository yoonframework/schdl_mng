package egovframework.mng.cmmn.dept.service.impl;

import java.util.List;

import org.egovframe.rte.psl.dataaccess.EgovAbstractMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import egovframework.mng.cmmn.dept.service.DeptVO;

/**
 * 부서를 처리하는 DAO 클래스
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
@Repository("DeptDAO")
public class DeptDAO extends EgovAbstractMapper {

	/**
	 * 부서정보 목록을 조회한다.
	 * 
	 * @Author : 김기윤
	 * @Date : 2022. 1. 24.
	 * @Method Name : deptSelectDept
	 * @return : List<DeptVO>
	 */
	public List<DeptVO> selectDeptList() throws DataAccessException {
		return selectList("deptDAO.selectDeptList");
	}

	/**
	 * 부서정보를 등록한다.
	 * 
	 * @Author : 김기윤
	 * @Date : 2022. 1. 24.
	 * @Method Name : insertDept
	 * @return : void
	 */
	public void insertDept(DeptVO deptVO) throws DataAccessException {
		insert("deptDAO.insertDept", deptVO);
	}

	/**
	 * 부서정보를 삭제한다.
	 * 
	 * @Author : 김기윤
	 * @Date : 2022. 1. 24.
	 * @Method Name : deleteDept
	 * @return : void
	 */
	public void deleteDept(DeptVO deptVO) throws DataAccessException {
		delete("deptDAO.deleteDept", deptVO);
	}

	/**
	 * 부서정보를 수정한다.
	 * 
	 * @Author : 김기윤
	 * @Date : 2022. 2. 17.
	 * @Method Name : updateDept
	 * @return : void
	 */
	public void updateDept(DeptVO deptVO) throws DataAccessException {
		update("deptDAO.updateDept", deptVO);
	}
}
