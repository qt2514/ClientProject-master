package com.twotr.twotr.tutorfiles;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by safetop on 6/2/18.
 */
class ImageController {
    Context context;
    ImageView IVverification;

    ImageController(Context context, ImageView imgMain) {
        this.context = context;
        this.IVverification = imgMain;
    }

    void setImgMain(Uri path) {
        Picasso
                .with(context)
                .load(path)
                .fit()
                .centerCrop()
                .into(IVverification);
    }
}