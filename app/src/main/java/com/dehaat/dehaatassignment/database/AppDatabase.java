package com.dehaat.dehaatassignment.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.dehaat.dehaatassignment.dao.AuthorDao;
import com.dehaat.dehaatassignment.dao.BookDao;
import com.dehaat.dehaatassignment.model.Author;
import com.dehaat.dehaatassignment.model.Book;

@Database(entities = {Author.class, Book.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract AuthorDao authorDao();

    public abstract BookDao bookDao();

    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "dehaat_database").fallbackToDestructiveMigration().build();
                }
            }
        }
        return INSTANCE;
    }

}
