package com.example.recipeappfirebase

import android.app.Application
import android.provider.ContactsContract
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyViewModel (applicationContext : Application): AndroidViewModel(applicationContext)  {
    val db = Firebase.firestore
    val applicationContext = applicationContext

    fun addRecipe(r: RecipeDetails){
        val recipe = hashMapOf(
                "id" to r.id,
                "title" to r.title,
                "author" to r.author,
                "Ingredents" to r.ingredients,
                "Instruction" to r.instructions,
        )
        db.collection("recipes")
                .add(recipe)
                .addOnSuccessListener { documentReference ->
                    Log.d("TAG", "DocumentSnapshot added with ID: ${documentReference.id}")
                    Toast.makeText(
                            applicationContext,
                            "Save success with id:" + "${documentReference.id}", Toast.LENGTH_SHORT
                    ).show();
                }
                .addOnFailureListener { e ->
                    Log.w("TAG", "Error adding document", e)
                }
    }

    fun getRecipes():LiveData<List<String>>{
        var send = ArrayList<String>()
        var recipes : MutableLiveData<List<String>> = MutableLiveData()
        var details = "\n"
        db.collection("recipes")
                .get()
                .addOnFailureListener { exception ->
                    Log.w("TAG", "Error getting documents.", exception)
                }
                .addOnSuccessListener { result ->
                    for (document in result) {
                        Log.d("TAG", "${document.id} => ${document.data}")
                        document.data.map { (key, value)
                            ->
                            details = details +"${document.id}:" + "$key = $value \n\n"
                        }
                        details = details +"____________________________________________\n\n"
                    }
                    send.add(details)
                    recipes.postValue(send)
                }
        println("data"+send)
        return recipes
    }
}