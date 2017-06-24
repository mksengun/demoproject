package com.mksengun.demoproject.model.local;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.mksengun.demoproject.model.Post;
import com.mksengun.demoproject.model.datasource.PostDataSource;
import com.mksengun.demoproject.model.repository.PostRepository;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by mustafa on 23/06/2017.
 */

public class PostLocalDataSource implements PostDataSource {

    private static final String TAG = PostLocalDataSource.class.getSimpleName();


    private static PostLocalDataSource INSTANCE;
    private static Realm realm;

    public PostLocalDataSource(@NonNull Context context) {
        checkNotNull(context);
        Realm.init(context);
        realm = Realm.getDefaultInstance();
    }


    public static PostLocalDataSource getInstance(@NonNull Context context) {
        if (INSTANCE == null) {
            INSTANCE = new PostLocalDataSource(context);
        }
        return INSTANCE;
    }


    /**
     * This method gets a post list from local database
     *
     * @param callback
     */
    @Override
    public void getPostList(@NonNull GetPostListCallback callback) {

        RealmResults<Post> result = realm.where(Post.class).findAll();
        List<Post> copied = realm.copyFromRealm(result);
        if(copied.size() > 0){
            // array is passing to repository
            callback.onPostListLoaded(copied);
        }else{
            // no records found
            callback.onDataNotAvailable();
        }
    }

    /**
     * This method is used for get a single {@link Post} data from database
     *
     * @param postId
     * @param callback used from {@link PostRepository#mPostLocalDataSource}
     */
    @Override
    public void getPost(@NonNull int postId, @NonNull GetPostCallback callback) {
        callback.onPostLoaded(realm.where(Post.class).equalTo("id", postId).findFirst());
    }


    /**
     * This method called from {@link PostRepository} and simple used to set remote data from
     * database with {@param postList}}
     */
    public void setPostList(final List<Post> postList) {

        // clear the Post data at database before start to adding new ones
        clearPosts();

        // Asynchronously update Post data on a background thread
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(postList);
            }
        });
    }

    /**
     * This method called from {@link PostRepository} and simple used to set remote data from
     * database with {@param post}}
     */
    public void setPost(final Post post) {

        // Asynchronously update Post data on a background thread
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(post);
            }
        });
    }

    /**
     *  This method clears posts in database. When we get new data from remote we will use this.
     */
    public void clearPosts() {
        realm.beginTransaction();
        realm.delete(Post.class);
        realm.commitTransaction();
    }

}
