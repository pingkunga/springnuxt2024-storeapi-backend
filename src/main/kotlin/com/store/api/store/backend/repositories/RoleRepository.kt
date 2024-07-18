package com.store.api.store.backend.repositories

import com.store.api.store.backend.models.Role
import com.store.api.store.backend.models.RoleName
import org.springframework.data.jpa.repository.JpaRepository

interface RoleRepository: JpaRepository<Role, Long> {
    fun findByRoleName(roleName: RoleName): Role?
}