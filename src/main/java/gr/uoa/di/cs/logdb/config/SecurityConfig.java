package gr.uoa.di.cs.logdb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // First configure CORS
                .cors().and()
                // Then disable CSRF
                .csrf().disable()
                // Configure authorization requests
                .authorizeRequests()
                .antMatchers("/api/users/register","/swagger-ui/**","/v3/api-docs/**","/api/**").permitAll() // Allow these endpoints without authentication
                .anyRequest().authenticated() // All other requests need authentication
                .and()
                // Finally configure HTTP Basic authentication
                .httpBasic();
    }
}
