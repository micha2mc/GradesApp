package com.zakado.zkd.gradesapp.utils

import com.zakado.zkd.gradesapp.model.Book
import java.math.RoundingMode
import java.text.DecimalFormat

class Utils {
    companion object {
        /**
         * Ésta funcion se encarga de clasificar la nota introducida.
         */
        fun gradesClasification(nota: Double): String {
            return when (nota) {
                in 0.0..4.9 -> "Suspenso"
                in 5.0..6.9 -> "Aprobado"
                in 7.0..8.9 -> "Notable"
                in 9.0..10.0 -> "Sobresaliente"
                else -> "Nota fuera de rango"
            }
        }

        /**
         * Funcion que se encarga de calcular la nota media del usuario.
         */
        fun calcularNotaMedia(allBooks: List<Book>): String {
            var sumatorio = 0.0
            val notaM: Double
            for (book in allBooks) {
                sumatorio += book.note
            }
            val df = DecimalFormat("#.#")
            df.roundingMode = RoundingMode.CEILING
            return if (allBooks.isNotEmpty()) {
                notaM = sumatorio / allBooks.size
                "Nota media: " + df.format(notaM) + " (${gradesClasification(notaM)})"
            } else {
                "Nota media: Sin especificar"
            }

        }
    }
}