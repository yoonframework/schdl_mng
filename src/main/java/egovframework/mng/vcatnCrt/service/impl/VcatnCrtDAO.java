package egovframework.mng.vcatnCrt.service.impl;

import java.util.List;

import org.egovframe.rte.psl.dataaccess.EgovAbstractMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import egovframework.com.uss.umt.service.MberManageVO;
import egovframework.mng.cmmn.dept.service.DeptVO;
import egovframework.mng.vcatnCrt.service.VcatnCrtVO;

/**
 * 휴가생성관리 DAO
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
@Repository("VcatnCrtDAO")
public class VcatnCrtDAO extends EgovAbstractMapper {

	/**
	 * 휴가생성관리 목록 조회
	 * 
	 * @Author : 김기윤
	 * @Date : 2022. 2. 22.
	 * @Method Name : selectVcatnCrtList
	 * @return : List<VcatnCrtVO>
	 */
	public List<VcatnCrtVO> selectVcatnCrtList(VcatnCrtVO vcatnCrtVO) throws DataAccessException {
		return selectList("vcatnCrtDAO.selectVcatnCrtList", vcatnCrtVO);
	}

	/**
	 * 휴가생성관리 목록 건수 조회
	 * 
	 * @Author : 김기윤
	 * @Date : 2022. 2. 22.
	 * @Method Name : selectVcatnCrtListTotCnt
	 * @return : int
	 */
	public int selectVcatnCrtListTotCnt(VcatnCrtVO vcatnCrtVO) throws DataAccessException {
		return (Integer) selectOne("vcatnCrtDAO.selectVcatnCrtListTotCnt", vcatnCrtVO);
	}

	/**
	 * 휴가생성관리 - 개별등록 - 트리조회
	 * 
	 * @Author : 김기윤
	 * @Date : 2022. 2. 22.
	 * @Method Name : selectVcatnCrtTree
	 * @return : List<DeptVO>
	 */
	public List<DeptVO> selectVcatnCrtTreeList(VcatnCrtVO vcatnCrtVO) throws DataAccessException {
		return selectList("vcatnCrtDAO.selectVcatnCrtTreeList", vcatnCrtVO);
	}

	/**
	 * 휴가생성관리 - 생성일자와 사용자로 생성 정보 조회
	 * 
	 * @Author : 김기윤
	 * @Date : 2022. 2. 22.
	 * @Method Name : selectVcatnCrtYr
	 * @return : VcatnCrtVO
	 */
	public VcatnCrtVO selectVcatnCrtYr(VcatnCrtVO vcatnCrtVO) throws DataAccessException {
		return (VcatnCrtVO) selectOne("vcatnCrtDAO.selectVcatnCrtYr", vcatnCrtVO);
	}

	/**
	 * 휴가생성관리 - 개별등록
	 * 
	 * @Author : 김기윤
	 * @Date : 2022. 2. 22.
	 * @Method Name : insertVcatnCrt
	 * @return : void
	 */
	public void insertVcatnCrt(VcatnCrtVO vcatnCrtVO) throws DataAccessException {
		insert("vcatnCrtDAO.insertVcatnCrt", vcatnCrtVO);
	}

	/**
	 * 휴가생성관리 - 일괄등록 - 회원 목록 조회
	 * 
	 * @Author : 김기윤
	 * @Date : 2022. 2. 22.
	 * @Method Name : selectMberList
	 * @return : List<MberManageVO>
	 */
	public List<MberManageVO> selectMberList(VcatnCrtVO vcatnCrtVO) throws DataAccessException {
		return selectList("vcatnCrtDAO.selectMberList", vcatnCrtVO);
	}

	/**
	 * 휴가생성관리 - 삭제
	 * 
	 * @Author : 김기윤
	 * @Date : 2022. 2. 22.
	 * @Method Name : deleteVcatnCrt
	 * @return : void
	 */
	public void deleteVcatnCrt(VcatnCrtVO vcatnCrtVO) throws DataAccessException {
		delete("vcatnCrtDAO.deleteVcatnCrt", vcatnCrtVO);
	}
}
