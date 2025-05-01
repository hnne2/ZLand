package com.gr.zland.repozitory

import com.gr.zland.model.Vape
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface VapeRepository : JpaRepository<Vape, Long>
