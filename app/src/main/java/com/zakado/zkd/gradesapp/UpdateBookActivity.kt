package com.zakado.zkd.gradesapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.zakado.zkd.gradesapp.model.Book
import com.zakado.zkd.gradesapp.databinding.ActivityUpdateBookBinding
import com.zakado.zkd.gradesapp.db.BooksDatabaseHelper
import com.zakado.zkd.gradesapp.utils.Utils

class UpdateBookActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateBookBinding
    private lateinit var db: BooksDatabaseHelper

    private var bookId: Int = -1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUpdateBookBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = BooksDatabaseHelper(this)

        bookId = intent.getIntExtra("book_id", -1)

        if (bookId == -1) {
            finish()
            return
        }

        val book = db.getBookByID(bookId)
        binding.updateNameEditText.setText(book.name)
        binding.updateNoteEditText.setText(book.note.toString())
        binding.updateSaveButton.setOnClickListener {
            //Actualizacion de la info de una calificacion.
            val newName = binding.updateNameEditText.text.toString()
            val newNote = binding.updateNoteEditText.text.toString()
            val category = Utils.gradesClasification(newNote.toDouble())
            db.updateBook(Book(bookId, newName, newNote.toDouble(), category))
            finish()
            Toast.makeText(this, "Changes saved", Toast.LENGTH_SHORT).show()
        }
    }
}