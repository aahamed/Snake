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
	private int screenWidth;
	private int screenHeight;
	private GameBoard board;
	//private Snake snake;
	private final String TAG = "GameViewThread";
	private boolean D = true;
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
		Paint paint = new Paint();
		canvas.drawColor(Color.DKGRAY);
		board.drawGameBoard(canvas);
	}
	
	int count = 0;
	public void updateCanvas()
	{
		if(count % 60 == 0)
		{
//			if(board.moveSnake() == false)
//			{
//				running = false;
//				Log.d(TAG, "snake is out of bounds");
//			}
			board.moveSnake();
			if(!board.snakeInBounds())
			{
				running = false;
				Log.d(TAG, "snake is out of bounds");
			}
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
			if(y < 150)
			{
				if(D) Log.d(TAG, "up");
				board.setSnakeDirection(Direction.UP);
			}
			else if(y > 570)
			{
				if(D) Log.d(TAG, "down");
				board.setSnakeDirection(Direction.DOWN);
			}
			else if(x < 200)
			{
				if(D) Log.d(TAG, "left");
				board.setSnakeDirection(Direction.LEFT);
			}
			else if(x > 996)
			{
				if(D) Log.d(TAG, "right");
				board.setSnakeDirection(Direction.RIGHT);
			}
			
		}		
	    return true ;
	}

}
