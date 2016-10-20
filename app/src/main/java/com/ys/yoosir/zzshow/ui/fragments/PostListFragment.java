package com.ys.yoosir.zzshow.ui.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.ys.yoosir.zzshow.R;
import com.ys.yoosir.zzshow.modle.toutiao.ArticleData;
import com.ys.yoosir.zzshow.presenter.PostListPresenterImpl;
import com.ys.yoosir.zzshow.ui.adapters.PostListAdapter;
import com.ys.yoosir.zzshow.ui.adapters.listener.RecyclerListener;
import com.ys.yoosir.zzshow.ui.fragments.base.BaseFragment;
import com.ys.yoosir.zzshow.view.PostListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PostListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PostListFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener,RecyclerListener,PostListView{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = PostListFragment.class.getSimpleName();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.post_rv)
    RecyclerView mRecyclerView;

    private PostListAdapter mPostsAdapter;
    private List<ArticleData> mArticleDataList;
    private boolean hasMore = false;

    public PostListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PostListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PostListFragment newInstance(String param1, String param2) {
        PostListFragment fragment = new PostListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_post_list;
    }

    @Override
    public void initViews(View view) {
        initSwipeRefreshLayout();
        initRecyclerView();
        initPresenter();
    }

    private void initSwipeRefreshLayout(){
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_red_light,android.R.color.holo_orange_light,android.R.color.holo_green_light,android.R.color.holo_blue_light);
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    private void initRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();

                int lastVisibleItemPosition = ((LinearLayoutManager) layoutManager)
                        .findLastVisibleItemPosition();
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();

                if (hasMore && visibleItemCount > 0 && newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItemPosition >= totalItemCount - 1) {
                    //TODO  load more & show footer

                    mRecyclerView.scrollToPosition(mPostsAdapter.getItemCount() - 1);
                }
            }
        });
        //TODO setAdapter
        mRecyclerView.setAdapter(mPostsAdapter);
    }

    private void initPresenter(){
        mPresenter = new PostListPresenterImpl();
        mPresenter.attachView(this);
        mPresenter.onCreate();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        mArticleDataList = new ArrayList<>();
        mPostsAdapter = new PostListAdapter(this,mArticleDataList);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void OnItemClickListener(View view, int type, int position) {

    }

    @Override
    public void setPostList(List<ArticleData> articleDataList) {
        Log.d(TAG,"setPostList");
        if(mPostsAdapter != null) {
            if (!mArticleDataList.isEmpty()) {
                mArticleDataList.clear();
            }
            mArticleDataList.addAll(articleDataList);
            mPostsAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showProgress() {
        Log.d(TAG,"showProgress");
    }

    @Override
    public void hideProgress() {
        Log.d(TAG,"hideProgress");
    }

    @Override
    public void showMsg(String message) {
        Log.d(TAG,"showMsg");
    }

}
