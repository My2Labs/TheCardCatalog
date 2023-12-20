package com.hemenway.thecardcatalog.ui;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hemenway.thecardcatalog.R;
import com.hemenway.thecardcatalog.entities.Author;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;


public class AuthorAdapter extends RecyclerView.Adapter<AuthorAdapter.AuthorViewHolder> {
    class AuthorViewHolder extends RecyclerView.ViewHolder {

        private final TextView authorListItem;

        private AuthorViewHolder(View itemView) {
            super(itemView);
            authorListItem = itemView.findViewById(R.id.authorListText);
            itemView.setOnClickListener(view -> {

                String myFormat = "MM/dd/yy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                int position = getAdapterPosition();
                final Author current = mAuthor.get(position);
                Intent intent = new Intent(context, AuthorDetailedView.class);

                intent.putExtra("authorId", current.getAuthorId());
                intent.putExtra("authorFirstName", current.getAuthorFirstName());
                intent.putExtra("authorLastName", current.getAuthorLastName());
                intent.putExtra("position", position);
                context.startActivity(intent);
            });
        }


    }


    private final LayoutInflater mInflater;
    private final Context context;
    private List<Author> mAuthor;

    public AuthorAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public AuthorAdapter.AuthorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.activity_author_list_item, parent, false);
        return new AuthorViewHolder((itemView));
    }

    @Override
    public void onBindViewHolder(@NonNull AuthorAdapter.AuthorViewHolder holder, int position) {

        if (mAuthor != null) {
            Author current = mAuthor.get(position);
            String authorFirstName = current.getAuthorFirstName();
            String authorLastName = current.getAuthorLastName();
            holder.authorListItem.setText(authorFirstName + " " + authorLastName);

        } else {
            holder.authorListItem.setText("No Title");
        }
    }

    public void setAuthorFirstName(List<Author> authorFirstName) {
        mAuthor = authorFirstName;
        notifyDataSetChanged();
    }

    public void setAuthorLastName(List<Author> authorLastName) {
        mAuthor = authorLastName;
        notifyDataSetChanged();
    }

    public void setAuthorList(List<Author> authorList) {
        mAuthor = authorList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mAuthor != null)
            return mAuthor.size();
        else return 0;
    }

    public void setAuthors(List<Author> authors) {
        mAuthor = authors;
        notifyDataSetChanged();
    }

}