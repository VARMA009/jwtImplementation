package varma.demo.jwtdemo.constant;

public class SecurityConstant {
	public static final long EXPIRATION_TIME = 36000000;
	public static final String TOKEN_PREFIX = "VarmaBearer ";
	public static final String JWT_TOKEN_HEADER = "Varma-Token";
	public static final String TOKEN_CANNOT_BE_VERIFIED = "Token cannot be verified";
	public static final String VARMA_INC = "Varma Group";
	public static final String VARMA_INC_ADMINISTRATION = "User Management";
	public static final String AUTHORITIES = "authorities";
	public static final String FORBIDDEN_MESSAGE = "You need to login to acces this page";
	public static final String ACCESS_DENIED_MESSAGE = "You don't have permission to access this page ";
	public static final String OPTIONS_HTTP_METHOD = "OPTIONS";
	// Urls that can be accessed without any security
	public static final String[] PUBLIC_URLS = { "/user/login", "/user/register", "/user/resetpassword/**","/user/image/**" };
	// public static final String[] PUBLIC_URLS = { "**" };
}
