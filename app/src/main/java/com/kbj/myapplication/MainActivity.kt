package com.kbj.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.squareup.picasso.Picasso
import kotlin.random.Random
import android.content.SharedPreferences
import android.widget.TextView
import java.io.IOException


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val prefs = getSharedPreferences("myapp", MODE_PRIVATE)
        val noNetText = findViewById<View>(R.id.noNet) as TextView;
        val name =
            prefs.getInt("id", 1) //"No name defined" is the default value.
        try {
            if (!isConnected()){
                noNetText.text = "No Internet connection Available"
            }
            Picasso.get()
                .load("https://placekitten.com/400/600?image=$name")
                .into(findViewById<View>(R.id.imageView) as ImageView)
        } catch (e: Exception) {
            noNetText.text = "No Internet connection Available"
        }
        findViewById<View>(R.id.button).setOnClickListener {
            try {
                if (!isConnected()){
                    noNetText.text = "No Internet connection Available"
                }
                val ran = Random.nextInt(1,16);
                val editor = getSharedPreferences("myapp", MODE_PRIVATE).edit()
                editor.putInt("id", ran)
                editor.apply()
                Picasso.get()
                    .load("https://placekitten.com/400/600?image=${ran}")
                    .into(findViewById<View>(R.id.imageView) as ImageView)
            } catch (e: Exception) {
                noNetText.text = "No Internet connection Available"
            }
        }
    }

    @Throws(InterruptedException::class, IOException::class)
    fun isConnected(): Boolean {
        val command = "ping -c 1 google.com"
        return Runtime.getRuntime().exec(command).waitFor() == 0
    }
}