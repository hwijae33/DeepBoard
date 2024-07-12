package com.newboard.newboard.config;

import com.newboard.newboard.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private CustomAuthenticationEntryPoint authenticationEntryPoint;
    @Autowired
    private CustomAccessDeniedHandler accessDeniedHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)  // CSRF 보호 비활성화
                .httpBasic(AbstractHttpConfigurer::disable)  // 기본 HTTP 인증 비활성화

                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/", "/register", "/user/register", "/user/loginForm", "/post/postDetail/{id}").permitAll()  // 특정 경로들은 모든 사용자에게 허용
                        .anyRequest().authenticated())  // 그 외 모든 요청은 인증된 사용자만 접근 가능

                .formLogin(formLogin -> formLogin
                        .loginPage("/user/loginForm")  // 사용자 정의 로그인 페이지
                        .loginProcessingUrl("/user/login")  // 로그인 처리 URL
                        .failureUrl("/error/403")  // 로그인 실패 시 이동할 경로 설정
                        .defaultSuccessUrl("/")  // 로그인 성공 시 이동할 기본 경로
                        .usernameParameter("email")  // 로그인 폼에서 사용할 사용자명 파라미터 이름
                        .passwordParameter("password"))  // 로그인 폼에서 사용할 비밀번호 파라미터 이름

                .userDetailsService(userService)  // 사용자 정의 UserDetailsService 설정
                .logout((logout) -> logout
                        .logoutSuccessUrl("/")  // 로그아웃 성공 시 이동할 경로 설정
                        .invalidateHttpSession(true))  // HTTP 세션 무효화
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)  // 인증 진입점 설정
                .accessDeniedHandler(accessDeniedHandler);  // 접근 거부 처리자 설정

//SessionCreationPolicy.STATELESS : 이 정책은 Spring Security가 HTTP 세션을 생성하지 않고 세션 관련 데이터를 저장하지 않음을 의미합니다.
//                .sessionManagement(session -> session
//                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))


        return http.build();
    }





//WebSecurityCustomizer를 사용하여 특정 요청 경로들을 Security 필터 체인 적용 대상에서 제외시킵니다.
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/view/login", "/error", "/error/*", "/img/**", "/favicon.ico");
    }

    //AuthenticationManager 빈을 설정합니다. Spring Security에서 인증 관리를 담당하는 인터페이스입니다.
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    //비밀번호를 안전하게 저장하기 위해 사용할 PasswordEncoder를 설정합니다. 여기서는 BCrypt 알고리즘을 사용하여 비밀번호를 해시화합니다.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
