package com.gr.zland.repository

import com.gr.zland.model.RaffleSettings
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RaffleSettingsRepository : JpaRepository<RaffleSettings, Long>
