package com.skoczo.iot_manager.security.token.web.config;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.skoczo.iot_manager.security.token.iot.IOTTokenAuthenticationProvider;
import com.skoczo.iot_manager.security.token.web.NoRedirectStrategy;
import com.skoczo.iot_manager.security.token.web.TokenAuthenticationFilter;
import com.skoczo.iot_manager.security.token.web.TokenAuthenticationProvider;


@EnableWebSecurity
class SecurityConfig {
	private static final  RequestMatcher iotUrl = new AntPathRequestMatcher("/iot/**");

  private static final RequestMatcher PUBLIC_URLS = new OrRequestMatcher(
    new AntPathRequestMatcher("/public/users/**"));
  private static final RequestMatcher PROTECTED_URLS = new AndRequestMatcher(new NegatedRequestMatcher(PUBLIC_URLS), new NegatedRequestMatcher(iotUrl));
  
  @Configuration
  @Order(3)
  class BasicAuth extends WebSecurityConfigurerAdapter {
	  @Autowired private TokenAuthenticationProvider provider;
	
	  @Override
	  protected void configure(final AuthenticationManagerBuilder auth) {
	    auth.authenticationProvider(provider);
	  }
	
	  @Override
	  public void configure(final WebSecurity web) {
	    web.ignoring().requestMatchers(PUBLIC_URLS);
	  }
	
	  @Override
	  protected void configure(final HttpSecurity http) throws Exception {
		  http 
		  .sessionManagement()
		      .sessionCreationPolicy(STATELESS)
		      .and()
		      .exceptionHandling()
		      // this entry point handles when you request a protected page and you are not yet
		      // authenticated
		      .defaultAuthenticationEntryPointFor(forbiddenEntryPoint(), PROTECTED_URLS)
			      .and()
				      .authenticationProvider(provider)
				      .addFilterBefore(restAuthenticationFilter(), AnonymousAuthenticationFilter.class)
				      .authorizeRequests()
				      .requestMatchers(PROTECTED_URLS)
				      .authenticated()
				    .and()
				      .csrf().disable()
				      .cors().disable()
				      .formLogin().disable()
				      .httpBasic().disable()
				      .logout().disable();
	  }
	
	  @Bean
	  TokenAuthenticationFilter restAuthenticationFilter() throws Exception {
	    final TokenAuthenticationFilter filter = new TokenAuthenticationFilter(PROTECTED_URLS);
	    filter.setAuthenticationManager(authenticationManager());
	    filter.setAuthenticationSuccessHandler(successHandler());
	    return filter;
	  }
	

  }
  
  @Configuration
  @Order(2)
  class IOTSecurityConfig extends WebSecurityConfigurerAdapter  {   
    @Autowired private IOTTokenAuthenticationProvider ioTprovider;

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) {
      auth.authenticationProvider(ioTprovider);
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
		  http 
		  .sessionManagement()
		      .sessionCreationPolicy(STATELESS)
		      .and()
		      .exceptionHandling()
		      // this entry point handles when you request a protected page and you are not yet
		      // authenticated
		      .defaultAuthenticationEntryPointFor(forbiddenEntryPoint(), iotUrl)
			      .and()
				      .authenticationProvider(ioTprovider)
				      .addFilterBefore(iotRestAuthenticationFilter(), AnonymousAuthenticationFilter.class)
				      .authorizeRequests()
				      .requestMatchers(iotUrl)
				      .authenticated()
				    .and()
				      .csrf().disable()
				      .cors().disable()
				      .formLogin().disable()
				      .httpBasic().disable()
				      .logout().disable();
    }
    
	  @Bean
	  TokenAuthenticationFilter iotRestAuthenticationFilter() throws Exception {
	    final TokenAuthenticationFilter filter = new TokenAuthenticationFilter(iotUrl);
	    filter.setAuthenticationManager(authenticationManager());
	    filter.setAuthenticationSuccessHandler(successHandler());
	    return filter;
	  }	 
  }
  
  @Bean
  SimpleUrlAuthenticationSuccessHandler successHandler() {
    final SimpleUrlAuthenticationSuccessHandler successHandler = new SimpleUrlAuthenticationSuccessHandler();
    successHandler.setRedirectStrategy(new NoRedirectStrategy());
    return successHandler;
  }
  
  @Bean
  AuthenticationEntryPoint forbiddenEntryPoint() {
    return new HttpStatusEntryPoint(FORBIDDEN);
  }
  
  @Bean
  public PasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
  }

//  /**
//   * Disable Spring boot automatic filter registration.
//   */
//  @Bean
//  FilterRegistrationBean disableAutoRegistration(final TokenAuthenticationFilter filter) {
//    final FilterRegistrationBean registration = new FilterRegistrationBean(filter);
//    registration.setEnabled(false);
//    return registration;
//  }
}