// Generated by view binder compiler. Do not edit!
package com.hemenway.thecardcatalog.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import com.hemenway.thecardcatalog.R;
import java.lang.NullPointerException;
import java.lang.Override;

public final class ContentAuthorDetailsWithBooksBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  private ContentAuthorDetailsWithBooksBinding(@NonNull ConstraintLayout rootView) {
    this.rootView = rootView;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ContentAuthorDetailsWithBooksBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ContentAuthorDetailsWithBooksBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.content_author_details_with_books, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ContentAuthorDetailsWithBooksBinding bind(@NonNull View rootView) {
    if (rootView == null) {
      throw new NullPointerException("rootView");
    }

    return new ContentAuthorDetailsWithBooksBinding((ConstraintLayout) rootView);
  }
}
