package com.developer.a7minuteworkoutapp

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import com.developer.a7minuteworkoutapp.databinding.ActivityFinishBinding
import com.developer.a7minuteworkoutapp.databinding.ActivityMainBinding
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class FinishActivity : AppCompatActivity() {
    private lateinit var binding : ActivityFinishBinding
    private var player : MediaPlayer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityFinishBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbarFinishActivity)
        val actionbar = supportActionBar
        if(actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true)
        }
        try {
            player = MediaPlayer.create(applicationContext, R.raw.press_start)
            player!!.isLooping = false
            player!!.start()
        }catch (e: Exception) {
            e.printStackTrace()
        }
        binding.btnFinish.setOnClickListener {
            finish()
        }
        addDateToDatabase()



    }
    private fun addDateToDatabase() {
        val calendar = Calendar.getInstance()
        val dateTime = calendar.time
        Log.i("DATE", "" + dateTime)
        val sdf = SimpleDateFormat("dd MM yyyy HH:mm:ss", Locale.getDefault())
        val date = sdf.format(dateTime)
        val dbHandler = SqlLiteOpenHelper(this, null)
        dbHandler.addDate(date)
        Log.i("Date :", "Added")
    }

}