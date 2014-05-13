package br.com.etyllica.paint;

import java.awt.Color;

import br.com.etyllica.context.Application;
import br.com.etyllica.core.event.Action;
import br.com.etyllica.core.event.GUIEvent;
import br.com.etyllica.gui.Button;
import br.com.etyllica.gui.label.ColorLabel;
import br.com.etyllica.paint.tools.Tool;
import br.com.etyllica.util.SVGColor;

public abstract class PaintInterface extends Application{
	
	public PaintInterface(int w, int h) {
		super(w, h);
	}
	
	protected Tool tool;

	//Color Buttons
	//UpperLine
	private Button blackButton;
	private Button grayButton;
	private Button maroonButton;
	private Button oliveButton;
	private Button greenButton;
	private Button tealButton;
	private Button navyButton;
	private Button purpleButton;
	private Button mossGreenButton;
	private Button richBlackButton;
	private Button skyBlueButton;
	private Button savageBlueButton;
	private Button lilacButton;
	private Button woodBrownButton;

	//Lower Line
	private Button whiteButton;
	private Button lightGrayButton;
	private Button redButton;
	private Button yellowButton;
	private Button limeButton;
	private Button cyanButton;
	private Button blueButton;
	private Button magentaButton;
	private Button eggCustardButton;
	private Button springGreenButton;
	private Button lightCyanButton;
	private Button lavendarButton;
	private Button darkRoseButton;
	private Button coralButton;
	
	protected Color primaryColor = Color.BLACK;
	protected Color secundaryColor = Color.WHITE;
	private Button primaryColorButton;
	private ColorLabel primaryColorLabel;
	private Button secundaryColorButton;
	private ColorLabel secundaryColorLabel;

	protected int colorToolBarH = 0;
	protected int colorToolBarY = 0;
	
