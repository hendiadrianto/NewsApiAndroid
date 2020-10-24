package com.hendi.newsapiandroid.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.hendi.newsapiandroid.Model.Source.SourceItem
import com.hendi.newsapiandroid.R
import com.mdi.stockin_backoffice.ApiHelper.RecyclerItemClickListener
import kotlinx.android.synthetic.main.list_categori.view.*

class CategoriAdapter(internal val context : Context, internal val list: MutableList<String>) : RecyclerView.Adapter<CategoriAdapter.viewData>()  {

    inner class viewData (view : View) : RecyclerView.ViewHolder(view) {
        fun data(item: String) {
            itemView.id_list_categori.text = item
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewData {
        val v = LayoutInflater.from(context).inflate(R.layout.list_categori,parent,false)

        return viewData(v)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: viewData, position: Int) {
        holder.data(list[position])
    }


}