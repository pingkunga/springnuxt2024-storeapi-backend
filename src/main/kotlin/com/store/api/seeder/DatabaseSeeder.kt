package com.store.api.seeder

import com.store.api.constant.RoleName
import com.store.api.model.Role
import com.store.api.repository.RoleRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class DatabaseSeeder(private val roleRepository: RoleRepository) : CommandLineRunner {
    override fun run(vararg args: String?) {
        //Seed Role Table
        if (roleRepository.count() == 0L) {
            roleRepository.saveAll(
                listOf(
                    Role(roleName = RoleName.USER),
                    Role(roleName = RoleName.MANAGER),
                    Role(roleName = RoleName.ADMIN)
                )
            )
        }
    }

}