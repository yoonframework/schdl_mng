package egovframework.com.menu.program.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.type.Alias;

/**
 * 메뉴 프로그램 정보 객체
 *
 * @author Jun
 */
@Alias("MenuProgramInfoVO")
public class MenuProgramInfoVO extends MenuProgramInfo {
	//이전 프로그램URL
	private List<MenuProgramInfoVO> menuProgramInfoList;
	//트리 프로그램URL ID
	private String proId;

	public List<MenuProgramInfoVO> getMenuProgramInfoList() {
		if (this.menuProgramInfoList == null) {
			return null;
		} else {
			List<MenuProgramInfoVO> list = this.menuProgramInfoList;
			return list;
		}
	}

	public void setMenuProgramInfoList(List<MenuProgramInfoVO> menuProgramInfoList) {
		if (menuProgramInfoList != null) {
			this.menuProgramInfoList = new ArrayList<MenuProgramInfoVO>();
			for (MenuProgramInfoVO vo : menuProgramInfoList) {
				this.menuProgramInfoList.add(vo);
			}
		}
	}

	public String getProId() {
		return proId;
	}

	public void setProId(String proId) {
		this.proId = proId;
	}
}
