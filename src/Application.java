import javax.swing.JFrame;


public class Application {

	public static void main(String[] args) {
		final Graphic graf1 = new Graphic();
		graf1.settx(600);
		graf1.settY(600);
		graf1.setXmin(-500);
		graf1.setYmin(-500);
		graf1.setXmax(500);
		graf1.setYmax(500);
		graf1.defineDots(new int[2][6]);
		
		graf1.defineMoon();
		graf1.defineCar();
		
		JFrame obj = new JFrame();
		obj.getContentPane().add(graf1);
		obj.setSize(600, 600);
		obj.setVisible(true);
		obj.setLayout(null);
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		graf1.initTimer(61, 1000, 61);
	}

}
