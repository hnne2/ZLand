package com.gr.zland.repozitory

import com.gr.zland.model.Partner
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PartnerRepository : JpaRepository<Partner, Long>
