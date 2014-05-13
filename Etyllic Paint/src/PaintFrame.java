

import br.com.etyllica.EtyllicaFrame;
import br.com.etyllica.context.Application;
import br.com.etyllica.paint.EtyllicPaint;

public class PaintFrame extends EtyllicaFrame {

	private static final long serialVersionUID = 1L;

	public PaintFrame() {
		super(960, 540);
	}
	
	public static void main(String[] args){
		PaintFrame frame = new PaintFrame();
		frame.init();
	}
	
	@Override
	public Application startApplication() {
		
		return new EtyllicPaint(w,h);
	}

}
