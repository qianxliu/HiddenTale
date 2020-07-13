package qian.xin.library.util;

import android.annotation.SuppressLint;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/*
 * 通用时间类
 *
 * @author Lemon
 * @use TimeUtil.xxxMethod(...);
 */
public class TimeUtil {
    private static final String TAG = "TimeUtil";

    private TimeUtil() {/* 不能实例化*/}


    /*
     * 系统计时开始时间
     */
    public static final int[] SYSTEM_START_DATE = {1970, 0, 1, 0, 0, 0};


    public static final int LEVEL_YEAR = 0;
    public static final int LEVEL_MONTH = 1;
    public static final int LEVEL_DAY = 2;
    public static final int LEVEL_HOUR = 3;
    public static final int LEVEL_MINUTE = 4;
    public static final int LEVEL_SECOND = 5;
    public static final int[] LEVELS = {
            LEVEL_YEAR,
            LEVEL_MONTH,
            LEVEL_DAY,
            LEVEL_HOUR,
            LEVEL_MINUTE,
            LEVEL_SECOND,
    };

    public static final String NAME_YEAR = "年";
    public static final String NAME_MONTH = "月";
    public static final String NAME_DAY = "日";
    public static final String NAME_HOUR = "时";
    public static final String NAME_MINUTE = "分";
    public static final String NAME_SECOND = "秒";
    public static final String[] LEVEL_NAMES = {
            NAME_YEAR,
            NAME_MONTH,
            NAME_DAY,
            NAME_HOUR,
            NAME_MINUTE,
            NAME_SECOND,
    };


    /*
     * @param level
     * @return
     */
    public static boolean isContainLevel(int level) {
        for (int existLevel : LEVELS) {
            if (level == existLevel) {
                return true;
            }
        }
        return false;
    }

    /*
     * @param level
     * @return
     */
    public static String getNameByLevel(int level) {
        return isContainLevel(level) ? LEVEL_NAMES[level - LEVEL_YEAR] : "";
    }


    public static class Day {

        public static final String NAME_TODAY = "今天";
        public static final String NAME_TOMORROW = "明天";
        public static final String NAME_THE_DAY_AFTER_TOMORROW = "后天";


        public static final int TYPE_SUNDAY = 0;
        public static final int TYPE_MONDAY = 1;
        public static final int TYPE_TUESDAY = 2;
        public static final int TYPE_WEDNESDAY = 3;
        public static final int TYPE_THURSDAY = 4;
        public static final int TYPE_FRIDAY = 5;
        public static final int TYPE_SATURDAY = 6;
        public static final int[] DAY_OF_WEEK_TYPES = {
                TYPE_SUNDAY,
                TYPE_MONDAY,
                TYPE_TUESDAY,
                TYPE_WEDNESDAY,
                TYPE_THURSDAY,
                TYPE_FRIDAY,
                TYPE_SATURDAY,
        };

        public static final String NAME_SUNDAY = "日";
        public static final String NAME_MONDAY = "一";
        public static final String NAME_TUESDAY = "二";
        public static final String NAME_WEDNESDAY = "三";
        public static final String NAME_THURSDAY = "四";
        public static final String NAME_FRIDAY = "五";
        public static final String NAME_SATURDAY = "六";
        public static final String[] DAY_OF_WEEK_NAMES = {
                NAME_SUNDAY,
                NAME_MONDAY,
                NAME_TUESDAY,
                NAME_WEDNESDAY,
                NAME_THURSDAY,
                NAME_FRIDAY,
                NAME_SATURDAY,
        };


        /*
         * @param type
         * @return
         */
        public static boolean isContainType(int type) {
            for (int existType : DAY_OF_WEEK_TYPES) {
                if (type == existType) {
                    return true;
                }
            }
            return false;
        }

        public static String getDayNameOfWeek(int type) {
            return isContainType(type) ? DAY_OF_WEEK_NAMES[type - TYPE_SUNDAY] : "";
        }

    }


    public static final int HOUR_OF_DAY = 3;
    public static final int MINUTE = 4;


    public static final int[] MIN_TIME_DETAILS = {0, 0, 0};
    public static final int[] MAX_TIME_DETAILS = {23, 59, 59};


