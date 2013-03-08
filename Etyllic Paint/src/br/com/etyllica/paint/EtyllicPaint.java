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
import br.com.etyllica.gui.icon.ImageIcon;

public class EtyllicPaint extends Application{

	public EtyllicPaint(int w, int h) {
		super(w, h);
		// TODO Auto-generated constructor stub
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
	private Button poligonalMark;
	private Button rectangularlMark;

	private Button eraser;
	private Button paintCan;

	private Button dropper;
	private Button magnify;

	private Button pencil;
	private Button brush;

	private Button sprayCan;
	private Button text;

	private Button line;
	private Button curve;

	private Button rect;
	private Button poly;

	private Button oval;
	private Button round;

	private Color primaryColor = Color.BLACK;
	private Color secundaryColor = Color.WHITE;

	@Override
	public void load() {

		loadingPhrase = "Loading Resources...";

		int toolBarX = 0;
		int toolBarY = 0;
		int toolBarColumns = 2;
		int buttonSize = 48;

		poligonalMark = new Button(toolBarX,toolBarY,buttonSize,buttonSize);
		add(poligonalMark);

		rectangularlMark = new Button(toolBarX+buttonSize+1,toolBarY,buttonSize,buttonSize);
		add(rectangularlMark);

		eraser = new Button(toolBarX,toolBarY+buttonSize*1+1,buttonSize,buttonSize);
		//From http://findicons.com/icon/441226/eraser?id=449660
		eraser.setLabel(new ImageIcon(8, 8, "eraser.png"));
		add(eraser);


		paintCan = new Button(toolBarX+buttonSize+1,toolBarY+buttonSize*1+1,buttonSize,buttonSize);
		//http://www.softicons.com/free-icons/toolbar-icons/some-icons-by-scardi-shek/paint-icon
		paintCan.setLabel(new ImageIcon(8, 8, "paint.png"));
		paintCan.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new GUIAction(this, "setMode", PaintMode.PAINT_CAN));
		add(paintCan);


		dropper = new Button(toolBarX,toolBarY+buttonSize*2+2,buttonSize,buttonSize);
		//http://findicons.com/icon/238374/color_picker?id=238374
		dropper.setLabel(new ImageIcon(8, 8, "dropper.png"));
		dropper.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new GUIAction(this, "setMode", PaintMode.DROPPER));
		add(dropper);

