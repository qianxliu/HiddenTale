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

package hidden.edu.view;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

import org.jetbrains.annotations.NotNull;

import hidden.edu.R;
import hidden.edu.model.User;
import qian.xin.library.base.BaseModel;
import qian.xin.library.base.BaseView;
import qian.xin.library.ui.WebViewActivity;
import qian.xin.library.util.CommonUtil;
import qian.xin.library.util.StringUtil;

/*
 * 用户View
 *
 * @author Lemon
 * @use <br> UserView userView = new UserView(context, resources);
 * <br> adapter中使用:[具体参考.BaseViewAdapter(getView使用自定义View的写法)]
 * <br> convertView = userView.createView(inflater);
 * <br> userView.bindView(position, data);
 * <br> 或  其它类中使用:
 * <br> containerView.addView(userView.createView(inflater));
 * <br> userView.bindView(data);
 * <br> 然后
 * <br> userView.setOnDataChangedListener(onDataChangedListener);data = userView.getData();//非必需
 * <br> userView.setOnClickListener(onClickListener);//非必需
 */
public class UserView extends BaseView<User> implements OnClickListener {

    public UserView(AppCompatActivity context, ViewGroup parent) {
        super(context, R.layout.user_view, parent);
    }

    private ImageView ivUserViewHead;
    private ImageView ivUserViewStar;

    private TextView tvUserViewSex;

    private TextView tvUserViewName;
    private TextView tvUserViewTime;
    private TextView tvUserViewOral;

    @SuppressLint("InflateParams")
    @Override
    public View createView() {
        ivUserViewHead = findView(R.id.ivUserViewHead, this);
        ivUserViewStar = findView(R.id.ivUserViewStar, this);

        tvUserViewSex = findView(R.id.tvUserViewSex, this);

        tvUserViewName = findView(R.id.tvUserViewName);

        tvUserViewTime = findView(R.id.tvUserViewTime);
        tvUserViewOral = findView(R.id.tvUserViewOral);

        return super.createView();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void bindView(User data_) {
        super.bindView(data_ != null ? data_ : new User());

        Glide.with(context).asBitmap().load(data.getHead()).into(new CustomTarget<Bitmap>() {

            @Override
            public void onResourceReady(@NotNull Bitmap bitmap, Transition<? super Bitmap> transition) {
                ivUserViewHead.setImageBitmap(CommonUtil.toRoundCorner(bitmap, bitmap.getWidth() / 2));
            }

            @Override
            public void onLoadCleared(@Nullable Drawable placeholder) {

            }
        });

        ivUserViewStar.setImageResource(data.getStarred() ? R.drawable.star_light : R.drawable.texture2);

        switch (data.getSex()) {
            case User.TREASURE:
                tvUserViewSex.setBackgroundResource(R.drawable.circle_white);
                tvUserViewSex.setText("宝");
                tvUserViewSex.setTextColor(R.drawable.texture3);
                break;
            case User.THEME:
                tvUserViewSex.setBackgroundResource(R.drawable.circle_pink);
                tvUserViewSex.setText("主");
                tvUserViewSex.setTextColor(R.drawable.texture2);
                break;
            case User.SCHOOL:
                tvUserViewSex.setBackgroundResource(R.drawable.circle_blue);
                tvUserViewSex.setText("校");
                tvUserViewSex.setTextColor(R.drawable.texture1);
                break;
            case User.ARCHIVE:
                tvUserViewSex.setBackgroundResource(R.drawable.circle_yellow);
                tvUserViewSex.setText("档");
                tvUserViewSex.setTextColor(R.drawable.texture1);
                break;
        }


        tvUserViewName.setText(StringUtil.getTrimedString(data.getName()));

        tvUserViewTime.setText("年代:" + StringUtil.getTrimedString(data.getTime()));
        //tvUserViewId.setText("Id:" + data.getId());
        tvUserViewOral.setText("材质:" + StringUtil.getNoBlankString(data.getOral()));
    }

    @Override
    public void onClick(View v) {
        if (!BaseModel.isCorrect(data)) {
            return;
        }
        if (v.getId() == R.id.ivUserViewHead) {
            toActivity(WebViewActivity.createIntent(context, data.getName(), data.getHead()));
        } else {
            if (v.getId() == R.id.ivUserViewStar) {
                data.setStarred(!data.getStarred());
                /*
                 case R.id.tvUserViewSex:
                 data.setSex(data.getSex() == User.SEX_FEMALE ? User.SEX_MAIL : User.SEX_FEMALE);
                 break;
                 */
            }
            bindView(data);
        }
    }
}