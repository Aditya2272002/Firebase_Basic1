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

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val etLoginEmail = findViewById<EditText>(R.id.etLoginEmail)
        val etLoginPassword = findViewById<EditText>(R.id.etLoginPassword)
        val goToSignUp = findViewById<TextView>(R.id.goToSignUp)

        auth = FirebaseAuth.getInstance()

        btnLogin.setOnClickListener {
            when {
                TextUtils.isEmpty(etLoginEmail.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this,
                        "Please enter email.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                TextUtils.isEmpty(etLoginPassword.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this,
                        "Please enter password.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                    val email = etLoginEmail.text.toString()
                    val pass = etLoginPassword.text.toString()
                    auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(this) {
                        if (it.isSuccessful) {
                            val firebaseUser: FirebaseUser = it.result!!.user!!
                            Toast.makeText(this, "Successfully LoggedIn", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this, MainActivity::class.java)
                            intent.flags =
                                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            intent.putExtra("user-id", firebaseUser.uid)
                            intent.putExtra("email-id", email)
                            startActivity(intent)
                            finish()
                        } else
                            Toast.makeText(this, "Log In failed ", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        goToSignUp.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}