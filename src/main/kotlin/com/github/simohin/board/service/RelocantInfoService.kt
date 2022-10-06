package com.github.simohin.board.service

import com.github.simohin.board.entity.RelocantInfo
import com.github.simohin.board.repository.RelocantInfoRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RelocantInfoService(
    private val relocantInfoRepository: RelocantInfoRepository
) {
    fun getCities(): List<String> = relocantInfoRepository.findAllCities()
    fun getAll(): List<RelocantInfo> = relocantInfoRepository.findAll()

    @Transactional
    fun save(items: List<RelocantInfo>): List<RelocantInfo> = relocantInfoRepository.saveAll(items)
}
