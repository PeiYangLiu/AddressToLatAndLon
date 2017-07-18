package com.relitu.dao.factory.inter;

import com.relitu.dao.PHIS_HOUSE_ATTRIBUTE_COUNT_Dao;
import com.relitu.dao.PHIS_HOUSE_KUANXUAN_Dao;

/**
 * @author : PeiYangLiu
 * @version: 2.0
 * @date: 2017/7/13
 */
public interface DaoFactoryInter {
	public PHIS_HOUSE_ATTRIBUTE_COUNT_Dao createPHIS_HOUSE_ATTRIBUTE_COUNT_Dao(int num, String city, String user,
			String password, String sid);

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
	 * @return : PHIS_HOUSE_KUANXUAN_Dao����
	 */
	public PHIS_HOUSE_KUANXUAN_Dao createPHIS_HOUSE_KUANXUAN_Dao(int num, String city, String user, String password,
			String sid, String cityid);
}
