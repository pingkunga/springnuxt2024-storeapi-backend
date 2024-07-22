package com.store.api.repository

import com.store.api.model.Role
import com.store.api.model.RoleName
import org.springframework.data.jpa.repository.JpaRepository

interface RoleRepository: JpaRepository<Role, Long> {
    fun findByRoleName(roleName: RoleName): Role?
}