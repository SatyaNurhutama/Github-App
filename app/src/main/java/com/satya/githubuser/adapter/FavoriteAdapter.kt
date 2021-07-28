package com.satya.githubuser.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.satya.githubuser.R
import com.satya.githubuser.activity.DetailActivity
import com.satya.githubuser.entity.Favorite
import com.satya.githubuser.entity.User
import kotlinx.android.synthetic.main.item_user.view.*
import java.util.*

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.ListViewHolder>() {

    var listFav = ArrayList<Favorite>()
        set(listFav) {
            if (listFav.size > 0) {
                this.listFav.clear()
            }
            this.listFav.addAll(listFav)

            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false))
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listFav[position])
        val user = listFav[position]
        holder.itemView.setOnClickListener {
            val dataUser = User(
                user.username,
                user.name,
                user.photo,
                user.company,
                user.location,
                user.repository,
                user.followers,
                user.following
            )
            val intent = Intent(it.context, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_DATA, dataUser)
            it.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = this.listFav.size

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(fav: Favorite) {
            with(itemView) {
                Glide.with(itemView.context)
                    .load(fav.photo)
                    .apply(RequestOptions().override(250, 250))
                    .into(itemView.img_photo)
                tv_name.text = fav.name
                tv_item_company.text = fav.company.toString()
            }
        }
    }
}