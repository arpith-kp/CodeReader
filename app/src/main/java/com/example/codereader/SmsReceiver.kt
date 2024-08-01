package com.example.codereader

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.telephony.SmsMessage
import android.util.Log
import java.util.regex.Pattern

class SmsReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == "android.provider.Telephony.SMS_RECEIVED") {
            val bundle = intent.extras
            if (bundle != null) {
                val pdus = bundle.get("pdus") as Array<*>
                val messages = pdus.map { pdu -> SmsMessage.createFromPdu(pdu as ByteArray) }
                for (message in messages) {
                    val messageBody = message.messageBody
                    val sender = message.displayOriginatingAddress

                    // Parse the message body to extract the login code
                    val loginCode = parseLoginCode(messageBody)

                    // Handle the extracted login code (e.g., send a broadcast or update UI)
                    val codeIntent = Intent("com.example.SMS_LOGIN_CODE")
                    codeIntent.putExtra("login_code", loginCode)
                    context.sendBroadcast(codeIntent)
                }
            }
        }
    }

    private fun parseLoginCode(messageBody: String): String? {
        // Implement your parsing logic here
        // For example, if the code is always a 6-digit number:
        val pattern = Pattern.compile("\\b\\d{6}\\b")
        val matcher = pattern.matcher(messageBody)
        return if (matcher.find()) {
            matcher.group(0)
        } else {
            null
        }
    }
}
