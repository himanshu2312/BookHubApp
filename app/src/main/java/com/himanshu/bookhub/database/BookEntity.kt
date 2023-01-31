package com.himanshu.bookhub.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("favBook")
class BookEntity(
    @PrimaryKey @ColumnInfo(name = "book_id") private val bookId: Int,
    @ColumnInfo(name = "book_name")private val bookName: String,
    @ColumnInfo(name = "book_author")private val bookAuthor: String,
    @ColumnInfo(name = "book_price")private val bookPrice: String,
    @ColumnInfo(name = "book_rating")private val bookRating: String,
    @ColumnInfo(name = "book_image")private val bookImage: String,
    @ColumnInfo(name = "book_des")private val bookDes: String
)