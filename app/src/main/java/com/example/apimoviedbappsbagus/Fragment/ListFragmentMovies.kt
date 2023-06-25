package com.example.apimoviedbappsbagus.Fragment

import android.content.ContentValues
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.apimoviedbappsbagus.Adapter.MovieAdapter
import com.example.apimoviedbappsbagus.Model.Results
import com.example.apimoviedbappsbagus.Model.ResponseListMovies
import com.example.apimoviedbappsbagus.Service.RetrofitInstance
import com.example.apimoviedbappsbagus.databinding.FragmentListMoviesBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.HashMap

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ListFragmentMovies.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListFragmentMovies : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding : FragmentListMoviesBinding
    private lateinit var movieAdapter : MovieAdapter
    val listMovies = arrayListOf<Results>()
    var idmovie :Int?=null
    //    val  lm = LinearLayoutManager(this)
    var page = 1

    var loading = true
    var pastItemsVisible: Int = 0
    var visibleItemCount: Int = 0
    var totalItemCount: Int = 0
    lateinit var lm  : LinearLayoutManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentListMoviesBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment

        prepareRecyclerView()
        getPopularMovies(page)
        return binding.root
    }



    private fun prepareRecyclerView() {
        lm = LinearLayoutManager(requireContext())

        movieAdapter = MovieAdapter(requireContext(),listMovies)

        binding.mRecyclerView.setHasFixedSize(true)
        binding.mRecyclerView.layoutManager = lm

        binding.mRecyclerView.adapter = movieAdapter


        binding.mRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) { //check for scroll down
                    visibleItemCount = lm.getChildCount()
                    totalItemCount = lm.getItemCount()
                    pastItemsVisible = lm.findFirstVisibleItemPosition()
                    if (loading) {
                        if (visibleItemCount + pastItemsVisible >= totalItemCount) {
                            loading = false
                            Log.v("...", "Last Item !")
                            loadmore()
                            // Do pagination.. i.e. fetch new data
                            loading = true
                        }
                    }
                }
            }
        })


    }
    fun loadmore()
    {
        page+=1
        getPopularMovies(page)
    }
    fun clear()
    {
        listMovies.clear()

    }

    fun getPopularMovies(page:Int) {
        val params: MutableMap<String, String> = HashMap()
        params["api_key"] = "7916ace8a965a1c3413cd5231af30364"
        params["language"] = "en-US"
        params["page"] = page.toString()

        RetrofitInstance.api.getPopularMovies(params).enqueue(object  :
            Callback<ResponseListMovies> {
            override fun onResponse(call: Call<ResponseListMovies>, response: Response<ResponseListMovies>) {
                if (response.body()!=null){
                    listMovies.addAll(response.body()!!.results)
                    Log.d(ContentValues.TAG, "inionResponse: "  + listMovies)
                    movieAdapter.notifyDataSetChanged()
                }
                else{
                    Log.d("TAGihan", response.code().toString())

                }
            }
            override fun onFailure(call: Call<ResponseListMovies>, t: Throwable) {

                Log.d("TAGihan", t.message.toString())
            }
        })
    }


}