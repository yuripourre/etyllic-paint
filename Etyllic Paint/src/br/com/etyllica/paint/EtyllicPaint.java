package br.com.etyllica.paint;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import br.com.etyllica.core.application.Application;
import br.com.etyllica.core.control.mouse.MouseButton;
import br.com.etyllica.core.event.GUIAction;
import br.com.etyllica.core.event.GUIEvent;
import br.com.etyllica.core.event.KeyboardEvent;
import br.com.etyllica.core.event.PointerEvent;
import br.com.etyllica.core.video.Grafico;
import br.com.etyllica.gui.Button;
import br.com.etyllica.gui.Panel;
import br.com.etyllica.gui.RadioGroup;
import br.com.etyllica.gui.icon.ImageIcon;
import br.com.etyllica.gui.label.ColorLabel;
import br.com.etyllica.gui.radio.CheckButtonRadio;

public class EtyllicPaint extends Application{

	public EtyllicPaint(int w, int h) {
		super(w, h);
	}

	private final int UNDEFINED = -1;

	private BufferedImage screen;

	private Graphics2D screenGraphics;

	private PaintMode mode = PaintMode.RECTANGULAR_MARK;

	//Mouse Position
	private int mx = 0;
	private int my = 0;

	private int startScreenX = 98;
	private int startScreenY = 0;

	//private MenuBar menu;

	//private ToolBar toolBar;
	
	private Panel panel;
	
	private CheckButtonRadio freeMark;
	private CheckButtonRadio rectangularlMark;

	private CheckButtonRadio eraser;
	private CheckButtonRadio paintCan;

	private CheckButtonRadio dropper;
	private CheckButtonRadio magnify;

	private CheckButtonRadio pencil;
	private CheckButtonRadio brush;

	private CheckButtonRadio sprayCan;
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
	private Button blackButton;
	private Button redButton;
	private Button greenButton;
	private Button blueButton;
	private Button whiteButton;

