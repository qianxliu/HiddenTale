/*Copyright ©2015 TommyLemon(https://github.com/TommyLemon)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.*/

package zuo.biao.library.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import zuo.biao.library.R;
import zuo.biao.library.base.BaseActivity;
import zuo.biao.library.interfaces.OnBottomDragListener;
import zuo.biao.library.util.EditTextUtil;

import static zuo.biao.library.util.StringUtil.getCurrentString;
import static zuo.biao.library.util.StringUtil.getLength;
import static zuo.biao.library.util.StringUtil.getTrimedString;
import static zuo.biao.library.util.StringUtil.isNotEmpty;

/*
 * 通用编辑个人资料文本界面
 *
 * @author Lemon
 * @use <br> toActivity或startActivityForResult (EditTextInfoActivity.createIntent(...), requestCode);
 * <br> 然后在onActivityResult方法内
 * <br> data.getStringExtra(EditTextInfoActivity.RESULT_EDIT_TEXT_INFO); 可得到输入框内容
 */
public class EditTextInfoActivity extends BaseActivity implements OnBottomDragListener {
    public static final String TAG = "EditTextInfoActivity";

    //启动方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    public static final String RESULT_TYPE = "RESULT_TYPE";
    public static final String RESULT_KEY = "RESULT_KEY";
    public static final String RESULT_VALUE = "RESULT_VALUE";
    public static final String RESULT_URL = "RESULT_URL";
    public static final String RESULT_ID = "RESULT_ID";
    public static final String RESULT_IMAGE_URL = "RESULT_IMAGE_URL";

    /*
     * @param context
     * @param key
     * @param value
     * @return
     */
    public static Intent createIntent(AppCompatActivity context, String key, String value) {
        return createIntent(context, 0, key, value);
    }

    /*
     * @param context
     * @param type
     * @param key
     * @param value
     * @return
     */
    public static Intent createIntent(AppCompatActivity context, int type, String key, String value) {
        return new Intent(context, EditTextInfoActivity.class).
                putExtra(INTENT_TYPE, type).
                putExtra(INTENT_KEY, key).
                putExtra(INTENT_VALUE, value);
    }

    //启动方法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_text_info_activity, this);//传this是为了全局滑动返回

