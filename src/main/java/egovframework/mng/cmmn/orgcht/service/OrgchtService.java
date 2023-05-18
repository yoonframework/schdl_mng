package egovframework.mng.cmmn.orgcht.service;

import java.util.List;

import org.egovframe.rte.fdl.cmmn.exception.EgovBizException;

import egovframework.com.uss.umt.service.MberManageVO;
import egovframework.mng.cmmn.dept.service.DeptVO;

/**
 * 조직도 및 사용자맵 Service
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
public interface OrgchtService {

	/**
	 * 부서 목록 조회
	 * 
	 * @Author : 김기윤
	 * @Date : 2022. 2. 21.
	 * @Method Name : selectDeptList
	 * @return : List<DeptVO>
	 */
	public List<DeptVO> selectDeptList() throws EgovBizException;

	/**
	 * 부서에 속한 회원 목록 조회
	 * 
	 * @Author : 김기윤
	 * @Date : 2022. 2. 21.
	 * @Method Name : selectDeptMbrList
	 * @return : List<MberManageVO>
	 */
	public List<MberManageVO> selectDeptMbrList(String deptId) throws EgovBizException;

	/**
	 * 전체 회원 목록 조회
	 * 
	 * @Author : 김기윤
	 * @Date : 2022. 2. 21.
	 * @Method Name : selectAllMbrList
	 * @return : List<MberManageVO>
	 */
	public List<MberManageVO> selectAllMbrList(MberManageVO mberManageVO) throws EgovBizException;

	/**
	 * 회원 상세 조회
	 * 
	 * @Author : 김기윤
	 * @Date : 2022. 2. 21.
	 * @Method Name : selectMbr
	 * @return : MberManageVO
	 */
	public MberManageVO selectMbr(MberManageVO mberManageVO) throws EgovBizException;
}
