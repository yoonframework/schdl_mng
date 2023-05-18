package egovframework.mng.schdl.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.egovframe.rte.fdl.cmmn.exception.EgovBizException;
import org.springframework.stereotype.Service;

import egovframework.mng.cmmn.dept.service.DeptVO;
import egovframework.mng.schdl.service.SchdlService;
import egovframework.mng.schdl.service.SchdlVO;

/**
 * 일정관리 Service Impl
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
@Service("SchdlService")
public class SchdlServiceImpl extends EgovAbstractServiceImpl implements SchdlService {

	/**
	 * 일정관리 DAO
	 */
	@Resource(name = "SchdlDAO")
	private SchdlDAO schdlDAO;

	@Override
	public List<SchdlVO> selectSchdlList(SchdlVO schdlVO) throws EgovBizException {
		return schdlDAO.selectSchdlList(schdlVO);
	}

	@Override
	public int selectSchdlListTotCnt(SchdlVO schdlVO) throws EgovBizException {
		return schdlDAO.selectSchdlListTotCnt(schdlVO);
	}

	@Override
	public List<DeptVO> selectDeptList() throws EgovBizException {
		return schdlDAO.selectDeptList();
	}

	@Override
	public List<DeptVO> selectMbrTreeList(SchdlVO schdlVO) throws EgovBizException {
		return schdlDAO.selectMbrTreeList(schdlVO);
	}
}
