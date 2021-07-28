package com.satya.githubuser.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.satya.githubuser.R
import com.satya.githubuser.entity.User
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.item_user.view.*

class FollowersAdapter(private val listUser: ArrayList<User>) : RecyclerView.Adapter<FollowersAdapter.ListViewHolder>() {

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
    }

    override fun getItemCount(): Int = listUser.size

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgPhoto: CircleImageView = itemView.img_photo
        var tvName: TextView = itemView.tv_name
        var tvCompany: TextView = itemView.tv_item_company
    }
}