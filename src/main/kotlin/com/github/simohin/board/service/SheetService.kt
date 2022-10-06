package com.github.simohin.board.service

import com.github.simohin.board.dto.SheetRow
import com.google.api.services.sheets.v4.Sheets
import org.springframework.beans.factory.annotation.Value
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.function.Predicate

@Service
class SheetService(
    @Value("\${service.sheet.id:null}")
    private val spreadSheetId: String,
    private val sheets: Sheets
) {
    companion object {
        private const val FULL_RANGE = "A2:E"
        private const val CITY_RANGE = "D2:D"
        private val DATETIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy H:mm:ss")
    }

    @Cacheable("SheetService.getAll")
    fun getAll(): List<SheetRow> = getAll { true }

    @Cacheable("SheetService.getCities")
    fun getCities() = sheets.spreadsheets()
        .values()
        .get(spreadSheetId, CITY_RANGE)
        .execute()
        .values
        .toDistinctValues()

    private fun getAll(filter: Predicate<SheetRow>) =
        sheets.spreadsheets()
            .values()
            .get(spreadSheetId, FULL_RANGE)
            .execute()
            .values
            .toSheetRows()
            .filter { filter.test(it) }

    private fun <E> MutableCollection<E>.toSheetRows() = getValues()
        .map {
            SheetRow(
                LocalDateTime.parse(it[0], DATETIME_FORMATTER),
                it[1],
                it[2],
                it[3],
                it[4]
            )
        }

    private fun <E> MutableCollection<E>.getValues() =
        this.filterIsInstance<ArrayList<ArrayList<String>>>()[0]

    private fun <E> MutableCollection<E>.toDistinctValues() =
        getValues().map { it[0].trim() }.toSet()
}
