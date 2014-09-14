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
}
