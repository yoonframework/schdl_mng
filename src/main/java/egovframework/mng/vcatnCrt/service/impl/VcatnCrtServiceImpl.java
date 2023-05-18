package egovframework.mng.vcatnCrt.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.egovframe.rte.fdl.cmmn.exception.EgovBizException;
import org.egovframe.rte.fdl.cmmn.exception.FdlException;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.springframework.stereotype.Service;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.uss.umt.service.MberManageVO;
import egovframework.mng.cmmn.dept.service.DeptVO;
import egovframework.mng.vcatnCrt.service.VcatnCrtService;
import egovframework.mng.vcatnCrt.service.VcatnCrtVO;

/**
 * 휴가생성관리 Service Impl
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
@Service("VcatnCrtService")
public class VcatnCrtServiceImpl extends EgovAbstractServiceImpl implements VcatnCrtService {

	/**
	 * 휴가생성관리 DAO
	 */
	@Resource(name = "VcatnCrtDAO")
	private VcatnCrtDAO vcatnCrtDAO;

	/**
	 * 휴가생성관리 ID 생성 서비스
	 */
	@Resource(name = "vcatnCrtIdGnrService")
	private EgovIdGnrService vcatnCrtIdGnrService;

	@Override
	public List<VcatnCrtVO> selectVcatnCrtList(VcatnCrtVO vcatnCrtVO) throws EgovBizException {
		return vcatnCrtDAO.selectVcatnCrtList(vcatnCrtVO);
	}

	@Override
	public int selectVcatnCrtListTotCnt(VcatnCrtVO vcatnCrtVO) throws EgovBizException {
		return vcatnCrtDAO.selectVcatnCrtListTotCnt(vcatnCrtVO);
	}

	@Override
	public List<DeptVO> selectVcatnCrtTreeList(VcatnCrtVO vcatnCrtVO) throws EgovBizException {
		return vcatnCrtDAO.selectVcatnCrtTreeList(vcatnCrtVO);
	}

	@Override
	public void insertVcatnCrt(VcatnCrtVO vcatnCrtVO) throws EgovBizException, FdlException {
		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		vcatnCrtVO.setRgtrId(user.getUniqId());
		vcatnCrtVO.setMdfrId(user.getUniqId());
		// 기존 휴가생성년도에 등록되있는지 조회
		VcatnCrtVO registedVO = vcatnCrtDAO.selectVcatnCrtYr(vcatnCrtVO);
		if (registedVO == null) {
			vcatnCrtVO.setVcatnCrtMngId(vcatnCrtIdGnrService.getNextStringId());
		} else {
			vcatnCrtVO.setVcatnCrtMngId(registedVO.getVcatnCrtMngId());
		}

		vcatnCrtDAO.insertVcatnCrt(vcatnCrtVO);
	}

	@Override
	public List<MberManageVO> selectMberList(VcatnCrtVO vcatnCrtVO) throws EgovBizException {
		return vcatnCrtDAO.selectMberList(vcatnCrtVO);
	}

	@Override
	public void insertBndeVcatnCrt(VcatnCrtVO vcatnCrtVO) throws EgovBizException, FdlException {
		if (vcatnCrtVO.getVcatnCrtList() != null && !vcatnCrtVO.getVcatnCrtList().isEmpty()) {
			for (VcatnCrtVO vo : vcatnCrtVO.getVcatnCrtList()) {
				LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
				vo.setRgtrId(user.getUniqId());
				vo.setMdfrId(user.getUniqId());
				// 기존 휴가생성년도에 등록되있는지 조회
				VcatnCrtVO registedVO = vcatnCrtDAO.selectVcatnCrtYr(vo);
				if (registedVO == null) {
					vo.setVcatnCrtMngId(vcatnCrtIdGnrService.getNextStringId());
				} else {
					vo.setVcatnCrtMngId(registedVO.getVcatnCrtMngId());
				}

				vcatnCrtDAO.insertVcatnCrt(vo);
			}
		}
	}

	@Override
	public void deleteVcatnCrt(VcatnCrtVO vcatnCrtVO) throws EgovBizException {
		if (vcatnCrtVO.getVcatnCrtList() != null && !vcatnCrtVO.getVcatnCrtList().isEmpty()) {
			for (VcatnCrtVO vo : vcatnCrtVO.getVcatnCrtList()) {
				vcatnCrtDAO.deleteVcatnCrt(vo);
			}
		}
	}
}
