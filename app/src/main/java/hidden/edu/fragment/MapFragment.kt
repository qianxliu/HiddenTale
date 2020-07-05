package hidden.edu.fragment

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import com.amap.api.maps.AMap.*
import com.amap.api.maps.AMapException
import com.amap.api.maps.AMapUtils
import com.amap.api.maps.model.*
import com.amap.api.navi.AmapNaviPage
import com.amap.api.navi.AmapNaviParams
import com.amap.api.navi.AmapNaviType
import com.amap.api.navi.INaviInfoCallback
import com.amap.api.navi.model.NaviLatLng
import com.amap.api.services.core.PoiItem
import com.amap.api.services.poisearch.PoiResult
import hidden.edu.R
import hidden.edu.activity.PdfViewActivity.Companion.createIntent
import hidden.edu.util.TestUtil
import qian.xin.library.base.BaseActivity
import qian.xin.library.ui.AlertDialog
import qian.xin.library.ui.AlertDialog.OnDialogButtonClickListener
import qian.xin.library.util.CommonUtil

class MapFragment : MapFragmentBase(), OnDialogButtonClickListener {
    private var naviLatLng: NaviLatLng? = null
    private val naviInfoCallback: INaviInfoCallback? = null
    override val target: LatLng?
        get() = LatLng(34.343147, 108.939621)

    override var cameraPosition: CameraPosition?
        get() = Companion.cameraPosition
        set(cameraPosition) {
            Companion.cameraPosition = cameraPosition
        }

