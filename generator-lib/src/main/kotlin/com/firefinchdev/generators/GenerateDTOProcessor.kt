package com.firefinchdev.generators

import com.google.devtools.ksp.processing.*
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSTypeReference
import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ksp.toTypeName
import com.squareup.kotlinpoet.ksp.writeTo

class GenerateDTOProcessor(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger
) : SymbolProcessor {

    override fun process(resolver: Resolver): List<KSAnnotated> {
        // Find all classes annotated with @GenerateDTO
        val symbols = resolver.getSymbolsWithAnnotation(GenerateDTO::class.qualifiedName!!)
            .filterIsInstance<KSClassDeclaration>()

        symbols.forEach { classDeclaration ->
            // Get the annotation's parameter for the new class name
            val dtoAnnotation = classDeclaration.annotations
                .first { it.shortName.asString() == "GenerateDTO" }
            val newClassName = dtoAnnotation.arguments
                .find { it.name?.asString() == "newClassName" }
                ?.value as? String ?: "${classDeclaration.simpleName.asString()}DTO"

            // Generate the new DTO class based on the original class
            generateDTOClass(classDeclaration, newClassName)
        }

        return emptyList() // Return an empty list as no symbols are deferred
    }

    private fun generateDTOClass(classDeclaration: KSClassDeclaration, newClassName: String) {
        val packageName = classDeclaration.packageName.asString()

        // Map for custom type transformations
        val typeMapping = mapOf(
            String::class.asTypeName() to ClassName("com.firefinchdev.data", "TextData"),
            Int::class.asTypeName() to LONG
        )

        // Create properties based on the original class's properties, applying type mappings
        val properties = classDeclaration.getAllProperties().map { property ->
            val originalType = property.type.toTypeName()
            val mappedType = typeMapping[originalType] ?: originalType

            PropertySpec.builder(property.simpleName.asString(), mappedType)
                .initializer(property.simpleName.asString())
                .build()
        }.toList()

        // Build the new data class
        val dtoClass = TypeSpec.classBuilder(newClassName)
            .addModifiers(KModifier.DATA)
            .primaryConstructor(
                FunSpec.constructorBuilder()
                    .addParameters(properties.map { param ->
                        ParameterSpec.builder(param.name, param.type).build()
                    })
                    .build()
            )
            .addProperties(properties)
            .build()

        // Create the Kotlin file and write it to the generated source directory
        val file = FileSpec.builder(packageName, newClassName)
            .addType(dtoClass)
            .build()

        file.writeTo(codeGenerator, Dependencies(true, classDeclaration.containingFile!!))
    }
}