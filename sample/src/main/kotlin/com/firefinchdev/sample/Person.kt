package com.firefinchdev.sample

import com.firefinchdev.generators.GenerateDTO

@GenerateDTO(newClassName = "PersonDTO")
data class Person(
    val name: String,
    val age: Int,
    val address: String
)

fun main() {
    val personDto: PersonDTO? = null
}