package egovframework.com.menu.autho.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.type.Alias;

/**
 * 메뉴 권한 정보 객체
 *
 * @author Jun
 */
@SuppressWarnings("serial")
@Alias("MenuAuthoInfo")
public class AuthoInfoVO extends AuthoInfo {
	/**
	 * 메뉴권한 정보 리스트
	 */
	private List<AuthoInfoVO> authoInfoList;

	public List<AuthoInfoVO> getAuthoInfoList() {
		if (this.authoInfoList == null) {
			return null;
		} else {
			List<AuthoInfoVO> list = this.authoInfoList;
			return list;
		}
	}

	public void setAuthoInfoList(List<AuthoInfoVO> authoInfoList) {
		if (authoInfoList != null) {
			this.authoInfoList = new ArrayList<>();
			for (AuthoInfoVO vo : authoInfoList) {
				this.authoInfoList.add(vo);
			}
		}
	}
}
