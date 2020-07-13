package qian.xin.library.util;

import android.content.Context;
import android.content.res.ColorStateList;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

import qian.xin.library.R;

import static qian.xin.library.util.StringUtil.*;

/*
 * 通用密码、手机号、验证码输入框输入字符判断及错误提示 类
 *
 * @author Lemon
 * @use EditTextUtil.xxxMethod(...);
 */
public class EditTextUtil {
    private static final String TAG = "EditTextUtil";


    //显示/隐藏输入法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    /*
     * 隐藏输入法
     *
     * @param context
     * @param toGetWindowTokenView
     */
    public static void hideKeyboard(AppCompatActivity context, View toGetWindowTokenView) {
        showKeyboard(context, null, toGetWindowTokenView, false);
    }

    /*
     * 显示输入法
     *
     * @param context
     * @param et
     */
    public static void showKeyboard(AppCompatActivity context, EditText et) {
        showKeyboard(context, et, true);
    }

    /*
     * 显示/隐藏输入法
     *
     * @param context
     * @param et
     * @param show
     */
    public static void showKeyboard(AppCompatActivity context, EditText et, boolean show) {
        showKeyboard(context, et, null, show);
    }

    /*
     * 显示/隐藏输入法
     *
     * @param context
     * @param et
     * @param toGetWindowTokenView(为null时toGetWindowTokenView = et) 包含et的父View，键盘根据toGetWindowTokenView的位置来弹出/隐藏
     * @param show
     */
    public static void showKeyboard(AppCompatActivity context, EditText et, View toGetWindowTokenView, boolean show) {
        if (context == null) {
            Log.e(TAG, "showKeyboard  context == null >> return;");
            return;
        }

        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);//imm必须与context唯一对应
        if (toGetWindowTokenView == null) {
            Log.w(TAG, "showKeyboard   toGetWindowTokenView == null");
            toGetWindowTokenView = et;
        }
        if (toGetWindowTokenView == null) {
            Log.e(TAG, "showKeyboard  toGetWindowTokenView == null && et == null  >> return;");
            return;
        }

        if (!show) {
            assert imm != null;
            Objects.requireNonNull(imm).hideSoftInputFromWindow(toGetWindowTokenView.getWindowToken(), 0);
            if (et != null) {
                et.clearFocus();
            }
        } else {
            if (et != null) {
                et.setFocusable(true);
                et.setFocusableInTouchMode(true);
                et.requestFocus();
                assert imm != null;
                Objects.requireNonNull(imm).toggleSoftInputFromWindow(toGetWindowTokenView.getWindowToken()
                        , InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
            }
        }
    }

    //显示/隐藏输入法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //对输入字符判断<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    public final static int TYPE_NOT_ALLOWED_EMPTY = 0;
    public final static int TYPE_VERIFY = 1;
    public final static int TYPE_PASSWORD = 2;
    public final static int TYPE_PHONE = 3;
    public final static int TYPE_MAIL = 4;

    /*
     * 判断edittext输入文字是否合法
     *
     * @param context
     * @param stringResId
     * @param et
     * @return
     */
    public static boolean isInputedCorrect(AppCompatActivity context, int stringResId, EditText et) {
        return isInputedCorrect(context, et, TYPE_NOT_ALLOWED_EMPTY, stringResId);
    }

    /*
     * 判断edittext输入文字是否合法
     *
     * @param context
     * @param et
     * @param type
     * @param stringResId
     * @return
     */
    public static boolean isInputedCorrect(AppCompatActivity context, EditText et, int type, int stringResId) {
        try {
            if (context != null && stringResId > 0) {
                return isInputedCorrect(context, et, type, context.getResources().getString(stringResId));
            }
        } catch (Exception e) {
            Log.e(TAG, "isInputedCorrect try { if (context != null && stringResId > 0) {catch (Exception e) \n" + e.getMessage());
        }
        return false;
    }

