package org.example.services.generic

import org.example.entities.user.UserEntity
import java.util.*

interface JwtService {
    // Method to generate an access token for a specific user
    fun generateAccessToken(user: UserEntity): String

    // Get the user ID from the token
    fun getUserId(token: String): String

    // Get the username from the token
    fun getUsername(token: String): String

    // Get the expiration date of the token
    fun getExpirationDate(token: String): Date

    // Validate whether our token is valid and was issued by our server
    fun validate(token: String): Boolean
}
