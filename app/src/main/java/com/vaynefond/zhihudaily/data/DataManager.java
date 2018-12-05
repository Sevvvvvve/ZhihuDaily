package com.vaynefond.zhihudaily.data;

import com.vaynefond.zhihudaily.data.preference.PreferenceProvider;
import com.vaynefond.zhihudaily.data.repository.DataRepository;

import io.reactivex.Maybe;

public interface DataManager extends DataRepository, PreferenceProvider {
    Maybe<Boolean> clearDatabase();
}
