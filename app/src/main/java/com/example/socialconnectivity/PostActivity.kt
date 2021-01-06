package com.example.socialconnectivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.socialconnectivity.daos.PostDao
import com.example.socialconnectivity.models.Post
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.Query
import com.google.firebase.ktx.Firebase

class PostActivity : AppCompatActivity(), IPostAdapter  {
    private lateinit var fab:FloatingActionButton
    private lateinit var postDao: PostDao
    private lateinit var adapter: PostAdapter
    private lateinit var recyclerView:RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)
        fab=findViewById(R.id.fab)
        recyclerView=findViewById(R.id.recycleView)
        fab.setOnClickListener{
            val intent = Intent(this, CreatePostActivity::class.java)
            startActivity(intent)
        }
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        postDao = PostDao()
        val postsCollections = postDao.postCollections
        val query = postsCollections.orderBy("createdAt", Query.Direction.DESCENDING)
        val recyclerViewOptions = FirestoreRecyclerOptions.Builder<Post>().setQuery(query, Post::class.java).build()

        adapter = PostAdapter(recyclerViewOptions, this)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    fun loadMemePage(view: View) {
        val intent = Intent(this, Page2::class.java)
        startActivity(intent)
    }
    fun loadPostPage(view: View) {
        val intent = Intent(this, PostActivity::class.java)
        startActivity(intent)
    }
    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }

    override fun onLikeClicked(postId: String) {
        postDao.updateLikes(postId)
    }

    fun logoutpagePost(view: View) {
        Firebase.auth.signOut()
        val intent = Intent(this, SiginActivity::class.java)
        startActivity(intent)
        finish()
    }


}