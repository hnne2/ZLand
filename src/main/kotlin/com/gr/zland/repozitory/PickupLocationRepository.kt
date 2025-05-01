package com.gr.zland.repozitory

import com.gr.zland.model.PickupLocation
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PickupLocationRepository : JpaRepository<PickupLocation, Long>

