package com.relitu.basic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.relitu.domain.PHIS_HOUSE_ATTRIBUTE_COUNT;

public abstract class BasicDao implements BasicDaoInter {
	public int num;
	public Connection con = null;// ����һ�����ݿ�����
	public PreparedStatement pre = null;// ����Ԥ����������һ�㶼�������������Statement
	public ResultSet result = null;// ����һ�����������
	public String city;
	//public String key = "kVz95Ivg9aZAwgNH2faaEitbcoTjZg49";b4OUeObBmhFvLFd2foTStEtvIRNuR8M8// ���ðٶȵ�ͼAPI�������key
	public String key = "kVz95Ivg9aZAwgNH2faaEitbcoTjZg49";
	public String Oracle_user;
	public String Oracle_password;
	public String Oracle_sid; 
}
