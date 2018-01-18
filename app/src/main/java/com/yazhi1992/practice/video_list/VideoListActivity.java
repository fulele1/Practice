package com.yazhi1992.practice.video_list;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yazhi1992.practice.R;
import com.yazhi1992.practice.databinding.ActivityVideoListBinding;
import com.yazhi1992.practice.databinding.ItemAllVideoBinding;

import java.util.ArrayList;
import java.util.List;

public class VideoListActivity extends AppCompatActivity {

    private ActivityVideoListBinding mBinding;

    // TODO: 2018/1/17 列表中 seekbar 复用问题
    // TODO: 2018/1/17 列表中直接显示duration

    private List<VideoDataViewModel> datas = new ArrayList<>();
    private SingleTypeAdapter2<VideoDataViewModel> mAdapter;

    private String url = "http://7xr4ce.media1.z0.glb.clouddn.com/22379-201801171138804.m3u8";
    private String url2 = "http://download.jiayouxueba.com/test1000.m3u8";
//    http://download.jiayouxueba.com/test1000.m3u8

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_video_list);

        for (int i = 0; i < 20; i++) {
            VideoDataViewModel videoDataViewModel = new VideoDataViewModel();
            videoDataViewModel.title.set("第 " + i);
            if (i == 0) {
                videoDataViewModel.url.set(url);
            } else if (i == 2) {
                videoDataViewModel.url.set(url2);
            }
            datas.add(videoDataViewModel);
        }

        mAdapter = new SingleTypeAdapter2<>(this, datas, R.layout.item_all_video);
        mAdapter.setPresenter(new SingleTypeAdapter2.Presenter<VideoDataViewModel>() {
            @Override
            public void onItemClick(View target, VideoDataViewModel itemViewModel) {

            }
        });
        mBinding.ry.setLayoutManager(new LinearLayoutManager(this));
        mBinding.ry.setAdapter(mAdapter);
        mBinding.ry.setRecyclerListener(new RecyclerView.RecyclerListener() {
            @Override
            public void onViewRecycled(RecyclerView.ViewHolder holder) {
                ((ItemAllVideoBinding)((BindingViewHolder)holder).mBinding).itemVideoView.mBinding.videoPlayer.release();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        VideoPlayManager.getInstance().release();
    }
}
