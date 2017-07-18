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
	 *            : 需要进行地址转换的条目数量
	 * @param city
	 *            : 需要进行地址转换的城市
	 * @param user
	 *            : 数据库用户名
	 * @param password
	 *            : 数据库密码
	 * @param sid
	 *            : Oracle数据库的SID
	 * @return : PHIS_HOUSE_KUANXUAN_Dao对象
	 */
	public PHIS_HOUSE_KUANXUAN_Dao createPHIS_HOUSE_KUANXUAN_Dao(int num, String city, String user, String password,
			String sid, String cityid);
}
