package com.gr.zland.servis

import com.gr.zland.dto.*
import com.gr.zland.model.*
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

    fun getAllCategories(): List<CategoryDto> {
        val sorts = vapeRepository.getDistinctSorts()

        return sorts.mapIndexed { index, sort ->
            CategoryDto(
                id = index.toLong(),
                icon = IconDto(
                    url = "/icons/a.svg", // можно динамически подставлять
                    alt = sort
                ),
                link = LinkDto(
                    to = sort.lowercase(),
                    label = sort
                )
            )
        }
    }

    fun getCatalogBySlug(slug: String): CatalogResponse {
        val vapes = if (slug == "all") {
            vapeRepository.findAll()
        } else {
            vapeRepository.findAllBySort(slug)
        }

        val products = vapes.map { vape ->
            ProductDto(
                id = vape.id,
                label = vape.flavorList,
                sort = vape.sort,
                flavorList = vape.flavorList,
                sweetness = vape.sweetness,
                iceLevel = vape.iceLevel,
                sourness = vape.sourness,
                image = ImageDto(
                    url = if (!vape.imagePath.isNullOrBlank()) "/${vape.imagePath}" else "/images/a.png",
                    alt = vape.flavorList
                ),
                isTop = false,
                parameters = listOf(
                    ParameterDto(1, "Сладость", vape.sweetness.toString()),
                    ParameterDto(2, "Холодок", vape.iceLevel.toString()),
                    ParameterDto(3, "Кислинка", vape.sourness.toString())
                )
            )
        }

        return CatalogResponse(
            seo = SeoDto(H1 = slug.replaceFirstChar { it.uppercase() } + " вкусы"),
            specification = generateSpecification(slug),
            products = products
        )
    }

    // Пример генерации описания
    private fun generateSpecification(slug: String): String {
        return when (slug.lowercase()) {
            "fruits" -> "<ul><li>Фруктовая основа</li><li>Освежающие</li></ul>"
            "mint" -> "<ul><li>Ментоловая свежесть</li></ul>"
            else -> "<ul><li>Описание отсутствует</li></ul>"
        }
    }
}