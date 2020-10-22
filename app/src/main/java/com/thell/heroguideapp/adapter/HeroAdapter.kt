package com.thell.heroguideapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.thell.heroguideapp.databinding.HeroItemLayoutBinding
import com.thell.heroguideapp.response.characters.Result

class HeroAdapter(private val data:ArrayList<Result>, val clickListener : (current:Result) -> Unit = {})
    : RecyclerView.Adapter<HeroAdapter.HeroViewHolder>() {

    inner class HeroViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        fun setData(characters: Result)
        {
           binding.tvName.text = characters.name

           Picasso.get()
                .load("${characters.thumbnail.path}/portrait_small.jpg")
                .into(binding.imgAvatar)

            binding.root.setOnClickListener {
                clickListener(characters)
            }
        }

    }

    private lateinit var binding: HeroItemLayoutBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeroViewHolder {
        binding = HeroItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        val view = binding.root
        return HeroViewHolder(view)
    }

    override fun getItemCount():Int {
        return data.size
    }

    override fun onBindViewHolder(holder: HeroViewHolder, position: Int) {
        holder.setData(data[position])
    }
}