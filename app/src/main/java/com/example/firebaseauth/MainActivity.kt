package com.example.firebaseauth

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.firebaseauth.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private var auth:FirebaseAuth= FirebaseAuth.getInstance()

    private var btn:Button?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding =ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        btn=findViewById(R.id.btnSignup)
        btn?.setOnClickListener {

            Toast.makeText(this,"SignUp Screen",Toast.LENGTH_SHORT).show()
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }

        binding.btnLogIn.setOnClickListener {

            if(binding.edtEmail.text.toString().isNullOrEmpty()){

                binding.tilEmail.isErrorEnabled=true
                binding.tilEmail.error= "Enter Email"
            }
            else if (binding.edtPassword.text.toString().isNullOrEmpty()){
                binding.tilPassword.isErrorEnabled=true
                binding.edtPassword.error="Enter Password"
            }
            else{

                auth.signInWithEmailAndPassword(
                    binding.edtEmail.text.toString(),
                    binding.edtPassword.text.toString()
                )

                    .addOnCompleteListener(this){ task ->
                        val user = auth.currentUser

                        if (task.isSuccessful){

                            Log.d(TAG, "signInWithEmail:success")
                            val user = auth.currentUser


                            startActivity(Intent(this, Home::class.java))
                        }
                        else{

                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.exception)
                            Toast.makeText(
                                baseContext,
                                "Authentication failed.",
                                Toast.LENGTH_SHORT,
                            ).show()


                            Toast.makeText(this,"SignUp Failed${user?.email}",Toast.LENGTH_SHORT ).show()
                        }

                    }

            }
        }
    }




}