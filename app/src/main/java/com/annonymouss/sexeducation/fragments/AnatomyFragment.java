package com.annonymouss.sexeducation.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.annonymouss.sexeducation.R;
import com.annonymouss.sexeducation.adapters.AnatomyAdapter;
import com.annonymouss.sexeducation.components.rvset.SmartRecyclerView;
import com.annonymouss.sexeducation.components.rvset.SmartScrollListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AnatomyFragment extends BaseFragment {
    @BindView(R.id.rv_anatomy_posts)SmartRecyclerView rvAnatomyPosts;
    @BindView(R.id.vp_empty_anatomy_post)View vpEmptyAnatomy;
    @BindView(R.id.swipe_refresh_layout)SwipeRefreshLayout mSwipeRefresh;

    private AnatomyAdapter mAdapter;
    private SmartScrollListener mSmartScrollListener;

    public AnatomyFragment(){
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_anatomy,container,false);
        ButterKnife.bind(this,fragmentView);

        rvAnatomyPosts.setEmptyView(vpEmptyAnatomy);
        mAdapter = new AnatomyAdapter(getContext());
        rvAnatomyPosts.setAdapter(mAdapter);
        rvAnatomyPosts.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));


        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(getContext(),"Swiping",Toast.LENGTH_LONG).show();
            }
        });

        mSmartScrollListener = new SmartScrollListener(new SmartScrollListener.ControllerSmartScroll() {
            @Override
            public void onListEndReached() {
                Toast.makeText(getContext(),"on List end Reach",Toast.LENGTH_LONG).show();
            }
        });
        rvAnatomyPosts.addOnScrollListener(mSmartScrollListener);

        return fragmentView;
    }
}
