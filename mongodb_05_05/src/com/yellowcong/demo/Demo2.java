package com.yellowcong.demo;

import java.net.UnknownHostException;
import java.util.Date;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

/**
 * 表单 的增删改查
 * @author 狂飙のyellowcong 2015年5月5日
 *
 */
public class Demo2 {
	
	public static void main(String[] args) throws Exception {
		
		Mongo mongo = new Mongo("127.0.0.1:1000");
		DB db = mongo.getDB("hhxy");
		if(!db.collectionExists("person")){
			System.out.println("不存在Perosn 集合");
		};
		
		//创建Object 对象
		DBObject dbObject = new BasicDBObject();
		dbObject.put("username", "yellowcong");
		dbObject.put("age",18);
		dbObject.put("create_date", new Date());
		
		//添加集合
		//当集合如果存在了，在创建就会有问题
		//而且由于mongodb中存在这，如果没有数据就会删除表单
		if(db.collectionExists("xxxx")){
			db.createCollection("xxxx",dbObject);
		}{
			DBCollection table = db.getCollection("xxxx");
			//删除 集合的方法
			table.drop();
		}
		
		
		
	}
	

}
