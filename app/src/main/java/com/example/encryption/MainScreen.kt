package com.example.encryption

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

const val KEY = "eHkFj34jaRb3f2N2"

@SuppressLint("UnrememberedMutableState")
@Composable
fun MainScreen() {
    val encryptionHelper = remember { EncryptionHelper(key = KEY) }
    var textToEncrypt by rememberSaveable { mutableStateOf("") }

    val encryptedText by derivedStateOf {
        if (textToEncrypt.isNotBlank()) {
            encryptionHelper.encrypt(textToEncrypt)
        } else ""
    }

    val decryptedText by derivedStateOf {
        if (encryptedText.isNotBlank()) {
            encryptionHelper.decrypt(encryptedText)
        } else ""
    }

    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp, vertical = 50.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        Text(text = "Encryption Demo", fontSize = 22.sp, color = Color.White)
        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = textToEncrypt,
            onValueChange = { newText ->
                textToEncrypt = newText
            },
            label = { Text(text = "Enter text to encrypt") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                }
            )
        )

        Spacer(modifier = Modifier.height(20.dp))

        if (encryptedText.isNotEmpty()) {
            Text(
                text = "Encrypted text:\n$encryptedText",
                color = Color.White,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        if (decryptedText.isNotEmpty()) {
            Text(
                text = "Decrypted text:\n$decryptedText",
                color = Color.White,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
