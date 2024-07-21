package com.store.api.store.backend.repositories

import com.store.api.store.backend.models.InvalidatedToken
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface InvalidatedTokenRepository : JpaRepository<InvalidatedToken, Long> {
    fun findByToken(token: String): InvalidatedToken?
    fun deleteByExpirationTimeBefore(now: LocalDateTime)
}