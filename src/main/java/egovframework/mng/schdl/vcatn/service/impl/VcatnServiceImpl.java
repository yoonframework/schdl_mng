package egovframework.mng.schdl.vcatn.service.impl;

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
import egovframework.mng.schdl.vcatn.service.VcatnService;
import egovframework.mng.schdl.vcatn.service.VcatnVO;

/**
 * 휴가 Service Impl
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
@Service("VcatnService")
public class VcatnServiceImpl implements VcatnService {

	/** 휴가 DAO */
	@Resource(name = "VcatnDAO")
	private VcatnDAO vcatnDAO;

	/** 휴가 ID 생성 서비스 */
	@Resource(name = "vcatnIdGnrService")
	private EgovIdGnrService vcatnIdGnrService;

	@Override
	public String selectRmndrDayCnt() throws EgovBizException {
		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		if (user.getAuthorCode().contains("ROLE_USER")) {
			return vcatnDAO.selectRmndrDayCnt(user.getUniqId());
		} else {
			return "";
		}
	}

	@Override
	public String insertVcatn(VcatnVO vcatnVO) throws EgovBizException, FdlException {
		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

		// 일반회원일경우 신청자 아이디를 세션에서 가져옴(일반회원은 클라이언트에서 가져오지 않음)
		if (StringUtils.isBlank(vcatnVO.getAplcntId()) && user.getAuthorCode().contains("ROLE_USER")) {
			vcatnVO.setAplcntId(user.getUniqId());
		}

		int regCnt = vcatnDAO.selectVcatnRegYn(vcatnVO);

		if (regCnt > 0) {
			return AjaxWrapper.FAIL;
		} else {
			vcatnVO.setVcatnId(vcatnIdGnrService.getNextStringId());
			vcatnVO.setRgtrId(user.getUniqId());
			vcatnVO.setMdfrId(user.getUniqId());
			vcatnDAO.insertVcatn(vcatnVO);

			return AjaxWrapper.SUCCESS;
		}
	}

	@Override
	public void updateVcatn(VcatnVO vcatnVO) throws EgovBizException {
		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		vcatnVO.setMdfrId(user.getUniqId());
		vcatnDAO.updateVcatn(vcatnVO);
	}

	@Override
	public void deleteVcatn(VcatnVO vcatnVO) throws EgovBizException {
		vcatnDAO.deleteVcatn(vcatnVO);
	}

	@Override
	public List<VcatnVO> selectVcatnList(VcatnVO vcatnVO) throws EgovBizException {
		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		vcatnVO.setAplcntId(user.getUniqId());
		return vcatnDAO.selectVcatnList(vcatnVO);
	}

	@Override
	public int selectVcatnListTotCnt(VcatnVO vcatnVO) throws EgovBizException {
		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		vcatnVO.setAplcntId(user.getUniqId());
		return vcatnDAO.selectVcatnListTotCnt(vcatnVO);
	}
}