		magnify = new Button(toolBarX+buttonSize+1,toolBarY+buttonSize*2+2,buttonSize,buttonSize);
		//http://findicons.com/icon/219105/find?id=356081
		magnify.setLabel(new ImageIcon(8, 8, "magnify.png"));
		magnify.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new GUIAction(this, "setMode", PaintMode.ZOOM));
		add(magnify);


		pencil = new Button(toolBarX,toolBarY+buttonSize*3+3,buttonSize,buttonSize);
		//http://findicons.com/icon/226814/pencil?width=32
		pencil.setLabel(new ImageIcon(8, 8, "pencil.png"));
		pencil.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new GUIAction(this, "setMode", PaintMode.PENCIL));
		add(pencil);

		brush = new Button(toolBarX+buttonSize+1,toolBarY+buttonSize*3+3,buttonSize,buttonSize);
		//http://findicons.com/icon/237997/paintbrush?width=32
		brush.setLabel(new ImageIcon(8, 8, "paintbrush.png"));
		brush.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new GUIAction(this, "setMode", PaintMode.PAINT_BRUSH));
		add(brush);

		sprayCan = new Button(toolBarX,toolBarY+buttonSize*4+4,buttonSize,buttonSize);
		//http://www.keywordpictures.com/abuse/spray%20can%20icon///
		sprayCan.setLabel(new ImageIcon(8, 8, "spray_can.png"));
		sprayCan.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new GUIAction(this, "setMode", PaintMode.SPRAY));
		add(sprayCan);

		text = new Button(toolBarX+buttonSize+1,toolBarY+buttonSize*4+4,buttonSize,buttonSize);
		text.setLabel(new ImageIcon(8, 8, "text.png"));
		text.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new GUIAction(this, "setMode", PaintMode.TEXT));
		add(text);

		line = new Button(toolBarX,toolBarY+buttonSize*5+5,buttonSize,buttonSize);
		line.setLabel(new ImageIcon(8, 8, "line.png"));
		line.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new GUIAction(this, "setMode", PaintMode.DRAW_LINE));
		add(line);

		curve = new Button(toolBarX+buttonSize+1,toolBarY+buttonSize*5+5,buttonSize,buttonSize);
		curve.setLabel(new ImageIcon(8, 8, "curve.png"));
		curve.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new GUIAction(this, "setMode", PaintMode.DRAW_CURVE));
		add(curve);

		rect = new Button(toolBarX,toolBarY+buttonSize*6+6,buttonSize,buttonSize);
		rect.setLabel(new ImageIcon(8, 8, "rect.png"));
		rect.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new GUIAction(this, "setMode", PaintMode.DRAW_RECT));
		add(rect);

		poly = new Button(toolBarX+buttonSize+1,toolBarY+buttonSize*6+6,buttonSize,buttonSize);
		poly.setLabel(new ImageIcon(8, 8, "poly.png"));
		poly.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new GUIAction(this, "setMode", PaintMode.DRAW_POLY));
		add(poly);

		oval = new Button(toolBarX,toolBarY+buttonSize*7+7,buttonSize,buttonSize);
		oval.setLabel(new ImageIcon(8, 8, "oval.png"));
		oval.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new GUIAction(this, "setMode", PaintMode.DRAW_OVAL));
		add(oval);

		round = new Button(toolBarX+buttonSize+1,toolBarY+buttonSize*7+7,buttonSize,buttonSize);
		round.setLabel(new ImageIcon(8, 8, "round.png"));
		round.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new GUIAction(this, "setMode", PaintMode.DRAW_ROUND));
		add(round);

		loading = 80;

		loadingPhrase = "Loading Screen...";


		startScreen();

		loading = 100;

	}

	private void startScreen(){

		screen = new BufferedImage(w-startScreenX, h-startScreenY, BufferedImage.TYPE_INT_ARGB);
		screenGraphics = screen.createGraphics();
		screenGraphics.setColor(Color.WHITE);
		screenGraphics.fillRect(0, 0, screen.getWidth(), screen.getHeight());

	}

	public void setMode(PaintMode mode) {
		this.mode = mode;
	}

	@Override
	public void draw(Grafico g) {

		g.drawImage(screen, startScreenX, startScreenY);

		if(mode==PaintMode.DRAW_LINE){

			if(startPointX!=UNDEFINED&&startPointY!=UNDEFINED){

				g.setColor(primaryColor);
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
		if(mx>startScreenX){

			switch(mode){

			case PAINT_CAN:
				paintCanModeEvent(event);
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

	private void lineModeEvent(PointerEvent event){

		if(event.getReleased(MouseButton.MOUSE_BUTTON_LEFT)){

			if(startPointX==UNDEFINED||startPointY==UNDEFINED){

				setStartPoint();

			}else{

				drawLine(primaryColor);

			}

		}
	}
	
	private void rectModeEvent(PointerEvent event){

		if(event.getReleased(MouseButton.MOUSE_BUTTON_LEFT)){

			if(startPointX==UNDEFINED||startPointY==UNDEFINED){

				setStartPoint();

			}else{

				drawRect(primaryColor);

			}

		}
	}
	
	private void ovalModeEvent(PointerEvent event){

		if(event.getReleased(MouseButton.MOUSE_BUTTON_LEFT)){

			if(startPointX==UNDEFINED||startPointY==UNDEFINED){

				setStartPoint();

			}else{

				drawOval(primaryColor);

			}

		}
	}

	private void setStartPoint(){

		startPointX = mx;
		startPointY = my;

	}

	private void drawLine(Color color){
		screenGraphics.setColor(color);
		screenGraphics.drawLine(startPointX-startScreenX, startPointY-startScreenY, mx-startScreenX, my-startScreenY);

		startPointX = UNDEFINED;
		startPointY = UNDEFINED;
	}
	
	private void drawRect(Color color){
		screenGraphics.setColor(color);

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
	
	private void drawOval(Color color){
		screenGraphics.setColor(color);

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
		g.setColor(primaryColor);

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
		g.setColor(primaryColor);

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