package com.jackathan.josefloading;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ProgressBar;

import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;

public class JosefLoading extends ProgressBar {
    public static final float STIFFNESS_HIGH = SpringForce.STIFFNESS_HIGH;
    public static final float STIFFNESS_MEDIUM = SpringForce.STIFFNESS_MEDIUM;
    public static final float STIFFNESS_LOW = SpringForce.STIFFNESS_LOW;
    public static final float STIFFNESS_VERY_LOW = SpringForce.STIFFNESS_VERY_LOW;
    public static final float DAMPING_RATIO_HIGH_BOUNCY = SpringForce.DAMPING_RATIO_HIGH_BOUNCY;
    public static final float DAMPING_RATIO_MEDIUM_BOUNCY = SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY;
    public static final float DAMPING_RATIO_LOW_BOUNCY = SpringForce.DAMPING_RATIO_LOW_BOUNCY;
    public static final float DAMPING_RATIO_NO_BOUNCY = SpringForce.DAMPING_RATIO_NO_BOUNCY;
    public static final float DEFAULT_FINAL_POSITION = 0;
    public static final long DEFAULT_ANIMATION_DURATION = 0;
    private SpringAnimation springAnimationX;
    private SpringAnimation springAnimationY;
    private float dX;
    private float dY;
    private boolean moveOnAxisX;
    private boolean moveOnAxisY;
    private long animationDuration;
    private float stiffness;
    private float dampingRatio;
    private float finalPosition;
    private View.OnTouchListener onTouchListener;

