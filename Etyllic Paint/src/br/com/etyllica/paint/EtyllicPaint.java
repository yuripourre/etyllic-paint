package br.com.etyllica.paint;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import br.com.etyllica.core.application.Application;
import br.com.etyllica.core.control.mouse.MouseButton;
import br.com.etyllica.core.event.GUIAction;
import br.com.etyllica.core.event.GUIEvent;
import br.com.etyllica.core.event.KeyState;
import br.com.etyllica.core.event.KeyboardEvent;
import br.com.etyllica.core.event.PointerEvent;
import br.com.etyllica.core.event.Tecla;
import br.com.etyllica.core.video.Grafico;
import br.com.etyllica.gui.Button;
import br.com.etyllica.gui.Panel;
import br.com.etyllica.gui.RadioGroup;
import br.com.etyllica.gui.icon.ImageIcon;
import br.com.etyllica.gui.label.ColorLabel;
import br.com.etyllica.gui.radio.CheckButtonRadio;
import br.com.etyllica.util.SVGColor;

public class EtyllicPaint extends Application{

	public EtyllicPaint(int w, int h) {
		super(w, h);
	}

	//private final int UNDEFINED = -1;
	private boolean undefined = false;

	private BufferedImage screen;

	private Graphics2D screenGraphics;

	private PaintMode mode = PaintMode.RECTANGULAR_MARK;

	//Mouse Position
	private int mx = 0;
	private int my = 0;

	private int startScreenX = 98;
	private int startScreenY = 0;

	private int screenW = 0;
	private int screenH = 0;

	//private MenuBar menu;

	//private ToolBar toolBar;

	private Panel panel;

	private CheckButtonRadio freeMark;
	private CheckButtonRadio rectangularMark;

	private CheckButtonRadio eraser;
	private CheckButtonRadio paintCan;

	private CheckButtonRadio dropper;
	private CheckButtonRadio magnify;

	private CheckButtonRadio pencil;
	private CheckButtonRadio brush;

	private CheckButtonRadio airBrush;
	private CheckButtonRadio text;

	private CheckButtonRadio line;
	private CheckButtonRadio curve;

	private CheckButtonRadio rect;
	private CheckButtonRadio poly;

	private CheckButtonRadio oval;
	private CheckButtonRadio round;

	private Color primaryColor = Color.BLACK;
	private Color secundaryColor = Color.WHITE;
	private Color undefinedColor = Color.BLACK;

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


	private int colorToolBarH = 0;
	private int colorToolBarY = 0;

	private int zoom = 0;

	@Override
	public void load() {

		int toolBarX = 0;
		int toolBarY = 0;
		int toolBarColumns = 2;
		int buttonSize = 48;
		colorToolBarH = buttonSize*2-1;
		colorToolBarY = h-colorToolBarH;

		loadingPhrase = "Loading Panel...";
		panel = new Panel(toolBarX,toolBarY,startScreenX,h);
		add(panel);

		RadioGroup toolbarGroup = new RadioGroup();

		loadingPhrase = "Loading Resources...";

		freeMark = new CheckButtonRadio(toolBarX,toolBarY,buttonSize,buttonSize);
		freeMark.setLabel(new ImageIcon(8, 8, "freemark.png"));
		freeMark.setAlt("Free Form Select");
		toolbarGroup.add(freeMark);
		freeMark.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new GUIAction(this, "setMode", PaintMode.FREE_MARK));
		add(freeMark);

