package com.relitu.main;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import com.relitu.dao.factory.inter.DaoFactoryInter;
import com.relitu.factory.DaoFactory;

/**
 * @author : PeiYangLiu
 * @version: 1.0
 * @date: 2017/6/30
 */
public class Main {
	public static void main(String[] args) {
		// new PHIS_HOUSE_ATTRIBUTE_COUNT_Dao(10, "…Ó€⁄", "gsms_416", "123456",
		// "orcl").doChange();
		DaoFactoryInter factory = new DaoFactory();
		// factory.createPHIS_HOUSE_ATTRIBUTE_COUNT_Dao(100, "…Ó€⁄", "gsms_416",
		// "123456", "orcl").doChange();
		factory.createPHIS_HOUSE_KUANXUAN_Dao(100, "", "gsms_416", "123456", "orcl").doChange();
	}
}
