package com.aqua.aquascape

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class Login : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnlog.setOnClickListener {
            val email = txtusername.text.toString()
            val password = txtpassword.text.toString()

            if (email.isEmpty() || password.isEmpty()){
                Toast.makeText(this, "Mohon Pastikan Semua Field Terisi", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(this, "Login Berhasil", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, SplashScreen::class.java)
                        startActivity(intent)
                        finish()
                    }else{
                        Toast.makeText(this, "Cek Email atau Password", Toast.LENGTH_LONG).show()
                    }
                }
        }

       btnregis.setOnClickListener {
            val intent = Intent(this, Registrasi::class.java)
          startActivity(intent)
      }
    }
    }
