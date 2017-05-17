package com.demo.security.oauth.geode;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class GeodeAccessTokenRegionHelper {
	private static final Logger LOGGER = LoggerFactory.getLogger(GeodeAccessTokenRegionHelper.class);
	Map<String, DemoAccessTokenEntity> dataStore = new HashMap<String, DemoAccessTokenEntity>();
	
	/**
	 * Stores DemoAccessTokenEntity
	 * 
	 * @param accessTokenEntity
	 */
	public void save(DemoAccessTokenEntity accessTokenEntity)  {

		dataStore.put(accessTokenEntity.getKey(), accessTokenEntity);
	}
	public DemoAccessTokenEntity getByKey(String key) throws Exception {

		return dataStore.get(key);
	}
	/**
	 * Returns DemoAccessTokenEntity by token value
	 * 
	 * @param accessToken
	 * @return
	 * @throws Exception
	 */
	public DemoAccessTokenEntity getByAccessToken(String accessToken) throws Exception {

		
		return dataStore.get(accessToken);
	}
	/**
	 * Remove AccessToken Entity by accessToken value
	 *  
	 * @param accessToken
	 * @throws Exception
	 */
	public void removeByAccessToken(String accessToken) throws Exception {
		dataStore.remove(accessToken);
	}
	public void removeByUserId(String userId) throws Exception {
		dataStore.remove(userId);
	}
}
