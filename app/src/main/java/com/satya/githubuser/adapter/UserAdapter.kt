package com.satya.githubuser.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.satya.githubuser.R
import com.satya.githubuser.activity.DetailActivity
import com.satya.githubuser.entity.User
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.item_user.view.*

class UserAdapter(private var listUser: ArrayList<User>) : RecyclerView.Adapter<UserAdapter.ListViewHolder>(){

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        return ListViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.item_user, viewGroup, false))
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val user = listUser[position]
        Glide.with(holder.itemView.context)
            .load(user.photo)
            .apply(RequestOptions().override(250, 250))
            .into(holder.imgPhoto)
        holder.tvName.text = user.name
        holder.tvCompany.text = user.company
        holder.itemView.setOnClickListener {
            val dataUser = User(
                user.username,
                user.name,
                user.photo,
                user.company,
                user.location,
                user.repository,
                user.followers,
                user.following,
                user.fav
            )
            val intentDetail = Intent(it.context, DetailActivity::class.java)
            intentDetail.putExtra(DetailActivity.EXTRA_DATA, dataUser)
            it.context.startActivity(intentDetail)
        }
    }

    override fun getItemCount(): Int = listUser.size

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgPhoto: CircleImageView = itemView.img_photo
        var tvName: TextView = itemView.tv_name
        var tvCompany: TextView = itemView.tv_item_company
    }
}