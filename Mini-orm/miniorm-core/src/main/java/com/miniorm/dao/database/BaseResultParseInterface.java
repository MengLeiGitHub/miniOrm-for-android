package com.miniorm.dao.database;


import com.miniorm.dao.reflex.ReflexEntity;

import java.util.List;

public interface BaseResultParseInterface<N> {

    public <T> T parse(N n, Class<T> t, ReflexEntity reflexEntity) throws IllegalAccessException, InstantiationException;

    public <T> List<T> parseList(N n, Class<T> t, ReflexEntity reflexEntity) throws IllegalAccessException, InstantiationException;

    public <T> int ParseLastInsertRowId(N n, Class<T> t, ReflexEntity reflexEntity);

    interface MiniOrmCursor {
        public byte[] getBlob(int columnIndex);

        public String getString(int columnIndex);

        public short getShort(int columnIndex);

        public int getInt(int columnIndex);

        public long getLong(int columnIndex);

        public float getFloat(int columnIndex);

        public double getDouble(int columnIndex);

        public int getColumnIndex(String columnName);

        public int getCount();

        public boolean moveToFirst();

        public boolean moveToNext();

        public void close();
    }


}
