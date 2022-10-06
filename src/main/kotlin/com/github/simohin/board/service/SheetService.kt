package com.github.simohin.board.service

import com.github.simohin.board.dto.SheetRow
import com.google.api.services.sheets.v4.Sheets
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Service
class SheetService(
    @Value("\${service.sheet.id:null}")
    private val spreadSheetId: String,
    private val sheets: Sheets
) {
    companion object {
        private const val FULL_RANGE = "A2:E"
        private val DATETIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy H:mm:ss")
    }

    fun getAll(): List<SheetRow> = sheets.spreadsheets()
        .values()
        .get(spreadSheetId, FULL_RANGE)
        .execute()
        .values
        .toSheetRows()

    fun getAfter(lastTime: LocalDateTime) = getAll().filter { it.time.isAfter(lastTime) }

    private fun <E> MutableCollection<E>.toSheetRows() = this.filterIsInstance<ArrayList<ArrayList<String>>>()[0]
        .map {
            SheetRow(
                LocalDateTime.parse(it[0], DATETIME_FORMATTER),
                it[1].trim(),
                it[2].trim(),
                it[3].trim(),
                it[4].trim()
            )
        }
}
