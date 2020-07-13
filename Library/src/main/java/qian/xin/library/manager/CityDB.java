package qian.xin.library.manager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import qian.xin.library.util.StringUtil;

/*地区管理类
 * @use CityDB.getInstance(...).xxMethod(...)
 */
public class CityDB {
    public static final String CITY_DB_NAME = "city.db";
    private static final String CITY_TABLE_NAME = "city";
    private SQLiteDatabase db;

    public CityDB(Context context, String path) {
        db = context.openOrCreateDatabase(path, Context.MODE_PRIVATE, null);
    }

    private static CityDB cityDB;

    public static synchronized CityDB getInstance(AppCompatActivity context, String packageName) {
        if (cityDB == null) {
            cityDB = openCityDB(context, packageName);
        }
        return cityDB;
    }

    private static CityDB openCityDB(AppCompatActivity context, String packageName) {
        String path = "/data"
                + Environment.getDataDirectory().getAbsolutePath()
                + File.separator + packageName + File.separator
                + CityDB.CITY_DB_NAME;
        File db = new File(path);
        if (!db.exists()) {
            try {
                InputStream is = context.getAssets().open("city.db");
                FileOutputStream fos = new FileOutputStream(db);
                int len;
                byte[] buffer = new byte[1024];
                while ((len = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, len);
                    fos.flush();
                }
                fos.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(0);
            }
        }
        return new CityDB(context, path);
    }


    public List<String> getAllProvince() {

        List<String> list = new ArrayList<>();

        @SuppressLint("Recycle") Cursor c = db.rawQuery("SELECT distinct province from " + CITY_TABLE_NAME, null);
        while (c.moveToNext()) {
            String province = c.getString(c.getColumnIndex("province"));
            list.add(province);
        }
        return list;
    }


    /*
     * 拿到省的所有 地级市
     *
     * @return
     */
    public List<String> getProvinceAllCity(String province) {
        province = StringUtil.getTrimedString(province);
        if (province.length() <= 0) {
            return null;
        }

        List<String> list = new ArrayList<>();

        @SuppressLint("Recycle") Cursor c = db.rawQuery("SELECT distinct city from " + CITY_TABLE_NAME + " where province = ? ", new String[]{province});
        while (c.moveToNext()) {
            String city = c.getString(c.getColumnIndex("city"));
            list.add(city);
        }
        return list;
    }
}
