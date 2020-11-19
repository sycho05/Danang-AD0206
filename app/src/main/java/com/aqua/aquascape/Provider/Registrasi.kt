package com.aqua.aquascape

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_registrasi.*

class Registrasi : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrasi)

        FirebaseDatabase.getInstance().reference.child("Users")
        btnbuat.setOnClickListener{
            val nama = txtnama.text.toString()
            val email = txtemail.text.toString()
            val pass = txtpass.text.toString()

            if (nama.isEmpty() || email.isEmpty() || pass.isEmpty()){
                Toast.makeText(this, "Mohon Pastikan Semua Field Terisi", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener {
                    if(!it.isSuccessful) return@addOnCompleteListener

                    val userId = FirebaseAuth.getInstance().currentUser!!.uid

                    val currentUserDb = FirebaseDatabase.getInstance().reference.child("Users").child(userId)
                    currentUserDb.child("nama").setValue(nama)

                    Toast.makeText(this, "Registrasi Berhasil !", Toast.LENGTH_SHORT).show()
                    finish()
                }
                .addOnFailureListener {

                    Toast.makeText(this, "Registrasi Gagal : ${it.message}", Toast.LENGTH_SHORT).show()
                }
        }

    }

}