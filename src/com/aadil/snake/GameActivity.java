package com.aadil.snake;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;

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
