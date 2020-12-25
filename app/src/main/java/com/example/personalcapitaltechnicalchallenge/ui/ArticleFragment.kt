package com.example.personalcapitaltechnicalchallenge.ui

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.example.personalcapitaltechnicalchallenge.MainActivity.Companion.mainLayout
import com.example.personalcapitaltechnicalchallenge.MainActivity.Companion.refreshButton
import com.example.personalcapitaltechnicalchallenge.helpers.Helpers
import com.example.personalcapitaltechnicalchallenge.helpers.MediaContentLoader
import com.example.personalcapitaltechnicalchallenge.models.Article


class ArticleFragment : Fragment() {

        private lateinit var constraintLayout: ConstraintLayout
        lateinit var article_title: TextView
        lateinit var article_content: WebView
        lateinit var article_author: TextView
        lateinit var article_date: TextView
        lateinit var article: Article
        lateinit var article_image: ImageView
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
            setHasOptionsMenu(true)
            //prevent overlapping layouts when creating new fragment
            container?.removeAllViews()


            //handle press of the back button
            activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {

                    val fm: FragmentManager = activity!!.supportFragmentManager
                    val fragment: Fragment =
                        MainFragment()
                    val fragmentA =
                        fragmentManager!!.findFragmentByTag("mainfrag")
                    //try to find the main fragment already created in the stack which should always work
                    if (fragmentA == null) {
                        fm.beginTransaction()
                            .replace(mainLayout.id, fragment)
                            .commit()
                    } else {
                        //create new main fragment but should never get here
                        fm.beginTransaction()
                            .replace(mainLayout.id, fragmentA)
                            .commit()
                    }
                }

            })
            container?.removeAllViews()

        //refresh button should not be visible when displaying an article
        refreshButton.visibility = View.GONE

        scrollView = ScrollView(activity!!)
        //automatically start at top of the article
        scrollView.pageScroll(View.FOCUS_UP)
        scrollView.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            scrollView.isSmoothScrollingEnabled = true
            constraintLayout = ConstraintLayout(activity!!)
            constraintLayout.id = View.generateViewId()
            constraintLayout.layoutParams = ViewGroup.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT
            )
            constraintLayout.setBackgroundColor(Color.parseColor("#000000"))

            //retrieve the article that was passed when the user clicked on it
            article =  arguments?.getSerializable("item") as Article

            //show image at top of article
            article_image = ImageView(context)
            article_image.id = View.generateViewId()
            article_image.scaleType = ImageView.ScaleType.FIT_XY
            article_image.layoutParams = ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                0
            )

            article_image.adjustViewBounds = true
            constraintLayout.addView(article_image)

        //asynchronously get the image from the source
            MediaContentLoader(
                article_image,
                article.featured_image
            ).execute()


            article_title = TextView(context)
            article_title.id = View.generateViewId()
            article_title.text = Helpers.fromHtml(article.title)
            article_title.textSize = 20F
            article_title.setTypeface(null, Typeface.BOLD)
            article_title.setTextColor(Color.parseColor("#FFFFFF"))
            article_title.layoutParams = ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
            )
            (article_title.layoutParams as ConstraintLayout.LayoutParams).setMargins(40, 0, 40, 0)

            var currentLayout = article_title.layoutParams as ConstraintLayout.LayoutParams
            currentLayout.topToBottom = article_image.id
            currentLayout.leftToLeft = constraintLayout.id
            currentLayout.goneEndMargin = 25
            currentLayout.goneStartMargin = 25
            currentLayout.goneTopMargin = -article_title.height + 10
            article_title.layoutParams = currentLayout

            article_title.setPadding(
                12,
                12,
                12,
                12
            )

            constraintLayout.addView(article_title)

            //display author below title
            article_author = TextView(context)
            article_author.id = View.generateViewId()
            article_author.text = "By: " + article.author
            article_author.textSize = 14F
            article_author.setTextColor(Color.parseColor("#FFFFFF"))
            article_author.layoutParams = ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
            )

            (article_author.layoutParams as ConstraintLayout.LayoutParams).setMargins(40, 10, 40, 0)


            currentLayout = article_author.layoutParams as ConstraintLayout.LayoutParams
            currentLayout.topToBottom = article_title.id
            article_author.layoutParams = currentLayout

            article_author.setPadding(
                12,
                12,
                12,
                12
            )


            constraintLayout.addView(article_author)

            //display date in proper format below author
            article_date = TextView(context)
            article_date.id = View.generateViewId()
            article_date.text = Helpers.formatDateTime(article.date_published)
            article_date.textSize = 14F
            article_date.setTextColor(Color.parseColor("#FFFFFF"))
            article_date.layoutParams = ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
            )

            (article_date.layoutParams as ConstraintLayout.LayoutParams).setMargins(40, 0, 40, 0)


            currentLayout = article_date.layoutParams as ConstraintLayout.LayoutParams
            currentLayout.topToBottom = article_author.id
            article_date.layoutParams = currentLayout

            article_date.setPadding(
                12,
                12,
                12,
                12
            )


            constraintLayout.addView(article_date)

            //display the content of the article in webview
            article_content = WebView(context)
            article_content.id = View.generateViewId()
            article_content.settings.javaScriptEnabled = true
            article_content.setBackgroundColor(Color.parseColor("#000000"))
            article_content.loadDataWithBaseURL(null, "<font color='white'><style>a{color: rgb(68, 188, 216); text-decoration:none}</style><style>p{color: white; text-decoration:none}</style><style>td{color: white; text-decoration:none}</style>" + article.content_html, "text/html", "UTF-8", null)
            article_content.layoutParams = ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
            )

            (article_content.layoutParams as ConstraintLayout.LayoutParams).setMargins(40, 50, 40, 0)


            currentLayout = article_content.layoutParams as ConstraintLayout.LayoutParams
            currentLayout.topToBottom = article_date.id
            article_content.layoutParams = currentLayout

            article_content.setPadding(
                12,
                12,
                12,
                12
            )


            constraintLayout.addView(article_content)
            scrollView.addView(constraintLayout)


            return scrollView
        }
}
