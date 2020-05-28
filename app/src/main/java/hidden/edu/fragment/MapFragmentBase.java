package hidden.edu.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapException;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MultiPointItem;
import com.amap.api.maps.model.MultiPointOverlay;
import com.amap.api.maps.model.MultiPointOverlayOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.offlinemap.OfflineMapManager;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import hidden.edu.R;

public abstract class MapFragmentBase extends Fragment implements LocationSource, AMapLocationListener, PoiSearch.OnPoiSearchListener, CompoundButton.OnCheckedChangeListener, OfflineMapManager.OfflineMapDownloadListener {
    MapView mapView;
    AMap aMap;
    private PoiSearch mPoiSearch;

    protected static AppCompatActivity activity;

    /**
     * 定位监听
     */

    private OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;


    abstract LatLng getTarget();

    abstract CameraPosition getCameraPosition();

    abstract void setCameraPosition(CameraPosition cameraPosition);

    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        activity = (AppCompatActivity) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //设置布局

        return inflater.inflate(R.layout.map_fragment, container, false);
        // 在activity执行onCreate时执行mapView.onCreate(savedInstanceState)，实现地图生命周期管理
        //mapView.onCreate(savedInstanceState);
    }

    protected void init() throws AMapException {
        if (aMap == null) {
            aMap = mapView.getMap();
            // 设置定位监听
            aMap.setLocationSource(this);
            aMap.setMapType(AMap.MAP_TYPE_NORMAL);
            aMap.setMyLocationEnabled(true);


            aMap.setCustomMapStyle(
                    new com.amap.api.maps.model.CustomMapStyleOptions()
                            .setEnable(true)
                            .setStyleId("0aa1d1eda90911d20d3551642fcd4980")//官网控制台-自定义样式 获取
            );
            aMap.showIndoorMap(true);
            aMap.showBuildings(true);
            //aMap.showMapText();

            MyLocationStyle myLocationStyle = new MyLocationStyle();
            myLocationStyle.interval(2);
            myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_MAP_ROTATE);
            aMap.setMyLocationStyle(myLocationStyle);


            UiSettings uiSettings = mapView.getMap().getUiSettings();
            // 设置默认定位按钮是否显示
            uiSettings.setMyLocationButtonEnabled(true);

            uiSettings.setCompassEnabled(true);
            //aMap.addOnMapLoadedListener((AMap.OnMapLoadedListener) this);
            //构造OfflineMapManager对象
            OfflineMapManager amapManager = new OfflineMapManager(activity.getApplicationContext(), this);

            //按照cityname下载
            amapManager.downloadByCityName("西安市");
        }
    }

    //		mGPSModeGroup = (RadioGroup) findViewById(R.id.gps_radio_group);
//		mGPSModeGroup.setOnCheckedChangeListener(this);
//		mLocationErrText = (TextView)findViewById(R.id.location_errInfo_text);
//		mLocationErrText.setVisibility(View.GONE);
    //followed by onCreateView
    @Override
    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mapView = view.findViewById(R.id.map);

        try {
            init();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (mapView != null) {
            //保存状态
            mapView.onCreate(savedInstanceState);
            if (getCameraPosition() == null) {
                aMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(getTarget(), 10, 0, 0)));
            } else {
                aMap.moveCamera(CameraUpdateFactory.newCameraPosition(getCameraPosition()));
            }
        }

    }


    /**
     * 方法必须重写
     */
    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
        //deactivate();
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onSaveInstanceState(@NotNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
//		TrickerUtils.showToast(getActivity(), amapLocation.getAddress());
        if (mListener != null && amapLocation != null) {
            try {
//				mLocationErrText.setVisibility(View.GONE);
                mListener.onLocationChanged(amapLocation);// 显示系统小蓝点
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        initPoiSearch(aMap.getMyLocation().getLatitude(), aMap.getMyLocation().getLongitude());
    }

    private void initPoiSearch(double lat, double lon) {
        if (mPoiSearch == null) {
            PoiSearch.Query poiQuery = new PoiSearch.Query("", "", "陕西省");
            com.amap.api.services.core.LatLonPoint centerPoint = new LatLonPoint(lat, lon);
            poiQuery.setPageSize(10);// 设置每页最多返回多少条poiitem
            poiQuery.setPageNum(1);//设置查询页码


            PoiSearch.SearchBound searchBound;
            searchBound = new PoiSearch.SearchBound(centerPoint, 5000);
            mPoiSearch = new PoiSearch(activity.getApplicationContext(), poiQuery);
            mPoiSearch.setBound(searchBound);
            mPoiSearch.setOnPoiSearchListener(this);
            mPoiSearch.searchPOIAsyn();

            mPoiSearch.setOnPoiSearchListener(new PoiSearch.OnPoiSearchListener() {
                @Override
                public void onPoiSearched(PoiResult poiResult, int i) {
                    ArrayList<PoiItem> pois = poiResult.getPois();
                    List<MultiPointItem> list = new ArrayList<>();
                    for (PoiItem item : pois) {
                        LatLonPoint latLonPoint = item.getLatLonPoint();
                        list.add(new MultiPointItem(new LatLng(latLonPoint.getLatitude(), latLonPoint.getLongitude())));
                    }
                    //showResultOnMap(list);
                }

                @Override
                public void onPoiItemSearched(PoiItem poiItem, int i) {

                }
            });
        }
    }

    private void showResultOnMap(List<MultiPointItem> list) {
        MultiPointOverlayOptions overlayOptions = new MultiPointOverlayOptions();
        //overlayOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.p12));
        overlayOptions.anchor(0.5f, 0.5f);


        MultiPointOverlay multiPointOverlay = aMap.addMultiPointOverlay(overlayOptions);
        multiPointOverlay.setItems(list);

        aMap.setOnMultiPointClickListener(pointItem -> false);
    }

    @Override
    public void activate(OnLocationChangedListener listener) {
        mListener = listener;
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(getActivity());
            AMapLocationClientOption mLocationOption = new AMapLocationClientOption();
//			mLocationOption.setNeedAddress(true);
            //设置定位监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            mLocationOption.setInterval(2);

            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();
        }

    }

    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;

    }

    /**
     * 方法必须重写
     */
    @Override
    public void onDestroy() {
        setCameraPosition(aMap.getCameraPosition());
        super.onDestroy();
        mapView.onDestroy();
        if (null != mlocationClient) {
            mlocationClient.onDestroy();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onDownload(int i, int i1, String s) {

    }

    @Override
    public void onCheckUpdate(boolean b, String s) {

    }

    @Override
    public void onRemove(boolean b, String s, String s1) {

    }

    @Override
    public void onPoiSearched(PoiResult poiResult, int i) {

    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }
}
