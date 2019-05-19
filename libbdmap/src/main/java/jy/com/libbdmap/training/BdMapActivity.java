package jy.com.libbdmap.training;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.baidu.location.*;
import com.baidu.mapapi.map.*;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.CityInfo;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;

import java.util.ArrayList;
import java.util.List;

import com.baidu.mapapi.search.route.*;
import jy.com.baidu.mapapi.overlayutil.PoiOverlay;
import jy.com.baidu.mapapi.overlayutil.WalkingRouteOverlay;
import jy.com.libbdmap.R;

/*
 * created by taofu on 2019/5/8
 **/
public class BdMapActivity extends AppCompatActivity implements SensorEventListener {

    private static final String TAG = "BdMapActivity";
    private MapView mMapView = null;

    private PoiSearch mPoiSearch;

    private RoutePlanSearch mRoutePlanSearch;

    private LocationClient mClient;

    private boolean isSacleMapView = false;

    private double mCurrentLat = 0.0;
    private double mCurrentLon = 0.0;
    private float mCurrentAccracy;

    private SensorManager mSensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bdmap);
        //获取地图控件引用
        mMapView = (MapView) findViewById(R.id.bmapView);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.menu_bd_map_type_satellite) {
            showSatelliteMap();
            //
        } else if (id == R.id.menu_bd_map_location) {
            location();
        } else if (id == R.id.menu_bd_map_mark) {
            mark2();
        } else if (id == R.id.menu_bd_map_poi) {

            poi(mMapView);

        }else if(id == R.id.menu_bd_map_route_plan){
                routePlan();
        }


        return super.onOptionsItemSelected(item);
    }

    private void location(){

        // 开启定位图层 一定不要少了这句，否则对在地图的设置、绘制定位点将无效，记得在 ondestroy 方法里面关闭
        mMapView.getMap().setMyLocationEnabled(true);
        //定位初始化
        mClient = new LocationClient(this);

        //通过LocationClientOption设置LocationClient相关参数
        LocationClientOption option = new LocationClientOption();
        /**
         * 默认高精度，设置定位模式
         * LocationMode.Hight_Accuracy 高精度定位模式：这种定位模式下，会同时使用
         网络定位（Wi-Fi和基站定位）和GPS定位，优先返回最高精度的定位结果；


         但是在室内gps无信号，只会返回网络定位结果；
         室外如果gps收不到信号，也只会返回网络定位结果。
         * LocationMode.Battery_Saving 低功耗定位模式：这种定位模式下，不会使用GPS，只会使用网络定位。
         * LocationMode.Device_Sensors 仅用设备定位模式：这种定位模式下，
         不需要连接网络，只使用GPS进行定位，这种模式下不支持室内环境的定位
         */
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);

        /**
         * 默认是true，设置是否使用gps定位
         * 如果设置为false，即使mOption.setLocationMode(LocationMode.Hight_Accuracy)也不会gps定位
         */
        option.setOpenGps(true); // 打开gps

        /**
         * 默认gcj02，设置返回的定位结果坐标系，如果配合百度地图使用，建议设置为bd09ll;
         * 目前国内主要有以下三种坐标系：
         1. wgs84：目前广泛使用的GPS全球卫星定位系统使用的标准坐标系；
         2. gcj02：经过国测局加密的坐标；
         3. bd09：为百度坐标系，其中bd09ll表示百度经纬度坐标，bd09mc表示百度墨卡托米制坐标；
         * 在国内获得的坐标系类型可以是：国测局坐标、百度墨卡托坐标 和 百度经纬度坐标。
         在海外地区，只能获得WGS84坐标。请在使用过程中注意选择坐标。
         */
        option.setCoorType("bd09ll"); // 设置坐标类型


        /**
         * 默认0，即仅定位一次；设置间隔需大于等于1000ms，表示周期性定位
         * 如果不在AndroidManifest.xml声明百度指定的Service，周期性请求无法正常工作
         * 这里需要注意的是：如果是室外gps定位，不用访问服务器，设置的间隔是3秒，那么就是3秒返回一次位置
         如果是WiFi基站定位，需要访问服务器，这个时候每次网络请求时间差异很大，设置的间隔是3秒，
         只能大概保证3秒左右会返回就一次位置，有时某次定位可能会5秒才返回
         */
        option.setScanSpan(1000);//周期性请求定位，1秒返回一次位置
        /**
         * 默认false，设置是否需要地址信息
         * 返回省、市、区、街道等地址信息，这个api用处很大，
         很多新闻类app会根据定位返回的市区信息推送用户所在市的新闻
         */
        option.setIsNeedAddress(true);

        /**
         * 默认false，设置是否需要位置语义化结果
         * 可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
         */
        option.setIsNeedLocationDescribe(true);

        /**
         * 默认false,设置是否需要设备方向传感器的方向结果
         * 一般在室外gps定位时，返回的位置信息是带有方向的，但是有时候gps返回的位置也不带方向，
         这个时候可以获取设备方向传感器的方向
         * wifi基站定位的位置信息是不带方向的，如果需要可以获取设备方向传感器的方向
         */
        option.setNeedDeviceDirect(false);

        /**
         * 默认false，设置是否当gps有效时按照设定的周期频率输出GPS结果
         * 室外gps有效时，周期性1秒返回一次位置信息，其实就是设置了
         locationManager.requestLocationUpdates中的minTime参数为1000ms，1秒回调一个gps位置
         * 如果设置了mOption.setScanSpan(3000)，那minTime就是3000ms了，3秒回调一个gps位置
         */
        option.setLocationNotify(false);

        /**
         * 默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
         * 如果你已经拿到了你要的位置信息，不需要再定位了，不杀死留着干嘛
         */
        option.setIgnoreKillProcess(true);


        /**
         * 默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
         * POI就是获取到的位置附近的一些商场、饭店、银行等信息
         */
        option.setIsNeedLocationPoiList(true);

        /**
         * 默认true，设置是否收集CRASH信息，默认收集
         */
        option.SetIgnoreCacheException(false);

        /**
         * 默认false，设置定位时是否需要海拔高度信息，默认不需要，除基础定位版本都可用
         */
        option.setIsNeedAltitude(false);

        //设置locationClientOption

        mClient.setLocOption(option);

        /**
         * MyLocationConfiguration 有两个构造函数
         * new MyLocationConfiguration(LocationMode lm, boolean direction, BitmapDescriptor bd);
         * new MyLocationConfiguration(LocationMode lm, boolean direction, BitmapDescriptor bd,int fillColor,int strokeColor);
         * 如果需要自定义定位图标显示样式，通过改api来实现。默认是 LocationMode.NORMAL 模式，显示的图标是小圆点
         *
         * 第一个参数：定位图标模式，有默认的，跟随，罗盘。
         * 第二个参数：是否显示方向
         * 第三个：自定义定位图标，如果不需要传一个 null
         * 第四个：精度圈填充的颜色
         * 第五个：精度圈边框的颜色
         *
         * 注意：只有当地图放到到21倍后精度圈才可以显示出来。
         * 定位精度圈大小 ，是根据当前定位精度自动控制的，无法手动控制大小。精度圈越小，代表当前定位精度越高；反之圈越大，代表当前定位精度越低。
         *
         * mapView.getMap().setMyLocationConfiguration(locationConfiguration);
         */
        MyLocationConfiguration locationConfiguration = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.FOLLOWING,
                true, BitmapDescriptorFactory.fromResource(R.drawable.icon_geo),Color.RED,Color.BLUE);
        //
       // MyLocationConfiguration locationConfiguration = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.FOLLOWING, true, null);
        mMapView.getMap().setMyLocationConfiguration(locationConfiguration);

       /* MapStatus.Builder builder = new MapStatus.Builder();
        builder.overlook(180);

        mMapView.getMap().animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));*/
        //
        //注册LocationListener监听器
        //注册LocationListener监听器
        mClient.registerLocationListener(new BDAbstractLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation location) {

                Log.d(TAG, "onReceiveLocation ---- ");
                if (location == null || mMapView == null) {
                    return;
                }
                //--------------------- 参数讲解-----------------
                /**
                 * location.getTime() 是指服务端出本次结果的时间，如果位置不发生变化，则时间不变
                 */

                location.getTime();

                /**
                 * 定位类型
                 BDLocation.TypeGpsLocation----gps定位
                 BDLocation.TypeNetWorkLocation----网络定位(wifi基站定位)
                 以及其他定位失败信息
                 */
                location.getLocType();

                /**
                 * 对应的定位类型说明
                 * 比如"NetWork location successful"之类的信息
                 */
                location.getLocTypeDescription();

                /**
                 * 纬度
                 */
                location.getLatitude();

                /**
                 * 经度
                 */
                location.getLongitude();

                /**
                 * 误差半径，代表你的真实位置在这个圆的覆盖范围内，
                 * 半径越小代表定位精度越高，位置越真实
                 * 在同一个地点，可能每次返回的经纬度都有微小的变化，
                 * 是因为返回的位置点并不是你真实的位置，有误差造成的。
                 */
                location.getRadius();
                location.getCountryCode();//国家码，null代表没有信息
                location.getCountry();//国家名称
                location.getCityCode();//城市编码
                location.getCity();//城市
                location.getDistrict();//区
                location.getStreet();//街道
                location.getAddrStr();//地址信息
                location.getLocationDescribe();//位置描述信息

                /**
                 * 判断用户是在室内，还是在室外
                 * 1：室内，0：室外，这个判断不一定是100%准确的
                 */
                location.getUserIndoorState();

                /**
                 * 获取方向
                 */
                location.getDirection();

                if (location.getPoiList() != null && !location.getPoiList().isEmpty()) {
                    for (int i = 0; i < location.getPoiList().size(); i++) {
                        Poi poi = (Poi) location.getPoiList().get(i);
                        Log.d(TAG, "location poi = " + poi.getName()) ;//获取位置附近的一些商场、饭店、银行等信息
                    }
                }

                if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS类型定位结果
                    location.getSpeed();//速度 单位：km/h，注意：网络定位结果是没有速度的
                    location.getSatelliteNumber();//卫星数目，gps定位成功最少需要4颗卫星
                    location.getAltitude();//海拔高度 单位：米
                    location.getGpsAccuracyStatus();//gps质量判断
                } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {//网络类型定位结果
                    if (location.hasAltitude()) {//如果有海拔高度
                        location.getAltitude();//单位：米
                    }
                    location.getOperators();//运营商信息
                } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                    //离线定位成功，离线定位结果也是有效的;
                } else if (location.getLocType() == BDLocation.TypeServerError) {
                    //服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com;
                } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                    //网络不通导致定位失败，请检查网络是否通畅;
                } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                    //无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机;
                }


                //--------------------- 参数讲解-----------------

                /**
                 * MyLocationData 定位数据类，地图上的定位位置需要经纬度、精度、方向这几个参数，所以这里我们把这个几个参数传给地图
                 * 如果不需要要精度圈，推荐builder.accuracy(0);
                 * mCurrentDirection 是通过手机方向传感器获取的方向；也可以设置option.setNeedDeviceDirect(true)获取location.getDirection()，
                 但是这不会时时更新位置的方向，因为周期性请求定位有时间间隔。
                 * location.getLatitude()和location.getLongitude()经纬度，如果你只需要在地图上显示某个固定的位置，完全可以写入固定的值，
                 如纬度36.958454，经度114.137588，这样就不要要同过定位sdk来获取位置了
                 */
                mCurrentLat = location.getLatitude();
                mCurrentLon = location.getLongitude();
                mCurrentAccracy = location.getRadius();
                MyLocationData locData = new MyLocationData.Builder()
                        // 定位精度，在室外，GPS 型号的好的情况下，精度就比较高。
                        // mCurrentAccracy 表示精度，值越大，精度越低，精度圈的半径就越大，反之精度越高，精度圈半径就越小
                        .accuracy(mCurrentAccracy)

                        // 此处设置开发者获取到的方向信息，顺时针0-360
                        .direction(location.getDirection()).latitude(mCurrentLat)
                        .longitude(mCurrentLon).build();
                mMapView.getMap().setMyLocationData(locData);

                if (isSacleMapView) {
                    isSacleMapView = false;

                    //定位位置回调

                    /* 当首次定位时，记得要放大地图，便于观察具体的位置
                     * LatLng是缩放的中心点，这里注意一定要和上面设置给地图的经纬度一致；
                     * MapStatus.Builder 地图状态构造器
                     **/
                    LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
                    MapStatus.Builder builder = new MapStatus.Builder();
                    //设置缩放中心点；缩放比例；
                    builder.target(ll).zoom(18.0f);
                    //给地图设置状态
                    mMapView.getMap().animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
                }
            }
        });
        //开启地图定位图层
        mClient.start();


    }

    private void routePlan(){

        mRoutePlanSearch = RoutePlanSearch.newInstance();

        mRoutePlanSearch.setOnGetRoutePlanResultListener(new OnGetRoutePlanResultListener() {
            @Override
            public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult) {
                //创建WalkingRouteOverlay实例
                WalkingRouteOverlay overlay = new WalkingRouteOverlay(mMapView.getMap());
                if (walkingRouteResult.getRouteLines().size() > 0) {
                    //获取路径规划数据,(以返回的第一条数据为例)
                    //为WalkingRouteOverlay实例设置路径数据
                    overlay.setData(walkingRouteResult.getRouteLines().get(0));
                    //在地图上绘制WalkingRouteOverlay
                    overlay.addToMap();
                }

            }

            @Override
            public void onGetTransitRouteResult(TransitRouteResult transitRouteResult) {

            }

            @Override
            public void onGetMassTransitRouteResult(MassTransitRouteResult massTransitRouteResult) {

            }

            @Override
            public void onGetDrivingRouteResult(DrivingRouteResult drivingRouteResult) {

            }

            @Override
            public void onGetIndoorRouteResult(IndoorRouteResult indoorRouteResult) {

            }

            @Override
            public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {

            }
        });


        PlanNode stNode = PlanNode.withCityNameAndPlaceName("北京", "西二旗地铁站");
        PlanNode enNode = PlanNode.withCityNameAndPlaceName("北京", "昌平地铁站");

       mRoutePlanSearch.walkingSearch((new WalkingRoutePlanOption())
                .from(stNode)
                .to(enNode));

    }

    private void poi(final MapView mapView) {
        //创建POI检索实例
        mPoiSearch = PoiSearch.newInstance();

        // 设置检索监听器
        mPoiSearch.setOnGetPoiSearchResultListener(new OnGetPoiSearchResultListener() {
            @Override
            public void onGetPoiResult(PoiResult result) {
                Log.d(TAG, "onGetPoiResult :");


                if (result == null || result.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
                    Toast.makeText(BdMapActivity.this, "未找到结果", Toast.LENGTH_LONG).show();
                    return;
                }

                if (result.error == SearchResult.ERRORNO.NO_ERROR) {
                    mapView.getMap().clear();

                    List<PoiInfo> poiInfos = result.getAllPoi();
                    if (poiInfos != null && poiInfos.size() > 0) {

                        for (PoiInfo poiInfn : poiInfos) {
                            Log.d(TAG, "PoiInfo :" + poiInfn.toString());
                            mark(poiInfn.location.latitude,poiInfn.location.longitude);
                        }

                    }

                  /*  //创建PoiOverlay对象
                    PoiOverlay poiOverlay = new PoiOverlay(mapView.getMap());

                    //设置Poi检索数据
                    poiOverlay.setData(result);

                    //将poiOverlay添加至地图并缩放至合适级别
                    poiOverlay.addToMap();
                    poiOverlay.zoomToSpan();*/


                    return;
                }

                if (result.error == SearchResult.ERRORNO.AMBIGUOUS_KEYWORD) {
                    // 当输入关键字在本市没有找到，但在其他城市找到时，返回包含该关键字信息的城市列表
                    String strInfo = "在";

                    for (CityInfo cityInfo : result.getSuggestCityList()) {
                        strInfo += cityInfo.city;
                        strInfo += ",";
                    }

                    strInfo += "找到结果";
                    Toast.makeText(BdMapActivity.this, strInfo, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {
                Log.d(TAG, "onGetPoiDetailResult :");
            }

            @Override
            public void onGetPoiDetailResult(PoiDetailSearchResult poiDetailSearchResult) {
                Log.d(TAG, "onGetPoiDetailResult :");
            }

            @Override
            public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {
                Log.d(TAG, "onGetPoiIndoorResult :");
            }
        });

        //设置PoiCitySearchOption，发起检索请求

        mPoiSearch.searchInCity(new PoiCitySearchOption()
                .city("北京") //必填
                .keyword("美食") //必填
                .pageNum(0)//分页编号，默认返回第0页结果
                .pageCapacity(10)// 设置每页容量，默认为10条结果
        );///

    }



    private void mark(double lat,double lng){
        //定义Maker坐标点
        LatLng point = new LatLng(lat, lng);
//构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.icon_marka);
//构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions().position(point).icon(bitmap);
//在地图上添加Marker，并显示
        mMapView.getMap().addOverlay(option);
    }


    private void mark1(){
        //定义Maker坐标点
        LatLng point = new LatLng(39.963175, 116.400244);
//构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.icon_marka);
//构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions().position(point).icon(bitmap);
        ((MarkerOptions) option).animateType(MarkerOptions.MarkerAnimateType.drop);
//在地图上添加Marker，并显示
        mMapView.getMap().addOverlay(option);
    }


    private void mark2(){

        //构建折线点坐标
        LatLng p1 = new LatLng(39.97923, 116.357428);
        LatLng p2 = new LatLng(39.94923, 116.397428);
        LatLng p3 = new LatLng(39.97923, 116.437428);
        List<LatLng> points = new ArrayList<LatLng>();
        points.add(p1);
        points.add(p2);
        points.add(p3);

//设置折线的属性
        OverlayOptions mOverlayOptions = new PolylineOptions()
                .width(10) // 线的宽度
                .color(0xAAFF0000) // 线的颜色
                .points(points);

//在地图上绘制折线
//mPloyline 折线对象
        Overlay mPolyline = mMapView.getMap().addOverlay(mOverlayOptions);
    }


    private void showSatelliteMap(){
        mMapView.getMap().setMapType(BaiduMap.MAP_TYPE_SATELLITE);
    }

    @Override
    protected void onResume() {
        super.onResume();

        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION), SensorManager.SENSOR_DELAY_UI);
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理

        mSensorManager.unregisterListener(this);
        mMapView.onPause();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理

        if(mClient != null){
            mClient.stop();
        }
        if(mPoiSearch != null){
            mPoiSearch.destroy();
            mPoiSearch = null;
        }
        if(mRoutePlanSearch != null){
            mRoutePlanSearch.destroy();
        }

        if(mMapView != null){
            mMapView.getMap().setMyLocationEnabled(false);
            mMapView.onDestroy();
            mMapView = null;
        }


    }
    private Double lastX = 0.0;
    @Override
    public void onSensorChanged(SensorEvent event) {

        double x = event.values[SensorManager.DATA_X];
        if (Math.abs(x - lastX) > 1.0) {
            int direction = (int) x;
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(mCurrentAccracy)
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(direction)
                    .latitude(mCurrentLat)
                    .longitude(mCurrentLon).build();
            mMapView.getMap().setMyLocationData(locData);
        }
        lastX = x;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
