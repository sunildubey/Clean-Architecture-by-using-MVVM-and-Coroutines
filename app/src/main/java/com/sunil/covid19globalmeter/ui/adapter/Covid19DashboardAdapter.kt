package com.sunil.covid19globalmeter.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sunil.covid19globalmeter.R
import com.sunil.covid19globalmeter.model.Country
import java.text.SimpleDateFormat
import java.util.*


class Covid19DashboardAdapter(
    private val context: Context,
    private val countyList: List<Country>,
    private val iItemClickCallback: IItemClickCallback
) : RecyclerView.Adapter<Covid19DashboardAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layout = R.layout.item_country_overview
        val v = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return ViewHolder(v)
    }

    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        try {
            if (position < countyList.size) {
                holder.mTvOptionName.text = countyList[position].Country
                holder.totalConfirm.text = countyList[position].TotalConfirmed.toString()
                holder.totalDeth.text = countyList[position].TotalDeaths.toString()
                holder.totalRecovered.text = countyList[position].TotalRecovered.toString()
                holder.newDeth.text = countyList[position].NewDeaths.toString()
                holder.newRecovered.text = countyList[position].NewRecovered.toString()
                holder.newConfirmed.text = countyList[position].NewConfirmed.toString()
                val sdf = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'")
                val newDate: Date = sdf.parse(countyList[position].Date);
                val spf = SimpleDateFormat("dd MMM yyyy hh:mm:ss a")
                val newDateString: String = spf.format(newDate)
                Log.e("date :: ", "$newDate $newDateString")
                holder.currentDate.text=newDateString
                holder.mLlParent.setOnClickListener {
                    iItemClickCallback.onListViewItemClick(position,countyList[position])
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Exception in onBindViewHolder", e)
        }
    }

    override fun getItemCount(): Int {
        return countyList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mTvOptionName: TextView = itemView.findViewById(R.id.tvOptionName)
        var mLlParent: LinearLayout = itemView.findViewById(R.id.llParent)
        var totalConfirm: TextView = itemView.findViewById(R.id.totalConfirm)
        var totalRecovered: TextView = itemView.findViewById(R.id.totalRecovered)
        var totalDeth: TextView = itemView.findViewById(R.id.totalDeth)
        var currentDate: TextView = itemView.findViewById(R.id.currentDate)
        var newConfirmed: TextView = itemView.findViewById(R.id.txtNewConfirmed)
        var newRecovered: TextView = itemView.findViewById(R.id.newRecovered)
        var newDeth: TextView = itemView.findViewById(R.id.newDeth)

    }

    companion object {
        private val TAG = Covid19DashboardAdapter::class.java.simpleName
    }

    interface IItemClickCallback {
        fun onListViewItemClick(
            position: Int,
            country: Country
        )
    }
}