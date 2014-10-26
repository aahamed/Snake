package com.aadil.snake;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;

public class MainMenuActivity extends ActionBarActivity {

	private Button mPlayButton;
	public static final String KEY_DIFFICULTY = "difficulty";
	private int selectedDifficulty = GameViewThread.DIFFICULTY_EASY;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_snake);
		mPlayButton = (Button)findViewById(R.id.play_button);
		mPlayButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(), GameActivity.class);
				intent.putExtra(KEY_DIFFICULTY, selectedDifficulty);
				startActivity(intent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.snake, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void onRadioButtonClicked(View view)
	{
		 // Is the button now checked?
	    boolean checked = ((RadioButton) view).isChecked();
	    
	    // Check which radio button was clicked
	    switch(view.getId()) {
	        case R.id.radio_easy:
	            if (checked)
	                // set difficulty to easy
	            	selectedDifficulty = GameViewThread.DIFFICULTY_EASY;
	            break;
	        case R.id.radio_medium:
	            if (checked)
	                // set difficulty to medium
	            	selectedDifficulty = GameViewThread.DIFFICULTY_MEDIUM;
	            break;
	        case R.id.radio_hard:
	        	if(checked)
	        		//set difficulty to hard
	        		selectedDifficulty = GameViewThread.DIFFICULTY_HARD;
	        	break;
	    }
	}
}
