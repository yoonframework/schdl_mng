package egovframework.mng.cmmn.orgcht.service.impl;

import java.util.List;

import org.egovframe.rte.psl.dataaccess.EgovAbstractMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import egovframework.com.uss.umt.service.MberManageVO;
import egovframework.mng.cmmn.dept.service.DeptVO;

/**
 * 조직도 및 사용자맵 DAO
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
@Repository("OrgchtDAO")
public class OrgchtDAO extends EgovAbstractMapper {

	/**
	 * 부서 목록 조회
	 * 
	 * @Author : 김기윤
	 * @Date : 2022. 2. 21.
	 * @Method Name : selectDeptList
	 * @return : List<DeptVO>
	 */
	public List<DeptVO> selectDeptList() throws DataAccessException {
		return selectList("OrgchtDAO.selectDeptList");
	}

	/**
	 * 부서에 속한 회원 목록 조회
	 * 
	 * @Author : 김기윤
	 * @Date : 2022. 2. 21.
	 * @Method Name : selectDeptMbrList
	 * @return : List<MberManageVO>
	 */
	public List<MberManageVO> selectDeptMbrList(String deptId) throws DataAccessException {
		return selectList("OrgchtDAO.selectDeptMbrList", deptId);
	}

	/**
	 * 전체 회원 목록 조회
	 * 
	 * @Author : 김기윤
	 * @Date : 2022. 2. 21.
	 * @Method Name : selectAllMbrList
	 * @return : List<MberManageVO>
	 */
	public List<MberManageVO> selectAllMbrList(MberManageVO mberManageVO) throws DataAccessException {
		return selectList("OrgchtDAO.selectAllMbrList", mberManageVO);
	}

	/**
	 * 회원 상세 조회
	 * 
	 * @Author : 김기윤
	 * @Date : 2022. 2. 21.
	 * @Method Name : selectMbr
	 * @return : MberManageVO
	 */
	public MberManageVO selectMbr(MberManageVO mberManageVO) throws DataAccessException {
		return (MberManageVO) selectOne("OrgchtDAO.selectMbr", mberManageVO);
	}
}
