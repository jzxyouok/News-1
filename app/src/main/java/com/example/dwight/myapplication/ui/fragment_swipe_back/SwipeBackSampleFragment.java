package com.example.dwight.myapplication.ui.fragment_swipe_back;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.yokeyword.fragmentation_swipeback.SwipeBackFragment;
import com.example.dwight.myapplication.R;
import com.example.dwight.myapplication.SwipeBackSampleActivity;

/**
 * SwipeBackSampleFragment
 * Created by YoKeyword on 16/4/20.
 */
public class SwipeBackSampleFragment extends SwipeBackFragment {
    private OnLockDrawLayoutListener mListener;

    public static SwipeBackSampleFragment newInstance() {
        SwipeBackSampleFragment fragment = new SwipeBackSampleFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_swipe_back, container, false);

        if (mListener != null) {
            mListener.onLockDrawLayout(true);
        }
        return attachToSwipeBack(view);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnLockDrawLayoutListener) {
            mListener = (OnLockDrawLayoutListener) context;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mListener != null) {
            mListener.onLockDrawLayout(false);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnLockDrawLayoutListener {
        void onLockDrawLayout(boolean lock);
    }
}
