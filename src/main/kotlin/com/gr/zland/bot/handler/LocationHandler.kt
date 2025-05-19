package com.gr.zland.bot.handler

import com.gr.zland.bot.model.PickupPoint
import com.gr.zland.bot.service.LocationService
import com.gr.zland.servis.PickupLocationService
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.Message

@Component
class LocationHandler(
    private val locationService: LocationService,
) {
    fun handle(message: Message) {
        val location = message.location
        val chatId = message.chatId.toString()
        locationService.sendNearestPickupLocations(
            chatId,
            location.latitude.toFloat(),
            location.longitude.toFloat()
        )
    }
}
