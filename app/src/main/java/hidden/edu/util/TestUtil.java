package hidden.edu.util;

import java.util.ArrayList;
import java.util.List;

import hidden.edu.model.User;

/**
 * 仅测试用，图片地址可能会失效
 *
 * @author Lemon
 */
public class TestUtil {

    public static List<User> getUserList() {
        return getUserList(0);
    }

    /**
     * @param page 页码
     */
    private static List<User> getUserList(int page) {
        return getUserList(page, URLS.length);
    }

    /*
     * @param page  页码
     * @param count 最大一页数量
     */
    //获取列表
    public static List<User> getUserList(int page, int count) {
        List<User> list = new ArrayList<>();
        long userId;
        User user;
        int length = (count <= 0 || count > URLS.length ? URLS.length : count);
        int index;
        for (int i = 0; i < length; i++) {
            userId = i + page * length + 1;
            index = i + page * length;
            while (index >= URLS.length) {
                index -= URLS.length;
            }
            if (index < 0) {
                index = 0;
            }

            user = new User();
            user.setId(userId);
            if (i < 10)
                user.setSex(0);
            else if (i < 15)
                user.setSex(1);
            else if (i < 17)
                user.setSex(2);
            else if (i < 21)
                user.setSex(3);
            user.setHead(URLS[index]);
            user.setName(NAMES[index]);
            user.setTime(TIMES[index]);
            user.setOral(ORALS[index]);
            user.setStarred(Math.random() * 5 % 5 == 3);
            list.add(user);
        }
        return list;
    }

    //private st
    private static String[] ORALS = {
            //1
            "石碑",
            //2
            "银",
            //3
            "铜",
            //4
            "银",
            //5
            "陶瓷",
            //6
            "金银",
            //7
            "玛瑙",
            //8
            "铜，铁",
            //9
            "青铜",
            //10
            "青铜",
            //11
            "文化",
            "文化",
            "文化",
            "文化",
            "文化",
            "高校故事",
            "高校故事",
            "珍贵档案故事",
            "珍贵档案故事",
            "珍贵档案故事",
            "珍贵档案故事",
    };

    private static String[] TIMES = {
            "秦代",
            "唐代",
            //3
            "唐代",
            "唐代",
            "唐代",
            "唐代",
            "唐代",
            //8
            "唐代",
            //9
            "西周",
            //10
            "西周",
            //11
            "现代",
            "现代",
            "现代",
            "现代",
            "现代",
            //16
            "未来",
            "未来",
            //18
            "档案",
            "档案",
            "档案",
            "档案",
    };
    private static String[] NAMES = {
            "杜虎符",
            "大秦景教流行中国碑",
            "鎏金舞马衔杯纹银壶",
            "镂空飞鸟葡萄纹银香囊",
            "唐三彩骆驼载乐俑",
            //6
            "鎏金鹦鹉纹提梁壶",
            "镶金兽首玛瑙杯",
            "鎏金铁芯铜龙",
            "多友鼎",
            //10
            "利簋",
            "八路军西安办事处",
            "西安事变纪念馆",
            //13
            "三秦抗战纪念馆",
            "葛牌镇区苏维埃政府纪念馆",
            "蓝田特别支部纪念馆",
            //16
            "西北大学",
            "西安交通大学",
            //18
            "西安辛亥档案揭秘",
            "安康机场作为抗战前线的历史",
            "清代陕西紫阳贡茶信票",
            "珍藏在西安市档案馆中的谍战故事",
    };
    /**
     * 图片地址，仅供测试用
     */
    private static String[] URLS = {
            //杜虎符
            "http://upload.art.ifeng.com/2017/1228/1514426877471.jpg",
            "https://cdn.shuge.org/uploads/2017/12/jing-jiao-liu-xing-zhong-guo-bei06-640x350.jpg",
            "http://www.ctaoci.com/Upload/news/200783084715_11878856862507434727823175673.jpg",
            "http://wenyi.gmw.cn/attachement/jpg/site2/20190724/f44d3075892a1ea201ba32.jpg",
            "http://art.people.com.cn/NMediaFile/2013/0916/MAIN201309160832000497991621947.jpg",
            "http://a1.att.hudong.com/80/32/01200000027621134374321610400_s.jpg",
            //7
            "http://a2.att.hudong.com/73/25/01300001216886130513252197445.jpg",
            "https://static.chiphell.com/forum/201810/06/174231pf04s2fl057alys2.jpg",
            "http://a2.att.hudong.com/16/15/01100000000022123973156672788.jpg",
            //10
            "http://a4.att.hudong.com/02/82/01300000332400125360820176081.jpg",
            //11
            "https://youimg1.c-ctrip.com/target/100j13000000uene1075E.jpg",
            //12
            "http://s3.lvjs.com.cn/uploads/pc/place2/2016-07-06/edf45a19-2b4a-4a97-8e7b-8ee039e7daa0_555_370.jpg",
            //13
            "https://ss2.meipian.me/users/3167963/41c8abdab62f47288f6933982d17ede0.jpg",
            //14
            "http://www.sanqinyou.com/uploadfiles/2010-02-26/midd20100226_112029_890.jpg",
            //15
            "https://cdn.moji002.com/images/simgs/2019/09/29/15697379170.75931200.1075_android.jpg",
            //16
            "http://www.nwu.edu.cn/images/img_main_bar2_logo.png",
            //17
            "http://www.xjtu.edu.cn/img/logo_pic99.png",
            //18
            "https://git.nwu.edu.cn/2018104171/pdf/raw/master/21.png",
            "http://sxakjc.com/storage/app/media/zou-jin-an-kang/zoujin4.png",
            //20
            "https://www.ziyanghong.com/export/sites/default/tea/tribute/.content/images/2016111417574149.jpg_752293428.jpg",
            //21
            "https://git.nwu.edu.cn/2018104171/pdf/raw/master/20.png",
    };

    public static String getPicture(int index) {
        return index < 0 || index >= URLS.length ? null : URLS[index];
    }
}