package org.mp.customview2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        happyButton.setOnClickListener {
            smileyFaceView.happinessState = SmileyView.HAPPY
        }

        sadButton.setOnClickListener {
            smileyFaceView.happinessState = SmileyView.SAD
        }
    }
}
