package com.gr.zland.servis

import com.gr.zland.model.Vape
import com.gr.zland.repository.VapeRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class VapeService @Autowired constructor(
    private val vapeRepository: VapeRepository
) {
    fun create(vape: Vape): Vape {
        return vapeRepository.save(vape)
    }

    fun findById(id: Long): Vape? {
        return vapeRepository.findById(id).orElse(null)
    }

    fun update(id: Long, updatedVape: Vape): Vape? {
        val existingVape = findById(id) ?: return null
        val vapeToUpdate = existingVape.copy(
            sort = updatedVape.sort,
            flavorList = updatedVape.flavorList,
            sweetness = updatedVape.sweetness,
            iceLevel = updatedVape.iceLevel,
            sourness = updatedVape.sourness,
            imagePath = updatedVape.imagePath
        )
        return vapeRepository.save(vapeToUpdate)
    }

    fun delete(id: Long) {
        vapeRepository.deleteById(id)
    }

    fun findAll(): List<Vape> {
        return vapeRepository.findAll()
    }
}
