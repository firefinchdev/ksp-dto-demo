package com.firefinchdev.generators

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
annotation class GenerateDTO(val newClassName: String = "")