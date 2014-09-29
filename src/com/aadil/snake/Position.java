package com.aadil.snake;

public class Position {
	int x;
	int y;
	
	public Position(int _x, int _y)
	{
		x = _x;
		y = _y;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	
	public Direction getRelativeDirection(Position pos)
	{
		if(pos.getY() == y)
		{
			if(pos.getX() > x)
			{
				return Direction.RIGHT;
			}
			else if(pos.getX() < x)
			{
				return Direction.LEFT;
			}
			else
			{
				throw new Error("These two positions are the same");
			}
		}
		else if(pos.getX() == x)
		{
			if(pos.getY() > y)
			{
				return Direction.DOWN;
			}
			else if(pos.getY() < y)
			{
				return Direction.UP;
			}
		}
		throw new Error("Two positions are not directly above or beside each other");
	}
	
	/* shiftPosition:- Shifts this position by 1 in the specified direction
	 * @params dir:- Direction of shift
	 */
	public void shiftPosition(Direction dir)
	{
		switch(dir)
		{
			case DOWN:
				y += 1;
				break;
			case LEFT:
				x -= 1;
				break;
			case RIGHT:
				x += 1;
				break;
			case UP:
				y -= 1;
				break;
			default:
				throw new Error("cannot determine Direction");
		}
	}
	
	public boolean equals(Position pos)
	{
		if(x == pos.getX() && y == pos.getY())
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	public String toString()
	{
		return "("+ x + ", " + y + ")";
	}
}
