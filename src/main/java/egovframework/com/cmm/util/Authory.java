package egovframework.com.cmm.util;

public class Authory {

	private Authory() {
		throw new IllegalStateException("Authory class");
	}

	/** 익명 */
	public static final String ROLE_ANONYMOUS = "ROLE_ANONYMOUS";
	/** 관리자 */
	public static final String ROLE_ADMIN = "ROLE_ADMIN";
	/** 일반사용자 */
	public static final String ROLE_USER = "ROLE_USER";
}
