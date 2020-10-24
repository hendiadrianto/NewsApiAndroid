package com.hendi.newsapiandroid.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hendi.newsapiandroid.Helper.mConvertDate
import com.hendi.newsapiandroid.Model.Headline.HeadlineItem
import com.hendi.newsapiandroid.R
import kotlinx.android.synthetic.main.list_headline.view.*

class HeadlineAdapter(internal val context: Context,internal val list : List<HeadlineItem>) : RecyclerView.Adapter<HeadlineAdapter.viewData>() {
    inner class viewData (view : View) : RecyclerView.ViewHolder(view){
        fun data(item: HeadlineItem) {
            if (item.urlToImage != null){
                Glide.with(context).load(item.urlToImage).into(itemView.id_list_photo)
            }

            itemView.id_list_name.text = item.title
            itemView.id_list_details.text = item.description
            itemView.id_list_source.text = item.source!!.name
            mConvertDate(item.publishedAt!!)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewData {
        val v = LayoutInflater.from(context).inflate(R.layout.list_headline,parent,false)

        return viewData(v)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: viewData, position: Int) {
        holder.data(list[position])
    }
}