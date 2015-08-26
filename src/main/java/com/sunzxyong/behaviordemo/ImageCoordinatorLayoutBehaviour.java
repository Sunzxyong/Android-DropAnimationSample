package com.sunzxyong.behaviordemo;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by 晓勇 on 2015/8/25 0025.
 */
public class ImageCoordinatorLayoutBehaviour extends CoordinatorLayout.Behavior<CircleImageView> {
    private int dyTotal = 0;
    private int childHeight;
    private boolean flag = true;

    public ImageCoordinatorLayoutBehaviour(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, CircleImageView child, View directTargetChild, View target, int nestedScrollAxes) {
        childHeight = child.getHeight();
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, final CircleImageView child, View target, int dx, final int dy, int[] consumed) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed);
        dyTotal += dy;
        if (dyTotal > 0 && dy < 0 || dyTotal < 0 && dy > 0) {
            dyTotal = 0;
        }
        if (dy > 0 && flag && dyTotal > 80) {
            ValueAnimator animator = ValueAnimator.ofInt(childHeight, Dp2Px(child.getContext(), 40));
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int values = (int) animation.getAnimatedValue();
                    CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) child.getLayoutParams();
                    params.width = Math.abs(values);
                    params.height = Math.abs(values);
                    child.setLayoutParams(params);
                }
            });
            animator.setDuration(500);
            animator.start();
            flag = false;
            dyTotal = 0;
        }
        if (dy < 0 && !flag && MainActivity.getFirstVisibleItemPosition() == 0 && dyTotal < -80) {
            ValueAnimator animator = ValueAnimator.ofInt(Dp2Px(child.getContext(), 40), childHeight);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int values = (int) animation.getAnimatedValue();
                    CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) child.getLayoutParams();
                    params.width = Math.abs(values);
                    params.height = Math.abs(values);
                    child.setLayoutParams(params);
                }
            });
            animator.setDuration(500);
            animator.start();
            flag = true;
            dyTotal = 0;
        }
    }

    public int Dp2Px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}
