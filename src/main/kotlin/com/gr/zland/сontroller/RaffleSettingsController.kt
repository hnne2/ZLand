package com.gr.zland.сontroller

import com.gr.zland.model.RaffleSettings
import com.gr.zland.servis.RaffleSettingsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/raffle-settings")
class RaffleSettingsController @Autowired constructor(
    private val raffleSettingsService: RaffleSettingsService
) {
    @GetMapping
    fun getAllRaffleSettings(): List<RaffleSettings> {
        return raffleSettingsService.findAll()
    }
}
