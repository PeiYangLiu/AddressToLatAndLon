package com.relitu.dao;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.relitu.basic.BasicDao;
import com.relitu.dao.inter.PHIS_HOUSE_ATTRIBUTE_COUNT_Inter;
import com.relitu.domain.PHIS_HOUSE_ATTRIBUTE_COUNT;

/**
 * @author : PeiYangLiu
 * @version: 1.0
 * @date: 2017/6/30
 */
public class PHIS_HOUSE_ATTRIBUTE_COUNT_Dao extends BasicDao implements PHIS_HOUSE_ATTRIBUTE_COUNT_Inter {

	private PHIS_HOUSE_ATTRIBUTE_COUNT[] list;// 存储读入的数据
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
	public PHIS_HOUSE_ATTRIBUTE_COUNT_Dao(int num, String city, String user, String password, String sid) {
		this.num = num;
		this.city = city;
		this.Oracle_user = user;
		this.Oracle_password = password;
		this.Oracle_sid = sid;
		list = new PHIS_HOUSE_ATTRIBUTE_COUNT[num];
	}

	private PHIS_HOUSE_ATTRIBUTE_COUNT getPoint(PHIS_HOUSE_ATTRIBUTE_COUNT ad) {// 调用百度地图geocoding服务,将街道地址转换为经纬度
		try {
			java.io.InputStream l_urlStream;
			// java.net.URL l_url = new
			// java.net.URL("http://api.map.baidu.com/geocoder/v2/?address=" +
			// ad.getHS_SIT()
			// +
			// "&output=json&ak=b4OUeObBmhFvLFd2foTStEtvIRNuR8M8&callback=showLocation");
			java.net.URL l_url = new java.net.URL("http://api.map.baidu.com/geocoder/v2/?ak=" + this.key
					+ "&callback=showLocation&output=json&address=" +this.city+"市"+ ad.getHS_SIT());// 创建URL
			java.net.HttpURLConnection l_connection = (java.net.HttpURLConnection) l_url.openConnection();// 打开URL
			l_connection.connect();// 连接URL
			l_urlStream = l_connection.getInputStream();
			java.io.BufferedReader l_reader = new java.io.BufferedReader(new java.io.InputStreamReader(l_urlStream));
			String str = l_reader.readLine();
			//System.out.println(str);
			// 用经度分割返回的网页代码
			if (str == null || str.contains("\"status\":1"))// 出错返回
				return null;
			// 将返回的字符串进行分割M,提取经纬度
			String s = "," + "\"" + "lat" + "\"" + ":";
			String strs[] = str.split(s, 2);
			String s1 = "\"" + "lng" + "\"" + ":";
			String a[] = strs[0].split(s1, 2);
			ad.setLng(a[1]);
			// System.out.println(a[1]);//lng
			s1 = "}" + "," + "\"";
			String a1[] = strs[1].split(s1, 2);
			ad.setLat(a1[0]);
			// System.out.println(a1[0]);//lat

		} catch (Exception e) {// 出现异常返回空
			// e.printStackTrace();
			return null;
		}
		return ad;
	}

	private void Connect() {// 连接数据库
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

	private void Select() {
		try {
			System.out.println("连接成功！");
			String sql = "select ID,HS_SIT from phis_house_attribute_count where lng is null and HS_SIT is not null and rownum<="
					+ num;// 预编译语句
			pre = con.prepareStatement(sql);// 实例化预编译语句
			result = pre.executeQuery();// 执行查询，注意括号中不需要再加参数
			int i = 0;
			while (result.next()) {
				// 当结果集不为空时
				// System.out.println(result.getString("HS_SIT"));
				PHIS_HOUSE_ATTRIBUTE_COUNT temp = new PHIS_HOUSE_ATTRIBUTE_COUNT();
				temp.setID(result.getString("ID"));
				temp.setHS_SIT(result.getString("HS_SIT"));
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

	private void Save(PHIS_HOUSE_ATTRIBUTE_COUNT ad) {// 保存数据
		try {
			String sql = "update phis_house_attribute_count set LNG = '" + ad.getLng() + "',LAT = '" + ad.getLat()
					+ "' where ID = '" + ad.getID() + "'";
			System.out.println(ad.getID() + " " + ad.getLng());
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

	public void doChange() {
		this.Connect();
		this.Select();
		for (int i = 0; i < list.length; i++) {
			PHIS_HOUSE_ATTRIBUTE_COUNT temp = this.getPoint(list[i]);
			if (temp == null || temp.getLat().equals("null"))
				continue;
			this.Save(temp);
		}
	}
}
