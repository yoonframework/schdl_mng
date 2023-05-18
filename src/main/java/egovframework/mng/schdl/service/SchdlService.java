package egovframework.mng.schdl.service;

import java.util.List;

import org.egovframe.rte.fdl.cmmn.exception.EgovBizException;

import egovframework.mng.cmmn.dept.service.DeptVO;

/**
 * 일정관리 Service
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
public interface SchdlService {

	/**
	 * 일정관리 목록 조회
	 * 
	 * @Author : 김기윤
	 * @Date : 2022. 2. 22.
	 * @Method Name : selectSchdlList
	 * @return : List<SchdlVO>
	 */
	public List<SchdlVO> selectSchdlList(SchdlVO schdlVO) throws EgovBizException;

	/**
	 * 일정관리 목록 건수 조회
	 * 
	 * @Author : 김기윤
	 * @Date : 2022. 2. 22.
	 * @Method Name : selectSchdlListTotCnt
	 * @return : int
	 */
	public int selectSchdlListTotCnt(SchdlVO schdlVO) throws EgovBizException;

	/**
	 * 부서 조회
	 * 
	 * @Author : 김기윤
	 * @Date : 2022. 2. 22.
	 * @Method Name : selectDeptList
	 * @return : List<DeptVO>
	 */
	public List<DeptVO> selectDeptList() throws EgovBizException;

	/**
	 * 일정(휴가, 출장) 등록 - 관리자 - 직원 검색 - 트리 조회
	 * 
	 * @Author : 김기윤
	 * @Date : 2022. 2. 24.
	 * @Method Name : selectMbrTreeList
	 * @return : List<DeptVO>
	 */
	public List<DeptVO> selectMbrTreeList(SchdlVO schdlVO) throws EgovBizException;
}
