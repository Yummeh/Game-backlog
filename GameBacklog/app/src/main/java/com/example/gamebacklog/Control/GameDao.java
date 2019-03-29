package com.example.gamebacklog.Control;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface GameDao {
	
	@Query("SELECT * FROM game")
	LiveData<List<Game>> getAllGames();
	
	@Insert
	void insertGame(Game game);
	
	@Update
	void updateGame(Game game);
	
	@Delete
	void deleteGame(Game game);
	
	@Delete
	void deleteAllGames(List<Game> games);
	
}