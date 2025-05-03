package com.gr.zland.servis

import com.gr.zland.model.RaffleSettings
import com.gr.zland.repository.RaffleSettingsRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class RaffleSettingsService @Autowired constructor(
    private val raffleSettingsRepository: RaffleSettingsRepository
) {
    fun create(raffleSettings: RaffleSettings): RaffleSettings {
        return raffleSettingsRepository.save(raffleSettings)
    }

    fun findById(id: Long): RaffleSettings? {
        return raffleSettingsRepository.findById(id).orElse(null)
    }

    fun update(id: Long, updatedRaffleSettings: RaffleSettings): RaffleSettings? {
        val existingRaffleSettings = findById(id) ?: return null
        val raffleSettingsToUpdate = existingRaffleSettings.copy(
            probability = updatedRaffleSettings.probability,
            prize = updatedRaffleSettings.prize,
            prizeImagePath = updatedRaffleSettings.prizeImagePath,
            pickupLocation = updatedRaffleSettings.pickupLocation,
            maxPrizes = updatedRaffleSettings.maxPrizes,
            latitude = updatedRaffleSettings.latitude,
            longitude = updatedRaffleSettings.longitude,
            createdAt = updatedRaffleSettings.createdAt,
            updatedAt = updatedRaffleSettings.updatedAt
        )
        return raffleSettingsRepository.save(raffleSettingsToUpdate)
    }

    fun delete(id: Long) {
        raffleSettingsRepository.deleteById(id)
    }

    fun findAll(): List<RaffleSettings> {
        return raffleSettingsRepository.findAll()
    }
}
