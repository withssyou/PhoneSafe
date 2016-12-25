package edu.zhuoxin.feicui.phonesafe.db;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import edu.zhuoxin.feicui.phonesafe.entity.AppRubish;
import edu.zhuoxin.feicui.phonesafe.entity.ClassListInfo;
import edu.zhuoxin.feicui.phonesafe.entity.TelNumber;

/**
 * Created by Administrator on 2016/12/15.
 * 管理数据库
 * Assets 文件夹下的资源会随着apk的打包，跟apk一起发布
 * 不能直接被操作，如果想要操作assets文件夹下的资源，需要先把资源写入内存中
 * 然后操作内存中的文件。
 */

public class DBManager {
    /**
     * 将assets文件夹下的数据库，写入内存中
     */
    public static void copyDbFileToStorage(Context context, File toFile, String dbName) {
        InputStream is = null;
        OutputStream os = null;
        try {
            is = context.getAssets().open(dbName);
            os = new FileOutputStream(toFile);

            byte[] bytes = new byte[1024 * 2];
            int len = 0;
            while ((len = is.read(bytes)) != -1) {
                os.write(bytes, 0, len);
                os.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * @param context
     * @return 分类列表
     */

    public static List<ClassListInfo> readClassListInfo(Context context) {
        List<ClassListInfo> list = new ArrayList<>();
        //创建数据库所在的文件对象
        File file = new File(context.getFilesDir(), "commonnum.db");
        if (file.exists()) {
            //打开或创建一个数据库对象
            SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(file, null);
            //查询数据
            Cursor cursor = db.query("classlist", new String[]{"name", "idx"}, null, null, null, null, null);
            if (cursor != null) {
                ClassListInfo info = null;
                while (cursor.moveToNext()) {
                    String name = cursor.getString(cursor.getColumnIndex("name"));
                    int idx = cursor.getInt(cursor.getColumnIndex("idx"));
                    info = new ClassListInfo(name, idx);
                    list.add(info);
                }
                cursor.close();
                db.close();
                return list;
            }

        } else {
            copyDbFileToStorage(context, new File(context.getFilesDir(), "commonnum.db"), "commonnum.db");
        }
        // 递归
        return readClassListInfo(context);
    }

    /**
     * @param context 上下文
     * @param idx     查询哪张表
     * @return 电话号码
     */
    public static List<TelNumber> readTelNumber(Context context, int idx) {
        List<TelNumber> list = new ArrayList<>();
        //创建数据库所在的文件对象
        File file = new File(context.getFilesDir(), "commonnum.db");
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(file, null);
        Cursor cursor = db.query("table" + idx, new String[]{"name", "number"}, null, null, null, null, null);
        if (cursor != null) {
            TelNumber info = null;
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String number = cursor.getString(cursor.getColumnIndex("number"));
                info = new TelNumber(name, number);
                list.add(info);
            }
            cursor.close();
            db.close();
            return list;
        }
        return null;
    }

    /**
     *  读取数据库中的垃圾软件
     * @param context
     * @return
     */
    public static List<AppRubish> readAppRubish(Context context) {
        List<AppRubish> list = new ArrayList<>();
        File file = new File(context.getFilesDir(), "clearpath.db");
        if (file.exists()) {
            SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(file, null);
            Cursor cursor = db.query("softdetail", null, null, null, null, null, null);
            if (cursor != null) {
                AppRubish rubish = null;
                while (cursor.moveToNext()) {
                    String chineseName = cursor.getString(cursor.getColumnIndex("softChinesename"));
                    String englishName = cursor.getString(cursor.getColumnIndex("softEnglishname"));
                    String pachageName = cursor.getString(cursor.getColumnIndex("apkname"));
                    String filepath = cursor.getString(cursor.getColumnIndex("filepath"));

                    rubish = new AppRubish(chineseName, englishName, pachageName, filepath);
                    list.add(rubish);
                }

            }
            cursor.close();
            db.close();
            return list;
        }else {
            copyDbFileToStorage(context,new File(context.getFilesDir(),"clearpath.db"),"clearpath.db");
        }
        return readAppRubish(context);
    }
}

