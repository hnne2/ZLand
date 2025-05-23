package com.gr.zland.repository

import com.gr.zland.model.Vape
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface VapeRepository : JpaRepository<Vape, Long> {
    @Query("SELECT DISTINCT sort FROM vape", nativeQuery = true)
    fun getDistinctSorts(): List<String>

    @Query("SELECT * FROM vape WHERE sort = :sort AND isActive = true", nativeQuery = true)
    fun findAllBySort(sort: String): List<Vape>

    @Query("SELECT * FROM vape WHERE is_top15 = true AND isActive = true", nativeQuery = true)
    fun getTop15(): List<Vape>

    @Query("SELECT * FROM vape WHERE isActive = true", nativeQuery = true)
    fun findAllActivated(): List<Vape>

}
