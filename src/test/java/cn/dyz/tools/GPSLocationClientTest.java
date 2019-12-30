package cn.dyz.tools;

import org.junit.Test;

import com.kuaishou.dp.geoTools.GPSLocationClient;

/**
 * Create by suzhiwu on 2019/12/7
 */
public class GPSLocationClientTest {

    @Test
    public void runCity() {
        GPSLocationClient client = new GPSLocationClient(true, false);
        //北京
        System.out.println(client.queryGPS(116.46, 39.92));
////        //上海
////        System.out.println(client.queryGPS(121.48, 31.22));
////        //重庆
////        System.out.println(client.queryGPS(106.54, 29.59));
////        //天津
////        System.out.println(client.queryGPS(117.20, 39.12));
////        //香港
////        System.out.println(client.queryGPS(114.151, 22.2822));
////        //澳门
////        System.out.println(client.queryGPS(113.55, 22.213));
////        //台湾
////        System.out.println(client.queryGPS(119.6287, 23.603));
    }

}
