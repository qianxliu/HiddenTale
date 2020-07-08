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

package hidden.edu.demo;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import hidden.edu.R;
import qian.xin.library.base.BaseView;
import qian.xin.library.model.Entry;
import qian.xin.library.util.StringUtil;

/*
  使用方法：复制>粘贴>改名>改代码
 */

/**
 * 自定义View模板，当View比较庞大复杂(解耦效果明显)或使用次数>=2(方便重用)时建议使用
 *
 * @author Lemon
 * @use <br> DemoView demoView = new DemoView(context, resources);
 * <br> adapter中使用:[具体参考.BaseViewAdapter(getView使用自定义View的写法)]
 * <br> convertView = demoView.createView(inflater);
 * <br> demoView.bindView(position, data);
 * <br> 或 其它类中使用:
 * <br> containerView.addView(demoView.createView(inflater));
 * <br> demoView.bindView(data);
 * <br> 然后
 * <br> demoView.setOnDataChangedListener(onDataChangedListener); data = demoView.getData();//非必需
 * <br> demoView.setOnClickListener(onClickListener);//非必需
 * <br> ...
 * @see DemoAdapter#createView
 */
public class DemoView extends BaseView<Entry<String, String>> implements OnClickListener {

    DemoView(AppCompatActivity context, ViewGroup parent) {
        super(context, R.layout.demo_view, parent);  //TODO demo_view改为你所需要的layout文件
    }


    //示例代码<<<<<<<<<<<<<<<<
    private ImageView ivDemoViewHead;
    private TextView tvDemoViewName;
    private TextView tvDemoViewNumber;

    //示例代码>>>>>>>>>>>>>>>>
    @Override
    public View createView() {

        //示例代码<<<<<<<<<<<<<<<<
        ivDemoViewHead = findView(R.id.ivDemoViewHead, this);
        tvDemoViewName = findView(R.id.tvDemoViewName);
        tvDemoViewNumber = findView(R.id.tvDemoViewNumber);
        //示例代码>>>>>>>>>>>>>>>>

        return super.createView();
    }


    @Override
    public void bindView(Entry<String, String> data_) {
        //示例代码<<<<<<<<<<<<<<<<
        super.bindView(data_ != null ? data_ : new Entry<>());

        itemView.setBackgroundResource(selected ? R.drawable.alpha3 : R.drawable.white_to_alpha);

        tvDemoViewName.setText(StringUtil.getTrimedString(data.getKey()));
        tvDemoViewNumber.setText(StringUtil.getTrimedString(data.getValue()));
        //示例代码>>>>>>>>>>>>>>>>
    }


    //示例代码<<<<<<<<<<<<<<<<
    @Override
    public void onClick(View v) {
        if (data == null) {
            return;
        }
        if (v.getId() == R.id.ivDemoViewHead) {
            data.setKey("New " + data.getKey());
            bindView(data);
            if (onDataChangedListener != null) {
                onDataChangedListener.onDataChanged();
            }
        }
    }
    //示例代码>>>>>>>>>>>>>>>>


}
