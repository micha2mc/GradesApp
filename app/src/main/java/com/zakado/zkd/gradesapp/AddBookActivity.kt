package com.zakado.zkd.gradesapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.zakado.zkd.gradesapp.model.Book
import com.zakado.zkd.gradesapp.databinding.ActivityAddBookBinding
import com.zakado.zkd.gradesapp.db.BooksDatabaseHelper
import com.zakado.zkd.gradesapp.utils.Utils

class AddBookActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddBookBinding
    private lateinit var db: BooksDatabaseHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBookBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = BooksDatabaseHelper(this)

        binding.saveButton.setOnClickListener {
            //Guardar una nueva calificación en la BBDD
            val name = binding.nameEditText.text.toString()
            val note = binding.noteEditText.text.toString()
            val category = Utils.gradesClasification(note.toDouble())
            val book = Book(0, name, note.toDouble(), category)

            db.insertBook(book)
            finish()
            Toast.makeText(this, "Saved grades", Toast.LENGTH_SHORT).show()
        }
    }
}