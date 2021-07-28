package com.satya.githubuser.activity

import android.content.ContentValues
import android.database.Cursor
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.satya.githubuser.R
import com.satya.githubuser.adapter.SectionsPagerAdapter
import com.satya.githubuser.db.DatabaseContract
import com.satya.githubuser.db.FavoriteHelper
import com.satya.githubuser.entity.User
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity(),View.OnClickListener {

    private lateinit var favHelper: FavoriteHelper
    private var checkFav = false

    companion object {
        const val EXTRA_DATA = "extra_data"
        const val EXTRA_FAV = "extra_fav"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        supportActionBar?.hide()

        favHelper = FavoriteHelper.getInstance(applicationContext)
        favHelper.open()

        val usernameData = intent.getParcelableExtra<User>(EXTRA_DATA) as User
        val cursor: Cursor = favHelper.queryById(usernameData.username.toString())

        if (cursor.moveToNext()) {
            checkFav = true
            statusFav(true)
        }

        dataDetail()
        viewPager()

        fab_fav.setOnClickListener(this)
    }

    private fun dataDetail() {
        val userDetail = intent.getParcelableExtra<User>(EXTRA_DATA) as User
        tv_detail_name.text = userDetail.name
        Glide.with(this)
            .load(userDetail.photo)
            .into(img_detail_photo)
        tv_username.text = userDetail.username
        tv_detail_company.text = userDetail.company
        tv_location.text = userDetail.location
        tv_repository.text = userDetail.repository
        tv_followers.text = userDetail.followers
        tv_following.text = userDetail.following
    }

    private fun statusFav(state: Boolean){
        if(state){
            fab_fav.setImageResource(R.drawable.baseline_favorite_white_18dp)
        }else{
            fab_fav.setImageResource(R.drawable.baseline_favorite_border_white_18dp)
        }
    }

    override fun onClick(view: View?) {
        val user = intent.getParcelableExtra<User>(EXTRA_DATA) as User
        when (view?.id) {
            R.id.fab_fav -> {
                if (checkFav) {
                    val id = user.username.toString()
                    favHelper.deleteById(id)
                    Toast.makeText(this, R.string.remove_fav, Toast.LENGTH_SHORT).show()
                    statusFav(false)
                    checkFav = true
                } else {
                    val values = ContentValues()
                    values.put(DatabaseContract.FavoriteColumns.USERNAME, user.username)
                    values.put(DatabaseContract.FavoriteColumns.NAME, user.name)
                    values.put(DatabaseContract.FavoriteColumns.PHOTO, user.photo)
                    values.put(DatabaseContract.FavoriteColumns.COMPANY, user.company)
                    values.put(DatabaseContract.FavoriteColumns.LOCATION, user.location)
                    values.put(DatabaseContract.FavoriteColumns.REPOSITORY, user.repository)
                    values.put(DatabaseContract.FavoriteColumns.FOLLOWERS, user.followers)
                    values.put(DatabaseContract.FavoriteColumns.FOLLOWING, user.following)
                    values.put(DatabaseContract.FavoriteColumns.FAVORITE, "fav")

                    checkFav = false
                    contentResolver.insert(DatabaseContract.FavoriteColumns.CONTENT_URI, values)
                    Toast.makeText(this, R.string.add_fav, Toast.LENGTH_SHORT).show()
                    statusFav(true)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        favHelper.close()
    }

    private fun viewPager() {
        val viewPagerDetailAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        view_pager.adapter = viewPagerDetailAdapter
        tabs_layout.setupWithViewPager(view_pager)
    }
}
