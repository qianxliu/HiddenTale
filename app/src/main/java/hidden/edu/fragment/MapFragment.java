package hidden.edu.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapException;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.Poi;
import com.amap.api.navi.AmapNaviPage;
import com.amap.api.navi.AmapNaviParams;
import com.amap.api.navi.AmapNaviType;
import com.amap.api.navi.INaviInfoCallback;
import com.amap.api.navi.model.NaviLatLng;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import hidden.edu.R;
import hidden.edu.activity.NaviActivity;
import hidden.edu.activity.PdfViewActivity;
import zuo.biao.library.ui.AlertDialog;

import static hidden.edu.fragment.MyRecyclerFragment.URLS;
import static hidden.edu.util.TestUtil.getUserList;
import static zuo.biao.library.util.CommonUtil.showShortToast;
import static zuo.biao.library.util.CommonUtil.toActivity;
import static zuo.biao.library.util.DataKeeper.TYPE_FILE_SHOT;
import static zuo.biao.library.util.DataKeeper.storeFile;

public class MapFragment extends MapFragmentBase implements AlertDialog.OnDialogButtonClickListener {
    //西安市经纬度
    private static final LatLng XIAN = new LatLng(34.343147, 108.939621);
    private static CameraPosition cameraPosition = new CameraPosition.Builder()
            .target(XIAN).zoom(18).bearing(0).tilt(30).build();
    public static float temp;
    private NaviLatLng naviLatLng;
    private INaviInfoCallback naviInfoCallback;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    LatLng getTarget() {
        return XIAN;
    }

    @Override
    CameraPosition getCameraPosition() {
        return cameraPosition;
    }

    @Override
    void setCameraPosition(CameraPosition cameraPosition) {
        MapFragment.cameraPosition = cameraPosition;
    }

    private static LatLng[] latLng = {
            new LatLng(34.057441, 108.312201),
            new LatLng(34.235222, 108.931813),
            new LatLng(34.192109, 108.896464),
            new LatLng(34.235222, 108.931813),
            new LatLng(34.086456, 108.519344),
            new LatLng(34.235222, 108.931813),
            new LatLng(34.235222, 108.931813),
            new LatLng(34.234321, 108.949763),
            new LatLng(34.23777, 108.766144),
            new LatLng(34.4763, 109.364455),
            //11
            new LatLng(34.273603, 108.954741),
            new LatLng(34.257313, 108.967721),
            new LatLng(34.222472, 109.024829),
            new LatLng(33.910533, 109.50539),
            new LatLng(34.128889, 109.228452),
            //16
            new LatLng(34.248333, 108.927083),
            new LatLng(34.246207, 108.98378),

    };
    private static String[] BRIEFS = {
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
    };

