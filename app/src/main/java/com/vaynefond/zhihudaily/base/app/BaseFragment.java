package com.vaynefond.zhihudaily.base.app;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.vaynefond.zhihudaily.base.app.BaseActivity.OnThemeChangedListener;
import com.vaynefond.zhihudaily.utils.ResourceUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.DaggerFragment;

public abstract class BaseFragment extends DaggerFragment {
    private Unbinder mUnbinder;
    protected OnFragmentInteractionListener mOnFragmentInteractionListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (requireActivity() instanceof OnFragmentInteractionListener) {
            mOnFragmentInteractionListener = (OnFragmentInteractionListener) requireActivity();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(fragmentLayoutId(), container, false);
        mUnbinder = ButterKnife.bind(this, root);
        setHasOptionsMenu(true);
        setupViews(root);
        return root;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                requireActivity().onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void setStatusBarColor(@ColorInt int color) {
        if (requireActivity() instanceof BaseActivity) {
            ((BaseActivity) requireActivity()).setStatusBarColor(color);
        }
    }

    @Override
    public void onDestroyView() {
        mUnbinder.unbind();
        super.onDestroyView();
    }

    protected void registerThemeChangedListener(@NonNull OnThemeChangedListener listener) {
        if (requireActivity() instanceof BaseActivity) {
            ((BaseActivity) requireActivity()).registerThemeChangedListener(listener);
        }
    }

    protected int getThemeColor(int resId) {
        return ResourceUtils.getColor(requireContext(), resId);
    }

    protected void invalidateStoryRecyclerView(@NonNull RecyclerView recyclerView) {
        try {
            Field recyclerField = Class.forName(RecyclerView.class.getName()).getDeclaredField("mRecycler");
            recyclerField.setAccessible(true);
            Method clearMethod = Class.forName(RecyclerView.Recycler.class.getName())
                    .getDeclaredMethod("clear", (Class<?>[]) new Class[0]);
            clearMethod.setAccessible(true);
            clearMethod.invoke(recyclerField.get(recyclerView), new Object[0]);
            RecyclerView.RecycledViewPool viewPool = recyclerView.getRecycledViewPool();
            viewPool.clear();
        } catch (ClassNotFoundException | NoSuchFieldException | NoSuchMethodException |
                IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @LayoutRes
    protected abstract int fragmentLayoutId();

    protected abstract void setupViews(@NonNull View root);

    public interface OnFragmentInteractionListener {
        void updateToolBarColor(@ColorInt int color);

        void updateToolBarTitle(String title);
    }
}
