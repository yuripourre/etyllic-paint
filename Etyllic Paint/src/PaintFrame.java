

import br.com.etyllica.Etyllica;
import br.com.etyllica.paint.EtyllicPaint;

public class PaintFrame extends Etyllica {

	private static final long serialVersionUID = 1L;

	public PaintFrame() {
		super(960, 540);
	}
	
	@Override
	public void startGame() {
		
		setMainApplication(new EtyllicPaint(w,h));
	}
	
}
