package varma.demo.jwtdemo.utility;

import static java.util.Arrays.stream;
import static varma.demo.jwtdemo.constant.SecurityConstant.AUTHORITIES;
import static varma.demo.jwtdemo.constant.SecurityConstant.EXPIRATION_TIME;
import static varma.demo.jwtdemo.constant.SecurityConstant.TOKEN_CANNOT_BE_VERIFIED;
import static varma.demo.jwtdemo.constant.SecurityConstant.VARMA_INC;
import static varma.demo.jwtdemo.constant.SecurityConstant.VARMA_INC_ADMINISTRATION;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;

import varma.demo.jwtdemo.domain.UserPrincipal;

@Component
public class JWTTokenProvider {
	
//	@Value("${jwt.secret}")
	private String secret = "very_strong_random_string_to_be_used";

	// Method to generate token which takes user as input
	public String generateJwtToken(UserPrincipal userPrincipal) {
		// getallclaims
		String[] claims = getClaimsFromUser(userPrincipal);
		// claims fine,so authentication success,why not generate jwt now
		return JWT.create().withIssuer(VARMA_INC).withAudience(VARMA_INC_ADMINISTRATION).withIssuedAt(new Date())
				.withSubject(userPrincipal.getUsername()).withArrayClaim(AUTHORITIES, claims)
				.withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.sign(Algorithm.HMAC256(secret.getBytes()));
	}

	public List<GrantedAuthority> getAuthorities(String token) {
		String[] claims = getClaimsFromToken(token);
		return stream(claims).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
	}

	// get authentication of user, the reason for this method is because i can
	// verify token is correct and i can tell spring security to get me the
	// authentication of user and set that authentication in spring security context
	public Authentication getAuthentication(String userName, List<GrantedAuthority> authorities,
			HttpServletRequest request) {
		UsernamePasswordAuthenticationToken userPassAuthToken = new UsernamePasswordAuthenticationToken(userName, null,
				authorities);
		userPassAuthToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		return userPassAuthToken;
	}

	public boolean isTokenValid(String username, String token) {
		JWTVerifier verifier = getJWTVerifier();
		return StringUtils.isNotBlank(username) && !isTokenExpired(verifier, token);
	}

	public String getSubject(String token) {
		JWTVerifier verifier = getJWTVerifier();
		return verifier.verify(token).getSubject();
	}

	public boolean isTokenExpired(JWTVerifier verifier, String token) {
		Date expiration = verifier.verify(token).getExpiresAt();
		return expiration.before(new Date());
	}

	private String[] getClaimsFromToken(String token) {
		JWTVerifier verifier = getJWTVerifier();
		return verifier.verify(token).getClaim(AUTHORITIES).asArray(String.class);
	}

	private JWTVerifier getJWTVerifier() {
		JWTVerifier verifier;
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);
			verifier = JWT.require(algorithm).withIssuer(VARMA_INC).build();
		} catch (JWTVerificationException e) {
			throw new JWTVerificationException(TOKEN_CANNOT_BE_VERIFIED);
		}
		return verifier;
	}

	private String[] getClaimsFromUser(UserPrincipal userPrincipal) {
		List<String> authorities = new ArrayList<>();
		for (GrantedAuthority grantedAuthority : userPrincipal.getAuthorities()) {
			authorities.add(grantedAuthority.getAuthority());
		}
		return authorities.toArray(new String[0]);
	}

}
