package com.test.test;

import com.miniorm.android.androidBaseDao;

/**
 * Created by admin on 2016/9/26.
 */
public class CardDao extends androidBaseDao<Card> {
    @Override
    public Card getQueryEntity() {
        return new Card();
    }
}
