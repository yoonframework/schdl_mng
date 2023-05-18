package egovframework.mng.vcatnCrt.service;

import java.util.List;

import org.egovframe.rte.fdl.cmmn.exception.EgovBizException;
import org.egovframe.rte.fdl.cmmn.exception.FdlException;

import egovframework.com.uss.umt.service.MberManageVO;
import egovframework.mng.cmmn.dept.service.DeptVO;

/**
 * 휴가생성관리 Service
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
public interface VcatnCrtService {

	/**
	 * 휴가생성관리 목록 조회
	 * 
	 * @Author : 김기윤
	 * @Date : 2022. 2. 22.
	 * @Method Name : selectVcatnCrtList
	 * @return : List<VcatnCrtVO>
	 */
	public List<VcatnCrtVO> selectVcatnCrtList(VcatnCrtVO vcatnCrtVO) throws EgovBizException;

	/**
	 * 휴가생성관리 목록 건수 조회
	 * 
	 * @Author : 김기윤
	 * @Date : 2022. 2. 22.
	 * @Method Name : selectVcatnCrtListTotCnt
	 * @return : int
	 */
	public int selectVcatnCrtListTotCnt(VcatnCrtVO vcatnCrtVO) throws EgovBizException;

	/**
	 * 휴가생성관리 - 개별등록 - 트리조회
	 * 
	 * @Author : 김기윤
	 * @Date : 2022. 2. 22.
	 * @Method Name : selectVcatnCrtTree
	 * @return : List<DeptVO>
	 */
	public List<DeptVO> selectVcatnCrtTreeList(VcatnCrtVO vcatnCrtVO) throws EgovBizException;

	/**
	 * 휴가생성관리 - 개별등록
	 * 
	 * @Author : 김기윤
	 * @Date : 2022. 2. 22.
	 * @Method Name : insertVcatnCrt
	 * @return : void
	 */
	public void insertVcatnCrt(VcatnCrtVO vcatnCrtVO) throws EgovBizException, FdlException;

	/**
	 * 휴가생성관리 - 일괄등록 - 회원 목록 조회
	 * 
	 * @Author : 김기윤
	 * @Date : 2022. 2. 22.
	 * @Method Name : selectMberList
	 * @return : List<MberManageVO>
	 */
	public List<MberManageVO> selectMberList(VcatnCrtVO vcatnCrtVO) throws EgovBizException;

	/**
	 * 휴가생성관리 - 일괄등록
	 * 
	 * @Author : 김기윤
	 * @Date : 2022. 2. 22.
	 * @Method Name : insertBndeVcatnCrt
	 * @return : void
	 */
	public void insertBndeVcatnCrt(VcatnCrtVO vcatnCrtVO) throws EgovBizException, FdlException;

	/**
	 * 휴가생성관리 - 삭제
	 * 
	 * @Author : 김기윤
	 * @Date : 2022. 2. 22.
	 * @Method Name : deleteVcatnCrt
	 * @return : void
	 */
	public void deleteVcatnCrt(VcatnCrtVO vcatnCrtVO) throws EgovBizException;
}
