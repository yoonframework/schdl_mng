package egovframework.com.sec.rmt.service;

import java.util.ArrayList;
import java.util.List;

/**
 * 롤관리에 대한 Vo 클래스를 정의한다.
 * @author 공통서비스 개발팀 이문준
 * @since 2009.06.01
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2009.03.20  이문준          최초 생성
 *
 * </pre>
 */

public class RoleManageVO extends RoleManage {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 롤 목록
	 */
	List <RoleManageVO> roleManageList;
	/**
	 * 삭제대상 목록
	 */
    String[] delYn;

	/**
	 * roleManageList attribute 를 리턴한다.
	 * @return List<RoleManageVO>
	 */
	public List<RoleManageVO> getRoleManageList() {
		if (this.roleManageList == null) {
			return null;
		} else {
			List<RoleManageVO> list = new ArrayList<>();
			list.addAll(this.roleManageList);
			return list;
		}
	}
	/**
	 * roleManageList attribute 값을 설정한다.
	 * @param roleManageList List<RoleManageVO>
	 */
	public void setRoleManageList(List<RoleManageVO> roleManageList) {
		if (roleManageList != null) {
			this.roleManageList = new ArrayList<RoleManageVO>();
			for (RoleManageVO vo : roleManageList) {
				this.roleManageList.add(vo);
			}
		}
	}
	/**
	 * delYn attribute 를 리턴한다.
	 * @return String[]
	 */
	public String[] getDelYn() {
		if (delYn == null) {
			return null;
		} else {
			int cnt = delYn.length;
			String[] list = new String[cnt];
			for (int i = 0; i < cnt; i++) {
				list[i] = delYn[i];
			}
			return list;
		}
	}
	/**
	 * delYn attribute 값을 설정한다.
	 * @param delYn String[]
	 */
	public void setDelYn(String[] delYn) {
		if (delYn != null) {
			int cnt = delYn.length;
			this.delYn = new String[cnt];
			for (int i = 0; i < cnt; i++) {
				this.delYn[i] = delYn[i];
			}
		}
	}


}