package com.vaynefond.zhihudaily.data.repository.local.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.vaynefond.zhihudaily.data.model.TopStory;

import java.util.List;

import io.reactivex.Maybe;

@Dao
public interface TopStoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTopStory(TopStory topStory);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTopStories(List<TopStory> topStories);

    @Query("select * from top_story where id = :id")
    Maybe<TopStory> loadTopStory(int id);

    @Query("select * from top_story order by ga_prefix DESC limit 0, 5")
    Maybe<List<TopStory>> loadTopStories();
}
