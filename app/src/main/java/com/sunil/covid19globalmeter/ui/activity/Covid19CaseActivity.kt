package com.sunil.covid19globalmeter.ui.activity

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.appbar.AppBarLayout
import com.sunil.covid19globalmeter.R
import com.sunil.covid19globalmeter.model.Country
import com.sunil.covid19globalmeter.model.DateWiseCountModelItem
import com.sunil.covid19globalmeter.ui.adapter.Covid19CaseAdapter
import com.sunil.covid19globalmeter.ui.viewmodel.DateWiseViewModel
import com.sunil.covid19globalmeter.utilities.APiState
import com.sunil.covid19globalmeter.utilities.VerticalSpaceItemDecoration
import org.koin.android.viewmodel.ext.android.viewModel

class Covid19CaseActivity : BaseActivity() {
    private val viewModel: DateWiseViewModel by viewModel()
    private var covid19CaseAdapter: Covid19CaseAdapter? = null

    private lateinit var mToolbarTitle: TextView
    private lateinit var mToolbar: Toolbar
    private lateinit var mAppBarlayout: AppBarLayout
    private lateinit var mRecycler: RecyclerView
    private lateinit var mSwipeRefreshLayout: SwipeRefreshLayout
    private var country: Country? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_covid19_case)
        country = intent.extras?.get("countryDetails") as Country
        Log.e(TAG, country.toString())
        initView()
        //load api
        loadData()
    }


    private fun initView() {
        mToolbarTitle = findViewById(R.id.toolbarTitle)
        mToolbarTitle.text = country?.Country
        mToolbar = findViewById(R.id.toolbar)
        mAppBarlayout = findViewById(R.id.appBarlayout)
        mRecycler = findViewById(R.id.recycler)
        mSwipeRefreshLayout = findViewById(R.id.swipeRefreshLayout)

        mSwipeRefreshLayout.setOnRefreshListener {
            loadData()
        }
        val linearLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        mRecycler.layoutManager = linearLayoutManager
        mRecycler?.addItemDecoration(VerticalSpaceItemDecoration(this))
    }

    private fun loadData() {
        viewModel.holdLiveData.observe(this, Observer { state ->
            when (state) {
                is APiState.Loading -> {
                    mSwipeRefreshLayout.isRefreshing = true
                }
                is APiState.Error -> {
                    Log.e(TAG, "Error :: " + state.message)
                    mSwipeRefreshLayout.isRefreshing = false
                    Toast.makeText(applicationContext, state.message, Toast.LENGTH_LONG).show()
                }
                is APiState.Success -> {
                    mSwipeRefreshLayout.isRefreshing = false
                    val data = state.data
                    Log.e(TAG, data.toString())
                    setData(data)
                }
            }
        })

        viewModel.getDateWise(country?.Slug!!)
    }

    private fun setData(data: List<DateWiseCountModelItem>) {
        val list = data.sortedByDescending { it.Date }
        covid19CaseAdapter = Covid19CaseAdapter(this, list)
        mRecycler.adapter = covid19CaseAdapter
        covid19CaseAdapter?.notifyDataSetChanged()
    }

    companion object {
        private val TAG = Covid19CaseActivity::class.java.simpleName
    }
}