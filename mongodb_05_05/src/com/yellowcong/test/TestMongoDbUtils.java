package com.yellowcong.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.bson.types.ObjectId;
import org.junit.Test;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;
import com.yellowcong.utils.MongoDBUtils;

public class TestMongoDbUtils {
	
	@Test
	public void testLoad(){
		DB db = MongoDBUtils.getDb();
		Set<String> names = db.getCollectionNames();
		for(String name:names){
			System.out.println(name);
		}
	}
	
	@Test
	public void testGetDB(){
		DB db = MongoDBUtils.getDb();
//		BasicDBObject对象
		
		DBObject object = new BasicDBObject();
		object.put("yellowcong", "xxx");
		//创建集合
		db.createCollection("xxx", object);
	}
	
	@Test
	public void testAddColl(){
		MongoDBUtils.createCollection("book");
	}
	
	@Test
	public void testDeletColl(){
		MongoDBUtils.dropCollection("book");
	}
	
	
	@Test
	public void testInsert(){
		DB db = MongoDBUtils.getDb();
		
		DBCollection table  = db.getCollection("person");
		DBObject object  = new  BasicDBObject();
		object.put("username", "yellowcong");
		object.put("age", 20);
		object.put("crate_date", new Date());
		
		WriteResult result = table.insert(object);
		System.out.println(result);
	}
	
	@Test
	public void testInsertObject(){
		
		DBObject object  = new  BasicDBObject();
		object.put("username", "yellowcong2");
		object.put("age", 22);
		object.put("email", "7173350389@qq.com");
		object.put("crate_date", new Date());
		
		MongoDBUtils.insert("users", object);
	}
	
	@Test
	public void testInserts(){
		DBObject user1 = new BasicDBObject("name", "zhang3feng");
		DBObject user2 = new BasicDBObject("name", "xiaohuang");
		DBObject user3 = new BasicDBObject("name", "yellowcong");
		List<DBObject> users = new ArrayList<DBObject>();
		users.add(user1);
		users.add(user2);
		users.add(user3);
		MongoDBUtils.insert("users", users);
		
		
	
	}
	
	@Test
	public void testDelete(){
		DBCollection coll = MongoDBUtils.getCollection("users");
		//通过id删除数据
		String id = "55487561c02ee9ab84cec371";
		//通过id删除数据
		coll.remove(new BasicDBObject("_id", new ObjectId(id)));
	}
	
	@Test
	public void testDeleteById(){
		MongoDBUtils.deleteById("users", "55487330c02e2659a30671dc");
	}
	
	@Test
	public void testDeleteObject(){
		MongoDBUtils.delete("users", new BasicDBObject("name", "xiaohuang"));
	}
	
	//测试数据查询
	@Test
	public void testFind(){
		DB db = MongoDBUtils.getDb();
		DBCollection coll = db.getCollection("users");
		DBCursor users = coll.find();
		
		while(users.hasNext()){
			System.out.println(users.next());
		}
	}
	
	@Test
	public void testUpdate(){
		DBCollection coll = MongoDBUtils.getCollection("users");
		DBCursor users = coll.find(new BasicDBObject("name", "yellowcong"));
		
		DBObject obj = new BasicDBObject();
		obj.put("$set", new BasicDBObject("email", "717350389@qq.com"));
		//更新的第一个参数 是查询函数 
		coll.update(new BasicDBObject(), obj,false,true);
		
	}
	
	@Test
	public void testFindAll(){
		DBCursor cursor = MongoDBUtils.findAll("users");
		while(cursor.hasNext()){
			System.out.println(cursor.next());
		}
	}
	
	@Test
	public void testFindOne(){
		DBCollection coll = MongoDBUtils.getCollection("users");
		//查询 name =yellowcong的数据
		System.err.println("显示满足条件所有数据");
		DBCursor cursor = coll.find(new BasicDBObject("name", "yellowcong"));
		while(cursor.hasNext()){
			System.out.println(cursor.next());
		}
		
		//显示我们需要显示的字段
		System.err.println("\r\r显示需要的字段");
		DBObject obj = new BasicDBObject();
		obj.put("_id", false);
		cursor = coll.find(null,obj);
		while(cursor.hasNext()){
			System.out.println(cursor.next());
		}
	}
	
	@Test
	public void  testQueryByPage(){
		
		DBObject key = new BasicDBObject();
		key.put("_id", false);
		
		
		DBCursor users= MongoDBUtils.findByPager("users", null, key, 0, 2);
		while(users.hasNext()){
			System.out.println(users.next());
		}
	}
	
}
