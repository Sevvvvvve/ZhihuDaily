package com.vaynefond.zhihudaily.data.repository.local.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.support.annotation.NonNull;

import com.vaynefond.zhihudaily.data.model.StoryExtra;

import io.reactivex.Maybe;

@Dao
public interface StoryExtraDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertStoryExtra(@NonNull StoryExtra storyExtra);

    @Query("select * from story_extra where id = :id")
    Maybe<StoryExtra> loadStoryExtra(int id);
}
