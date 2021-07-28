package com.satya.githubuser.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.satya.githubuser.BuildConfig
import com.satya.githubuser.R
import com.satya.githubuser.activity.DetailActivity
import com.satya.githubuser.adapter.FollowersAdapter
import com.satya.githubuser.entity.Favorite
import com.satya.githubuser.entity.User
import cz.msebera.android.httpclient.Header
import kotlinx.android.synthetic.main.fragment_followers.*
import org.json.JSONArray
import org.json.JSONObject

class FollowersFragment : Fragment() {

    private val apiKey : String = BuildConfig.GithubApiKey
    private var listUser: ArrayList<User> = ArrayList()
    private lateinit var adapter: FollowersAdapter
    private var favorites: Favorite? = null
    private lateinit var userFavorite: Favorite
    private lateinit var userDetail: User

    companion object {
        private val TAG = FollowersFragment::class.java.simpleName
        const val EXTRA_DATA = "extra_data"
        const val EXTRA_NOTE = "extra_note"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_followers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = FollowersAdapter(listUser)
        listUser.clear()

        favorites = requireActivity().intent.getParcelableExtra(DetailActivity.EXTRA_FAV)

        if (favorites != null) {
            userFavorite = requireActivity().intent.getParcelableExtra<Favorite>(EXTRA_NOTE) as Favorite
            getFollowers(userFavorite.username.toString())
        } else {
            userDetail = requireActivity().intent.getParcelableExtra<User>(EXTRA_DATA) as User
            getFollowers(userDetail.username.toString())
        }
    }

    private fun showLoading(status: Boolean){
        if (status) {
            pb_followers.visibility = View.VISIBLE
        } else {
            pb_followers.visibility = View.INVISIBLE
        }
    }

    private fun getFollowers(id: String) {
        showLoading(true)
        val client = AsyncHttpClient()
        client.addHeader("User-Agent", "request")
        client.addHeader("Authorization", "token $apiKey")
        val url = "https://api.github.com/users/$id/followers"
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {
                showLoading(false)
                val result = String(responseBody)
                Log.d(TAG, result)
                try {
                    val jsonArray = JSONArray(result)
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val username: String = jsonObject.getString("login")
                        getDetail(username)
                    }
                } catch (e: Exception) {
                    Toast.makeText(activity, e.message, Toast.LENGTH_SHORT)
                        .show()
                    e.printStackTrace()
                }
            }

            override fun onFailure(statusCode: Int, headers: Array<Header>, responseBody: ByteArray, error: Throwable) {
                showLoading(false)
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error.message}"
                }
                Toast.makeText(activity, errorMessage, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun getDetail(id: String) {
        showLoading(true)
        val client = AsyncHttpClient()
        client.addHeader("User-Agent", "request")
        client.addHeader("Authorization", "token $apiKey")
        val url = "https://api.github.com/users/$id"
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {
                showLoading(false)
                val result = String(responseBody)
                Log.d(TAG, result)
                try {
                    val jsonObject = JSONObject(result)
                    val username: String? = jsonObject.getString("login")
                    val name: String? = jsonObject.getString("name")
                    val photo: String? = jsonObject.getString("avatar_url")
                    val company: String? = jsonObject.getString("company")
                    val location: String? = jsonObject.getString("location")
                    val repository: String? = jsonObject.getString("public_repos")
                    val followers: String? = jsonObject.getString("followers")
                    val following: String? = jsonObject.getString("following")
                    listUser.add(
                        User(
                            username,
                            name,
                            photo,
                            company,
                            location,
                            repository,
                            followers,
                            following
                        )
                    )
                    recyclerList()
                } catch (e: Exception) {
                    Toast.makeText(activity, e.message, Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }

            override fun onFailure(statusCode: Int, headers: Array<Header>, responseBody: ByteArray, error: Throwable) {
                showLoading(false)
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error.message}"
                }
                Toast.makeText(activity, errorMessage, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun recyclerList() {
        rv_followers.layoutManager = LinearLayoutManager(activity)
        rv_followers.adapter = adapter
    }
}
