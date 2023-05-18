package egovframework.com.uss.umt.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.egovframe.rte.fdl.cmmn.exception.EgovBizException;
import org.egovframe.rte.fdl.cmmn.exception.FdlException;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.menu.program.service.MenuFunctionInfoVO;
import egovframework.com.uss.umt.service.EgovMberManageService;
import egovframework.com.uss.umt.service.MberManageVO;
import egovframework.com.uss.umt.service.UserDefaultVO;
import egovframework.com.utl.sim.service.EgovFileScrty;
import egovframework.mng.emp.service.CrqfcMngVO;

/**
 * 일반회원관리에 관한비지니스클래스를 정의한다.
 * 
 * @author 공통서비스 개발팀 조재영
 * @since 2009.04.10
 * @version 1.0
 * @see
 *
 *      <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2009.04.10  조재영          최초 생성
 *   2014.12.08	 이기하			암호화방식 변경(EgovFileScrty.encryptPassword)
 *
 *      </pre>
 */
@Service("mberManageService")
public class EgovMberManageServiceImpl extends EgovAbstractServiceImpl implements EgovMberManageService {

	/** userManageDAO */
	@Resource(name = "userManageDAO")
	private UserManageDAO userManageDAO;

	/** mberManageDAO */
	@Resource(name = "mberManageDAO")
	private MberManageDAO mberManageDAO;

	/** entrprsManageDAO */
	@Resource(name = "entrprsManageDAO")
	private EntrprsManageDAO entrprsManageDAO;

	/** egovUsrCnfrmIdGnrService */
	@Resource(name = "egovUsrCnfrmIdGnrService")
	private EgovIdGnrService idgenService;

	@Resource(name = "egovFunctionAuthoIdGnrService")
	private EgovIdGnrService egovFunctionAuthoIdGnrService;

	/** 자격증 아이디 생성 */
	@Autowired
	private EgovIdGnrService crqfcIdGnrService;

	/**
	 * 사용자의 기본정보를 화면에서 입력하여 항목의 정합성을 체크하고 데이터베이스에 저장
	 * 
	 * @param mberManageVO 일반회원 등록정보
	 * @return result 등록결과
	 * @throws FdlException
	 * @throws Exception
	 */
	@Override
	public String insertMber(MberManageVO mberManageVO) throws EgovBizException, FdlException {
		// 고유아이디 셋팅
		String uniqId = idgenService.getNextStringId();
		mberManageVO.setUniqId(uniqId);
		// 패스워드 암호화
		String pass = EgovFileScrty.encryptPassword(mberManageVO.getPassword(), mberManageVO.getMberId());
		mberManageVO.setPassword(pass);

		String result = mberManageDAO.insertMber(mberManageVO);

		// 자격증 정보 저장
		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		mberManageDAO.deleteCrqfcMng(mberManageVO.getUniqId());
		if (mberManageVO.getCrqfcList() != null && mberManageVO.getCrqfcList().size() != 0) {
			for (CrqfcMngVO vo : mberManageVO.getCrqfcList()) {
				vo.setEmpId(mberManageVO.getUniqId());
				vo.setCrqfcId(crqfcIdGnrService.getNextStringId());
				vo.setRgtrId(user.getUniqId());
				vo.setMdfrId(user.getUniqId());
				mberManageDAO.insertCrqfcMng(vo);
			}
		}

		// 기능권한정보 수정 시작
		// LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		// mberManageDAO.deleteFunctionAutho(mberManageVO);
		// if (mberManageVO.getFunctionList() != null &&
		// mberManageVO.getFunctionList().size() != 0) {
		// for(FunctionAuthoInfoVO vo : mberManageVO.getFunctionList()) {
		// vo.setUserId(mberManageVO.getUniqId());
		// vo.setFncAuthorSeq(egovFunctionAuthoIdGnrService.getNextStringId());
		// vo.setFrstRegisterId(user.getUniqId());
		//
		// mberManageDAO.insertFunctionAutho(vo);
		// }
		// }
		// 기능권한정보 수정 종료

		return result;
	}

	/**
	 * 기 등록된 사용자 중 검색조건에 맞는 일반회원의 정보를 데이터베이스에서 읽어와 화면에 출력
	 * 
	 * @param uniqId 상세조회대상 일반회원아이디
	 * @return mberManageVO 일반회원상세정보
	 * @throws Exception
	 */
	@Override
	public MberManageVO selectMber(String uniqId) {
		MberManageVO mberManageVO = mberManageDAO.selectMber(uniqId);
		return mberManageVO;
	}

	/**
	 * 기 등록된 회원 중 검색조건에 맞는 회원들의 정보를 데이터베이스에서 읽어와 화면에 출력
	 * 
	 * @param userSearchVO 검색조건
	 * @return List<MberManageVO> 일반회원목록정보
	 */
	@Override
	public List<MberManageVO> selectMberList(UserDefaultVO userSearchVO) throws EgovBizException {
		return mberManageDAO.selectMberList(userSearchVO);
	}

