package com.thell.heroguideapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.thell.heroguideapp.R
import com.thell.heroguideapp.response.characters.Result
import kotlinx.android.synthetic.main.hero_item_layout.view.*

class HeroAdapterv2(private val data:ArrayList<Result>, val clickListener : (current: Result) -> Unit = {})
    : RecyclerView.Adapter<HeroAdapterv2.HeroViewHolder>()  {

    inner class HeroViewHolder(item: View)  :RecyclerView.ViewHolder(item){
        val rootLaout = item.hero_rootLayout
        val tv_name = item.tv_name
        val img_avatar = item.img_avatar

        fun setData(data: Result) {
            rootLaout.setOnClickListener { clickListener(data) }

            tv_name.text = data.name

            Picasso.get()
                    .load("${data.thumbnail.path}/portrait_small.jpg")
                    .into(img_avatar)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeroViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        var v : View? = null
        v =  inflater.inflate(R.layout.hero_item_layout, parent, false)
        val holder = HeroViewHolder(v)
        return holder
    }

    override fun onBindViewHolder(holder: HeroViewHolder, position: Int) {
        val data = data[position]

        holder.setData(data)
    }

    override fun getItemCount(): Int {
        return  data.size
    }


}