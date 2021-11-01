package com.example.recipeappfirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private val myViewModel by lazy { ViewModelProvider(this).get(MyViewModel::class.java) }
    lateinit var title:EditText
    lateinit var name:EditText
    lateinit var Ingredents:EditText
    lateinit var Instruction:EditText
    lateinit var savebtn: Button
    lateinit var view: Button
    lateinit var tv: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val db = Firebase.firestore

        title = findViewById<EditText>(R.id.edTitle)
        name = findViewById<EditText>(R.id.edName)
        Ingredents = findViewById<EditText>(R.id.edIngredents)
        Instruction = findViewById<EditText>(R.id.edInstruction)
        savebtn = findViewById<Button>(R.id.btsave)
        view = findViewById<Button>(R.id.btview)
        tv = findViewById(R.id.tvList)

        view.setOnClickListener {
            view()
        }
        savebtn.setOnClickListener {
            save()
        }
    }
    fun save(){
        if (title.text.isEmpty() ||
                name.text.isEmpty() ||
                Ingredents.text.isEmpty() ||
                Instruction.text.isEmpty()) {
            Toast.makeText(applicationContext, "Fill all field please!! ", Toast.LENGTH_LONG).show()
            println("Fill all field please!! ")
        } else {
            var Recipe = RecipeDetails(
                    0,
                    title.text.toString(),
                    name.text.toString(),
                    Ingredents.text.toString(),
                    Instruction.text.toString()
            )
            myViewModel.addRecipe(Recipe)
            Toast.makeText(applicationContext, "data saved successfully! ", Toast.LENGTH_SHORT)
                    .show()
            title.setText("")
            name.setText("")
            Ingredents.setText("")
            Instruction.setText("")
        }
    }
    fun view(){
        myViewModel.getRecipes().observe(this,{
            recipes ->
            for (i in recipes){
                tv.text = i +"\n"
            }
        //tv.text = recipes.toString()
        })
    }
}


//retrive in main activity
//            db.collection("recipes")
//                .get()
//                .addOnFailureListener { exception ->
//                    Log.w("TAG", "Error getting documents.", exception)
//                }
//                .addOnSuccessListener { result ->
//                    var details = "\n"
//                    for (document in result) {
//                        Log.d("TAG", "${document.id} => ${document.data}")
//                        document.data.map { (key, value)
//                            ->
//                            details = details +"${document.id}:" + "$key = $value \n\n"
//                        }
//                        details = details +"____________________________________________\n\n"
//                    }
//                    tv.text =details
//                }

//fun view(){
//     val recipes = myViewModel.getRecipes()
//     println(recipes)
//     println("fsdf")
// tv.text = recipes.toString()
//
//}