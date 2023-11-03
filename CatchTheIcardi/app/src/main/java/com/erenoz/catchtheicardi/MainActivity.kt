package com.erenoz.catchtheicardi

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.erenoz.catchtheicardi.databinding.ActivityMainBinding
import kotlinx.coroutines.Runnable
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var score = 0
    val imageArray = ArrayList<ImageView>()
    var runnable = Runnable {}
    var handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        imageArray.add(binding.imageView)
        imageArray.add(binding.imageView2)
        imageArray.add(binding.imageView3)
        imageArray.add(binding.imageView4)
        imageArray.add(binding.imageView5)
        imageArray.add(binding.imageView6)
        imageArray.add(binding.imageView7)
        imageArray.add(binding.imageView8)
        imageArray.add(binding.imageView9)

        for (image in imageArray) {
            image.visibility = View.INVISIBLE
        }

        binding.timeText.visibility = View.INVISIBLE
        binding.scoreText.visibility = View.INVISIBLE

    }

    fun ready(view: View) {

        hideImages()

        binding.timeText.visibility = View.VISIBLE
        binding.scoreText.visibility = View.VISIBLE
        binding.button.visibility = View.INVISIBLE

        object : CountDownTimer(15500,1000) {
            override fun onTick(millisUntilFinished: Long) {
                binding.timeText.text = "Time: ${millisUntilFinished / 1000}"
            }

            override fun onFinish() {
                binding.timeText.text = "Time: 0"
                handler.removeCallbacks(runnable)

                for (image in imageArray) {
                    image.visibility = View.INVISIBLE
                }

                val alertDialog = AlertDialog.Builder(this@MainActivity)
                alertDialog.setTitle("GAME OVER!")
                alertDialog.setMessage("Restart the game ?")
                alertDialog.setPositiveButton("Yes",DialogInterface.OnClickListener { dialog, which ->
                    val intentFromMain = intent
                    finish()
                    startActivity(intentFromMain)
                })
                alertDialog.setNegativeButton("No",DialogInterface.OnClickListener { dialog, which ->
                    Toast.makeText(this@MainActivity, "Game Over!", Toast.LENGTH_LONG)
                })
                    .show()
            }

        }.start()
    }

    fun hideImages() {

        runnable = object : Runnable {
            override fun run() {
                for (image in imageArray) {
                    image.visibility = View.INVISIBLE
                }
                val random = java.util.Random()
                val randomIndex = random.nextInt(9)
                imageArray[randomIndex].visibility = View.VISIBLE

                handler.postDelayed(runnable,500)

            }

        }
        handler.post(runnable)


    }

    fun increaseScore (view : View) {
        score += 1
        binding.scoreText.text = "Score: ${score}"
    }
}