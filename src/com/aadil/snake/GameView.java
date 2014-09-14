package com.aadil.snake;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

class GameView extends SurfaceView 
{
        private final GameLoopThread gameLoopThread;
        private SurfaceHolder holder;
        private Paint paint;
        private final String TAG = "GameView";
        private boolean D = Constants.D;
        volatile boolean running;
        
        public GameView(Context context) 
        {
            super (context);
            gameLoopThread = new GameLoopThread(this);            
            paint = new Paint();
            holder = getHolder();
            holder.addCallback(new SurfaceHolder.Callback() {
				
				@Override
				public void surfaceDestroyed(SurfaceHolder arg0) {
					// TODO Auto-generated method stub
					if(D) Log.d(TAG, "surface destroyed");
					boolean retry = true;
					gameLoopThread.setRunning(false);
					while(retry)
					{
						try
						{
							gameLoopThread.join();
							retry = false;
						}
						catch(InterruptedException e)
						{
							
						}
					}
				}
				
				@Override
				public void surfaceCreated(SurfaceHolder arg0) {
					// TODO Auto-generated method stub
					if(D) Log.d(TAG, "surface created");
					gameLoopThread.setRunning(true);
					gameLoopThread.start();
				}
				
				@Override
				public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
					// TODO Auto-generated method stub
					//ignored
				}
			});
        }
        
        @Override
		public void draw(Canvas canvas)
        {
        	canvas.drawColor(Color.BLACK);
        	paint.setColor(Color.WHITE);
        	canvas.drawRect(10, 10, 100, 100, paint);
        }
//        public void resume() {
//            running = true ;
//        }
//        public void run() {
//            while (running) {
//                if (!holder.getSurface().isValid())
//                    continue ;
//                Canvas canvas = holder.lockCanvas();
//                canvas.drawRGB(0, 0, 0);
//                paint.setColor(Color.RED);
//                canvas.drawRect(10, 10, 100, 100, paint);
//                holder.unlockCanvasAndPost(canvas);
//            }
//        }

//        public void pause() {
//            running = false ;
//            while (true ) {
//                try {
//                    renderThread.join();
//                    return ;
//                }catch (InterruptedException e) {
//                    // retry
//                }
//            }
//        }
        
        class GameLoopThread extends Thread
        {
        	private GameView view;
        	private boolean running;
        	
        	public GameLoopThread(GameView _view)
        	{
        		view = _view;
        		running = false;
        	}
        	
        	public void setRunning(boolean run)
        	{
        		running = run;
        	}
        	@Override
        	public void run()
        	{
        		while(running)
        		{
        			Canvas c = null;
        			try
        			{
        				c = view.getHolder().lockCanvas();
        				synchronized(view.getHolder())
        				{
        					view.draw(c);
        				}
        			}
        			finally
        			{
        				if(c != null)
        				{
        					view.getHolder().unlockCanvasAndPost(c);
        				}
        			}
        		}
        	}
        }
}