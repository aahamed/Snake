package com.aadil.snake;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.util.Log;

public class GameBoard {

	Snake snake;
	int geWidth = 50; //grid element width
	int boardLength = 1000;
	int boardHeight = 700;
	int rows = boardHeight / geWidth;
	int cols = boardLength / geWidth;
	int leftOffset = 98;
	int topOffset = 10;
	Square [][] board = new Square[cols][rows]; //(col, row)20, 14 are placeholders for now
	Direction currSnakeDir;
	Paint paint = new Paint();
	private String TAG = "GameBoard";
	
	public GameBoard()
	{
		snake = new Snake();
		currSnakeDir = Direction.RIGHT;
		for(int i = 0; i < board.length; i++)
		{
			for(int j = 0; j < board[i].length; j++)
			{
				board[i][j] = new Square(leftOffset + (geWidth * i), topOffset + (geWidth * j), geWidth);
			}
		}
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
		paint.setColor(Color.RED);
		for(int i = 0; i < board.length; i++)
		{
			for(int j = 0; j < board[i].length; j++)
			{
				board[i][j].draw(canvas, paint);
			}
		}
	}
	public void drawGameBoard(Canvas canvas)
	{
		paint.setStyle(Style.FILL);
		paint.setColor(Color.BLACK);
		canvas.drawRect(getBoundaryRect(), paint);
		turnGridOn(canvas);
		drawSnake(canvas);
	}
	
	public void drawSnake(Canvas canvas)
	{
		Position pos;
		Square square;
		ArrayList <Position> snakeBody = snake.getSnakeBody();
		for(int i = 0; i < snakeBody.size(); i++)
		{
			pos = snakeBody.get(i);
			square = board[pos.getX()][pos.getY()];
			Paint paint = new Paint();
			paint.setStyle(Style.STROKE);
			paint.setColor(Color.GREEN);
			square.draw(canvas, paint);
		}
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
	
	public void moveSnake()
	{
		snake.move();
	}
}
