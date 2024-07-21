package com.store.api.store.backend.utils

import com.store.api.store.backend.models.InvalidatedToken
import com.store.api.store.backend.repositories.InvalidatedTokenRepository
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class TokenStore(
    private val invalidatedTokenRepository: InvalidatedTokenRepository
) {

    fun invalidateToken(token: String, expirationTime: Long) {
        val expirationDateTime = LocalDateTime.now().plusSeconds(expirationTime / 1000)
        val invalidatedToken = InvalidatedToken(token = token, expirationTime = expirationDateTime)
        invalidatedTokenRepository.save(invalidatedToken)
    }

    fun isTokenInvalidated(token: String): Boolean {
        return invalidatedTokenRepository.findByToken(token) != null
    }
}