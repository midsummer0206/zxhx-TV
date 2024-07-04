package com.fongmi.android.tv.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.viewbinding.ViewBinding;

import com.fongmi.android.tv.bean.Episode;
import com.fongmi.android.tv.databinding.FragmentEpisodeBinding;
import com.fongmi.android.tv.model.SiteViewModel;
import com.fongmi.android.tv.ui.adapter.EpisodeAdapter;
import com.fongmi.android.tv.ui.base.BaseFragment;
import com.fongmi.android.tv.ui.base.ViewType;

import java.util.ArrayList;
import java.util.List;

public class EpisodeFragment extends BaseFragment implements EpisodeAdapter.OnClickListener {

    private FragmentEpisodeBinding mBinding;
    private SiteViewModel mViewModel;

    private int getDownload() {
        return getArguments().getInt("download");
    }

    private int getSpanCount() {
        return getArguments().getInt("spanCount");
    }

    private ArrayList<Episode> getItems() {
        return getArguments().getParcelableArrayList("items");
    }

    public static EpisodeFragment newInstance(int spanCount, List<Episode> items) {
        return newInstance(spanCount, items, 0);
    }

    public static EpisodeFragment newInstance(int spanCount, List<Episode> items, int download) {
        Bundle args = new Bundle();
        args.putInt("download", download);
        args.putInt("spanCount", spanCount);
        args.putParcelableArrayList("items", new ArrayList<>(items));
        EpisodeFragment fragment = new EpisodeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected ViewBinding getBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return mBinding = FragmentEpisodeBinding.inflate(inflater, container, false);
    }

    @Override
    protected void initView() {
        setRecyclerView();
        setViewModel();
    }

    private void setRecyclerView() {
        EpisodeAdapter adapter;
        mBinding.recycler.setHasFixedSize(true);
        mBinding.recycler.setItemAnimator(null);
        mBinding.recycler.setLayoutManager(new GridLayoutManager(getContext(), getSpanCount()));
        mBinding.recycler.setAdapter(adapter = new EpisodeAdapter(this, ViewType.GRID, getItems()));
        mBinding.recycler.scrollToPosition(adapter.getPosition());
    }

    private void setViewModel() {
        mViewModel = new ViewModelProvider(requireActivity()).get(SiteViewModel.class);
    }

    @Override
    public void onItemClick(Episode item) {
        switch (getDownload()){
            case 0:
                mViewModel.setEpisode(item);
                break;
            case 1:
                mViewModel.setOuterDownload(item);
                break;
            case 2:
                mViewModel.setInnerDownload(item);
                break;
            default:
                break;
        }
    }
}
