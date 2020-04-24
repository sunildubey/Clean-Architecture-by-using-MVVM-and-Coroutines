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
import com.sunil.covid19globalmeter.model.DateWiseCountModelItem
import java.text.SimpleDateFormat
import java.util.*

class Covid19CaseAdapter(
    private val context: Context,
    private val dateWiseList: List<DateWiseCountModelItem>
) : RecyclerView.Adapter<Covid19CaseAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layout = R.layout.item_country_date_wise
        val v = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return ViewHolder(v)
    }

    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        try {
            if (position < dateWiseList.size) {
                val sdf = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'")
                val newDate: Date = sdf.parse(dateWiseList[position].Date)
                val spf = SimpleDateFormat("dd MMM yyyy")
                val newDateString: String = spf.format(newDate)
                Log.e("date :: ", "$newDate $newDateString")
                holder.mTxtDate.text = newDateString
                holder.mTotalConfirm.text = dateWiseList[position].Confirmed.toString()
                holder.mTotalRecovered.text = dateWiseList[position].Recovered.toString()
                holder.mTotalDeath.text = dateWiseList[position].Deaths.toString()

            }
        } catch (e: Exception) {
            Log.e(TAG, "Exception in onBindViewHolder", e)
        }
    }

    override fun getItemCount(): Int {
        return dateWiseList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mLlParent: LinearLayout = itemView.findViewById(R.id.llParent)
        var mTxtDate: TextView = itemView.findViewById(R.id.txtDate)
        var mTotalConfirm: TextView = itemView.findViewById(R.id.totalConfirm)
        var mTotalRecovered: TextView = itemView.findViewById(R.id.totalRecovered)
        var mTotalDeath: TextView = itemView.findViewById(R.id.totalDeath)


    }

    companion object {
        private val TAG = Covid19DashboardAdapter::class.java.simpleName
    }
}

