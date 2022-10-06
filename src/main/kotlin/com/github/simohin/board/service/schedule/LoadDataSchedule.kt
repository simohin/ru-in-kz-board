package com.github.simohin.board.service.schedule

import com.github.simohin.board.entity.RelocantInfo
import com.github.simohin.board.service.RelocantInfoService
import com.github.simohin.board.service.SheetService
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.logging.Logger

@Service
class LoadDataScheduleService(
    private val relocantInfoService: RelocantInfoService,
    private val sheetService: SheetService
) {

    companion object {
        private val log = Logger.getLogger(LoadDataScheduleService::class.simpleName)
    }

    private var lastDataTime: LocalDateTime = LocalDateTime.MIN

    @Scheduled(fixedRateString = "\${schedule.data.load.rate.millis:5000}")
    fun run() = try {
        sheetService.getAfter(lastDataTime)
            .onEach { if (it.time.isAfter(lastDataTime)) lastDataTime = it.time }
            .map { RelocantInfo(it) }
            .let { relocantInfoService.save(it) }
    } catch (e: Exception) {
        log.warning { "Failed to load data" }
    }
}
