package qian.xin.library.ui;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import qian.xin.library.R;
import qian.xin.library.model.Entry;
import qian.xin.library.util.StringUtil;


/*
 * 网格选择器adapter
 *
 * @use new GridPickerAdapter(...); 具体参考.DemoAdapter
 */
public class GridPickerAdapter extends BaseAdapter {

    public static final int TYPE_CONTNET_ENABLE = 0;
    public static final int TYPE_CONTNET_UNABLE = 1;
    public static final int TYPE_TITLE = 2;

    private OnItemSelectedListener onItemSelectedListener;

    public void setOnItemSelectedListener(OnItemSelectedListener onItemSelectedListener) {
        this.onItemSelectedListener = onItemSelectedListener;
    }

    private final LayoutInflater inflater;
    private final Resources resources;
    private int currentPosition;//初始选中位置
    private int height;//item高度

    public GridPickerAdapter(AppCompatActivity context, int currentPosition, int height) {
        this.inflater = context.getLayoutInflater();
        this.resources = context.getResources();
        this.currentPosition = currentPosition;
        this.height = height;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public String getCurrentItemName() {
        return StringUtil.getTrimedString(getItem(getCurrentPosition()).getValue());
    }


    public List<Entry<Integer, String>> list;

    /*
     * 刷新列表
     *
     * @param list
     */
    public synchronized void refresh(List<Entry<Integer, String>> list) {
        this.list = list == null ? null : new ArrayList<>(list);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Entry<Integer, String> getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }


    //getView的常规写法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    private LayoutParams layoutParams;

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        ViewHolder holder = convertView == null ? null : (ViewHolder) convertView.getTag();
        if (holder == null) {
            convertView = inflater.inflate(R.layout.grid_picker_item, parent, false);
            holder = new ViewHolder();

            holder.tv = convertView.findViewById(R.id.tvGridPickerItem);

            convertView.setTag(holder);
        }

        final Entry<Integer, String> data = getItem(position);
        final int type = data.getKey();
        //		if (isEnabled == false && position == currentPosition) {
        //			currentPosition ++;
        //		}

        holder.tv.setText(StringUtil.getTrimedString(data.getValue()));
        holder.tv.setTextColor(resources.getColor(type == TYPE_CONTNET_ENABLE ? R.color.black : R.color.gray_2));
        holder.tv.setBackgroundResource(position == currentPosition
                ? R.drawable.round_green : R.drawable.null_drawable);

        convertView.setBackgroundResource(type == TYPE_TITLE ? R.color.alpha_1 : R.color.alpha_complete);

        convertView.setOnClickListener(v -> {
            if (type == TYPE_CONTNET_ENABLE) {
                currentPosition = position;
                if (onItemSelectedListener != null) {
                    onItemSelectedListener.onItemSelected(null, v, position, getItemId(position));
                }
                notifyDataSetChanged();
            }
        });

        if (height > 0) {
            if (layoutParams == null || layoutParams.height != height) {
                layoutParams = convertView.getLayoutParams();
                layoutParams.height = height;
            }
            convertView.setLayoutParams(layoutParams);
        }

        return convertView;
    }

    static class ViewHolder {
        public TextView tv;
    }
    //getView的常规写法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

}
