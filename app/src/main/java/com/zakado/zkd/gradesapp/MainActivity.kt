package com.zakado.zkd.gradesapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.zakado.zkd.gradesapp.dao.Book
import com.zakado.zkd.gradesapp.databinding.ActivityMainBinding
import com.zakado.zkd.gradesapp.db.BooksAdapter
import com.zakado.zkd.gradesapp.db.BooksDatabaseHelper
import com.zakado.zkd.gradesapp.utils.Utils
import java.math.RoundingMode
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var db: BooksDatabaseHelper
    private lateinit var booksAdapter: BooksAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = BooksDatabaseHelper(this)
        val listBooks = db.getAllBooks()
        booksAdapter = BooksAdapter(listBooks, this)

        //intercambio de informacion con View
        binding.booksRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.booksRecyclerView.adapter = booksAdapter
        binding.booksMedia.text = Utils.calcularNotaMedia(listBooks)

        binding.addButton.setOnClickListener {
            //lanzamiento de la actividad AddBookActivity
            val intent = Intent(this, AddBookActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onResume() {
        super.onResume()
        binding.booksMedia.text = Utils.calcularNotaMedia(db.getAllBooks())
        booksAdapter.refreshData(db.getAllBooks())
    }
}