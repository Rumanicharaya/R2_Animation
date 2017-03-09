package com.nikki.bbli.r2_animation;

import android.content.Context;

/**
 * Created by Ideal on 3/9/2017.
 */

public class Place {
    public String id;
    public String name;
    public String imageName;
    public boolean isFav;
    public int getImageResourceId(Context context) {
        return context.getResources().getIdentifier(this.imageName, "drawable", context.getPackageName());
    }
}
