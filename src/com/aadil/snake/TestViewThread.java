package com.aadil.snake;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

public class TestViewThread extends SurfaceView implements Runnable {

	private Thread renderThread;
	private SurfaceHolder holder;
	volatile boolean running;
	private WindowManager wm;
	private int screenWidth;
	private int screenHeight;
	private float leftPos;
	private float rightPos;
	private float topPos;
	private float botPos;
	private int speedX;
	private int speedY;
	private final String TAG = "GameViewThread";
	private boolean D = false;

	public TestViewThread(Context context) {
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
		leftPos = 10;
		rightPos = 100;
		topPos = 10;
		botPos = 100;
		speedX = 0;
		speedY = 0;
	}
	
	public void renderCanvas(Canvas canvas)
	{
		if(D) Log.d(TAG, "in renderCanvas()");
		Paint paint = new Paint();
		canvas.drawColor(Color.BLACK);
    	paint.setColor(Color.WHITE);
    	canvas.drawRect(leftPos, topPos, rightPos, botPos, paint);
	}
	
	public void updateCanvas(float deltaTime)
	{
		if(D)
		{
			Log.d(TAG, "leftPos = " + leftPos + " rightPos = " + rightPos + " screenWidth = " + screenWidth
					+ "\ntopPos = " + topPos + " botPos = " + botPos + " screenHeight = " + screenHeight);
		}
		float deltaPosX = speedX * deltaTime;
		float deltaPosY = speedY * deltaTime;
    	if((rightPos + (deltaPosX) < screenWidth) && (leftPos + deltaPosX > 0))
    	{
    		rightPos += deltaPosX;
    		leftPos += deltaPosX;
    	}
    	if((botPos + deltaPosY < screenHeight) && (topPos + deltaPosY  > 0))
    	{
    		topPos += deltaPosY;
    		botPos += deltaPosY;
    	}
	}
	
	public void resume()
	{
		running = true;
		renderThread.start();
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
				float deltaTime = (System.nanoTime() - startTime)/1000000000.0f;
				startTime = System.nanoTime();
				renderCanvas(canvas);
				updateCanvas(deltaTime);
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
				speedY = -200;
				speedX = 0;
				if(D) Log.d(TAG, "up");
			}
			else if(y > 570)
			{
				speedY =  200;
				speedX = 0;
				if(D) Log.d(TAG, "down");
			}
			else if(x < 200)
			{
				speedX = -200;
				speedY = 0;
				if(D) Log.d(TAG, "left");
			}
			else if(x > 996)
			{
				speedX =  200;
				speedY = 0;
				if(D) Log.d(TAG, "right");
			}
			
		}		
	    return true ;
	}

}
