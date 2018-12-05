package com.vaynefond.zhihudaily.data.repository.local.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.support.annotation.NonNull;

import com.vaynefond.zhihudaily.data.model.Story;
import com.vaynefond.zhihudaily.data.model.Theme;

import java.util.List;

import io.reactivex.Maybe;

@Dao
public interface ThemeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTheme(@NonNull Theme theme);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertThemes(@NonNull List<Theme> themes);

    @Query("select * from theme where id = :id")
    Maybe<Theme> loadTheme(int id);

    @Query("select * from theme order by id")
    Maybe<List<Theme>> loadThemes();
}
