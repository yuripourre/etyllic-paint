package br.com.etyllica.paint.tools;

import java.awt.Color;
import java.awt.Graphics2D;

public abstract class ToolImpl implements Tool {

	protected Graphics2D screen;
	
	protected int screenX = 0;
	
	protected int screenY = 0;
		
	protected Color undefinedColor;
	
	protected Color anotherColor;
	
	protected Color primaryColor;
	
	protected Color secundaryColor;
	
	protected boolean draw = true;
	
	protected boolean shift = true;
	
	protected boolean temporary = true;

	public ToolImpl() {
		super();				
	}
	
	public ToolImpl(Graphics2D screen, int screenX, int screenY) {
		super();
		
		configureScreen(screen, screenX, screenY);
		
	}
	
	public void configureScreen(Graphics2D screen, int screenX, int screenY) {
			
		this.screen = screen;
		
		this.screenX = screenX;
		
		this.screenY = screenY;
				
	}

	public void setPrimaryColor(Color primaryColor) {
		this.primaryColor = primaryColor;
	}

	public void setSecundaryColor(Color secundaryColor) {
		this.secundaryColor = secundaryColor;
	}

	public boolean isDraw() {
		return draw;
	}

	public void setDraw(boolean draw) {
		this.draw = draw;
	}

	public boolean isShift() {
		return shift;
	}

	public void setShift(boolean shift) {
		this.shift = shift;
	}

	public boolean isTemporary() {
		return temporary;
	}
			
}
