package com.gr.zland.сontroller

import com.gr.zland.model.Vape
import com.gr.zland.servis.VapeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/vapes")
class VapeController @Autowired constructor(
    private val vapeService: VapeService
) {
    @GetMapping
    fun getAllVapes(): List<Vape> {
        return vapeService.findAll()
    }
}

