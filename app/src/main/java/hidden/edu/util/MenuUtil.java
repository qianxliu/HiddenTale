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

package hidden.edu.util;

import java.util.ArrayList;
import java.util.List;

import hidden.edu.R;
import zuo.biao.library.model.Menu;

/**
 * 底部菜单工具类
 *
 * @author Lemon
 */
public class MenuUtil {

    private static final String NAME_SEND_MESSAGE = "发信息";//信息记录显示在通话记录左边
    private static final String NAME_CALL = "呼叫";//信息记录显示在通话记录左边
    private static final String NAME_SEND = "发送";
    private static final String NAME_QRCODE = "二维码";
    private static final String NAME_ADD_TO = "添加至";
    private static final String NAME_EDIT = "编辑";
    private static final String NAME_EDIT_ALL = "编辑所有";
    private static final String NAME_DELETE = "删除";
    private static final String NAME_SEND_EMAIL = "发邮件";

    public static final int INTENT_CODE_SEND_MESSAGE = 1;//信息记录显示在通话记录左边
    public static final int INTENT_CODE_CALL = 2;//信息记录显示在通话记录左边
    public static final int INTENT_CODE_SEND = 3;
    public static final int INTENT_CODE_QRCODE = 4;
    //	public static final int INTENT_CODE_SETTING = 5;
    private static final int INTENT_CODE_ADD_TO = 6;
    private static final int INTENT_CODE_EDIT = 7;
    private static final int INTENT_CODE_EDIT_ALL = 8;
    private static final int INTENT_CODE_DELETE = 9;
    public static final int INTENT_CODE_SEND_EMAIL = 10;

    private static Menu FSB_SEND_MESSAGE = new Menu(NAME_SEND_MESSAGE, R.drawable.mail_light, INTENT_CODE_SEND_MESSAGE);
    private static Menu FSB_CALL = new Menu(NAME_CALL, R.drawable.call_light, INTENT_CODE_CALL);
    private static Menu FSB_SEND = new Menu(NAME_SEND, R.drawable.send_light, INTENT_CODE_SEND);
    private static Menu FSB_QRCODE = new Menu(NAME_QRCODE, R.drawable.qrcode, INTENT_CODE_QRCODE);
    //	public static Menu FSB_SETTING = new Menu(NAME_SETTING, R.drawable.setting_light, INTENT_CODE_SETTING);
    public static Menu FSB_ADD_TO = new Menu(NAME_ADD_TO, R.drawable.add_light, INTENT_CODE_ADD_TO);
    public static Menu FSB_EDIT = new Menu(NAME_EDIT, R.drawable.edit_light, INTENT_CODE_EDIT);
    public static Menu FSB_EDIT_ALL = new Menu(NAME_EDIT_ALL, R.drawable.edit_light, INTENT_CODE_EDIT_ALL);
    public static Menu FSB_DELETE = new Menu(NAME_DELETE, R.drawable.delete_light, INTENT_CODE_DELETE);
    private static Menu FSB_SEND_EMAIL = new Menu(NAME_SEND_EMAIL, R.drawable.mail_light, INTENT_CODE_SEND_EMAIL);

    public static final int CONTACT_LIST_FRAGMENT_MULTI = 1;

    public static final int USER = 1;

    public static List<Menu> getMenuList(int which) {
        List<Menu> list = new ArrayList<>();
        if (which == USER) {
            list.add(FSB_SEND_MESSAGE);
            list.add(FSB_CALL);
            list.add(FSB_SEND_EMAIL);
            list.add(FSB_SEND);
            list.add(FSB_QRCODE);
        }
        return list;
    }

}
