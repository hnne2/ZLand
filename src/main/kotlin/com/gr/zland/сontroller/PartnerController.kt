package com.gr.zland.сontroller

import com.gr.zland.model.Partner
import com.gr.zland.servis.PartnerService
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/partners")
class PartnerController @Autowired constructor(
    private val partnerService: PartnerService
) {
    @PostMapping
    fun createPartner(@Valid @RequestBody partner: Partner): ResponseEntity<Partner> {
        val savedPartner = partnerService.create(partner)
        return ResponseEntity.ok(savedPartner)
    }
}
