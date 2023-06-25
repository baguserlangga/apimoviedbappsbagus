package com.example.apimoviedbappsbagus

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.apimoviedbappsbagus.Adapter.GenreAdapter
import com.example.apimoviedbappsbagus.Fragment.ListFragmentMovies
import com.example.apimoviedbappsbagus.Fragment.NowPlayingFragmentMovies
import com.example.apimoviedbappsbagus.Model.GenresModel
import com.example.apimoviedbappsbagus.Model.ResponseGenres
import com.example.apimoviedbappsbagus.Model.ResponseListMovies
import com.example.apimoviedbappsbagus.Service.RetrofitInstance
import com.example.apimoviedbappsbagus.databinding.ActivityMainBinding
import com.example.apimoviedbappsbagus.databinding.DialogCategoryLayoutBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    val listgenre = ArrayList<GenresModel>()
    var search = false

    private lateinit var bindingCategory:  DialogCategoryLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        init()
        supportActionBar!!.hide()
        setContentView(binding.root)
    }
    fun init()
    {
        binding.tabLayoutInputManual.addTab(binding.tabLayoutInputManual.newTab().setText("popular movies"))
        binding.tabLayoutInputManual.addTab(binding.tabLayoutInputManual.newTab().setText("Now Playing"))

        binding.tabLayoutInputManual.tabGravity= TabLayout.GRAVITY_FILL
        val adapter = ViewPagerAdapter(this,supportFragmentManager,binding.tabLayoutInputManual.tabCount)
        binding.viewPager.adapter=adapter

        binding.viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(binding.tabLayoutInputManual))
        binding.tabLayoutInputManual.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                binding.viewPager.currentItem=tab!!.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}


        })

        binding.imageViewSearch.setOnClickListener {
            if(search)
            {
                binding.editTextSearch.visibility = View.GONE
                binding.imageViewSearch.visibility = View.VISIBLE
                binding.imageViewSearch.visibility = View.VISIBLE
                search=false
            }
            else{
                binding.editTextSearch.visibility = View.VISIBLE
                binding.imageViewSearch.visibility = View.GONE
                binding.imageViewSearch.visibility = View.GONE
                search = true
            }

        }
        binding.imageViewCategory.setOnClickListener{
            getGenre()
        }

        binding.editTextSearch.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {

//                performSearch()

                return@OnEditorActionListener true
            }
            false
        })

    }
   inner class ViewPagerAdapter(var context: Context, fm: FragmentManager, var totaltabs : Int) : FragmentPagerAdapter(fm) {

       override fun getCount(): Int {
           return totaltabs
       }

       override fun getItem(position: Int): Fragment {
           return when (position) {
               0 -> {
                   ListFragmentMovies()
               }
               1->{
                NowPlayingFragmentMovies()
               }
               else -> getItem(position)

           }
       }

   }

    fun getGenre() {
        val params: MutableMap<String, String> = HashMap()
        params["api_key"] = "7916ace8a965a1c3413cd5231af30364"
        params["language"] = "en-US"
        params["with_genres"] = ""
        RetrofitInstance.api.getGenre(params).enqueue(object  :
            Callback<ResponseGenres> {
            override fun onResponse(call: Call<ResponseGenres>, response: Response<ResponseGenres>) {
                if (response.body()!=null){
                    Log.d("TAGihansss", response.body()!!.genres[0].name)
                    listgenre.addAll(response.body()!!.genres)
                    showBottomSheetDialog(listgenre)
                }
                else{
                    Log.d("TAGihan", response.code().toString())

                }
            }
            override fun onFailure(call: Call<ResponseGenres>, t: Throwable) {

                Log.d("TAGihan", t.message.toString())
            }
        })
    }

    private fun showBottomSheetDialog(listgenre:ArrayList<GenresModel>) {
        val bottomSheetDialog = BottomSheetDialog(this)
        bindingCategory = DialogCategoryLayoutBinding.inflate(layoutInflater)
        bottomSheetDialog.setContentView(bindingCategory.root)

        val  lm = LinearLayoutManager(this)

        var  movieAdapter = GenreAdapter(this,listgenre)

        bindingCategory.mRecyclerView.setHasFixedSize(true)
        bindingCategory.mRecyclerView.layoutManager = lm
        bindingCategory.mRecyclerView.adapter = movieAdapter




        bottomSheetDialog.show()
    }


}