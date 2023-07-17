package com.example.shoetap.models

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Prefs( val context: Context) {
    val gson = Gson()
    private val shared_db_name= "MyShoesDB"
    private val storage = context.getSharedPreferences( shared_db_name, 0)

    fun saveShoe( shoe:Shoe){
        var name = shoe.name.toString()
        var json = gson.toJson(shoe)

        storage.edit().putString( name, json).apply()
    }

    fun getShoeList( key: String): ArrayList<Shoe> {
        var json_get = storage.getString(key, null)
        val type = object : TypeToken<ArrayList<Shoe>>() {}.type

       return gson.fromJson(json_get, type)?: ArrayList()
    }
}