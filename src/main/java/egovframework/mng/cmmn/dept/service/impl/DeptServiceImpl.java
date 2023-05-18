package egovframework.mng.cmmn.dept.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.egovframe.rte.fdl.cmmn.exception.EgovBizException;
import org.egovframe.rte.fdl.cmmn.exception.FdlException;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.springframework.stereotype.Service;

import egovframework.mng.cmmn.dept.service.DeptService;
import egovframework.mng.cmmn.dept.service.DeptVO;

/**
 * 부서를 처리하는 비즈니스 구현 클래스
 * 
 * @since 2022. 1. 24.
 * @author 김기윤
 * 
 *         <PRE>
 * -----------------------
 * 개정이력
 * 2022. 1. 24. 김기윤 : 최초작성
 *         </PRE>
 */
@Service("DeptService")
public class DeptServiceImpl extends EgovAbstractServiceImpl implements DeptService {
	@Resource(name = "DeptDAO")
	private DeptDAO deptDAO;

	@Resource(name = "deptIdGnrService")
	private EgovIdGnrService deptIdGnrService;

	// 부정정보 목록 조회
	@Override
	public List<DeptVO> selectDeptList() throws EgovBizException {
		return deptDAO.selectDeptList();
	}

	// 부서정보 생성(새로운 노드 생성)
	@Override
	public DeptVO insertDept(DeptVO deptVO) throws EgovBizException, FdlException {
		deptVO.setDeptId(deptIdGnrService.getNextStringId());
		deptDAO.insertDept(deptVO);
		return deptVO;
	}

	// 부서정보 삭제
	@Override
	public void deleteDept(DeptVO deptVO) throws EgovBizException {
		deptDAO.deleteDept(deptVO);
	}

	// 부서정보 수정
	@Override
	public void updateDept(DeptVO deptVO) throws EgovBizException {
		deptDAO.updateDept(deptVO);
	}

	// 부서정보 트리노드 드래그 수정
	@Override
	public void updateDeptNode(DeptVO deptVO) throws EgovBizException {
		for (DeptVO vo : deptVO.getDeptList()) {
			deptDAO.updateDept(vo);
		}

	}
}
