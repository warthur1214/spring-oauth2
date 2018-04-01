package com.warthur.oauth2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * Created by warth on 2018/3/30.
 */
@Configuration
@EnableAuthorizationServer
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter {

	private static final String DEMO_RESOURCE_ID = "api";
	private static final int TOKEN_EXPIRE_SECONDS = 30;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private RedisConnectionFactory redisConnectionFactory;

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		//配置两个客户端,一个用于password认证一个用于client认证
		clients.inMemory().withClient("client_1")
				.resourceIds(DEMO_RESOURCE_ID)
				.authorizedGrantTypes("client_credentials", "refresh_token", "password", "authorization_code")
				.scopes("userinfo")
				.authorities("client")
				.secret("123456")
				.accessTokenValiditySeconds(TOKEN_EXPIRE_SECONDS)
				.refreshTokenValiditySeconds(TOKEN_EXPIRE_SECONDS);
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) {

		endpoints.tokenStore(new RedisTokenStore(redisConnectionFactory))
				.authenticationManager(authenticationManager);
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
		//允许表单认证
		oauthServer.allowFormAuthenticationForClients();
	}

}
