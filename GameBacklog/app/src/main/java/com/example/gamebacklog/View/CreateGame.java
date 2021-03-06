package com.example.gamebacklog.View;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.gamebacklog.Control.Game;
import com.example.gamebacklog.R;

public class CreateGame extends AppCompatActivity {
	
	private EditText mEditTitle;
	private EditText mPlatform;
	private Spinner mStatus;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_game);
		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		
		mEditTitle = findViewById(R.id.editTextPlatform_C);
		mPlatform = findViewById(R.id.editTextPlatform_C);
		
		mStatus = findViewById(R.id.spinnerStatus_C);
		
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
				R.array.status, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mStatus.setAdapter(adapter);
		
		FloatingActionButton fab = findViewById(R.id.fabAdd);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				
				String title = mEditTitle.getText().toString();
				String platform = mPlatform.getText().toString();
				String status = mStatus.getSelectedItem().toString();
				
				Game game = new Game(title, platform, status);
				Intent intent = new Intent();
				intent.putExtra(MainActivity.CREATE_GAME, game);
				setResult(Activity.RESULT_OK, intent);
				finish();
			}
		});
	}
}
