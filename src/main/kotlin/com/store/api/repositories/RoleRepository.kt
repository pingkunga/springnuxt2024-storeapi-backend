package com.store.api.repositories

import com.store.api.models.Role
import com.store.api.models.RoleName
import org.springframework.data.jpa.repository.JpaRepository

interface RoleRepository: JpaRepository<Role, Long> {
    fun findByRoleName(roleName: RoleName): Role?
}