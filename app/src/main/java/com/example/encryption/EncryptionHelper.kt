package com.example.encryption

import android.util.Base64
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

class EncryptionHelper(private val key: String) {

    fun encrypt(textToEncrypt: String): String {
        return try {
            val ivByte = ByteArray(16)
            SecureRandom().nextBytes(ivByte)
            val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
            val secretKeySpec = SecretKeySpec(key.toByteArray(), "AES")
            val iv = IvParameterSpec(ivByte)

            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, iv)
            val encryptedData = cipher.doFinal(textToEncrypt.toByteArray())
            val combine = ivByte + encryptedData
            Base64.encodeToString(combine, Base64.DEFAULT)

        } catch (e: Exception) {
            e.printStackTrace().toString()
        }
    }

    fun decrypt(textToDecrypt: String): String {
        return try {
            val combined = Base64.decode(textToDecrypt, Base64.DEFAULT)
            val ivByte = combined.copyOfRange(fromIndex = 0, toIndex = 16)
            val encryptedData = combined.copyOfRange(fromIndex = 16, combined.size)
            val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
            val secretKeySpec = SecretKeySpec(key.toByteArray(), "AES")
            val iv = IvParameterSpec(ivByte)

            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, iv)
            val decryptedByte = cipher.doFinal(encryptedData)
            String(decryptedByte)

        } catch (e: Exception) {
            e.printStackTrace().toString()
        }
    }
}