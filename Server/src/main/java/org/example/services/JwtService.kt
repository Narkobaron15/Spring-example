package org.example.services

import io.jsonwebtoken.*
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.example.entities.UserEntity
import org.example.entities.UserRoleEntity
import org.example.repositories.UserRoleRepo
import org.springframework.stereotype.Service
import java.security.Key
import java.util.*

@Service
class JwtService(
    private val userRoleRepo: UserRoleRepo
) {
    // ключ, яким ми шифруємо (будь-які букви чи цифри)
    private val jwtSecret =
        "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970"
    //вказує хто власник цього токена. Можна вписати ім'я свого домена
    private val jwtIssuer = "step.io"

    //метод призначений для того, щоб для визначеного юзера зробити jwt token
    fun generateAccessToken(user: UserEntity): String {
        val roles: List<UserRoleEntity> = userRoleRepo.findByUser(user)
        return Jwts.builder()
            .setSubject(String.format("%s,%s", user.id, user.username))
            .claim("email", user.username) //.claim("image", user.getImage())
            .claim("roles",
                roles//витягується списочок ролей, які є у юзера
                    .map { role: UserRoleEntity -> role.role.name }
                    .toTypedArray()
            )
            .setIssuer(jwtIssuer) //записуємо хто власник токена
            .setIssuedAt(Date(System.currentTimeMillis())) //коли був створений токен
            .setExpiration(Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000)) // 1 week  зазначаємо скільки часу буде жити токен
            .signWith(signInKey, SignatureAlgorithm.HS256) //шифруємо токен за допомогою сікретного ключа
            .compact()
    }

    private val signInKey: Key
        get() {
            val keyBytes: ByteArray = Decoders.BASE64.decode(jwtSecret)
            return Keys.hmacShaKeyFor(keyBytes)
        }

    // з токена можна витягнути Id юзера
    fun getUserId(token: String?): String {
        val claims: Claims = Jwts.parser()
            .setSigningKey(jwtSecret) // перевіряється чи цей токен видавався нашим серваком
            .parseClaimsJws(token)
            .body
        return claims.subject.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            .get(0) //з токена бере перший елемент Id
    }

    // з токена можна витягнути username юзера
    fun getUsername(token: String?): String {
        val claims: Claims = Jwts.parser()
            .setSigningKey(jwtSecret)
            .parseClaimsJws(token)
            .body
        return claims.subject.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray().get(1)
    }

    // метод повертає дату до якої живе токен
    fun getExpirationDate(token: String?): Date {
        val claims: Claims = Jwts.parser()
            .setSigningKey(jwtSecret)
            .parseClaimsJws(token)
            .body
        return claims.expiration
    }

    //перевіряє чи наш токен валідний і чи видавався нашим сервером
    fun validate(token: String?): Boolean {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token)
            return true
        } catch (ex: SignatureException) {
            println("Invalid JWT signature - " + ex.message)
        } catch (ex: MalformedJwtException) {
            println("Invalid JWT token - " + ex.message)
        } catch (ex: ExpiredJwtException) {
            println("Expired JWT token - " + ex.message)
        } catch (ex: UnsupportedJwtException) {
            println("Unsupported JWT token - " + ex.message)
        } catch (ex: IllegalArgumentException) {
            println("JWT claims string is empty - " + ex.message)
        }
        return false
    }
}
