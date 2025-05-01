package com.gr.zland.servis

import com.gr.zland.model.PickupLocation
import com.gr.zland.repozitory.PickupLocationRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import kotlin.math.*

@Service
class PickupLocationService @Autowired constructor(
    private val pickupLocationRepository: PickupLocationRepository
) {
    fun findNearestLocations(latitude: Float, longitude: Float, limit: Int): List<PickupLocation> {
        val locations = pickupLocationRepository.findAll()
        return locations
            .filter { it.latitude != null && it.longitude != null }
            .map { location ->
                val distance = calculateHaversineDistance(
                    latitude.toDouble(),
                    longitude.toDouble(),
                    location.latitude!!.toDouble(),
                    location.longitude!!.toDouble()
                )
                Pair(location, distance)
            }
            .sortedBy { it.second }
            .take(limit)
            .map { it.first }
    }

    private fun calculateHaversineDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val earthRadius = 6371.0 // Радиус Земли в километрах

        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)

        val a = sin(dLat / 2).pow(2) +
                cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2)) * sin(dLon / 2).pow(2)
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))

        return earthRadius * c
    }
    fun create(pickupLocation: PickupLocation): PickupLocation {
        return pickupLocationRepository.save(pickupLocation)
    }

    fun findById(id: Long): PickupLocation? {
        return pickupLocationRepository.findById(id).orElse(null)
    }

    fun update(id: Long, updatedPickupLocation: PickupLocation): PickupLocation? {
        val existingPickupLocation = findById(id) ?: return null
        val pickupLocationToUpdate = existingPickupLocation.copy(
            name = updatedPickupLocation.name,
            address = updatedPickupLocation.address,
            latitude = updatedPickupLocation.latitude,
            longitude = updatedPickupLocation.longitude,
            createdAt = updatedPickupLocation.createdAt,
            updatedAt = updatedPickupLocation.updatedAt
        )
        return pickupLocationRepository.save(pickupLocationToUpdate)
    }

    fun delete(id: Long) {
        pickupLocationRepository.deleteById(id)
    }

    fun findAll(): List<PickupLocation> {
        return pickupLocationRepository.findAll()
    }
}
