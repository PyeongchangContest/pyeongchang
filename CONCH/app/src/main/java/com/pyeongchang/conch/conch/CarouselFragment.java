package com.pyeongchang.conch.conch;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.carousel.CarouselView;
import com.pyeongchang.conch.conch.panel.ImagePanel;
import com.pyeongchang.conch.conch.panel.LayoutPanel;
import com.pyeongchang.conch.conch.panel.ListLayoutPanel;

import java.util.ArrayList;
import java.util.List;

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
                if (position!=nextPosition&&position!=0) {
                    TorchCommunity newTorch = ((MainActivity) getActivity()).getCommunityList().get(mCarouselView.getSelectedItemPosition()-1);
                    ((MainActivity) getActivity()).changeText(
                            newTorch.getCommunityName(),
                            newTorch.getCommunityScore(),
                            newTorch.getCommunityRank()
                    );
                    nextPosition=position;
                }
            }

            @Override
            public void onPositionClicked(int position) {

            }
        });
//        view.findViewById(R.id.prev).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int position = mCarouselView.getSelectedItemPosition() == 0 ? mCarouselView.getCount() - 1 : mCarouselView.getSelectedItemPosition() - 1;
//                mCarouselView.scrollToChild(position);
//                mCarouselView.invalidate();
//            }
//        });
//
//        view.findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int position = mCarouselView.getSelectedItemPosition() == mCarouselView.getCount() - 1 ? 0 : mCarouselView.getSelectedItemPosition() + 1;
//                mCarouselView.scrollToChild(position);
//                mCarouselView.invalidate();
//            }
//        });
    }


    // Stub items
    private List<View> initStubItems() {
//        ImagePanel torchBtn = new ImagePanel(getActivity());
//        torchBtn.setImageResId(R.drawable.torch);
//        torchList.add(torchBtn);

        ImagePanel plusBtn = new ImagePanel(getActivity());
        plusBtn.setImageResId(R.drawable.plus_torch);
        plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).popupTorch();

            }
        });
        torchList.add(plusBtn);

//        LayoutPanel layoutPanel = new LayoutPanel(getActivity());
//        torchList.add(layoutPanel);
//
//        ListLayoutPanel listLayoutPanel = new ListLayoutPanel(getActivity());
//        listLayoutPanel.setOnScrollListener(this);
//        result.add(listLayoutPanel);



        return torchList;
    }
    @Override
    public void onScroll() {
       mCarouselView.invalidate();
    }

    public void createNewTorch(){
        ImagePanel plusTorch = new ImagePanel(getActivity());
        plusTorch.setImageResId(R.drawable.torch);
        torchList.add(plusTorch);
        mCarouselView.addView(plusTorch);
        mCarouselView.notifyDataSetChanged();
    }

    public void changeText(String text){
        tv.setText(text);
    }
}