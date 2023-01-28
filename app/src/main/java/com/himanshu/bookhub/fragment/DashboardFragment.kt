package com.himanshu.bookhub.fragment

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.himanshu.bookhub.R
import com.himanshu.bookhub.adapter.DashboardRecyclerAdapter
import com.himanshu.bookhub.model.Book
import com.himanshu.bookhub.util.ConnectionManager


class DashboardFragment : Fragment() {
    private lateinit var recyclerDashboard: RecyclerView
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var recyclerAdapter: DashboardRecyclerAdapter
    private lateinit var btnCheckConnection: Button
    private var bookInfoList= arrayListOf(
        Book("P.S. I love You", "Cecelia Ahern", "Rs. 299", "4.5", R.drawable.ps_ily),
        Book("The Great Gatsby", "F. Scott Fitzgerald", "Rs. 399", "4.1", R.drawable.great_gatsby),
        Book("Anna Karenina", "Leo Tolstoy", "Rs. 199", "4.3", R.drawable.anna_kare),
        Book("Madame Bovary", "Gustave Flaubert", "Rs. 500", "4.0", R.drawable.madame),
        Book("War and Peace", "Leo Tolstoy", "Rs. 249", "4.8", R.drawable.war_and_peace),
        Book("Lolita", "Vladimir Nabokov", "Rs. 349", "3.9", R.drawable.lolita),
        Book("MiddleMarch", "George Eliot", "Rs. 599", "4.2", R.drawable.middlemarch),
        Book("The Adventures of Huckleberry Finn", "Mark Twain", "Rs. 699", "4.5", R.drawable.adventures_finn),
        Book("Moby-Dick", "Herman Melville", "Rs. 499", "4.5", R.drawable.moby_dick),
        Book("The Lord of the Rings", "J.R.R Tolkien", "Rs. 749", "5.0", R.drawable.lord_of_rings)

    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_dashboard, container, false)

        btnCheckConnection=view.findViewById(R.id.btnCheckConnection)
        recyclerDashboard=view.findViewById(R.id.recyclerDashboard)
        layoutManager= LinearLayoutManager(activity)
        recyclerAdapter= DashboardRecyclerAdapter(activity as Context,bookInfoList)

        recyclerDashboard.adapter= recyclerAdapter
        recyclerDashboard.layoutManager= layoutManager
        val dividerItemDecoration=DividerItemDecoration(recyclerDashboard.context,(layoutManager as LinearLayoutManager).orientation)
        recyclerDashboard.addItemDecoration(dividerItemDecoration)

        btnCheckConnection.setOnClickListener{
            if (ConnectionManager().checkConnectivity(activity as Context)){
                val dialog = AlertDialog.Builder(activity as Context)
                dialog.setTitle("Success")
                dialog.setMessage("Internet Connection found")
                dialog.setPositiveButton("Ok"){
                        text, listener ->
                }
                dialog.setNegativeButton("Cancel"){
                    text,listener->
                }
                dialog.create()
                dialog.show()
            }
            else{
                val dialog = AlertDialog.Builder(activity as Context)
                dialog.setTitle("Error")
                dialog.setMessage("Internet Connection is not found")
                dialog.setPositiveButton("Ok"){
                        text, listener ->
                }
                dialog.setNegativeButton("Cancel"){
                        text,listener->
                }
                dialog.create()
                dialog.show()
            }
        }

        return view
    }

}