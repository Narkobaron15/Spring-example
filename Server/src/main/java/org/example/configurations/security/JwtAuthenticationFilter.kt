package org.example.configurations.security

import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.example.services.implementations.JwtServiceImpl
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException

/**
 * Filter that runs once per request and checks if
 * the request contains a valid JWT token.
 *
 * @param jwtService Service containing JWT methods
 * @param userDetailsService Service containing user details methods
 */
@Component
class JwtAuthenticationFilter(
    private val jwtService: JwtServiceImpl,
    private val userDetailsService: UserDetailsService
) : OncePerRequestFilter() {
    /**
     * Filter method
     * @param request HTTP request
     * @param response HTTP response
     * @param filterChain Chain of filters (middlewares) that will be applied to the request
     */
    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authHeader = request.getHeader("Authorization")
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response)
            return
        }

        val jwt: String = authHeader.substring(7)
        val userEmail: String = jwtService.getUsername(jwt)

        if (SecurityContextHolder.getContext().authentication == null) {
            val userDetails = userDetailsService.loadUserByUsername(userEmail)
            if (jwtService.validate(jwt)) {
                val authToken = UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.authorities
                )
                authToken.details = WebAuthenticationDetailsSource().buildDetails(request)
                SecurityContextHolder.getContext().authentication = authToken
            }
        }

        filterChain.doFilter(request, response)
    }
}
