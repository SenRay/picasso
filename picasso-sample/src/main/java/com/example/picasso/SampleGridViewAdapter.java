package com.example.picasso;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.squareup.picasso3.provider.PicassoProvider;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.widget.ImageView.ScaleType.CENTER_CROP;
import static com.example.picasso.Data.URLS;

final class SampleGridViewAdapter extends BaseAdapter {
  private final Context context;
  private final List<String> urls = new ArrayList<>();

  SampleGridViewAdapter(Context context) {
    this.context = context;

    // Ensure we get a different ordering of images on each run.
    Collections.addAll(urls, URLS);
    Collections.shuffle(urls);

    // Triple up the list.
    ArrayList<String> copy = new ArrayList<>(urls);
    urls.addAll(copy);
    urls.addAll(copy);
  }

  @Override public View getView(int position, View convertView, ViewGroup parent) {
    SquaredImageView view = (SquaredImageView) convertView;
    if (view == null) {
      view = new SquaredImageView(context);
      view.setScaleType(CENTER_CROP);
    }

    // Get the image URL for the current position.
    int resourceId = getItem(position);

    // Trigger the download of the URL asynchronously into the image view.
    PicassoProvider.get() //
        .load(resourceId) //
        .placeholder(R.drawable.placeholder) //
        .error(R.drawable.error) //
        .fit() //
        .tag(context) //
        .into(view);

    return view;
  }

  @Override public int getCount() {
    return URLS.length;
  }

  @Override public Integer getItem(int position) {
    return position % 4 == 1 ? R.drawable.animated_clock
        : position % 2 == 1 ? R.drawable.vector : R.drawable.icon;
  }

  @Override public long getItemId(int position) {
    return position;
  }
}
