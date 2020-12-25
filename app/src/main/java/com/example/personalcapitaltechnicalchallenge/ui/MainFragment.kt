package com.example.personalcapitaltechnicalchallenge.ui

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.telephony.TelephonyManager
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.ScrollView
import androidx.activity.OnBackPressedCallback
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import androidx.recyclerview.widget.RecyclerView
import com.example.personalcapitaltechnicalchallenge.MainActivity.Companion.maintoolbar
import com.example.personalcapitaltechnicalchallenge.MainActivity.Companion.refreshButton
import com.example.personalcapitaltechnicalchallenge.adapters.CustomRecyclerViewAdapter
import com.example.personalcapitaltechnicalchallenge.mainviewmodelfactory.MainViewModelFactory
import com.example.personalcapitaltechnicalchallenge.networking.ApiCall
import com.example.personalcapitaltechnicalchallenge.viewmodel.MainViewModel
import java.util.*
import kotlin.math.pow
import kotlin.math.sqrt


class MainFragment : Fragment() {


    private lateinit var viewModel: MainViewModel
    private lateinit var mainViewModelFactory: MainViewModelFactory
    private lateinit var recyclerView: RecyclerView
    private lateinit var constraintLayout: RelativeLayout
    private lateinit var scrollView: ScrollView
    var listener: FragmentActivity? = null



    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is Activity) {
            this.listener = context as FragmentActivity
        }
    }

    override fun onDetach() {
        super.onDetach()
        this.listener = null
    }

    override fun onPause() {
        super.onPause()
        constraintLayout.visibility = View.GONE
    }

    override fun onResume() {
        super.onResume()
        constraintLayout.visibility = View.VISIBLE

    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
            }
        })

        maintoolbar.title = "Daily Capital Blog"

        container?.removeAllViews()
        refreshButton.visibility = View.VISIBLE
        mainViewModelFactory = MainViewModelFactory()

        //create the viewmodel that will hold the data
        viewModel = ViewModelProvider(
            this,
            mainViewModelFactory
        ).get(MainViewModel::class.java)

        //get the Data object from the rss feed and parse it into Article objects
        val art = ApiCall().execute()
        viewModel.init(MainViewModel.parser.parseData(art.get()))

        scrollView = ScrollView(activity!!)
        scrollView.id = View.generateViewId()
        scrollView.layoutParams = ViewGroup.LayoutParams(
            RelativeLayout.LayoutParams.MATCH_PARENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT
        )

        scrollView.setBackgroundColor(Color.parseColor("#000000"))

        constraintLayout = RelativeLayout(activity!!)
        constraintLayout.id = View.generateViewId()
        constraintLayout.layoutParams = ViewGroup.LayoutParams(
            RelativeLayout.LayoutParams.MATCH_PARENT,
            RelativeLayout.LayoutParams.MATCH_PARENT
        )
        constraintLayout.setBackgroundColor(Color.parseColor("#000000"))

        recyclerView = RecyclerView(activity!!)
        recyclerView.id = View.generateViewId()
        recyclerView.layoutParams = RecyclerView.LayoutParams(
            RecyclerView.LayoutParams.MATCH_PARENT,
            RecyclerView.LayoutParams.MATCH_PARENT
        )
        var spanSize = 2
        if(checkIsTablet()) spanSize = 3
        recyclerView.layoutManager = GridLayoutManager(context, spanSize)
        (recyclerView.layoutManager as GridLayoutManager).spanSizeLookup = object : SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (checkIsTablet()) {
                    if (position == 0) 3
                    else 1
                }else{
                    if (position == 0) 2
                    else 1
                }
            }
        }

        refreshButton.setOnClickListener {
            refreshButton.isClickable = false
            var art2 = ApiCall().execute()
            viewModel.article.value = MainViewModel.parser.parseData(art2.get())
            recyclerView.adapter?.notifyDataSetChanged()
            refreshButton.isClickable = true
        }

        recyclerView.setBackgroundColor(Color.parseColor("#000000"))
        val itemViewType = 0

        //load everything at once instead of every time the user scrolls
        recyclerView.recycledViewPool.setMaxRecycledViews(itemViewType, 0)
        ViewCompat.setNestedScrollingEnabled(recyclerView, false)
        recyclerView.adapter = viewModel.article.value?.items?.let {
            CustomRecyclerViewAdapter(
                context, it,
                requireActivity(), constraintLayout
            )
        }

        constraintLayout.addView(recyclerView)

        scrollView.addView(constraintLayout)
        scrollView.isSmoothScrollingEnabled = true

        return scrollView

    }





    override fun onDestroyView() {
        if (view != null) {
            val parentViewGroup = view!!.parent as ViewGroup?
            parentViewGroup?.removeAllViews()
        }
        super.onDestroyView()
    }

    private fun checkIsTablet(): Boolean {
        val display = (activity as Activity).windowManager.defaultDisplay
        val metrics = DisplayMetrics()
        display.getMetrics(metrics)
        val widthInches = metrics.widthPixels / metrics.xdpi
        val heightInches = metrics.heightPixels / metrics.ydpi
        val diagonalInches = sqrt(
            widthInches.toDouble().pow(2.0) + heightInches.toDouble().pow(2.0)
        )
        return diagonalInches >= 7.0
    }
}