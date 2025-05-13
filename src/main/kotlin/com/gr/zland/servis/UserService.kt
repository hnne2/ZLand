package com.gr.zland.servis

import com.gr.zland.model.TelegramUser
import com.gr.zland.repository.UserRepository
import org.springframework.stereotype.Service


@Service
class myTelegramUserService(
    private val userRepository: UserRepository
) {
    fun getOrCreateUser(telegramUser: TelegramUser): TelegramUser {
        return userRepository.findByTelegramId(telegramUser.telegramId)
            ?: userRepository.save(telegramUser)
    }
    fun getTelegramUserById(id:Long):TelegramUser?{
        return userRepository.findByTelegramId(id)
    }
    fun save(telegramUser: TelegramUser){
        userRepository.save(telegramUser)
    }

}