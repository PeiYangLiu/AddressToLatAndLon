package com.relitu.main;

import com.relitu.dao.factory.inter.DaoFactoryInter;
import com.relitu.factory.DaoFactory;

/**
 * @author : PeiYangLiu
 * @version: 1.0
 * @date: 2017/6/30
 */ 
public class Main {
	public static void main(String[] args) {
		DaoFactoryInter factory = new DaoFactory();
		factory.createPHIS_HOUSE_KUANXUAN_Dao(30000, "·ðÉ½ÊÐ", "gsms_416", "123456", "orcl").doChange();
	}
}
