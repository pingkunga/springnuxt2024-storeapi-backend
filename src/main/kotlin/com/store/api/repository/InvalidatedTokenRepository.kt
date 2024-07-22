package com.store.api.repository

import com.store.api.model.InvalidatedToken
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface InvalidatedTokenRepository : JpaRepository<InvalidatedToken, Long> {
    fun findByToken(token: String): InvalidatedToken?
    fun deleteByExpirationTimeBefore(now: LocalDateTime)
}