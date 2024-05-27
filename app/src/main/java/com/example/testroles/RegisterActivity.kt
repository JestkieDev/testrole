package com.example.testroles

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {

    private lateinit var R_Et_nombre_usuario : EditText
    private lateinit var R_Et_email : EditText
    private lateinit var R_Et_password : EditText
    private lateinit var R_Et_r_password : EditText
    private lateinit var Btn_registrar : Button

    private lateinit var auth : FirebaseAuth
    private lateinit var reference : DatabaseReference

    private lateinit var progressDialog : ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        InicializarVariables()

        Btn_registrar.setOnClickListener {
            ValidarDatos()
        }
    }

    private fun InicializarVariables (){
        R_Et_nombre_usuario = findViewById(R.id.R_Et_nombre_usuario)
        R_Et_email = findViewById(R.id.R_Et_email)
        R_Et_password = findViewById(R.id.R_Et_password)
        R_Et_r_password = findViewById(R.id.R_Et_r_password)
        Btn_registrar = findViewById(R.id.Btn_registrar)
        auth = FirebaseAuth.getInstance()

        //Configurar
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Происходит регистрация")
        progressDialog.setCanceledOnTouchOutside(false)
    }

    private fun ValidarDatos() {
        val nombre_usuario : String = R_Et_nombre_usuario.text.toString()
        val email : String = R_Et_email.text.toString()
        val password : String = R_Et_password.text.toString()
        val r_password : String = R_Et_r_password.text.toString()

        if (nombre_usuario.isEmpty()){
            Toast.makeText(applicationContext, "Введите имя пользователя",Toast.LENGTH_SHORT).show()
        }
        else if (email.isEmpty()){
            Toast.makeText(applicationContext, "Введите свой адрес электронной почты",Toast.LENGTH_SHORT).show()
        }
        else if (password.isEmpty()){
            Toast.makeText(applicationContext, "Введите свой пароль",Toast.LENGTH_SHORT).show()
        }
        else if (r_password.isEmpty()){
            Toast.makeText(applicationContext, "Пожалуйста, повторите свой пароль",Toast.LENGTH_SHORT).show()
        }
        else if (!password.equals(r_password)){
            Toast.makeText(applicationContext, "Пароли не совпадают",Toast.LENGTH_SHORT).show()
        }
        else{
            RegistrarUsuario(email, password)
        }
    }

    private fun RegistrarUsuario(email: String, password: String) {
        progressDialog.setMessage("Пожалуйста, подождите")
        progressDialog.show()
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener{task->
                if (task.isSuccessful){
                    progressDialog.dismiss()
                    var uid : String = ""
                    uid = auth.currentUser!!.uid
                    reference = FirebaseDatabase.getInstance().reference.child("users").child(uid)

                    val hashmap = HashMap<String, Any>()
                    val h_username : String = R_Et_nombre_usuario.text.toString()
                    val h_email : String = R_Et_email.text.toString()

                    hashmap["uid"] = uid
                    hashmap["username"] = h_username
                    hashmap["email"] = h_email
                    hashmap["smth"] = "nothing"



                    reference.updateChildren(hashmap).addOnCompleteListener{task2->
                        if (task2.isSuccessful){
                            val intent = Intent(this@RegisterActivity, MainActivity::class.java)
                            Toast.makeText(applicationContext, "Успешная регистрация", Toast.LENGTH_SHORT).show()
                            startActivity(intent)
                        }
                    }
                        .addOnFailureListener{e->
                            Toast.makeText(applicationContext, "${e.message}", Toast.LENGTH_SHORT).show()
                        }
                }else{
                    progressDialog.dismiss()
                    Toast.makeText(applicationContext, "Произошла ошибка", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener{e->
                progressDialog.dismiss()
                Toast.makeText(applicationContext, "${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}