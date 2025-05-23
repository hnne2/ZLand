package com.gr.zland.bot.service

import com.gr.zland.bot.keyboard.MenuKeyboard
import com.gr.zland.bot.model.PickupPoint
import com.gr.zland.bot.utils.calculateDistance
import com.gr.zland.model.PickupLocation
import com.gr.zland.servis.PickupLocationService
import org.springframework.stereotype.Service
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.send.SendLocation
import org.telegram.telegrambots.meta.api.methods.send.SendMessage

@Service
class LocationService(
    private val bot: TelegramLongPollingBot,
    private val pickupLocationService: PickupLocationService,
    private val menuKeyboard: MenuKeyboard
) {
    fun sendNearestPickupLocations(
        chatId: String,
        userLat: Float,
        userLon: Float,
    ) {
        val nearestLocations = pickupLocationService.findNearestLocations(userLat, userLon,5)

        if (nearestLocations.isEmpty()) {
            val noPointsMsg = SendMessage(chatId, "К сожалению, поблизости нет пунктов выдачи 😔")
            bot.execute(noPointsMsg)
            return
        }

        nearestLocations.forEachIndexed { index, point ->
            val textMessage = SendMessage().apply {
                this.chatId = chatId
                text = "📍 ${index + 1}) ${point.name} \n ${point.address}"
                if (index == nearestLocations.lastIndex) {
                    replyMarkup = menuKeyboard.create()
                    val mapUrl = buildGoogleMapUrl(nearestLocations)
                    val urlMessage = SendMessage(chatId, "🗺 Все точки на карте: $mapUrl")
                    bot.execute(urlMessage)
                }
            }

            val locationMessage = SendLocation().apply {
                this.chatId = chatId
                latitude = point.latitude?.toDouble() ?: 0.0
                longitude = point.longitude?.toDouble() ?: 0.0
            }

            bot.execute(textMessage)
            bot.execute(locationMessage)
        }
    }
    fun buildGoogleMapUrl(points: List<PickupLocation>): String {
        if (points.isEmpty()) return "https://maps.google.com"

        // Первая точка — как конечный пункт (destination)
        val destination = "${points.first().latitude},${points.first().longitude}"

        // Остальные точки — как промежуточные (waypoints)
        val waypoints = points.drop(1).joinToString("|") { point ->
            "${point.latitude},${point.longitude}"
        }

        return buildString {
            append("https://www.google.com/maps/dir/?api=1")
            append("&destination=$destination")
            if (waypoints.isNotEmpty()) {
                append("&waypoints=$waypoints")
            }
        }
    }

}
