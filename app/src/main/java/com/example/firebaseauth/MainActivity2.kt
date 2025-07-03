package com.example.firebaseauth

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.firebaseauth.databinding.ActivityMain2Binding
import com.google.firebase.auth.FirebaseAuth

class MainActivity2 : AppCompatActivity() {

    lateinit var binding: ActivityMain2Binding
    private  var auth: FirebaseAuth= FirebaseAuth.getInstance()

    private var txt:TextView?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding=ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        txt=findViewById(R.id.tv2)
        txt?.setOnClickListener {
            Toast.makeText( this,"SignIn Screen", Toast.LENGTH_SHORT).show()
            val intent= Intent(this,MainActivity::class.java)
            startActivity(intent)
        }



        binding.btnSignup.setOnClickListener {

            if(binding.edtEmail.text.toString().isNullOrEmpty()){

                binding.tilEmail.isErrorEnabled=true
                binding.tilEmail.error= "Enter Email"
            }
            else if (binding.edtPassword.text.toString().isNullOrEmpty()){
                binding.tilPassword.isErrorEnabled=true
                binding.edtPassword.error="Enter Password"
            }
            else{

                auth.createUserWithEmailAndPassword(
                    binding.edtEmail.text.toString(),
                    binding.edtPassword.text.toString()
                )

                    .addOnCompleteListener(this){ task ->
                        val user = auth.currentUser

                        if (task.isSuccessful){

                            Toast.makeText(this,"SignUp Successful${user?.email}",Toast.LENGTH_SHORT ).show()
                            startActivity(Intent(this, Home::class.java))
                        }
                        else{
                            Toast.makeText(this,"SignUp Failed${user?.email}",Toast.LENGTH_SHORT ).show()
                        }

                    }

            }
        }

    }
}