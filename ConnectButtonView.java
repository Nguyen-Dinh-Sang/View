package com.android.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;

import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieDrawable;
import com.android.utils.ResourceHelper;
import com.vpn.vpnlib.R;

import androidx.appcompat.widget.AppCompatImageView;

public class ConnectButtonView extends AppCompatImageView {
    AnimationDrawable frameAnimation;
    public static final int STATUS_CONNECT = 1;
    public static final int STATUS_CONNECTING = 2;
    public static final int STATUS_CONNECTED = 3;
    private CustomDrawable connectRes;
    private CustomDrawable connectingRes;
    private CustomDrawable connectedRes;
    private int currentStatus = STATUS_CONNECT;


    public ConnectButtonView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    public ConnectButtonView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public ConnectButtonView(Context context) {
        super(context);
        init(null);
    }


    private void init(AttributeSet attrs) {
//        if (attrs == null)
//            return;
//        new Thread(() -> {
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.ConnectButtonView);
        int connectResId = ta.getResourceId(R.styleable.ConnectButtonView_connectBackground, -1);
        int connectingResId = ta.getResourceId(R.styleable.ConnectButtonView_connectingBackground, -1);
        int connectedResId = ta.getResourceId(R.styleable.ConnectButtonView_connectedBackground, -1);
        float speed = ta.getFloat(R.styleable.ConnectButtonView_lottie_speed, 1f);
        Log.d("ICT", "ConnectButtonView  " + ta.getString(R.styleable.ConnectButtonView_connectBackground));
        ta.recycle();

//        new Thread(() -> {
            connectRes = new CustomDrawable(getContext(), connectResId, speed);
            connectingRes = new CustomDrawable(getContext(), connectingResId, speed);
            connectedRes = new CustomDrawable(getContext(), connectedResId, speed);

            setStatus(STATUS_CONNECT);
//        }).start();

    }


    public void setStatus(int status) {
        switch (status) {
            case STATUS_CONNECT:
                setBackgroundResource(connectRes);

                setClickable(true);
                break;
            case STATUS_CONNECTING:
                setBackgroundResource(connectingRes);
                setClickable(false);
                break;
            case STATUS_CONNECTED:
                setBackgroundResource(connectedRes);
                setClickable(true);
                break;
            default:
                return;
        }
        currentStatus = status;
    }

    public int getStatus() {
        return currentStatus;
    }


    private void setBackgroundResource(CustomDrawable resource) {
        if (resource == null)
            return;
        post(() -> {
            if (resource.isLottie()) {
                setImageDrawable(resource.getDrawable());
            } else {
                setImageDrawable(resource.getDrawable());

                if (frameAnimation != null && frameAnimation.isRunning()) {
                    frameAnimation.stop();
                    frameAnimation = null;

                }
                try {
                    frameAnimation = (AnimationDrawable) getBackground();
                    if (frameAnimation != null)
                        frameAnimation.start();
                } catch (ClassCastException e) {
//                    return;
                }
            }

        });
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (frameAnimation == null)
            return;
        if (frameAnimation.isRunning())
            return;
        post(() -> frameAnimation.start());

    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (frameAnimation == null)
            return;
        if (!frameAnimation.isRunning())
            return;
        post(() -> frameAnimation.stop());

    }

    private LottieDrawable createLottieDrawable(int id, float speed) {
        final LottieDrawable lottieDrawable = new LottieDrawable();

        LottieComposition.Factory.fromRawFile(getContext(), id,
                composition -> {
                    lottieDrawable.setComposition(composition);
                    lottieDrawable.loop(true);
                    lottieDrawable.setSpeed(speed);
                    lottieDrawable.playAnimation();
                });

        return lottieDrawable;
    }

    private LottieDrawable createLottieDrawable(int id) {
        return createLottieDrawable(id, 1f);
    }

    private class CustomDrawable {
        Drawable drawable;
        boolean isLottie = false;


        public CustomDrawable(Context context, int id) {
            Log.d("ICT", "CustomDrawable  " + id);
            if (ResourceHelper.checkTypeId(context, id, ResourceHelper.TYPE_RAW)) {
                drawable = createLottieDrawable(id);
                isLottie = true;
            } else
                drawable = getResources().getDrawable(id);
        }

        public CustomDrawable(Context context, int id, float speed) {
            Log.d("ICT", "CustomDrawable  " + id);
            if (ResourceHelper.checkTypeId(context, id, ResourceHelper.TYPE_RAW)) {
                drawable = createLottieDrawable(id, speed);
                isLottie = true;
            } else
                drawable = getResources().getDrawable(id);
        }

        public Drawable getDrawable() {
            return drawable;
        }

        public void setDrawable(Drawable drawable) {
            this.drawable = drawable;
        }

        public boolean isLottie() {
            return isLottie;
        }

    }

}
