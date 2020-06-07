package com.example.demo;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.example.demo.consts.UserRole;
import com.example.demo.domain.model.UserModel;
import com.example.demo.domain.repository.UserRepository;
import com.example.demo.domain.service.EtaUserDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.filter.GenericFilterBean;

/**
 * セキュリティ設定クラス
 * @see https://spring.io/guides/gs/securing-web/
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AppConfig appConfig;

    // ★1
    @Value("${security.secret-key:secret}")
    private String secretKey = "secret";

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http
            // AUTHORIZE
            .authorizeRequests()
                .mvcMatchers("/hello/**")
                    .permitAll()
                .mvcMatchers("/user/**")
                    .hasRole(UserRole.USER.toString())
                .mvcMatchers("/admin/**")
                    .hasRole(UserRole.ADMIN.toString())
                .anyRequest()
                    .authenticated()
            .and()
            // EXCEPTION
            .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint())
                .accessDeniedHandler(accessDeniedHandler())
            .and()
            // LOGIN
            .formLogin()
                .loginProcessingUrl("/login").permitAll()
                    .usernameParameter("email")
                    .passwordParameter("pass")
                .successHandler(authenticationSuccessHandler())
                .failureHandler(authenticationFailureHandler())
            .and()
            // ★2 LOGOUT
            .logout()
                .logoutUrl("/logout")
                .logoutSuccessHandler(logoutSuccessHandler())
            .and()
            // ★3 CSRF
            .csrf()
                .disable()
            // ★4 AUTHORIZE
            .addFilterBefore(tokenFilter(), UsernamePasswordAuthenticationFilter.class)
            // ★5 SESSION
            .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            ;
        // @formatter:on
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.eraseCredentials(true)
                .userDetailsService(simpleUserDetailsService())
                .passwordEncoder(passwordEncoder());
    }

    @Bean("simpleUserDetailsService")
    UserDetailsService simpleUserDetailsService() {
        return new EtaUserDetailsService(this.userRepository, this.appConfig);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    private GenericFilterBean tokenFilter() {
        return new SimpleTokenFilter(this.userRepository, secretKey, this.appConfig);
    }

    private AuthenticationEntryPoint authenticationEntryPoint() {
        return new SimpleAuthenticationEntryPoint();
    }

    private AccessDeniedHandler accessDeniedHandler() {
        return new SimpleAccessDeniedHandler();
    }

    private AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new SimpleAuthenticationSuccessHandler(secretKey);
    }

    private AuthenticationFailureHandler authenticationFailureHandler() {
        return new SimpleAuthenticationFailureHandler();
    }

    private LogoutSuccessHandler logoutSuccessHandler() {
        return new HttpStatusReturningLogoutSuccessHandler();
    }

    @Bean
    public UserRepository userRepository() {
        return new UserRepository(){
        
            @Override
            public <S extends UserModel> Optional<S> findOne(Example<S> example) {
                return null;                
            }
        
            @Override
            public <S extends UserModel> Page<S> findAll(Example<S> example, Pageable pageable) {
                return null;
            }
        
            @Override
            public <S extends UserModel> boolean exists(Example<S> example) {
                return false;
            }
        
            @Override
            public <S extends UserModel> long count(Example<S> example) {
                return 0;
            }
        
            @Override
            public <S extends UserModel> S save(S entity) {
                return null;
            }
        
            @Override
            public Optional<UserModel> findById(String id) {
                return null;
            }
        
            @Override
            public Iterable<UserModel> findAllById(Iterable<String> ids) {
                return null;
            }
        
            @Override
            public boolean existsById(String id) {
                return false;
            }
        
            @Override
            public void deleteById(String id) {
                
            }
        
            @Override
            public void deleteAll(Iterable<? extends UserModel> entities) {
                
            }
        
            @Override
            public void deleteAll() {
                
            }
        
            @Override
            public void delete(UserModel entity) {
                
            }
        
            @Override
            public long count() {
                return 0;
            }
        
            @Override
            public Page<UserModel> findAll(Pageable pageable) {
                return null;
            }
        
            @Override
            public <S extends UserModel> List<S> saveAll(Iterable<S> entities) {
                return null;
            }
        
            @Override
            public <S extends UserModel> List<S> insert(Iterable<S> entities) {
                return null;
            }
        
            @Override
            public <S extends UserModel> S insert(S entity) {
                return null;
            }
        
            @Override
            public <S extends UserModel> List<S> findAll(Example<S> example, Sort sort) {
                return null;
            }
        
            @Override
            public <S extends UserModel> List<S> findAll(Example<S> example) {
                return null;
            }
        
            @Override
            public List<UserModel> findAll(Sort sort) {
                return null;
            }
        
            @Override
            public List<UserModel> findAll() {
                return null;
            }
        
            @Override
            public Optional<UserModel> findOneByLoginId(String loginId) {
                final UserModel user = new UserModel();
                user.setIsEnable(true);
                user.setLastUpdateDate(new Date());
                user.setLoginId("test");
                user.setLoginRetryCount(99);
                user.setName("Judy");
                user.setPassword("password");
                user.setRole(UserRole.USER.toString());

                return Optional.of(user);
            }
        };
    }
}
