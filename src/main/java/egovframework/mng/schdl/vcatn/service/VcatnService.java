package egovframework.mng.schdl.vcatn.service;

import java.util.List;

import org.egovframe.rte.fdl.cmmn.exception.EgovBizException;
import org.egovframe.rte.fdl.cmmn.exception.FdlException;

/**
 * 휴가 Service
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
public interface VcatnService {

	/**
	 * 휴가 등록 - 사용자 휴가 남은 일수 조회
	 * 
	 * @Author : 김기윤
	 * @Date : 2022. 2. 24.
	 * @Method Name : selectRmndrDayCnt
	 * @return : String
	 */
	public String selectRmndrDayCnt() throws EgovBizException;

	/**
	 * 휴가 등록
	 * 
	 * @Author : 김기윤
	 * @Date : 2022. 2. 24.
	 * @Method Name : insertVcatn
	 * @return : String
	 */
	public String insertVcatn(VcatnVO vcatnVO) throws EgovBizException, FdlException;

	/**
	 * 휴가 수정
	 * 
	 * @Author : 김기윤
	 * @Date : 2022. 2. 24.
	 * @Method Name : updateVcatn
	 * @return : void
	 */
	public void updateVcatn(VcatnVO vcatnVO) throws EgovBizException;

	/**
	 * 휴가 삭제
	 * 
	 * @Author : 김기윤
	 * @Date : 2022. 2. 24.
	 * @Method Name : deleteVcatn
	 * @return : void
	 */
	public void deleteVcatn(VcatnVO vcatnVO) throws EgovBizException;

	/**
	 * 휴가 목록 조회
	 * 
	 * @Author : 김기윤
	 * @Date : 2022. 2. 25.
	 * @Method Name : selectVcatnList
	 * @return : List<VcatnVO>
	 */
	public List<VcatnVO> selectVcatnList(VcatnVO vcatnVO) throws EgovBizException;

	/**
	 * 휴가 목록 건수 조회
	 * 
	 * @Author : 김기윤
	 * @Date : 2022. 2. 25.
	 * @Method Name : selectVcatnListTotCnt
	 * @return : int
	 */
	public int selectVcatnListTotCnt(VcatnVO vcatnVO) throws EgovBizException;
}
