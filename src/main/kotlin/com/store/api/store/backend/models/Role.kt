package com.store.api.store.backend.models

import jakarta.persistence.*

enum class RoleName {
    USER, MANAGER, ADMIN
}

@Entity
@Table(name = "roles")
data class Role(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Enumerated(EnumType.STRING)
    @Column(unique = true, nullable = false)
    val roleName: RoleName,

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_roles",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "role_id")]
    )
    var roles: MutableList<Role> = mutableListOf(),

    @ManyToMany(mappedBy = "roles")
    val users: MutableList<User> = mutableListOf()



)
