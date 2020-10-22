package com.thell.heroguideapp.ui.fragment

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.thell.heroguideapp.adapter.ComicAdapter
import com.thell.heroguideapp.response.characters.Result
import com.thell.heroguideapp.viewmodel.DetailFragmentViewModel
import com.thell.heroguideapp.databinding.FragmentDetailBinding
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME
import java.util.*
import kotlin.collections.ArrayList


class DetailFragment : Fragment() {

    private lateinit var navController: NavController
    private  var binding: FragmentDetailBinding? = null
    private lateinit var viewModel: DetailFragmentViewModel
    private lateinit var recycleView : RecyclerView
    private lateinit var adapter : ComicAdapter
    private lateinit var selectedHero : Result
    private lateinit var layoutManager:LinearLayoutManager

    private  var selectedHeroData:ArrayList<Result> = arrayListOf()
    private  var comicList:ArrayList<com.thell.heroguideapp.response.comics.Result> = arrayListOf()

    private lateinit var tv_name : TextView
    private lateinit var tv_description : TextView
    private lateinit var img_avatar : ImageView

    private fun getCharacters()
    {
        viewModel.get(selectedHero.id).observe(viewLifecycleOwner, Observer {
            selectedHeroData.addAll(it.data.results)
            init()
        })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getComics()
    {
        viewModel.getComics(selectedHero.id).observe(viewLifecycleOwner, Observer {
            val dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ");

            val result = it.data.results.sortedByDescending {
                LocalDate.parse(it.modified,dateTimeFormatter)
            }.take(10)
            comicList.addAll(result)
            adapter.notifyDataSetChanged()

        })
    }

    private fun setupRecyclerView()
    {
        adapter = ComicAdapter(
                comicList
        )
        recycleView.layoutManager = layoutManager
        recycleView.adapter = adapter
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private  fun  init()
    {

        layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.VERTICAL,
                false
        )
        setupRecyclerView()
        getComics()
        if(selectedHeroData.first().description.isNotEmpty())
            tv_description.text = selectedHeroData.first().description

        tv_name.text = selectedHeroData.first().name

         Picasso.get()
                .load("${selectedHeroData.first().thumbnail.path}/portrait_fantastic.jpg")
                .into(img_avatar)
    }

    private  fun initUI()
    {
        tv_name = binding!!.fragmentDetailName
        tv_description = binding!!.fragmentDetailDescription
        img_avatar = binding!!.fragmentDetailAvatar
        recycleView = binding!!.fragmentDetailComics
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        if(arguments != null)
        {
            val args = DetailFragmentArgs.fromBundle(requireArguments())
            selectedHero = args.selectedCharacter!!
        }
        binding = FragmentDetailBinding.inflate(layoutInflater)
        initUI()

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        viewModel = ViewModelProviders.of(this).get(DetailFragmentViewModel::class.java)
        getCharacters()
    }

}