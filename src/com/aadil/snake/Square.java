package com.aadil.snake;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;

public class Square {
	int left;
	int top;
	int right;
	int bottom;
	
	public Square(int _left, int _top, int _right, int _bottom)
	{
		left = _left;
		top = _top;
		right = _right;
		bottom = _bottom;
	}
	
	public Square(int _left, int _top, int _width)
	{
		left = _left;
		top = _top;
		right = left + _width;
		bottom = top + _width;
	}
	
	public int getLeft() {
		return left;
	}
	public void setLeft(int left) {
		this.left = left;
	}
	public int getTop() {
		return top;
	}
	public void setTop(int top) {
		this.top = top;
	}
	public int getRight() {
		return right;
	}
	public void setRight(int right) {
		this.right = right;
	}
	public int getBottom() {
		return bottom;
	}
	public void setBottom(int bottom) {
		this.bottom = bottom;
	}
	public int getWidth()
	{
		return right - left;
	}
	public int getHeight()
	{
		return bottom - top;
	}
	public void draw(Canvas canvas, Paint paint)
	{
		canvas.drawRect(left, top, right, bottom, paint);
	}
	public void draw(Canvas canvas)
	{
		Paint paint = new Paint();
		paint.setStyle(Style.STROKE);
		paint.setColor(Color.GREEN);
		canvas.drawRect(left, top, right, bottom, paint);
	}
}
