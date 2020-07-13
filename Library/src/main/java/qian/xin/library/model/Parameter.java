package qian.xin.library.model;

/*
 * Http请求参数类，最快在 19.0 删除，请使用 Map<String, Object> 替代
 */

public class Parameter extends Entry<String, Object> {

    private static final long serialVersionUID = 1L;
    public String key;
    public Object value;

    public Parameter(String key, Object value) {
        super(key, value);
    }
}