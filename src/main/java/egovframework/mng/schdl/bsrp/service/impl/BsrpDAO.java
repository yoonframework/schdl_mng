package egovframework.mng.schdl.bsrp.service.impl;

import java.util.List;

import org.egovframe.rte.psl.dataaccess.EgovAbstractMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import egovframework.mng.cmmn.dept.service.DeptVO;
import egovframework.mng.schdl.bsrp.service.BsrpCncdntVO;
import egovframework.mng.schdl.bsrp.service.BsrpVO;

/**
 * 출장 DAO
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
@Repository("BsrpDAO")
public class BsrpDAO extends EgovAbstractMapper {

	/**
	 * 출장 등록 - 동행자 정보 조회
	 * 
	 * @Author : 김기윤
	 * @Date : 2022. 2. 24.
	 * @Method Name : selectCncdntMbrTreeList
	 * @return : List<DeptVO>
	 */
	public List<DeptVO> selectCncdntMbrTreeList(BsrpVO bsrpVO) throws DataAccessException {
		return selectList("bsrpDAO.selectCncdntMbrTreeList", bsrpVO);
	}

	/**
	 * 출장 등록 - 사용자 지정 일자에 출장 등록 여부 확인
	 * 
	 * @Author : 김기윤
	 * @Date : 2022. 2. 25.
	 * @Method Name : selectBsrpRegYn
	 * @return : int
	 */
	public int selectBsrpRegYn(BsrpVO bsrpVO) throws DataAccessException {
		return (Integer) selectOne("bsrpDAO.selectBsrpRegYn", bsrpVO);
	}

	/**
	 * 출장 등록
	 * 
	 * @Author : 김기윤
	 * @Date : 2022. 2. 24.
	 * @Method Name : insertBsrp
	 * @return : void
	 */
	public void insertBsrp(BsrpVO bsrpVO) throws DataAccessException {
		insert("bsrpDAO.insertBsrp", bsrpVO);
	}

	/**
	 * 출장 동행자 삭제
	 * 
	 * @Author : 김기윤
	 * @Date : 2022. 2. 24.
	 * @Method Name : deleteBsrpCncdnt
	 * @return : void
	 */
	public void deleteBsrpCncdnt(BsrpVO bsrpVO) throws DataAccessException {
		delete("bsrpDAO.deleteBsrpCncdnt", bsrpVO);
	}

	/**
	 * 출장 동행자 등록
	 * 
	 * @Author : 김기윤
	 * @Date : 2022. 2. 24.
	 * @Method Name : insertBsrpCncdnt
	 * @return : void
	 */
	public void insertBsrpCncdnt(BsrpCncdntVO bsrpCncdntVO) throws DataAccessException {
		insert("bsrpDAO.insertBsrpCncdnt", bsrpCncdntVO);
	}

	/**
	 * 출장 수정
	 * 
	 * @Author : 김기윤
	 * @Date : 2022. 2. 24.
	 * @Method Name : updateBsrp
	 * @return : void
	 */
	public void updateBsrp(BsrpVO bsrpVO) throws DataAccessException {
		update("bsrpDAO.updateBsrp", bsrpVO);
	}

	/**
	 * 출장 삭제
	 * 
	 * @Author : 김기윤
	 * @Date : 2022. 2. 24.
	 * @Method Name : deleteBsrp
	 * @return : void
	 */
	public void deleteBsrp(BsrpVO bsrpVO) throws DataAccessException {
		update("bsrpDAO.deleteBsrp", bsrpVO);
	}

	/**
	 * 출장 목록 조회
	 * 
	 * @Author : 김기윤
	 * @Date : 2022. 2. 25.
	 * @Method Name : selectBsrpList
	 * @return : List<BsrpVO>
	 */
	public List<BsrpVO> selectBsrpList(BsrpVO bsrpVO) throws DataAccessException {
		return selectList("bsrpDAO.selectBsrpList", bsrpVO);
	}

	/**
	 * 출장 목록 건수 조회
	 * 
	 * @Author : 김기윤
	 * @Date : 2022. 2. 25.
	 * @Method Name : selectBsrpListTotCnt
	 * @return : int
	 */
	public int selectBsrpListTotCnt(BsrpVO bsrpVO) throws DataAccessException {
		return (Integer) selectOne("bsrpDAO.selectBsrpListTotCnt", bsrpVO);
	}
}