    public static boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || year % 400 == 0;
    }


    /*
     * 获取时间,hh:mm:ss
     *
     * @param date
     * @return
     */
    public static String getTime(Date date) {
        return date == null ? "" : getTime(date.getTime());
    }

    /*
     * 获取时间,hh:mm:ss
     *
     * @param date
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String getTime(long date) {
        return new SimpleDateFormat("hh:mm:ss").format(new Date(date));
    }


    /*
     * 获取完整时间
     *
     * @param date
     * @return
     */
    public static String getWholeTime(Date date) {
        return date == null ? "" : getWholeTime(date.getTime());
    }

    /*
     * 获取完整时间 yyyy年mm月dd日hh时mm分
     *
     * @param date
     * @return
     */
    public static String getWholeTime(long date) {
        int[] details = TimeUtil.getWholeDetail(date);

        return details[0] + "年" + details[1] + "月"
                + details[2] + "日  " + details[3] + "时" + details[4] + "分";
    }


    public static String getSmartDate(Date date) {
        return date == null ? "" : getSmartDate(date.getTime());
    }

    /*
     * 智能时间显示，12:30,昨天，前天...
     *
     * @param date
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String getSmartDate(long date) {

        int[] nowDetails = getWholeDetail(System.currentTimeMillis());
        int[] smartDetail = getWholeDetail(date);

        String smartDate;

        if (nowDetails[0] == smartDetail[0]) {//this year
            if (nowDetails[1] == smartDetail[1]) {//this month
                String time = " " + StringUtil.getString(new SimpleDateFormat("HH:mm").format(date));

                long day = nowDetails[2] - smartDetail[2];//between/(24*3600);
                if (day >= 3) {//fomer day
                    smartDate = smartDetail[2] + "日" + time;
                } else if (day >= 2) {//fomer day
                    smartDate = "前天" + time;
                } else if (day >= 1) {//fomer day
                    smartDate = "昨天" + time;
                } else if (day >= 0) {//today
                    if (0 == (nowDetails[HOUR_OF_DAY] - smartDetail[HOUR_OF_DAY])) {
                        long minute = nowDetails[MINUTE] - smartDetail[MINUTE];
                        if (minute < 1) {
                            smartDate = "刚刚";
                        } else if (minute < 31) {
                            smartDate = minute + "分钟前";
                        } else {
                            smartDate = time;
                        }
                    } else {
                        smartDate = time;
                    }
                } else if (day >= -1) {//tomorrow
                    smartDate = "明天" + time;
                } else if (day >= -2) {//the day after tomorrow
                    smartDate = "后天" + time;
                } else {
                    smartDate = smartDetail[2] + "日" + time;
                }
            } else {//!!!
                smartDate = smartDetail[1] + "月" + smartDetail[2] + "日";
            }
        } else {//!!!
            smartDate = smartDetail[0] + "年" + smartDetail[1] + "月";
        }

        //		System.out.println("返回智能日期" + smartDate);
        return smartDate;
    }

    /*
     * 获取日期 年，月， 日 对应值
     *
     * @param time
     * @return
     */
    public static int[] getDateDetail(long time) {
        final Calendar mCalendar = Calendar.getInstance();
        mCalendar.setTimeInMillis(time);
        return new int[]{
                mCalendar.get(Calendar.YEAR),//0
                mCalendar.get(Calendar.MONTH) + 1,//1
                mCalendar.get(Calendar.DAY_OF_MONTH),//2
        };
    }

    /*
     * 获取日期  时， 分， 秒 对应值
     *
     * @param time
     * @return
     */
    public static int[] getTimeDetail(long time) {
        final Calendar mCalendar = Calendar.getInstance();
        mCalendar.setTimeInMillis(time);
        return new int[]{
                mCalendar.get(Calendar.HOUR_OF_DAY),//3
                mCalendar.get(Calendar.MINUTE),//4
                mCalendar.get(Calendar.SECOND)//5
        };
    }

    /*
     * 获取日期 年，月， 日， 时， 分， 秒 对应值
     *
     * @param time
     * @return
     */
    public static int[] getWholeDetail(long time) {
        final Calendar mCalendar = Calendar.getInstance();
        mCalendar.setTimeInMillis(time);
        return new int[]{
                mCalendar.get(Calendar.YEAR),//0
                mCalendar.get(Calendar.MONTH) + 1,//1
                mCalendar.get(Calendar.DAY_OF_MONTH),//2
                mCalendar.get(Calendar.HOUR_OF_DAY),//3
                mCalendar.get(Calendar.MINUTE),//4
                mCalendar.get(Calendar.SECOND)//5
        };
    }

    /*
     * 根据生日获取年龄
     *
     * @param birthday
     * @return
     */
    public static int getAge(long birthday) {
        return getAge(getDateDetail(birthday));
    }

    /*
     * 根据生日获取年龄
     *
     * @return
     */
    public static int getAge(int[] birthdayDetail) {
        if (birthdayDetail == null || birthdayDetail.length < 3) {
            return 0;
        }

        int[] nowDetails = getDateDetail(System.currentTimeMillis());

        int age = nowDetails[0] - birthdayDetail[0];

        if (nowDetails[1] < birthdayDetail[1]) {
            age = age - 1;
        } else if (nowDetails[1] == birthdayDetail[1]) {
            if (nowDetails[2] < birthdayDetail[2]) {
                age = age - 1;
            }
        }

        return age;
    }


    /*
     * 获取生日,不带年份
     *
     * @param date
     * @return
     */
    public static String getBirthday(Date date) {
        return getBirthday(date, false);
    }

    /*
     * 获取生日
     *
     * @param date
     * @param needYear
     * @return
     */
    public static String getBirthday(Date date, boolean needYear) {
        return date == null ? "" : getBirthday(date.getTime(), needYear);
    }

    /*
     * 获取生日
     *
     * @param date
     * @param needYear
     * @return
     */
    public static String getBirthday(long date, boolean needYear) {
        int[] details = TimeUtil.getWholeDetail(date);

        if (needYear) {
            return details[0] + "年" + details[1] + "月" + details[2] + "日";
        }
        return details[1] + "月" + details[2] + "日";
    }

    /*
     * 获取智能生日
     *
     * @return
     */
    public static String getSmartBirthday(int[] birthdayDetails) {
        if (birthdayDetails == null || birthdayDetails.length < 3) {
            return "";
        }
        if (birthdayDetails[0] > TimeUtil.SYSTEM_START_DATE[0]) {
            birthdayDetails[0] = birthdayDetails[0] - TimeUtil.SYSTEM_START_DATE[0];
        }
        return getSmartBirthday(new Date(birthdayDetails[0], birthdayDetails[1], birthdayDetails[2]));
    }

    /*
     * @param birthday
     * @return
     */
    public static String getSmartBirthday(Date birthday) {
        if (birthday == null) {
            return "";
        }
        if (birthday.getYear() > getDateDetail(System.currentTimeMillis())[0]) {
            birthday.setYear(birthday.getYear() - TimeUtil.SYSTEM_START_DATE[0]);
        }

        return getSmartBirthday(birthday.getTime(), false) + " " + (TimeUtil
                .getDateDetail(System.currentTimeMillis())[0] - birthday.getYear()) + "岁";
    }

    /*
     * 获取智能生日
     *
     * @param birthday
     * @param needYear
     * @return
     */
    public static String getSmartBirthday(long birthday, boolean needYear) {
        int[] birthdayDetails = getDateDetail(birthday);
        int[] nowDetails = getDateDetail(System.currentTimeMillis());

        Calendar birthdayCalendar = Calendar.getInstance();
        birthdayCalendar.set(birthdayDetails[0], birthdayDetails[1], birthdayDetails[2]);

        Calendar nowCalendar = Calendar.getInstance();
        nowCalendar.set(nowDetails[0], nowDetails[1], nowDetails[2]);

        int days = birthdayCalendar.get(Calendar.DAY_OF_YEAR) - nowCalendar.get(Calendar.DAY_OF_YEAR);
        if (days < 8) {
            if (days >= 3) {
                return days + "天后";
            }
            if (days >= 2) {
                return TimeUtil.Day.NAME_THE_DAY_AFTER_TOMORROW;
            }
            if (days >= 1) {
                return TimeUtil.Day.NAME_TOMORROW;
            }
            if (days >= 0) {
                return TimeUtil.Day.NAME_TODAY;
            }
        }

        if (needYear) {
            return birthdayDetails[0] + "年" + birthdayDetails[1] + "月" + birthdayDetails[2] + "日";
        }
        return birthdayDetails[1] + "月" + birthdayDetails[2] + "日";
    }

    public static boolean fomerIsEqualOrBigger(int[] fomer, int[] current) {
        return fomer == current || fomerIsBigger(fomer, current);
    }

    public static boolean fomerIsBigger(int[] fomer, int[] current) {
        if (fomer == null || current == null) {
            Log.e(TAG, "fomerIsBigger  fomer == null || current == null" +
                    " >>  return false;");
            return false;
        }
        int compareLength = Math.min(fomer.length, current.length);

        for (int i = 0; i < compareLength; i++) {
            if (fomer[i] < current[i]) {
                return false;
            }
            if (fomer[i] > current[i]) {
                return true;
            }
        }

        return false;
    }

    /*
     * 判断现在是否属于一段时间,不包含端点
     *
     * @param start
     * @param end
     * @return
     */
    public static boolean isNowInTimeArea(int[] start, int[] end) {
        return isInTimeArea(getTimeDetail(System.currentTimeMillis()), start, end);
    }

    /*
     * 判断一个时间是否属于一段时间,不包含端点
     * (start, end)可跨越0:00,即start < end也行
     *
     * @param time
     * @param start
     * @param end
     * @return
     */
    public static boolean isInTimeArea(int[] time, int[] start, int[] end) {
        if (fomerIsEqualOrBigger(end, start)) {
            return fomerIsEqualOrBigger(time, start) && fomerIsEqualOrBigger(end, time);
        }

        if (fomerIsEqualOrBigger(time, start) && fomerIsEqualOrBigger(MAX_TIME_DETAILS, time)) {
            return true;
        }
        return fomerIsEqualOrBigger(time, MIN_TIME_DETAILS) && fomerIsEqualOrBigger(end, time);
    }

}
