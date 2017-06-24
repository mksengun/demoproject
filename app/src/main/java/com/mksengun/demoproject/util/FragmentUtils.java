package com.mksengun.demoproject.util;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.transition.TransitionInflater;
import android.view.View;

import com.google.common.base.Strings;
import com.mksengun.demoproject.R;

import java.util.Iterator;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;



/**
 * This provides methods to help Fragments and Activities to load their UI.
 *
 */
public class FragmentUtils {

    private static FragmentTransaction transaction;

    /**
     * The {@code fragment} is added to the container view with id {@code frameId}. The operation is
     * performed by the {@code fragmentManager}.
     *
     */
    public static void addFragmentToActivity (@NonNull FragmentManager fragmentManager,
                                              @NonNull Fragment fragment, int frameId) {
        checkNotNull(fragmentManager);
        checkNotNull(fragment);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frameId, fragment);
        transaction.commit();
    }

    /**
     * The {@param fragment} will replaced to the container view with id {@param frameId}.
     *
     * The operation will use {@param context} and {@param fragmentManager}
     *
     *  @param viewList is list of transition views
     *  @param backstack can be null. In that case it wont be added to backstack.
     *
     */
    public static void replaceFragmentInActivity (@NonNull Context context,
                                                  @NonNull FragmentManager fragmentManager,
                                                  @NonNull Fragment fragment,
                                                  int frameId,
                                                  List<View> viewList,
                                                  @Nullable String backstack) {
        checkNotNull(fragmentManager);
        checkNotNull(fragment);
        checkNotNull(context);

        //Set Return, Enter, Exit animations
        fragment.setSharedElementReturnTransition(TransitionInflater.from(context)
                .inflateTransition(R.transition.trans_change));
        fragment.setExitTransition(TransitionInflater.from(context)
                .inflateTransition(android.R.transition.move));
        fragment.setSharedElementEnterTransition(TransitionInflater.from(context)
                .inflateTransition(R.transition.trans_change));
        fragment.setEnterTransition(TransitionInflater.from(context)
                .inflateTransition(android.R.transition.move));

        transaction = null;
        transaction = fragmentManager.beginTransaction();
        transaction.replace(frameId, fragment);
        for (Iterator<View> i = viewList.iterator(); i.hasNext();) {
            View item = i.next();
            try{
                transaction.addSharedElement(item,item.getTransitionName());
            }catch (IllegalArgumentException e){
                continue;
            }
        }

        if(!Strings.isNullOrEmpty(backstack)){
            transaction.addToBackStack(Strings.isNullOrEmpty(backstack)?null:backstack);
        }

        //to see ripple effect which is cool
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                transaction.commit();
            }
        }, 100);
    }

}
