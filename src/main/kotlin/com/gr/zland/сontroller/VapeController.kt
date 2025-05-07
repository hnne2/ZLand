package com.gr.zland.сontroller

import com.gr.zland.model.Vape
import com.gr.zland.servis.VapeService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/vapes")
class VapeController @Autowired constructor(
    private val vapeService: VapeService
) {
    @GetMapping
    @Operation(summary = "Получить все вейпы")
    fun getAllVapes(): List<Vape> {
        return vapeService.findAll()
    }
    @GetMapping("categories")
    fun getAllCategories():List<String>{
        return vapeService.getAllCategories()
    }
}

