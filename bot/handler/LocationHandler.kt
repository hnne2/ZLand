package com.gr.ZLand.bot.handler

import com.gr.ZLand.bot.model.PickupPoint
import com.gr.ZLand.bot.service.LocationService
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.Message

@Component
class LocationHandler(
    private val locationService: LocationService,
    private val pickupPoints: List<PickupPoint>
) {
    fun handle(message: Message) {
        val location = message.location
        val chatId = message.chatId.toString()
        locationService.sendNearestPickupLocations(
            chatId,
            location.latitude,
            location.longitude,
            pickupPoints
        )
    }
}
