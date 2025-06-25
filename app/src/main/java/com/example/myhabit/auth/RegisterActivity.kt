package com.example.myhabit.auth
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myhabit.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.registerBtn.setOnClickListener {
            val email = binding.emailEdit.text.toString().trim()
            val pw = binding.passwordEdit.text.toString().trim()
            if (email.isEmpty() || pw.length<6) {
                Toast.makeText(this, "Email valid & password >=6 chars", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(email,pw)
                .addOnSuccessListener {
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                }
                .addOnFailureListener{
                    Toast.makeText(this, it.localizedMessage, Toast.LENGTH_LONG).show()
                }
        }
        binding.loginNowText.setOnClickListener{
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}