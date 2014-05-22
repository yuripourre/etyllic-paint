package br.com.etyllica.paint;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.Queue;

import br.com.etyllica.core.event.Action;
import br.com.etyllica.core.event.GUIEvent;
import br.com.etyllica.core.event.KeyEvent;
import br.com.etyllica.core.event.PointerEvent;
import br.com.etyllica.core.graphics.Graphic;
import br.com.etyllica.core.input.mouse.MouseButton;
import br.com.etyllica.gui.Panel;
import br.com.etyllica.gui.RadioGroup;
import br.com.etyllica.gui.label.ImageLabel;
import br.com.etyllica.gui.radio.RadioButton;
import br.com.etyllica.linear.vector.IntVector2D;
import br.com.etyllica.paint.tools.LineTool;
import br.com.etyllica.paint.tools.OvalTool;
import br.com.etyllica.paint.tools.RectTool;
import br.com.etyllica.paint.tools.RoundRectTool;
import br.com.etyllica.paint.tools.Tool;

public class EtyllicPaint extends PaintInterface{

	public EtyllicPaint(int w, int h) {
		super(w, h);
	}

	private final int REFRESH_INTERVAL = 15;

	private boolean temporary = false;

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
	private final float dash1[] = { 10.0f };
	private final BasicStroke dashed = new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash1, 0.0f);

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

	private RadioButton round;
	private RadioButton drawRoundButton;
	private RadioButton fillRoundButton;

	private Color undefinedColor = primaryColor;
	private Color anotherColor = secundaryColor;

	private int zoom = 0;

	private boolean shift = false;

	//List Tools
	private LineTool lineTool;
	
	private RectTool rectTool;
	
	private OvalTool ovalTool;
	
	private RoundRectTool roundRectTool;

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

		loadingPhrase = "Loading Tools...";

		freeMark = new RadioButton(toolBarX,toolBarY,buttonSize,buttonSize);
		freeMark.setCenterLabel(new ImageLabel("freemark.png"));
		freeMark.setAlt("Free Form Select");
		toolbarGroup.add(freeMark);
		freeMark.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new Action(this, "setMode", PaintMode.FREE_MARK));
		add(freeMark);

		rectangularMark = new RadioButton(toolBarX+buttonSize+1,toolBarY,buttonSize,buttonSize);
		rectangularMark.setCenterLabel(new ImageLabel("rectmark.png"));
		toolbarGroup.add(rectangularMark);
		rectangularMark.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new Action(this, "setMode", PaintMode.RECTANGULAR_MARK));
		add(rectangularMark);

		eraser = new RadioButton(toolBarX,toolBarY+buttonSize*1+1,buttonSize,buttonSize);
		//From http://findicons.com/icon/441226/eraser?id=449660
		eraser.setCenterLabel(new ImageLabel("eraser.png"));
		eraser.setAlt("Eraser/Color Eraser");
		eraser.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new Action(this, "setMode", PaintMode.ERASER));
		toolbarGroup.add(eraser);
		add(eraser);

		paintCan = new RadioButton(toolBarX+buttonSize+1,toolBarY+buttonSize*1+1,buttonSize,buttonSize);
		//http://www.softicons.com/free-icons/toolbar-icons/some-icons-by-scardi-shek/paint-icon
		paintCan.setCenterLabel(new ImageLabel("paint.png"));
		paintCan.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new Action(this, "setMode", PaintMode.PAINT_CAN));
		toolbarGroup.add(paintCan);
		add(paintCan);


		dropper = new RadioButton(toolBarX,toolBarY+buttonSize*2+2,buttonSize,buttonSize);
		//http://findicons.com/icon/238374/color_picker?id=238374
		dropper.setCenterLabel(new ImageLabel("dropper.png"));
		dropper.setAlt("Pick Color");
		dropper.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new Action(this, "setMode", PaintMode.DROPPER));
		toolbarGroup.add(dropper);
		add(dropper);

		magnify = new RadioButton(toolBarX+buttonSize+1,toolBarY+buttonSize*2+2,buttonSize,buttonSize);
		//http://findicons.com/icon/219105/find?id=356081
		magnify.setCenterLabel(new ImageLabel("magnify.png"));
		magnify.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new Action(this, "setMode", PaintMode.ZOOM));
		toolbarGroup.add(magnify);
		add(magnify);


		pencil = new RadioButton(toolBarX,toolBarY+buttonSize*3+3,buttonSize,buttonSize);
		//http://findicons.com/icon/226814/pencil?width=32
		pencil.setCenterLabel(new ImageLabel("pencil.png"));
		pencil.setAlt("Pencil");
		pencil.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new Action(this, "setMode", PaintMode.PENCIL));
		toolbarGroup.add(pencil);
		add(pencil);

		brush = new RadioButton(toolBarX+buttonSize+1,toolBarY+buttonSize*3+3,buttonSize,buttonSize);
		//http://findicons.com/icon/237997/paintbrush?width=32
		brush.setCenterLabel(new ImageLabel("paintbrush.png"));
		brush.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new Action(this, "setMode", PaintMode.PAINT_BRUSH));
		toolbarGroup.add(brush);
		add(brush);

		airBrush = new RadioButton(toolBarX,toolBarY+buttonSize*4+4,buttonSize,buttonSize);
		//http://www.keywordpictures.com/abuse/spray%20can%20icon/
		airBrush.setCenterLabel(new ImageLabel("spray_can.png"));
		airBrush.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new Action(this, "setMode", PaintMode.SPRAY));
		toolbarGroup.add(airBrush);
		add(airBrush);

		text = new RadioButton(toolBarX+buttonSize+1,toolBarY+buttonSize*4+4,buttonSize,buttonSize);
		text.setCenterLabel(new ImageLabel("text.png"));
		text.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new Action(this, "setMode", PaintMode.TEXT));
		toolbarGroup.add(text);
		add(text);

		line = new RadioButton(toolBarX,toolBarY+buttonSize*5+5,buttonSize,buttonSize);
		line.setCenterLabel(new ImageLabel("line.png"));
		line.setAlt("Line");
		line.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new Action(this, "setLineMode"));
		toolbarGroup.add(line);
		add(line);

		curve = new RadioButton(toolBarX+buttonSize+1,toolBarY+buttonSize*5+5,buttonSize,buttonSize);
		curve.setCenterLabel(new ImageLabel("curve.png"));
		curve.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new Action(this, "setMode", PaintMode.DRAW_CURVE));
		toolbarGroup.add(curve);
		add(curve);

		rect = new RadioButton(toolBarX,toolBarY+buttonSize*6+6,buttonSize,buttonSize);
		rect.setCenterLabel(new ImageLabel("rect.png"));
		rect.setAlt("Rectangle");
		rect.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new Action(this, "setRectMode"));
		toolbarGroup.add(rect);
		add(rect);

		//TODO Rect Options
		RadioGroup rectGroup = new RadioGroup();

		drawRectButton = new RadioButton(toolBarX, 400, buttonSize*2, buttonSize);
		drawRectButton.setCenterLabel(new ImageLabel("rect.png"));
		drawRectButton.setAlt("Draw Rectangle");
		drawRectButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new Action(this, "setModeDraw"));
		rectGroup.add(drawRectButton);
		drawRectButton.setChecked(true);
		drawRectButton.setVisible(false);
		add(drawRectButton);

		fillRectButton = new RadioButton(toolBarX, 400+buttonSize+2, buttonSize*2, buttonSize);
		fillRectButton.setCenterLabel(new ImageLabel("rectfilled.png"));
		fillRectButton.setAlt("Fill Rectangle");
		fillRectButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new Action(this, "setModeFill"));
		rectGroup.add(fillRectButton);
		fillRectButton.setVisible(false);
		add(fillRectButton);

		poly = new RadioButton(toolBarX+buttonSize+1,toolBarY+buttonSize*6+6,buttonSize,buttonSize);
		poly.setCenterLabel(new ImageLabel("poly.png"));
		poly.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new Action(this, "setMode", PaintMode.DRAW_POLY));
		toolbarGroup.add(poly);
		add(poly);

		oval = new RadioButton(toolBarX,toolBarY+buttonSize*7+7,buttonSize,buttonSize);
		oval.setCenterLabel(new ImageLabel("oval.png"));
		oval.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new Action(this, "setOvalMode"));
		toolbarGroup.add(oval);
		add(oval);

		//TODO Oval Options
		RadioGroup ovalGroup = new RadioGroup();

		drawOvalButton = new RadioButton(toolBarX, 400, buttonSize*2, buttonSize);
		drawOvalButton.setCenterLabel(new ImageLabel("oval.png"));
		drawOvalButton.setAlt("Draw Rectangle");
		drawOvalButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new Action(this, "setModeDraw"));
		ovalGroup.add(drawOvalButton);
		drawOvalButton.setChecked(true);
		drawOvalButton.setVisible(false);
		add(drawOvalButton);

		fillOvalButton = new RadioButton(toolBarX, 400+buttonSize+2, buttonSize*2, buttonSize);
		fillOvalButton.setCenterLabel(new ImageLabel("ovalfilled.png"));
		fillOvalButton.setAlt("Fill Rectangle");
		fillOvalButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new Action(this, "setModeFill"));
		ovalGroup.add(fillOvalButton);
		fillOvalButton.setVisible(false);
		add(fillOvalButton);

		round = new RadioButton(toolBarX+buttonSize+1,toolBarY+buttonSize*7+7,buttonSize,buttonSize);
		round.setCenterLabel(new ImageLabel("round.png"));
		round.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new Action(this, "setRoundMode"));
		toolbarGroup.add(round);
		add(round);

		//TODO Round Options
		RadioGroup roundGroup = new RadioGroup();

		drawRoundButton = new RadioButton(toolBarX, 400, buttonSize*2, buttonSize);
		drawRoundButton.setCenterLabel(new ImageLabel("round.png"));
		drawRoundButton.setAlt("Draw Rectangle");
		drawRoundButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new Action(this, "setModeDraw"));
		roundGroup.add(drawRoundButton);
		drawRoundButton.setChecked(true);
		drawRoundButton.setVisible(false);
		add(drawRoundButton);

		fillRoundButton = new RadioButton(toolBarX, 400+buttonSize+2, buttonSize*2, buttonSize);
		fillRoundButton.setCenterLabel(new ImageLabel("roundfilled.png"));
		fillRoundButton.setAlt("Fill Rectangle");
		fillRoundButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new Action(this, "setModeFill"));
		roundGroup.add(fillRoundButton);
		fillRoundButton.setVisible(false);
		add(fillRoundButton);

		loading = 70;

		loadingPhrase = "Loading Colors...";

		loadInterface(buttonSize);		

		loading = 90;

		loadingPhrase = "Loading Screen...";

		createScreen();

		//Load Tools
		lineTool = new LineTool();
		
		rectTool = new RectTool(drawRectButton, fillRectButton);
		
		ovalTool = new OvalTool(drawOvalButton, fillOvalButton);
		
		roundRectTool = new RoundRectTool(drawRoundButton, fillRoundButton);

		tool = rectTool;
		
		setMode(PaintMode.RECTANGULAR_MARK);
		rectangularMark.mark();

		loading = 100;

	}

	private void createScreen() {

		screenW = w-startScreenX;
		screenH = h-startScreenY-colorToolBarH;

		screen = new BufferedImage(screenW, screenH, BufferedImage.TYPE_INT_ARGB);
		screenGraphics = screen.createGraphics();
		screenGraphics.setColor(Color.WHITE);
		screenGraphics.fillRect(0, 0, screen.getWidth(), screen.getHeight());

	}

	private void selectTool(Tool tool) {
		this.tool = tool;

		tool.select();
		tool.configureScreen(screenGraphics, startScreenX, startScreenY);
		tool.setDraw(rectDraw);
		tool.setShift(shift);
		tool.setPrimaryColor(primaryColor);
		tool.setSecundaryColor(secundaryColor);
	}
	
	public void setLineMode() {
		setMode(PaintMode.DRAW_LINE);

		selectTool(lineTool);
	}
	
	public void setRectMode() {
		setMode(PaintMode.DRAW_RECT);

		selectTool(rectTool);
	}	

	public void setOvalMode() {
		setMode(PaintMode.DRAW_OVAL);
		
		selectTool(ovalTool);
	}

	public void setRoundMode() {
		setMode(PaintMode.DRAW_ROUND);
		
		selectTool(roundRectTool);
	}

	public void setModeDraw() {
		rectDraw = true;
		tool.setDraw(rectDraw);
	}

	public void setModeFill() {
		rectDraw = false;
		tool.setDraw(rectDraw);
	}
	
	public void setModeShift(boolean shift) {
		this.shift = shift;
		tool.setShift(shift);
	}

	public void setMode(PaintMode mode) {

		if(tool!=null) {
			tool.deselect();
		}

		//tool = selected Tool;
		//tool.select

		this.mode = mode;

	}

	public void openColorPickerWindow() {
		System.out.println("openColorPickerWindow");
	}

	@Override
	public void draw(Graphic g) {

		if(zoom>0) {
			g.setTransform(AffineTransform.getScaleInstance(zoom+1, zoom+1));
			g.drawImage(screen, startScreenX, startScreenY);
			g.setTransform(AffineTransform.getScaleInstance(1, 1));
		}else if(zoom<0) {
			g.setTransform(AffineTransform.getScaleInstance((double)(1.0/(-zoom+1)), (double)(1.0/(-zoom+1))));
			g.drawImage(screen, startScreenX, startScreenY);
			g.setTransform(AffineTransform.getScaleInstance(1, 1));
		}

		else{
			g.drawImage(screen, startScreenX, startScreenY);
		}

		if(mode==PaintMode.RECTANGULAR_MARK) {

			if(temporary) {
				drawUndefinedRectangularMark(g);
			}

		}

		if(mode==PaintMode.ERASER) {
			drawEraser(g);
		}

		if((mode==PaintMode.DRAW_RECT)||(mode==PaintMode.DRAW_OVAL)||(mode==PaintMode.DRAW_ROUND||mode==PaintMode.DRAW_LINE)) {

			if(tool.isTemporary()) {
			
				tool.draw(mx, my, g);
				
			}


		}

		drawCoordinates(g);


	}

	private void drawCoordinates(Graphic g) {

		g.setColor(Color.WHITE);

		if((mx>startScreenX)&&(my>startScreenY)) {
			g.drawString(Integer.toString(mx-startScreenX)+","+Integer.toString(my-startScreenY),875, 530);
		}

	}

	@Override
	public GUIEvent updateKeyboard(KeyEvent event) {

		if(event.isKeyDown(KeyEvent.TSK_SHIFT_DIREITA)||event.isKeyDown(KeyEvent.TSK_SHIFT_ESQUERDA)) {
			setModeShift(true);
			
			System.out.println("Shift true");
			return GUIEvent.UPDATE_MOUSE;
		}

		if(event.isKeyUp(KeyEvent.TSK_SHIFT_DIREITA)||event.isKeyUp(KeyEvent.TSK_SHIFT_ESQUERDA)) {
			setModeShift(false);
			
			System.out.println("Shift false");
			return GUIEvent.UPDATE_MOUSE;
		}

		//Eraser Growing
		if(event.isKeyDown(KeyEvent.TSK_IGUAL)) {
			if(mode==PaintMode.ERASER) {
				eraserSize+=2;
			}
		}
		else if(event.isKeyDown(KeyEvent.TSK_MENOS)) {
			if(mode==PaintMode.ERASER) {
				eraserSize-=2;
			}
		}

		return GUIEvent.NONE;
	}

	@Override
	public GUIEvent updateMouse(PointerEvent event) {

		mx = event.getX();
		my = event.getY();		

		//TODO Change to colision
		if((mx>startScreenX)&&(my>startScreenY&&(my<colorToolBarY))) {

			switch(mode) {

			case RECTANGULAR_MARK:
				rectangularMarkModeEvent(event);
				break;

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
			case DRAW_RECT:
			case DRAW_OVAL:
			case DRAW_ROUND:
				//rectModeEvent(event);
				tool.handleEvent(event);

				break;
				
			}

		}

		if(event.onButtonDown(MouseButton.MOUSE_WHEEL_UP)||event.onButtonDown(MouseButton.MOUSE_WHEEL_DOWN)) {
			zoom-=event.getAmount();
		}

		return GUIEvent.NONE;
	}

	private void paintCanModeEvent(PointerEvent event) {

		if(event.onButtonUp(MouseButton.MOUSE_BUTTON_LEFT)) {
			floodFill(mx-startScreenX, my-startScreenY, primaryColor.getRGB());
		}

		if(event.onButtonUp(MouseButton.MOUSE_BUTTON_RIGHT)) {
			floodFill(mx-startScreenX, my-startScreenY, secundaryColor.getRGB());
		}

	}

	private boolean erasing = false;
	private int eraserSize = 10;

	private void eraserModeEvent(PointerEvent event) {

		//Erase
		if(event.onButtonDown(MouseButton.MOUSE_BUTTON_LEFT)) {

			erasing = true;

		}
		else if(event.onButtonUp(MouseButton.MOUSE_BUTTON_LEFT)) {

			erasing = false;

		}

	}

	private void drawEraser(Graphic g) {
		g.setColor(secundaryColor);
		g.fillRect(mx-eraserSize/2, my-eraserSize/2, eraserSize, eraserSize);

		g.setColor(Color.BLACK);
		g.drawRect(mx-eraserSize/2, my-eraserSize/2, eraserSize, eraserSize);
	}

	private int startPointX = 0;
	private int startPointY = 0;

	private boolean pencilLeftPressed = false;
	private boolean pencilRightPressed = false;

	private void pencilModeEvent(PointerEvent event) {

		if(event.onButtonDown(MouseButton.MOUSE_BUTTON_LEFT)) {
			pencilLeftPressed = true;
		}
		if(event.onButtonUp(MouseButton.MOUSE_BUTTON_LEFT)) {
			pencilLeftPressed = false;
		}

		if(event.onButtonDown(MouseButton.MOUSE_BUTTON_RIGHT)) {
			pencilRightPressed = true;
		}
		if(event.onButtonUp(MouseButton.MOUSE_BUTTON_RIGHT)) {
			pencilRightPressed = false;
		}

	}

	private void rectangularMarkModeEvent(PointerEvent event) {

		if(event.onButtonDown(MouseButton.MOUSE_BUTTON_LEFT)) {
			setStartPoint();
			temporary = true;
		}

		if(event.onButtonDown(MouseButton.MOUSE_BUTTON_RIGHT)) {
			setStartPoint();
			temporary = true;
		}

		if(event.onButtonUp(MouseButton.MOUSE_BUTTON_LEFT)||event.onButtonUp(MouseButton.MOUSE_BUTTON_RIGHT)) {

			//TODO RectangularMark
			temporary = false;
		}
	}

	private void dropperModeEvent(PointerEvent event) {

		if(event.onButtonDown(MouseButton.MOUSE_BUTTON_LEFT)) {
			if(mx>startScreenX&&my>startScreenY) {

				setPrimaryColor(new Color(screen.getRGB(mx-startScreenX, my-startScreenY)));				

			}
		}

		if(event.onButtonDown(MouseButton.MOUSE_BUTTON_RIGHT)) {
			if(mx>startScreenX&&my>startScreenY) {

				setSecundaryColor(new Color(screen.getRGB(mx-startScreenX, my-startScreenY)));

			}
		}

	}

	private void setStartPoint() {

		startPointX = mx;
		startPointY = my;

	}

	private void drawPixel(Color color) {
		//screenGraphics.setColor(color);
		screen.setRGB(mx-startScreenX, my-startScreenY, color.getRGB());
		//screenGraphics.drawRect(mx-startScreenX, my-startScreenY, 1, 1);
	}

	private void drawUndefinedRectangularMark(Graphic g) {

		g.setStroke(dashed);

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

		g.setColor(Color.BLACK);
		g.drawRect(ix, iy, difx, dify);

		g.setColor(Color.WHITE);
		g.drawRect(ix+1, iy+1, difx, dify);


	}

	private void floodFill(int mx, int my, int paintColor) {

		int w = screen.getWidth();
		int h = screen.getHeight();

		int searchColor = screen.getRGB(mx, my); 

		boolean[][] painted = new boolean[w][h];

		if(screen.getRGB(mx,my)==searchColor && !painted[mx][my]) {

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

	@Override
	public void update(long now) {

		if(erasing) {
			screenGraphics.setColor(secundaryColor);
			screenGraphics.fillRect(mx-startScreenX-eraserSize/2, my-startScreenY-eraserSize/2, eraserSize, eraserSize);
		}

		//TODO DrawLine
		if(pencilLeftPressed) {

			drawPixel(primaryColor);

		}else if(pencilRightPressed) {

			drawPixel(secundaryColor);

		}
	}

}