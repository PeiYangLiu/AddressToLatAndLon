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
	 *            : ��Ҫ���е�ַת������Ŀ����
	 * @param city
	 *            : ��Ҫ���е�ַת���ĳ���
	 * @param user
	 *            : ���ݿ��û���
	 * @param password
	 *            : ���ݿ�����
	 * @param sid
	 *            : Oracle���ݿ��SID
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
	 *            : ��Ҫͨ���ٶȵ�ͼAPI������PHIS_HOUSE_KUANXUAN����
	 * @return : ���Ӱٶȵ�ͼAPI��URL
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
	 *            : ��Ҫͨ���ٶȵ�ͼAPI������PHIS_HOUSE_KUANXUAN����
	 * @return : ���Ӱٶȵ�ͼAPI��URL
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
	 *            : ��Ҫͨ���ٶȵ�ͼAPI������PHIS_HOUSE_KUANXUAN����
	 * @param l_url
	 *            : ���Ӱٶȵ�ͼAPI��URL
	 * @return : �������PHIS_HOUSE_KUANXUAN����
	 * @throws Exception
	 */
	private PHIS_HOUSE_KUANXUAN getPoint(PHIS_HOUSE_KUANXUAN ad, URL l_url) throws Exception {// ���ðٶȵ�ͼgeocoding����,���ֵ���ַת��Ϊ��γ��
		InputStream l_urlStream;
		HttpURLConnection l_connection = (HttpURLConnection) l_url.openConnection();// ��URL
		l_connection.connect();// ����URL
		l_urlStream = l_connection.getInputStream();
		BufferedReader l_reader = new BufferedReader(new java.io.InputStreamReader(l_urlStream));
		String str = l_reader.readLine();
		return GetLocation.getLocationFromAction(str, ad);
	}

	/**
	 * �������ݿ�
	 */
	private void Connect() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");// ����Oracle��������
			System.out.println("��ʼ�����������ݿ⣡");
			String url = "jdbc:oracle:thin:@192.168.0.191:1521:" + this.Oracle_sid;// 127.0.0.1�Ǳ�����ַ
			String user = this.Oracle_user;// �û���,ϵͳĬ�ϵ��˻���
			String password = this.Oracle_password;// �㰲װʱѡ���õ�����
			con = DriverManager.getConnection(url, user, password);// ��ȡ����
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ��ѯ���ݿ�
	 */
	private void Select() {
		try {
			System.out.println("���ӳɹ���");
			String sql = "select HS_SIT,HS_NUM,HS_COCITY_CODE,HS_REGION_CODE,HS_SIT_STREET,HS_SIT_BUILDING from PHIS_HOUSE_KUANXUAN where lng is null and HS_SIT is not null and HS_COCITY_CODE = '"+this.cityid+"' and rownum<="
					+ num;
			pre = con.prepareStatement(sql);// ʵ����Ԥ�������
			result = pre.executeQuery();// ִ�в�ѯ��ע�������в���Ҫ�ټӲ���
			int i = 0;
			while (result.next()) {
				// ���������Ϊ��ʱ
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
	 *            : ��Ҫ�����PHIS_HOUSE_KUANXUAN����
	 */
	private void Save(PHIS_HOUSE_KUANXUAN ad) {
		try {
			String sql = "update PHIS_HOUSE_KUANXUAN set LNG = '" + ad.getLNG() + "',LAT = '" + ad.getLAT()
					+ "' where HS_NUM = '" + ad.getHS_NUM() + "' and HS_COCITY_CODE = '" + ad.getHS_COCITY_CODE() + "'";
			System.out.println(ad.getLNG());
			pre = con.prepareStatement(sql);// ʵ����Ԥ�������
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
	 * ִ�е�ַ����
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
