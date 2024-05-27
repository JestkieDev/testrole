package com.example.testroles

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var L_Et_email : EditText
    private lateinit var L_Et_password : EditText
    private lateinit var Txt_olvide_password : TextView
    private lateinit var Btn_login : Button
    private lateinit var auth : FirebaseAuth
    private lateinit var TXT_ir_registro : TextView
    private lateinit var progressDialog : ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //supportActionBar!!.title = "Login"
        InicializarVariables()

        Btn_login.setOnClickListener {
            ValidarDatos()
        }

        TXT_ir_registro.setOnClickListener {
            val intent = Intent(this@MainActivity, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun InicializarVariables(){
        L_Et_email = findViewById(R.id.L_Et_email)
        L_Et_password = findViewById(R.id.L_Et_password)
        Btn_login = findViewById(R.id.Btn_login)
        auth = FirebaseAuth.getInstance()
        TXT_ir_registro = findViewById(R.id.TXT_ir_registro)


        //Configurar progressDialog
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Вход в систему")
        progressDialog.setCanceledOnTouchOutside(false)
    }

    private fun ValidarDatos() {
        val email : String = L_Et_email.text.toString()
        val password : String = L_Et_password.text.toString()

        if (email.isEmpty()){
            Toast.makeText(applicationContext, "Введите свой адрес электронной почты", Toast.LENGTH_SHORT).show()
        }
        if (password.isEmpty()){
            Toast.makeText(applicationContext, "Введите свой пароль", Toast.LENGTH_SHORT).show()
        }
        else{
            LoginUsuario(email, password)
        }

    }

    private fun LoginUsuario(email: String, password: String) {
        progressDialog.setMessage("Пожалуйста, подождите")
        progressDialog.show()
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener{task->
                if (task.isSuccessful){
                    progressDialog.dismiss()
                    val intent = Intent(this@MainActivity, BaseActivity::class.java)
                    Toast.makeText(applicationContext, "Вы вошли в систему", Toast.LENGTH_SHORT).show()
                    startActivity(intent)
                    finish()
                }else{
                    progressDialog.dismiss()
                    Toast.makeText(applicationContext, "Произошла ошибка", Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener{e->
                progressDialog.dismiss()
                Toast.makeText(applicationContext, "{${e.message}}", Toast.LENGTH_SHORT).show()
            }
    }
}