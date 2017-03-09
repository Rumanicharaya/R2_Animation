package com.nikki.bbli.r2_animation;

import android.app.Activity;
import android.graphics.drawable.Animatable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailActivity extends Activity implements View.OnClickListener {
    private ImageButton mAddButton;
    public static final String EXTRA_PARAM_ID = "place_id";
    private Place mPlace;
    private ImageView mImageView;
    private TextView mTitle;
    private boolean isEditTextVisible;
    private Animatable mAnimatable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mPlace = PlaceData.placeList().get(getIntent().getIntExtra(EXTRA_PARAM_ID, 0));
        mImageView = (ImageView) findViewById(R.id.placeImage);
        mTitle = (TextView) findViewById(R.id.place_Name);
        mAddButton = (ImageButton) findViewById(R.id.btn_add);



        mAddButton.setImageResource(R.drawable.icn_morph_reverse);
        mAddButton.setOnClickListener(this);
        loadPlace();
    }

    private void loadPlace() {
        mTitle.setText(mPlace.name);
        mImageView.setImageResource(mPlace.getImageResourceId(this));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_add:
            if(!isEditTextVisible){
                mAddButton.setImageResource(R.drawable.icn_morp);
                mAnimatable = (Animatable) (mAddButton).getDrawable();
                mAnimatable.start();
                isEditTextVisible = true;
            }else{
                mAddButton.setImageResource(R.drawable.icn_morph_reverse);
                mAnimatable = (Animatable) (mAddButton).getDrawable();
                mAnimatable.start();
                isEditTextVisible = false;
            }
        }
    }
}
