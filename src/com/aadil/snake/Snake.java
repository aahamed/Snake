package com.aadil.snake;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.Log;

public class Snake {
	private ArrayList<Position> snakeBody = new ArrayList<Position>();
	private final int initLength = 3;
	private final int length = initLength;
	private final String TAG = "Snake";
	Direction currentDir;
	
	public Snake()
	{
		createSnake();
		currentDir = Direction.RIGHT;
	}
	
	public ArrayList<Position> getSnakeBody() {
		return snakeBody;
	}

	public int getLength() {
		return length;
	}
	
	private void createSnake()
	{
		for(int i = 0; i < length; i++)
		{
			Position pos = new Position(length - 1 - i, 0);
			snakeBody.add(pos);
		}
	}
	
	public Position getHead()
	{
		return snakeBody.get(0);
	}
	
	public void elongate()
	{
		int length = snakeBody.size();
		Position nextTail;
		Position currTail = snakeBody.get(length-1);
		Position prevTail = snakeBody.get(length-2);
		Direction relDir = prevTail.getRelativeDirection(currTail);
		nextTail = new Position(currTail.getX(), currTail.getY());
		nextTail.shiftPosition(relDir);
		snakeBody.add(nextTail);
	}
	
	public Direction getCurrentDirection()
	{
		return currentDir;
	}
	
	/*
	 * setDirection:- sets the snake's current direction. Cannot set direction in reverse of the current direction
	 * 				  Only 90 degree turns allowed
	 * @params dir:- snake's new direction
	 * @return boolean:- true if new direction is set, false if dir is reverse of current direction
	 */
	public boolean setDirection(Direction dir)
	{
		if(!dir.equals(currentDir.getReverseDirection()))
		{
			currentDir = dir;
			return true;
		}
		else
		{
			Log.d(TAG, "cannot reverse direction");
			return false;
		}
	}
	
	public void updateHeadPosition()
	{
		Position head = getHead();
		head.shiftPosition(currentDir);
	}
	/*
	 *  moveSnake:- Moves the snake on the game board
	 *  @return boolean: false if snake moves outside the game board; true if snake movement is
	 *  				 within the game board 
	 */
	public void move()
	{
		for(int i = snakeBody.size()-1; i >= 0; i--)
		{
			Position pos = snakeBody.get(i);
			//if head of snake
			if(i == 0)
			{
				updateHeadPosition();
			}
			else
			{
				Position newPos = snakeBody.get(i-1);
				pos.setX(newPos.getX());
				pos.setY(newPos.getY());
			}
		}
	}
	
	public boolean hasEatenItself()
	{
		Position head = getHead();
		for(int i = 1; i < snakeBody.size(); i++)
		{
			if(head.equals(snakeBody.get(i)))
			{
				return true;
			}
		}
		return false;
	}
	
	public void turnClockWise()
	{
		setDirection(currentDir.getCWDirection());
	}
	
	public void turnCounterClockWise()
	{
		setDirection(currentDir.getCCWDirection());
	}
}
