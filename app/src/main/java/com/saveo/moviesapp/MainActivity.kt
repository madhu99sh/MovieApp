package com.saveo.moviesapp

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import com.saveo.moviesapp.ui.list.MoviesListActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val timer = object : CountDownTimer(5000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                text_counter.text = "${millisUntilFinished / 1000}"
            }

            override fun onFinish() {
                finishAffinity()
                val newIntent = Intent(applicationContext, MoviesListActivity::class.java)
                startActivity(newIntent)
            }
        }
        timer.start()


    }
}