package com.example.testingapp

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val btnRegister = findViewById<Button>(R.id.btnRegister)
        val etRegisterEmail = findViewById<EditText>(R.id.etRegisterEmail)
        val etRegisterPassword = findViewById<EditText>(R.id.etRegisterPassword)
        val tvRedirectLogin = findViewById<TextView>(R.id.tvRedirectLogin)

        auth = FirebaseAuth.getInstance()

        btnRegister.setOnClickListener {
            when {
                TextUtils.isEmpty(etRegisterEmail.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this,
                        "Please enter email.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                TextUtils.isEmpty(etRegisterPassword.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this,
                        "Please enter password.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                else -> {
                    val email: String = etRegisterEmail.text.toString().trim { it <= ' ' }
                    val password: String = etRegisterPassword.text.toString().trim { it <= ' ' }

                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this) {
                            if (it.isSuccessful) {
                                val firebaseUser : FirebaseUser = it.result!!.user!!
                                Toast.makeText(this, "Successfully Signed Up", Toast.LENGTH_SHORT).show()
                                val intent = Intent(this,MainActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                intent.putExtra("user-id",firebaseUser.uid)
                                intent.putExtra("email-id",email)
                                startActivity(intent)
                                finish()
                            } else {
                                Toast.makeText(this, "Signed Up Failed!", Toast.LENGTH_SHORT).show()
                            }
                        }
                }
            }
        }

        tvRedirectLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}