	private int colorToolBarH = 0;
	private int colorToolBarY = 0;
	
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
		toolbarGroup.add(freeMark);
		freeMark.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new GUIAction(this, "setMode", PaintMode.FREE_MARK));
		add(freeMark);

		rectangularlMark = new CheckButtonRadio(toolBarX+buttonSize+1,toolBarY,buttonSize,buttonSize);
		toolbarGroup.add(rectangularlMark);
		rectangularlMark.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new GUIAction(this, "setMode", PaintMode.RECTANGULAR_MARK));
		add(rectangularlMark);

		eraser = new CheckButtonRadio(toolBarX,toolBarY+buttonSize*1+1,buttonSize,buttonSize);
		//From http://findicons.com/icon/441226/eraser?id=449660
		eraser.setLabel(new ImageIcon(8, 8, "eraser.png"));
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
		pencil.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new GUIAction(this, "setMode", PaintMode.PENCIL));
		toolbarGroup.add(pencil);
		add(pencil);

		brush = new CheckButtonRadio(toolBarX+buttonSize+1,toolBarY+buttonSize*3+3,buttonSize,buttonSize);
		//http://findicons.com/icon/237997/paintbrush?width=32
		brush.setLabel(new ImageIcon(8, 8, "paintbrush.png"));
		brush.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new GUIAction(this, "setMode", PaintMode.PAINT_BRUSH));
		toolbarGroup.add(brush);
		add(brush);

		sprayCan = new CheckButtonRadio(toolBarX,toolBarY+buttonSize*4+4,buttonSize,buttonSize);
		//http://www.keywordpictures.com/abuse/spray%20can%20icon///
		sprayCan.setLabel(new ImageIcon(8, 8, "spray_can.png"));
		sprayCan.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new GUIAction(this, "setMode", PaintMode.SPRAY));
		toolbarGroup.add(sprayCan);
		add(sprayCan);

		text = new CheckButtonRadio(toolBarX+buttonSize+1,toolBarY+buttonSize*4+4,buttonSize,buttonSize);
		text.setLabel(new ImageIcon(8, 8, "text.png"));
		text.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new GUIAction(this, "setMode", PaintMode.TEXT));
		toolbarGroup.add(text);
		add(text);

		line = new CheckButtonRadio(toolBarX,toolBarY+buttonSize*5+5,buttonSize,buttonSize);
		line.setLabel(new ImageIcon(8, 8, "line.png"));
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

		loading = 100;

	}

	private void createButtons(int buttonSize){
		
		int colorButtonsY = h-colorToolBarH;
		
		blackButton = new Button(300+buttonSize*0+0, colorButtonsY, buttonSize,buttonSize);
		ColorLabel blackLabel = new ColorLabel(0,0,20,20);
		blackLabel.setColor(Color.BLACK);
		blackButton.setCenterLabel(blackLabel);
		blackButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new GUIAction(this, "setPrimaryColor", blackLabel.getColor()));
		blackButton.addAction(GUIEvent.MOUSE_RIGHT_BUTTON_UP, new GUIAction(this, "setSecundaryColor", blackLabel.getColor()));
		add(blackButton);
		
		redButton = new Button(300+buttonSize*1+1, colorButtonsY, buttonSize,buttonSize);
		ColorLabel redLabel = new ColorLabel(0,0,20,20);
		redLabel.setColor(Color.RED);
		redButton.setCenterLabel(redLabel);
		redButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new GUIAction(this, "setPrimaryColor", redLabel.getColor()));
		redButton.addAction(GUIEvent.MOUSE_RIGHT_BUTTON_UP, new GUIAction(this, "setSecundaryColor", redLabel.getColor()));
		add(redButton);
		
		greenButton = new Button(300+buttonSize*2+2, colorButtonsY, buttonSize,buttonSize);
		ColorLabel greenLabel = new ColorLabel(0,0,20,20);
		greenLabel.setColor(Color.GREEN);
		greenButton.setCenterLabel(greenLabel);
		greenButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new GUIAction(this, "setPrimaryColor", greenLabel.getColor()));
		greenButton.addAction(GUIEvent.MOUSE_RIGHT_BUTTON_UP, new GUIAction(this, "setSecundaryColor", greenLabel.getColor()));
		add(greenButton);
		
		blueButton = new Button(300+buttonSize*3+3, colorButtonsY, buttonSize,buttonSize);
		ColorLabel blueLabel = new ColorLabel(0,0,20,20);
		blueLabel.setColor(Color.BLUE);
		blueButton.setCenterLabel(blueLabel);
		blueButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new GUIAction(this, "setPrimaryColor", blueLabel.getColor()));
		blueButton.addAction(GUIEvent.MOUSE_RIGHT_BUTTON_UP, new GUIAction(this, "setSecundaryColor", blueLabel.getColor()));
		add(blueButton);
		
		whiteButton = new Button(300+buttonSize*0+0, colorButtonsY+buttonSize+1, buttonSize,buttonSize);
		ColorLabel whiteLabel = new ColorLabel(0,0,20,20);
		whiteLabel.setColor(Color.WHITE);
		whiteButton.setCenterLabel(whiteLabel);
		whiteButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new GUIAction(this, "setPrimaryColor", whiteLabel.getColor()));
		whiteButton.addAction(GUIEvent.MOUSE_RIGHT_BUTTON_UP, new GUIAction(this, "setSecundaryColor", whiteLabel.getColor()));
		add(whiteButton);
	}
	
	private void createScreen(){
		
		screen = new BufferedImage(w-startScreenX, h-startScreenY-colorToolBarH, BufferedImage.TYPE_INT_ARGB);
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

	@Override
	public void draw(Grafico g) {

		g.drawImage(screen, startScreenX, startScreenY);

		if(mode==PaintMode.DRAW_LINE){

			if(startPointX!=UNDEFINED&&startPointY!=UNDEFINED){

				g.setColor(undefinedColor);
				g.drawLine(startPointX, startPointY, mx, my);

			}

		}

		if(mode==PaintMode.DRAW_RECT){

			if(startPointX!=UNDEFINED&&startPointY!=UNDEFINED){

				drawUndefinedRect(g);

			}

		}

		if(mode==PaintMode.DRAW_OVAL){

			if(startPointX!=UNDEFINED&&startPointY!=UNDEFINED){
				drawUndefinedOval(g);
			}

		}

	}

	@Override
	public GUIEvent updateKeyboard(KeyboardEvent event) {

		return GUIEvent.NONE;
	}

	@Override
	public GUIEvent updateMouse(PointerEvent event) {
		
		mx = event.getX();
		my = event.getY();

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

		return GUIEvent.NONE;
	}

	public void paintCanModeEvent(PointerEvent event) {

		//TODO FloodFill from mx my

		if(event.getReleased(MouseButton.MOUSE_BUTTON_LEFT)){

			System.out.println("PAINT with Primary Color");

			screenGraphics.setColor(primaryColor);
			screenGraphics.fillRect(0, 0, screen.getWidth(), screen.getHeight());

		}

		if(event.getReleased(MouseButton.MOUSE_BUTTON_RIGHT)){

			System.out.println("PAINT with Secundary Color");

			screenGraphics.setColor(secundaryColor);
			screenGraphics.fillRect(0, 0, screen.getWidth(), screen.getHeight());
		}

	}

	private int startPointX = UNDEFINED;
	private int startPointY = UNDEFINED;
	
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

		if(event.getReleased(MouseButton.MOUSE_BUTTON_LEFT)){

			if(startPointX==UNDEFINED||startPointY==UNDEFINED){

				setStartPoint();
				undefinedColor = primaryColor;

			}else{

				drawLine();

			}

		}
		
		if(event.getReleased(MouseButton.MOUSE_BUTTON_RIGHT)){

			if(startPointX==UNDEFINED||startPointY==UNDEFINED){

				setStartPoint();
				undefinedColor = secundaryColor;

			}else{

				drawLine();

			}

		}
	}
	
	private void rectModeEvent(PointerEvent event){

		if(event.getReleased(MouseButton.MOUSE_BUTTON_LEFT)){

			if(startPointX==UNDEFINED||startPointY==UNDEFINED){

				setStartPoint();
				undefinedColor = primaryColor;						

			}else{

				drawRect();

			}

		}
		
		if(event.getReleased(MouseButton.MOUSE_BUTTON_RIGHT)){

			if(startPointX==UNDEFINED||startPointY==UNDEFINED){

				setStartPoint();
				undefinedColor = secundaryColor;
				

			}else{

				drawRect();

			}

		}
	}
	
	private void ovalModeEvent(PointerEvent event){

		if(event.getReleased(MouseButton.MOUSE_BUTTON_LEFT)){

			if(startPointX==UNDEFINED||startPointY==UNDEFINED){

				setStartPoint();
				undefinedColor = primaryColor;

			}else{

				drawOval();

			}

		}
		
		if(event.getReleased(MouseButton.MOUSE_BUTTON_RIGHT)){

			if(startPointX==UNDEFINED||startPointY==UNDEFINED){

				setStartPoint();
				undefinedColor = secundaryColor;

			}else{

				drawOval();

			}

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

		startPointX = UNDEFINED;
		startPointY = UNDEFINED;
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

		startPointX = UNDEFINED;
		startPointY = UNDEFINED;
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

		startPointX = UNDEFINED;
		startPointY = UNDEFINED;
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