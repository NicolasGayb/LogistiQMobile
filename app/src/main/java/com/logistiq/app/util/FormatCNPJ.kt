package com.logistiq.app.util

fun formatCNPJ(value: String): String {
    // Remove tudo que não é número
    val digits = value.filter { it.isDigit() }

    // Formata progressivamente
    return buildString {
        digits.forEachIndexed { index, c ->
            append(c)
            when (index) {
                1, 4 -> append(".")
                7 -> append("/")
                11 -> append("-")
            }
            if (index >= 13) return@buildString
        }
    }
}
