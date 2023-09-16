package org.example.mappers

import org.example.repositories.UserRepo
import org.springframework.ldap.core.DirContextAdapter
import org.springframework.ldap.core.DirContextOperations
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.ldap.userdetails.UserDetailsContextMapper

open class CustomUserDetailsContextMapper(
    private val userRepo: UserRepo
) : UserDetailsContextMapper {
    @Throws(UsernameNotFoundException::class)
    override fun mapUserFromContext(
        ctx: DirContextOperations, username: String,
        authorities: Collection<GrantedAuthority?>
    ): UserDetails {
        return try {
            userRepo.findAll().first { x -> x.username == username }
        } catch (e: NoSuchElementException) {
            throw UsernameNotFoundException("User $username is not found")
        }
    }

    override fun mapUserToContext(user: UserDetails, ctx: DirContextAdapter) {
        TODO()
    }
}

