package egovframework.com.menu.web;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;

/**
 * 경고창 이후 페이지 이동 뷰
 * @author ljh
 *
 */
@Component
public class AlertView extends AbstractView {

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String contextPath = request.getContextPath();
		String msg = (String) model.get("message");
		String returnUrl = (String) model.get("returnUrl");
		
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.print("<script>");
		if( msg != null ) {
			out.print("alert('" + msg +"');");
		}
		out.println("location.href='"+ contextPath + returnUrl + "'</script>");
	}
}