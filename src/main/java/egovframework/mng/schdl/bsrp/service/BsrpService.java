package egovframework.mng.schdl.bsrp.service;

import java.util.List;

import org.egovframe.rte.fdl.cmmn.exception.EgovBizException;
import org.egovframe.rte.fdl.cmmn.exception.FdlException;

import egovframework.mng.cmmn.dept.service.DeptVO;

/**
 * 출장 Service
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
public interface BsrpService {

	/**
	 * 출장 등록 - 동행자 정보 조회
	 * 
	 * @Author : 김기윤
	 * @Date : 2022. 2. 24.
	 * @Method Name : selectCncdntMbrTreeList
	 * @return : List<DeptVO>
	 */
	public List<DeptVO> selectCncdntMbrTreeList(BsrpVO bsrpVO) throws EgovBizException;

	/**
	 * 출장 등록
	 * 
	 * @Author : 김기윤
	 * @Date : 2022. 2. 24.
	 * @Method Name : insertBsrp
	 * @return : String
	 */
	public String insertBsrp(BsrpVO bsrpVO) throws EgovBizException, FdlException;

	/**
	 * 출장 수정
	 * 
	 * @Author : 김기윤
	 * @Date : 2022. 2. 24.
	 * @Method Name : updateBsrp
	 * @return : String
	 */
	public String updateBsrp(BsrpVO bsrpVO) throws EgovBizException, FdlException;

	/**
	 * 출장 삭제
	 * 
	 * @Author : 김기윤
	 * @Date : 2022. 2. 24.
	 * @Method Name : deleteBsrp
	 * @return : void
	 */
	public void deleteBsrp(BsrpVO bsrpVO) throws EgovBizException;

	/**
	 * 출장 목록 조회
	 * 
	 * @Author : 김기윤
	 * @Date : 2022. 2. 25.
	 * @Method Name : selectBsrpList
	 * @return : List<BsrpVO>
	 */
	public List<BsrpVO> selectBsrpList(BsrpVO bsrpVO) throws EgovBizException;

	/**
	 * 출장 목록 건수 조회
	 * 
	 * @Author : 김기윤
	 * @Date : 2022. 2. 25.
	 * @Method Name : selectBsrpListTotCnt
	 * @return : int
	 */
	public int selectBsrpListTotCnt(BsrpVO bsrpVO) throws EgovBizException;
}
