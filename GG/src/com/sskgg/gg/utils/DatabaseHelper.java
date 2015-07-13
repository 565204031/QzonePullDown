package com.sskgg.gg.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jivesoftware.smack.XMPPConnection;

import com.sskgg.gg.entity.MessageInfo;
import com.sskgg.gg.entity.MessageList;
import com.sskgg.gg.entity.MultiUserList;
import com.sskgg.gg.entity.MultiUserMessageInfo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper{
	
	public static SQLiteDatabase initdb(Context ct)
	{
		  MySQLiteOpenHelper helper=new MySQLiteOpenHelper(ct, "gg_db.db", null, 1);   
		  SQLiteDatabase db = helper.getWritableDatabase();
		  return db;
	}
	/**
	 * @param mContext
	 * @return 根据房间id 返回聊天记录
	 */
	public static List<MultiUserMessageInfo> getListMessageInfo(Context mContext,long _listid,int top){
		 List<MultiUserMessageInfo> list=new ArrayList<MultiUserMessageInfo>();
		  SQLiteDatabase db=initdb(mContext);
		  Cursor cursor = db.query("multiuserinfo", null,"_listid=?", new String[]{_listid+""}, null, null, top>1?top+"":null);
		while(cursor.moveToNext()){
      	long id = cursor.getLong(cursor.getColumnIndex("_id"));
      	int mode = cursor.getInt(cursor.getColumnIndex("_mode"));
    	String from = cursor.getString(cursor.getColumnIndex("_from"));
    	String body = cursor.getString(cursor.getColumnIndex("_body"));
    	boolean read = cursor.getString(cursor.getColumnIndex("_read"))=="0"?false:true;
    	String type = cursor.getString(cursor.getColumnIndex("_type"));
      	String time = cursor.getString(cursor.getColumnIndex("_time"));
      	MultiUserMessageInfo info=new MultiUserMessageInfo();
    	info.setId(id);
      	info.setListid(_listid);
      	info.setMode(mode);
      	info.setFrom(from);
     	info.setBody(body);
     	info.setRead(read);
     	info.setType(type);
     	info.setTime(time);
      	list.add(info);
		}
		return list;
	}
	/**
	 * 新增会议室列表
	 */
	public static long addMultiUserInfo(Context mContext,MultiUserMessageInfo info){
	  SQLiteDatabase db=initdb(mContext);
	  ContentValues values = new ContentValues();
      values.put("_listid", info.getListid());
      values.put("_mode", info.getMode());
      values.put("_from", info.getFrom());
      values.put("_body", info.getBody());
      values.put("_read", info.isRead());
      values.put("_type", info.getType());
      values.put("_time", new Date().toString());
      return db.insert("multiuserinfo", null, values);
	}
	/**
	 * @param mContext
	 * @return 返回用户所加入的会议室
	 */
	public static List<MultiUserList> getListByUserName(Context mContext,String name){
		 List<MultiUserList> list=new ArrayList<MultiUserList>();
		  SQLiteDatabase db=initdb(mContext);
		  Cursor cursor = db.query("multiuserlist", null,"_ownership=?", new String[]{name}, null, null, null);
		while(cursor.moveToNext()){
      	long id = cursor.getLong(cursor.getColumnIndex("_id"));
      	String room = cursor.getString(cursor.getColumnIndex("_room"));
      	String time = cursor.getString(cursor.getColumnIndex("_time"));
      	MultiUserList info=new MultiUserList();
      	info.setId(id);
      	info.setRoom(room);
    	info.setTime(time);
      	list.add(info);
		}
		return list;
	}
	
	/**
	 * 删除会议室列表
	 */
	public static long delMultiUserList(Context mContext,String room,String name){
	  SQLiteDatabase db=initdb(mContext);
      return db.delete("multiuserlist", "_room=? and _ownership=?", new String[]{room,name});
	}
	/**
	 * 新增会议室列表
	 */
	public static long addMultiUserList(Context mContext,String room,String name){
	  SQLiteDatabase db=initdb(mContext);
	  ContentValues values = new ContentValues();
      values.put("_room", room);
      values.put("_ownership", name);
      values.put("_time", new Date().toString());
      return db.insert("multiuserlist", null, values);
	}
	/**
	 * 
	 * @return 会议室id
	 */
	public static long getMultiUserListId(Context mContext,String room){
		  SQLiteDatabase db=initdb(mContext);
		  long id=0;
		  Cursor cursor = db.query("multiuserlist", null,"_room=?", new String[]{room}, null, null, null);
		  while(cursor.moveToNext()){
			  id = cursor.getLong(cursor.getColumnIndex("_id"));
		  }
		  return id;
	}
	
	/**
	 * 修改消息表未读条数，最后内容
	 * @return
	 */
	public static long updateMessageList(Context mContext,MessageList list)
	{
		SQLiteDatabase db=initdb(mContext);
		ContentValues values = new ContentValues();
		values.put("_body", list.getBody());
      	values.put("_number", list.getNumber());
      	values.put("_time", new Date().toString());
		return db.update("messageslist",values,"_id=?",new String[]{list.getId()+""});
	}
	/**
	 * 根据_listid 返回未读内容条数
	 * @param mContext
	 * @param _listid
	 * @return
	 */
	public static long getMessageInfoNumber(Context mContext,long _listid)
	{
		  SQLiteDatabase db=initdb(mContext);
		  Cursor cursor = db.query("messagesinfo", null,"_listid=? and _read=0", new String[]{_listid+""}, null, null, null);
          LogUtils.i("info",cursor.getCount()+"");		
		  return cursor.getCount();
	}
     /**
      * 返回所有消息列表
      * @param mContext
      * @return
      */
	public static List<MessageList> queryLastBodyByUsernameAndTo(Context mContext){
		 List<MessageList> list=new ArrayList<MessageList>();
		  SQLiteDatabase db=initdb(mContext);
		  Cursor cursor = db.query("messageslist", null,"_isremove=?", new String[]{"0"}, null, null, null);
		  while(cursor.moveToNext()){
       	long id = cursor.getLong(cursor.getColumnIndex("_id"));
       	String _username = cursor.getString(cursor.getColumnIndex("_username"));
       	boolean _isremove = cursor.getInt(cursor.getColumnIndex("_isremove"))==1?true:false;
       	String to = cursor.getString(cursor.getColumnIndex("_to"));
       	String time = cursor.getString(cursor.getColumnIndex("_time"));
       	String body = cursor.getString(cursor.getColumnIndex("_body"));
       	long number = cursor.getInt(cursor.getColumnIndex("_number"));
        MessageList info=new MessageList();
       	info.setId(id);
       	info.setUsername(_username);
    	info.setTo(to);
       	info.setIsremove(_isremove);
      	info.setTime(time);
      	info.setBody(body);
      	info.setNumber(number);
       	list.add(info);
		  }
		return list;
	}
	/**
	 * 
	 * @return 消息列表id
	 */
	public static long queryMessageListIdByNameAndTo(Context mContext,String name,String to){
		  SQLiteDatabase db=initdb(mContext);
		  long id=0;
		  Cursor cursor = db.query("messageslist", null,"_username=? and _to=?", new String[]{name,to}, null, null, null);
		  while(cursor.moveToNext()){
			  id = cursor.getLong(cursor.getColumnIndex("_id"));
		  }
		  return id;
	}
	
	/**
	 * 根据消息列表id返回消息内容
	 * @param mContext
	 * @param 消息列表id 
	 */
	public static List<MessageInfo> getMessageInfoList(Context mContext,long _listid)
	{
		  List<MessageInfo> list=new ArrayList<MessageInfo>();
		  SQLiteDatabase db=initdb(mContext);
		  Cursor cursor = db.query("messagesinfo", null,"_listid=?", new String[]{_listid+""}, null, null, null);
		  while(cursor.moveToNext()){
        	long id = cursor.getLong(cursor.getColumnIndex("_id"));
        	long listid = cursor.getLong(cursor.getColumnIndex("_listid"));
        	String body = cursor.getString(cursor.getColumnIndex("_body"));
        	boolean read = cursor.getInt(cursor.getColumnIndex("_read"))==1?true:false;
        	String type = cursor.getString(cursor.getColumnIndex("_type"));
         	String time = cursor.getString(cursor.getColumnIndex("_time"));
        	MessageInfo info=new MessageInfo();
        	info.setId(id);
        	info.setBody(body);
        	info.setRead(read);
        	info.setType(type);
        	info.setTime(time);
        	info.setListid(listid);
        	list.add(info);
		  }
		  return list;
	}
	/**
	 * 新增信息列表
	 */
	public static long addMessageList(Context mContext,MessageList list){
	  SQLiteDatabase db=initdb(mContext);
	  ContentValues values = new ContentValues();
      values.put("_username", list.getUsername());
      values.put("_to", list.getTo());
      values.put("_isremove", list.isIsremove());
      values.put("_body", "");
      values.put("_number", "0");
      values.put("_time", new Date().toString());
      return db.insert("messageslist", null, values);
	}
	/**
	 * 新增消息内容
	 */
	public static long addMessageInfo(Context mContext,MessageInfo info){
        //插入数据 
	  SQLiteDatabase db=initdb(mContext);
	  ContentValues values = new ContentValues();
      values.put("_listid", info.getListid());
      values.put("_mode", info.getMode());
      values.put("_body", info.getBody());
      values.put("_read", info.isRead());
      values.put("_type", info.getType());
      values.put("_time", new Date().toString());
      return db.insert("messagesinfo", null, values);
	}
    private static class MySQLiteOpenHelper extends SQLiteOpenHelper{

    	public MySQLiteOpenHelper(Context context, String name,
				CursorFactory factory, int version) {
			super(context, name, factory, version);
			// TODO Auto-generated constructor stub
		}
		@Override
    	public void onCreate(SQLiteDatabase db) {
			db.beginTransaction();
			try{
				//聊天表
				//发送者,接收者,未读条数,最后接收消息内容,时间,是否移除消息队列
				db.execSQL("create table messageslist(_id integer primary key autoincrement," +
						"_username varchar(200),"
						+"_to varchar(200),"
						+"_number integer,"
						+"_body varchar(200),"
						+"_time date,"
						+"_isremove bit)"
						);
				//聊天记录表
				//外键id,发送or接收,是否已读0,消息类型,时间
				db.execSQL("create table messagesinfo(_id integer primary key autoincrement," +
						"_listid integer,"
						+"_mode integer,"
						+"_body varchar(200),"
						+"_read bit,"
						+"_type varchar(20),"
						+"_time date)");
				
				//会议室列表
				//ID，所有者，房间标志，加入时间
				db.execSQL("create table multiuserlist(_id integer primary key autoincrement," +
						"_ownership varchar(20),"
						+"_room varchar(100),"
						+"_time date)");
				//会议室消息表
				//外键id,发送or接收,发送人,是否已读0,消息类型,时间
				db.execSQL("create table multiuserinfo(_id integer primary key autoincrement," +
						"_listid integer,"
						+"_mode integer,"
						+"_from varchar(20),"
						+"_body varchar(200),"
						+"_read bit,"
						+"_type varchar(20),"
						+"_time date)");
				db.setTransactionSuccessful();//设置事务成功
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				db.endTransaction();//结束事务
			}
    	}
    	@Override
    	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    		// 升级版本调用
    	}
    }
	

}
