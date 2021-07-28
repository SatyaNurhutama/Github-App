package com.satya.consumerapp.activity

import android.database.ContentObserver
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.satya.consumerapp.R
import com.satya.consumerapp.adapter.FavoriteAdapter
import com.satya.consumerapp.db.DatabaseContract.FavoriteColumns.Companion.CONTENT_URI
import com.satya.consumerapp.entity.Favorite
import com.satya.consumerapp.helper.MappingHelper
import kotlinx.android.synthetic.main.activity_favorite.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FavoriteActivity : AppCompatActivity() {

    private lateinit var adapter: FavoriteAdapter

    companion object {
        private const val EXTRA_STATE = "EXTRA_STATE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        val actionBar = supportActionBar
        actionBar!!.title = getString(R.string.favorite_title)

        rv_fav.layoutManager = LinearLayoutManager(this)
        rv_fav.setHasFixedSize(true)
        adapter = FavoriteAdapter()
        rv_fav.adapter = adapter

        val handlerThread = HandlerThread("DataObserver")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)

        val myObserver = object : ContentObserver(handler) {
            override fun onChange(self: Boolean) {
                loadFavAsync()
            }
        }

        contentResolver.registerContentObserver(CONTENT_URI, true, myObserver)

        if (savedInstanceState == null) {
            loadFavAsync()
        } else {
            val list = savedInstanceState.getParcelableArrayList<Favorite>(EXTRA_STATE)
            if (list != null) {
                adapter.listFav = list
            }
        }
    }

    private fun loadFavAsync() {
        GlobalScope.launch(Dispatchers.Main) {
            progressBar.visibility = View.VISIBLE
            val deferredNotes = async(Dispatchers.IO) {
                val cursor = contentResolver.query(CONTENT_URI, null, null, null, null)
                MappingHelper.mapCursorToArrayList(cursor)
            }
            progressBar.visibility = View.INVISIBLE
            val favData = deferredNotes.await()

            if (favData.size > 0) {
                adapter.listFav = favData
            } else {
                adapter.listFav = ArrayList()
                emptyMessage()
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_STATE, adapter.listFav)
    }

    override fun onResume() {
        super.onResume()
        loadFavAsync()
    }

    private fun emptyMessage() {
        Toast.makeText(this, getString(R.string.favorite_empty), Toast.LENGTH_SHORT).show()
    }
}