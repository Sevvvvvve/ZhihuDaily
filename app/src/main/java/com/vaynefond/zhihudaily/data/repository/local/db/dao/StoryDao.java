package com.vaynefond.zhihudaily.data.repository.local.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.support.annotation.NonNull;

import com.vaynefond.zhihudaily.data.model.Story;

import java.util.List;

import io.reactivex.Maybe;

@Dao
public interface StoryDao {
    @Query("select max(date) from story")
    Maybe<String> loadLatestDate();

    @Query("select * from story where date in (select max(date) from story) order by ga_prefix DESC")
    Maybe<List<Story>> loadLatestStories();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertStory(@NonNull Story story);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertStories(@NonNull List<Story> stories);

    @Query("select * from story where id = :id")
    Maybe<Story> loadStory(int id);

    @Query("select * from story order by ga_prefix DESC")
    Maybe<List<Story>> loadStories();

    @Query("select * from story where date = :date order by ga_prefix DESC")
    Maybe<List<Story>> loadStoriesByDate(String date);

    @Query("select * from story where favorite = 1 order by favorite_date DESC")
    Maybe<List<Story>> loadFavoriteStories();

    @Query("update story set favorite = :favorite where id = :storyId")
    void favoriteStory(int storyId, int favorite);
}
