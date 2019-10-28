Application must be started on port 8080 with context path /demoOAuth
OAuthClientCredentialTestIT contain 1 integration test, which requires the local instance of app running.
Just running this integration cause stackoverflow error.

To Reproduce the issue
1. UnComment ".passwordEncoder(new BCryptPasswordEncoder())" line 51 @ DemoAuthorizationConfigurer.configure(AuthorizationServerSecurityConfigurer oauthServer)
2. UnComment "e.setClientSecret("$2a$10$ja0qvmrEQn/Bmz3RXTvOyu3qfB12hcXyEkRxnGWHUAys45z5ldTfu"); //password" line 27 @ GeodeClientDetailsHelper.getByClientId()
2. Comment "e.setClientSecret("password");" line 28 @ GeodeClientDetailsHelper.getByClientId()
