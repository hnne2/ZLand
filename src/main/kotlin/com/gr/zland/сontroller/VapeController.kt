package com.gr.zland.сontroller

import com.gr.zland.dto.CatalogResponse
import com.gr.zland.model.*
import com.gr.zland.servis.VapeService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/apiZ/vapes")
class VapeController @Autowired constructor(
    private val vapeService: VapeService
) {
    @GetMapping
    @Operation(summary = "Получить все вейпы")
    fun getAllVapes(): List<Vape> {
        return vapeService.findAll()
    }
    @GetMapping("categories")
    fun getCatalog(): ResponseEntity<Any> {
        val categories = vapeService.getAllCategories()
        return ResponseEntity.ok(
            mapOf(
                "seo" to mapOf("H1" to "Каталог вкусов"),
                "categories" to categories
            )
        )
    }

    @GetMapping("/categories/{slug}")
    fun getCategoryBySlug(@PathVariable slug: String): ResponseEntity<CatalogResponse> {
        val response = vapeService.getCatalogBySlug(slug)
        return ResponseEntity.ok(response)
    }

}

