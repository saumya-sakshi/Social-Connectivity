package com.example.socialconnectivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Button
import android.widget.EditText
import com.example.socialconnectivity.daos.PostDao
import com.example.socialconnectivity.Page2

class CreatePostActivity : AppCompatActivity() {
    private lateinit var postDao: PostDao
    private lateinit var postButton:Button
    private lateinit var postInput:EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_post)



        postDao = PostDao()
        postButton=findViewById(R.id.postButton)
        postInput=findViewById(R.id.postInput)


        postButton.setOnClickListener {
            val input = postInput.text.toString().trim()
            if(input.isNotEmpty()) {
                postDao.addPost(input)
                finish()
            }
        }

        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {

    }
}