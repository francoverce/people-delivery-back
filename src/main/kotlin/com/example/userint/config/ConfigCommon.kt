package com.example.userint.config

import org.springframework.context.annotation.Configuration
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*

@Configuration
class ConfigCommon {
    fun hashWith256(textToHash: String?): String? {
        try {
            val digest: MessageDigest = MessageDigest.getInstance("SHA-256")
            val byteOfTextToHash =
                textToHash!!.toByteArray(StandardCharsets.UTF_8)
            val hashedByetArray = digest.digest(byteOfTextToHash)
            return Base64.getEncoder().encodeToString(hashedByetArray)
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }
        return null
    }
}
