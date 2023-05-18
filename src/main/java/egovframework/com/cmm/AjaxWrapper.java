package egovframework.com.cmm;

public class AjaxWrapper {

	public static final String SUCCESS = "success";
	public static final String DUPLICATION = "duplication";
	public static final String FAIL = "fail";
	public static final String SESSION_EXPRY = "sessionExpry";

	private String sttus;
	private Object result;

	public AjaxWrapper() {}

	public AjaxWrapper(String sttus) {
		this.sttus = sttus;
	}

	public AjaxWrapper(String sttus, Object result) {
		this.sttus = sttus;
		this.result = result;
	}

	public String getSttus() {
		return sttus;
	}

	public void setSttus(String sttus) {
		this.sttus = sttus;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}
}