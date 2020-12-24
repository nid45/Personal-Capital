package com.example.personalcapitaltechnicalchallenge.adapters


import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.personalcapitaltechnicalchallenge.ui.ArticleFragment
import com.example.personalcapitaltechnicalchallenge.helpers.Helpers
import com.example.personalcapitaltechnicalchallenge.helpers.MediaContentLoader
import com.example.personalcapitaltechnicalchallenge.models.Article


class CustomRecyclerViewAdapter
/**
 * the main recycler view adapter constructor will take in several parameters to build the dynamic
 * recycler view with all the data from the rss article list
 *
 * @param context             activity context
 * @param articleList            list of article items from the rss article
 * @param onArticleClickListener onclick listener to handle webview transitions
 */

internal constructor(
    private var context: Context?,
    private var articleList: List<Article>?,
    var activity: FragmentActivity,
    constraintLayout: RelativeLayout
) : RecyclerView.Adapter<ViewHolder>() {
    lateinit var cardView: CardView
    lateinit var linearLayout: LinearLayout
    lateinit var article_image: ImageView
    lateinit var article_description: TextView
    lateinit var article_title: TextView
    var mainLayout: RelativeLayout = constraintLayout


    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            MAIN -> MainViewHolder(
                mainArticle
            )
            NORMAL -> NormalViewHolder(
                normalArticle
            )
            else -> NormalViewHolder(normalArticle)
        }
    }



    override fun getItemId(position: Int): Long {
        return position.toLong()
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article: Article = articleList!![position]

        val mainArticleHolder: MainViewHolder
        val normalArticleHolder: NormalViewHolder
        when (holder.itemViewType) {
            MAIN -> {
                mainArticleHolder = holder as MainViewHolder
                mainArticleHolder.title.text = Helpers.fromHtml(article.title)
                val fullDescription: String =
                    (Helpers.formatDateTime(article.date_published)
                            + Helpers.fromHtml(" &mdash; ")
                            + Helpers.fromHtml(article.summary))
                mainArticleHolder.description.text = fullDescription.trim { it <= ' ' }
                MediaContentLoader(
                    mainArticleHolder.media_content,
                    article.featured_image
                ).execute()
                mainArticleHolder.view.setOnClickListener { v ->
                    val arguments = Bundle()
                    arguments.putSerializable("item", article)
                    val fm: FragmentManager = activity.supportFragmentManager
                    val fragment: Fragment =
                        ArticleFragment()
                    fragment.arguments = arguments

                    fm.beginTransaction()
                        .replace(mainLayout.id, fragment)
                        .addToBackStack("mainfrag")
                        .commit()

                }
            }
            NORMAL -> {
                normalArticleHolder = holder as NormalViewHolder
                normalArticleHolder.title.text = Helpers.fromHtml(article.title)
                MediaContentLoader(
                    normalArticleHolder.media_content,
                    article.featured_image
                ).execute()
                normalArticleHolder.view.setOnClickListener { v ->
                    val arguments = Bundle()
                    arguments.putSerializable("item", article)
                    val fm: FragmentManager = activity.supportFragmentManager
                    val fragment: Fragment =
                        ArticleFragment()
                    fragment.arguments = arguments

                    fm.beginTransaction()
                        .replace(mainLayout.id, fragment)
                        .addToBackStack("mainfrag")
                        .commit()
                }
            }else->{
            normalArticleHolder = holder as NormalViewHolder
            normalArticleHolder.title.text = Helpers.fromHtml(article.title)
            MediaContentLoader(
                normalArticleHolder.media_content,
                article.featured_image
            ).execute()
            normalArticleHolder.view.setOnClickListener { v ->
                val arguments = Bundle()
                arguments.putSerializable("item", article)
                val fm: FragmentManager = activity.supportFragmentManager
                val fragment: Fragment =
                    ArticleFragment()
                fragment.arguments = arguments

                fm.beginTransaction()
                    .replace(mainLayout.id, fragment)
                    .addToBackStack("mainfrag")
                    .commit()
            }
        }
        }
    }

    override fun getItemCount(): Int {
        return if (articleList != null) {
            articleList!!.size
        } else {
            0
        }
    }


    private val mainArticle: CardView
        get() {

            cardView = CardView(context!!)
            cardView.isClickable = true
            cardView.elevation = 2F
            cardView.setCardBackgroundColor(Color.parseColor("#000000"))
            cardView.setContentPadding(
                3, 3, 3, 3
            )
            val cardLayoutParams: FrameLayout.LayoutParams = FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            cardLayoutParams.setMargins(
                2,
                2,
                2,
                2
            )
            cardView.layoutParams = cardLayoutParams

            linearLayout = LinearLayout(context)
            linearLayout.orientation = LinearLayout.VERTICAL
            linearLayout.gravity = Gravity.CENTER_HORIZONTAL
            linearLayout.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )

            article_image = ImageView(context)
            article_image.id = View.generateViewId()
            article_image.scaleType = ImageView.ScaleType.FIT_XY
            article_image.layoutParams = ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.MATCH_PARENT
            )

            article_image.adjustViewBounds = true
            linearLayout.addView(article_image)



            article_title = TextView(context)
            article_title.id = View.generateViewId()
            article_title.setLines(1)
            article_title.ellipsize = TextUtils.TruncateAt.END
            article_title.maxLines = 1
            article_title.textSize = 20F
            article_title.setTextColor(Color.parseColor("#FFFFFF"))
            article_title.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            article_title.setPadding(
                12,
                12,
                12,
                12
            )
            linearLayout.addView(article_title)


            article_description = TextView(context)
            article_description.id = View.generateViewId()
            article_description.ellipsize = TextUtils.TruncateAt.END
            article_description.setLines(2)
            article_description.maxLines = 2
            article_description.setTextColor(Color.parseColor("#FFFFFF"))
            article_description.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            article_description.setPadding(
                12,
                12,
                12,
                12
            )
            linearLayout.addView(article_description)
            cardView.addView(linearLayout)
            return cardView
        }



    private val normalArticle: CardView
        get() {
            val cardView = CardView(context!!)
            cardView.isClickable = true
            cardView.elevation = 2F
            cardView.radius = 0F
            cardView.setBackgroundColor(Color.parseColor("#add8e6"))

            cardView.setContentPadding(
                2,
                2,
                2,
                2
            )

            val cardLayoutParams: FrameLayout.LayoutParams = FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            cardLayoutParams.setMargins(
                20,
                20,
                20,
                0
            )

            cardView.layoutParams = cardLayoutParams

            linearLayout = LinearLayout(context)
            linearLayout.orientation = LinearLayout.VERTICAL
            linearLayout.gravity = Gravity.CENTER_HORIZONTAL
            linearLayout.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )

            article_image = ImageView(context)
            article_image.id = View.generateViewId()
            article_image.setBackgroundColor(Color.parseColor("#000000"))
            article_image.scaleType = ImageView.ScaleType.FIT_XY
            article_image.layoutParams = ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.MATCH_PARENT
            )

            article_image.adjustViewBounds = true
            linearLayout.addView(article_image)

            article_title = TextView(context)
            article_title.id = View.generateViewId()
            article_title.setLines(2)
            article_title.setBackgroundColor(Color.parseColor("#000000"))
            article_title.ellipsize = TextUtils.TruncateAt.END
            article_title.maxLines = 2
            article_title.textSize = 14F
            article_title.setTextColor(Color.parseColor("#FFFFFF"))
            article_title.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            article_title.setPadding(
                12,
                12,
                12,
                12
            )
            linearLayout.addView(article_title)
            cardView.addView(linearLayout)
            return cardView
        }




    inner class MainViewHolder(v: View) : ViewHolder(v) {
        var title: TextView
        var media_content: ImageView
        var description: TextView
        var view: View

        init {
            this.title = v.findViewById(article_title.id)
            this.media_content = v.findViewById(article_image.id)
            this.description = v.findViewById(article_description.id)
            view = v
        }
    }


    inner class NormalViewHolder(v: View) : ViewHolder(v) {
        var title: TextView
        var media_content: ImageView
        var view: View

        init {
            title = v.findViewById(article_title.id)
            media_content = v.findViewById(article_image.id)
            view = v
        }
    }

    companion object {
        private const val MAIN = 0
        private const val NORMAL = 1
    }
}
