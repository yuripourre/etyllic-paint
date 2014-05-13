package br.com.etyllica.paint.tools;

import br.com.etyllica.core.event.PointerEvent;
import br.com.etyllica.core.input.mouse.MouseButton;

public abstract class DrawingTool extends ToolImpl {

	protected int startPointX = 0;

	protected int startPointY = 0;

	@Override
	public void handleEvent(PointerEvent event) {

		if(event.onButtonDown(MouseButton.MOUSE_BUTTON_LEFT)) {

			startPointX = event.getX();
			startPointY = event.getY();

			undefinedColor = primaryColor;
			anotherColor = secundaryColor;

			temporary = true;

		}

		if(event.onButtonDown(MouseButton.MOUSE_BUTTON_RIGHT)) {

			startPointX = event.getX();
			startPointY = event.getY();

			undefinedColor = secundaryColor;
			anotherColor = primaryColor;

			temporary = true;

		}

		if(event.onButtonUp(MouseButton.MOUSE_BUTTON_LEFT)||event.onButtonUp(MouseButton.MOUSE_BUTTON_RIGHT)) {

			drawPermanent(event.getX(), event.getY());

			temporary = false;

		}

	}
	
	public abstract void drawPermanent(int mx, int my);
	
}
