package com.relitu.basic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.relitu.domain.PHIS_HOUSE_ATTRIBUTE_COUNT;
/**
 * @author : PeiYangLiu
 * @version: 2.0
 * @date: 2017/7/13
 */
public abstract class BasicDao implements BasicDaoInter {
	public int num;
	public Connection con = null;// 创建一个数据库连接
	public PreparedStatement pre = null;// 创建预编译语句对象，一般都是用这个而不用Statement
	public ResultSet result = null;// 创建一个结果集对象
	public String city;
	//public String key = "kVz95Ivg9aZAwgNH2faaEitbcoTjZg49";b4OUeObBmhFvLFd2foTStEtvIRNuR8M8// 调用百度地图API必须得有key
	public String key = "b4OUeObBmhFvLFd2foTStEtvIRNuR8M8";
	public String Oracle_user;
	public String Oracle_password;
	public String Oracle_sid;  
	public String cityid;
}
