package pl.kibao.githubclient.ui.utils;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;

public class BindingImageAdapter {
    @BindingAdapter(value = {"bind:imageUrl", "bind:placeholder"},
        requireAll = false)
    public static void loadImage(ImageView view, String url, Drawable placeholder) {
        DrawableTypeRequest<String> creator = Glide.with(view.getContext()).load(url);
        if (placeholder != null) {
            creator.placeholder(placeholder);
        }
        creator.into(view);
    }
}
