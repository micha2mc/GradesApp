package com.zakado.zkd.gradesapp.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.zakado.zkd.gradesapp.dao.Book

class BooksDatabaseHelper (context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){
    /**
     * Datos básicos de la BBDD
     */
    companion object {
        private const val DATABASE_NAME = "booksapp.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "allbooks"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_NOTE = "note"
        private const val COLUMN_CATEGORY = "category"

    }

    /**
     * Funcion que ejecuta la query de la creación de la BBDD
     */
    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery =
            "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_NAME TEXT, $COLUMN_NOTE REAL, $COLUMN_CATEGORY TEXT)"
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTableQuery)
        onCreate(db)
    }

    /**
     * Función para insertar datos en la BBDD
     */
    fun insertBook(book: Book) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, book.name)
            put(COLUMN_NOTE, book.note)
            put(COLUMN_CATEGORY, book.category)
        }
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    /**
     * Función de obtencion de todas las asignaturas existentes en la BBDD
     */
    fun getAllBooks(): List<Book> {
        val booksList = mutableListOf<Book>();
        val db = readableDatabase;
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(query, null);

        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
            val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
            val note = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_NOTE));
            val category = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY));
            booksList.add(Book(id, name, note, category));
        }
        cursor.close();
        db.close()
        return booksList;
    }

    /**
     * Función para actualizar información de una asignatura
     */
    fun updateBook(book: Book) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, book.name)
            put(COLUMN_NOTE, book.note)
            put(COLUMN_CATEGORY, book.category)
        }

        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(book.id.toString())

        db.update(TABLE_NAME, values, whereClause, whereArgs)
        db.close()
    }

    /**
     * Función de búsqueda de una única asignatura a partir de su ID
     */
    fun getBookByID(bookId: Int): Book {
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = $bookId"
        val cursor = db.rawQuery(query, null)
        //cogemos el primer elemento situando el cursor al principio.
        cursor.moveToFirst()

        val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
        val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
        val note = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_NOTE))
        val category = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY))

        cursor.close()
        db.close()
        return Book(id, name, note, category)
    }

    /**
     * Función que elimina una asignatura.
     */
    fun deleteBook(bookId: Int) {
        val db = writableDatabase
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(bookId.toString())

        db.delete(TABLE_NAME, whereClause, whereArgs)
        db.close()
    }
}