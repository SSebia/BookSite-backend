package lt.almantas.booksite.security;

import lombok.RequiredArgsConstructor;
import lt.almantas.booksite.model.Entity.Roles;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST,"/api/books/add").hasAuthority(Roles.ADMIN.getRoleName())
                        .requestMatchers(HttpMethod.POST,"/api/books/edit/**").hasAuthority(Roles.ADMIN.getRoleName())
                        .requestMatchers(HttpMethod.POST,"/api/books/delete/**").hasAuthority(Roles.ADMIN.getRoleName())
                        .requestMatchers(HttpMethod.POST,"/api/category/**").hasAuthority(Roles.ADMIN.getRoleName())
                        .requestMatchers(HttpMethod.POST,"/api/books/**").hasAnyAuthority(Roles.USER.getRoleName(), Roles.ADMIN.getRoleName())
                        .requestMatchers(HttpMethod.POST,"/api/token/verify").hasAnyAuthority(Roles.USER.getRoleName(), Roles.ADMIN.getRoleName())
                        .requestMatchers(HttpMethod.POST,"/api/auth/**").permitAll()
                        .anyRequest().authenticated())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exceptionHandling -> exceptionHandling.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .build();
    }
}