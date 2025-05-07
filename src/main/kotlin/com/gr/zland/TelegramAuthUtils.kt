package com.gr.zland

import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

object TelegramAuthUtils {
    fun createSecretKey(botToken: String): ByteArray {
        val sha256 = MessageDigest.getInstance("SHA-256")
        return sha256.digest(botToken.toByteArray(StandardCharsets.UTF_8))
    }

    fun computeHmacSha256Base64Url(dataCheckString: String, secretKey: ByteArray): String {
        val hmac = Mac.getInstance("HmacSHA256")
        val keySpec = SecretKeySpec(secretKey, "HmacSHA256")
        hmac.init(keySpec)
        val hashBytes = hmac.doFinal(dataCheckString.toByteArray(StandardCharsets.UTF_8))
        return java.util.Base64.getUrlEncoder().withoutPadding().encodeToString(hashBytes)
    }
}
