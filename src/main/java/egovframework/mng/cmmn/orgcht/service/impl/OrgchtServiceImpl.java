package egovframework.mng.cmmn.orgcht.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.egovframe.rte.fdl.cmmn.exception.EgovBizException;
import org.springframework.stereotype.Service;

import egovframework.com.uss.umt.service.MberManageVO;
import egovframework.mng.cmmn.dept.service.DeptVO;
import egovframework.mng.cmmn.orgcht.service.OrgchtService;

/**
 * 조직도 및 사용자맵 Service Impl
 * 
 * @since 2022. 2. 21.
 * @author 김기윤
 * 
 *         <PRE>
 * -----------------------
 * 개정이력
 *  2022. 2. 21. 김기윤 : 최초작성
 *         </PRE>
 */
@Service("OrgchtService")
public class OrgchtServiceImpl extends EgovAbstractServiceImpl implements OrgchtService {

	/**
	 * 조직도 및 사용자맵 DAO
	 */
	@Resource(name = "OrgchtDAO")
	private OrgchtDAO orgchtDAO;

	@Override
	public List<DeptVO> selectDeptList() throws EgovBizException {
		return orgchtDAO.selectDeptList();
	}

	@Override
	public List<MberManageVO> selectDeptMbrList(String deptId) throws EgovBizException {
		return orgchtDAO.selectDeptMbrList(deptId);
	}

	@Override
	public List<MberManageVO> selectAllMbrList(MberManageVO mberManageVO) throws EgovBizException {
		return orgchtDAO.selectAllMbrList(mberManageVO);
	}

	@Override
	public MberManageVO selectMbr(MberManageVO mberManageVO) throws EgovBizException {
		return orgchtDAO.selectMbr(mberManageVO);
	}
}
