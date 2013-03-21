package br.com.etyllica.paint;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import br.com.etyllica.core.control.mouse.MouseButton;
import br.com.etyllica.core.event.GUIAction;
import br.com.etyllica.core.event.GUIEvent;
import br.com.etyllica.core.event.KeyState;
import br.com.etyllica.core.event.KeyboardEvent;
import br.com.etyllica.core.event.PointerEvent;
import br.com.etyllica.core.event.Tecla;
import br.com.etyllica.core.video.Grafico;
import br.com.etyllica.gui.Panel;
import br.com.etyllica.gui.RadioGroup;
import br.com.etyllica.gui.icon.ImageIcon;
import br.com.etyllica.gui.radio.CheckButtonRadio;

public class EtyllicPaint extends PaintInterface{

	public EtyllicPaint(int w, int h) {
		super(w, h);
	}

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
	private CheckButtonRadio drawRectButton;
	private CheckButtonRadio fillRectButton;
	private boolean rectDraw = true;
	
	private CheckButtonRadio poly;

	private CheckButtonRadio oval;
	private CheckButtonRadio round;

	private Color primaryColor = Color.BLACK;
	private Color secundaryColor = Color.WHITE;
	private Color undefinedColor = primaryColor;
	private Color anotherColor = secundaryColor;

	private int zoom = 0;

	private boolean shift = false;

	@Override
	public void load() {

		int toolBarX = 0;
		int toolBarY = 0;
		int toolBarColumns = 2;
		int buttonSize = 48;
		
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
		rect.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new GUIAction(this, "setRectMode"));
		toolbarGroup.add(rect);
		add(rect);
		
		//TODO Rect Options
		RadioGroup rectGroup = new RadioGroup();
		
		drawRectButton = new CheckButtonRadio(toolBarX, 400, buttonSize*2, buttonSize);
		drawRectButton.setCenterLabel(new ImageIcon("rect.png"));
		drawRectButton.setAlt("Draw Rectangle");
		drawRectButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new GUIAction(this, "setRectModeDraw"));
		rectGroup.add(drawRectButton);
		drawRectButton.setVisible(false);
		add(drawRectButton);
		
		fillRectButton = new CheckButtonRadio(toolBarX, 400+buttonSize+2, buttonSize*2, buttonSize);
		fillRectButton.setCenterLabel(new ImageIcon("rectfilled.png"));
		fillRectButton.setAlt("Fill Rectangle");
		fillRectButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new GUIAction(this, "setRectModeFill"));
		rectGroup.add(fillRectButton);
		fillRectButton.setVisible(false);
		add(fillRectButton);

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

		loadInterface(buttonSize);		

		loading = 90;

		loadingPhrase = "Loading Screen...";


		createScreen();

		setMode(PaintMode.RECTANGULAR_MARK);
		rectangularMark.mark();

		loading = 100;

	}

	private void createScreen(){

		screenW = w-startScreenX;
		screenH = h-startScreenY-colorToolBarH;

		screen = new BufferedImage(screenW, screenH, BufferedImage.TYPE_INT_ARGB);
		screenGraphics = screen.createGraphics();
		screenGraphics.setColor(Color.WHITE);
		screenGraphics.fillRect(0, 0, screen.getWidth(), screen.getHeight());

	}

	public void setRectMode() {
		setMode(PaintMode.DRAW_RECT);
		
		showRectCheckButtons();		
	}
	
	private void showRectCheckButtons(){
		drawRectButton.setVisible(true);
		fillRectButton.setVisible(true);
	}
		
	public void setRectModeDraw(){
		rectDraw = true;
	}
	
	public void setRectModeFill(){
		rectDraw = false;
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
			anotherColor = secundaryColor;
			undefined = true;

		}

		if(event.getPressed(MouseButton.MOUSE_BUTTON_RIGHT)){

			setStartPoint();
			undefinedColor = secundaryColor;
			anotherColor = primaryColor;
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
			anotherColor = secundaryColor;
			undefined = true;

		}

		if(event.getPressed(MouseButton.MOUSE_BUTTON_RIGHT)){
			setStartPoint();
			undefinedColor = secundaryColor;
			anotherColor = primaryColor;
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
			anotherColor = secundaryColor;
			undefined = true;

		}

		if(event.getReleased(MouseButton.MOUSE_BUTTON_RIGHT)){
			setStartPoint();
			undefinedColor = secundaryColor;
			anotherColor = primaryColor;
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
		
		if(rectDraw){
			screenGraphics.setColor(undefinedColor);
			screenGraphics.drawRect(ix-startScreenX, iy-startScreenY, difx, dify);
		}else{
			screenGraphics.setColor(anotherColor);
			screenGraphics.fillRect(ix-startScreenX, iy-startScreenY, difx, dify);
			
			screenGraphics.setColor(undefinedColor);	
			screenGraphics.drawRect(ix-startScreenX, iy-startScreenY, difx, dify);
		}

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

		int difx = mx-startPointX;
		int dify = my-startPointY;

		int ix = startPointX;
		int iy = startPointY;

		boolean xflag = false;
		boolean yflag = false;
		
		//if(!shift){

			if(difx<0){
				ix = mx;
				//ix += difx;
				difx =-difx;
				xflag = true;
			}
			if(dify<0){
				iy = my;
				//iy += dify;
				dify =-dify;
				yflag = true;
			}

		//}else{
		if(shift){
						
			if(difx<dify){
				
				difx += dify-difx;
				
				if(xflag){
					ix += dify-difx;
				}
				
			}else if(dify<difx){
				dify += difx-dify;
				if(yflag){
					iy += difx-dify;
				}
			}
			
		}

		if(rectDraw){
			g.setColor(undefinedColor);
			g.drawRect(ix, iy, difx, dify);
		}else{
			g.setColor(anotherColor);
			g.fillRect(ix, iy, difx, dify);
			
			g.setColor(undefinedColor);	
			g.drawRect(ix, iy, difx, dify);
		}
		

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