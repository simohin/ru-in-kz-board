package com.github.simohin.board.repository

import com.github.simohin.board.entity.RelocantInfo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface RelocantInfoRepository : JpaRepository<RelocantInfo, Long> {

    @Query("SELECT DISTINCT i.city FROM RelocantInfo i")
    fun findAllCities(): List<String>
}
