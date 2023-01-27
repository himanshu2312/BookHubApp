package com.himanshu.bookhub.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.himanshu.bookhub.R
import com.himanshu.bookhub.adapter.DashboardRecyclerAdapter


class DashboardFragment : Fragment() {
    private lateinit var recyclerDashboard: RecyclerView
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private var bookList= arrayListOf(
        "P.S. I Love You",
        "The Great Gatsby",
        "Anna Karenina",
        "Madame Bovary",
        "Lolita",
        "MiddleMarch",
        "The Adventure of Huckleberry Finn",
        "Moby-Dick",
        "The Lord of the Rings",
        "Himanshu"
    )
    private lateinit var recyclerAdapter: DashboardRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_dashboard, container, false)

        recyclerDashboard=view.findViewById(R.id.recyclerDashboard)
        layoutManager= LinearLayoutManager(activity)
        recyclerAdapter= DashboardRecyclerAdapter(activity as Context,bookList)

        recyclerDashboard.adapter= recyclerAdapter
        recyclerDashboard.layoutManager= layoutManager

        return view
    }

}