package qian.xin.library.ui;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import qian.xin.library.R;
import qian.xin.library.base.BaseView;
import qian.xin.library.util.StringUtil;

/*
 * 自定义顶栏切换标签View
 *
 * @author Lemon
 * @warn 复制到其它工程内使用时务必修改import R文件路径为所在应用包名
 * @use <br> TopTabView modleView = new TopTabView(context, inflater);
 * <br> adapter中使用:[具体见.BaseTabActivity]
 * <br> convertView = modleView.getView();
 * <br> 或  其它类中使用
 * <br> containerView.addView(modleView.getConvertView());
 * <br> 然后
 * <br> modleView.bindView(object);
 * <br> modleView.setOnTabSelectedListener(onItemSelectedListener);
 */
public class TopTabView extends BaseView<String[]> {
    private static final String TAG = "TopTabView";

    /*
     *
     */
    public interface OnTabSelectedListener {
        //		void beforeTabSelected(TextView tvTab, int position, int id);
        void onTabSelected(TextView tvTab, int position, int id);
    }

    private OnTabSelectedListener onTabSelectedListener;

    public void setOnTabSelectedListener(OnTabSelectedListener onTabSelectedListener) {
        this.onTabSelectedListener = onTabSelectedListener;
    }

    private LayoutInflater inflater;

    private int minWidth;

    public TopTabView(AppCompatActivity context, int minWidth, @LayoutRes int resource) {
        super(context, resource);
        this.minWidth = minWidth;
        this.inflater = context.getLayoutInflater();
    }


    private int currentPosition = 0;

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }


    public TextView tvTopTabViewTabFirst;
    public TextView tvTopTabViewTabLast;

    public LinearLayout llTopTabViewContainer;

    @Override
    public View createView() {
        tvTopTabViewTabFirst = findView(R.id.tvTopTabViewTabFirst);
        tvTopTabViewTabLast = findView(R.id.tvTopTabViewTabLast);

        llTopTabViewContainer = findView(R.id.llTopTabViewContainer);

        return super.createView();
    }


    public String[] names;//传进来的数据

    public int getCount() {
        return names.length;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public TextView getCurrentTab() {
        return tvTabs[getCurrentPosition()];
    }

    /*
     * @param nameList
     */
    public void bindView(List<String> nameList) {
        if (nameList != null) {
            for (int i = 0; i < nameList.size(); i++) {
                names[i] = nameList.get(i);
            }
        }
        bindView(names);
    }

    @Override
    public void bindView(String[] names) {
        if (names == null || names.length < 2) {
            Log.e(TAG, "setInerView names == null || names.length < 2 >> return; ");
            return;
        }
        super.bindView(names);
        this.names = names;
        int lastPosition = getCount() - 1;

        tvTabs = new TextView[getCount()];

        tvTabs[0] = tvTopTabViewTabFirst;
        tvTabs[lastPosition] = tvTopTabViewTabLast;

        llTopTabViewContainer.removeAllViews();
        for (int i = 0; i < tvTabs.length; i++) {
            final int position = i;

            if (tvTabs[position] == null) {
                //viewgroup.addView(child)中的child相同，否则会崩溃
                tvTabs[position] = (TextView) inflater.inflate(R.layout.top_tab_tv_center, llTopTabViewContainer, false);
                llTopTabViewContainer.addView(tvTabs[position]);

                View divider = inflater.inflate(R.layout.divider_vertical_1dp, llTopTabViewContainer, false);
                divider.setBackgroundResource(R.color.white);
                llTopTabViewContainer.addView(divider);
            }
            tvTabs[position].setText(StringUtil.getTrimedString(names[position]));
            tvTabs[position].setOnClickListener(v -> select(position));

            int width = tvTabs[position].getWidth();
            if (minWidth < width) {
                minWidth = width;
            }
        }

        //防止超出
        int maxWidth = llTopTabViewContainer.getMeasuredWidth() / tvTabs.length;
        if (minWidth > maxWidth) {
            minWidth = maxWidth;
        }
        for (TextView tvTab : tvTabs) {
            //保持一致
            tvTab.setMinWidth(minWidth);

            //防止超出
            if (tvTab.getWidth() > maxWidth) {
                tvTab.setWidth(maxWidth);
            }
        }


        select(currentPosition);
    }

    private TextView[] tvTabs;

    /*
     * 选择tab
     *
     * @param position
     */
    public void select(int position) {
        Log.i(TAG, "select  position = " + position);
        if (position < 0 || position >= getCount()) {
            Log.e(TAG, "select  position < 0 || position >= getCount() >> return;");
            return;
        }

        for (int i = 0; i < tvTabs.length; i++) {
            tvTabs[i].setSelected(i == position);
        }

        if (onTabSelectedListener != null) {
            onTabSelectedListener.onTabSelected(tvTabs[position]
                    , position, tvTabs[position].getId());
        }

        this.currentPosition = position;
    }

}
