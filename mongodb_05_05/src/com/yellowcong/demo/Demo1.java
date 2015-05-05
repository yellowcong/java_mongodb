package com.yellowcong.demo;

import java.util.List;
import java.util.Set;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.util.JSON;

public class Demo1 {

	public static void main(String[] args) throws Exception {
		//创建mongo的连接
		Mongo mongo = new Mongo("127.0.0.1:1000");
		//获取所有数据库名称
		List<String> dbNames = mongo.getDatabaseNames();
		
		//将所有的数据名打印出来
		for(String name:dbNames){
			System.out.println(name);
		}
		
		System.out.println("\r\r");
		//获取指定的数据库
		DB db = mongo.getDB("hhxy");
		//获取所有集合的名称
		System.out.println("获取所有集合数据");
		Set<String> tableNames = db.getCollectionNames();
		for(String tableName :tableNames){
			System.out.println(tableName);
		}
		
		//获取集合中的数据
		DBCollection users = db.getCollection("user");
		//记录条数
		System.out.println(users.getCount());
		
		//获取查询的所有数据
		DBCursor cursor = users.find();
		//获取所有的数据
		while(cursor.hasNext()){
			DBObject object = cursor.next();
			System.out.println(object);
		}
		
		System.out.println("\r\rJSON序列化");
		String date = JSON.serialize(cursor);
		System.out.println(date);
	}
}
