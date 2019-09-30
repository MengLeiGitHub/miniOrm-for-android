package com.miniorm.sqlcipher;

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Context;

import android.os.Build;


import com.miniorm.MiniOrm;
import com.miniorm.android.TableUpgrade;
import com.miniorm.constant.MiniOrmDataConfig;
import com.miniorm.dao.utils.ResultType;
import com.miniorm.dao.utils.StringUtils;
import com.miniorm.debug.DebugLog;

import net.sqlcipher.Cursor;
import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteDatabaseHook;
import net.sqlcipher.database.SQLiteException;
import net.sqlcipher.database.SQLiteOpenHelper;
import net.sqlcipher.database.SQLiteStatement;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public class SQLCipherHelper extends SQLiteOpenHelper {

    private static volatile SQLCipherHelper sqlHelper;
    SQLiteDatabase db;
    static String DbName = "";
    private static    boolean isUseSdCard;
    private static  String path;

    private AtomicInteger mOpenCounter = new AtomicInteger();
    private static java.util.concurrent.Semaphore semaphoreTransaction = new java.util.concurrent.Semaphore(1);


    protected SQLCipherHelper(Context context, int version, String dbname) {
        super(context, dbname, null, version, new SQLiteDatabaseHook() {
            @Override
            public void preKey(SQLiteDatabase database) {

            }

            @Override
            public void postKey(SQLiteDatabase database) {
                database.execSQL("PRAGMA cipher_page_size = 1024");
                database.execSQL("PRAGMA kdf_iter = 64000");
                database.execSQL("PRAGMA cipher_hmac_algorithm = HMAC_SHA1");
                database.execSQL("PRAGMA cipher_kdf_algorithm = PBKDF2_HMAC_SHA1");

            }
        });
        SQLiteDatabase.loadLibs(context);
        db = openDatabase();
    }

    public synchronized SQLiteDatabase openDatabase() {
        if (mOpenCounter.incrementAndGet() == 1) {
            // Opening new database
            db = getWritableDatabase(MiniOrm.password);
            DebugLog.e(db.getPath());
        }
        return db;
    }

    public synchronized  void closeDatabase() {
        if (mOpenCounter.decrementAndGet() == 0) {
            // Closing database
            if(db!=null){
                db.close();
            }

        }
    }

    @Override
    public  void close() {
        sqlHelper=null;
        super.close();
    }

    public static synchronized  SQLCipherHelper getInstance() {


        if (DbName != null && DbName.equals(MiniOrm.dbName)) {
            if (sqlHelper == null){
                initDbHelper();
            }
            return sqlHelper;
        } else {
            sqlHelper = null;
            initDbHelper();
            return sqlHelper;
        }
    }


    private static void initDbHelper() {
        if (sqlHelper == null) {
            DebugLog.e("initDbHelper()  versino=" + MiniOrm.version + "  MiniOrm.dbName=" + MiniOrm.dbName+"-sqlcipher");
            DbName = MiniOrm.dbName;

            MiniOrmDataConfig dataConfig = new MiniOrmDataConfig(MiniOrm.getApplication());
            if ((!StringUtils.isNull(MiniOrm.dbName)) && MiniOrm.version != 0&&StringUtils.isNull(MiniOrm.password)){
                throw new RuntimeException("当前使用的是加密数据库必须传入数据库密码，请调用 MiniOrmUtils.getInstance().init()方法时.设置秘密");
            }
            if (StringUtils.isNull(MiniOrm.dbName) || MiniOrm.version == 0||StringUtils.isNull(MiniOrm.password) ) {
                MiniOrm.dbName = dataConfig.get("DBNAME");
                MiniOrm.version = dataConfig.getInt("DBVersion");
                MiniOrm.password=dataConfig.get("pass");
            } else {
                dataConfig.save("DBNAME", MiniOrm.dbName);
                dataConfig.saveint("DBVersion", MiniOrm.version);
                dataConfig.save("pass",MiniOrm.password);
            }
            /*if (!isUseSdCard){
                isUseSdCard=dataConfig.getBoolean(MiniOrmDataConfig.Config.USE_SD_CARD+MiniOrm.version);
            }*/
            /*try {
                encrypt(MiniOrm.getApplication(),MiniOrm.dbName,MiniOrm.password,dataConfig);
            } catch (IOException e) {
                e.printStackTrace();
            }*/
            sqlHelper = new SQLCipherHelper(MiniOrm.getApplication(), MiniOrm.version, MiniOrm.dbName+"-sqlcipher");
        }
    }

    public static void encrypt(Context ctxt, String dbName,
                               String passphrase,MiniOrmDataConfig config) throws IOException {
        File originalFile = null;
        if(!isUseSdCard){
            originalFile= ctxt.getDatabasePath(dbName);
        }else {
            originalFile=new File(path);
        }
        String key="passphrase"+MiniOrm.dbName+MiniOrm.version;
        if(originalFile.exists()){
            if(config.getBoolean(key)){
                return ;
            }
        }

        if (originalFile.exists()) {
            File newFile =
                    File.createTempFile("sqlcipherutils", "tmp",
                            ctxt.getCacheDir());
            SQLiteDatabase.loadLibs(ctxt);
            SQLiteDatabase db =
                    SQLiteDatabase.openDatabase(originalFile.getAbsolutePath(),
                            "", null,
                            SQLiteDatabase.OPEN_READWRITE);

            db.rawExecSQL(String.format("ATTACH DATABASE '%s' AS encrypted KEY '%s';",
                    newFile.getAbsolutePath(), passphrase));
            db.rawExecSQL("SELECT sqlcipher_export('encrypted')");
            db.rawExecSQL("DETACH DATABASE encrypted;");
            int version = db.getVersion();
            db.close();
            db =SQLiteDatabase.openDatabase(newFile.getAbsolutePath(),
                            passphrase, null,
                            SQLiteDatabase.OPEN_READWRITE);
            db.setVersion(version);
            db.close();

            originalFile.delete();
            newFile.renameTo(originalFile);
            config.saveBoolean(key,true);
        }
    }


    public synchronized SQLiteDatabase getDb() {
        return db;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    public void beginTransaction() {
        try {
            semaphoreTransaction.acquire();
            db.beginTransaction();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void endTransaction() {
        synchronized (db) {
            if (db.inTransaction()) {
                db.endTransaction();
            }
        }
        semaphoreTransaction.release();

    }

    public  int execSQL(String sql) {
        try {
            long timer1 = System.currentTimeMillis();

            db.execSQL(sql);
            timer += (System.currentTimeMillis() - timer1);

            return ResultType.SUCCESS;
        } catch (Exception e) {
            return ResultType.FAIL;
        }
    }

    public static long timer;


    public long execInsert(SQLiteStatement sqLiteStatement){
        try {
            long timer1 = System.currentTimeMillis();
            long result=sqLiteStatement.executeInsert();
            timer += (System.currentTimeMillis() - timer1);
            sqLiteStatement.close();
            return result;
        } catch (Exception e) {
            return ResultType.FAIL;
        }
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public int execSQL2(SQLiteStatement sqLiteStatement) {
        try {
            long timer1 = System.currentTimeMillis();
            sqLiteStatement.executeUpdateDelete();
            timer += (System.currentTimeMillis() - timer1);
            sqLiteStatement.close();
            return ResultType.SUCCESS;
        } catch (Exception e) {
            return ResultType.FAIL;
        }
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        new Thread(new Runnable() {
            @Override
            public void run() {
                new TableUpgrade(new SqlcipherDatabaseExcute()).update();
            }
        }).start();

    }




    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                new TableUpgrade(new SqlcipherDatabaseExcute()).update();
            }
        }).start();
    }

    public void insert(String table, String nullColumnHack, String... str) {

        ContentValues values = new ContentValues();
        values.put("name", str[0]);
        db.insert("stu", nullColumnHack, values);
    }

    public Cursor query(String string, Object object, Object object2,
                        Object object3, Object object4, Object object5, Object object6) {
        // TODO Auto-generated method stub


        return db.query("stu", null, null, null, null, null, null);
    }


    public Cursor rawQuery(String sql, String... selectionArgs) throws SQLiteException {

        return db.rawQuery(sql, selectionArgs);
    }

    public int update(String table, String whereClause, String[] whereArgs, String... values) {

        return db.update(table, null, whereClause, whereArgs);


    }


    public void setTransactionSuccessful() {
        if (db.isDbLockedByCurrentThread() && db.inTransaction()){
            db.setTransactionSuccessful();
        }

    }


    @Deprecated
    public static void useSDCard(boolean isUseSdCard, String path) {
        SQLCipherHelper.isUseSdCard=isUseSdCard;
        SQLCipherHelper.path=path;
    }
}
