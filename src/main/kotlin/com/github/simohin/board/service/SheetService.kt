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
        private const val RANGE = "A2:E"
        private val DATETIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy H:mm:ss")
    }

    fun getAll(): List<SheetRow> =
        sheets.spreadsheets()
            .values()
            .get(spreadSheetId, RANGE)
            .execute()
            .values
            .toSheetRows()

    private fun <E> MutableCollection<E>.toSheetRows(): List<SheetRow> =
        this.filterIsInstance<ArrayList<ArrayList<String>>>()[0].map {
            SheetRow(
                LocalDateTime.parse(it[0], DATETIME_FORMATTER),
                it[1],
                it[2],
                it[3],
                it[4]
            )
        }
}
