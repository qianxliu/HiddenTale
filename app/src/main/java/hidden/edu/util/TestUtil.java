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
            "https://git.nwu.edu.cn/2018104171/pdf/raw/master/1.png",
            "https://git.nwu.edu.cn/2018104171/pdf/raw/master/2.png",
            "https://git.nwu.edu.cn/2018104171/pdf/raw/master/3.png",
            "https://git.nwu.edu.cn/2018104171/pdf/raw/master/4.png",
            "https://git.nwu.edu.cn/2018104171/pdf/raw/master/5.png",
            "https://git.nwu.edu.cn/2018104171/pdf/raw/master/6.png",
            //7
            "https://git.nwu.edu.cn/2018104171/pdf/raw/master/7.png",
            "https://git.nwu.edu.cn/2018104171/pdf/raw/master/8.png",
            "https://git.nwu.edu.cn/2018104171/pdf/raw/master/9.png",
            //10
            "https://git.nwu.edu.cn/2018104171/pdf/raw/master/10.png",
            //11
            "https://git.nwu.edu.cn/2018104171/pdf/raw/master/11.png",
            //12
            "https://git.nwu.edu.cn/2018104171/pdf/raw/master/12.png",
            //13
            "https://git.nwu.edu.cn/2018104171/pdf/raw/master/13.png",
            //14
            "https://git.nwu.edu.cn/2018104171/pdf/raw/master/14.png",
            //15
            "https://git.nwu.edu.cn/2018104171/pdf/raw/master/15.png",
            //16
            "https://git.nwu.edu.cn/2018104171/pdf/raw/master/16.png",
            //17
            "https://git.nwu.edu.cn/2018104171/pdf/raw/master/17.png",
            //18
            "https://git.nwu.edu.cn/2018104171/pdf/raw/master/18.png",
            "https://git.nwu.edu.cn/2018104171/pdf/raw/master/19.png",
            //20
            "https://git.nwu.edu.cn/2018104171/pdf/raw/master/20.png",
            //21
            "https://git.nwu.edu.cn/2018104171/pdf/raw/master/21.png",
    };

    public static String getPicture(int index) {
        return index < 0 || index >= URLS.length ? null : URLS[index];
    }
}