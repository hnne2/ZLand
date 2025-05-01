package com.gr.zland.bot.service

import com.gr.zland.bot.model.PickupPoint
import com.gr.zland.bot.utils.calculateDistance
import org.springframework.stereotype.Service
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.send.SendLocation
import org.telegram.telegrambots.meta.api.methods.send.SendMessage

@Service
class LocationService(
    private val bot: TelegramLongPollingBot
) {
    fun sendNearestPickupLocations(
        chatId: String,
        userLat: Double,
        userLon: Double,
        pickupPoints: List<PickupPoint>
    ) {
        val nearest = findNearestPickupPoints(userLat, userLon, pickupPoints)

        if (nearest.isEmpty()) {
            val noPointsMsg = SendMessage(chatId, "К сожалению, поблизости нет пунктов выдачи 😔")
            bot.execute(noPointsMsg)
            return
        }

        nearest.forEachIndexed { index, point ->
            val textMessage = SendMessage().apply {
                this.chatId = chatId
                text = "📍 ${index + 1}) ${point.name}"
            }
            val locationMessage = SendLocation().apply {
                this.chatId = chatId
                latitude = point.latitude
                longitude = point.longitude
            }
            bot.execute(textMessage)
            bot.execute(locationMessage)
        }
    }

    private fun findNearestPickupPoints(
        userLat: Double,
        userLon: Double,
        pickupPoints: List<PickupPoint>,
        limit: Int = 3
    ): List<PickupPoint> {
        return pickupPoints
            .map { point -> point to calculateDistance(userLat, userLon, point.latitude, point.longitude) }
            .sortedBy { it.second }
            .take(limit)
            .map { it.first }
    }
}
