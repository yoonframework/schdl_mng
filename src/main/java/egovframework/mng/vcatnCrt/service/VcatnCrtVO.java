package egovframework.mng.vcatnCrt.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.type.Alias;

import egovframework.com.uss.umt.service.MberManageVO;

@Alias("VcatnCrtVO")
public class VcatnCrtVO extends VcatnCrt implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 하위메뉴 갯수
	 */
	private String droppable;

	/**
	 * 휴가 생성자 이름
	 */
	private String vcatnCrtrNm;

	/**
	 * 휴가생성목록
	 */
	private List<VcatnCrtVO> vcatnCrtList;

	/**
	 * 회원관리
	 */
	private MberManageVO mberManageVO;

	/**
	 * 부서ID
	 */
	private String deptId;

	public String getDroppable() {
		return droppable;
	}

	public void setDroppable(String droppable) {
		this.droppable = droppable;
	}

	public String getVcatnCrtrNm() {
		return vcatnCrtrNm;
	}

	public void setVcatnCrtrNm(String vcatnCrtrNm) {
		this.vcatnCrtrNm = vcatnCrtrNm;
	}

	public List<VcatnCrtVO> getVcatnCrtList() {
		if (this.vcatnCrtList == null) {
			return null;
		} else {
			List<VcatnCrtVO> list = this.vcatnCrtList;
			return list;
		}
	}

	public void setVcatnCrtList(List<VcatnCrtVO> vcatnCrtList) {
		if (vcatnCrtList != null) {
			this.vcatnCrtList = new ArrayList<VcatnCrtVO>();
			for (VcatnCrtVO vo : vcatnCrtList) {
				this.vcatnCrtList.add(vo);
			}
		}
	}

	public MberManageVO getMberManageVO() {
		return mberManageVO;
	}

	public void setMberManageVO(MberManageVO mberManageVO) {
		this.mberManageVO = mberManageVO;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

}
