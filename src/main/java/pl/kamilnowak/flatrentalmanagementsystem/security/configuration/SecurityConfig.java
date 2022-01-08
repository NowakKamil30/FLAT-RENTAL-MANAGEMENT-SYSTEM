package pl.kamilnowak.flatrentalmanagementsystem.security.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pl.kamilnowak.flatrentalmanagementsystem.security.filter.JWTFilter;
import pl.kamilnowak.flatrentalmanagementsystem.security.service.LoginUserService;
import pl.kamilnowak.flatrentalmanagementsystem.security.type.TypeAccount;
import pl.kamilnowak.flatrentalmanagementsystem.util.info.ConfigInfo;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final LoginUserService loginUserService;
    private final ConfigInfo configInfo;

    @Autowired
    public SecurityConfig(LoginUserService loginUserService, ConfigInfo configInfo) {
        this.loginUserService = loginUserService;
        this.configInfo = configInfo;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(loginUserService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/v1/authorization/**").permitAll()
                .and()
                .addFilterBefore(new JWTFilter(configInfo.getSecretKey()), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/v1/loginUser/**").hasAnyRole(
                        TypeAccount.USER.toString(),
                        TypeAccount.ADMIN.toString())
                .antMatchers(HttpMethod.PUT, "/v1/loginUser/**").hasAnyRole(
                        TypeAccount.ADMIN.toString())
                .antMatchers(HttpMethod.DELETE, "/v1/loginUser/**").hasAnyRole(
                        TypeAccount.ADMIN.toString())
                .antMatchers(HttpMethod.GET, "/v1/role/**").hasAnyRole(
                        TypeAccount.USER.toString(),
                        TypeAccount.ADMIN.toString())
                .antMatchers(HttpMethod.GET, "/v1/userData/**").hasAnyRole(
                        TypeAccount.USER.toString(),
                        TypeAccount.ADMIN.toString())
                .antMatchers(HttpMethod.PUT, "/v1/userData/**").hasAnyRole(
                        TypeAccount.USER.toString(),
                        TypeAccount.ADMIN.toString())
                .antMatchers(HttpMethod.DELETE, "/v1/userData/**").hasAnyRole(
                        TypeAccount.ADMIN.toString())
                .antMatchers(HttpMethod.POST, "/v1/userData/**").hasAnyRole(
                        TypeAccount.ADMIN.toString())
                .antMatchers(HttpMethod.GET, "/v1/apartment/**").hasAnyRole(
                        TypeAccount.USER.toString(),
                        TypeAccount.ADMIN.toString())
                .antMatchers(HttpMethod.POST, "/v1/apartment/**").hasAnyRole(
                        TypeAccount.USER.toString(),
                        TypeAccount.ADMIN.toString())
                .antMatchers(HttpMethod.DELETE, "/v1/apartment/**").hasAnyRole(
                        TypeAccount.USER.toString(),
                        TypeAccount.ADMIN.toString())
                .antMatchers(HttpMethod.PUT, "/v1/apartment/**").hasAnyRole(
                        TypeAccount.USER.toString(),
                        TypeAccount.ADMIN.toString())
                .antMatchers(HttpMethod.GET, "/v1/currency/**").hasAnyRole(
                        TypeAccount.USER.toString(),
                        TypeAccount.ADMIN.toString())
                .antMatchers(HttpMethod.POST, "/v1/currency/**").hasAnyRole(
                        TypeAccount.ADMIN.toString())
                .antMatchers(HttpMethod.DELETE, "/v1/currency/**").hasAnyRole(
                        TypeAccount.ADMIN.toString())
                .antMatchers(HttpMethod.PUT, "/v1/currency/**").hasAnyRole(
                        TypeAccount.ADMIN.toString())
                .antMatchers(HttpMethod.GET, "/v1/image/**").hasAnyRole(
                        TypeAccount.USER.toString(),
                        TypeAccount.ADMIN.toString())
                .antMatchers(HttpMethod.POST, "/v1/image/**").hasAnyRole(
                        TypeAccount.USER.toString(),
                        TypeAccount.ADMIN.toString())
                .antMatchers(HttpMethod.DELETE, "/v1/image/**").hasAnyRole(
                        TypeAccount.USER.toString(),
                        TypeAccount.ADMIN.toString())
                .antMatchers(HttpMethod.PUT, "/v1/image/**").hasAnyRole(
                        TypeAccount.USER.toString(),
                        TypeAccount.ADMIN.toString())
                .antMatchers(HttpMethod.GET, "/v1/document/**").hasAnyRole(
                        TypeAccount.USER.toString(),
                        TypeAccount.ADMIN.toString())
                .antMatchers(HttpMethod.PUT, "/v1/document/**").hasAnyRole(
                        TypeAccount.USER.toString(),
                        TypeAccount.ADMIN.toString())
                .antMatchers(HttpMethod.POST, "/v1/document/**").hasAnyRole(
                        TypeAccount.USER.toString(),
                        TypeAccount.ADMIN.toString())
                .antMatchers(HttpMethod.DELETE, "/v1/document/**").hasAnyRole(
                        TypeAccount.USER.toString(),
                        TypeAccount.ADMIN.toString())
                .antMatchers(HttpMethod.GET, "/v1/tenant/**").hasAnyRole(
                        TypeAccount.USER.toString(),
                        TypeAccount.ADMIN.toString())
                .antMatchers(HttpMethod.POST, "/v1/tenant/**").hasAnyRole(
                        TypeAccount.USER.toString(),
                        TypeAccount.ADMIN.toString())
                .antMatchers(HttpMethod.PUT, "/v1/tenant/**").hasAnyRole(
                        TypeAccount.USER.toString(),
                        TypeAccount.ADMIN.toString())
                .antMatchers(HttpMethod.DELETE, "/v1/tenant/**").hasAnyRole(
                        TypeAccount.USER.toString(),
                        TypeAccount.ADMIN.toString())
                .antMatchers(HttpMethod.GET, "/v1/mail/**").hasAnyRole(
                        TypeAccount.USER.toString(),
                        TypeAccount.ADMIN.toString());
        http.csrf().disable();
    }
}
