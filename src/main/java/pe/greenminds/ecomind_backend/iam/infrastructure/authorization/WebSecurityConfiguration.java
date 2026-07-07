package pe.greenminds.ecomind_backend.iam.infrastructure.authorization;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import pe.greenminds.ecomind_backend.iam.application.outboundservices.TokenService;
import pe.greenminds.ecomind_backend.iam.infrastructure.persistence.jpa.repositories.AccountCredentialPersistenceRepository;
import pe.greenminds.ecomind_backend.profile.domain.repositories.UserRepository;

import java.util.List;

@Configuration
@EnableMethodSecurity
public class WebSecurityConfiguration {
    private final TokenService tokenService;
    private final AccountCredentialPersistenceRepository credentialRepository;
    private final UserRepository userRepository;
    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final String[] allowedOrigins;

    public WebSecurityConfiguration(TokenService tokenService,
                                    AccountCredentialPersistenceRepository credentialRepository,
                                    UserRepository userRepository,
                                    AuthenticationEntryPoint authenticationEntryPoint,
                                    @Value("${cors.allowed-origins}") String[] allowedOrigins) {
        this.tokenService = tokenService;
        this.credentialRepository = credentialRepository;
        this.userRepository = userRepository;
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.allowedOrigins = allowedOrigins;
    }

    @Bean
    public BearerAuthorizationRequestFilter bearerAuthorizationRequestFilter() {
        return new BearerAuthorizationRequestFilter(tokenService, credentialRepository, userRepository);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .exceptionHandling(exception -> exception.authenticationEntryPoint(authenticationEntryPoint))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers(
                                "/api/v1/authentication/**",
                                "/v3/api-docs/**",
                                "/swagger-ui.html",
                                "/swagger-ui/**",
                                "/swagger-resources/**",
                                "/webjars/**"
                        ).permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/community/communities").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/community/communities").permitAll()
                        .requestMatchers("/api/v1/**").authenticated()
                        .anyRequest().permitAll()
                )
                .addFilterBefore(bearerAuthorizationRequestFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        var configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of(allowedOrigins));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
