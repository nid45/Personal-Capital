package com.example.personalcapitaltechnicalchallenge.ui

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.telephony.TelephonyManager
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
import com.example.personalcapitaltechnicalchallenge.MainActivity
import com.example.personalcapitaltechnicalchallenge.MainActivity.Companion.refreshButton
import com.example.personalcapitaltechnicalchallenge.adapters.CustomRecyclerViewAdapter
import com.example.personalcapitaltechnicalchallenge.mainviewmodelfactory.MainViewModelFactory
import com.example.personalcapitaltechnicalchallenge.models.Article
import com.example.personalcapitaltechnicalchallenge.models.Data
import com.example.personalcapitaltechnicalchallenge.networking.ApiCall
import com.example.personalcapitaltechnicalchallenge.viewmodel.MainViewModel
import org.json.JSONArray
import org.json.JSONObject
import java.util.*


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


        container?.removeAllViews()
        refreshButton.visibility = View.VISIBLE
        mainViewModelFactory = MainViewModelFactory()

        viewModel = ViewModelProvider(
            this,
            mainViewModelFactory
        ).get(MainViewModel::class.java)

        val art = ApiCall().execute()
        viewModel.init(parseData(art.get()))

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

        recyclerView.layoutManager = GridLayoutManager(context, 2)
        (recyclerView.layoutManager as GridLayoutManager).spanSizeLookup = object : SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                var manager: TelephonyManager = context?.getSystemService(
                    Context.TELEPHONY_SERVICE) as TelephonyManager
                if (Objects.requireNonNull(manager).phoneType == TelephonyManager.PHONE_TYPE_NONE) {
                    return if (position == 0) 3
                    else 1
                }else{
                    return if (position == 0) 2
                    else 1
                }
            }
        }

        refreshButton.setOnClickListener {
            refreshButton.isClickable = false
            var art2 = ApiCall().execute()
            viewModel.article.value = parseData(art2.get())
            recyclerView.adapter?.notifyDataSetChanged()
            refreshButton.isClickable = true
        }

        recyclerView.setBackgroundColor(Color.parseColor("#000000"))
        val itemViewType = 0
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



    private fun parseData(message: String?): Data {
        val jsonObject = JSONObject(message)

        val description = jsonObject.getString("description").toString()
        val feedUrl = jsonObject.getString("feed_url").toString()
        val homePageUrl = jsonObject.getString("home_page_url").toString()
        val titleData = jsonObject.getString("title").toString()
        val userComment = jsonObject.getString("user_comment").toString()
        val version = jsonObject.getString("version").toString()
        val itemArray = JSONArray(jsonObject.getString("items"))

        val itemList: MutableList<Article> = mutableListOf()
        for (i in 0 until itemArray.length()) {
            val item = itemArray[i] as JSONObject
            val authorob = JSONObject(item.get("author").toString())
            val author = authorob.get("name").toString()
            val category = item.getString("category").toString()
            val content = item.getString("content").toString()
            val contentHtml = item.getString("content_html").toString()
            val dateModified = item.getString("date_modified").toString()
            val datePublished = item.getString("date_published").toString()
            val encodedTitle = item.getString("encoded_title").toString()
            val featuredImage = item.getString("featured_image").toString()
            val id = item.getString("id").toString()
            val insightSummary = item.getString("insight_summary").toString()
            val summary = item.getString("summary").toString()
            val summaryHtml = item.getString("summary_html").toString()
            val title = item.getString("title").toString()
            val url = item.getString("url").toString()
            itemList.add(
                (Article(
                    author,
                    category,
                    content,
                    contentHtml,
                    dateModified,
                    datePublished,
                    encodedTitle,
                    featuredImage,
                    id,
                    insightSummary,
                    summary,
                    summaryHtml,
                    title,
                    url
                ))
            )
        }
        return Data(description, feedUrl, homePageUrl, itemList, titleData, userComment, version)

    }

    override fun onDestroyView() {
        if (view != null) {
            val parentViewGroup = view!!.parent as ViewGroup?
            parentViewGroup?.removeAllViews()
        }
        super.onDestroyView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        // ensure that the refresh menu item is visible in this fragment
//        //val menuArticle = menu.findArticle(R.id.ic)
//        //  menuArticle.isVisible = true
//        super.onCreateOptionsMenu(menu, inflater)
//    }

    /**
     * access the fragment that contains the feed and attempt to refresh it
     */
    fun refreshContent() {
//        val mainFragment =
//            requireActivity().supportFragmentManager.findFragmentById(R.id.fl_fragment_main) as MainFragment

        // if the fragment we are requesting is attached then we can refresh the data
        // mainFragment.refreshContent()
    }


}
}