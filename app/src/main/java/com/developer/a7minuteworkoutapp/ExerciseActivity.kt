package com.developer.a7minuteworkoutapp

import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.developer.a7minuteworkoutapp.databinding.ActivityExerciseBinding
import com.google.android.material.snackbar.Snackbar
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private lateinit var binding : ActivityExerciseBinding
    private var restTimer : CountDownTimer? = null
    private var restProgress = 0
    private var restTimerDuration : Long = 2
    private var exerciseTimer : CountDownTimer? = null
    private var exerciseProgress = 0
    private var exerciseTimerDuration : Long = 3
    private var exerciseList : ArrayList<ExerciseModel>? = null
    private var currentExercisePosition = -1
    private var tts : TextToSpeech?= null
    private var player : MediaPlayer? = null
    private var exerciseAdapter : ExerciseStatusAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarExerciseActivity)
        val actionbar = supportActionBar
        if(actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true)
        }
        binding.toolbarExerciseActivity.setNavigationOnClickListener {
            showCustomDialog()

        }
        tts = TextToSpeech(this, this)
        exerciseList = Constants.defaultExerciseList()
        setUpRestView()
        setUpExerciseStatusRecyclerView()


        }

    override fun onDestroy() {
        if(restTimer != null) {
            restTimer!!.cancel()
            restProgress = 0
        }
        if(exerciseTimer != null){
            exerciseTimer!!.cancel()
            exerciseProgress = 0
        }

        if(tts != null) {
            tts!!.stop()
            tts!!.shutdown()
        }
        if(player != null) {
            player!!.stop()
        }
        super.onDestroy()

    }

    private fun setRestProgressBar(){
        binding.progressBar.progress = restProgress
        restTimer = object : CountDownTimer(restTimerDuration * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                binding.progressBar.progress = 10 - restProgress
                binding.tvTimer.text = (restTimerDuration.toInt() - restProgress).toString()
            }

            override fun onFinish() {
                currentExercisePosition++

                exerciseList!![currentExercisePosition].setIsSelected(true)
                exerciseAdapter!!.notifyDataSetChanged()
                setUpExerciseView()
            }
        }.start()
    }
    private fun setUpRestView() {

        try {
            player = MediaPlayer.create(applicationContext, R.raw.press_start)
            player!!.isLooping = false
            player!!.start()
        }catch (e:Exception) {
            e.printStackTrace()
        }

        if(restTimer != null) {
            restTimer!!.cancel()
            restProgress = 0
        }
        binding.llExerciseView.visibility = View.GONE
        binding.llRestView.visibility = View.VISIBLE
        setRestProgressBar()

        binding.tvUpcomingExercise.text = exerciseList!![currentExercisePosition + 1].getName()

    }
    private fun setExerciseProgressBar() {
        exerciseTimer = object : CountDownTimer(exerciseTimerDuration * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                exerciseProgress++
                binding.exerciseProgressBar.progress = 3 - exerciseProgress
                binding.exerciseTvTimer.text = (exerciseTimerDuration.toInt() - exerciseProgress).toString()
            }

            override fun onFinish() {
                if(currentExercisePosition < exerciseList?.size!! - 1) {
                    exerciseList!![currentExercisePosition].setIsSelected(false)
                    exerciseList!![currentExercisePosition].setIsCompleted(true)
                    exerciseAdapter!!.notifyDataSetChanged()
                    setUpRestView()
                }else {

                    val intent = Intent(this@ExerciseActivity, FinishActivity::class.java)
                    startActivity(intent)
                    finish()
                    Toast.makeText(this@ExerciseActivity,
                    "Congratulations You have completed 7 minute workout exercise",
                    Toast.LENGTH_LONG).show()
                }
            }
        }.start()
    }
    private fun setUpExerciseView() {
        if(exerciseTimer != null) {
            exerciseTimer!!.cancel()
            exerciseProgress = 0
        }
        binding.llRestView.visibility = View.GONE
        binding.llExerciseView.visibility = View.VISIBLE
        setExerciseProgressBar()
        binding.tvImage.setImageResource(exerciseList!![currentExercisePosition].getImage())
        binding.tvExerciseName.text = exerciseList!![currentExercisePosition].getName()
        speakOut(binding.tvExerciseName.text.toString())
    }

    override fun onInit(status: Int) {
        if(status == TextToSpeech.SUCCESS) {
            tts!!.setLanguage(Locale.US)
            if(status == TextToSpeech.LANG_MISSING_DATA || status == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "Language data is missing or not supported")
            }
        }else {
            Log.e("TTS", "Initialization failed")
        }
    }
    private fun speakOut(text:String) {
        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    private fun setUpExerciseStatusRecyclerView() {
        binding.rvExerciseStatus.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,
        false)
        exerciseAdapter = ExerciseStatusAdapter(exerciseList!!, this)
        binding.rvExerciseStatus.adapter = exerciseAdapter

    }
    private fun showCustomDialog() {
        val alertDialog = Dialog(this)
        alertDialog.setContentView(R.layout.dialog_custom)
        alertDialog.findViewById<Button>(R.id.btnYes).setOnClickListener {
            finish()
            alertDialog.dismiss()
        }
        alertDialog.findViewById<Button>(R.id.btnNo).setOnClickListener {
            alertDialog.dismiss()
        }
        alertDialog.show()
    }

    }