	protected void loadInterface(int buttonSize){
		
		colorToolBarH = buttonSize*2-1;
		colorToolBarY = h-colorToolBarH;
		
		int colorButtonsX = 182;
		int colorButtonsY = h-colorToolBarH;

		blackButton = new Button(colorButtonsX+buttonSize*0+0, colorButtonsY, buttonSize,buttonSize);
		ColorLabel blackLabel = new ColorLabel(0,0,26,26);
		blackLabel.setColor(Color.BLACK);
		blackButton.setCenterLabel(blackLabel);
		blackButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new Action(this, "setPrimaryColor", blackLabel.getColor()));
		blackButton.addAction(GUIEvent.MOUSE_RIGHT_BUTTON_UP, new Action(this, "setSecundaryColor", blackLabel.getColor()));
		blackButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_DOUBLE_CLICK, new Action(this, "openColorPickerWindow"));
		add(blackButton);

		grayButton = new Button(colorButtonsX+buttonSize*1+1, colorButtonsY, buttonSize,buttonSize);
		ColorLabel grayLabel = new ColorLabel(0,0,26,26);
		grayLabel.setColor(SVGColor.GRAY);
		grayButton.setCenterLabel(grayLabel);
		grayButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new Action(this, "setPrimaryColor", grayLabel.getColor()));
		grayButton.addAction(GUIEvent.MOUSE_RIGHT_BUTTON_UP, new Action(this, "setSecundaryColor", grayLabel.getColor()));
		grayButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_DOUBLE_CLICK, new Action(this, "openColorPickerWindow"));
		add(grayButton);

		maroonButton = new Button(colorButtonsX+buttonSize*2+2, colorButtonsY, buttonSize,buttonSize);
		ColorLabel maroonLabel = new ColorLabel(0,0,26,26);
		maroonLabel.setColor(SVGColor.MAROON);
		maroonButton.setCenterLabel(maroonLabel);
		maroonButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new Action(this, "setPrimaryColor", maroonLabel.getColor()));
		maroonButton.addAction(GUIEvent.MOUSE_RIGHT_BUTTON_UP, new Action(this, "setSecundaryColor", maroonLabel.getColor()));
		maroonButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_DOUBLE_CLICK, new Action(this, "openColorPickerWindow"));
		add(maroonButton);

		oliveButton = new Button(colorButtonsX+buttonSize*3+3, colorButtonsY, buttonSize,buttonSize);
		ColorLabel oliveLabel = new ColorLabel(0,0,26,26);
		oliveLabel.setColor(SVGColor.OLIVE);
		oliveButton.setCenterLabel(oliveLabel);
		oliveButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new Action(this, "setPrimaryColor", oliveLabel.getColor()));
		oliveButton.addAction(GUIEvent.MOUSE_RIGHT_BUTTON_UP, new Action(this, "setSecundaryColor", oliveLabel.getColor()));
		oliveButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_DOUBLE_CLICK, new Action(this, "openColorPickerWindow"));
		add(oliveButton);

		greenButton = new Button(colorButtonsX+buttonSize*4+4, colorButtonsY, buttonSize,buttonSize);
		ColorLabel greenLabel = new ColorLabel(0,0,26,26);
		greenLabel.setColor(SVGColor.GREEN);
		greenButton.setCenterLabel(greenLabel);
		greenButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new Action(this, "setPrimaryColor", greenLabel.getColor()));
		greenButton.addAction(GUIEvent.MOUSE_RIGHT_BUTTON_UP, new Action(this, "setSecundaryColor", greenLabel.getColor()));
		greenButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_DOUBLE_CLICK, new Action(this, "openColorPickerWindow"));
		add(greenButton);

		tealButton = new Button(colorButtonsX+buttonSize*5+5, colorButtonsY, buttonSize,buttonSize);
		ColorLabel tealLabel = new ColorLabel(0,0,26,26);
		tealLabel.setColor(SVGColor.TEAL);
		tealButton.setCenterLabel(tealLabel);
		tealButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new Action(this, "setPrimaryColor", tealLabel.getColor()));
		tealButton.addAction(GUIEvent.MOUSE_RIGHT_BUTTON_UP, new Action(this, "setSecundaryColor", tealLabel.getColor()));
		tealButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_DOUBLE_CLICK, new Action(this, "openColorPickerWindow"));
		add(tealButton);

		navyButton = new Button(colorButtonsX+buttonSize*6+6, colorButtonsY, buttonSize,buttonSize);
		ColorLabel navyLabel = new ColorLabel(0,0,26,26);
		navyLabel.setColor(SVGColor.NAVY);
		navyButton.setCenterLabel(navyLabel);
		navyButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new Action(this, "setPrimaryColor", navyLabel.getColor()));
		navyButton.addAction(GUIEvent.MOUSE_RIGHT_BUTTON_UP, new Action(this, "setSecundaryColor", navyLabel.getColor()));
		navyButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_DOUBLE_CLICK, new Action(this, "openColorPickerWindow"));
		add(navyButton);

		purpleButton = new Button(colorButtonsX+buttonSize*7+7, colorButtonsY, buttonSize,buttonSize);
		ColorLabel purpleLabel = new ColorLabel(0,0,26,26);
		purpleLabel.setColor(SVGColor.PURPLE);
		purpleButton.setCenterLabel(purpleLabel);
		purpleButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new Action(this, "setPrimaryColor", purpleLabel.getColor()));
		purpleButton.addAction(GUIEvent.MOUSE_RIGHT_BUTTON_UP, new Action(this, "setSecundaryColor", purpleLabel.getColor()));
		purpleButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_DOUBLE_CLICK, new Action(this, "openColorPickerWindow"));
		add(purpleButton);

		mossGreenButton = new Button(colorButtonsX+buttonSize*8+8, colorButtonsY, buttonSize,buttonSize);
		ColorLabel mossGreenLabel = new ColorLabel(0,0,26,26);
		mossGreenLabel.setColor(SVGColor.MOSS_GREEN);
		mossGreenButton.setCenterLabel(mossGreenLabel);
		mossGreenButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new Action(this, "setPrimaryColor", mossGreenLabel.getColor()));
		mossGreenButton.addAction(GUIEvent.MOUSE_RIGHT_BUTTON_UP, new Action(this, "setSecundaryColor", mossGreenLabel.getColor()));
		mossGreenButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_DOUBLE_CLICK, new Action(this, "openColorPickerWindow"));
		add(mossGreenButton);

		richBlackButton = new Button(colorButtonsX+buttonSize*9+9, colorButtonsY, buttonSize,buttonSize);
		ColorLabel richBlackLabel = new ColorLabel(0,0,26,26);
		richBlackLabel.setColor(SVGColor.RICH_BLACK);
		richBlackButton.setCenterLabel(richBlackLabel);
		richBlackButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new Action(this, "setPrimaryColor", richBlackLabel.getColor()));
		richBlackButton.addAction(GUIEvent.MOUSE_RIGHT_BUTTON_UP, new Action(this, "setSecundaryColor", richBlackLabel.getColor()));
		richBlackButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_DOUBLE_CLICK, new Action(this, "openColorPickerWindow"));
		add(richBlackButton);

		skyBlueButton = new Button(colorButtonsX+buttonSize*10+10, colorButtonsY, buttonSize,buttonSize);
		ColorLabel skyBlueLabel = new ColorLabel(0,0,26,26);
		skyBlueLabel.setColor(SVGColor.SKY_BLUE);
		skyBlueButton.setCenterLabel(skyBlueLabel);
		skyBlueButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new Action(this, "setPrimaryColor", skyBlueLabel.getColor()));
		skyBlueButton.addAction(GUIEvent.MOUSE_RIGHT_BUTTON_UP, new Action(this, "setSecundaryColor", skyBlueLabel.getColor()));
		skyBlueButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_DOUBLE_CLICK, new Action(this, "openColorPickerWindow"));
		add(skyBlueButton);

		savageBlueButton = new Button(colorButtonsX+buttonSize*11+11, colorButtonsY, buttonSize,buttonSize);
		ColorLabel savageBlueLabel = new ColorLabel(0,0,26,26);
		savageBlueLabel.setColor(SVGColor.SAVAGE_BLUE);
		savageBlueButton.setCenterLabel(savageBlueLabel);
		savageBlueButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new Action(this, "setPrimaryColor", savageBlueLabel.getColor()));
		savageBlueButton.addAction(GUIEvent.MOUSE_RIGHT_BUTTON_UP, new Action(this, "setSecundaryColor", savageBlueLabel.getColor()));
		savageBlueButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_DOUBLE_CLICK, new Action(this, "openColorPickerWindow"));
		add(savageBlueButton);

		lilacButton = new Button(colorButtonsX+buttonSize*12+12, colorButtonsY, buttonSize,buttonSize);
		ColorLabel lilacLabel = new ColorLabel(0,0,26,26);
		lilacLabel.setColor(SVGColor.LILAC);
		lilacButton.setCenterLabel(lilacLabel);
		lilacButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new Action(this, "setPrimaryColor", lilacLabel.getColor()));
		lilacButton.addAction(GUIEvent.MOUSE_RIGHT_BUTTON_UP, new Action(this, "setSecundaryColor", lilacLabel.getColor()));
		lilacButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_DOUBLE_CLICK, new Action(this, "openColorPickerWindow"));
		add(lilacButton);

		woodBrownButton = new Button(colorButtonsX+buttonSize*13+13, colorButtonsY, buttonSize,buttonSize);
		ColorLabel woodBrownLabel = new ColorLabel(0,0,26,26);
		woodBrownLabel.setColor(SVGColor.WOOD_BROWN);
		woodBrownButton.setCenterLabel(woodBrownLabel);
		woodBrownButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new Action(this, "setPrimaryColor", woodBrownLabel.getColor()));
		woodBrownButton.addAction(GUIEvent.MOUSE_RIGHT_BUTTON_UP, new Action(this, "setSecundaryColor", woodBrownLabel.getColor()));
		woodBrownButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_DOUBLE_CLICK, new Action(this, "openColorPickerWindow"));
		add(woodBrownButton);


		//Lower Line
		whiteButton = new Button(colorButtonsX+buttonSize*0+0, colorButtonsY+buttonSize+1, buttonSize,buttonSize);
		ColorLabel whiteLabel = new ColorLabel(0,0,26,26);
		whiteLabel.setColor(Color.WHITE);
		whiteButton.setCenterLabel(whiteLabel);
		whiteButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new Action(this, "setPrimaryColor", whiteLabel.getColor()));
		whiteButton.addAction(GUIEvent.MOUSE_RIGHT_BUTTON_UP, new Action(this, "setSecundaryColor", whiteLabel.getColor()));
		whiteButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_DOUBLE_CLICK, new Action(this, "openColorPickerWindow"));
		add(whiteButton);

		lightGrayButton = new Button(colorButtonsX+buttonSize*1+1, colorButtonsY+buttonSize+1, buttonSize,buttonSize);
		ColorLabel lightGrayLabel = new ColorLabel(0,0,26,26);
		lightGrayLabel.setColor(new Color(0xC0,0xC0,0xC0));
		lightGrayButton.setCenterLabel(lightGrayLabel);
		lightGrayButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new Action(this, "setPrimaryColor", lightGrayLabel.getColor()));
		lightGrayButton.addAction(GUIEvent.MOUSE_RIGHT_BUTTON_UP, new Action(this, "setSecundaryColor", lightGrayLabel.getColor()));
		lightGrayButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_DOUBLE_CLICK, new Action(this, "openColorPickerWindow"));
		add(lightGrayButton);

		redButton = new Button(colorButtonsX+buttonSize*2+2, colorButtonsY+buttonSize+1, buttonSize,buttonSize);
		ColorLabel redLabel = new ColorLabel(0,0,26,26);
		redLabel.setColor(Color.RED);
		redButton.setCenterLabel(redLabel);
		redButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new Action(this, "setPrimaryColor", redLabel.getColor()));
		redButton.addAction(GUIEvent.MOUSE_RIGHT_BUTTON_UP, new Action(this, "setSecundaryColor", redLabel.getColor()));
		redButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_DOUBLE_CLICK, new Action(this, "openColorPickerWindow"));
		add(redButton);

		yellowButton = new Button(colorButtonsX+buttonSize*3+3, colorButtonsY+buttonSize+1, buttonSize,buttonSize);
		ColorLabel yellowLabel = new ColorLabel(0,0,26,26);
		yellowLabel.setColor(Color.YELLOW);
		yellowButton.setCenterLabel(yellowLabel);
		yellowButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new Action(this, "setPrimaryColor", yellowLabel.getColor()));
		yellowButton.addAction(GUIEvent.MOUSE_RIGHT_BUTTON_UP, new Action(this, "setSecundaryColor", yellowLabel.getColor()));
		yellowButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_DOUBLE_CLICK, new Action(this, "openColorPickerWindow"));
		add(yellowButton);

		limeButton = new Button(colorButtonsX+buttonSize*4+4, colorButtonsY+buttonSize+1, buttonSize,buttonSize);
		ColorLabel limeLabel = new ColorLabel(0,0,26,26);
		limeLabel.setColor(SVGColor.LIME);
		limeButton.setCenterLabel(limeLabel);
		limeButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new Action(this, "setPrimaryColor", limeLabel.getColor()));
		limeButton.addAction(GUIEvent.MOUSE_RIGHT_BUTTON_UP, new Action(this, "setSecundaryColor", limeLabel.getColor()));
		limeButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_DOUBLE_CLICK, new Action(this, "openColorPickerWindow"));
		add(limeButton);

		cyanButton = new Button(colorButtonsX+buttonSize*5+5, colorButtonsY+buttonSize+1, buttonSize,buttonSize);
		ColorLabel cyanLabel = new ColorLabel(0,0,26,26);
		cyanLabel.setColor(SVGColor.CYAN);
		cyanButton.setCenterLabel(cyanLabel);
		cyanButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new Action(this, "setPrimaryColor", cyanLabel.getColor()));
		cyanButton.addAction(GUIEvent.MOUSE_RIGHT_BUTTON_UP, new Action(this, "setSecundaryColor", cyanLabel.getColor()));
		cyanButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_DOUBLE_CLICK, new Action(this, "openColorPickerWindow"));
		add(cyanButton);

		blueButton = new Button(colorButtonsX+buttonSize*6+6, colorButtonsY+buttonSize+1, buttonSize,buttonSize);
		ColorLabel blueLabel = new ColorLabel(0,0,26,26);
		blueLabel.setColor(Color.BLUE);
		blueButton.setCenterLabel(blueLabel);
		blueButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new Action(this, "setPrimaryColor", blueLabel.getColor()));
		blueButton.addAction(GUIEvent.MOUSE_RIGHT_BUTTON_UP, new Action(this, "setSecundaryColor", blueLabel.getColor()));
		blueButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_DOUBLE_CLICK, new Action(this, "openColorPickerWindow"));
		add(blueButton);

		magentaButton = new Button(colorButtonsX+buttonSize*7+7, colorButtonsY+buttonSize+1, buttonSize,buttonSize);
		ColorLabel magentaLabel = new ColorLabel(0,0,26,26);
		magentaLabel.setColor(Color.MAGENTA);
		magentaButton.setCenterLabel(magentaLabel);
		magentaButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new Action(this, "setPrimaryColor", magentaLabel.getColor()));
		magentaButton.addAction(GUIEvent.MOUSE_RIGHT_BUTTON_UP, new Action(this, "setSecundaryColor", magentaLabel.getColor()));
		magentaButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_DOUBLE_CLICK, new Action(this, "openColorPickerWindow"));
		add(magentaButton);

		eggCustardButton = new Button(colorButtonsX+buttonSize*8+8, colorButtonsY+buttonSize+1, buttonSize,buttonSize);
		ColorLabel eggCustardLabel = new ColorLabel(0,0,26,26);
		eggCustardLabel.setColor(SVGColor.EGG_CUSTARD);
		eggCustardButton.setCenterLabel(eggCustardLabel);
		eggCustardButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new Action(this, "setPrimaryColor", eggCustardLabel.getColor()));
		eggCustardButton.addAction(GUIEvent.MOUSE_RIGHT_BUTTON_UP, new Action(this, "setSecundaryColor", eggCustardLabel.getColor()));
		eggCustardButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_DOUBLE_CLICK, new Action(this, "openColorPickerWindow"));
		add(eggCustardButton);

		springGreenButton = new Button(colorButtonsX+buttonSize*9+9, colorButtonsY+buttonSize+1, buttonSize,buttonSize);
		ColorLabel springGreenLabel = new ColorLabel(0,0,26,26);
		springGreenLabel.setColor(SVGColor.SPRING_GREEN);
		springGreenButton.setCenterLabel(springGreenLabel);
		springGreenButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new Action(this, "setPrimaryColor", springGreenLabel.getColor()));
		springGreenButton.addAction(GUIEvent.MOUSE_RIGHT_BUTTON_UP, new Action(this, "setSecundaryColor", springGreenLabel.getColor()));
		springGreenButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_DOUBLE_CLICK, new Action(this, "openColorPickerWindow"));
		add(springGreenButton);

		lightCyanButton = new Button(colorButtonsX+buttonSize*10+10, colorButtonsY+buttonSize+1, buttonSize,buttonSize);
		ColorLabel lightCyanLabel = new ColorLabel(0,0,26,26);
		lightCyanLabel.setColor(SVGColor.LIGHT_CYAN);
		lightCyanButton.setCenterLabel(lightCyanLabel);
		lightCyanButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new Action(this, "setPrimaryColor", lightCyanLabel.getColor()));
		lightCyanButton.addAction(GUIEvent.MOUSE_RIGHT_BUTTON_UP, new Action(this, "setSecundaryColor", lightCyanLabel.getColor()));
		lightCyanButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_DOUBLE_CLICK, new Action(this, "openColorPickerWindow"));
		add(lightCyanButton);

		lavendarButton = new Button(colorButtonsX+buttonSize*11+11, colorButtonsY+buttonSize+1, buttonSize,buttonSize);
		ColorLabel lavendarLabel = new ColorLabel(0,0,26,26);
		lavendarLabel.setColor(SVGColor.LAVENDAR);
		lavendarButton.setCenterLabel(lavendarLabel);
		lavendarButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new Action(this, "setPrimaryColor", lavendarLabel.getColor()));
		lavendarButton.addAction(GUIEvent.MOUSE_RIGHT_BUTTON_UP, new Action(this, "setSecundaryColor", lavendarLabel.getColor()));
		lavendarButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_DOUBLE_CLICK, new Action(this, "openColorPickerWindow"));
		add(lavendarButton);

		darkRoseButton = new Button(colorButtonsX+buttonSize*12+12, colorButtonsY+buttonSize+1, buttonSize,buttonSize);
		ColorLabel darkRoseLabel = new ColorLabel(0,0,26,26);
		darkRoseLabel.setColor(SVGColor.DARK_ROSE);
		darkRoseButton.setCenterLabel(darkRoseLabel);
		darkRoseButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new Action(this, "setPrimaryColor", darkRoseLabel.getColor()));
		darkRoseButton.addAction(GUIEvent.MOUSE_RIGHT_BUTTON_UP, new Action(this, "setSecundaryColor", darkRoseLabel.getColor()));
		darkRoseButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_DOUBLE_CLICK, new Action(this, "openColorPickerWindow"));
		add(darkRoseButton);

		coralButton = new Button(colorButtonsX+buttonSize*13+13, colorButtonsY+buttonSize+1, buttonSize,buttonSize);
		ColorLabel coralLabel = new ColorLabel(0,0,26,26);
		coralLabel.setColor(SVGColor.CORAL);
		coralButton.setCenterLabel(coralLabel);
		coralButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new Action(this, "setPrimaryColor", coralLabel.getColor()));
		coralButton.addAction(GUIEvent.MOUSE_RIGHT_BUTTON_UP, new Action(this, "setSecundaryColor", coralLabel.getColor()));
		coralButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_DOUBLE_CLICK, new Action(this, "openColorPickerWindow"));
		add(coralButton);
				
		secundaryColorButton = new Button(colorButtonsX+2-buttonSize-buttonSize/4, colorButtonsY+buttonSize/2+14, buttonSize+buttonSize/6,buttonSize-buttonSize/6);
		secundaryColorLabel = new ColorLabel(0,0,secundaryColorButton.getW()-2,secundaryColorButton.getH()-2);
		secundaryColorLabel.setColor(secundaryColor);
		secundaryColorButton.setCenterLabel(secundaryColorLabel);
		add(secundaryColorButton);
		
		primaryColorButton = new Button(secundaryColorButton.getX()-buttonSize/2, secundaryColorButton.getY()-buttonSize/2, secundaryColorButton.getW(),secundaryColorButton.getH());
		primaryColorLabel = new ColorLabel(0,0,primaryColorButton.getW()-2,primaryColorButton.getH()-2);
		primaryColorLabel.setColor(primaryColor);
		primaryColorButton.setCenterLabel(primaryColorLabel);
		add(primaryColorButton);
		
	}
	
	public void setPrimaryColor(Color color) {
		
		System.out.println("primaryColor");
		
		this.primaryColor = color;
		primaryColorLabel.setColor(color);
		
		tool.setPrimaryColor(primaryColor);
		
	}

	public void setSecundaryColor(Color color) {
		this.secundaryColor = color;
		
		secundaryColorLabel.setColor(color);
		
		tool.setSecundaryColor(secundaryColor);
	}
	
}
