package com.dehaat.dehaatassignment.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dehaat.dehaatassignment.databinding.AuthorsItemBinding;
import com.dehaat.dehaatassignment.model.Author;

import java.util.ArrayList;
import java.util.List;

public class AuthorAdapter extends RecyclerView.Adapter<AuthorAdapter.AuthorViewHolder> {
    private AuthorsItemBinding authorsItemBinding;
    private List<Author> authorList = new ArrayList<>();
    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void setAuthorList(List<Author> authorList) {
        this.authorList=authorList;
        notifyItemInserted(authorList.size());

    }

    public List<Author> getAuthorList() {
        return authorList;
    }

    @NonNull
    @Override
    public AuthorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        authorsItemBinding = AuthorsItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new AuthorViewHolder(authorsItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull final AuthorViewHolder holder, final int position) {
        Author author = authorList.get(position);
        holder.authorsItemBinding.tvAuthorName.setText(author.getAuthorName());
        holder.authorsItemBinding.tvBio.setText(author.getAuthorBio());
        holder.authorsItemBinding.tvBio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                holder.authorsItemBinding.tvBio.toggle();

            }
        });
        holder.authorsItemBinding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null) {
                    itemClickListener.onClick(position);
                }
            }
        });


    }

    @Override
    public int getItemCount() {

        return Math.max(authorList.size(), 0);
    }

    static class AuthorViewHolder extends RecyclerView.ViewHolder {
        private AuthorsItemBinding authorsItemBinding;

        public AuthorViewHolder(@NonNull AuthorsItemBinding authorsItemBinding) {
            super(authorsItemBinding.getRoot());
            this.authorsItemBinding = authorsItemBinding;
        }
    }
}