    private void icon(Marker marker, int len) {
        switch ((int) (Math.random() * len % len)) {
            case 0:
                marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.p1));
                break;
            case 1:
                marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.p2));
                break;
            case 2:
                marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.p3));
                break;
            case 3:
                marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.p4));
                break;
            case 4:
                marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.p5));
                break;
            case 5:
                marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.p6));
                break;
            case 6:
                marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.p7));
                break;
            case 7:
                marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.p8));
                break;
            case 8:
                marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.p9));
                break;
            case 9:
                marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.p10));
                break;
            case 10:
                marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.p11));
                break;
            case 11:
                marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.p12));
                break;
        }
    }

    @Override
    protected void init() throws AMapException {
        super.init();
        aMap.setOnMapClickListener(v ->
                {
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(new LatLng(v.latitude, v.longitude)).setFlat(false).zIndex(-2);
                    markerOptions.draggable(true);
                    markerOptions.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                            .decodeResource(getResources(), R.drawable.icon_end)));
                    aMap.addMarker(markerOptions);
                }
        );
        aMap.setOnMarkerDragListener(new AMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {

            }

            @Override
            public void onMarkerDrag(Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                if (marker.getZIndex() == -2)
                    marker.remove();
            }
        });
        //生成标记点
        for (int i = 1; i < latLng.length; i++) {
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng[i]).title(getUserList().get(i).getName()).snippet("坐标" + latLng[i].toString() + "\n" + BRIEFS[i] + "点击查看详细信息");
            if (Math.random() * 2 % 2 == 0)
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.p11));
            else
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.p12));
            markerOptions.zIndex(i);
            aMap.addMarker(markerOptions);
        }

        // 定义 Marker 点击事件监听
        // marker 对象被点击时回调的接口
        // 返回 true 则表示接口已响应事件，否则返回false
        AMap.OnMarkerClickListener markerClickListener = marker -> {
            if (marker.isInfoWindowShown() && !marker.isFlat() && marker.getZIndex() != -2) {
                marker.hideInfoWindow();
                if (Math.random() * 2 % 2 == 0)
                    marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.p12));
                else
                    marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.p11));
                caculate();
            } else if (!marker.isInfoWindowShown() && !marker.isFlat() && marker.getZIndex() != -2) {
                marker.showInfoWindow();
                icon(marker, (int) (Math.random() * 10 % 10));
                caculate();
            } else if (marker.getZIndex() == -2) {
                new AlertDialog(activity, "导航", "导航到这里吗?", true, 0, this).show();
                naviLatLng = new NaviLatLng(marker.getPosition().latitude, marker.getPosition().longitude);
            }
            return true;
        };
        AMap.OnInfoWindowClickListener listener = marker -> {
            if (marker.getZIndex() < latLng.length - 1) {
                toActivity(activity, PdfViewActivity.createIntent(activity, URLS[(int) marker.getZIndex()], String.valueOf((int) marker.getZIndex() + 1)));
                caculate();
            } else if (marker.getZIndex() < latLng.length + 1) {
                toActivity(activity, PdfViewActivity.createIntent(activity, URLS[(int) marker.getZIndex()], String.valueOf((int) marker.getZIndex() + 1)));
                showShortToast(activity, marker.getTitle() + "期待你的补充!");
                caculate();
            }
        };
        //绑定信息窗点击事件
        aMap.setOnInfoWindowClickListener(listener);
        // 绑定 Marker 被点击事件
        aMap.setOnMarkerClickListener(markerClickListener);

        isPosition();
    }

    //Dialog choose positive
    @Override
    public void onDialogButtonClick(int requestCode, boolean isPositive) {
        if (isPositive) {
            NaviActivity.mEndLatlng = naviLatLng;
            AmapNaviParams naviParams = new AmapNaviParams(new Poi("", new LatLng(aMap.getMyLocation().getLatitude(), aMap.getMyLocation().getLongitude()), ""), null, new Poi("目的地", new LatLng(naviLatLng.getLatitude(), naviLatLng.getLongitude()), ""), AmapNaviType.DRIVER);
            AmapNaviPage.getInstance().showRouteActivity(activity.getApplicationContext(), naviParams, naviInfoCallback);
        }
    }

    private void isPosition() {
        if (AMapUtils.calculateLineDistance(new LatLng(aMap.getMyLocation().getLatitude(), aMap.getMyLocation().getLongitude()), XIAN) < 100000) {
            showShortToast(activity, "检测到当前位置不在西安市附近");
            aMap.setMyLocationStyle(new MyLocationStyle().myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER));
        }
        setCameraPosition(getCameraPosition());
    }


    public void caculate() {
        temp = 100000;
        LatLng o;
        if (aMap.getMyLocation() != null) {
            o = new LatLng(aMap.getMyLocation().getLatitude(), aMap.getMyLocation().getLongitude());
            for (int i = 0; i < latLng.length; i++) {
                float distance = AMapUtils.calculateLineDistance(o, latLng[i]);
                if (distance < 100000) {
                    if (distance < temp) {
                        temp = distance;
                    }
                    showShortToast(activity, "附近宝藏出没!为" + getUserList().get(i).getTime() + "的" + getUserList().get(i).getOral() + "制" + getUserList().get(i).getName() + "!" + "距离" + distance + "米");
                }
            }
        } else {
            temp = 0;
        }
    }

    public void onShot() {
        /*
         * 对地图进行截屏
         */
        aMap.getMapScreenShot(new AMap.OnMapScreenShotListener() {
            @Override
            public void onMapScreenShot(Bitmap bitmap) {

            }

            @Override
            public void onMapScreenShot(Bitmap bitmap, int status) {
                if (null == bitmap) {
                    return;
                }
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                boolean b = bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);

                storeFile(baos.toByteArray(), ".png", TYPE_FILE_SHOT);
                try {
                    baos.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    baos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                StringBuilder buffer = new StringBuilder();
                if (b)
                    buffer.append("截屏成功 ");
                else {
                    buffer.append("截屏失败 ");
                }
                if (status != 0)
                    buffer.append("地图渲染完成，截屏无网格");
                else {
                    buffer.append("地图未渲染完成，截屏有网格");
                }
                showShortToast(activity, buffer.toString());
            }
        });
    }

    @Override
    public void onPoiSearched(PoiResult poiResult, int i) {

    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }
}


