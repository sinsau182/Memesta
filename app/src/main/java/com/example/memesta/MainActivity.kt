package com.example.memesta

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.memesta.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.progressBar

class MainActivity : AppCompatActivity() {
    var ImageUrl: String? = null
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnNext.setOnClickListener {
            loadMeme()
        }

        loadMeme()
    }
    private fun loadMeme() {

       progressBar.visibility = View.VISIBLE
        val url = "https://meme-api.com/gimme"

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                 ImageUrl = response.getString("url")
                Glide.with(this).load(ImageUrl).listener(object : RequestListener<Drawable>{
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                       progressBar.visibility = View.GONE
                        return false
                    }
                }).into(binding.imageViewMeme)
            },
    { error ->
        Toast.makeText(this, "error", Toast.LENGTH_LONG).show()
    })
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }
    fun shareMeme(view: View) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"

        intent.putExtra(
            Intent.EXTRA_TEXT,
            "Hey, Checkout this amazing meme, I got from Reddit $ImageUrl}"
        )
        val chooser = Intent.createChooser(intent, "Share this meme using...")
        startActivity(chooser)
    }


}