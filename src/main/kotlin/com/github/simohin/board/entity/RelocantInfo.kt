package com.github.simohin.board.entity

import com.github.simohin.board.dto.SheetRow
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class RelocantInfo(
    val time: LocalDateTime,
    val name: String,
    val contact: String,
    val city: String,
    val good: String,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
) {
    constructor(row: SheetRow) : this(row.time, row.name, row.contact, row.city, row.good)

    fun searchFields() = setOf(time.toString(), name, contact, good)
}
