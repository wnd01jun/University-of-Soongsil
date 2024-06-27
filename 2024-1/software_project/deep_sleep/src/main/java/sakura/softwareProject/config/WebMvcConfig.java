package sakura.softwareProject.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import sakura.softwareProject.repository.MemberRepository;
import sakura.softwareProject.security.Interceptor;
import sakura.softwareProject.security.JwtProvider;
import sakura.softwareProject.service.MemberService;

@Configuration
@AllArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {
    private final JwtProvider jwtProvider;
    private final MemberRepository memberRepository;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor())
                .excludePathPatterns("/css/**", "/js/**", "/img/**", "/font/**")
                .excludePathPatterns("/member/sign-up", "/member/sign-in")
                .excludePathPatterns("/swagger-ui/**", "/swagger-ui.html", "/v2/api-docs", "/webjars/**", "/swagger-resources/**")
                .excludePathPatterns("/api*", "/api-docs/**")
                .excludePathPatterns("/swagger", "/swagger-ui.html", "/swagger-ui/**", "/api-docs", "/api-docs/**", "/v3/api-docs/**");
    }

    @Bean // 테스트를 위해 중지
    public Interceptor interceptor() {
        return new Interceptor(jwtProvider, memberRepository);
    }
}
