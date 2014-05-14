package br.com.etyllica.paint.tools;

import br.com.etyllica.gui.radio.RadioButton;

public abstract class DrawAreaTool extends DrawingTool {

	protected RadioButton drawButton;

	protected RadioButton fillButton;
	
	public DrawAreaTool(RadioButton drawButton, RadioButton fillButton) {
		super();

		this.drawButton = drawButton;

		this.fillButton = fillButton;

	}
	
	@Override
	public void select() {

		drawButton.setVisible(true);

		fillButton.setVisible(true);

	}

	@Override
	public void deselect() {

		drawButton.setVisible(false);

		fillButton.setVisible(false);

	}

}
