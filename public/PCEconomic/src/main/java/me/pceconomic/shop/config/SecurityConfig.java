package me.pceconomic.shop.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import me.pceconomic.shop.config.filters.UsernamePasswordFilter;
import me.pceconomic.shop.services.auth.AuthenticationManagerService;
import me.pceconomic.shop.services.auth.AuthenticationProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private UsernamePasswordFilter usernamePasswordFilter;

    @Autowired
    private AuthenticationProviderService authenticationProviderService;
    @Autowired
    private AuthenticationManagerService authenticationManagerService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .addFilterAt(usernamePasswordFilter, UsernamePasswordAuthenticationFilter.class)
                .userDetailsService(userDetailsService)
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/areaclients/**").authenticated()
                        .anyRequest().permitAll()
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true)
                        .permitAll()
                )
                .logout((logout) -> logout
                        .logoutUrl("/logout")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .logoutSuccessUrl("/")
                        .permitAll()
                );
        return http.build();
    }

/*
    @Bean
    public FilterRegistrationBean<CustomIpFilter> customIpFilter() {
        FilterRegistrationBean<CustomIpFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new CustomIpFilter("127.0.0.1"));
        registrationBean.addUrlPatterns("/api/*");

        return registrationBean;
    }
*/

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return authenticationManagerService;
    }

    private static class CustomIpFilter extends OncePerRequestFilter {
        private final String ipAddress;

        private CustomIpFilter(String ipAddress) {
            this.ipAddress = ipAddress;
        }

        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
            String remoteIp = request.getRemoteAddr();
            if (!ipAddress.equals(remoteIp)) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            filterChain.doFilter(request, response);
        }
    }
}
