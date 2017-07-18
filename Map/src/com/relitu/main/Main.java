package com.relitu.main;

import com.relitu.dao.factory.inter.DaoFactoryInter;
import com.relitu.factory.DaoFactory;

/**
 * @author : PeiYangLiu
 * @version: 2.0
 * @date: 2017/7/13
 */
public class Main {
	public static void s(){
	}
	public static void main(String[] args) {
		DaoFactoryInter factory = new DaoFactory();
		factory.createPHIS_HOUSE_KUANXUAN_Dao(270000, "ÔÆ¸¡ÊÐ", "gsms_416", "123456", "orcl","445300").doChange();
	}
}