    private fun icon(marker: Marker, len: Int) {
        when ((Math.random() * len % len).toInt()) {
            0 -> marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.p1))
            1 -> marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.p2))
            2 -> marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.p3))
            3 -> marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.p4))
            4 -> marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.p5))
            5 -> marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.p6))
            6 -> marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.p7))
            7 -> marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.p8))
            8 -> marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.p9))
            9 -> marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.p10))
            10 -> marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.p11))
            11 -> marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.p12))
        }
    }

    @SuppressLint("ShowToast")
    @Throws(AMapException::class)
    override fun init() {
        super.init()

        aMap!!.setOnMapClickListener { v: LatLng ->
            val markerOptions = MarkerOptions()
            markerOptions.position(LatLng(v.latitude, v.longitude)).setFlat(false).zIndex(-2f)
            markerOptions.draggable(true)
            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                    .decodeResource(resources, R.drawable.icon_end)))
            aMap!!.addMarker(markerOptions)
        }
        aMap!!.setOnMarkerDragListener(object : OnMarkerDragListener {
            override fun onMarkerDragStart(marker: Marker) {}
            override fun onMarkerDrag(marker: Marker) {}
            override fun onMarkerDragEnd(marker: Marker) {
                if (marker.zIndex == -2f) marker.remove()
            }
        })
        //生成标记点
        for (i in 1 until latLng.size) {
            val markerOptions = MarkerOptions()
            markerOptions.position(latLng[i]).title(TestUtil.getUserList()[i].name).snippet("""
    坐标${latLng[i]}
    ${BRIEFS[i]}点击查看详细信息
    """.trimIndent())
            if (Math.random() * 2 % 2 == 0.0) markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.p11)) else markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.p12))
            markerOptions.zIndex(i.toFloat())
            aMap!!.addMarker(markerOptions)
        }

        // 定义 Marker 点击事件监听
        // marker 对象被点击时回调的接口
        // 返回 true 则表示接口已响应事件，否则返回false
        val markerClickListener = OnMarkerClickListener { marker: Marker ->
            if (marker.isInfoWindowShown && !marker.isFlat && marker.zIndex != -2f) {
                marker.hideInfoWindow()
                if (Math.random() * 2 % 2 == 0.0) marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.p12)) else marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.p11))
                caculate()
            } else if (!marker.isInfoWindowShown && !marker.isFlat && marker.zIndex != -2f) {
                marker.showInfoWindow()
                icon(marker, (Math.random() * 10 % 10).toInt())
                caculate()
            } else if (marker.zIndex == -2f) {
                AlertDialog(activity, "导航", "导航到这里吗?", true, 0, this).show()
                naviLatLng = NaviLatLng(marker.position.latitude, marker.position.longitude)
            }
            true
        }
        val listener = OnInfoWindowClickListener { marker: Marker ->
            if (marker.zIndex < latLng.size + 1) {
                CommonUtil.toActivity(activity, createIntent(activity as BaseActivity, MyRecyclerFragment.URLS[marker.zIndex.toInt()], (marker.zIndex.toInt() + 1).toString()))
                caculate()
            }
        }
        //绑定信息窗点击事件
        aMap!!.setOnInfoWindowClickListener(listener)
        // 绑定 Marker 被点击事件
        aMap!!.setOnMarkerClickListener(markerClickListener)
        isPosition
    }

    //Dialog choose positive
    override fun onDialogButtonClick(requestCode: Int, isPositive: Boolean) {
        if (isPositive) {
            val naviParams = AmapNaviParams(Poi("", LatLng(aMap!!.myLocation.latitude, aMap!!.myLocation.longitude), ""), null, Poi("目的地", LatLng(naviLatLng!!.latitude, naviLatLng!!.longitude), ""), AmapNaviType.DRIVER)
            AmapNaviPage.getInstance().showRouteActivity(activity!!.applicationContext, naviParams, naviInfoCallback)
        }
    }

    private val isPosition: Unit
        get() {
            if (AMapUtils.calculateLineDistance(LatLng(aMap!!.myLocation.latitude, aMap!!.myLocation.longitude), this.target) < 100000) {
                CommonUtil.showShortToast(activity, "检测到当前位置不在西安市附近")
                aMap!!.myLocationStyle = MyLocationStyle().myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER)
            }
            cameraPosition = cameraPosition
        }

    fun caculate() {
        temp = 100000f
        val o: LatLng
        if (aMap!!.myLocation != null) {
            o = LatLng(aMap!!.myLocation.latitude, aMap!!.myLocation.longitude)
            for (i in latLng.indices) {
                val distance = AMapUtils.calculateLineDistance(o, latLng[i])
                if (distance < 100000) {
                    if (distance < temp) {
                        temp = distance
                    }
                    CommonUtil.showShortToast(activity, "附近宝藏出没!为" + TestUtil.getUserList()[i].time + "的" + TestUtil.getUserList()[i].oral + "制" + TestUtil.getUserList()[i].name + "!" + "距离" + distance + "米")
                }
            }
        } else {
            temp = 0f
        }
    }

    override fun onPoiSearched(poiResult: PoiResult, i: Int) {}
    override fun onPoiItemSearched(poiItem: PoiItem, i: Int) {}

    companion object {
        //西安市经纬度
        private val target = LatLng(34.343147, 108.939621)
        private var cameraPosition = CameraPosition.Builder()
                .target(target).zoom(18f).bearing(0f).tilt(30f).build()
        var temp = 0f
        private val latLng = arrayOf(
                LatLng(34.057441, 108.312201),
                LatLng(34.235222, 108.931813),
                LatLng(34.192109, 108.896464),
                LatLng(34.235222, 108.931813),
                LatLng(34.086456, 108.519344),
                LatLng(34.235222, 108.931813),
                LatLng(34.235222, 108.931813),
                LatLng(34.234321, 108.949763),
                LatLng(34.23777, 108.766144),
                LatLng(34.4763, 109.364455),  //11
                LatLng(34.273603, 108.954741),
                LatLng(34.257313, 108.967721),
                LatLng(34.222472, 109.024829),
                LatLng(33.910533, 109.50539),
                LatLng(34.128889, 109.228452),  //16
                LatLng(34.248333, 108.927083),
                LatLng(34.246207, 108.98378),
                LatLng(34.149169, 108.904904),
                LatLng(34.30448, 108.948038),
                LatLng(34.30448, 108.948038),
                LatLng(34.149169, 108.904904)
        )
        private val BRIEFS = arrayOf(
                "中外文明相互交融的见证\n",
                "唐王朝盛极而衰的挽歌\n",
                "中华现存最早的调兵凭证\n",
                "葡萄花鸟满庭芳，越千年，莫相忘\n",
                "四路上的“流动音团”\n",
                "道教文化的历史见证\n",
                "依色取巧，随形变化的俏色孤品\n",
                "力量与美的杰出代表\n",
                "千年战争历史的见证者\n",
                "西周第一青铜器\n",
                "陕西省西安市古城内西五路北新街七贤庄1号\n",
                "陕西省西安市碑林区建国路69号\n",
                "陕西省西安市雁塔区等驾坡街道长鸣路66号\n",
                "陕西省西安市蓝田县葛牌镇葛牌街\n",
                "陕西省西安市蓝田县孟吴路\n",
                "西安市碑林区太白北路229号\n",
                "西安市碑林区咸宁西路28号\n",
                "陕西省档案馆 陕西省西安市长安区子午大道与学府大街十字东北角\n",
                "西安市档案馆 陕西省西安市未央区未央路103号\n",
                "西安市档案馆 陕西省西安市未央区未央路103号\n",
                "陕西省档案馆 陕西省西安市长安区子午大道与学府大街十字东北角\n"
        )
    }
}