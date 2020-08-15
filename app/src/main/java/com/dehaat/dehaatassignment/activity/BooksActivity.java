package com.dehaat.dehaatassignment.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.dehaat.dehaatassignment.R;
import com.dehaat.dehaatassignment.adapter.AuthorAdapter;
import com.dehaat.dehaatassignment.adapter.BookAdapter;
import com.dehaat.dehaatassignment.databinding.ActivityBooksBinding;
import com.dehaat.dehaatassignment.model.Author;
import com.dehaat.dehaatassignment.model.AuthorsViewModel;
import com.dehaat.dehaatassignment.model.Book;
import com.dehaat.dehaatassignment.utils.SharedPrefUtils;

import java.util.List;

public class BooksActivity extends AppCompatActivity {
    private ActivityBooksBinding booksBinding;
    public static final String ITEM = "item";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        booksBinding = ActivityBooksBinding.inflate(getLayoutInflater());
        View view = booksBinding.getRoot();
        setContentView(view);
        setSupportActionBar(booksBinding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.app_name));
            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE | ActionBar.DISPLAY_HOME_AS_UP);
        }

        List<Book> books = (List<Book>) getIntent().getSerializableExtra(ITEM);

        final BookAdapter bookAdapter = new BookAdapter();
        booksBinding.recyclerview.setLayoutManager(new LinearLayoutManager(this));
        booksBinding.recyclerview.setAdapter(bookAdapter);
        bookAdapter.setBookList(books);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        } else if (item.getItemId() == R.id.menu_logout) {
            Toast.makeText(this, "logout successfully", Toast.LENGTH_SHORT).show();
            SharedPrefUtils.saveData(getApplicationContext(), SharedPrefUtils.TOKEN, null);
            Intent mIntent = new Intent(getApplicationContext(), LoginActivity.class);
            mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(mIntent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}