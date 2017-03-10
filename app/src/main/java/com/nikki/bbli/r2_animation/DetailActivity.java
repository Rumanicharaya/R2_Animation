package com.nikki.bbli.r2_animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.transition.Fade;
import android.transition.Transition;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DetailActivity extends Activity implements View.OnClickListener {
    private ImageButton mAddButton;
    public static final String EXTRA_PARAM_ID = "place_id";
    private Place mPlace;
    private ImageView mImageView;
    private TextView mTitle;
    private boolean isEditTextVisible;
    private Animatable mAnimatable;
    private Palette mPalette;
    private LinearLayout mTitleHolder,mEditTextHolder;
    int defaultColorForRipple;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mPlace = PlaceData.placeList().get(getIntent().getIntExtra(EXTRA_PARAM_ID, 0));
        mImageView = (ImageView) findViewById(R.id.placeImage);
        mTitle = (TextView) findViewById(R.id.place_Name);
        mTitleHolder = (LinearLayout) findViewById(R.id.placeNameHolder);
        mEditTextHolder = (LinearLayout) findViewById(R.id.EditTextHolder);
        mAddButton = (ImageButton) findViewById(R.id.btn_add);
        defaultColorForRipple = getResources().getColor(R.color.colorPrimaryDark);
        mAddButton.setImageResource(R.drawable.icn_morph_reverse);
        mAddButton.setOnClickListener(this);

        mEditTextHolder.setVisibility(View.INVISIBLE);
        isEditTextVisible = false;
        loadPlace();
        windowTransition();
        getPhoto();
    }
    private void getPhoto() {
        Bitmap photo = BitmapFactory.decodeResource(getResources(), mPlace.getImageResourceId(this));
        colorize(photo);
    }
    private void colorize(Bitmap photo) {
        mPalette = Palette.generate(photo);
        applyPalette(mPalette);
    }

    private void applyPalette(Palette mPalette) {
        getWindow().setBackgroundDrawable(new ColorDrawable(mPalette.getDarkMutedColor(defaultColorForRipple)));
        mTitleHolder.setBackgroundColor(mPalette.getMutedColor(defaultColorForRipple));
        mImageView.setBackgroundColor(mPalette.getLightVibrantColor(defaultColorForRipple));
        applyRippleColor(mPalette.getVibrantColor(defaultColorForRipple),
                mPalette.getDarkVibrantColor(defaultColorForRipple));

    }
    private void applyRippleColor(int bgColor, int tintColor) {
        colorRipple(mAddButton, bgColor, tintColor);
    }

    private void colorRipple(ImageButton id, int bgColor, int tintColor) {
        View buttonView = id;
        RippleDrawable ripple = (RippleDrawable) buttonView.getBackground();
        GradientDrawable rippleBackground = (GradientDrawable) ripple.getDrawable(0);
        rippleBackground.setColor(bgColor);
        ripple.setColor(ColorStateList.valueOf(tintColor));
    }

    private void windowTransition() {
        Log.d("Detail","Enter method");
        getWindow().setEnterTransition(makeEnterTransition());
        getWindow().getEnterTransition().addListener(new TransitionAdapter() {
            @Override
            public void onTransitionEnd(Transition transition) {
                Log.d("Detail","transition running");
                mAddButton.animate().alpha(1.0f);
                getWindow().getEnterTransition().removeListener(this);
            }
        });
    }

    public static Transition makeEnterTransition() {
        Log.d("Detail","transition entered");
        Transition fade = new Fade();
        fade.excludeTarget(android.R.id.navigationBarBackground, true);
        fade.excludeTarget(android.R.id.statusBarBackground, true);
        return fade;
    }

    private void loadPlace() {
        mTitle.setText(mPlace.name);
        mImageView.setImageResource(mPlace.getImageResourceId(this));
    }

    private void revealEnter() {
        int cx = mEditTextHolder.getRight();
        int cy = mEditTextHolder.getBottom();
        int finalRadius = Math.max(mEditTextHolder.getWidth(), mEditTextHolder.getHeight());
        Animator anim = ViewAnimationUtils.createCircularReveal(mEditTextHolder, cx, cy, 0, finalRadius);
        mEditTextHolder.setVisibility(View.VISIBLE);
        isEditTextVisible = true;
        anim.start();
    }

    private void revealExit(){
        int cx = mEditTextHolder.getRight();
        int cy = mEditTextHolder.getBottom();
        int finalRadius = Math.max(mEditTextHolder.getWidth(), mEditTextHolder.getHeight());
        Animator anim = ViewAnimationUtils.createCircularReveal(mEditTextHolder, cx, cy, finalRadius,0);
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mEditTextHolder.setVisibility(View.INVISIBLE);
            }
        });
        isEditTextVisible = false;
        anim.start();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_add:
            if(!isEditTextVisible){
                mAddButton.setImageResource(R.drawable.icn_morp);
                mAnimatable = (Animatable) (mAddButton).getDrawable();
                mAnimatable.start();
                revealEnter();

            }else{
                mAddButton.setImageResource(R.drawable.icn_morph_reverse);
                mAnimatable = (Animatable) (mAddButton).getDrawable();
                mAnimatable.start();
                isEditTextVisible = false;
                revealExit();

            }
        }
    }

    @Override
    public void onBackPressed() {
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        alphaAnimation.setDuration(100);
        mAddButton.startAnimation(alphaAnimation);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mAddButton.setVisibility(View.GONE);
                finishAfterTransition();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

}
