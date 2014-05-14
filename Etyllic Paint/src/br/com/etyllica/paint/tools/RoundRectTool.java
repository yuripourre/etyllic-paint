package br.com.etyllica.paint.tools;

import br.com.etyllica.core.graphics.Graphic;
import br.com.etyllica.gui.radio.RadioButton;

public class RoundRectTool extends DrawAreaTool {

	private int roundBorder = 16;
	
	public RoundRectTool(RadioButton drawRoundRectButton, RadioButton fillRoundRectButton) {
		super(drawRoundRectButton, fillRoundRectButton);
	}
	
	public void drawPermanent(int mx, int my) {

		int ix = startPointX;
		int difx = mx-startPointX;
		int iy = startPointY;
		int dify = my-startPointY;

		if(mx<startPointX) {
			ix = mx;
			difx =-difx;
		}

		if(my<startPointY) {
			iy = my;
			dify =-dify;
		}

		if(draw) {

			screen.setColor(undefinedColor);
			screen.drawRoundRect(ix-screenX, iy-screenY, difx, dify, roundBorder, roundBorder);

		} else {

			screen.setColor(anotherColor);
			screen.fillRoundRect(ix-screenX, iy-screenY, difx, dify, roundBorder, roundBorder);

			screen.setColor(undefinedColor);	
			screen.drawRoundRect(ix-screenX, iy-screenY, difx, dify, roundBorder, roundBorder);
		}

	}

	public void draw(int mx, int my, Graphic g) {

		if(!temporary) {
			return;
		}

		int difx = mx-startPointX;
		int dify = my-startPointY;

		int ix = startPointX;
		int iy = startPointY;

		boolean xflag = false;
		boolean yflag = false;

		//if(!shift) {

		if(difx<0) {
			ix = mx;
			//ix += difx;
			difx =-difx;
			xflag = true;
		}
		if(dify<0) {
			iy = my;
			//iy += dify;
			dify =-dify;
			yflag = true;
		}

		//}else{
		if(shift) {

			if(difx<dify) {

				difx += dify-difx;

				if(xflag) {
					ix += dify-difx;
				}

			}else if(dify<difx) {
				dify += difx-dify;
				if(yflag) {
					iy += difx-dify;
				}
			}

		}

		if(draw) {
			
			g.setColor(undefinedColor);
			g.drawRoundRect(ix, iy, difx, dify, roundBorder, roundBorder);
			
		} else {
			
			g.setColor(anotherColor);
			g.fillRoundRect(ix, iy, difx, dify, roundBorder, roundBorder);

			g.setColor(undefinedColor);	
			g.drawRoundRect(ix, iy, difx, dify, roundBorder, roundBorder);
			
		}

	}

}
