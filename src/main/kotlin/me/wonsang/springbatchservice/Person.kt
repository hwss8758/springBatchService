package me.wonsang.springbatchservice

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class Person(
        @Id
        @GeneratedValue
        var id: Long? = null,
        var lastName: String,
        var firstName: String)