package com.example.socialconnectivity

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class Page2 : AppCompatActivity() {
    var currentMemeUrl: String? = null
     lateinit var nextButton: Button
   var texttoshare:String?=null
    lateinit var PostButton: ImageButton
    private lateinit var shareButton: Button
 private lateinit var progressBar: ProgressBar
    lateinit var img:ImageView
private lateinit var navigationView:BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_page2)

        nextButton=findViewById(R.id.nextmeme)
        shareButton=findViewById(R.id.sharememe)
        PostButton=findViewById(R.id.postpage)
        img=findViewById(R.id.memepic)
        progressBar=findViewById(R.id.progressbarmeme)
        PostButton.setOnClickListener{
            val intent = Intent(this, PostActivity::class.java)
            startActivity(intent)
            finish()
        }
        loadMemes()


}

    fun loadMeme(view: View) {    loadMemes()}


private fun loadMemes() {

    this.progressBar.visibility = View.VISIBLE
    val url = "https://meme-api.herokuapp.com/gimme"

    // Request a string response from the provided URL.
    val jsonObjectRequest = JsonObjectRequest(
        Request.Method.GET, url, null,
        { response ->
            currentMemeUrl = response.getString("url")

          Glide.with(this).load(currentMemeUrl).listener(object : RequestListener<Drawable>{
              override fun onLoadFailed(
                  e: GlideException?,
                  model: Any?,
                  target: Target<Drawable>?,
                  isFirstResource: Boolean
              ): Boolean {
                  progressBar.visibility = View.GONE
                  nextButton.isEnabled = true
                  shareButton.isEnabled = true
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
          }).into(img)
        },
        {
            progressBar.visibility = View.GONE
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show()
        })

    // Add the request to the RequestQueue.
    MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
}

fun showNextMeme(view: View) {
    loadMemes()
}

fun shareMeme(view: View) {
    val i = Intent(Intent.ACTION_SEND)
    i.type = "text/plain"
    i.putExtra(Intent.EXTRA_TEXT, "Hi, checkout this meme $currentMemeUrl")
    texttoshare="Hi, checkout this meme $currentMemeUrl"
    startActivity(Intent.createChooser(i, "Share this meme with"))
}

    fun logOutpage2(view: View) {
        Firebase.auth.signOut()
        val intent = Intent(this, SiginActivity::class.java)
        startActivity(intent)
        finish()
    }




}