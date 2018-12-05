package com.vaynefond.zhihudaily.data.repository.local.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vaynefond.zhihudaily.data.model.Comment;
import com.vaynefond.zhihudaily.data.model.Story;
import com.vaynefond.zhihudaily.data.model.StoryContent;
import com.vaynefond.zhihudaily.data.model.StoryExtra;
import com.vaynefond.zhihudaily.data.model.Theme;
import com.vaynefond.zhihudaily.data.model.TopStory;
import com.vaynefond.zhihudaily.data.repository.local.db.dao.StoryContentDao;
import com.vaynefond.zhihudaily.data.repository.local.db.dao.StoryDao;
import com.vaynefond.zhihudaily.data.repository.local.db.dao.StoryExtraDao;
import com.vaynefond.zhihudaily.data.repository.local.db.dao.ThemeDao;
import com.vaynefond.zhihudaily.data.repository.local.db.dao.TopStoryDao;

import java.lang.reflect.Type;
import java.util.List;

@Database(version = AppDataBase.VERSION,
        entities = {
                Story.class,
                TopStory.class,
                StoryContent.class,
                StoryExtra.class,
                Theme.class
        })
@TypeConverters(AppDataBase.Converters.class)
public abstract class AppDataBase extends RoomDatabase {
    public static final String NAME = "zhihu_daily.db";
    public static final int VERSION = 1;

    public abstract StoryDao storyDao();

    public abstract TopStoryDao topStoryDao();

    public abstract StoryContentDao storyContentDao();

    public abstract StoryExtraDao storyExtraDao();

    public abstract ThemeDao themeDao();

    public static class Converters {
        @TypeConverter
        public String fromList(List<String> list) {
            if (list == null) {
                return null;
            }

            Gson gson = new Gson();
            Type type = new TypeToken<List<String>>(){}.getType();
            return gson.toJson(list, type);
        }

        @TypeConverter
        public List<String> toList(String json) {
            if (json == null) {
                return null;
            }

            Gson gson = new Gson();
            Type type = new TypeToken<List<String>>(){}.getType();
            return gson.fromJson(json, type);
        }

        @TypeConverter
        public String fromSection(StoryContent.Section section) {
            if (section == null) {
                return null;
            }

            return new Gson().toJson(section);
        }

        @TypeConverter
        public StoryContent.Section toSection(String json) {
            if (json == null) {
                return null;
            }

            return new Gson().fromJson(json, StoryContent.Section.class);
        }
    }
}
