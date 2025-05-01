package com.gr.zland.сontroller

import com.gr.zland.model.PickupLocation
import com.gr.zland.servis.PickupLocationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/pickup-locations")
class PickupLocationController @Autowired constructor(
    private val pickupLocationService: PickupLocationService
) {
    @GetMapping("/nearest")
    fun findNearestLocations(
        @RequestParam latitude: Float,
        @RequestParam longitude: Float
    ): ResponseEntity<List<PickupLocation>> {
        if (latitude < -90 || latitude > 90 || longitude < -180 || longitude > 180) {
            return ResponseEntity.badRequest().body(emptyList())
        }
        val nearestLocations = pickupLocationService.findNearestLocations(latitude, longitude, 5)
        return ResponseEntity.ok(nearestLocations)
    }
}

