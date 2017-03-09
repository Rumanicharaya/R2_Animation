package com.nikki.bbli.r2_animation;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailActivity extends Activity {

    public static final String EXTRA_PARAM_ID = "place_id";
    private Place mPlace;
    private ImageView mImageView;
    private TextView mTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mPlace = PlaceData.placeList().get(getIntent().getIntExtra(EXTRA_PARAM_ID, 0));
        mImageView = (ImageView) findViewById(R.id.placeImage);
        mTitle = (TextView) findViewById(R.id.place_Name);
        loadPlace();
    }

    private void loadPlace() {
        mTitle.setText(mPlace.name);
        Log.d("rumani","rohit "+mPlace.name);
        mImageView.setImageResource(mPlace.getImageResourceId(this));
    }
}
