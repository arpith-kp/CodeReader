package com.example.codereader

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private lateinit var loginCodeTextView: TextView
    private lateinit var loginCodeReceiver: BroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loginCodeTextView = findViewById(R.id.login_code_text_view)

        loginCodeReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                val loginCode = intent.getStringExtra("login_code")
                loginCode?.let {
                    loginCodeTextView.text = it
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        registerReceiver(loginCodeReceiver, IntentFilter("com.example.SMS_LOGIN_CODE"))
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(loginCodeReceiver)
    }
}