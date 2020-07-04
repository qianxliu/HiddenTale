package hidden.edu.adapter

import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import hidden.edu.model.User
import hidden.edu.view.UserView
import qian.xin.library.base.BaseAdapter

class UserAdapter  //	private static final String TAG = "UserAdapter";
(context: AppCompatActivity?) : BaseAdapter<User?, UserView?>(context) {
    override fun createView(position: Int, parent: ViewGroup): UserView {
        return UserView(context, parent)
    }

    override fun getItemId(position: Int): Long {
        return getItem(position)!!.getId()
    }
}