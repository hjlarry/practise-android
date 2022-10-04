package com.example.broadcasttest

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.example.broadcasttest.databinding.ActivityLoginBinding

class LoginActivity : BaseActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginBtn.setOnClickListener {
            val account = binding.editAccount.text.toString()
            val pwd = binding.editPwd.text.toString()

            if(account == "admin" && pwd == "123"){
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "incorrect pwd", Toast.LENGTH_SHORT).show()
            }
        }
    }
}