package com.aadil.snake;

public enum Direction {
	UP,
	RIGHT,
	DOWN,
	LEFT;
	
	public Direction getReverseDirection()
	{
		if(this.equals(UP))
			return DOWN;
		if(this.equals(RIGHT))
			return LEFT;
		if(this.equals(DOWN))
			return UP;
		if(this.equals(LEFT))
			return RIGHT;
		throw new Error("Cannot Determine Reverse Direction");
	}
	
	public Direction getCWDirection()
	{
		if(this.equals(UP))
			return RIGHT;
		if(this.equals(RIGHT))
			return DOWN;
		if(this.equals(DOWN))
			return LEFT;
		if(this.equals(LEFT))
			return UP;
		throw new Error("Cannot Determine ClockWise Direction");
	}
	
	public Direction getCCWDirection()
	{
		if(this.equals(UP))
			return LEFT;
		if(this.equals(RIGHT))
			return UP;
		if(this.equals(DOWN))
			return RIGHT;
		if(this.equals(LEFT))
			return DOWN;
		throw new Error("Cannot Determine CounterClockWise Direction");
	}
}
