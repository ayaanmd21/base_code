package com.dehaat.dehaatassignment.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.dehaat.dehaatassignment.R;
import com.dehaat.dehaatassignment.adapter.AuthorAdapter;
import com.dehaat.dehaatassignment.adapter.BookAdapter;
import com.dehaat.dehaatassignment.adapter.ItemClickListener;
import com.dehaat.dehaatassignment.database.AppDatabase;
import com.dehaat.dehaatassignment.databinding.ActivityMainBinding;
import com.dehaat.dehaatassignment.model.Author;
import com.dehaat.dehaatassignment.model.AuthorsViewModel;
import com.dehaat.dehaatassignment.utils.SharedPrefUtils;

import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding mainBinding;
    private AuthorAdapter authorAdapter;
    private BookAdapter bookAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = mainBinding.getRoot();
        setContentView(view);
        ViewModelProvider.AndroidViewModelFactory viewModelFactory = new ViewModelProvider.AndroidViewModelFactory(getApplication());
        AuthorsViewModel authorsViewModel = new ViewModelProvider(this, viewModelFactory).get(AuthorsViewModel.class);
        setSupportActionBar(mainBinding.toolbar);
        authorAdapter = new AuthorAdapter();
        mainBinding.recyclerviewAuthor.setLayoutManager(new LinearLayoutManager(this));
        mainBinding.recyclerviewAuthor.setAdapter(authorAdapter);
        mainBinding.progressCircular.setVisibility(View.VISIBLE);
        authorsViewModel.fetchAAuthors();
        authorsViewModel.getAuthorsResponseMutableLiveData().observe(this, new Observer<List<Author>>() {
            @Override
            public void onChanged(List<Author> authors) {
                if (authors != null) {
                    mainBinding.progressCircular.setVisibility(View.GONE);
                    authorAdapter.setAuthorList(authors);
                    //save into db
                    new SaveDataIntoDB(authors,MainActivity.this).execute();


                    if(getOrientation()==Configuration.ORIENTATION_LANDSCAPE){
                        bookAdapter.setBookList(authorAdapter.getAuthorList().get(0).getBooks());
                    }

                }
            }
        });

        authorAdapter.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(int position) {
               if(getOrientation()==Configuration.ORIENTATION_LANDSCAPE){
                   bookAdapter.setBookList(authorAdapter.getAuthorList().get(position).getBooks());

               }else {
                   Intent books = new Intent(MainActivity.this, BooksActivity.class);
                   books.putExtra(BooksActivity.ITEM, (Serializable) authorAdapter.getAuthorList().get(position).getBooks());
                   startActivity(books);
               }
            }
        });
        handleOrientation();

    }

    private int getOrientation(){
        return getResources().getConfiguration().orientation;

    }

    private void handleOrientation(){
        if(getOrientation()==Configuration.ORIENTATION_LANDSCAPE){
            bookAdapter = new BookAdapter();
            mainBinding.recyclerviewBook.setLayoutManager(new LinearLayoutManager(this));
            mainBinding.recyclerviewBook.setAdapter(bookAdapter);

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_logout) {
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


    private static class SaveDataIntoDB extends AsyncTask<Void,Void,Void>{

        private List<Author> authorList;
        private AppDatabase appDatabase;
        private WeakReference<MainActivity> activityReference;

        public SaveDataIntoDB(List<Author> authorList, MainActivity context) {
            this.authorList = authorList;
            activityReference=new WeakReference<>(context);
            appDatabase=AppDatabase.getDatabase(activityReference.get());
        }

        @Override
        protected Void doInBackground(Void... voids) {
            for (Author author: authorList){
                 appDatabase.authorDao().insert(author);
                 appDatabase.bookDao().insertAll(author.getBooks());

            }


            return null;
        }
    }

}