	/**
	 * 일반회원 총 갯수를 조회한다.
	 * 
	 * @param userSearchVO 검색조건
	 * @return 일반회원총갯수(int)
	 */
	@Override
	public int selectMberListTotCnt(UserDefaultVO userSearchVO) throws EgovBizException {
		return mberManageDAO.selectMberListTotCnt(userSearchVO);
	}

	/**
	 * 화면에 조회된 일반회원의 기본정보를 수정하여 항목의 정합성을 체크하고 수정된 데이터를 데이터베이스에 반영
	 * 
	 * @param mberManageVO 일반회원수정정보
	 * @throws FdlException
	 * @throws Exception
	 */
	@Override
	@CacheEvict(value = "functionAutho", key = "{#mberManageVO.uniqId}")
	public void updateMber(MberManageVO mberManageVO) throws EgovBizException, FdlException {
		// 패스워드 암호화
		if (!StringUtils.isBlank(mberManageVO.getPassword())) {
			String pass = EgovFileScrty.encryptPassword(mberManageVO.getPassword(), mberManageVO.getMberId());
			mberManageVO.setPassword(pass);
		}
		mberManageDAO.updateMber(mberManageVO);

		// 자격증 정보 저장
		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		mberManageDAO.deleteCrqfcMng(mberManageVO.getUniqId());
		if (mberManageVO.getCrqfcList() != null && mberManageVO.getCrqfcList().size() != 0) {
			for (CrqfcMngVO vo : mberManageVO.getCrqfcList()) {
				vo.setEmpId(mberManageVO.getUniqId());
				vo.setCrqfcId(crqfcIdGnrService.getNextStringId());
				vo.setRgtrId(user.getUniqId());
				vo.setMdfrId(user.getUniqId());

				mberManageDAO.insertCrqfcMng(vo);
			}
		}

		// 기능권한정보 수정 시작
		// LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		// mberManageDAO.deleteFunctionAutho(mberManageVO);
		// if (mberManageVO.getFunctionList() != null &&
		// mberManageVO.getFunctionList().size() != 0) {
		// for(FunctionAuthoInfoVO vo : mberManageVO.getFunctionList()) {
		// vo.setUserId(mberManageVO.getUniqId());
		// vo.setFncAuthorSeq(egovFunctionAuthoIdGnrService.getNextStringId());
		// vo.setFrstRegisterId(user.getUniqId());
		//
		// mberManageDAO.insertFunctionAutho(vo);
		// }
		// }
		// 기능권한정보 수정 종료
	}

	/**
	 * 화면에 조회된 사용자의 정보를 데이터베이스에서 삭제
	 * 
	 * @param checkedIdForDel 삭제대상 일반회원아이디
	 * @throws Exception
	 */
	@Override
	public void deleteMber(String checkedIdForDel) throws Exception {
		String[] delId = checkedIdForDel.split(",");
		for (int i = 0; i < delId.length; i++) {
			String[] id = delId[i].split(":");
			if (id[0].equals("USR03")) {
				// 업무사용자(직원)삭제
				userManageDAO.deleteUser(id[1]);
			} else if (id[0].equals("USR01")) {
				// 등록된 자격증 삭제
				mberManageDAO.deleteCrqfcMng(id[1]);
				// 일반회원삭제
				mberManageDAO.deleteMber(id[1]);
			} else if (id[0].equals("USR02")) {
				// 기업회원삭제
				entrprsManageDAO.deleteEntrprsmber(id[1]);
			}
		}
	}

	/**
	 * 일반회원 약관확인
	 * 
	 * @param stplatId 일반회원약관아이디
	 * @return 일반회원약관정보(List)
	 * @throws Exception
	 */
	@Override
	public List<?> selectStplat(String stplatId) {
		return mberManageDAO.selectStplat(stplatId);
	}

	/**
	 * 일반회원암호수정
	 * 
	 * @param mberManageVO 일반회원수정정보(비밀번호)
	 * @throws Exception
	 */
	@Override
	public void updatePassword(MberManageVO mberManageVO) {
		mberManageDAO.updatePassword(mberManageVO);
	}

	/**
	 * 일반회원이 비밀번호를 기억하지 못할 때 비밀번호를 찾을 수 있도록 함
	 * 
	 * @param passVO 일반회원암호 조회조건정보
	 * @return mberManageVO 일반회원암호정보
	 * @throws Exception
	 */
	@Override
	public MberManageVO selectPassword(MberManageVO passVO) {
		MberManageVO mberManageVO = mberManageDAO.selectPassword(passVO);
		return mberManageVO;
	}

	/**
	 * 일반회원이 회원정보 수정에서 탈퇴 시 처리
	 * 
	 * @param mberManageVO
	 * @throws Exception
	 */
	@Override
	public void mberSttusUpdate(MberManageVO mberManageVO) throws Exception {
		// TODO Auto-generated method stub
		mberManageDAO.mberSttusUpdate(mberManageVO);
	}

	@Override
	public List<MenuFunctionInfoVO> selectMenuFunctionInfo() throws Exception {
		// TODO Auto-generated method stub
		return mberManageDAO.selectMenuFunctionInfo();
	}
}