package egovframework.com.menu.program.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.type.Alias;

/**
 * 메뉴 정보 Vo
 *
 * @author Jun
 */
@SuppressWarnings("serial")
@Alias("MenuInfoVO")
public class MenuInfoVO extends MenuInfo {

	/**
	 * 메뉴 프로그램 리스트
	 */
	private List<MenuProgramInfoVO> menuProgramList;

	/**
	 * 메뉴 정보 리스트
	 */
	private List<MenuInfoVO> menuInfoList;

	/**
	 * 메뉴 프로그램 삭제리스트
	 */
	private List<MenuProgramInfoVO> deleteProgramList;

	/**
	 * 메뉴 기능 리스트
	 */
	private List<MenuFunctionInfoVO> menuFunctionList;

	/**
	 * 메뉴 기능 삭제 리스트
	 */
	private List<MenuFunctionInfoVO> deleteFunctionList;

	/**
	 * 메뉴번호 treeId
	 */
	private String id;

	/**
	 * 상위메뉴번호 treePid
	 */
	private String pId;

	public List<MenuProgramInfoVO> getMenuProgramList() {
		if (this.menuProgramList == null) {
			return null;
		} else {
			List<MenuProgramInfoVO> list = this.menuProgramList;
			return list;
		}
	}

	public void setMenuProgramList(List<MenuProgramInfoVO> menuProgramList) {
		if (menuProgramList != null) {
			this.menuProgramList = new ArrayList<MenuProgramInfoVO>();
			for (MenuProgramInfoVO vo : menuProgramList) {
				this.menuProgramList.add(vo);
			}
		}
	}

	public List<MenuInfoVO> getMenuInfoList() {
		if (this.menuInfoList == null) {
			return null;
		} else {
			List<MenuInfoVO> list = this.menuInfoList;
			return list;
		}
	}

	public void setMenuInfoList(List<MenuInfoVO> menuInfoList) {
		if (menuInfoList != null) {
			this.menuInfoList = new ArrayList<MenuInfoVO>();
			for (MenuInfoVO vo : menuInfoList) {
				this.menuInfoList.add(vo);
			}
		}
	}

	public List<MenuProgramInfoVO> getDeleteProgramList() {
		if (this.deleteProgramList == null) {
			return null;
		} else {
			List<MenuProgramInfoVO> list = this.deleteProgramList;
			return list;
		}
	}

	public void setDeleteProgramList(List<MenuProgramInfoVO> deleteProgramList) {
		if (deleteProgramList != null) {
			this.deleteProgramList = new ArrayList<MenuProgramInfoVO>();
			for (MenuProgramInfoVO vo : deleteProgramList) {
				this.deleteProgramList.add(vo);
			}
		}
	}

	public List<MenuFunctionInfoVO> getMenuFunctionList() {
		if (this.menuFunctionList == null) {
			return null;
		} else {
			List<MenuFunctionInfoVO> list = this.menuFunctionList;
			return list;
		}
	}

	public void setMenuFunctionList(List<MenuFunctionInfoVO> menuFunctionList) {
		if (menuFunctionList != null) {
			this.menuFunctionList = new ArrayList<MenuFunctionInfoVO>();
			for (MenuFunctionInfoVO vo : menuFunctionList) {
				this.menuFunctionList.add(vo);
			}
		}
	}

	public List<MenuFunctionInfoVO> getDeleteFunctionList() {
		if (this.deleteFunctionList == null) {
			return null;
		} else {
			List<MenuFunctionInfoVO> list = this.deleteFunctionList;
			return list;
		}
	}

	public void setDeleteFunctionList(List<MenuFunctionInfoVO> deleteFunctionList) {
		if (deleteFunctionList != null) {
			this.deleteFunctionList = new ArrayList<MenuFunctionInfoVO>();
			for (MenuFunctionInfoVO vo : deleteFunctionList) {
				this.deleteFunctionList.add(vo);
			}
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}
}
