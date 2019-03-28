package com.example.gamebacklog.Model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

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
	private Date date;
	
	public Game() {
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
		dest.writeLong(this.date != null ? this.date.getTime() : -1);
	}
	
	protected Game(Parcel in) {
		this.id = (Long) in.readValue(Long.class.getClassLoader());
		this.mTitle = in.readString();
		this.mPlatform = in.readString();
		this.status = in.readString();
		long tmpDate = in.readLong();
		this.date = tmpDate == -1 ? null : new Date(tmpDate);
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
