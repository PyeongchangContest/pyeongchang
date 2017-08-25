package com.pyeongchang.conch.conch.panel;

import android.content.Context;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.pyeongchang.conch.conch.R;

/**
 * Created by CarouselView on 7/7/16.
 */
public class ImagePanel extends BasePanel {

    public ImageView getmImageViewHolder() {
        return mImageViewHolder;
    }

    private ImageView mImageViewHolder;
    //Constructors
    public ImagePanel(Context context) {
        this(context, null);
    }

    public ImagePanel(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ImagePanel(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mImageViewHolder = new ImageView(getContext());
    }

    public void setImageResId(int resId) {
        mImageViewHolder.setImageResource(resId);
    }

    /* ***************************************************************************** */
    /* ******************************** Utility API ******************************** */
    /* ***************************************************************************** */

    public void initImageLayout(){
        LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mImageViewHolder.setLayoutParams(lp);
        mImageViewHolder.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        mPanelContainer.addView(mImageViewHolder);
    }
    public void customImageLayout(){
        final int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 240, getResources().getDisplayMetrics());
        final int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 280, getResources().getDisplayMetrics());
        LayoutParams lp = new LayoutParams(width,height);
        mImageViewHolder.setLayoutParams(lp);
        mImageViewHolder.setScaleType(ImageView.ScaleType.MATRIX);
        Matrix matrix=new Matrix();
        matrix.postScale(2,2);
        matrix.postTranslate(240,280);
        mImageViewHolder.setImageMatrix(matrix);
        mImageViewHolder.invalidate();
        //해상도문제.. 성화 짤들의 해상도를 낮춰야 함.. 빈도수 높여야함
//        mImageViewHolder.setBackground(ResourcesCompat.getDrawable(getResources(),R.drawable.anim,null));
//
//        final AnimationDrawable drawable=(AnimationDrawable)mImageViewHolder.getBackground();
//        drawable.start();

        mPanelContainer.addView(mImageViewHolder);

    }

}
