package com.vaynefond.zhihudaily.data.repository.local.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.support.annotation.NonNull;

import com.vaynefond.zhihudaily.data.model.StoryContent;

import io.reactivex.Maybe;

@Dao
public interface StoryContentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertStoryContent(@NonNull StoryContent storyContent);

    @Query("select * from story_content where id = :id")
    Maybe<StoryContent> loadStoryContent(int id);
}
