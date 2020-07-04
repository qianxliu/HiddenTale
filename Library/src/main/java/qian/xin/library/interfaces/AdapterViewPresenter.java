package qian.xin.library.interfaces;

import android.view.ViewGroup;

public interface AdapterViewPresenter<V> {

	/**生成新的BV
	 * @param viewType
	 * @param parent
	 * @return
	 */
    V createView(int viewType, ViewGroup parent);

	/**设置BV显示
	 * @param position
	 * @param bv
	 * @return
	 */
    void bindView(int position, V bv);

}
