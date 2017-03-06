package com.test.test;

import com.miniorm.android.androidBaseDao;
import com.miniorm.android.androidBaseDao2;

/**
 * Created by admin on 2016/9/26.
 */
public class CardDao extends androidBaseDao2<Card> {
    @Override
    public Card getQueryEntity() {
        return new Card();
    }
}
