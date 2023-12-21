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
import com.hemenway.thecardcatalog.entities.Book;
import com.hemenway.thecardcatalog.entities.BookNumber;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder>{

    class BookViewHolder extends RecyclerView.ViewHolder{
        private final TextView bookListItem;


        private BookViewHolder(View itemView) {
            super(itemView);
            bookListItem = itemView.findViewById(R.id.bookListItem);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int position = getAdapterPosition();
                    final Book current = mBook.get(position);

                    Intent intent = new Intent(context, BookDetailedView.class);

                    intent.putExtra("authorId", current.getAuthorId());
                    intent.putExtra("bookId", current.getBookId());
                    intent.putExtra("bookTitle", current.getBookTitle());
                    intent.putExtra("bookPublisher", current.getBookPublisher());
                    intent.putExtra("bookFormat", current.getBookFormat());
                    intent.putExtra("bookIsbn", current.getIsbn());


                    context.startActivity(intent);
                }

            });
        }
    }

    private final LayoutInflater mInflater;

    private final Context context;

    private List<Book> mBook;

    public BookAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public BookAdapter.BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.activity_book_list_item, parent,false);
        return new BookAdapter.BookViewHolder((itemView));
    }

    @Override
    public void onBindViewHolder(@NonNull BookAdapter.BookViewHolder holder, int position) {

        if(mBook != null) {
            Book current = mBook.get(position);
            String title =  current.getBookTitle();
            holder.bookListItem.setText(title);
        }else{
            holder.bookListItem.setText("No Title");
        }
    }

    public void setAuthorFirstName(List<Book> firstName) {
        mBook = firstName;
        notifyDataSetChanged();
    }

    public void setAuthorLastName(List<Book> lastName) {
        mBook = lastName;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount(){
        if(mBook != null)
            return mBook.size();
        else return 0;
    }

    public void setBooks (List<Book> books){
        mBook = books;
        notifyDataSetChanged();
    }
}