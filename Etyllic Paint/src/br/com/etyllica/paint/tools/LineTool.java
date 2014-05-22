package br.com.etyllica.paint.tools;

import br.com.etyllica.context.Scene;
import br.com.etyllica.core.graphics.Graphic;

public class LineTool extends DrawingTool implements Tool {

	private int size = 1;

	public LineTool() {
		super();
	}

	@Override
	public void select() {
		//Show line size  buttons
	}

	@Override
	public void deselect() {
		//Hide line size  buttons
	}

	@Override
	public void draw(int mx, int my, Graphic g) {

		//setStrokeSize

		g.setColor(undefinedColor);

		if(shift) {

			int mydif = my-startPointY;
			int mxdif = mx-startPointX;

			if(my<startPointY) {
				mydif = startPointY-my;
			}

			if(mx<startPointX) {
				mxdif = startPointX-mx;	
			}

			if(mxdif>mydif) {
				my = startPointY;
			}else if(mydif>mxdif) {
				mx = startPointX;
			}

		}

		g.drawLine(startPointX, startPointY, mx, my);
		
	}

	@Override
	public void drawPermanent(int mx, int my) {
		
		screen.setColor(undefinedColor);
		
		if(shift) {

			int mydif = my-startPointY;
			int mxdif = mx-startPointX;

			if(my<startPointY) {
				mydif = startPointY-my;
			}

			if(mx<startPointX) {
				mxdif = startPointX-mx;	
			}

			if(mxdif>mydif) {
				my = startPointY;
			}else if(mydif>mxdif) {
				mx = startPointX;
			}

		}

		screen.drawLine(startPointX-screenX, startPointY-screenY, mx-screenX, my-screenY);
		
	}

}
