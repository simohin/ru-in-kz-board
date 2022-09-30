package com.github.simohin.board.dto

import java.time.LocalDateTime

data class SheetRow(
    val time: LocalDateTime,
    val name: String,
    val contact: String,
    val city: String,
    val good: String
)
