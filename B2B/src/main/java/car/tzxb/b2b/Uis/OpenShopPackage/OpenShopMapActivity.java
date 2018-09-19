package car.tzxb.b2b.Uis.OpenShopPackage;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;

import butterknife.BindView;
import butterknife.OnClick;
import car.tzxb.b2b.BasePackage.BasePresenter;
import car.tzxb.b2b.BasePackage.MyBaseAcitivity;
import car.tzxb.b2b.MyApp;
import car.tzxb.b2b.R;

public class OpenShopMapActivity extends MyBaseAcitivity implements OnGetGeoCoderResultListener,BaiduMap.OnMapClickListener,BDLocationListener{

    @BindView(R.id.open_shop_mapview)
    MapView mapView;
    @BindView(R.id.tv_actionbar_title)
    TextView tv_title;
    @BindView(R.id.tv_open_shop_latlog)
    TextView tv_ll;
  /*  private String address;
    private String city;
    private String from;*/
    private BaiduMap baiduMap;
    private GeoCoder mSearch;
    private String resultAddress;
    private LatLng latLng;

    public LocationClient mLocationClient = null;
    @Override
    public void initParms(Bundle parms) {

    /*    address = getIntent().getStringExtra("address");
        city = getIntent().getStringExtra("city");
        from = getIntent().getStringExtra("from");*/
    }

    @Override
    public int bindLayout() {
        //注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(MyApp.getContext());
        return R.layout.activity_open_shop_map;
    }

    @Override
    public void doBusiness(Context mContext) {
        tv_title.setText("标记地图");
        initMap();
        showMyLocation();
        mSearch = GeoCoder.newInstance();
        mSearch.setOnGetGeoCodeResultListener(this);
       //mSearch.geocode(new GeoCodeOption().city(city).address(address)); //调用此方法发起搜索
    }



    @Override
    protected BasePresenter bindPresenter() {
        return null;
    }

    private void initMap() {

        baiduMap = mapView.getMap();
        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(17);
        baiduMap.animateMapStatus(msu);
        // 不显示地图上比例尺
        mapView.showScaleControl(false);
        // 不显示地图缩放控件（按钮控制栏）
        mapView.showZoomControls(false);
         //点击监听
        baiduMap.setOnMapClickListener(this);
    }

    @OnClick(R.id.tv_actionbar_back)
    public void back() {
        onBackPressed();
    }

    private void showMyLocation() {
        mLocationClient = new LocationClient(this);
        mLocationClient.registerLocationListener(this);
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setCoorType("bd09ll");
        int span = 0;
        option.setScanSpan(span);
        option.setIsNeedAddress(true);
        option.setOpenGps(true);
        option.setLocationNotify(true);
        option.setIsNeedLocationDescribe(true);
        option.setIsNeedLocationPoiList(true);
        option.setIgnoreKillProcess(false);
        option.SetIgnoreCacheException(false);
        option.setEnableSimulateGps(false);
        mLocationClient.setLocOption(option);
        mLocationClient.start();
    }

    @Override
    public void onGetGeoCodeResult(GeoCodeResult result) {
     /*        //搜索城市,获取地理编码,即地址转坐标
        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            //没有检索到结果
            mSearch.geocode(new GeoCodeOption().city(city).address(city+"政府"));
            return;
        }
        latLng = result.getLocation();
        Log.i("精确找到了",result.getLocation().latitude+"________"+result.getLocation().longitude);

        //获取地理编码结果,开始定位
        LatLng location = new LatLng(result.getLocation().latitude, result.getLocation().longitude);
        MarkerOptions option = new MarkerOptions();
        option.position(location);
        option.icon(BitmapDescriptorFactory.fromResource(R.mipmap.register_icon_coordinate));
        baiduMap.addOverlay(option);
        MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(location);
        baiduMap.animateMapStatus(msu);
         //发起逆地理编码
        mSearch.reverseGeoCode(new ReverseGeoCodeOption().location(location));*/
    }

    @Override
    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
        //反地理编码,即坐标转地址
       //  resultAddress = reverseGeoCodeResult.getAddress()+reverseGeoCodeResult.getSematicDescription();
        resultAddress=reverseGeoCodeResult.getSematicDescription();
        tv_ll.setText(resultAddress);
    }



    @OnClick(R.id.btn_sumbit_open_shop_latlog)
    public void submit(){
        Intent intent=new Intent();
        Bundle bundle=new Bundle();
        bundle.putString("ResultAddress",resultAddress);
        bundle.putParcelable("latLng",latLng);
        intent.putExtra("map",bundle);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    protected void onResume() {
        mapView.setVisibility(View.VISIBLE);
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        mSearch.destroy();
    }

    @Override
    public void onPause() {
        mapView.setVisibility(View.INVISIBLE);
        mapView.onPause();
        super.onPause();
    }


    @Override
    public void onMapClick(LatLng ll) {
           //在此处理点击事件
        baiduMap.clear();
        latLng=ll;
        Log.i("点击地图的经纬度",ll.latitude+"______"+ll.longitude);
        //获取地理编码结果,开始定位
        MarkerOptions option = new MarkerOptions();
        option.position(ll);
        option.icon(BitmapDescriptorFactory.fromResource(R.mipmap.register_icon_coordinate));
        baiduMap.addOverlay(option);
        MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(ll);
        baiduMap.animateMapStatus(msu);
        //发起逆地理编码
        mSearch.reverseGeoCode(new ReverseGeoCodeOption().location(ll));
    }

    @Override
    public boolean onMapPoiClick(MapPoi mapPoi) {
        //在此处理底图标注点击事件
        return false;
    }


    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
        int type = bdLocation.getLocType();

        double lat = -1;
        double lng = -1;

        if (type == 61 || type == 65 || type == 66 || type == 161) {

            lat = bdLocation.getLatitude();
            lng = bdLocation.getLongitude();

        } else {
            //定位错误默认位置
            lng = 116.465037;
            lat = 39.876425;
        }
        LatLng location = new LatLng(lat, lng);
        //发起逆地理编码
        mSearch.reverseGeoCode(new ReverseGeoCodeOption().location(location));
        latLng=location;
        Log.i("定位位置",latLng.latitude+"____"+latLng.longitude);
        MarkerOptions option = new MarkerOptions();
        option.position(location);
        option.icon(BitmapDescriptorFactory.fromResource(R.mipmap.register_icon_coordinate));
        baiduMap.addOverlay(option);
        MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(location);
        baiduMap.animateMapStatus(msu);
        mLocationClient.stop();
        mLocationClient.unRegisterLocationListener(this);
        mLocationClient = null;
    }
}
