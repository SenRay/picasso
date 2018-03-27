package com.squareup.picasso3;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import static com.squareup.picasso3.Picasso.LoadedFrom.DISK;

public class ResourceDrawableRequestHandler extends RequestHandler {
  private final Context context;
  private final DrawableLoader loader;

  ResourceDrawableRequestHandler(Context context, DrawableLoader loader) {
    this.context = context;
    this.loader = loader;
  }

  @Override public boolean canHandleRequest(Request data) {
    return data.resourceId != 0 && isXmlResource(context.getResources(), data.resourceId);
  }

  @Override
  public void load(Picasso picasso, Request request, int networkPolicy, Callback callback) {
    Drawable drawable = loader.load(request.resourceId);
    if (drawable == null) {
      callback.onError(new IllegalArgumentException(
          "invalid resId: " + Integer.toHexString(request.resourceId)));
    } else {
      callback.onSuccess(new Result(drawable, DISK));
    }
  }

  public static ResourceDrawableRequestHandler create(Context context, DrawableLoader loader) {
    return new ResourceDrawableRequestHandler(context, loader);
  }

  public static ResourceDrawableRequestHandler create(final Context context) {
    return create(context, new DrawableLoader() {
      @Override public Drawable load(int resId) {
        return ContextCompat.getDrawable(context, resId);
      }
    });
  }
}
