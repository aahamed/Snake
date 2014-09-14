package com.aadil.framework;

import com.aadil.framework.Graphics.PixmapFormat;

public interface Pixmap {
	public int getWidth();

    public int getHeight();

    public PixmapFormat getFormat();

    public void dispose();
}
