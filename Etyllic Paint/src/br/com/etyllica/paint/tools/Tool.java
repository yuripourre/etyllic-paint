package br.com.etyllica.paint.tools;

import java.awt.Color;
import java.awt.Graphics2D;

import br.com.etyllica.core.event.PointerEvent;
import br.com.etyllica.core.graphics.Graphic;

public interface Tool {
	
	public void select();
	
	public void deselect();
	
	public void configureScreen(Graphics2D screen, int screenX, int screenY);
	
	public void handleEvent(PointerEvent event);
	
	public boolean isTemporary();
	
	public void setDraw(boolean draw);
	
	public void setShift(boolean shift);
		
	public void setPrimaryColor(Color primaryColor);	

	public void setSecundaryColor(Color secundaryColor);
	
	public void draw(int mx, int my, Graphic g);
		
}
