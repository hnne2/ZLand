package com.gr.zland.servis

import com.gr.zland.model.Winner
import com.gr.zland.repozitory.WinnerRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class WinnerService @Autowired constructor(
    private val winnerRepository: WinnerRepository
) {
    fun create(winner: Winner): Winner {
        return winnerRepository.save(winner)
    }

    fun findById(id: Long): Winner? {
        return winnerRepository.findById(id).orElse(null)
    }

    fun update(id: Long, updatedWinner: Winner): Winner? {
        val existingWinner = findById(id) ?: return null
        val winnerToUpdate = existingWinner.copy(
            phone = updatedWinner.phone,
            telegramNick = updatedWinner.telegramNick,
            date = updatedWinner.date,
            time = updatedWinner.time,
            prize = updatedWinner.prize,
            createdAt = updatedWinner.createdAt,
            updatedAt = updatedWinner.updatedAt
        )
        return winnerRepository.save(winnerToUpdate)
    }

    fun delete(id: Long) {
        winnerRepository.deleteById(id)
    }

    fun findAll(): List<Winner> {
        return winnerRepository.findAll()
    }
}
