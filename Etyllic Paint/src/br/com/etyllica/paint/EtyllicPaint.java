package br.com.etyllica.paint;

import java.awt.Color;
import java.awt.image.BufferedImage;

import br.com.etyllica.core.application.Application;
import br.com.etyllica.core.event.GUIEvent;
import br.com.etyllica.core.event.KeyboardEvent;
import br.com.etyllica.core.event.PointerEvent;
import br.com.etyllica.core.video.Grafico;
import br.com.etyllica.gui.Button;
import br.com.etyllica.gui.icon.ImageIcon;

public class EtyllicPaint extends Application{

	private BufferedImage screen;
	
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
		add(paintCan);
		
		
		dropper = new Button(toolBarX,toolBarY+buttonSize*2+2,buttonSize,buttonSize);
		//http://findicons.com/icon/238374/color_picker?id=238374
		dropper.setLabel(new ImageIcon(8, 8, "dropper.png"));
		add(dropper);
		
		magnify = new Button(toolBarX+buttonSize+1,toolBarY+buttonSize*2+2,buttonSize,buttonSize);
		//http://findicons.com/icon/219105/find?id=356081
		magnify.setLabel(new ImageIcon(8, 8, "magnify.png"));
		add(magnify);
		

		pencil = new Button(toolBarX,toolBarY+buttonSize*3+3,buttonSize,buttonSize);
		//http://findicons.com/icon/226814/pencil?width=32
		pencil.setLabel(new ImageIcon(8, 8, "pencil.png"));
		add(pencil);
		
		brush = new Button(toolBarX+buttonSize+1,toolBarY+buttonSize*3+3,buttonSize,buttonSize);
		//http://findicons.com/icon/237997/paintbrush?width=32
		brush.setLabel(new ImageIcon(8, 8, "paintbrush.png"));
		add(brush);
		
		sprayCan = new Button(toolBarX,toolBarY+buttonSize*4+4,buttonSize,buttonSize);
		//http://www.keywordpictures.com/abuse/spray%20can%20icon///
		sprayCan.setLabel(new ImageIcon(8, 8, "spray_can.png"));
		add(sprayCan);
		
		text = new Button(toolBarX+buttonSize+1,toolBarY+buttonSize*4+4,buttonSize,buttonSize);
		text.setLabel(new ImageIcon(8, 8, "text.png"));
		add(text);
		
		line = new Button(toolBarX,toolBarY+buttonSize*5+5,buttonSize,buttonSize);
		line.setLabel(new ImageIcon(8, 8, "line.png"));
		add(line);
		
		curve = new Button(toolBarX+buttonSize+1,toolBarY+buttonSize*5+5,buttonSize,buttonSize);
		curve.setLabel(new ImageIcon(8, 8, "curve.png"));
		add(curve);
		
		rect = new Button(toolBarX,toolBarY+buttonSize*6+6,buttonSize,buttonSize);
		rect.setLabel(new ImageIcon(8, 8, "rect.png"));
		add(rect);
		
		poly = new Button(toolBarX+buttonSize+1,toolBarY+buttonSize*6+6,buttonSize,buttonSize);
		poly.setLabel(new ImageIcon(8, 8, "poly.png"));
		add(poly);
		
		oval = new Button(toolBarX,toolBarY+buttonSize*7+7,buttonSize,buttonSize);
		oval.setLabel(new ImageIcon(8, 8, "oval.png"));
		add(oval);
		
		round = new Button(toolBarX+buttonSize+1,toolBarY+buttonSize*7+7,buttonSize,buttonSize);
		round.setLabel(new ImageIcon(8, 8, "round.png"));
		add(round);
		
		loading = 100;

	}

	@Override
	public void draw(Grafico g) {

		g.setColor(Color.BLUE);
		g.fillRect(98, y, w, h);

	}

	@Override
	public GUIEvent updateKeyboard(KeyboardEvent event) {

		return GUIEvent.NONE;
	}

	@Override
	public GUIEvent updateMouse(PointerEvent event) {

		return GUIEvent	.NONE;
	}

}
