package varma.demo.jwtdemo.service;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

@Service
public class LoginAttemptService {
//	public static final int MAXIMUM_NUMBER_OF_ATTEMPTS = 5;
//	public static final int ATTEMPT_INCREMENT = 1;
//	public Cache loginAttemptCache;
//
//	@Autowired
//	public LoginAttemptService() {
//		System.out.println(Arrays.toString(CacheManager.getInstance().getCacheNames()));
//		this.loginAttemptCache = CacheManager.getInstance().getCache("default");
//	}
//
//	public void removeUserFromLoginAttemptCache(String username) {
//		this.loginAttemptCache = CacheManager.getInstance().getCache("objectCache");
//		loginAttemptCache.remove(username);
//	}
//
//	public void addUserToLoginAttemptCache(String username) throws ExecutionException {
//		int attempts = 0;
//		try {
//			this.loginAttemptCache = CacheManager.getInstance().getCache("objectCache");
//			attempts = ATTEMPT_INCREMENT + Integer.valueOf((String) loginAttemptCache.get(username).getObjectValue());
//
//		} catch (Exception e) {
//		}
//		loginAttemptCache.put(new Element(username, attempts));
//	}
//
//	public boolean hasExceededMaxAttempts(String username) {
//		try {
//			System.out.println(Arrays.toString(CacheManager.getInstance().getCacheNames()));
//
//			this.loginAttemptCache = CacheManager.getInstance().getCache("objectCache");
//			return Integer
//					.valueOf((String) loginAttemptCache.get(username).getObjectValue()) >= MAXIMUM_NUMBER_OF_ATTEMPTS;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return false;
//	}

	private static final int MAXIMUM_NUMBER_OF_ATTEMPTS = 5;
	private static final int ATTEMPT_INCREMENT = 1;
	private LoadingCache<String, Integer> loginAttemptCache;

	public LoginAttemptService() {
		super();
		loginAttemptCache = CacheBuilder.newBuilder().expireAfterWrite(15, TimeUnit.MINUTES).maximumSize(100)
				.build(new CacheLoader<String, Integer>() {
					public Integer load(String key) {
						return 0;
					}
				});
	}

	public void evictUserFromLoginAttemptCache(String username) {
		loginAttemptCache.invalidate(username.trim());
	}

	public void addUserToLoginAttemptCache(String username) {
		int attempts = 0;
		try {
			attempts = ATTEMPT_INCREMENT + loginAttemptCache.get(username.trim());
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		loginAttemptCache.put(username.trim(), attempts);
	}

	public boolean hasExceededMaxAttempts(String username) {
		try {
			return loginAttemptCache.get(username.trim()) >= MAXIMUM_NUMBER_OF_ATTEMPTS;
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return false;
	}

}
