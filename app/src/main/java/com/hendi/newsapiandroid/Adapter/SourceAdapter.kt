package com.hendi.newsapiandroid.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hendi.newsapiandroid.Model.Source.SourceItem
import com.hendi.newsapiandroid.R
import kotlinx.android.synthetic.main.list_categori.view.*
import kotlinx.android.synthetic.main.list_source.view.*

class SourceAdapter(internal val context : Context,internal var list: List<SourceItem>) : RecyclerView.Adapter<SourceAdapter.viewData>() {
    inner class viewData (view : View) : RecyclerView.ViewHolder(view) {
        fun data(item: SourceItem) {
            itemView.id_list_name_source.text = item.name
            itemView.id_list_details_source.text = item.description
            itemView.id_list_categori_source.text = item.category
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewData {
        val v = LayoutInflater.from(context).inflate(R.layout.list_source,parent,false)

        return viewData(v)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: viewData, position: Int) {
        holder.data(list[position])
    }

    fun filterSource(new : MutableList<SourceItem>){
        list = new
        notifyDataSetChanged()
    }

}