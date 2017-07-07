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

	private PHIS_HOUSE_ATTRIBUTE_COUNT[] list;// �洢���������
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
	public PHIS_HOUSE_ATTRIBUTE_COUNT_Dao(int num, String city, String user, String password, String sid) {
		this.num = num;
		this.city = city;
		this.Oracle_user = user;
		this.Oracle_password = password;
		this.Oracle_sid = sid;
		list = new PHIS_HOUSE_ATTRIBUTE_COUNT[num];
	}

	private PHIS_HOUSE_ATTRIBUTE_COUNT getPoint(PHIS_HOUSE_ATTRIBUTE_COUNT ad) {// ���ðٶȵ�ͼgeocoding����,���ֵ���ַת��Ϊ��γ��
		try {
			java.io.InputStream l_urlStream;
			// java.net.URL l_url = new
			// java.net.URL("http://api.map.baidu.com/geocoder/v2/?address=" +
			// ad.getHS_SIT()
			// +
			// "&output=json&ak=b4OUeObBmhFvLFd2foTStEtvIRNuR8M8&callback=showLocation");
			java.net.URL l_url = new java.net.URL("http://api.map.baidu.com/geocoder/v2/?ak=" + this.key
					+ "&callback=showLocation&output=json&address=" +this.city+"��"+ ad.getHS_SIT());// ����URL
			java.net.HttpURLConnection l_connection = (java.net.HttpURLConnection) l_url.openConnection();// ��URL
			l_connection.connect();// ����URL
			l_urlStream = l_connection.getInputStream();
			java.io.BufferedReader l_reader = new java.io.BufferedReader(new java.io.InputStreamReader(l_urlStream));
			String str = l_reader.readLine();
			//System.out.println(str);
			// �þ��ȷָ�ص���ҳ����
			if (str == null || str.contains("\"status\":1"))// ������
				return null;
			// �����ص��ַ������зָ�M,��ȡ��γ��
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

		} catch (Exception e) {// �����쳣���ؿ�
			// e.printStackTrace();
			return null;
		}
		return ad;
	}

	private void Connect() {// �������ݿ�
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

	private void Select() {
		try {
			System.out.println("���ӳɹ���");
			String sql = "select ID,HS_SIT from phis_house_attribute_count where lng is null and HS_SIT is not null and rownum<="
					+ num;// Ԥ�������
			pre = con.prepareStatement(sql);// ʵ����Ԥ�������
			result = pre.executeQuery();// ִ�в�ѯ��ע�������в���Ҫ�ټӲ���
			int i = 0;
			while (result.next()) {
				// ���������Ϊ��ʱ
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

	private void Save(PHIS_HOUSE_ATTRIBUTE_COUNT ad) {// ��������
		try {
			String sql = "update phis_house_attribute_count set LNG = '" + ad.getLng() + "',LAT = '" + ad.getLat()
					+ "' where ID = '" + ad.getID() + "'";
			System.out.println(ad.getID() + " " + ad.getLng());
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
