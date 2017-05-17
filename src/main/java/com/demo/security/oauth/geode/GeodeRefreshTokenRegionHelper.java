package com.demo.security.oauth.geode;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class GeodeRefreshTokenRegionHelper {
	private static final Logger LOGGER = LoggerFactory.getLogger(GeodeRefreshTokenRegionHelper.class);
	Map<String, DemoRefreshTokenEntity> dataStore = new HashMap<String, DemoRefreshTokenEntity>();
	
	public void save(DemoRefreshTokenEntity accessTokenEntity) throws Exception {

		dataStore.put(accessTokenEntity.getRefreshToken(), accessTokenEntity);
	}

	public DemoRefreshTokenEntity get(String refreshToken) {
		return dataStore.get(refreshToken);
	}

	public void delete(String refreshToken) {
		dataStore.remove(refreshToken);
	}
	
	public void removeByUserId(String userId) throws Exception {
		dataStore.remove(userId);
		
	}
}
