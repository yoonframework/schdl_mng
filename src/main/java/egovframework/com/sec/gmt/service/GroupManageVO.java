package egovframework.com.sec.gmt.service;

import java.util.ArrayList;
import java.util.List;



/**
 * 그룹관리에 대한 Vo 클래스를 정의한다.
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

public class GroupManageVO extends GroupManage {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 그룹 목록
	 */
	List <GroupManageVO> groupManageList;
	/**
	 * 삭제대상 목록
	 */
    String[] delYn;

	/**
	 * groupManageList attribute 를 리턴한다.
	 * @return List<GroupManageVO>
	 */
	public List<GroupManageVO> getGroupManageList() {
		if (this.groupManageList == null) {
			return null;
		} else {
			List<GroupManageVO> list = new ArrayList<>();
			list.addAll(this.groupManageList);
			return list;
		}
	}

	/**
	 * groupManageList attribute 값을 설정한다.
	 * @param groupManageList List<GroupManageVO>
	 */
	public void setGroupManageList(List<GroupManageVO> groupManageList) {
		if (groupManageList != null) {
			this.groupManageList = new ArrayList<GroupManageVO>();
			for (GroupManageVO vo : groupManageList) {
				this.groupManageList.add(vo);
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