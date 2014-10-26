package com.aadil.snake;

import java.util.ArrayList;
import java.util.Random;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.util.Log;

public class GameBoard {

	static int geWidth = 40; //grid element width
	final int rows = 18;
	final int cols = 20;
	int boardLength = geWidth * cols;
	int boardHeight = geWidth * rows;
	int leftOffset = 0;
	int topOffset = 0;
	int score = 0;
	private int applesEaten = 0;
	private long startTime;
	String stringScore;
	Square [][] board = new Square[cols][rows]; //(col, row)20, 14 are placeholders for now
	Snake snake;
	Direction currSnakeDir;
	Apple apple;
	Paint paint = new Paint();
	private String TAG = "GameBoard";
	private boolean D = false;
	
	public GameBoard()
	{
		snake = new Snake();
		createNewApple();
		currSnakeDir = Direction.RIGHT;
		for(int i = 0; i < board.length; i++)
		{
			for(int j = 0; j < board[i].length; j++)
			{
				board[i][j] = new Square(leftOffset + (geWidth * i), topOffset + (geWidth * j), geWidth);
			}
		}
		startTime = System.currentTimeMillis();
		stringScore = "Score: " + score;
	}
	
	private Rect getBoundaryRect()
	{
		int left = board[0][0].getLeft();
		int top = board[0][0].getTop();
		int right = board[cols-1][rows-1].getRight();
		int bottom = board[cols-1][rows-1].getBottom();
		return new Rect(left, top, right, bottom);
	}
	
	public void turnGridOn(Canvas canvas)
	{
		paint.setStyle(Style.STROKE);
		paint.setColor(Color.WHITE);
		for(int i = 0; i < board.length; i++)
		{
			for(int j = 0; j < board[i].length; j++)
			{
				board[i][j].draw(canvas, paint);
			}
		}
	}
	
	private final int KEY_WIDTH = 90;
	private final int KEYPAD_OFFSET = 63;
	private Rect leftKey = new Rect(boardLength + KEYPAD_OFFSET, (boardHeight/2) - (KEY_WIDTH/2), boardLength + KEYPAD_OFFSET + KEY_WIDTH,
			(boardHeight/2) + (KEY_WIDTH/2));
	private Rect rightKey = new Rect(leftKey.right + KEY_WIDTH, leftKey.top, leftKey.right+(KEY_WIDTH*2), leftKey.bottom);
	private Rect upKey = new Rect(leftKey.right, leftKey.top - KEY_WIDTH, rightKey.left, leftKey.top);
	private Rect downKey = new Rect(upKey.left, leftKey.bottom, upKey.right, leftKey.bottom + (KEY_WIDTH));
	private void drawKeyPad(Canvas canvas)
	{
		
		paint.setStyle(Style.STROKE);
		paint.setColor(Color.WHITE);
		canvas.drawRect(leftKey, paint);
		canvas.drawRect(rightKey, paint);
		canvas.drawRect(upKey, paint);
		canvas.drawRect(downKey, paint);
	}
	
