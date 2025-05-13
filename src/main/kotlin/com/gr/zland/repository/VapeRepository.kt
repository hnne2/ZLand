package com.gr.zland.repository

import com.gr.zland.model.Vape
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface VapeRepository : JpaRepository<Vape, Long> {
    @Query("SELECT DISTINCT sort FROM vape", nativeQuery = true)
    fun getDistinctSorts(): List<String>

    fun findAllBySort(sort: String): List<Vape>
}