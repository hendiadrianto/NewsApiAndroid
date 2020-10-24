package com.hendi.newsapiandroid.View

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.hendi.newsapiandroid.Adapter.CategoriAdapter
import com.hendi.newsapiandroid.Adapter.HeadlineAdapter
import com.hendi.newsapiandroid.Adapter.SourceAdapter
import com.hendi.newsapiandroid.Helper.Constant
import com.hendi.newsapiandroid.Helper.mToastyError
import com.hendi.newsapiandroid.Helper.mToastyWarning
import com.hendi.newsapiandroid.Model.Headline.HeadlineItem
import com.hendi.newsapiandroid.Model.Headline.HeadlineResponse
import com.hendi.newsapiandroid.Model.Source.SourceItem
import com.hendi.newsapiandroid.Model.Source.SourceResponse
import com.hendi.newsapiandroid.Presenter.Headline.HeadlineImpl
import com.hendi.newsapiandroid.Presenter.Source.SourceImpl
import com.hendi.newsapiandroid.R
import com.hendi.newsapiandroid.View.Headline.HeadlineView
import com.hendi.newsapiandroid.View.Source.SourceView
import com.mdi.stockin_backoffice.ApiHelper.RecyclerItemClickListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),HeadlineView,SourceView {

    lateinit var context: Context
    lateinit var headlineImp : HeadlineImpl
    lateinit var sourceImp : SourceImpl
    lateinit var headlineAdapter : HeadlineAdapter
    lateinit var categoriAdapter : CategoriAdapter
    lateinit var sourceAdapter : SourceAdapter
    var listHeadline : List<HeadlineItem>? = ArrayList()
    var listSource : List<SourceItem>? = ArrayList()
    var listSourceResult = ArrayList<SourceItem>()

    var listCategory : MutableList<String>? = ArrayList()

    var kategoriName = ""
    var kategoriPosition = 0

    var is_dataSize = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        getSupportActionBar()!!.setDisplayHomeAsUpEnabled(false)
        getSupportActionBar()!!.setHomeButtonEnabled(false)

        context = this
        headlineImp = HeadlineImpl(this)
        sourceImp = SourceImpl(this)

        getHeadline()
        getSources()

        id_refresh.setOnRefreshListener {
            getHeadline()
            getSources()
        }

        categoryRV()

        headlineRV()
        sourceRV()

    }

    private fun headlineRV() {
        id_Rv_Headline.addOnItemTouchListener(RecyclerItemClickListener(context,object : RecyclerItemClickListener.OnItemClickListener{
            override fun onItemClick(view: View, position: Int) {
                Log.d("Klik Web Head",headlineAdapter.list[position].url!!)
                startWebView(headlineAdapter.list[position].url!!,headlineAdapter.list[position].title!!)
            }
        }))
    }

    private fun sourceRV() {
        id_Rv_Source.addOnItemTouchListener(RecyclerItemClickListener(context,object : RecyclerItemClickListener.OnItemClickListener{
            override fun onItemClick(view: View, position: Int) {
                Log.d("Klik Web Source",sourceAdapter.list[position].url!!)
                startWebView(sourceAdapter.list[position].url!!,sourceAdapter.list[position].name!!)
            }
        }))
    }

    private fun startWebView(url: String, title : String) {
        val intent = Intent(context, WebViewActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        intent.putExtra(Constant.URL_STRING, url)
        intent.putExtra(Constant.TITLE_STRING, title)
        startActivity(intent)
    }


    private fun categoryRV() {
        id_rv_categori.addOnItemTouchListener(RecyclerItemClickListener(context,object : RecyclerItemClickListener.OnItemClickListener{
            override fun onItemClick(view: View, position: Int) {

                kategoriPosition = position
                Log.d("Kategori Position",kategoriPosition.toString())

                if (position == 0){
                    id_Rv_Source.visibility = View.GONE
                    id_Rv_Headline.visibility = View.VISIBLE
                } else {
                    id_Rv_Headline.visibility = View.GONE
                    id_Rv_Source.visibility = View.VISIBLE
                    kategoriMenu(position)
                }
            }
        }))
    }

    private fun kategoriMenu(position : Int) {
        kategoriName = categoriAdapter.list[position]

        val result = ArrayList<SourceItem>()
        Log.w("Klik Kategori",categoriAdapter.list[position].toLowerCase())

        for (i in listSource!!){
            if (i.category!!.toLowerCase().contains(categoriAdapter.list[position].toLowerCase())){
                result.add(i)
            }
        }

        listSourceResult = result
        Log.w("Result kategori",listSourceResult.size.toString())
        sourceAdapter.filterSource(listSourceResult)
        id_Rv_Source.adapter = sourceAdapter
        sourceAdapter.notifyDataSetChanged()
    }

    private fun getSources() {
        sourceImp.sourceGet(context)
    }

    private fun getHeadline() {
        val data : MutableMap<String,String> = HashMap()
        data["country"] = "id"
        headlineImp.headlineGet(context,data)
    }

    override fun headlineResponse(data: HeadlineResponse) {
        id_refresh.isRefreshing = false
        Log.w("Headline Size",data.articles!!.size.toString())

        if (data.articles.size > 0){
            listHeadline = data.articles
            id_Rv_Headline.layoutManager = LinearLayoutManager(context)
            headlineAdapter = HeadlineAdapter(context, listHeadline!!)
            id_Rv_Headline.adapter = headlineAdapter
            headlineAdapter.notifyDataSetChanged()

        } else {
            is_dataSize = false
            mToastyWarning(context,"Data Heaadline kosong.")
        }

    }

    override fun headlineError(string: String) {
        id_refresh.isRefreshing = false
        mToastyError(context,string)
    }

    override fun sourceResponse(data: SourceResponse) {
        id_refresh.isRefreshing = false
        listCategory!!.clear()

        Log.w("Source Size",data.sources!!.size.toString())
        listCategory!!.add("Headline")

        if (data.sources.size > 0){
            data.sources.forEachIndexed { i, sourceItem ->
                val find = listCategory!!.find { t -> t == sourceItem.category}
                if (find == null){
                    listCategory!!.add(sourceItem.category!!)
                    Log.d("Category",sourceItem.category)
                }
            }

            id_rv_categori.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
            categoriAdapter = CategoriAdapter(context,listCategory!!)
            id_rv_categori.adapter = categoriAdapter
            categoriAdapter.notifyDataSetChanged()

            listSource = data.sources
            id_Rv_Source.layoutManager = LinearLayoutManager(context)
            sourceAdapter = SourceAdapter(context,listSource!!)
            id_Rv_Source.adapter = sourceAdapter
            sourceAdapter.notifyDataSetChanged()

        } else {
            is_dataSize = false
            mToastyWarning(context,"Data Source kosong.")
        }
    }

    override fun sourceError(string: String) {
        id_refresh.isRefreshing = false
        mToastyError(context,string)
    }


    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search,menu)
        val search = menu!!.findItem(R.id.id_search_toolbar)
        if (search != null){
            val searchView = search.actionView as androidx.appcompat.widget.SearchView
            searchView.setBackgroundResource(R.drawable.white_corner)
            searchView.queryHint = "search title..."

            searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (kategoriPosition == 0){
                        if (newText!!.isNotEmpty()){
                            val result = ArrayList<HeadlineItem>()
                            for (i in listHeadline!!){
                                if (i.title!!.toLowerCase().contains(newText.toString().toLowerCase())){
                                    result.add(i)
                                }
                            }

                            headlineAdapter = HeadlineAdapter(context, result)
                            id_Rv_Headline.adapter = headlineAdapter
                            headlineAdapter.notifyDataSetChanged()
                        } else {
                            headlineAdapter = HeadlineAdapter(context, listHeadline!!)
                            id_Rv_Headline.adapter = headlineAdapter
                            headlineAdapter.notifyDataSetChanged()
                        }
                    } else {
                        if (newText!!.isNotEmpty()){
                            val result = ArrayList<SourceItem>()
                            for (i in listSource!!){
                                if (i.name!!.toLowerCase().contains(newText.toString().toLowerCase()) && i.category!!.toLowerCase().contains(kategoriName.toLowerCase()) ){
                                    result.add(i)
                                }
                            }

                            sourceAdapter = SourceAdapter(context,result)
                            id_Rv_Source.adapter = sourceAdapter
                            sourceAdapter.notifyDataSetChanged()
                        } else {
                            sourceAdapter = SourceAdapter(context,listSourceResult)
                            id_Rv_Source.adapter = sourceAdapter
                            sourceAdapter.notifyDataSetChanged()
                        }
                    }

                    return true
                }

            })
        }

        return super.onCreateOptionsMenu(menu)
    }

    override fun onResume() {
        /*if(mIsNetworkAvailable(context)){
            //getHeadline()
            //getSources()
        }*/
        super.onResume()
    }

}