package com.relitu.factory;

import com.relitu.dao.PHIS_HOUSE_ATTRIBUTE_COUNT_Dao;
import com.relitu.dao.PHIS_HOUSE_KUANXUAN_Dao;
import com.relitu.dao.factory.inter.DaoFactoryInter;
/**
 * @author : PeiYangLiu
 * @version: 2.0
 * @date: 2017/7/13
 */
public class DaoFactory implements DaoFactoryInter {
	@Override
	public PHIS_HOUSE_ATTRIBUTE_COUNT_Dao createPHIS_HOUSE_ATTRIBUTE_COUNT_Dao(int num, String city, String user,
			String password, String sid) {
		return new PHIS_HOUSE_ATTRIBUTE_COUNT_Dao(num, city, user, password, sid);
	}
 
	@Override
	public PHIS_HOUSE_KUANXUAN_Dao createPHIS_HOUSE_KUANXUAN_Dao(int num, String city, String user, String password,
			String sid) {
		// TODO Auto-generated method stub
		return new PHIS_HOUSE_KUANXUAN_Dao(num, city, user, password, sid);
	}

}
