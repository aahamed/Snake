package com.aadil.snake;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

public class GameActivity extends Activity{
	
	private GameViewThread renderView;
	private final String TAG = "GameActivity";
	private boolean D = Constants.D;
	private int difficulty;
	
	public void onCreate(Bundle savedInstanceState) {
        super .onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                             WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Intent intent = getIntent();
        difficulty = intent.getIntExtra(MainMenuActivity.KEY_DIFFICULTY, GameViewThread.DIFFICULTY_EASY);
        renderView = new GameViewThread(this, difficulty);
        setContentView(renderView);
    }
	
	protected void onStart()
	{
		super.onStart();
		Log.d(TAG, "onStart() called");
	}
	
	protected void onResume() {
        super .onResume();
        renderView.resume();
	}
    protected void onPause() {
        super .onPause();
        renderView.pause();
    }
    
}