    /*
     * 判断edittext输入文字是否合法
     *
     * @param context
     * @param et
     * @param type
     * @return
     */
    public static boolean isInputedCorrect(AppCompatActivity context, EditText et, int type, String errorRemind) {
        if (context == null || et == null) {
            Log.e(TAG, "isInputedCorrect context == null || et == null >> return false;");
            return false;
        }
        ColorStateList oringinalHintColor = et.getHintTextColors();

        String inputed = getTrimedString(et);
        switch (type) {
            case TYPE_VERIFY:
                if (inputed.length() < 4) {
                    return showInputedError(context, et, isNotEmpty(errorRemind, true) ? errorRemind : "验证码不能小于4位");
                }
                break;
            case TYPE_PASSWORD:
                if (inputed.length() < 6) {
                    return showInputedError(context, et, isNotEmpty(errorRemind, true) ? errorRemind : "密码不能小于6位");
                }
                if (!isNumberOrAlpha(inputed)) {
                    return showInputedError(context, et, isNotEmpty(errorRemind, true) ? errorRemind : "密码只能含有字母或数字");
                } else {
                    break;
                }
            case TYPE_PHONE:
                if (inputed.length() != 11) {
                    return showInputedError(context, et, isNotEmpty(errorRemind, true) ? errorRemind : "请输入11位手机号");
                }
                if (!isPhone(inputed)) {
                    Toast.makeText(context, "您输入的手机号格式不对哦~", Toast.LENGTH_SHORT).show();
                    return false;
                }
                break;
            case TYPE_MAIL:
                if (isEmail(inputed)) break;
                else {
                    return showInputedError(context, "您输入的邮箱格式不对哦~");
                }
            default:
                if (!isNotEmpty(inputed, true)) {
                    return showInputedError(context, et, isNotEmpty(errorRemind, true) ? errorRemind : getTrimedString(et));
                } else if (inputed.equals(getTrimedString(et.getHint()))) {
                    return showInputedError(context, et, isNotEmpty(errorRemind, true) ? errorRemind : getTrimedString(et));
                }
                break;
        }

        et.setHintTextColor(oringinalHintColor);
        return true;
    }


    /*
     * 字符不合法提示(toast)
     *
     * @param context
     * @param resId
     * @return
     */
    public static boolean showInputedError(AppCompatActivity context, int resId) {
        return showInputedError(context, null, resId);
    }

    /*
     * 字符不合法提示(et == null ? toast : hint)
     *
     * @param context
     * @param et
     * @param resId
     * @return
     */
    public static boolean showInputedError(AppCompatActivity context, EditText et, int resId) {
        try {
            return showInputedError(context, et, context.getResources().getString(resId));
        } catch (Exception e) {
            Log.e(TAG, "" + e.getMessage());
        }
        return false;
    }

    /*
     * 字符不合法提示(toast)
     *
     * @param context
     * @param string
     * @return
     */
    public static boolean showInputedError(AppCompatActivity context, String string) {
        return showInputedError(context, null, string);
    }

    /*
     * 字符不合法提示(et == null ? toast : hint)
     *
     * @param context
     * @param et
     * @param string
     * @return
     */
    public static boolean showInputedError(AppCompatActivity context, EditText et, String string) {
        if (!isNotEmpty(string, false)) {
            Log.e(TAG, "showInputedError  context == null || et == null || StringUtil.isNotEmpty(string, false) == false >> return false;");
            return false;
        } else if (context == null) {
            Log.e(TAG, "showInputedError  context == null || et == null || StringUtil.isNotEmpty(string, false) == false >> return false;");
            return false;
        }
        if (et == null) {
            Toast.makeText(context, getTrimedString(string), Toast.LENGTH_SHORT).show();
        } else {
            et.setText("");
            et.setHint(string);
            et.setHintTextColor(context.getResources().getColor(R.color.red));
        }
        return false;
    }

    //对输入字符判断>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

}
