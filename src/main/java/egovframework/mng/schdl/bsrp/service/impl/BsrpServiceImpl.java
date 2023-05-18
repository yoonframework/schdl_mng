package egovframework.mng.schdl.bsrp.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.egovframe.rte.fdl.cmmn.exception.EgovBizException;
import org.egovframe.rte.fdl.cmmn.exception.FdlException;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.springframework.stereotype.Service;

import egovframework.com.cmm.AjaxWrapper;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.mng.cmmn.dept.service.DeptVO;
import egovframework.mng.schdl.bsrp.service.BsrpCncdntVO;
import egovframework.mng.schdl.bsrp.service.BsrpService;
import egovframework.mng.schdl.bsrp.service.BsrpVO;

/**
 * 출장 Service Impl
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
@Service("BsrpService")
public class BsrpServiceImpl implements BsrpService {

	/** 출장 DAO */
	@Resource(name = "BsrpDAO")
	private BsrpDAO bsrpDAO;

	/** 출장 ID 생성 서비스 */
	@Resource(name = "bsrpIdGnrService")
	private EgovIdGnrService bsrpIdGnrService;

	/** 출장 동행자 ID 생성 서비스 */
	@Resource(name = "bsrpCncdntIdGnrService")
	private EgovIdGnrService bsrpCncdntIdGnrService;

	@Override
	public List<DeptVO> selectCncdntMbrTreeList(BsrpVO bsrpVO) throws EgovBizException {
		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		BsrpCncdntVO bsrpCncdntVO = new BsrpCncdntVO();
		bsrpCncdntVO.setTgrpnId(user.getUniqId());

		if (bsrpVO.getBsrpCncdntList() == null) {
			List<BsrpCncdntVO> bsrpCncdntList = new ArrayList<BsrpCncdntVO>();
			bsrpCncdntList.add(bsrpCncdntVO);
			bsrpVO.setBsrpCncdntList(bsrpCncdntList);
		} else {
			bsrpVO.getBsrpCncdntList().add(bsrpCncdntVO);
		}
		return bsrpDAO.selectCncdntMbrTreeList(bsrpVO);
	}

	@Override
	public String insertBsrp(BsrpVO bsrpVO) throws EgovBizException, FdlException {

		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

		// 일반회원일경우 신청자 아이디를 세션에서 가져옴(일반회원은 클라이언트에서 가져오지 않음)
		if (StringUtils.isBlank(bsrpVO.getAplcntId()) && user.getAuthorCode().contains("ROLE_USER")) {
			bsrpVO.setAplcntId(user.getUniqId());
		}

		int regCnt = bsrpDAO.selectBsrpRegYn(bsrpVO);

		if (regCnt > 0) {
			return AjaxWrapper.FAIL;
		} else {
			bsrpVO.setBsrpId(bsrpIdGnrService.getNextStringId());
			bsrpVO.setRgtrId(user.getUniqId());
			bsrpVO.setMdfrId(user.getUniqId());
			bsrpDAO.insertBsrp(bsrpVO);

			bsrpDAO.deleteBsrpCncdnt(bsrpVO);
			if (bsrpVO.getBsrpCncdntList() != null && !bsrpVO.getBsrpCncdntList().isEmpty()) {
				for (BsrpCncdntVO vo : bsrpVO.getBsrpCncdntList()) {
					vo.setBsrpCncdntId(bsrpCncdntIdGnrService.getNextStringId());
					vo.setBsrpId(bsrpVO.getBsrpId());
					vo.setRgtrId(user.getUniqId());
					vo.setMdfrId(user.getUniqId());
					bsrpDAO.insertBsrpCncdnt(vo);
				}
			}

			return AjaxWrapper.SUCCESS;
		}
	}

	@Override
	public String updateBsrp(BsrpVO bsrpVO) throws EgovBizException, FdlException {
		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

		int regCnt = bsrpDAO.selectBsrpRegYn(bsrpVO);

		if (regCnt > 0) {
			return AjaxWrapper.FAIL;
		} else {
			bsrpVO.setMdfrId(user.getUniqId());
			bsrpDAO.updateBsrp(bsrpVO);
			bsrpDAO.deleteBsrpCncdnt(bsrpVO);
			if (bsrpVO.getBsrpCncdntList() != null && !bsrpVO.getBsrpCncdntList().isEmpty()) {
				for (BsrpCncdntVO vo : bsrpVO.getBsrpCncdntList()) {
					vo.setBsrpCncdntId(bsrpCncdntIdGnrService.getNextStringId());
					vo.setBsrpId(bsrpVO.getBsrpId());
					vo.setRgtrId(user.getUniqId());
					vo.setMdfrId(user.getUniqId());
					bsrpDAO.insertBsrpCncdnt(vo);
				}
			}

			return AjaxWrapper.SUCCESS;
		}

	}

	@Override
	public void deleteBsrp(BsrpVO bsrpVO) throws EgovBizException {
		bsrpDAO.deleteBsrpCncdnt(bsrpVO);
		bsrpDAO.deleteBsrp(bsrpVO);
	}

	@Override
	public List<BsrpVO> selectBsrpList(BsrpVO bsrpVO) throws EgovBizException {
		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		bsrpVO.setAplcntId(user.getUniqId());
		return bsrpDAO.selectBsrpList(bsrpVO);
	}

	@Override
	public int selectBsrpListTotCnt(BsrpVO bsrpVO) throws EgovBizException {
		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		bsrpVO.setAplcntId(user.getUniqId());
		return bsrpDAO.selectBsrpListTotCnt(bsrpVO);
	}
}
