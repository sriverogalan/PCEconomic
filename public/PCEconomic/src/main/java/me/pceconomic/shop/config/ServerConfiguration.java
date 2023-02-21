package me.pceconomic.shop.config;

import me.pceconomic.shop.interceptors.CompraInterceptor;
import me.pceconomic.shop.interceptors.LoginInterceptor;
import me.pceconomic.shop.interceptors.TokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.support.NoOpCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class ServerConfiguration implements WebMvcConfigurer {

    private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {
            "classpath:/META-INF/resources/", "classpath:/resources/",
            "classpath:/static/", "classpath:/public/"};
    private final LoginInterceptor loginInterceptor;
    private final CompraInterceptor compraInterceptor;

    @Autowired
    public ServerConfiguration(LoginInterceptor loginInterceptor, CompraInterceptor compraInterceptor) {
        this.loginInterceptor = loginInterceptor;
        this.compraInterceptor = compraInterceptor;
    }

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/error").setViewName("error");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS")
                .allowedOrigins(
                        "http://localhost:80",
                        "http://localhost:5500",
                        "http://localhost:8080",
                        "http://pceconomic.live:8080/",
                        "http://www.pceconomic.me/",
                        "http://pceconomic.me/",
                        "http://admin.pceconomic.me/",
                        "http://api.pceconomic.me/",
                        "http://pceconomic.me:8080/",
                        "http://pceconomic.live:8000",
                        "http://pceconomic.live:3000"
                )
                .allowCredentials(true);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(compraInterceptor)
                .addPathPatterns("/compra", "/carrito/**")
                .excludePathPatterns("/carrito");
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/carrito/**", "/compra/**", "/areaclients/**", "/compra", "/pagament/**")
                .excludePathPatterns("/carrito", "/css/**", "/js/**", "/img/**", "/fonts/**", "/error");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CacheManager cacheManager() {
        return new NoOpCacheManager();
    }
}