package com.nikki.bbli.r2_animation;

import android.transition.Transition;
import android.util.Log;

/**
 * Created by Ideal on 3/10/2017.
 */

    public class TransitionAdapter implements Transition.TransitionListener  {
    @Override
    public void onTransitionStart(Transition transition) {
        Log.d("TransitionAdapter","transition start");
    }

    @Override
    public void onTransitionEnd(Transition transition) {
        Log.d("TransitionAdapter","transition end");
    }

    @Override
    public void onTransitionCancel(Transition transition) {
        Log.d("TransitionAdapter","transition cancel");
    }

    @Override
    public void onTransitionPause(Transition transition) {
        Log.d("TransitionAdapter","transition pause");
    }

    @Override
    public void onTransitionResume(Transition transition) {
        Log.d("TransitionAdapter","transition resume");
    }
}