    private void initFields(AttributeSet attr) {
        moveOnAxisX = true;
        moveOnAxisY = true;
        animationDuration = DEFAULT_ANIMATION_DURATION;
        stiffness = STIFFNESS_MEDIUM;
        dampingRatio = DAMPING_RATIO_MEDIUM_BOUNCY;
        finalPosition = DEFAULT_FINAL_POSITION;
        if (attr != null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attr, R.styleable.JosefLoading);
            moveOnAxisX = typedArray.getBoolean(R.styleable.JosefLoading_moveOnAxisX, true);
            moveOnAxisY = typedArray.getBoolean(R.styleable.JosefLoading_moveOnAxisY, true);
            setAnimationDuration((long) typedArray.getInteger(R.styleable.JosefLoading_animation_duration, (int) DEFAULT_ANIMATION_DURATION));
            if (typedArray.hasValue(R.styleable.JosefLoading_stiffness)) {
                setStiffness(getStiffnessFromAttr(typedArray.getInteger(R.styleable.JosefLoading_stiffness, 2)));
            }
            if (typedArray.hasValue(R.styleable.JosefLoading_damping_ratio)) {
                setDampingRatio(getDampingRatioFromAttr(typedArray.getInteger(R.styleable.JosefLoading_damping_ratio, 2)));
            }
            setFinalPosition(typedArray.getFloat(R.styleable.JosefLoading_final_position, DEFAULT_FINAL_POSITION));
            typedArray.recycle();
        }
    }

    private float getStiffnessFromAttr(int stiffnessEnum) {
        switch (stiffnessEnum) {
            case 1:
                return STIFFNESS_HIGH;
            case 2:
                return STIFFNESS_MEDIUM;
            case 3:
                return STIFFNESS_LOW;
            case 4:
                return STIFFNESS_VERY_LOW;
            default:
                return STIFFNESS_MEDIUM;
        }
    }

    private float getDampingRatioFromAttr(int dampingRatioEnum) {
        switch (dampingRatioEnum) {
            case 1:
                return DAMPING_RATIO_HIGH_BOUNCY;
            case 2:
                return DAMPING_RATIO_MEDIUM_BOUNCY;
            case 3:
                return DAMPING_RATIO_LOW_BOUNCY;
            case 4:
                return DAMPING_RATIO_NO_BOUNCY;
            default:
                return DAMPING_RATIO_MEDIUM_BOUNCY;
        }
    }

    private void initSpringAnimation() {
        springAnimationX = new SpringAnimation(this, DynamicAnimation.TRANSLATION_X);
        SpringForce springForceX = new SpringForce(DEFAULT_FINAL_POSITION);
        springForceX.setStiffness(STIFFNESS_MEDIUM);
        springForceX.setDampingRatio(DAMPING_RATIO_MEDIUM_BOUNCY);
        springAnimationX.setSpring(springForceX);

        springAnimationY = new SpringAnimation(this, DynamicAnimation.TRANSLATION_Y);
        SpringForce springForceY = new SpringForce(DEFAULT_FINAL_POSITION);
        springForceY.setStiffness(STIFFNESS_MEDIUM);
        springForceY.setDampingRatio(DAMPING_RATIO_MEDIUM_BOUNCY);
        springAnimationY.setSpring(springForceY);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initTouchListener() {
        onTouchListener = new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (moveOnAxisX) {
                            dX = v.getX() - event.getRawX();
                            springAnimationX.cancel();
                        }
                        if (moveOnAxisY) {
                            dY = v.getY() - event.getRawY();
                            springAnimationY.cancel();

                        }
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (moveOnAxisY) {
                            v.animate().y(event.getRawY() + dY);
                        }
                        if (moveOnAxisX) {
                            v.animate().x(event.getRawX() + dX);
                        }
                        v.animate().setDuration(animationDuration).start();
                        break;
                    case MotionEvent.ACTION_UP:
                        if (moveOnAxisX) {
                            springAnimationX.start();
                        }
                        if (moveOnAxisY) {
                            springAnimationY.start();
                        }
                        break;
                }
                return true;
            }
        };
        this.setOnTouchListener(onTouchListener);
    }


    private void init(AttributeSet attr) {
        initSpringAnimation();
        initFields(attr);
        initTouchListener();
    }


    public JosefLoading(Context context) {
        super(context);
        init(null);
    }

    public JosefLoading(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public JosefLoading(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public JosefLoading(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    /**
     * Returns boolean whether the view can move on X axis
     *
     * @return True if the view can move on X axis
     */
    public boolean isMoveOnAxisX() {
        return moveOnAxisX;
    }

    /**
     * Sets whether the view can move on X axis
     *
     * @param moveOnAxisX Boolean to determine movement on X axis
     */
    public void setMoveOnAxisX(boolean moveOnAxisX) {
        this.moveOnAxisX = moveOnAxisX;
    }

    /**
     * Returns boolean whether the view can move on Y axis
     *
     * @return True if the view can move on Y axis
     */
    public boolean isMoveOnAxisY() {
        return moveOnAxisY;
    }

    /**
     * Sets whether the view can move on X axis
     *
     * @param moveOnAxisY Boolean to determine movement on X axis
     */
    public void setMoveOnAxisY(boolean moveOnAxisY) {
        this.moveOnAxisY = moveOnAxisY;
    }

    /**
     * Gets the duration of the animation in milliseconds
     *
     * @return Milliseconds of the animation
     */
    public long getAnimationDuration() {
        return animationDuration;
    }

    /**
     * Sets how long the animation should last
     *
     * @param animationDuration Milliseconds of the animation
     */
    public void setAnimationDuration(long animationDuration) {
        if (animationDuration >= 0) {
            this.animationDuration = animationDuration;
        }
    }

    /**
     * Gets the stiffness of the spring animation
     *
     * @return Float that represents how stiff the spring animation should be
     */
    public float getStiffness() {
        return stiffness;
    }

    /**
     * Sets the stiffness of the spring animation
     *
     * @param stiffness of the spring animation
     */
    public void setStiffness(float stiffness) {
        if (stiffness > 0) {
            springAnimationX.getSpring().setStiffness(stiffness);
            springAnimationY.getSpring().setStiffness(stiffness);
            this.stiffness = stiffness;
        }
    }

    /**
     * Gets how bouncy the spring is
     *
     * @return the spring bounciness
     */
    public float getDampingRatio() {
        return dampingRatio;
    }

    /**
     * Set the spring animation bounciness
     *
     * @param dampingRatio How bouncy the spring should be
     */
    public void setDampingRatio(float dampingRatio) {
        if (dampingRatio > 0) {
            springAnimationX.getSpring().setDampingRatio(dampingRatio);
            springAnimationY.getSpring().setDampingRatio(dampingRatio);
            this.dampingRatio = dampingRatio;
        } else if (dampingRatio > 1) {
            springAnimationX.getSpring().setDampingRatio(DAMPING_RATIO_NO_BOUNCY);
            springAnimationY.getSpring().setDampingRatio(DAMPING_RATIO_NO_BOUNCY);
            this.dampingRatio = DAMPING_RATIO_NO_BOUNCY;
        } else if (dampingRatio == 0) {
            springAnimationX.getSpring().setDampingRatio(DAMPING_RATIO_HIGH_BOUNCY);
            springAnimationY.getSpring().setDampingRatio(DAMPING_RATIO_HIGH_BOUNCY);
            this.dampingRatio = DAMPING_RATIO_HIGH_BOUNCY;
        }
    }

    /**
     * Returns the final position of the view
     *
     * @return the position of the view
     */
    public float getFinalPosition() {
        return finalPosition;
    }

    /**
     * Set final position of the view
     *
     * @param finalPosition where the view should rest once the animation completes
     */
    public void setFinalPosition(float finalPosition) {
        if (finalPosition >= 0) {
            springAnimationX.getSpring().setFinalPosition(finalPosition);
            springAnimationY.getSpring().setFinalPosition(finalPosition);
            this.finalPosition = finalPosition;
        }
    }
}
