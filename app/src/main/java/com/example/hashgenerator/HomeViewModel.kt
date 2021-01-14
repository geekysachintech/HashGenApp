package com.example.hashgenerator

import androidx.lifecycle.ViewModel
import java.security.MessageDigest

class HomeViewModel: ViewModel ()
{
    fun getHash(plainText: String, algorithmType: String) : String
    {
        val bytes = MessageDigest.getInstance(algorithmType).digest(plainText.toByteArray())
        return toHex(bytes)
    }

    private fun toHex(byteArray: ByteArray): String
    {
        return byteArray.joinToString("") { "%02x".format(it) }
    }
}