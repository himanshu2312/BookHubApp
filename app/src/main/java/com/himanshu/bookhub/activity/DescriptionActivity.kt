@file:Suppress("DEPRECATION")

package com.himanshu.bookhub.activity

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.room.Room
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import com.himanshu.bookhub.R
import org.json.JSONObject
import com.android.volley.toolbox.JsonObjectRequest
import com.himanshu.bookhub.database.BookDatabase
import com.himanshu.bookhub.database.BookEntity
import com.himanshu.bookhub.util.ConnectionManager
import com.squareup.picasso.Picasso
import org.json.JSONException

@Suppress("DEPRECATION")
class DescriptionActivity : AppCompatActivity() {
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var txtBookName: TextView
    private lateinit var txtBookPrice: TextView
    private lateinit var txtBookRating: TextView
    private lateinit var txtBookAuthor: TextView
    private lateinit var imgBookImage: ImageView
    private lateinit var txtBookDes: TextView
    private lateinit var btnAddToFav: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var progressBarLayout: RelativeLayout
    private var booId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Book Details"
        txtBookAuthor = findViewById(R.id.txtBookAuthor)
        txtBookName = findViewById(R.id.txtBookName)
        txtBookRating = findViewById(R.id.txtBookRating)
        txtBookPrice = findViewById(R.id.txtBookPrice)
        txtBookDes = findViewById(R.id.txtBookDescription)
        imgBookImage = findViewById(R.id.imgBookImage)
        btnAddToFav = findViewById(R.id.btnAddToFav)
        progressBar = findViewById(R.id.progressBar)
        progressBar.visibility = View.VISIBLE
        progressBarLayout = findViewById(R.id.progressBarLayout)
        progressBarLayout.visibility = View.VISIBLE

        booId = intent.getStringExtra("book_id")
        if (booId == null) {
            Toast.makeText(
                this@DescriptionActivity,
                "Some error occurred!!",
                Toast.LENGTH_SHORT
            )
                .show()
            finish()
        } else {
            val queue = Volley.newRequestQueue(this@DescriptionActivity)
            val url = "http://13.235.250.119/v1/book/get_book/"
            val jsonObject = JSONObject()
            jsonObject.put("book_id", booId)
            if (ConnectionManager().checkConnectivity(this@DescriptionActivity)) {
                val jsonRequest = object : JsonObjectRequest(
                    Method.POST, url, jsonObject,
                    Response.Listener {
                        try {
                            if (it.getBoolean("success")) {
                                val bookData = it.getJSONObject("book_data")
                                txtBookName.text = bookData.getString("name")
                                txtBookPrice.text = bookData.getString("price")
                                txtBookRating.text = bookData.getString("rating")
                                txtBookAuthor.text = bookData.getString("author")
                                txtBookDes.text = bookData.getString("description")
                                Picasso.get().load(bookData.getString("image"))
                                    .error(R.drawable.default_book_cover).into(imgBookImage)
                                progressBarLayout.visibility = View.GONE

                                val bookEntity = BookEntity(
                                    booId?.toInt() as Int,
                                    bookData.getString("name"),
                                    bookData.getString("author"),
                                    bookData.getString("price"),
                                    bookData.getString("rating"),
                                    bookData.getString("image"),
                                    bookData.getString("description")
                                )
                                if (PerformOnEntity(applicationContext, bookEntity,1).execute().get()){
                                    switchButtonText()
                                }
                                btnAddToFav.setOnClickListener {
                                    if (!PerformOnEntity(applicationContext, bookEntity,1).execute().get()) {
                                        try {
                                            PerformOnEntity(applicationContext, bookEntity,2).execute()
                                            Toast.makeText(
                                                this@DescriptionActivity,
                                                "Book added to Favorites",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            switchButtonText()
                                        } catch (e: java.lang.Error) {
                                            Toast.makeText(
                                                this@DescriptionActivity,
                                                "Error : $e",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    } else {
                                        try {
                                            PerformOnEntity(applicationContext, bookEntity,3).execute()
                                            Toast.makeText(
                                                this@DescriptionActivity,
                                                "Book removed from Favorites",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            switchButtonText()
                                        } catch (e: java.lang.Error) {
                                            Toast.makeText(
                                                this@DescriptionActivity,
                                                "Error : $e",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    }
                                }

                            } else {
                                Toast.makeText(
                                    this@DescriptionActivity,
                                    "Not able to fetch Book details!!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } catch (e: JSONException) {
                            Toast.makeText(
                                this@DescriptionActivity,
                                "Json error occurred!!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    },
                    Response.ErrorListener {
                        Toast.makeText(
                            this@DescriptionActivity,
                            "Volley error occurred!!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }) {
                    override fun getHeaders(): MutableMap<String, String> {
                        val headers = HashMap<String, String>()
                        headers["Content-Type"] = "application/json"
                        headers["token"] = "56e490debc1169"
                        return headers
                    }
                }
                queue.add(jsonRequest)
            } else {
                val dialog = AlertDialog.Builder(this@DescriptionActivity)
                dialog.setTitle("Error")
                dialog.setMessage("Internet Connection is not found")
                dialog.setPositiveButton("Open Settings") { text, listener ->
                    val intent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
                    startActivity(intent)
                    finish()
                }
                dialog.setNegativeButton("Exit") { text, listener ->
                    ActivityCompat.finishAffinity(this@DescriptionActivity)
                }
                dialog.create()
                dialog.show()
            }

        }

    }

    fun switchButtonText() {
        if (btnAddToFav.text == getString(R.string.btn_add_to_fav)) {
            btnAddToFav.text = getString(R.string.btn_remove_from_fav)
            val btnColor = ContextCompat.getColor(applicationContext, R.color.favorite)
            btnAddToFav.setBackgroundColor(btnColor)
        } else {
            btnAddToFav.text = getString(R.string.btn_add_to_fav)
            val btnColor = ContextCompat.getColor(applicationContext, R.color.colorPrimary)
            btnAddToFav.setBackgroundColor(btnColor)
        }
    }

    class PerformOnEntity(context: Context, private val bookEntity: BookEntity, private val operation:Int):
        AsyncTask<Void, Void, Boolean>() {
        private val db = Room.databaseBuilder(context, BookDatabase::class.java, "favBook_db").build()

        @Deprecated("Deprecated in Java")
        override fun doInBackground(vararg p0: Void?): Boolean {

            when(operation){
                1-> {
                    val book: BookEntity? =
                        db.bookDao().getFavBookById(bookEntity.bookId.toString())
                    db.close()
                    return book!=null
                }

                2 -> {
                    db.bookDao().insertBook(bookEntity)
                    db.close()
                    return true
                }

                3 -> {
                    db.bookDao().deleteBook(bookEntity)
                    db.close()
                    return true
                }
            }
            return true
        }
    }
}