package com.gr.zland.сontroller


import com.gr.zland.dto.*
import com.gr.zland.model.Winner
import com.gr.zland.servis.RaffleSettingsService
import com.gr.zland.servis.WinnerService
import com.gr.zland.servis.myTelegramUserService
import jakarta.persistence.OptimisticLockException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import java.time.LocalTime

@RestController
@RequestMapping("/apiZ/raffle-settings")
class RaffleSettingsController (
    private val raffleSettingsService: RaffleSettingsService,
    private val userService: myTelegramUserService,
    private val winnerService: WinnerService
) {
    @GetMapping
    fun getSpinnerData(@AuthenticationPrincipal user: UserDetails): SpinnerResponseDto {
        val settings = raffleSettingsService.getLatest()
        val telegramUser = userService.getTelegramUserById(user.username.toLong())
        val spins = if (telegramUser!=null) telegramUser.spins?:20 else 0
        return SpinnerResponseDto(
            isEnded = settings.maxPrizes <= 0,
            spinner = SpinnerData(
                chanceToWin = settings.probability,
                maxSpins = spins
            ),
            prize = PrizeData(
                image = PrizeImage(
                    url = settings.prizeImagePath ?: "",
                    alt = settings.prize
                ),
                title = settings.prize,
                message = "Поздравляем с выигрышем! Наш менеджер свяжется с вами, чтобы уточнить детали доставки"
            ),
            theEnd = TheEndData(
                title = "Розыгрыш завершился",
                description ="Все призы уже разыграны. Следующий розыгрыш не за горами — не упусти шанс снова испытать удачу"
            ),
            seo = SeoData(
                H1 = "Вращайте барабан и выигрывайте призы!"
            )
        )
    }
    @PostMapping("/update-spins")
    fun updateSpins(
        @AuthenticationPrincipal user: UserDetails,
        @RequestBody request: UpdateSpinsRequestDto
    ): ResponseEntity<UpdateSpinsResponseDto> {
        val settings = raffleSettingsService.getLatest()
        val telegramUser = userService.getTelegramUserById(user.username.toLong())
            ?: return ResponseEntity.badRequest().body(
                UpdateSpinsResponseDto(
                    success = false,
                    isEnded = settings.maxPrizes <= 0,
                    message = "Пользователь не найден"
                )
            )


        if (settings.maxPrizes <= 0) {
            return ResponseEntity.ok(
                UpdateSpinsResponseDto(
                    success = false,
                    isEnded = true,
                    message = "Розыгрыш завершён"
                )
            )
        }

        val currentSpins = telegramUser.spins ?: 20
        if (request.countSpins > currentSpins) {
            return ResponseEntity.badRequest().body(
                UpdateSpinsResponseDto(
                    success = false,
                    isEnded = settings.maxPrizes <= 0,
                    message = "Превышено максимальное количество спинов"
                )
            )
        }

        telegramUser.spins = request.countSpins
        if (request.isWin) {
            try {
                raffleSettingsService.decrementPrizesIfWin(settings)
                val winner = Winner(
                    phone = "",
                    telegramNick = telegramUser.username ?: "",
                    date = LocalDate.now(),
                    time = LocalTime.now(),
                    prize = settings.prize,
                    createdAt = System.currentTimeMillis(),
                    updatedAt = System.currentTimeMillis()
                )
                winnerService.create(winner)
            } catch (e: OptimisticLockException) {
                return ResponseEntity.status(409).body(
                    UpdateSpinsResponseDto(
                        success = false,
                        isEnded = true,
                        message = "Кто-то успел раньше — призов больше нет"
                    )
                )
            }
        }
        userService.save(telegramUser)

        return ResponseEntity.ok(
            UpdateSpinsResponseDto(
                success = true,
                isEnded = settings.maxPrizes <= 0,
                countSpins = telegramUser.spins,
                message = "Количество спинов успешно обновлено"
            )
        )
    }

}
data class UpdateSpinsRequestDto(
    val countSpins: Int,
    val isWin: Boolean
)


data class UpdateSpinsResponseDto(
    val success: Boolean,
    val isEnded: Boolean,
    val countSpins: Int? = null,
    val message: String? = null
)