	public boolean inRect(int x, int y, Rect r)
	{
		if(x > r.left && x < r.right && y > r.top && y < r.bottom)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public void isKeyPressed(int x, int y)
	{
		if(inRect(x, y, leftKey))
		{
			if(D) Log.d(TAG, "left key is pressed");
			setSnakeDirection(Direction.LEFT);
		}
		else if(inRect(x, y, rightKey))
		{
			if(D) Log.d(TAG, "right key is pressed");
			setSnakeDirection(Direction.RIGHT);
		}
		else if(inRect(x, y, upKey))
		{
			if(D) Log.d(TAG, "upKey is pressed");
			setSnakeDirection(Direction.UP);
		}
		else if(inRect(x, y, downKey))
		{
			if(D) Log.d(TAG, "downKey is pressed");
			setSnakeDirection(Direction.DOWN);
		}
	}
	
	public void draw(Canvas canvas)
	{
		paint.setStyle(Style.FILL);
		paint.setColor(Color.BLACK);
		canvas.drawRect(getBoundaryRect(), paint);
		//turnGridOn(canvas);
		drawApple(canvas);
		drawSnake(canvas);
		drawKeyPad(canvas);
		drawScore(canvas);
	}
	
	public void drawScore(Canvas canvas)
	{
		float textSize = 30.0f;
		paint.setColor(Color.WHITE);
		paint.setTextAlign(Align.CENTER);
		paint.setTextSize(textSize);
		canvas.drawText(stringScore, upKey.left + (KEY_WIDTH/2), upKey.top/2, paint);
	}
	
	public void update()
	{
		moveSnake();
		if(isAppleEaten())
		{
			createNewApple();
			snake.elongate();
			applesEaten++;
			Log.d(TAG, "startTime = " + startTime);
			float deltaTime = (System.currentTimeMillis() - startTime)/1000;
			startTime = System.currentTimeMillis();
			Log.d(TAG, "deltaTime = " + deltaTime);
			updateScore((int)(deltaTime));
			stringScore = "Score: " + score;
			incDifficulty();
		}
	}
	
	int base = 10;
	public void updateScore(int deltaTime)
	{
		if(D) Log.d(TAG, "updateScore() called");
		int multiplier = 5;
		int bonus = 0;
		if(applesEaten % 5 == 0)
		{
			base += 5;
		}
		bonus = base - deltaTime;
		if(bonus < 0)
			bonus = 0;
		score += (multiplier + (bonus)) * applesEaten;
		Log.d(TAG, "multiplier = " + multiplier + " base = " + base + "\napplesEaten = " + applesEaten + " score = " + score);
	}
	
	private void incDifficulty()
	{
		if((snake.getLength()-3) % 4 == 0)
		{
			snake.incSpeed();
		}
	}
	
	public void drawSnake(Canvas canvas)
	{
		Position pos;
		Square square;
		ArrayList <Position> snakeBody = snake.getSnakeBody();
		for(int i = 0; i < snakeBody.size(); i++)
		{
			pos = snakeBody.get(i);
			//Log.d(TAG, "i = " + i + "posX = " + pos.getX() + " posY = " + pos.getY());
			square = board[pos.getX()][pos.getY()];
			Paint paint = new Paint();
			paint.setStyle(Style.STROKE);
			paint.setColor(Color.GREEN);
			square.draw(canvas, paint);
		}
	}
	
	public int getSnakeSpeed()
	{
		return snake.getSpeed();
	}
	
	public void setSnakeSpeed(int speed)
	{
		snake.setSpeed(speed);
	}
	public void createNewApple()
	{
		boolean valid = false;
		Position applePos;
		Random random = new Random();
		do
		{
			applePos = new Position(random.nextInt(cols), random.nextInt(rows));
			for(Position pos : snake.getSnakeBody())
			{
				if(pos.equals(applePos))
				{
					valid = false;
					break;
				}
				else
				{
					valid = true;
				}
			}
		}
		while(!valid);
		apple = new Apple(applePos);
	}
	
	//location is location on screen
	public void drawApple(Canvas canvas)
	{
		Paint paint = new Paint();
		paint.setStyle(Style.FILL);
		paint.setColor(Color.RED);
		Position gridPos = apple.getPositon();
		Square square = board[gridPos.getX()][gridPos.getY()];
		Position center = square.getCenter();
		if(D) Log.d(TAG, "apple position = " + gridPos + "\napple center = " + center);
		canvas.drawCircle(center.getX(), center.getY(), apple.getRadius(), paint);
	}	
	
	public boolean isAppleEaten()
	{
		if(snake.getHead().equals(apple.getPositon()))
			return true;
		else
			return false;
	}
	public Snake getSnake()
	{
		return snake;
	}
	
	public int getRows()
	{
		return rows;
	}
	
	public int getCols()
	{
		return cols;
	}
	
	public Direction getCurrentSnakeDirection()
	{
		return snake.getCurrentDirection();
	}
	
	public boolean setSnakeDirection(Direction dir)
	{
		return snake.setDirection(dir);
	}
	
	public void turnSnakeCW()
	{
		snake.turnClockWise();
	}
	
	public void turnSnakeCCW()
	{
		snake.turnCounterClockWise();
	}
	
	public boolean snakeInBounds()
	{
		Position pos = snake.getHead();
		if(pos.getX() >= 0 && pos.getX() < cols && pos.getY() >= 0 && pos.getY() < rows)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public boolean gameOver()
	{
		if(snakeInBounds() == false || snake.hasEatenItself() == true)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public void moveSnake()
	{
		snake.move();
	}
	
	private class Apple
	{
		// position on gameboard grid
		Position position;
		int radius = GameBoard.geWidth / 4; 
		
		public Apple(Position pos)
		{
			position = pos;
		}
		
		public Position getPositon()
		{
			return position;
		}
	
		public void setPosition(Position pos)
		{
			position = pos;
		}

		public int getRadius()
		{
			return radius;
		}
		
	}
}
