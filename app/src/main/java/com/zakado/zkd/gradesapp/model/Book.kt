package com.zakado.zkd.gradesapp.model

/**
 * Modelo de datos a guardar
 * Id           => Identificador unico de la asignatura
 * name         => Nombre de la asignatura
 * note/grades  => Calificación correspondiente a la asignatura
 * category     => Clasificación de la nota en: Suspenso, Aprobado, Notable o Sobresaliente.
 */
data class Book(val id: Int, val name: String, val note: Double, val category: String)
