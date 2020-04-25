package com.example.conection_firestore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.FirebaseOptions
import com.google.firebase.firestore.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val db = FirebaseFirestore.getInstance()
    lateinit var btsalvar: Button
    lateinit var btmostrar: Button
    lateinit var btdel: Button
    lateinit var btUpd: Button
    lateinit var ednome: EditText
    lateinit var edsobre: EditText
    lateinit var data_list: ListView
    private val TAG = "MainActivity"
    lateinit var UserList: ArrayList<data>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        UserList = arrayListOf()


        btsalvar = findViewById(R.id.button)
        btmostrar = findViewById(R.id.buttonmostrar)
        btdel = findViewById(R.id.buttonDel)
        btUpd = findViewById(R.id.buttonUpdate)
        ednome = findViewById(R.id.editText)
        edsobre = findViewById(R.id.editText2)
        data_list = findViewById(R.id.lista_teste)


        btsalvar.setOnClickListener {
            var nome = ednome.text.toString().trim()
            var idade = edsobre.text.toString().trim()
            val teste = hashMapOf(
                "nome" to nome,
                "idade" to idade
            )

//            db.collection("users")
//                .add(teste)
//                .addOnSuccessListener { documentReference ->
//                    Log.d("teste", "DocumentSnapshot added with ID: ${documentReference.id}")
//                }
//                .addOnFailureListener { e ->
//                    Log.w("teste", "Error adding document", e)
//                }

            //Aqui eu seto o id pelo .document
            db.collection("users")
                .document("123456")
                .set(teste)
                .addOnSuccessListener(OnSuccessListener<Void> {
                    Log.d("TAG", "DocumentSnapshot successfully written!")
                    chamar()
                })
                .addOnFailureListener(OnFailureListener { e ->
                    Log.w("te", "Error writing document", e)
                })


        }

        btmostrar.setOnClickListener {
            //mostrar individualmente
//            db.collection("users").document("123456")
//                .get()
//                .addOnSuccessListener { document ->
//                    if (document != null) {
//                        Log.d("teste", "DocumentSnapshot data: ${document.data}")
//                        //mostra no textview
//                        textViewteste.text = document.getString("nome")
//                    }
//                }
//                .addOnFailureListener { exception ->
//                    Log.w("teste", "Error getting documents.", exception)
//                }
            //ou utilizar o where
            getbyvalor()
        }

        btdel.setOnClickListener { delete() }


        chamar()
    }

    override fun onResume() {
        super.onResume()
        chamar()
    }

    fun chamar() {
        //função para mostrar todos os dados numa lista
        db.collection("users")
            .get()
            .addOnCompleteListener(OnCompleteListener<QuerySnapshot> { task ->
                if (task.isSuccessful) {
                    UserList.clear()
                    for (document in task.result!!) {
                        Log.d(TAG, document.id + " => " + document.data)
                        var idade: String? = document.getString("idade")
                        var nome: String? = document.getString("nome")
                        var data = data(idade, nome)
                        UserList.add(data)
                    }
                    val adapter = AdapterList(applicationContext, R.layout.lista_layout, UserList)
                    data_list.adapter = adapter
                } else {
                    Log.d(TAG, "Error getting documents: ", task.exception)
                }
            })
    }

    fun delete() {
        db.collection("users").document("123456")
            .delete()
            .addOnSuccessListener(OnSuccessListener<Void> {
                Log.d(TAG, "DocumentSnapshot successfully deleted!")
            })
            .addOnFailureListener(OnFailureListener { e ->
                Log.w(TAG, "Error deleting document", e)
            })
        chamar()
    }


    fun getbyvalor(){
        db.collection("users").whereEqualTo("nome", "Caio")
            .get()
            .addOnCompleteListener(OnCompleteListener<QuerySnapshot> { task ->
                if (task.isSuccessful) {
                    UserList.clear()
                    for (document in task.result!!) {
                        Log.d(TAG, document.id + " => " + document.data)
                        var idade: String? = document.getString("idade")
                        var nome: String? = document.getString("nome")
                        var data = data(idade, nome)
                        UserList.add(data)
                    }
                    val adapter = AdapterList(applicationContext, R.layout.lista_layout, UserList)
                    data_list.adapter = adapter
                } else {
                    Log.d(TAG, "Error getting documents: ", task.exception)
                }
            })
    }
}


