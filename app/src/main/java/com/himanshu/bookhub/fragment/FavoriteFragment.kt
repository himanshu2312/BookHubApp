@file:Suppress("DEPRECATION")

package com.himanshu.bookhub.fragment

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.himanshu.bookhub.R
import com.himanshu.bookhub.adapter.FavoriteRecyclerAdapter
import com.himanshu.bookhub.database.BookDatabase
import com.himanshu.bookhub.database.BookEntity

class FavoriteFragment : Fragment() {
    private lateinit var recyclerFavorite: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var progressBarLayout: RelativeLayout
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var recyclerAdapter: FavoriteRecyclerAdapter
    private var favBookList= listOf<BookEntity>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_favorite, container, false)
        recyclerFavorite = view.findViewById(R.id.recyclerFavorite)
        progressBar = view.findViewById(R.id.progressBar)
        progressBarLayout = view.findViewById(R.id.progressBarLayout)
        progressBarLayout.visibility=View.VISIBLE
        progressBar.visibility=View.VISIBLE
        if (activity != null) {
            layoutManager = GridLayoutManager(activity as Context, 2)
            recyclerFavorite.layoutManager = layoutManager
            favBookList = FavBooksData(activity as Context).execute().get()
            println(favBookList)
            progressBar.visibility=View.GONE
            progressBarLayout.visibility=View.GONE
            recyclerAdapter = FavoriteRecyclerAdapter(activity as Context,favBookList)
            recyclerFavorite.adapter = recyclerAdapter
        }
        return view
    }

    @Suppress("DEPRECATION")
    class FavBooksData(context: Context) : AsyncTask<Void, Void, List<BookEntity>>() {

        private val db = Room.databaseBuilder(context, BookDatabase::class.java, "favBook_db").build()
        @Deprecated("Deprecated in Java")
        override fun doInBackground(vararg p0: Void?): List<BookEntity> {
            return db.bookDao().getFavBooks()
        }
    }

}