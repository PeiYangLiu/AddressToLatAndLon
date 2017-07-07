package com.relitu.dao.factory.inter;

import com.relitu.dao.PHIS_HOUSE_ATTRIBUTE_COUNT_Dao;
import com.relitu.dao.PHIS_HOUSE_KUANXUAN_Dao;

public interface DaoFactoryInter {
     public PHIS_HOUSE_ATTRIBUTE_COUNT_Dao createPHIS_HOUSE_ATTRIBUTE_COUNT_Dao(int num, String city, String user, String password, String sid);
     public PHIS_HOUSE_KUANXUAN_Dao createPHIS_HOUSE_KUANXUAN_Dao(int num, String city, String user, String password, String sid);
}
