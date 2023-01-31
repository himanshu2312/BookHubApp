package com.himanshu.bookhub.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface BookDao {
    @Insert
    fun insertBook(bookEntity: BookEntity)

    @Delete
    fun deleteBook(bookEntity: BookEntity)

    @Query("SELECT * FROM favBook")
    fun getFavBooks(): List<BookEntity>

    @Query("SELECT * FROM favBook WHERE book_id = :bookId")
    fun getFavBookById(bookId: String): BookEntity
}