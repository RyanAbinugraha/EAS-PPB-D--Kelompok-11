package com.example.myhabit.auth
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myhabit.databinding.ActivityLoginBinding
import com.example.myhabit.home.HomeActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.loginBtn.setOnClickListener{
            val email=binding.emailEdit.text.toString().trim()
            val pw=binding.passwordEdit.text.toString().trim()
            if(email.isEmpty()||pw.isEmpty()){
                Toast.makeText(this,"Enter credentials",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            FirebaseAuth.getInstance()
                .signInWithEmailAndPassword(email,pw)
                .addOnSuccessListener{
                    startActivity(Intent(this,HomeActivity::class.java))
                    finish()
                }
                .addOnFailureListener{
                    Toast.makeText(this,it.localizedMessage,Toast.LENGTH_LONG).show()
                }
        }
        binding.registerNowText.setOnClickListener{
            startActivity(Intent(this,RegisterActivity::class.java))
        }
    }
}