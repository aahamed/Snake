package com.aadil.snake;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;

public class GameActivity extends Activity{
	
	private GameViewThread renderView;
	private final String TAG = "GameActivity";
	private boolean D = Constants.D;
	
	public void onCreate(Bundle savedInstanceState) {
        super .onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                             WindowManager.LayoutParams.FLAG_FULLSCREEN);
        renderView = new GameViewThread(this);
        setContentView(renderView);
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
