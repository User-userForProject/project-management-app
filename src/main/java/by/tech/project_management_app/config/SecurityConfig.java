package by.tech.project_management_app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private UserDetailsService userDetailsService;

    @Autowired
    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        .httpBasic()
        .and()
        .authorizeRequests()
        .antMatchers(HttpMethod.GET, "/api/v1/projects/**").hasAnyAuthority("USER", "ADMIN")
        .antMatchers(HttpMethod.POST, "/api/v1/projects/**").hasAuthority("ADMIN")
        .antMatchers(HttpMethod.PUT, "/api/v1/projects/**").hasAuthority("ADMIN")
        .antMatchers(HttpMethod.PATCH, "/api/v1/projects/**").hasAuthority("ADMIN")
        .antMatchers(HttpMethod.DELETE, "/api/v1/projects/**").hasAuthority("ADMIN")
        .antMatchers(HttpMethod.GET, "/api/v1/tasks/**").hasAnyAuthority("USER", "ADMIN")
        .antMatchers(HttpMethod.POST, "/api/v1/tasks/**").hasAuthority("ADMIN")
        .antMatchers(HttpMethod.PUT, "/api/v1/tasks/**").hasAuthority("ADMIN")
        .antMatchers(HttpMethod.PATCH, "/api/v1/tasks/**").hasAuthority("ADMIN")
        .antMatchers(HttpMethod.DELETE, "/api/v1/tasks/**").hasAuthority("ADMIN")
        .antMatchers("/api/v1/users/**").hasAuthority("ADMIN")
        .and()
        .csrf().disable()
        .formLogin().disable();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
}