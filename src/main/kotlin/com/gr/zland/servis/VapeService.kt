package com.gr.zland.servis

import com.gr.zland.dto.*
import com.gr.zland.model.*
import com.gr.zland.repository.VapeRepository
import com.gr.zland.сontroller.ImageController
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class VapeService @Autowired constructor(
    private val vapeRepository: VapeRepository,
    private val imageController: ImageController
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
        val categories = listOf(
            "Фрукты" to "icon-fruits.png",
            "Десерты" to "icon-desserts.png",
            "Классика" to "icon-classic.png",
            "Напитки" to "icon-drinks.png",
            "Растения" to "icon-plants.png",
            "Миксы" to "icon-mixes.png"
        )

        return categories.mapIndexed { index, (name, icon) ->
            CategoryDto(
                id = index.toLong(),
                icon = IconDto(
                    url = "/$icon",
                    alt = name
                ),
                link = LinkDto(
                    to = name.lowercase(),
                    label = name
                )
            )
        }
    }

    fun getCatalogBySlug(slug: String): CatalogResponse {
        val vapes = when (slug) {
            "all" -> vapeRepository.findAll()
            "top" -> vapeRepository.getTop15()
            else -> vapeRepository.findAllBySort(slug)
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
                isTop = vape.isTop15,
                parameters = listOf(
                    ParameterDto(1, "Сладость", vape.sweetness.toString()),
                    ParameterDto(2, "Холодок", vape.iceLevel.toString()),
                    ParameterDto(3, "Кислинка", vape.sourness.toString())
                )
            )
        }

        return CatalogResponse(
            seo = SeoDto(h1 = slug.replaceFirstChar { it.uppercase() }),
            specification = generateSpecification(slug),
            products = products
        )
    }

    // Пример генерации описания
    private fun generateSpecification(slug: String): String {
        return when (slug.lowercase()) {
            "фрукты" -> "<ul><li>До 900 затяжек;</li><li>35 вкусов: 1.9% солевой никотин;</li><li>Объем бака 2 мл;</li><li>Подходят для устройства Zland Mini;</li><li>Регулируемая мощность;</li><li>Двойная сетчатая спираль</li></ul>"
            "mint" -> "<ul><li>Ментоловая свежесть</li></ul>"
            else -> "<ul><li>Описание отсутствует</li></ul>"
        }
    }
    fun getTop15(): List<Vape> {
        return vapeRepository.getTop15()
    }
}