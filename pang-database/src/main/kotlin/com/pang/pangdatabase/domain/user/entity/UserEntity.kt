package com.pang.pangdatabase.domain.user.entity

import com.pang.pangdatabase.domain.common.entity.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,
    val name: String,
    val password: String,
): BaseEntity() {
}