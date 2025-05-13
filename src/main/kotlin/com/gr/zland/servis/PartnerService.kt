package com.gr.zland.servis

import com.gr.zland.model.Partner
import com.gr.zland.repository.PartnerRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PartnerService @Autowired constructor(
    private val partnerRepository: PartnerRepository
) {
    fun create(partner: Partner): Partner {
        return partnerRepository.save(partner)
    }

    fun findById(id: Long): Partner? {
        return partnerRepository.findById(id).orElse(null)
    }

    fun update(id: Long, updatedPartner: Partner): Partner? {
        val existingPartner = findById(id) ?: return null
        val partnerToUpdate = existingPartner.copy(
            name = updatedPartner.name,
            tel = updatedPartner.tel,
            email = updatedPartner.email,
            telegram = updatedPartner.telegram,
            comment = updatedPartner.comment,
            createdAt = updatedPartner.createdAt,
            updatedAt = updatedPartner.updatedAt
        )
        return partnerRepository.save(partnerToUpdate)
    }

    fun delete(id: Long) {
        partnerRepository.deleteById(id)
    }

    fun findAll(): List<Partner> {
        return partnerRepository.findAll()
    }
}
