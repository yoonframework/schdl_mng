package egovframework.mng.schdl.vcatn.service.impl;

import java.util.List;

import org.egovframe.rte.psl.dataaccess.EgovAbstractMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import egovframework.mng.schdl.vcatn.service.VcatnVO;

/**
 * 휴가 DAO
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
@Repository("VcatnDAO")
public class VcatnDAO extends EgovAbstractMapper {

	/**
	 * 휴가 등록 - 사용자 휴가 남은 일수 조회
	 * 
	 * @Author : 김기윤
	 * @Date : 2022. 2. 24.
	 * @Method Name : selectRmndrDayCnt
	 * @return : String
	 */
	public String selectRmndrDayCnt(String uniqId) throws DataAccessException {
		return (String) selectOne("vcatnDAO.selectRmndrDayCnt", uniqId);
	}

	/**
	 * 휴가 등록 - 사용자 지정 일자에 휴가 등록 여부 확인
	 * 
	 * @Author : 김기윤
	 * @Date : 2022. 2. 25.
	 * @Method Name : selectVcatnRegYn
	 * @return : int
	 */
	public int selectVcatnRegYn(VcatnVO vcatnVO) throws DataAccessException {
		return (Integer) selectOne("vcatnDAO.selectVcatnRegYn", vcatnVO);
	}

	/**
	 * 휴가 등록
	 * 
	 * @Author : 김기윤
	 * @Date : 2022. 2. 24.
	 * @Method Name : insertVcatn
	 * @return : void
	 */
	public void insertVcatn(VcatnVO vcatnVO) throws DataAccessException {
		insert("vcatnDAO.insertVcatn", vcatnVO);
	}

	/**
	 * 휴가 수정
	 * 
	 * @Author : 김기윤
	 * @Date : 2022. 2. 24.
	 * @Method Name : updateVcatn
	 * @return : void
	 */
	public void updateVcatn(VcatnVO vcatnVO) throws DataAccessException {
		update("vcatnDAO.updateVcatn", vcatnVO);
	}

	/**
	 * 휴가 삭제
	 * 
	 * @Author : 김기윤
	 * @Date : 2022. 2. 24.
	 * @Method Name : deleteVcatn
	 * @return : void
	 */
	public void deleteVcatn(VcatnVO vcatnVO) throws DataAccessException {
		delete("vcatnDAO.deleteVcatn", vcatnVO);
	}

	/**
	 * 휴가 목록 조회
	 * 
	 * @Author : 김기윤
	 * @Date : 2022. 2. 25.
	 * @Method Name : selectVcatnList
	 * @return : List<VcatnVO>
	 */
	public List<VcatnVO> selectVcatnList(VcatnVO vcatnVO) throws DataAccessException {
		return selectList("vcatnDAO.selectVcatnList", vcatnVO);
	}

	/**
	 * 휴가 목록 건수 조회
	 * 
	 * @Author : 김기윤
	 * @Date : 2022. 2. 25.
	 * @Method Name : selectVcatnListTotCnt
	 * @return : int
	 */
	public int selectVcatnListTotCnt(VcatnVO vcatnVO) throws DataAccessException {
		return (Integer) selectOne("vcatnDAO.selectVcatnListTotCnt", vcatnVO);
	}
}
