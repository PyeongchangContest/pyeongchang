package com.pyeongchang.conch.conch;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.carousel.CarouselView;
import com.pyeongchang.conch.conch.panel.ImagePanel;
import com.pyeongchang.conch.conch.panel.ListLayoutPanel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadFactory;

/**
 * Created by binary on 3/8/16.
 */
public class CarouselFragment extends Fragment implements ListLayoutPanel.OnScrollListener {

    public static CarouselFragment newInstance() {
        Bundle args = new Bundle();
        CarouselFragment fragment = new CarouselFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public CarouselView getmCarouselView() {
        return mCarouselView;
    }
    private CarouselView mCarouselView;
    private List<View> torchList = new ArrayList<>();
    private TextView tv;
    private int nextPosition=0;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_main,null);
        tv=view.findViewById(R.id.main_torchRank);
        return inflater.inflate(R.layout.fragment_carousel, null, false);
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initStubItems();
        mCarouselView = view.findViewById(R.id.carouselView);
        for (View stubItem : torchList) {
            mCarouselView.addView(stubItem);
        }
        mCarouselView.notifyDataSetChanged();

        mCarouselView.setCarouselScrollListener(new CarouselView.CarouselScrollListener() {
            @Override
            public void onPositionChanged(int position) {
                Log.i("(테스트)초기포지션 : ",String.valueOf(position));
                if (position!=nextPosition&&position!=0) {
                    TorchCommunity newTorch = ((MainActivity) getActivity()).getCommunityList().get(mCarouselView.getSelectedItemPosition()-1);
                    ((MainActivity) getActivity()).changeTorchInfo(
                            newTorch.getCommunityName(),
                            newTorch.getCommunityScore(),
                            newTorch.getCommunityRank()
                    );
                    ((MainActivity) getActivity()).visibilityOfTorchInfo(true);
                    nextPosition=position;
                }else if (position==0){
                    ((MainActivity) getActivity()).visibilityOfTorchInfo(false);
                    nextPosition=position;
                }
            }

            @Override
            public void onPositionClicked(int position) {

            }
        });
    }

    // Stub items
    private List<View> initStubItems() {

        ImagePanel plusBtn = new ImagePanel(getActivity());
        plusBtn.initImageLayout();
        plusBtn.setImageResId(R.drawable.plus_torch);
        plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((MainActivity)getActivity()).getCommunityList().size()<=4){
                    ((MainActivity)getActivity()).popupTorch();
                }else {
                    ((MainActivity)getActivity()).showToastText("더이상 성화를 생성할 수 없습니다.");
                }
            }
        });
        torchList.add(plusBtn);

        return torchList;
    }
    @Override
    public void onScroll() {
       mCarouselView.invalidate();
    }

    public void createNewTorch(final String communityName){
        ImagePanel plusTorch = new ImagePanel(getActivity());
        plusTorch.customImageLayout();
//        plusTorch.setImageResId(R.drawable.torch);
        GlideDrawableImageViewTarget imageViewTarget=new GlideDrawableImageViewTarget(plusTorch.getmImageViewHolder());
        Glide.with(this).load(R.raw.torchanim).into(imageViewTarget);
        plusTorch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CommunityActivity.class);
                intent.putExtra("communityName", communityName);
                getActivity().startActivity(intent);
            }
        });
        torchList.add(plusTorch);
        mCarouselView.addView(plusTorch);
        mCarouselView.notifyDataSetChanged();

    }

    public void changeText(String text){
        tv.setText(text);
    }
}
