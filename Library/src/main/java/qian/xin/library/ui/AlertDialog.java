package qian.xin.library.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import qian.xin.library.R;
import qian.xin.library.util.StringUtil;

/*通用对话框类
 * @use new AlertDialog(...).show();
 */
public class AlertDialog extends Dialog implements android.view.View.OnClickListener {

    //	private static final String TAG = "AlertDialog";

    /*
     * 自定义Dialog监听器
     */
    public interface OnDialogButtonClickListener {

        /*点击按钮事件的回调方法
         * @param requestCode 传入的用于区分某种情况下的showDialog
         * @param isPositive
         */
        void onDialogButtonClick(int requestCode, boolean isPositive);
    }


    private String title;
    private String message;
    private String strPositive;
    private String strNegative;
    private boolean showNegativeButton;
    private int requestCode;
    private OnDialogButtonClickListener listener;

    /*
     * 带监听器参数的构造函数
     */
    public AlertDialog(Activity context, String title, String message, boolean showNegativeButton,
                       int requestCode, OnDialogButtonClickListener listener) {
        super(context, R.style.MyDialog);

        this.title = title;
        this.message = message;
        this.showNegativeButton = showNegativeButton;
        this.requestCode = requestCode;
        this.listener = listener;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alert_dialog);
        setCanceledOnTouchOutside(true);

        TextView tvTitle = findViewById(R.id.tvAlertDialogTitle);
        TextView tvMessage = findViewById(R.id.tvAlertDialogMessage);
        Button btnPositive = findViewById(R.id.btnAlertDialogPositive);
        Button btnNegative = findViewById(R.id.btnAlertDialogNegative);

        tvTitle.setVisibility(StringUtil.isNotEmpty(title, true) ? View.VISIBLE : View.GONE);
        tvTitle.setText("" + StringUtil.getCurrentString());

        if (StringUtil.isNotEmpty(strPositive, true)) {
            btnPositive.setText(StringUtil.getCurrentString());
        }
        btnPositive.setOnClickListener(this);

        if (showNegativeButton) {
            if (StringUtil.isNotEmpty(strNegative, true)) {
                btnNegative.setText(StringUtil.getCurrentString());
            }
            btnNegative.setOnClickListener(this);
        } else {
            btnNegative.setVisibility(View.GONE);
        }

        tvMessage.setText(StringUtil.getTrimedString(message));
    }

    @Override
    public void onClick(final View v) {
        if (v.getId() == R.id.btnAlertDialogPositive) {
            listener.onDialogButtonClick(requestCode, true);
        } else if (v.getId() == R.id.btnAlertDialogNegative) {
            listener.onDialogButtonClick(requestCode, false);
        }
        dismiss();
    }
}

