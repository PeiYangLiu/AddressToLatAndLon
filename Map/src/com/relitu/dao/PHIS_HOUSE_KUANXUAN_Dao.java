package com.relitu.dao;

import java.io.BufferedReader;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.relitu.basic.BasicDao;
import com.relitu.dao.inter.PHIS_HOUSE_KUANXUAN_Inter;
import com.relitu.domain.PHIS_HOUSE_KUANXUAN;
import com.relitu.tools.GetLocation;

/**
 * @author : PeiYangLiu
 * @version: 2.0
 * @date: 2017/7/13
 */
public class PHIS_HOUSE_KUANXUAN_Dao extends BasicDao implements PHIS_HOUSE_KUANXUAN_Inter {
	private PHIS_HOUSE_KUANXUAN[] list;

	/**
	 * 
	 * @param num
	 *            : 需要进行地址转换的条目数量
	 * @param city
	 *            : 需要进行地址转换的城市
	 * @param user
	 *            : 数据库用户名
	 * @param password
	 *            : 数据库密码
	 * @param sid
	 *            : Oracle数据库的SID
	 */
	public PHIS_HOUSE_KUANXUAN_Dao(int num, String city, String user, String password, String sid, String cityid) {
		this.num = num;
		this.city = city;
		this.Oracle_user = user;
		this.Oracle_password = password;
		this.Oracle_sid = sid;
		this.list = new PHIS_HOUSE_KUANXUAN[num];
		this.cityid = cityid;
	}

	/**
	 * 
	 * @param ad
	 *            : 需要通过百度地图API解析的PHIS_HOUSE_KUANXUAN对象
	 * @return : 连接百度地图API的URL
	 * @throws Exception
	 */
	private URL getURLByHS_SIT(PHIS_HOUSE_KUANXUAN ad) throws Exception {
		URL l_url = new URL(
				"http://api.map.baidu.com/geocoder/v2/?address=" + URLEncoder.encode(ad.getHS_SIT(), "UTF-8")
						+ "&output=json&ak=" + this.key + "&callback=showLocation");
		return l_url;
	}

	/**
	 * 
	 * @param ad
	 *            : 需要通过百度地图API解析的PHIS_HOUSE_KUANXUAN对象
	 * @return : 连接百度地图API的URL
	 * @throws Exception
	 */
	private URL getURLByStreet(PHIS_HOUSE_KUANXUAN ad) throws Exception {
		String url = "http://api.map.baidu.com/geocoder/v2/?address="
				+ URLEncoder.encode(this.city + ad.getHS_REGION_CODE() + ad.getHS_SIT_STREET(), "UTF-8")
				+ "&output=json&ak=" + this.key + "&callback=showLocation";
		return new URL(url);
	}

	/**
	 * 
	 * @param ad
	 *            : 需要通过百度地图API解析的PHIS_HOUSE_KUANXUAN对象
	 * @param l_url
	 *            : 连接百度地图API的URL
	 * @return : 解析后的PHIS_HOUSE_KUANXUAN对象
	 * @throws Exception
	 */
	private PHIS_HOUSE_KUANXUAN getPoint(PHIS_HOUSE_KUANXUAN ad, URL l_url) throws Exception {// 调用百度地图geocoding服务,将街道地址转换为经纬度
		InputStream l_urlStream;
		HttpURLConnection l_connection = (HttpURLConnection) l_url.openConnection();// 打开URL
		l_connection.connect();// 连接URL
		l_urlStream = l_connection.getInputStream();
		BufferedReader l_reader = new BufferedReader(new java.io.InputStreamReader(l_urlStream));
		String str = l_reader.readLine();
		return GetLocation.getLocationFromAction(str, ad);
	}

	/**
	 * 连接数据库
	 */
	private void Connect() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");// 加载Oracle驱动程序
			System.out.println("开始尝试连接数据库！");
			String url = "jdbc:oracle:thin:@192.168.0.191:1521:" + this.Oracle_sid;// 127.0.0.1是本机地址
			String user = this.Oracle_user;// 用户名,系统默认的账户名
			String password = this.Oracle_password;// 你安装时选设置的密码
			con = DriverManager.getConnection(url, user, password);// 获取连接
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 查询数据库
	 */
	private void Select() {
		try {
			System.out.println("连接成功！");
			String sql = "select HS_SIT,HS_NUM,HS_COCITY_CODE,HS_REGION_CODE,HS_SIT_STREET,HS_SIT_BUILDING from PHIS_HOUSE_KUANXUAN where lng is null and HS_SIT is not null and HS_COCITY_CODE = '"+this.cityid+"' and rownum<="
					+ num;
			pre = con.prepareStatement(sql);// 实例化预编译语句
			result = pre.executeQuery();// 执行查询，注意括号中不需要再加参数
			int i = 0;
			while (result.next()) {
				// 当结果集不为空时
				PHIS_HOUSE_KUANXUAN temp = new PHIS_HOUSE_KUANXUAN();
				temp.setHS_NUM(result.getString("HS_NUM"));
				temp.setHS_SIT(result.getString("HS_SIT"));
				temp.setHS_COCITY_CODE(result.getString("HS_COCITY_CODE"));
				temp.setHS_REGION_CODE(result.getString("HS_REGION_CODE"));
				temp.setHS_SIT_STREET(result.getString("HS_SIT_STREET"));
				temp.setHS_SIT_BUILDING(result.getString("HS_SIT_BUILDING"));
				list[i] = temp;
				i++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pre.close();
				result.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 
	 * @param ad
	 *            : 需要保存的PHIS_HOUSE_KUANXUAN对象
	 */
	private void Save(PHIS_HOUSE_KUANXUAN ad) {
		try {
			String sql = "update PHIS_HOUSE_KUANXUAN set LNG = '" + ad.getLNG() + "',LAT = '" + ad.getLAT()
					+ "' where HS_NUM = '" + ad.getHS_NUM() + "' and HS_COCITY_CODE = '" + ad.getHS_COCITY_CODE() + "'";
			System.out.println(ad.getLNG());
			pre = con.prepareStatement(sql);// 实例化预编译语句
			pre.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pre.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 执行地址解析
	 */
	public void doChange() {
		this.Connect();
		this.Select();
		for (int i = 0; i < list.length; i++) {
			PHIS_HOUSE_KUANXUAN temp = null;
			try {
				temp = this.getPoint(list[i], getURLByHS_SIT(list[i]));
				if (temp == null)
					temp = this.getPoint(list[i], getURLByStreet(list[i]));
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (temp == null || temp.getLAT().equals("null"))
				continue;
			this.Save(temp);
		}
	}
}
