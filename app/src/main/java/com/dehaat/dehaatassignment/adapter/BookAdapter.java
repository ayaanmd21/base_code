package com.dehaat.dehaatassignment.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dehaat.dehaatassignment.databinding.AuthorsItemBinding;
import com.dehaat.dehaatassignment.databinding.ItemBooksBinding;
import com.dehaat.dehaatassignment.model.Author;
import com.dehaat.dehaatassignment.model.Book;

import java.util.ArrayList;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BooksViewHolder> {
    private ItemBooksBinding itemBooksBinding;
    private List<Book> bookList = new ArrayList<>();

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BooksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        itemBooksBinding = ItemBooksBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new BookAdapter.BooksViewHolder(itemBooksBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull final BooksViewHolder holder, int position) {
        Book book = bookList.get(position);
        holder.itemBooksBinding.tvBookTitle.setText(book.getTitle());
        holder.itemBooksBinding.tvDescription.setText(book.getDescription());
        holder.itemBooksBinding.tvPrice.setText(book.getPrice());
        holder.itemBooksBinding.tvPublisher.setText(book.getPublisher());
        holder.itemBooksBinding.tvPublishedDate.setText(book.getPublishedDate());
        holder.itemBooksBinding.tvExpand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.itemBooksBinding.layoutCollapse.setVisibility(View.VISIBLE);
                holder.itemBooksBinding.tvExpand.setVisibility(View.GONE);

            }
        });


    }

    @Override
    public int getItemCount() {
        return Math.max(bookList.size(), 0);
    }

    static class BooksViewHolder extends RecyclerView.ViewHolder {
        private ItemBooksBinding itemBooksBinding;

        public BooksViewHolder(@NonNull ItemBooksBinding itemBooksBinding) {
            super(itemBooksBinding.getRoot());
            this.itemBooksBinding = itemBooksBinding;
        }
    }
}
