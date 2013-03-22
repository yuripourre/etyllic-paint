package br.com.etyllica.paint;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.Queue;

import br.com.etyllica.core.control.mouse.MouseButton;
import br.com.etyllica.core.event.GUIAction;
import br.com.etyllica.core.event.GUIEvent;
import br.com.etyllica.core.event.KeyboardEvent;
import br.com.etyllica.core.event.PointerEvent;
import br.com.etyllica.core.event.Tecla;
import br.com.etyllica.core.video.Grafico;
import br.com.etyllica.gui.Panel;
import br.com.etyllica.gui.RadioGroup;
import br.com.etyllica.gui.icon.ImageIcon;
import br.com.etyllica.gui.radio.RadioButton;
import br.com.etyllica.linear.vector.IntVector2D;

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

	private RadioButton freeMark;
	private RadioButton rectangularMark;

	private RadioButton eraser;
	private RadioButton paintCan;

	private RadioButton dropper;
	private RadioButton magnify;

	private RadioButton pencil;
	private RadioButton brush;

	private RadioButton airBrush;
	private RadioButton text;

	private RadioButton line;
	private RadioButton curve;

	private RadioButton rect;
	private RadioButton drawRectButton;
	private RadioButton fillRectButton;
	private boolean rectDraw = true;

	private RadioButton poly;

	private RadioButton oval;
	private RadioButton drawOvalButton;
	private RadioButton fillOvalButton;
	private boolean ovalDraw = true;

	private RadioButton round;

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

		freeMark = new RadioButton(toolBarX,toolBarY,buttonSize,buttonSize);
		freeMark.setCenterLabel(new ImageIcon("freemark.png"));
		freeMark.setAlt("Free Form Select");
		toolbarGroup.add(freeMark);
		freeMark.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new GUIAction(this, "setMode", PaintMode.FREE_MARK));
		add(freeMark);

		rectangularMark = new RadioButton(toolBarX+buttonSize+1,toolBarY,buttonSize,buttonSize);
		rectangularMark.setCenterLabel(new ImageIcon("rectmark.png"));
		toolbarGroup.add(rectangularMark);
		rectangularMark.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new GUIAction(this, "setMode", PaintMode.RECTANGULAR_MARK));
		add(rectangularMark);

		eraser = new RadioButton(toolBarX,toolBarY+buttonSize*1+1,buttonSize,buttonSize);
		//From http://findicons.com/icon/441226/eraser?id=449660
		eraser.setCenterLabel(new ImageIcon("eraser.png"));
		eraser.setAlt("Eraser/Color Eraser");
		eraser.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new GUIAction(this, "setMode", PaintMode.ERASER));
		toolbarGroup.add(eraser);
		add(eraser);


		paintCan = new RadioButton(toolBarX+buttonSize+1,toolBarY+buttonSize*1+1,buttonSize,buttonSize);
		//http://www.softicons.com/free-icons/toolbar-icons/some-icons-by-scardi-shek/paint-icon
		paintCan.setCenterLabel(new ImageIcon("paint.png"));
		paintCan.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new GUIAction(this, "setMode", PaintMode.PAINT_CAN));
		toolbarGroup.add(paintCan);
		add(paintCan);


		dropper = new RadioButton(toolBarX,toolBarY+buttonSize*2+2,buttonSize,buttonSize);
		//http://findicons.com/icon/238374/color_picker?id=238374
		dropper.setCenterLabel(new ImageIcon("dropper.png"));
		dropper.setAlt("Pick Color");
		dropper.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new GUIAction(this, "setMode", PaintMode.DROPPER));
		toolbarGroup.add(dropper);
		add(dropper);

		magnify = new RadioButton(toolBarX+buttonSize+1,toolBarY+buttonSize*2+2,buttonSize,buttonSize);
		//http://findicons.com/icon/219105/find?id=356081
		magnify.setCenterLabel(new ImageIcon("magnify.png"));
		magnify.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new GUIAction(this, "setMode", PaintMode.ZOOM));
		toolbarGroup.add(magnify);
		add(magnify);


		pencil = new RadioButton(toolBarX,toolBarY+buttonSize*3+3,buttonSize,buttonSize);
		//http://findicons.com/icon/226814/pencil?width=32
		pencil.setCenterLabel(new ImageIcon("pencil.png"));
		pencil.setAlt("Pencil");
		pencil.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new GUIAction(this, "setMode", PaintMode.PENCIL));
		toolbarGroup.add(pencil);
		add(pencil);

		brush = new RadioButton(toolBarX+buttonSize+1,toolBarY+buttonSize*3+3,buttonSize,buttonSize);
		//http://findicons.com/icon/237997/paintbrush?width=32
		brush.setCenterLabel(new ImageIcon("paintbrush.png"));
		brush.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new GUIAction(this, "setMode", PaintMode.PAINT_BRUSH));
		toolbarGroup.add(brush);
		add(brush);

		airBrush = new RadioButton(toolBarX,toolBarY+buttonSize*4+4,buttonSize,buttonSize);
		//http://www.keywordpictures.com/abuse/spray%20can%20icon///
		airBrush.setCenterLabel(new ImageIcon("spray_can.png"));
		airBrush.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new GUIAction(this, "setMode", PaintMode.SPRAY));
		toolbarGroup.add(airBrush);
		add(airBrush);

		text = new RadioButton(toolBarX+buttonSize+1,toolBarY+buttonSize*4+4,buttonSize,buttonSize);
		text.setCenterLabel(new ImageIcon("text.png"));
		text.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new GUIAction(this, "setMode", PaintMode.TEXT));
		toolbarGroup.add(text);
		add(text);

		line = new RadioButton(toolBarX,toolBarY+buttonSize*5+5,buttonSize,buttonSize);
		line.setCenterLabel(new ImageIcon("line.png"));
		line.setAlt("Line");
		line.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new GUIAction(this, "setMode", PaintMode.DRAW_LINE));
		toolbarGroup.add(line);
		add(line);

		curve = new RadioButton(toolBarX+buttonSize+1,toolBarY+buttonSize*5+5,buttonSize,buttonSize);
		curve.setCenterLabel(new ImageIcon("curve.png"));
		curve.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new GUIAction(this, "setMode", PaintMode.DRAW_CURVE));
		toolbarGroup.add(curve);
		add(curve);

		rect = new RadioButton(toolBarX,toolBarY+buttonSize*6+6,buttonSize,buttonSize);
		rect.setCenterLabel(new ImageIcon("rect.png"));
		rect.setAlt("Rectangle");
		rect.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new GUIAction(this, "setRectMode"));
		toolbarGroup.add(rect);
		add(rect);

		//TODO Rect Options
		RadioGroup rectGroup = new RadioGroup();

		drawRectButton = new RadioButton(toolBarX, 400, buttonSize*2, buttonSize);
		drawRectButton.setCenterLabel(new ImageIcon("rect.png"));
		drawRectButton.setAlt("Draw Rectangle");
		drawRectButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new GUIAction(this, "setRectModeDraw"));
		rectGroup.add(drawRectButton);
		drawRectButton.setChecked(true);
		drawRectButton.setVisible(false);
		add(drawRectButton);

		fillRectButton = new RadioButton(toolBarX, 400+buttonSize+2, buttonSize*2, buttonSize);
		fillRectButton.setCenterLabel(new ImageIcon("rectfilled.png"));
		fillRectButton.setAlt("Fill Rectangle");
		fillRectButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new GUIAction(this, "setRectModeFill"));
		rectGroup.add(fillRectButton);
		fillRectButton.setVisible(false);
		add(fillRectButton);

		poly = new RadioButton(toolBarX+buttonSize+1,toolBarY+buttonSize*6+6,buttonSize,buttonSize);
		poly.setCenterLabel(new ImageIcon("poly.png"));
		poly.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new GUIAction(this, "setMode", PaintMode.DRAW_POLY));
		toolbarGroup.add(poly);
		add(poly);

		oval = new RadioButton(toolBarX,toolBarY+buttonSize*7+7,buttonSize,buttonSize);
		oval.setCenterLabel(new ImageIcon("oval.png"));
		oval.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new GUIAction(this, "setOvalMode"));
		toolbarGroup.add(oval);
		add(oval);

		//TODO Oval Options
		RadioGroup ovalGroup = new RadioGroup();

		drawOvalButton = new RadioButton(toolBarX, 400, buttonSize*2, buttonSize);
		drawOvalButton.setCenterLabel(new ImageIcon("oval.png"));
		drawOvalButton.setAlt("Draw Rectangle");
		drawOvalButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new GUIAction(this, "setOvalModeDraw"));
		ovalGroup.add(drawOvalButton);
		drawOvalButton.setChecked(true);
		drawOvalButton.setVisible(false);
		add(drawOvalButton);

		fillOvalButton = new RadioButton(toolBarX, 400+buttonSize+2, buttonSize*2, buttonSize);
		fillOvalButton.setCenterLabel(new ImageIcon("ovalfilled.png"));
		fillOvalButton.setAlt("Fill Rectangle");
		fillOvalButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new GUIAction(this, "setOvalModeFill"));
		ovalGroup.add(fillOvalButton);
		fillOvalButton.setVisible(false);
		add(fillOvalButton);

		round = new RadioButton(toolBarX+buttonSize+1,toolBarY+buttonSize*7+7,buttonSize,buttonSize);
		round.setCenterLabel(new ImageIcon("round.png"));
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

	public void setOvalMode() {
		setMode(PaintMode.DRAW_OVAL);
		showOvalCheckButtons();
	}

	private void showOvalCheckButtons(){
		drawOvalButton.setVisible(true);
		fillOvalButton.setVisible(true);
	}

	public void setRectModeDraw(){
		rectDraw = true;
	}

	public void setRectModeFill(){
		rectDraw = false;
	}

	public void setOvalModeDraw(){
		ovalDraw = true;
	}

	public void setOvalModeFill(){
		ovalDraw = false;
	}

	public void setMode(PaintMode mode) {
		this.mode = mode;
		resetMode();
	}

	private void resetMode(){
		//Reset Rect
		drawRectButton.setVisible(false);
		fillRectButton.setVisible(false);

		//Reset Oval
		drawOvalButton.setVisible(false);
		fillOvalButton.setVisible(false);
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

		if(mode==PaintMode.ERASER){
			drawEraser(g);
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

		mx = event.getX();
		my = event.getY();

		//TODO Change to colision
		if((mx>startScreenX)&&(my>startScreenY&&(my<colorToolBarY))){

			switch(mode){

			case PAINT_CAN:
				paintCanModeEvent(event);
				break;
				
			case ERASER:
				eraserModeEvent(event);
				break;

			case DROPPER:
				dropperModeEvent(event);
				break;

				//TODO Just for now
			case PAINT_BRUSH:
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

	private void paintCanModeEvent(PointerEvent event) {

		if(event.getReleased(MouseButton.MOUSE_BUTTON_LEFT)){
			floodFill(mx-startScreenX, my-startScreenY, primaryColor.getRGB());
		}

		if(event.getReleased(MouseButton.MOUSE_BUTTON_RIGHT)){
			floodFill(mx-startScreenX, my-startScreenY, secundaryColor.getRGB());
		}

	}

	private int eraserSize = 10;
	
	private void eraserModeEvent(PointerEvent event) {
				
		//Erase
		if(event.getReleased(MouseButton.MOUSE_BUTTON_LEFT)){
			screenGraphics.setColor(secundaryColor);
			screenGraphics.fillRect(mx-startScreenX, my-startScreenY, eraserSize, eraserSize);
		}

	}
	
	private void drawEraser(Grafico g){
		g.setColor(secundaryColor);
		g.fillRect(mx, my, eraserSize, eraserSize);
		
		g.setColor(Color.BLACK);
		g.drawRect(mx, my, eraserSize, eraserSize);
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

		if(event.getPressed(MouseButton.MOUSE_BUTTON_RIGHT)){
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

	private void dropperModeEvent(PointerEvent event){

		if(event.getPressed(MouseButton.MOUSE_BUTTON_LEFT)){
			if(mx>startScreenX&&my>startScreenY){

				setPrimaryColor(new Color(screen.getRGB(mx-startScreenX, my-startScreenY)));

			}
		}

		if(event.getPressed(MouseButton.MOUSE_BUTTON_RIGHT)){
			if(mx>startScreenX&&my>startScreenY){

				setSecundaryColor(new Color(screen.getRGB(mx-startScreenX, my-startScreenY)));

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

		if(ovalDraw){
			screenGraphics.setColor(undefinedColor);
			screenGraphics.drawOval(ix-startScreenX, iy-startScreenY, difx, dify);
		}else{
			screenGraphics.setColor(anotherColor);
			screenGraphics.fillOval(ix-startScreenX, iy-startScreenY, difx, dify);

			screenGraphics.setColor(undefinedColor);	
			screenGraphics.drawOval(ix-startScreenX, iy-startScreenY, difx, dify);
		}

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
		
		if(ovalDraw){
			g.setColor(undefinedColor);
			g.drawOval(ix, iy, difx, dify);			
		}else{
			g.setColor(anotherColor);
			g.fillOval(ix, iy, difx, dify);

			g.setColor(undefinedColor);	
			g.drawOval(ix, iy, difx, dify);
		}
	}

	private void floodFill(int mx, int my, int paintColor){

		int w = screen.getWidth();
		int h = screen.getHeight();

		int searchColor = screen.getRGB(mx, my); 

		boolean[][] painted = new boolean[w][h];

		if(screen.getRGB(mx,my)==searchColor && !painted[mx][my]){

			Queue<IntVector2D> queue = new LinkedList<IntVector2D>();
			queue.add(new IntVector2D(mx, my));

			while (!queue.isEmpty()) {
				IntVector2D p = queue.remove();

				if ((p.getX() >= 0) && (p.getX() < w &&
						(p.getY() >= 0) && (p.getY() < h))) {
					if (!painted[p.getX()][p.getY()] && (screen.getRGB((int)p.getX(), (int)p.getY())==searchColor)) {
						screen.setRGB(p.getX(), p.getY(), paintColor);
						painted[p.getX()][p.getY()] = true;

						queue.add(new IntVector2D(p.getX() + 1, p.getY()));
						queue.add(new IntVector2D(p.getX() - 1, p.getY()));
						queue.add(new IntVector2D(p.getX(), p.getY() + 1));
						queue.add(new IntVector2D(p.getX(), p.getY() - 1));

					}
				}
			}
		}

	}

}