package com.example.gamebacklog.Control;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity(tableName = "game")
public class Game implements Parcelable {
	
	@PrimaryKey(autoGenerate = true)
	private Long id;
	
	@ColumnInfo(name = "title")
	private String mTitle;
	
	@ColumnInfo(name = "platform")
	private String mPlatform;
	
	@ColumnInfo(name = "status")
	private String status;
	
	@ColumnInfo(name = "date")
	private String date;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getTitle() {
		return mTitle;
	}
	
	public void setTitle(String mTitle) {
		this.mTitle = mTitle;
	}
	
	public String getPlatform() {
		return mPlatform;
	}
	
	public void setPlatform(String mPlatform) {
		this.mPlatform = mPlatform;
	}
	
	public String getDate() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public Game(String mTitle, String mPlatform, String status) {
		this.mTitle = mTitle;
		this.mPlatform = mPlatform;
		this.status = status;
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date date = new Date();
		this.date = dateFormat.format(date);
	}
	
	@Override
	public int describeContents() {
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeValue(this.id);
		dest.writeString(this.mTitle);
		dest.writeString(this.mPlatform);
		dest.writeString(this.status);
		dest.writeString(this.date);
	}
	
	protected Game(Parcel in) {
		this.id = (Long) in.readValue(Long.class.getClassLoader());
		this.mTitle = in.readString();
		this.mPlatform = in.readString();
		this.status = in.readString();
		this.date = in.readString();
	}
	
	public static final Creator<Game> CREATOR = new Creator<Game>() {
		@Override
		public Game createFromParcel(Parcel source) {
			return new Game(source);
		}
		
		@Override
		public Game[] newArray(int size) {
			return new Game[size];
		}
	};
}
