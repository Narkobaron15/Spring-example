package org.example.configurations.security

import org.example.constants.Roles
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
open class SecurityConfiguration (
    private val jwtAuthFilter: JwtAuthenticationFilter,
    //    private val logoutHandler: LogoutHandler,
    private val authenticationProvider: AuthenticationProvider
) {
//

    @Bean
    @Throws(Exception::class)
    open fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .cors(Customizer.withDefaults())
            .csrf { it.disable() }
            .authorizeHttpRequests { auths ->
                auths.requestMatchers("/api/account/**").permitAll()
                    .requestMatchers("/api/account/**").permitAll()
                    .requestMatchers("/files/**").permitAll()
                    .requestMatchers("/uploads/**").permitAll()
                    .requestMatchers("/static/**").permitAll()
                    .requestMatchers("/swagger-resources/**").permitAll()
                    .requestMatchers("/v3/api-docs/**").permitAll()
                    .requestMatchers("/webjars/**").permitAll()
                    .requestMatchers("/rest-api-docs/**").permitAll()
                    .requestMatchers("/swagger-ui/**").permitAll()
                    .requestMatchers("/api/categories/**").hasAuthority(Roles.Admin)
                    .requestMatchers(HttpMethod.POST, "/api/categories").hasAuthority(Roles.Admin)
                    .requestMatchers(HttpMethod.GET, "/api/products").permitAll()
                    //.requestMatchers("/api/products/**").hasAuthority(Roles.Admin)
                    .anyRequest().authenticated()
            }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(
                jwtAuthFilter,
                UsernamePasswordAuthenticationFilter::class.java
            )
//            .logout {
//                it.logoutUrl("/api/v1/auth/logout")
//                    .addLogoutHandler(logoutHandler)
//                    .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
//            }


        return http.build()
    }
}
