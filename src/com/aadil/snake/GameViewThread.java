package com.aadil.snake;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

public class GameViewThread extends SurfaceView implements Runnable {

	private Thread renderThread;
	private SurfaceHolder holder;
	volatile boolean running;
	private WindowManager wm;
	public static int screenWidth;
	public static int screenHeight;
	private GameBoard board;
	//private Snake snake;
	private final String TAG = "GameViewThread";
	private boolean D = true;
	private final int SCREEN_FPS = 60;
	private final int TARGET_FPS = 4;
	private boolean touchDown = false;
	final Handler handler = new Handler();

	public GameViewThread(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		holder = getHolder();
		renderThread = new Thread(this);
		wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		screenWidth = size.x;
		screenHeight = size.y;
		Log.d(TAG, "screen width: " + screenWidth + " screen height: " + screenHeight);
		board = new GameBoard();
		//snake = board.getSnake();
	}
	
	public void renderCanvas(Canvas canvas)
	{
		//if(D) Log.d(TAG, "in renderCanvas()");
		//Paint paint = new Paint();
		canvas.drawColor(Color.DKGRAY);
		board.draw(canvas);
	}
	
	int count = 0;
	public void updateCanvas()
	{
		if(count % (SCREEN_FPS/TARGET_FPS) == 0)
		{
//			if(board.moveSnake() == false)
//			{
//				running = false;
//				Log.d(TAG, "snake is out of bounds");
//			}
			board.update();
			touchDown = false;
			if(board.gameOver())
			{
				running = false;
				Log.d(TAG, "Game Over!");
			}
//			if(!board.snakeInBounds())
//			{
//				running = false;
//				Log.d(TAG, "snake is out of bounds");
//			}
			count = 0;

		}
		count++;
	}
	
	public void resume()
	{
		if(running == false)
		{
			running = true;
			renderThread.start();
		}
	}
	
	public void pause()
	{
		running = false ;
	    while (true) 
	    {
	        try 
	        {
	            renderThread.join();
	            return;
	        }
	        catch (InterruptedException e) 
	        {
	              // retry
	        }
	      }
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		long startTime = System.nanoTime();
		while(running)
		{
			if(!holder.getSurface().isValid())
			{
				continue;
			}
			Canvas canvas = holder.lockCanvas();
			synchronized(holder)
			{
				renderCanvas(canvas);
				//float deltaTime = 0;
				//startTime = System.currentTimeMillis();
				updateCanvas();
				holder.unlockCanvasAndPost(canvas);
			}
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		switch (event.getAction()) 
	    {
	      case MotionEvent.ACTION_DOWN:
	          if(D) Log.d(TAG, "finger down");
	          break ;
	      case MotionEvent.ACTION_MOVE:
	          if(D) Log.d(TAG, "finger moved");
	          break ;
	      case MotionEvent.ACTION_CANCEL:
	    	  if(D) Log.d(TAG, "cancel");
	          break ;
	      case MotionEvent.ACTION_UP:
	    	  if(D) Log.d(TAG, "finger up");
	          break ;
	    }
		float x, y;
		x= event.getX();
		y = event.getY();
		if(D) Log.d(TAG, "x = " + event.getX() + " y = " + event.getY());
		synchronized(holder)
		{
			if(touchDown == false)
			{
				board.isKeyPressed((int)x, (int)y);
				touchDown = true;
			}
		}		
	    return true ;
	}

}
