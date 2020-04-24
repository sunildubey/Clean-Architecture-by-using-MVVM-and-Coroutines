package com.sunil.covid19globalmeter.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.work.*
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.sunil.covid19globalmeter.R
import com.sunil.covid19globalmeter.model.Country
import com.sunil.covid19globalmeter.model.DashboardModel
import com.sunil.covid19globalmeter.ui.NotificationWorker
import com.sunil.covid19globalmeter.ui.adapter.Covid19DashboardAdapter
import com.sunil.covid19globalmeter.ui.viewmodel.DashboardViewModel
import com.sunil.covid19globalmeter.utilities.APiState
import com.sunil.covid19globalmeter.utilities.VerticalSpaceItemDecoration
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.concurrent.TimeUnit

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class Covid19DashboardActivity : BaseActivity(), Covid19DashboardAdapter.IItemClickCallback {

    private val viewModel: DashboardViewModel by viewModel()
    private var dashboardAdapter: Covid19DashboardAdapter? = null
    private lateinit var mTxtTotalConfirmed: TextView
    private lateinit var mTxtTotalRecovered: TextView
    private lateinit var mTxtTotalDeaths: TextView
    private lateinit var mTxtNewConfirmed: TextView
    private lateinit var mTxtNewDeaths: TextView
    private lateinit var mTxtNewRecovered: TextView
    private lateinit var mExpandedImage: LinearLayout
    private lateinit var mToolbarLayout: CollapsingToolbarLayout
    private lateinit var mSwipeLayout: SwipeRefreshLayout
    private lateinit var mCcLayout: CoordinatorLayout
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var fab: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView();
        //load api
        loadData()
        initWorker()
        fab.setOnClickListener {
            val intent = Intent(this, AboutCovid19Activity::class.java)
            startActivity(intent)
        }
    }

    private fun initWorker() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val notificationWorkRequest =
            PeriodicWorkRequestBuilder<NotificationWorker>(1, TimeUnit.MINUTES)
                .setConstraints(constraints)
                .build()

        val JOB_TAG = "jobTag"
        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
            JOB_TAG,
            ExistingPeriodicWorkPolicy.KEEP,
            notificationWorkRequest
        )
    }


    private fun initView() {
        mTxtTotalConfirmed = findViewById(R.id.txtTotalConfirmed)
        mTxtTotalRecovered = findViewById(R.id.txtTotalRecovered)
        mTxtTotalDeaths = findViewById(R.id.txtTotalDeaths)
        mTxtNewConfirmed = findViewById(R.id.txtNewConfirmed)
        mTxtNewDeaths = findViewById(R.id.txtNewDeaths)
        mTxtNewRecovered = findViewById(R.id.txtNewRecovered)
        mExpandedImage = findViewById(R.id.expandedImage)
        mToolbarLayout = findViewById(R.id.toolbar_layout)
        mRecyclerView = findViewById(R.id.recyclerView)
        mSwipeLayout = findViewById(R.id.swipeLayout)
        mCcLayout = findViewById(R.id.ccLayout)
        fab = findViewById(R.id.fab)
        mSwipeLayout.setOnRefreshListener {
            loadData()
        }
        val linearLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        mRecyclerView.layoutManager = linearLayoutManager
        mRecyclerView?.addItemDecoration(VerticalSpaceItemDecoration(this))
    }

    private fun loadData() {
        viewModel.holdLiveData.observe(this, Observer { state ->
            when (state) {
                is APiState.Loading -> {
                    mSwipeLayout.isRefreshing = true
                }
                is APiState.Error -> {
                    Log.e(TAG, "Error :: " + state.message)
                    mSwipeLayout.isRefreshing = false
                    Toast.makeText(applicationContext, state.message, Toast.LENGTH_LONG).show()
                }
                is APiState.Success -> {
                    mSwipeLayout.isRefreshing = false
                    val data = state.data
                    Log.e(TAG, data.toString())
                    setData(data)
                }
            }
        })

        viewModel.getDashBoard()
    }

    private fun setData(data: DashboardModel) {

        mTxtTotalConfirmed.text = data.Global.TotalConfirmed.toString()
        mTxtTotalDeaths.text = data.Global.TotalDeaths.toString()
        mTxtTotalRecovered.text = data.Global.TotalRecovered.toString()

        mTxtNewConfirmed.text = data.Global.NewConfirmed.toString()
        mTxtNewDeaths.text = data.Global.NewDeaths.toString()
        mTxtNewRecovered.text = data.Global.NewRecovered.toString()
        //val list = data.Countries.sortedWith(compareBy { it.TotalConfirmed })
        val list = data.Countries.sortedByDescending { it.TotalConfirmed }
        dashboardAdapter = Covid19DashboardAdapter(this, list, this@Covid19DashboardActivity)
        mRecyclerView.adapter = dashboardAdapter
        dashboardAdapter?.notifyDataSetChanged()
    }

    companion object {
        private val TAG = Covid19DashboardActivity::class.java.simpleName
    }

    override fun onListViewItemClick(position: Int, country: Country) {
        val intent = Intent(this, Covid19CaseActivity::class.java)
        intent.putExtra("countryDetails", country)
        startActivity(intent)
    }
}

