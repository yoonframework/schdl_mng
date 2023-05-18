package egovframework.com.menu.web;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import egovframework.com.menu.service.MenuService;
import egovframework.com.menu.service.MenuTreeVO;

@Controller
public class MenuController {

	@Resource(name = "MenuService")
	private MenuService menuService;

	/**
	 * 메뉴 트리 생성을 위한 목록 데이터
	 * @param menuTreeVO
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/menu/menuTreeList.do", produces = "application/json; charset=utf8")
	@ResponseBody
	public String menuTreeList(@ModelAttribute MenuTreeVO menuTreeVO) throws Exception {
		Gson gson = new Gson();
		return gson.toJson(menuService.selectMenuTreeList());
	}
}
