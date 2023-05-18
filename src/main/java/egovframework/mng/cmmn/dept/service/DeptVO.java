package egovframework.mng.cmmn.dept.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.type.Alias;

@Alias("DeptVO")
public class DeptVO extends Dept {
	/**
	 * 하위메뉴 갯수
	 */
	private String droppable;

	/**
	 * 부서 리스트
	 */
	private List<DeptVO> deptList;

	/**
	 * 직원 휴가 남은 일수
	 */
	private String rmndrDayCnt;

	/**
	 * 직원 직위 ID
	 */
	private String tgrpnOfcpsId;
	/**
	 * 직원 직위 명
	 */
	private String tgrpnOfcpsNm;
	/**
	 * 직원 부서 ID
	 */
	private String tgrpnDeptId;
	/**
	 * 직원 부서 명
	 */
	private String tgrpnDeptNm;

	public String getDroppable() {
		return droppable;
	}

	public void setDroppable(String droppable) {
		this.droppable = droppable;
	}

	public List<DeptVO> getDeptList() {
		if (this.deptList == null) {
			return null;
		} else {
			List<DeptVO> list = this.deptList;
			return list;
		}
	}

	public void setDeptList(List<DeptVO> deptList) {
		if (deptList != null) {
			this.deptList = new ArrayList<DeptVO>();
			for (DeptVO vo : deptList) {
				this.deptList.add(vo);
			}
		}
	}

	public String getRmndrDayCnt() {
		return rmndrDayCnt;
	}

	public void setRmndrDayCnt(String rmndrDayCnt) {
		this.rmndrDayCnt = rmndrDayCnt;
	}

	public String getTgrpnOfcpsId() {
		return tgrpnOfcpsId;
	}

	public void setTgrpnOfcpsId(String tgrpnOfcpsId) {
		this.tgrpnOfcpsId = tgrpnOfcpsId;
	}

	public String getTgrpnOfcpsNm() {
		return tgrpnOfcpsNm;
	}

	public void setTgrpnOfcpsNm(String tgrpnOfcpsNm) {
		this.tgrpnOfcpsNm = tgrpnOfcpsNm;
	}

	public String getTgrpnDeptId() {
		return tgrpnDeptId;
	}

	public void setTgrpnDeptId(String tgrpnDeptId) {
		this.tgrpnDeptId = tgrpnDeptId;
	}

	public String getTgrpnDeptNm() {
		return tgrpnDeptNm;
	}

	public void setTgrpnDeptNm(String tgrpnDeptNm) {
		this.tgrpnDeptNm = tgrpnDeptNm;
	}

}