		rectangularMark = new CheckButtonRadio(toolBarX+buttonSize+1,toolBarY,buttonSize,buttonSize);
		rectangularMark.setLabel(new ImageIcon(8, 8, "rectmark.png"));
		toolbarGroup.add(rectangularMark);
		rectangularMark.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new GUIAction(this, "setMode", PaintMode.RECTANGULAR_MARK));
		add(rectangularMark);

		eraser = new CheckButtonRadio(toolBarX,toolBarY+buttonSize*1+1,buttonSize,buttonSize);
		//From http://findicons.com/icon/441226/eraser?id=449660
		eraser.setLabel(new ImageIcon(8, 8, "eraser.png"));
		eraser.setAlt("Eraser/Color Eraser");
		eraser.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new GUIAction(this, "setMode", PaintMode.ERASER));
		toolbarGroup.add(eraser);
		add(eraser);


		paintCan = new CheckButtonRadio(toolBarX+buttonSize+1,toolBarY+buttonSize*1+1,buttonSize,buttonSize);
		//http://www.softicons.com/free-icons/toolbar-icons/some-icons-by-scardi-shek/paint-icon
		paintCan.setLabel(new ImageIcon(8, 8, "paint.png"));
		paintCan.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new GUIAction(this, "setMode", PaintMode.PAINT_CAN));
		toolbarGroup.add(paintCan);
		add(paintCan);


		dropper = new CheckButtonRadio(toolBarX,toolBarY+buttonSize*2+2,buttonSize,buttonSize);
		//http://findicons.com/icon/238374/color_picker?id=238374
		dropper.setLabel(new ImageIcon(8, 8, "dropper.png"));
		dropper.setAlt("Pick Color");
		dropper.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new GUIAction(this, "setMode", PaintMode.DROPPER));
		toolbarGroup.add(dropper);
		add(dropper);

		magnify = new CheckButtonRadio(toolBarX+buttonSize+1,toolBarY+buttonSize*2+2,buttonSize,buttonSize);
		//http://findicons.com/icon/219105/find?id=356081
		magnify.setLabel(new ImageIcon(8, 8, "magnify.png"));
		magnify.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new GUIAction(this, "setMode", PaintMode.ZOOM));
		toolbarGroup.add(magnify);
		add(magnify);


		pencil = new CheckButtonRadio(toolBarX,toolBarY+buttonSize*3+3,buttonSize,buttonSize);
		//http://findicons.com/icon/226814/pencil?width=32
		pencil.setLabel(new ImageIcon(8, 8, "pencil.png"));
		pencil.setAlt("Pencil");
		pencil.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new GUIAction(this, "setMode", PaintMode.PENCIL));
		toolbarGroup.add(pencil);
		add(pencil);

		brush = new CheckButtonRadio(toolBarX+buttonSize+1,toolBarY+buttonSize*3+3,buttonSize,buttonSize);
		//http://findicons.com/icon/237997/paintbrush?width=32
		brush.setLabel(new ImageIcon(8, 8, "paintbrush.png"));
		brush.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new GUIAction(this, "setMode", PaintMode.PAINT_BRUSH));
		toolbarGroup.add(brush);
		add(brush);

		airBrush = new CheckButtonRadio(toolBarX,toolBarY+buttonSize*4+4,buttonSize,buttonSize);
		//http://www.keywordpictures.com/abuse/spray%20can%20icon///
		airBrush.setLabel(new ImageIcon(8, 8, "spray_can.png"));
		airBrush.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new GUIAction(this, "setMode", PaintMode.SPRAY));
		toolbarGroup.add(airBrush);
		add(airBrush);

		text = new CheckButtonRadio(toolBarX+buttonSize+1,toolBarY+buttonSize*4+4,buttonSize,buttonSize);
		text.setLabel(new ImageIcon(8, 8, "text.png"));
		text.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new GUIAction(this, "setMode", PaintMode.TEXT));
		toolbarGroup.add(text);
		add(text);

		line = new CheckButtonRadio(toolBarX,toolBarY+buttonSize*5+5,buttonSize,buttonSize);
		line.setLabel(new ImageIcon(8, 8, "line.png"));
		line.setAlt("Line");
		line.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new GUIAction(this, "setMode", PaintMode.DRAW_LINE));
		toolbarGroup.add(line);
		add(line);

		curve = new CheckButtonRadio(toolBarX+buttonSize+1,toolBarY+buttonSize*5+5,buttonSize,buttonSize);
		curve.setLabel(new ImageIcon(8, 8, "curve.png"));
		curve.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new GUIAction(this, "setMode", PaintMode.DRAW_CURVE));
		toolbarGroup.add(curve);
		add(curve);

		rect = new CheckButtonRadio(toolBarX,toolBarY+buttonSize*6+6,buttonSize,buttonSize);
		rect.setLabel(new ImageIcon(8, 8, "rect.png"));
		rect.setAlt("Rectangle");
		rect.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new GUIAction(this, "setMode", PaintMode.DRAW_RECT));
		toolbarGroup.add(rect);
		add(rect);

		poly = new CheckButtonRadio(toolBarX+buttonSize+1,toolBarY+buttonSize*6+6,buttonSize,buttonSize);
		poly.setLabel(new ImageIcon(8, 8, "poly.png"));
		poly.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new GUIAction(this, "setMode", PaintMode.DRAW_POLY));
		toolbarGroup.add(poly);
		add(poly);

		oval = new CheckButtonRadio(toolBarX,toolBarY+buttonSize*7+7,buttonSize,buttonSize);
		oval.setLabel(new ImageIcon(8, 8, "oval.png"));
		oval.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new GUIAction(this, "setMode", PaintMode.DRAW_OVAL));
		toolbarGroup.add(oval);
		add(oval);

		round = new CheckButtonRadio(toolBarX+buttonSize+1,toolBarY+buttonSize*7+7,buttonSize,buttonSize);
		round.setLabel(new ImageIcon(8, 8, "round.png"));
		round.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new GUIAction(this, "setMode", PaintMode.DRAW_ROUND));
		toolbarGroup.add(round);
		add(round);

		loading = 70;

		loadingPhrase = "Loading Colors...";

		createButtons(buttonSize);

		loading = 90;

		loadingPhrase = "Loading Screen...";


		createScreen();

		setMode(PaintMode.RECTANGULAR_MARK);
		rectangularMark.mark();

		loading = 100;

	}

	private void createButtons(int buttonSize){

		int colorButtonsX = 180;
		int colorButtonsY = h-colorToolBarH;

		blackButton = new Button(colorButtonsX+buttonSize*0+0, colorButtonsY, buttonSize,buttonSize);
		ColorLabel blackLabel = new ColorLabel(0,0,26,26);
		blackLabel.setColor(Color.BLACK);
		blackButton.setCenterLabel(blackLabel);
		blackButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new GUIAction(this, "setPrimaryColor", blackLabel.getColor()));
		blackButton.addAction(GUIEvent.MOUSE_RIGHT_BUTTON_UP, new GUIAction(this, "setSecundaryColor", blackLabel.getColor()));
		blackButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_DOUBLE_CLICK, new GUIAction(this, "openColorPickerWindow"));
		add(blackButton);

		grayButton = new Button(colorButtonsX+buttonSize*1+1, colorButtonsY, buttonSize,buttonSize);
		ColorLabel grayLabel = new ColorLabel(0,0,26,26);
		grayLabel.setColor(SVGColor.GRAY);
		grayButton.setCenterLabel(grayLabel);
		grayButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new GUIAction(this, "setPrimaryColor", grayLabel.getColor()));
		grayButton.addAction(GUIEvent.MOUSE_RIGHT_BUTTON_UP, new GUIAction(this, "setSecundaryColor", grayLabel.getColor()));
		grayButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_DOUBLE_CLICK, new GUIAction(this, "openColorPickerWindow"));
		add(grayButton);

		maroonButton = new Button(colorButtonsX+buttonSize*2+2, colorButtonsY, buttonSize,buttonSize);
		ColorLabel maroonLabel = new ColorLabel(0,0,26,26);
		maroonLabel.setColor(SVGColor.MAROON);
		maroonButton.setCenterLabel(maroonLabel);
		maroonButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new GUIAction(this, "setPrimaryColor", maroonLabel.getColor()));
		maroonButton.addAction(GUIEvent.MOUSE_RIGHT_BUTTON_UP, new GUIAction(this, "setSecundaryColor", maroonLabel.getColor()));
		maroonButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_DOUBLE_CLICK, new GUIAction(this, "openColorPickerWindow"));
		add(maroonButton);

		oliveButton = new Button(colorButtonsX+buttonSize*3+3, colorButtonsY, buttonSize,buttonSize);
		ColorLabel oliveLabel = new ColorLabel(0,0,26,26);
		oliveLabel.setColor(SVGColor.OLIVE);
		oliveButton.setCenterLabel(oliveLabel);
		oliveButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new GUIAction(this, "setPrimaryColor", oliveLabel.getColor()));
		oliveButton.addAction(GUIEvent.MOUSE_RIGHT_BUTTON_UP, new GUIAction(this, "setSecundaryColor", oliveLabel.getColor()));
		oliveButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_DOUBLE_CLICK, new GUIAction(this, "openColorPickerWindow"));
		add(oliveButton);

		greenButton = new Button(colorButtonsX+buttonSize*4+4, colorButtonsY, buttonSize,buttonSize);
		ColorLabel greenLabel = new ColorLabel(0,0,26,26);
		greenLabel.setColor(SVGColor.GREEN);
		greenButton.setCenterLabel(greenLabel);
		greenButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new GUIAction(this, "setPrimaryColor", greenLabel.getColor()));
		greenButton.addAction(GUIEvent.MOUSE_RIGHT_BUTTON_UP, new GUIAction(this, "setSecundaryColor", greenLabel.getColor()));
		greenButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_DOUBLE_CLICK, new GUIAction(this, "openColorPickerWindow"));
		add(greenButton);

		tealButton = new Button(colorButtonsX+buttonSize*5+5, colorButtonsY, buttonSize,buttonSize);
		ColorLabel tealLabel = new ColorLabel(0,0,26,26);
		tealLabel.setColor(SVGColor.TEAL);
		tealButton.setCenterLabel(tealLabel);
		tealButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new GUIAction(this, "setPrimaryColor", tealLabel.getColor()));
		tealButton.addAction(GUIEvent.MOUSE_RIGHT_BUTTON_UP, new GUIAction(this, "setSecundaryColor", tealLabel.getColor()));
		tealButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_DOUBLE_CLICK, new GUIAction(this, "openColorPickerWindow"));
		add(tealButton);

		navyButton = new Button(colorButtonsX+buttonSize*6+6, colorButtonsY, buttonSize,buttonSize);
		ColorLabel navyLabel = new ColorLabel(0,0,26,26);
		navyLabel.setColor(SVGColor.NAVY);
		navyButton.setCenterLabel(navyLabel);
		navyButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new GUIAction(this, "setPrimaryColor", navyLabel.getColor()));
		navyButton.addAction(GUIEvent.MOUSE_RIGHT_BUTTON_UP, new GUIAction(this, "setSecundaryColor", navyLabel.getColor()));
		navyButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_DOUBLE_CLICK, new GUIAction(this, "openColorPickerWindow"));
		add(navyButton);

		purpleButton = new Button(colorButtonsX+buttonSize*7+7, colorButtonsY, buttonSize,buttonSize);
		ColorLabel purpleLabel = new ColorLabel(0,0,26,26);
		purpleLabel.setColor(SVGColor.PURPLE);
		purpleButton.setCenterLabel(purpleLabel);
		purpleButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new GUIAction(this, "setPrimaryColor", purpleLabel.getColor()));
		purpleButton.addAction(GUIEvent.MOUSE_RIGHT_BUTTON_UP, new GUIAction(this, "setSecundaryColor", purpleLabel.getColor()));
		purpleButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_DOUBLE_CLICK, new GUIAction(this, "openColorPickerWindow"));
		add(purpleButton);

		mossGreenButton = new Button(colorButtonsX+buttonSize*8+8, colorButtonsY, buttonSize,buttonSize);
		ColorLabel mossGreenLabel = new ColorLabel(0,0,26,26);
		mossGreenLabel.setColor(SVGColor.MOSS_GREEN);
		mossGreenButton.setCenterLabel(mossGreenLabel);
		mossGreenButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new GUIAction(this, "setPrimaryColor", mossGreenLabel.getColor()));
		mossGreenButton.addAction(GUIEvent.MOUSE_RIGHT_BUTTON_UP, new GUIAction(this, "setSecundaryColor", mossGreenLabel.getColor()));
		mossGreenButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_DOUBLE_CLICK, new GUIAction(this, "openColorPickerWindow"));
		add(mossGreenButton);

		richBlackButton = new Button(colorButtonsX+buttonSize*9+9, colorButtonsY, buttonSize,buttonSize);
		ColorLabel richBlackLabel = new ColorLabel(0,0,26,26);
		richBlackLabel.setColor(SVGColor.RICH_BLACK);
		richBlackButton.setCenterLabel(richBlackLabel);
		richBlackButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new GUIAction(this, "setPrimaryColor", richBlackLabel.getColor()));
		richBlackButton.addAction(GUIEvent.MOUSE_RIGHT_BUTTON_UP, new GUIAction(this, "setSecundaryColor", richBlackLabel.getColor()));
		richBlackButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_DOUBLE_CLICK, new GUIAction(this, "openColorPickerWindow"));
		add(richBlackButton);

		skyBlueButton = new Button(colorButtonsX+buttonSize*10+10, colorButtonsY, buttonSize,buttonSize);
		ColorLabel skyBlueLabel = new ColorLabel(0,0,26,26);
		skyBlueLabel.setColor(SVGColor.SKY_BLUE);
		skyBlueButton.setCenterLabel(skyBlueLabel);
		skyBlueButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new GUIAction(this, "setPrimaryColor", skyBlueLabel.getColor()));
		skyBlueButton.addAction(GUIEvent.MOUSE_RIGHT_BUTTON_UP, new GUIAction(this, "setSecundaryColor", skyBlueLabel.getColor()));
		skyBlueButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_DOUBLE_CLICK, new GUIAction(this, "openColorPickerWindow"));
		add(skyBlueButton);

		savageBlueButton = new Button(colorButtonsX+buttonSize*11+11, colorButtonsY, buttonSize,buttonSize);
		ColorLabel savageBlueLabel = new ColorLabel(0,0,26,26);
		savageBlueLabel.setColor(SVGColor.SAVAGE_BLUE);
		savageBlueButton.setCenterLabel(savageBlueLabel);
		savageBlueButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new GUIAction(this, "setPrimaryColor", savageBlueLabel.getColor()));
		savageBlueButton.addAction(GUIEvent.MOUSE_RIGHT_BUTTON_UP, new GUIAction(this, "setSecundaryColor", savageBlueLabel.getColor()));
		savageBlueButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_DOUBLE_CLICK, new GUIAction(this, "openColorPickerWindow"));
		add(savageBlueButton);

		lilacButton = new Button(colorButtonsX+buttonSize*12+12, colorButtonsY, buttonSize,buttonSize);
		ColorLabel lilacLabel = new ColorLabel(0,0,26,26);
		lilacLabel.setColor(SVGColor.LILAC);
		lilacButton.setCenterLabel(lilacLabel);
		lilacButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new GUIAction(this, "setPrimaryColor", lilacLabel.getColor()));
		lilacButton.addAction(GUIEvent.MOUSE_RIGHT_BUTTON_UP, new GUIAction(this, "setSecundaryColor", lilacLabel.getColor()));
		lilacButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_DOUBLE_CLICK, new GUIAction(this, "openColorPickerWindow"));
		add(lilacButton);

		woodBrownButton = new Button(colorButtonsX+buttonSize*13+13, colorButtonsY, buttonSize,buttonSize);
		ColorLabel woodBrownLabel = new ColorLabel(0,0,26,26);
		woodBrownLabel.setColor(SVGColor.WOOD_BROWN);
		woodBrownButton.setCenterLabel(woodBrownLabel);
		woodBrownButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new GUIAction(this, "setPrimaryColor", woodBrownLabel.getColor()));
		woodBrownButton.addAction(GUIEvent.MOUSE_RIGHT_BUTTON_UP, new GUIAction(this, "setSecundaryColor", woodBrownLabel.getColor()));
		woodBrownButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_DOUBLE_CLICK, new GUIAction(this, "openColorPickerWindow"));
		add(woodBrownButton);


		//Lower Line
		whiteButton = new Button(colorButtonsX+buttonSize*0+0, colorButtonsY+buttonSize+1, buttonSize,buttonSize);
		ColorLabel whiteLabel = new ColorLabel(0,0,26,26);
		whiteLabel.setColor(Color.WHITE);
		whiteButton.setCenterLabel(whiteLabel);
		whiteButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new GUIAction(this, "setPrimaryColor", whiteLabel.getColor()));
		whiteButton.addAction(GUIEvent.MOUSE_RIGHT_BUTTON_UP, new GUIAction(this, "setSecundaryColor", whiteLabel.getColor()));
		whiteButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_DOUBLE_CLICK, new GUIAction(this, "openColorPickerWindow"));
		add(whiteButton);

		lightGrayButton = new Button(colorButtonsX+buttonSize*1+1, colorButtonsY+buttonSize+1, buttonSize,buttonSize);
		ColorLabel lightGrayLabel = new ColorLabel(0,0,26,26);
		lightGrayLabel.setColor(new Color(0xC0,0xC0,0xC0));
		lightGrayButton.setCenterLabel(lightGrayLabel);
		lightGrayButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new GUIAction(this, "setPrimaryColor", lightGrayLabel.getColor()));
		lightGrayButton.addAction(GUIEvent.MOUSE_RIGHT_BUTTON_UP, new GUIAction(this, "setSecundaryColor", lightGrayLabel.getColor()));
		lightGrayButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_DOUBLE_CLICK, new GUIAction(this, "openColorPickerWindow"));
		add(lightGrayButton);

		redButton = new Button(colorButtonsX+buttonSize*2+2, colorButtonsY+buttonSize+1, buttonSize,buttonSize);
		ColorLabel redLabel = new ColorLabel(0,0,26,26);
		redLabel.setColor(Color.RED);
		redButton.setCenterLabel(redLabel);
		redButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new GUIAction(this, "setPrimaryColor", redLabel.getColor()));
		redButton.addAction(GUIEvent.MOUSE_RIGHT_BUTTON_UP, new GUIAction(this, "setSecundaryColor", redLabel.getColor()));
		redButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_DOUBLE_CLICK, new GUIAction(this, "openColorPickerWindow"));
		add(redButton);

		yellowButton = new Button(colorButtonsX+buttonSize*3+3, colorButtonsY+buttonSize+1, buttonSize,buttonSize);
		ColorLabel yellowLabel = new ColorLabel(0,0,26,26);
		yellowLabel.setColor(Color.YELLOW);
		yellowButton.setCenterLabel(yellowLabel);
		yellowButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new GUIAction(this, "setPrimaryColor", yellowLabel.getColor()));
		yellowButton.addAction(GUIEvent.MOUSE_RIGHT_BUTTON_UP, new GUIAction(this, "setSecundaryColor", yellowLabel.getColor()));
		yellowButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_DOUBLE_CLICK, new GUIAction(this, "openColorPickerWindow"));
		add(yellowButton);

		limeButton = new Button(colorButtonsX+buttonSize*4+4, colorButtonsY+buttonSize+1, buttonSize,buttonSize);
		ColorLabel limeLabel = new ColorLabel(0,0,26,26);
		limeLabel.setColor(SVGColor.LIME);
		limeButton.setCenterLabel(limeLabel);
		limeButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new GUIAction(this, "setPrimaryColor", limeLabel.getColor()));
		limeButton.addAction(GUIEvent.MOUSE_RIGHT_BUTTON_UP, new GUIAction(this, "setSecundaryColor", limeLabel.getColor()));
		limeButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_DOUBLE_CLICK, new GUIAction(this, "openColorPickerWindow"));
		add(limeButton);

		cyanButton = new Button(colorButtonsX+buttonSize*5+5, colorButtonsY+buttonSize+1, buttonSize,buttonSize);
		ColorLabel cyanLabel = new ColorLabel(0,0,26,26);
		cyanLabel.setColor(SVGColor.CYAN);
		cyanButton.setCenterLabel(cyanLabel);
		cyanButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new GUIAction(this, "setPrimaryColor", cyanLabel.getColor()));
		cyanButton.addAction(GUIEvent.MOUSE_RIGHT_BUTTON_UP, new GUIAction(this, "setSecundaryColor", cyanLabel.getColor()));
		cyanButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_DOUBLE_CLICK, new GUIAction(this, "openColorPickerWindow"));
		add(cyanButton);

		blueButton = new Button(colorButtonsX+buttonSize*6+6, colorButtonsY+buttonSize+1, buttonSize,buttonSize);
		ColorLabel blueLabel = new ColorLabel(0,0,26,26);
		blueLabel.setColor(Color.BLUE);
		blueButton.setCenterLabel(blueLabel);
		blueButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new GUIAction(this, "setPrimaryColor", blueLabel.getColor()));
		blueButton.addAction(GUIEvent.MOUSE_RIGHT_BUTTON_UP, new GUIAction(this, "setSecundaryColor", blueLabel.getColor()));
		blueButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_DOUBLE_CLICK, new GUIAction(this, "openColorPickerWindow"));
		add(blueButton);

		magentaButton = new Button(colorButtonsX+buttonSize*7+7, colorButtonsY+buttonSize+1, buttonSize,buttonSize);
		ColorLabel magentaLabel = new ColorLabel(0,0,26,26);
		magentaLabel.setColor(Color.MAGENTA);
		magentaButton.setCenterLabel(magentaLabel);
		magentaButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new GUIAction(this, "setPrimaryColor", magentaLabel.getColor()));
		magentaButton.addAction(GUIEvent.MOUSE_RIGHT_BUTTON_UP, new GUIAction(this, "setSecundaryColor", magentaLabel.getColor()));
		magentaButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_DOUBLE_CLICK, new GUIAction(this, "openColorPickerWindow"));
		add(magentaButton);

		eggCustardButton = new Button(colorButtonsX+buttonSize*8+8, colorButtonsY+buttonSize+1, buttonSize,buttonSize);
		ColorLabel eggCustardLabel = new ColorLabel(0,0,26,26);
		eggCustardLabel.setColor(SVGColor.EGG_CUSTARD);
		eggCustardButton.setCenterLabel(eggCustardLabel);
		eggCustardButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new GUIAction(this, "setPrimaryColor", eggCustardLabel.getColor()));
		eggCustardButton.addAction(GUIEvent.MOUSE_RIGHT_BUTTON_UP, new GUIAction(this, "setSecundaryColor", eggCustardLabel.getColor()));
		eggCustardButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_DOUBLE_CLICK, new GUIAction(this, "openColorPickerWindow"));
		add(eggCustardButton);

		springGreenButton = new Button(colorButtonsX+buttonSize*9+9, colorButtonsY+buttonSize+1, buttonSize,buttonSize);
		ColorLabel springGreenLabel = new ColorLabel(0,0,26,26);
		springGreenLabel.setColor(SVGColor.SPRING_GREEN);
		springGreenButton.setCenterLabel(springGreenLabel);
		springGreenButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new GUIAction(this, "setPrimaryColor", springGreenLabel.getColor()));
		springGreenButton.addAction(GUIEvent.MOUSE_RIGHT_BUTTON_UP, new GUIAction(this, "setSecundaryColor", springGreenLabel.getColor()));
		springGreenButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_DOUBLE_CLICK, new GUIAction(this, "openColorPickerWindow"));
		add(springGreenButton);

		lightCyanButton = new Button(colorButtonsX+buttonSize*10+10, colorButtonsY+buttonSize+1, buttonSize,buttonSize);
		ColorLabel lightCyanLabel = new ColorLabel(0,0,26,26);
		lightCyanLabel.setColor(SVGColor.LIGHT_CYAN);
		lightCyanButton.setCenterLabel(lightCyanLabel);
		lightCyanButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new GUIAction(this, "setPrimaryColor", lightCyanLabel.getColor()));
		lightCyanButton.addAction(GUIEvent.MOUSE_RIGHT_BUTTON_UP, new GUIAction(this, "setSecundaryColor", lightCyanLabel.getColor()));
		lightCyanButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_DOUBLE_CLICK, new GUIAction(this, "openColorPickerWindow"));
		add(lightCyanButton);

		lavendarButton = new Button(colorButtonsX+buttonSize*11+11, colorButtonsY+buttonSize+1, buttonSize,buttonSize);
		ColorLabel lavendarLabel = new ColorLabel(0,0,26,26);
		lavendarLabel.setColor(SVGColor.LAVENDAR);
		lavendarButton.setCenterLabel(lavendarLabel);
		lavendarButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new GUIAction(this, "setPrimaryColor", lavendarLabel.getColor()));
		lavendarButton.addAction(GUIEvent.MOUSE_RIGHT_BUTTON_UP, new GUIAction(this, "setSecundaryColor", lavendarLabel.getColor()));
		lavendarButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_DOUBLE_CLICK, new GUIAction(this, "openColorPickerWindow"));
		add(lavendarButton);

		darkRoseButton = new Button(colorButtonsX+buttonSize*12+12, colorButtonsY+buttonSize+1, buttonSize,buttonSize);
		ColorLabel darkRoseLabel = new ColorLabel(0,0,26,26);
		darkRoseLabel.setColor(SVGColor.DARK_ROSE);
		darkRoseButton.setCenterLabel(darkRoseLabel);
		darkRoseButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new GUIAction(this, "setPrimaryColor", darkRoseLabel.getColor()));
		darkRoseButton.addAction(GUIEvent.MOUSE_RIGHT_BUTTON_UP, new GUIAction(this, "setSecundaryColor", darkRoseLabel.getColor()));
		darkRoseButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_DOUBLE_CLICK, new GUIAction(this, "openColorPickerWindow"));
		add(darkRoseButton);

		coralButton = new Button(colorButtonsX+buttonSize*13+13, colorButtonsY+buttonSize+1, buttonSize,buttonSize);
		ColorLabel coralLabel = new ColorLabel(0,0,26,26);
		coralLabel.setColor(SVGColor.CORAL);
		coralButton.setCenterLabel(coralLabel);
		coralButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new GUIAction(this, "setPrimaryColor", coralLabel.getColor()));
		coralButton.addAction(GUIEvent.MOUSE_RIGHT_BUTTON_UP, new GUIAction(this, "setSecundaryColor", coralLabel.getColor()));
		coralButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_DOUBLE_CLICK, new GUIAction(this, "openColorPickerWindow"));
		add(coralButton);


	}

	private void createScreen(){

		screenW = w-startScreenX;
		screenH = h-startScreenY-colorToolBarH;

		screen = new BufferedImage(screenW, screenH, BufferedImage.TYPE_INT_ARGB);
		screenGraphics = screen.createGraphics();
		screenGraphics.setColor(Color.WHITE);
		screenGraphics.fillRect(0, 0, screen.getWidth(), screen.getHeight());

	}

	public void setMode(PaintMode mode) {
		this.mode = mode;
	}

	public void setPrimaryColor(Color color) {
		this.primaryColor = color;
	}

	public void setSecundaryColor(Color color) {
		this.secundaryColor = color;
	}

	public void openColorPickerWindow() {
		System.out.println("openColorPickerWindow");
	}

	@Override
	public void draw(Grafico g) {

		if(zoom>0){
			g.setTransform(AffineTransform.getScaleInstance(zoom+1, zoom+1));
			g.drawImage(screen, startScreenX, startScreenY);
			g.setTransform(AffineTransform.getScaleInstance(1, 1));
		}else if(zoom<0){
			g.setTransform(AffineTransform.getScaleInstance((double)(1.0/(-zoom+1)), (double)(1.0/(-zoom+1))));
			g.drawImage(screen, startScreenX, startScreenY);
			g.setTransform(AffineTransform.getScaleInstance(1, 1));
		}
		else{
			g.drawImage(screen, startScreenX, startScreenY);
		}

		if(mode==PaintMode.DRAW_LINE){

			if(undefined){
				drawUndefinedLine(g);
			}

		}

		if(mode==PaintMode.DRAW_RECT){

			if(undefined){

				drawUndefinedRect(g);

			}

		}

		if(mode==PaintMode.DRAW_OVAL){

			if(undefined){
				drawUndefinedOval(g);
			}

		}

		drawCoordinates(g);


	}

	private void drawCoordinates(Grafico g){

		g.setColor(Color.WHITE);

		if((mx>startScreenX)&&(my>startScreenY)){
			g.escreveSombra(875, 530, Integer.toString(mx-startScreenX)+","+Integer.toString(my-startScreenY));
		}

	}

	private boolean shift = false;

	@Override
	public GUIEvent updateKeyboard(KeyboardEvent event) {

		if(event.getPressed(Tecla.TSK_SHIFT_DIREITA)||event.getPressed(Tecla.TSK_SHIFT_ESQUERDA)){
			shift = true;
			System.out.println("Shift true");
		}

		if(event.getReleased(Tecla.TSK_SHIFT_DIREITA)||event.getReleased(Tecla.TSK_SHIFT_ESQUERDA)){
			shift = false;
			System.out.println("Shift false");
		}

		return GUIEvent.NONE;
	}

	@Override
	public GUIEvent updateMouse(PointerEvent event) {

		if(event.getState()!=KeyState.RELEASED){
			mx = event.getX();
			my = event.getY();
		}

		//TODO Change to colision
		if((mx>startScreenX)&&(my>startScreenY&&(my<colorToolBarY))){

			switch(mode){

			case PAINT_CAN:
				paintCanModeEvent(event);
				break;

			case PENCIL:
				pencilModeEvent(event);
				break;

			case DRAW_LINE:
				lineModeEvent(event);
				break;
			case DRAW_RECT:
				rectModeEvent(event);
				break;
			case DRAW_OVAL:
				ovalModeEvent(event);
				break;
			case DRAW_ROUND:

				break;

			}

		}

		if(event.getPressed(MouseButton.MOUSE_WHEEL_UP)||event.getPressed(MouseButton.MOUSE_WHEEL_DOWN)){
			zoom-=event.getAmount();
		}

		return GUIEvent.NONE;
	}

	public void paintCanModeEvent(PointerEvent event) {

		//TODO FloodFill from mx my

		if(event.getReleased(MouseButton.MOUSE_BUTTON_LEFT)){

			//TODO Floodfill with primary color

			screenGraphics.setColor(primaryColor);
			screenGraphics.fillRect(0, 0, screen.getWidth(), screen.getHeight());

		}

		if(event.getReleased(MouseButton.MOUSE_BUTTON_RIGHT)){

			//TODO Floodfill with secundary color

			screenGraphics.setColor(secundaryColor);
			screenGraphics.fillRect(0, 0, screen.getWidth(), screen.getHeight());
		}

	}

	private int startPointX = 0;
	private int startPointY = 0;

	private boolean pencilLeftPressed = false;
	private boolean pencilRightPressed = false;

	private void pencilModeEvent(PointerEvent event){

		if(event.getPressed(MouseButton.MOUSE_BUTTON_LEFT)){
			pencilLeftPressed = true;
		}
		if(event.getReleased(MouseButton.MOUSE_BUTTON_LEFT)){
			pencilLeftPressed = false;
		}

		if(event.getPressed(MouseButton.MOUSE_BUTTON_RIGHT)){
			pencilRightPressed = true;
		}
		if(event.getReleased(MouseButton.MOUSE_BUTTON_RIGHT)){
			pencilRightPressed = false;
		}

		if(pencilLeftPressed){

			drawPixel(primaryColor);

		}else if(pencilRightPressed){

			drawPixel(secundaryColor);

		}

	}

	private void lineModeEvent(PointerEvent event){

		if(event.getPressed(MouseButton.MOUSE_BUTTON_LEFT)){

			setStartPoint();
			undefinedColor = primaryColor;
			undefined = true;

		}

		if(event.getReleased(MouseButton.MOUSE_BUTTON_RIGHT)){

			setStartPoint();
			undefinedColor = secundaryColor;
			undefined = true;

		}

		if(event.getReleased(MouseButton.MOUSE_BUTTON_LEFT)||event.getReleased(MouseButton.MOUSE_BUTTON_RIGHT)){
			drawLine();
			undefined = false;
		}

	}

	private void rectModeEvent(PointerEvent event){

		if(event.getPressed(MouseButton.MOUSE_BUTTON_LEFT)){

			setStartPoint();
			undefinedColor = primaryColor;
			undefined = true;

		}

		if(event.getReleased(MouseButton.MOUSE_BUTTON_RIGHT)){
			setStartPoint();
			undefinedColor = secundaryColor;
			undefined = true;
		}

		if(event.getReleased(MouseButton.MOUSE_BUTTON_LEFT)||event.getReleased(MouseButton.MOUSE_BUTTON_RIGHT)){

			drawRect();
			undefined = false;

		}

	}

	private void ovalModeEvent(PointerEvent event){

		if(event.getPressed(MouseButton.MOUSE_BUTTON_LEFT)){

			setStartPoint();
			undefinedColor = primaryColor;
			undefined = true;

		}

		if(event.getReleased(MouseButton.MOUSE_BUTTON_RIGHT)){
			setStartPoint();
			undefinedColor = secundaryColor;
			undefined = true;
		}

		if(event.getReleased(MouseButton.MOUSE_BUTTON_LEFT)||event.getReleased(MouseButton.MOUSE_BUTTON_RIGHT)){

			drawOval();
			undefined = false;

		}
	}

	private void setStartPoint(){

		startPointX = mx;
		startPointY = my;

	}

	private void drawPixel(Color color){
		//screenGraphics.setColor(color);
		screen.setRGB(mx-startScreenX, my-startScreenY, color.getRGB());
		//screenGraphics.drawRect(mx-startScreenX, my-startScreenY, 1, 1);
	}

	private void drawLine(){

		screenGraphics.setColor(undefinedColor);
		screenGraphics.drawLine(startPointX-startScreenX, startPointY-startScreenY, mx-startScreenX, my-startScreenY);

	}

	private void drawRect(){

		screenGraphics.setColor(undefinedColor);

		int ix = startPointX;
		int difx = mx-startPointX;
		int iy = startPointY;
		int dify = my-startPointY;

		if(mx<startPointX){
			ix = mx;
			difx =-difx;
		}
		if(my<startPointY){
			iy = my;
			dify =-dify;
		}

		screenGraphics.drawRect(ix-startScreenX, iy-startScreenY, difx, dify);

	}

	private void drawOval(){
		screenGraphics.setColor(undefinedColor);

		int ix = startPointX;
		int difx = mx-startPointX;
		int iy = startPointY;
		int dify = my-startPointY;

		if(mx<startPointX){
			ix = mx;
			difx =-difx;
		}
		if(my<startPointY){
			iy = my;
			dify =-dify;
		}

		screenGraphics.drawOval(ix-startScreenX, iy-startScreenY, difx, dify);

	}

	private void drawUndefinedLine(Grafico g){

		g.setColor(undefinedColor);

		if(shift){

			int mydif = my-startPointY;
			int mxdif = mx-startPointX;

			if(my<startPointY){
				mydif = startPointY-my;
			}

			if(mx<startPointX){
				mxdif = startPointX-mx;	
			}

			if(mxdif>mydif){
				my = startPointY;
			}else if(mydif>mxdif){
				mx = startPointX;
			}

		}

		g.drawLine(startPointX, startPointY, mx, my);

	}	

	private void drawUndefinedRect(Grafico g){

		g.setColor(undefinedColor);

		int ix = startPointX;
		int difx = mx-startPointX;
		int iy = startPointY;
		int dify = my-startPointY;

		if(mx<startPointX){
			ix = mx;
			difx =-difx;
		}
		if(my<startPointY){
			iy = my;
			dify =-dify;
		}

		g.drawRect(ix, iy, difx, dify);
	}

	private void drawUndefinedOval(Grafico g){
		g.setColor(undefinedColor);

		int ix = startPointX;
		int difx = mx-startPointX;
		int iy = startPointY;
		int dify = my-startPointY;

		if(mx<startPointX){
			ix = mx;
			difx =-difx;
		}
		if(my<startPointY){
			iy = my;
			dify =-dify;
		}

		g.drawOval(ix, iy, difx, dify);
	}

}