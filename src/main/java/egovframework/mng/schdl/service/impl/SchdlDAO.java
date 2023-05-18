package egovframework.mng.schdl.service.impl;

import java.util.List;

import org.egovframe.rte.psl.dataaccess.EgovAbstractMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import egovframework.mng.cmmn.dept.service.DeptVO;
import egovframework.mng.schdl.service.SchdlVO;

/**
 * 일정관리 DAO
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
@Repository("SchdlDAO")
public class SchdlDAO extends EgovAbstractMapper {

	/**
	 * 일정관리 목록 조회
	 * 
	 * @Author : 김기윤
	 * @Date : 2022. 2. 22.
	 * @Method Name : selectSchdlList
	 * @return : List<SchdlVO>
	 */
	public List<SchdlVO> selectSchdlList(SchdlVO schdlVO) throws DataAccessException {
		return selectList("schdlDAO.selectSchdlList", schdlVO);
	}

	/**
	 * 일정관리 목록 건수 조회
	 * 
	 * @Author : 김기윤
	 * @Date : 2022. 2. 22.
	 * @Method Name : selectSchdlListTotCnt
	 * @return : int
	 */
	public int selectSchdlListTotCnt(SchdlVO schdlVO) throws DataAccessException {
		return (Integer) selectOne("schdlDAO.selectSchdlListTotCnt", schdlVO);
	}

	/**
	 * 부서 조회
	 * 
	 * @Author : 김기윤
	 * @Date : 2022. 2. 22.
	 * @Method Name : selectDeptList
	 * @return : List<DeptVO>
	 */
	public List<DeptVO> selectDeptList() throws DataAccessException {
		return selectList("schdlDAO.selectDeptList");
	}

	/**
	 * 일정(휴가, 출장) 등록 - 관리자 - 직원 검색 - 트리 조회
	 * 
	 * @Author : 김기윤
	 * @Date : 2022. 2. 24.
	 * @Method Name : selectMbrTreeList
	 * @return : List<DeptVO>
	 */
	public List<DeptVO> selectMbrTreeList(SchdlVO schdlVO) throws DataAccessException {
		return selectList("schdlDAO.selectMbrTreeList", schdlVO);
	}
}
