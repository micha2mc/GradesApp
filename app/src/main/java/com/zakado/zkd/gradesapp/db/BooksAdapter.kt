package com.zakado.zkd.gradesapp.db

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.zakado.zkd.gradesapp.R
import com.zakado.zkd.gradesapp.UpdateBookActivity
import com.zakado.zkd.gradesapp.databinding.ActivityMainBinding
import com.zakado.zkd.gradesapp.model.Book
import com.zakado.zkd.gradesapp.utils.Utils

class BooksAdapter(
    private var books: List<Book>,
    private var binding: ActivityMainBinding,
    context: Context
) :
    RecyclerView.Adapter<BooksAdapter.BookViewHolder>() {

    private val db: BooksDatabaseHelper = BooksDatabaseHelper(context)

    class BookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        val noteTextView: TextView = itemView.findViewById(R.id.noteTextView)
        val categoryTextView: TextView = itemView.findViewById(R.id.categoryTextView)
        val updateButton: ImageView = itemView.findViewById(R.id.updateButton)
        val deleteButton: ImageView = itemView.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.book_item, parent, false)
        return BookViewHolder(view)
    }

    override fun getItemCount(): Int = books.size

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = books[position]
        holder.nameTextView.text = book.name
        holder.noteTextView.text = book.note.toString()
        holder.categoryTextView.text = book.category

        holder.updateButton.setOnClickListener {
            //lanzamiento de la actividad UpdateBookActivity
            val intent = Intent(holder.itemView.context, UpdateBookActivity::class.java).apply {
                putExtra("book_id", book.id)
            }
            holder.itemView.context.startActivity(intent)
        }

        holder.deleteButton.setOnClickListener {
            db.deleteBook(book.id)
            refreshData(db.getAllBooks())
            Toast.makeText(holder.itemView.context, "Deleted grades", Toast.LENGTH_SHORT)
                .show()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun refreshData(newBooks: List<Book>) {
        books = newBooks
        //Actualizamos el calculo y mensaje de la nota media
        binding.booksMedia.text = Utils.calcularNotaMedia(books)
        notifyDataSetChanged()
    }
}