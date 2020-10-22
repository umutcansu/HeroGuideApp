package com.thell.heroguideapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.thell.heroguideapp.R
import com.thell.heroguideapp.response.comics.Result
import kotlinx.android.synthetic.main.comic_item_layout.view.*

class ComicAdapter(private val data:ArrayList<Result>)
    : RecyclerView.Adapter<ComicAdapter.ComicViewHolder>() {

    inner class ComicViewHolder(item: View)  : RecyclerView.ViewHolder(item){
        val tv_number = item.tv_number
        val tv_title = item.tv_title
        val tv_description = item.tv_description
        val tv_date = item.tv_date

        fun setData(data: Result,number:Int) {
            tv_number.text = number.toString()
            tv_title.text = data.title
            tv_description.text = data.description
            tv_date.text = data.modified
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComicAdapter.ComicViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        var v : View? = null
        v =  inflater.inflate(R.layout.comic_item_layout, parent, false)
        val holder = ComicViewHolder(v)
        return holder
    }

    override fun onBindViewHolder(holder: ComicAdapter.ComicViewHolder, position: Int) {
        val data = data[position]

        holder.setData(data,position+1)
    }

    override fun getItemCount(): Int {
        return  data.size
    }
}