        //必须调用<<<<<<<<<<<
        initView();
        initData();
        initEvent();
        //必须调用>>>>>>>>>>

    }


    //UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    private EditText etEditTextInfo;
    private View ivEditTextInfoClear;
    private TextView tvEditTextInfoRemind;
    private ListView lvEditTextInfo;

    //	private XListView lvEditTextInfo;
    @Override
    public void initView() {//必须调用

        etEditTextInfo = findView(R.id.etEditTextInfo);
        ivEditTextInfoClear = findView(R.id.ivEditTextInfoClear);
        tvEditTextInfoRemind = findView(R.id.tvEditTextInfoRemind);

        lvEditTextInfo = findView(R.id.lvEditTextInfo);
    }

    private ArrayAdapter<String> adapter;

    /*
     * 显示列表内容
     *
     * @param list
     * @author author
     */
    private void setList(List<String> list) {
        if (!hasList) {
            Log.i(TAG, "setList list == null || list.size() <= 0 >> adapter = null;lvEditTextInfo.setAdapter(null); return;");
            adapter = null;
            lvEditTextInfo.setAdapter(null);
            return;
        } else {
            if (list == null || list.size() <= 0) {
                Log.i(TAG, "setList list == null || list.size() <= 0 >> adapter = null;lvEditTextInfo.setAdapter(null); return;");
                adapter = null;
                lvEditTextInfo.setAdapter(null);
                return;
            }
        }

        adapter = new ArrayAdapter<>(context, R.layout.list_item, R.id.tvListItem, list);
        lvEditTextInfo.setAdapter(adapter);
        //			if (hasUrl) {
        //			}
        lvEditTextInfo.smoothScrollBy(60, 200);
    }


    //UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //Data数据区(存在数据获取或处理代码，但不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    public static final int TYPE_NICK = EditTextInfoWindow.TYPE_NICK;
    public static final int TYPE_NAME = EditTextInfoWindow.TYPE_NAME;

    public static final int TYPE_PHONE = EditTextInfoWindow.TYPE_PHONE;
    public static final int TYPE_WEBSITE = EditTextInfoWindow.TYPE_WEBSITE;
    public static final int TYPE_EMAIL = EditTextInfoWindow.TYPE_EMAIL;
    public static final int TYPE_FAX = EditTextInfoWindow.TYPE_FAX;

    public static final int TYPE_USUALADDRESS = EditTextInfoWindow.TYPE_USUALADDRESS;
    public static final int TYPE_MAILADDRESS = EditTextInfoWindow.TYPE_MAILADDRESS;
    public static final int TYPE_SCHOOL = EditTextInfoWindow.TYPE_SCHOOL;
    public static final int TYPE_COMPANY = EditTextInfoWindow.TYPE_COMPANY;

    public static final int TYPE_PROFESSION = EditTextInfoWindow.TYPE_PROFESSION;
    public static final int TYPE_NOTE = EditTextInfoWindow.TYPE_NOTE;
    //	public static final int TYPE_OTHER = EditTextInfoWindow.TYPE_OTHER;

    public static final String INTENT_TYPE = EditTextInfoWindow.INTENT_TYPE;
    public static final String INTENT_KEY = EditTextInfoWindow.INTENT_KEY;
    public static final String INTENT_VALUE = EditTextInfoWindow.INTENT_VALUE;

    private int intentType = 0;
    private int maxEms = 30;
    private boolean hasList = false;
    private boolean hasUrl = false;

    private ArrayList<String> list;

    @SuppressLint("SetTextI18n")
    @Override
    public void initData() {//必须调用
        intent = getIntent();

        intentType = intent.getIntExtra(INTENT_TYPE, 0);
        if (isNotEmpty(intent.getStringExtra(INTENT_KEY), true)) {
            assert tvBaseTitle != null;
            Objects.requireNonNull(tvBaseTitle).setText(getCurrentString());
        }
        etEditTextInfo.setSingleLine(intentType != TYPE_NOTE);

        switch (intentType) {
            case TYPE_NICK:
                maxEms = 20;
                break;
            case TYPE_PHONE:
                etEditTextInfo.setInputType(InputType.TYPE_CLASS_PHONE);
                maxEms = 11;
                break;
            case TYPE_EMAIL:
                etEditTextInfo.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                maxEms = 60;
                break;
            case TYPE_WEBSITE:
                etEditTextInfo.setInputType(InputType.TYPE_TEXT_VARIATION_WEB_EDIT_TEXT);
                maxEms = 200;
                break;
            case TYPE_MAILADDRESS:
                maxEms = 60;
                break;
            case TYPE_PROFESSION:
                tvEditTextInfoRemind.setText("所属行业");
                maxEms = 15;
            case TYPE_NOTE:
                maxEms = 100;
                break;
            default:
                break;
        }
        etEditTextInfo.setMaxEms(maxEms);
        tvEditTextInfoRemind.setText("限" + maxEms / 2 + "个字（或" + maxEms + "个字符）");


        getList(intentType);
    }

    private int requestSize = 20;

    /*
     * 获取列表
     *
     * @param listType
     * @return
     * @author lemon
     */
    protected void getList(final int listType) {
        if (hasList) {
            list = new ArrayList<>();
            runThread(TAG + "getList", () -> {
                Log.i(TAG, "getList  listType = " + listType);
                if (listType == TYPE_PROFESSION) {
                    list = new ArrayList<>(Arrays.asList(context.getResources().getStringArray(R.array.profesions)));
                }

                runUiThread(() -> {
                    dismissProgressDialog();
                    if (hasList) {
                        setList(list);
                    }
                });
            });
        }

    }

    private void saveAndExit() {
        String editedValue = getTrimedString(etEditTextInfo);
        if (editedValue.equals("" + getIntent().getStringExtra(INTENT_VALUE))) {
            showShortToast("内容没有改变哦~");
        } else {
            intent = new Intent();
            intent.putExtra(RESULT_TYPE, getIntent().getIntExtra(INTENT_TYPE, -1));
            //				intent.putExtra(RESULT_TYPE, StringUtil.getTrimedString(etEditTextInfo));
            //				intent.putExtra(RESULT_KEY, StringUtil.getTrimedString(etEditTextInfo));
            intent.putExtra(RESULT_VALUE, editedValue);
            setResult(RESULT_OK, intent);

            exitAnim = R.anim.left_push_out;
            finish();
        }
    }

    //Data数据区(存在数据获取或处理代码，但不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //Event事件区(只要存在事件监听代码就是)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    private String inputedString;

    private static final long SEARCH_DELAY_TIME = 240;
    private Handler searchHandler;

    @SuppressLint({"ClickableViewAccessibility", "SetTextI18n"})
    @Override
    public void initEvent() {//必须调用

        searchHandler = new Handler(msg -> {

            Log.i(TAG, "inputedString = " + inputedString + "msg.obj = " + msg.obj);
            if (inputedString != null) {
                if (inputedString.equals(msg.obj)) {
                    getList(intentType);
                }
            }
            return false;
        });
        etEditTextInfo.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                inputedString = getTrimedString(s);
                if (!isNotEmpty(inputedString, true)) {
                    ivEditTextInfoClear.setVisibility(View.GONE);
                } else {
                    ivEditTextInfoClear.setVisibility(View.VISIBLE);
                    if (hasUrl) {
                        Message msg = new Message();
                        msg.obj = inputedString;
                        searchHandler.sendMessageDelayed(msg, SEARCH_DELAY_TIME);
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        ivEditTextInfoClear.setOnClickListener(v -> etEditTextInfo.setText(""));

        etEditTextInfo.setText(getTrimedString(getIntent().getStringExtra(INTENT_VALUE)));
        etEditTextInfo.setSelection(getLength(etEditTextInfo, true));

        if (hasList) {
            EditTextUtil.hideKeyboard(context, etEditTextInfo);

            if (hasUrl) {
                lvEditTextInfo.setOnScrollListener(new OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(AbsListView view, int scrollState) {
                    }

                    @Override
                    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                        if (adapter != null && lvEditTextInfo.getLastVisiblePosition() >= adapter.getCount() - 1) {
                            requestSize += 20;
                            Log.i(TAG, "initEvent  lvEditTextInfo.setOnScrollListener( >> onScroll getList(intentType);requestSize = " + requestSize);
                            getList(intentType);
                        }
                    }
                });
                //				} else {
            }
            lvEditTextInfo.setOnItemClickListener((parent, view, position, id) -> {
                //					if (hasUrl) {
                //						position -= lvEditTextInfo.getHeaderViewsCount();
                //					}
                if (position < adapter.getCount()) {
                    etEditTextInfo.setText("" + adapter.getItem(position));
                    if (hasUrl) {
                        intent = new Intent();
                        intent.putExtra(RESULT_TYPE, getIntent().getIntExtra(INTENT_TYPE, -1));
                        intent.putExtra(RESULT_KEY, getIntent().getStringExtra(INTENT_KEY));
                        intent.putExtra(RESULT_VALUE, adapter.getItem(position));
                        setResult(RESULT_OK, intent);
                        finish();
                        return;
                    }
                    saveAndExit();
                }
            });
            lvEditTextInfo.setOnTouchListener((v, event) -> {
                EditTextUtil.hideKeyboard(context, etEditTextInfo);
                return false;
            });
        }

    }

    @Override
    public void onDragBottom(boolean rightToLeft) {
        if (rightToLeft) {
            saveAndExit();
            return;
        }
        finish();
    }


    @Override
    public void onForwardClick(View v) {
        if (!hasUrl) {
            onDragBottom(true);
        } else {
            Message msg = new Message();
            msg.obj = inputedString;
            searchHandler.sendMessage(msg);
        }
    }

    //系统自带监听方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    @Override
    public void finish() {
        super.finish();
        EditTextUtil.hideKeyboard(context, etEditTextInfo);
    }

    //类相关监听<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


    //类相关监听>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    //系统自带监听方法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //Event事件区(只要存在事件监听代码就是)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //内部类,尽量少用<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


    //内部类,尽量少用>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

}