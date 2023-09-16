package org.example.configurations.security

import org.example.entities.UserEntity
import org.example.repositories.UserRepo
import org.example.repositories.UserRoleRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
open class ApplicationConfig(
    @Autowired private val userRepo: UserRepo,
    @Autowired private val userRoleRepo: UserRoleRepo
) {
    @Bean
    open fun userDetailsService(): UserDetailsService {
        return object : UserDetailsService {
            @Throws(UsernameNotFoundException::class)
            override fun loadUserByUsername(username: String): UserDetails {
                val userEntity = userRepo.findByUsername(username)
                    .orElseThrow { UsernameNotFoundException("User not found") }

                //Інформація про користувача і список його ролей
                val roles = getRoles(userEntity)

                return User(
                    userEntity.username,
                    userEntity.password,
                    roles
                )
            }

            private fun getRoles(userEntity: UserEntity): Collection<GrantedAuthority> {
                val roles = userRoleRepo.findByUser(userEntity)
                val userRoles: Array<String> = roles.map{ uRole -> uRole.role.name }.toTypedArray()
                return AuthorityUtils.createAuthorityList(*userRoles)
            }
        }
    }

    @Bean
    open fun authenticationProvider(): AuthenticationProvider {
        val authProvider = DaoAuthenticationProvider()
        authProvider.setUserDetailsService(userDetailsService())
        authProvider.setPasswordEncoder(passwordEncoder())
        return authProvider
    }

    @Bean
    @Throws(Exception::class)
    open fun authenticationManager(
        config: AuthenticationConfiguration
    ): AuthenticationManager {
        return config.getAuthenticationManager()
    }

//    @Bean
//    @Throws(Exception::class)
//    open fun filterChain(http: HttpSecurity): SecurityFilterChain {
////        return http.build()
//    }

//    @Bean
//    open fun ldapAuthenticationManager(
//        contextSource: BaseLdapPathContextSource
//    ): AuthenticationManager {
//        val factory = LdapBindAuthenticationManagerFactory(contextSource)
//        factory.setUserDetailsContextMapper(CustomUserDetailsContextMapper(userRepo))
//        factory.setUserDnPatterns("uid={0},ou=people") // what ?
//        return factory.createAuthenticationManager()
//    }

    @Bean
    open fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}