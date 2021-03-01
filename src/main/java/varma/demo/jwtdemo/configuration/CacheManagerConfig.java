package varma.demo.jwtdemo.configuration;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import net.sf.ehcache.config.CacheConfiguration;

@Configuration
@EnableCaching
public class CacheManagerConfig extends CachingConfigurerSupport {

	@Bean
	public net.sf.ehcache.CacheManager ehCacheManger() {
		// Enabling MasterCache for Master Tables in Product Starts
		CacheConfiguration masterCache = new CacheConfiguration();
		masterCache.setName("masterCache");
		masterCache.setMemoryStoreEvictionPolicy("LRU");
		masterCache.setTimeToLiveSeconds(24000);
		masterCache.setMaxEntriesLocalHeap(1000);
		masterCache.setLogging(true);
		// Enabling MasterCache for Master Tables in Product Ends

		// Enabling ObjectCache for Master Tables in Product Starts
		CacheConfiguration objectCache = new CacheConfiguration();
		objectCache.setName("objectCache");
		objectCache.setMemoryStoreEvictionPolicy("LRU");
		objectCache.setTimeToLiveSeconds(24000);
		objectCache.setMaxEntriesLocalHeap(1000);
		objectCache.setLogging(true);
		// Enabling ObjectCache for Master Tables in Product Ends

		net.sf.ehcache.config.Configuration config = new net.sf.ehcache.config.Configuration();
		config.addCache(masterCache);
		config.addCache(objectCache);

		System.out.println(config.getCacheConfigurations());
		return net.sf.ehcache.CacheManager.newInstance(config);
	}

//	@Bean
//	public EhCacheManagerFactoryBean ehCacheCacheManager() {
//		EhCacheManagerFactoryBean factory = new EhCacheManagerFactoryBean();
//		factory.setShared(true);
//		return factory;
//	}

	@Bean
	@Override
	public CacheManager cacheManager() {
		return new EhCacheCacheManager(ehCacheManger());
	}
}
