package com.yellowcong.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

public class MongoDBUtils {
	private static DB database = null;
	static{
		try {
			InputStream in = MongoDBUtils.class.getClassLoader().getResourceAsStream("mongo-config.properties");
			Properties prop = new Properties();
			prop.load(in);
			//获取里面的数据
			String url = prop.get("url").toString();
			String dbname = prop.get("dbname").toString();
			Mongo  mongo = new Mongo(url);
			database = mongo.getDB(dbname);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//私有化构造方法
	private MongoDBUtils(){}
	
	public static DB getDb(){
		return database;
	}
	
	/**
	 * 创建集合对象
	 * @param collectionName
	 */
	public static void createCollection(String collectionName){
		DB db = getDb();
		//当存在集合的情况
		if(!db.collectionExists(collectionName)){
			db.createCollection(collectionName, new BasicDBObject("key","val"));
		}
	}
	
	/**
	 * 删除集合对象
	 * @param collectionName
	 */
	public static void dropCollection(String collectionName){
		DB db = getDb();
		//获取集合
		if(db.collectionExists(collectionName)){
			DBCollection collection = db.getCollection(collectionName);
			collection.drop();
		}
	}
	
	/**
	 * 获取表的方法
	 * @param collectionName 集合的名称
	 * @return 集合对象  如果 存在就返回DBCollection 如果不存在就返回null
	 */
	public static DBCollection getCollection(String collectionName){
		DB db = getDb();
		DBCollection coll = null;
		//获取集合
		if(db.collectionExists(collectionName)){
			coll  = db.getCollection(collectionName);
		}
		return coll;
	}
	
	/**
	 * 插入数据，添加集合的名称和插入的数据对象
	 * 插入单个数据
	 * @param collectionName
	 * @param object
	 */
	public static void insert(String collectionName,DBObject object){
		DB db = getDb();
		if(!db.collectionExists(collectionName)){
			createCollection(collectionName);
		}
		DBCollection table = db.getCollection(collectionName);
		table.insert(object);
	}
	
	/**
	 * 插入多条数据
	 * @param collectionName 集合名称
	 * @param objs List<DBOject>
	 */
	public static void insert(String collectionName,List<DBObject> objs){
		DB db = getDb();
		if(!db.collectionExists(collectionName)){
			createCollection(collectionName);
		}
		DBCollection table = db.getCollection(collectionName);
		//可以直接传递一个集合过来，插入
		table.insert(objs);
	}
	
	/**
	 * 通过Id 来删除数据
	 * @param collectionName  集合名称
	 * @param id  id名称
	 */
	public static void deleteById(String collectionName,String id){
		delete(collectionName, new BasicDBObject("_id", new ObjectId(id)));
	}
	/**
	 * 通过 条件来删除数据
	 * @param collectionName  集合名称
	 * @param object  DBOject 对象
	 */
	public static void delete(String collectionName,DBObject object){
		DBCollection collection = getCollection(collectionName);
		collection.remove(object);
	}
	
	/**
	 * 更新数据 
	 * @param collectionName 集合名称
	 * @param find  查询数据
	 * @param update 更新数据  需要添加一些字段  $set
	 */
	public static void update(String collectionName,DBObject find,DBObject update){
		DBCollection coll = getCollection(collectionName);
		//find是查找， update是更新对象 ， false表示 insertOrupdate  true表示批量更新操作
		coll.update(find, update, false, true);
	}
	/**
	 * 获取所有的数据
	 * @param collectionName 集合名称
	 * @return DBCursor数据
	 */
	public static DBCursor findAll(String collectionName){
		DBCollection coll = getCollection(collectionName);
		DBCursor br = coll.find();
		return br;
	}
	
	/**
	 * 分页查询
	 * @param collectionName 集合名称
	 * @param find  DBOject 查询语句 当为null 表示查询所有数据
	 * @param key  DBoejct 显示的字段 默认全部显示
	 * @param start  开始大小
	 * @param pageSize  页面数
	 * @return
	 */
	public static DBCursor findByPager(String collectionName,DBObject find,DBObject key,int start,int pageSize){
		DBCollection coll = getCollection(collectionName);
		DBCursor br =  coll.find(find, key);
		br.limit(pageSize).skip(start);
		return br;
	}
	
}	
