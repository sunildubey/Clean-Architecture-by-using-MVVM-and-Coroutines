package com.sunil.covid19globalmeter.ui.fragment

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.sunil.covid19globalmeter.R

private val TAB_TITLES = arrayOf(
    R.string.overview,
    R.string.symptoms,
    R.string.prevention,
    R.string.treatments
)


class SectionsPagerAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {

        var fragment: Fragment? = null
        when (position) {
            0 -> fragment =
                OverviewFragment()
            1 -> fragment =
                SymptomsFragment()
            2 -> fragment =
                PreventionFragment()
            3 -> fragment =
                TreatmentsFragment()
        }

        return fragment!!

    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        return 4
    }
}
