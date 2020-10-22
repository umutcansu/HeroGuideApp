package com.thell.heroguideapp.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.ProgressBar
import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.thell.heroguideapp.adapter.HeroAdapter
import com.thell.heroguideapp.adapter.HeroAdapterv2
import com.thell.heroguideapp.databinding.FragmentListBinding
import com.thell.heroguideapp.response.characters.Result
import com.thell.heroguideapp.viewmodel.ListFragmentViewModel


class ListFragment : Fragment(),SwipeRefreshLayout.OnRefreshListener {

    private var limit = 30
    private var page = 1
    private var offset = 0

    private var isLoading = false
    private lateinit var adapter : HeroAdapterv2
    private lateinit var navController: NavController
    private  var binding: FragmentListBinding? = null
    private lateinit var viewModel: ListFragmentViewModel
    private lateinit var characterList:ArrayList<Result>

    private lateinit var recycleView : RecyclerView
    private lateinit var searchBox: SearchView
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var progressBar: ProgressBar
    private lateinit var filter: Filter
    private lateinit var layoutManager:LinearLayoutManager

    private fun initSearchBox()
    {
        searchBox.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filter.filter(newText)
                return false
            }

        })
    }

    private fun getCharacters()
    {
        isLoading = true
        progressBar.visibility = View.VISIBLE

        offset = (page - 1) * limit
        if(offset < 0)  offset = 0

        viewModel.get(limit*page,offset).observe(viewLifecycleOwner, Observer {
            characterList.addAll(it.data.results)

            adapter.notifyDataSetChanged()
            progressBar.visibility = View.GONE
            isLoading = false
        })
    }

    private fun setupRecyclerView()
    {
        adapter = HeroAdapterv2(
            characterList,
            ::clickHero
        )
        recycleView.layoutManager = layoutManager
        recycleView.adapter = adapter

        initFilter()
        initSearchBox()

    }

    override fun onRefresh()
    {
        searchBox.setQuery("",false)
        init()

        getCharacters()
        swipeRefresh.isRefreshing = false
    }


    private fun initFilter()
    {
        filter = object : android.widget.Filter()
        {
            override fun performFiltering(constraint: CharSequence?): FilterResults
            {
                val result = FilterResults()

                if (constraint.isNullOrEmpty() )
                {

                    val resultList = ArrayList<Result>()
                    for (d in characterList)
                    {
                        resultList.add(d)
                    }

                    result.count = resultList.size
                    result.values = resultList
                }
                else
                {
                    val resultList = ArrayList<Result>()
                    for (d in characterList)
                    {
                        if (d.name.toLowerCase().contains(constraint.toString().toLowerCase()))
                            resultList.add(d)

                    }

                    result.count = resultList.size
                    result.values = resultList

                }

                return result
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?)
            {
                @Suppress("UNCHECKED_CAST")
                adapter =
                    if(results?.values != null)
                        HeroAdapterv2(results.values as ArrayList<Result>,::clickHero)
                    else
                        HeroAdapterv2(arrayListOf(),::clickHero)

                recycleView.adapter = adapter
            }

        }

    }

    private fun clickHero(hero:Result)
    {
        val direction = ListFragmentDirections.actionListFragmentToDetailFragment()
        direction.selectedCharacter = hero
        navController.navigate(direction)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentListBinding.inflate(layoutInflater)


        return binding?.root
    }

    private  fun initUI()
    {
        recycleView = binding!!.fragmentHeroListRecycleView
        searchBox =  binding!!.fragmentHeroListSearchView
        swipeRefresh =  binding!!.fragmentHeroListSwipeRefresh
        progressBar = binding!!.fragmentHeroListProgressBar
        swipeRefresh.setOnRefreshListener(this)

        recycleView.addOnScrollListener(object : RecyclerView.OnScrollListener()
        {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if(dy > 0)
                {
                    val visibleCount = layoutManager.childCount
                    val past = layoutManager.findLastCompletelyVisibleItemPosition()
                    val total = layoutManager.itemCount

                    if(!isLoading && visibleCount + past >= total)
                    {
                        page++
                        getCharacters()
                    }
                }
            }
        })
    }

    private fun init()
    {
        characterList = arrayListOf()
        limit = 30
        page = 1
        offset = 0
        layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.VERTICAL,
                false
        )
        setupRecyclerView()

    }

    override fun onResume() {
        super.onResume()
        initUI()
        init()
        getCharacters()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        viewModel = ViewModelProviders.of(this).get(ListFragmentViewModel::class.java)
    }

}