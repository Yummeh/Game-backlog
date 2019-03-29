package com.example.gamebacklog.View;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.gamebacklog.Control.Game;
import com.example.gamebacklog.R;

import static java.lang.System.in;

public class EditGame extends AppCompatActivity {
	
	private EditText mEditTitle;
	private EditText mPlatform;
	private Spinner mStatus;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_game);
		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		
		mEditTitle = findViewById(R.id.editTextTitle_E);
		mPlatform = findViewById(R.id.editTextPlatform_E);
		
		mStatus = findViewById(R.id.spinnerStatus_E);
		
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
				R.array.status, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mStatus.setAdapter(adapter);
		
		final Game game = getIntent().getParcelableExtra(MainActivity.UPDATE_GAME);
		mEditTitle.setText(game.getTitle());
		mPlatform.setText(game.getPlatform());
		
		FloatingActionButton fab = findViewById(R.id.fabAdd);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				game.setPlatform(mPlatform.getText().toString());
				game.setStatus(mStatus.getSelectedItem().toString());
				game.setTitle(mEditTitle.getText().toString());
				game.setDate(game.getDate());
				
				Intent intent = new Intent();
				intent.putExtra(MainActivity.UPDATE_GAME, game);
				setResult(Activity.RESULT_OK, intent);
				finish();
			}
		});
	}
	
}
