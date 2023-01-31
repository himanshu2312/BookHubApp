package com.himanshu.bookhub.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.himanshu.bookhub.R
import com.himanshu.bookhub.activity.DescriptionActivity
import com.himanshu.bookhub.model.Book
import com.squareup.picasso.Picasso

class DashboardRecyclerAdapter(private val context: Context,private val bookList: ArrayList<Book>): RecyclerView.Adapter<DashboardRecyclerAdapter.DashboardViewHolder>() {

    class DashboardViewHolder(view: View): RecyclerView.ViewHolder(view){
            var bookName:TextView=view.findViewById(R.id.txtBookName)
            var bookAuthor:TextView=view.findViewById(R.id.txtBookAuthor)
            var bookPrice:TextView=view.findViewById(R.id.txtBookPrice)
            var bookRating:TextView=view.findViewById(R.id.txtBookRating)
            var bookImage:ImageView=view.findViewById(R.id.imgBookImage)
            var content: LinearLayout=view.findViewById(R.id.llContent)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_books_single_row,parent,false)

        return DashboardViewHolder(view)
    }

    override fun onBindViewHolder(holder: DashboardViewHolder, position: Int) {
        val book=bookList[position]
        holder.bookName.text=book.bookName
        holder.bookPrice.text=book.bookPrice
        holder.bookAuthor.text=book.bookAuthor
        holder.bookRating.text=book.bookRating
        Picasso.get().load(book.bookImage).error(R.drawable.default_book_cover).into(holder.bookImage)

        holder.content.setOnClickListener{
            val intent= Intent(context,DescriptionActivity::class.java)
            intent.putExtra("book_id",book.bookId)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return bookList.size
    }
}