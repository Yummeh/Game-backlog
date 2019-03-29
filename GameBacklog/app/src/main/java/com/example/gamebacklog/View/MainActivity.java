package com.example.gamebacklog.View;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.example.gamebacklog.Control.Game;
import com.example.gamebacklog.Control.GameAdapter;
import com.example.gamebacklog.Control.MainViewModel;
import com.example.gamebacklog.Model.GameRoomDatabase;
import com.example.gamebacklog.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity implements RecyclerView.OnItemTouchListener {
	
	public static final String CREATE_GAME = "create_game";
	public static final String UPDATE_GAME = "update_game";
	public static final int REQUESTCODE_C = 1234;
	public static final int REQUESTCODE_E = 1235;   
	
	private GameAdapter mGameAdapter;
	private RecyclerView mRecyclerView;
	private List<Game> mGames;
	
	private MainViewModel mMainViewModel;
	
	private GestureDetector mGestureDetector;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		
		
		initMainViewModel();
		initControl();
		initFAB();
		initGesture();
		initSwiped();
	}
	
	private void initFAB() {
		FloatingActionButton fab = findViewById(R.id.fabAdd);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(MainActivity.this, CreateGame.class);
				startActivityForResult(intent, REQUESTCODE_C);
			}
		});
	}
	
	private void initControl() {
		mGames = new ArrayList<>();
		mGameAdapter = new GameAdapter(mGames);
		
		mRecyclerView = findViewById(R.id.recyclerView);
		
		mRecyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
		mRecyclerView.addOnItemTouchListener(this);
		mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
		
		mRecyclerView.setAdapter(mGameAdapter);
	}
	
	private void initMainViewModel() {
		mMainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
		mMainViewModel.getGames().observe(this, new Observer<List<Game>>() {
			@Override
			public void onChanged(@Nullable List<Game> reminders) {
				mGames = reminders;
				updateUI();
			}
		});
	}
	
	private void initSwiped() {
		ItemTouchHelper.SimpleCallback simpleItemTouchCallback =
				new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
					@Override
					public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
						return false;
					}
					
					//Called when a user swipes left or right on a ViewHolder
					@Override
					public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
						int position = (viewHolder.getAdapterPosition());
						mMainViewModel.delete(mGames.get(position));
					}
				};
		
		ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
		itemTouchHelper.attachToRecyclerView(mRecyclerView);
		mRecyclerView.addOnItemTouchListener(this);
	}
	
	void initGesture() {
		mGestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
			@Override
			public boolean onSingleTapUp(MotionEvent e) {
				return true;
			}
		});
	}
	
	private void updateUI() {
		if (mGameAdapter == null) {
			mGameAdapter = new GameAdapter(mGames);
			mRecyclerView.setAdapter(mGameAdapter);
		} else {
			mGameAdapter.swapList(mGames);
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
		if (requestCode == REQUESTCODE_C) {
			if (resultCode == RESULT_OK) {
				Game game = data.getParcelableExtra(MainActivity.CREATE_GAME);
				mMainViewModel.insert(game);
			}
		} else if (requestCode == REQUESTCODE_E) {
			if (resultCode == RESULT_OK) {
				Game game = data.getParcelableExtra(MainActivity.UPDATE_GAME);
				mMainViewModel.update(game);
			}
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		
		//noinspection SimplifiableIfStatement
		if (id == R.id.action_delete_item) {
			mMainViewModel.deleteAll(mGames);
			return true;
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public boolean onInterceptTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {
		View child = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
		int mAdapterPosition = recyclerView.getChildAdapterPosition(child);
		if (child != null && mGestureDetector.onTouchEvent(motionEvent)) {
			
			Intent intent = new Intent(MainActivity.this, EditGame.class);
			intent.putExtra(UPDATE_GAME, mGames.get(mAdapterPosition));
			startActivityForResult(intent, REQUESTCODE_E);
			return true;
		}
		return false;
	}
	
	@Override
	public void onTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {
		
	}
	
	@Override
	public void onRequestDisallowInterceptTouchEvent(boolean b) {
		
	}
}