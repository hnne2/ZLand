package com.gr.zland.repository

import com.gr.zland.model.Winner
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface WinnerRepository : JpaRepository<Winner, Long>
