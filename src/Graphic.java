import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JComponent;


public class Graphic extends JComponent{	
	private static final long serialVersionUID = 1L;
	private int[][] m = new int[2][6];
	private int Xmin, Xmax, Ymin, Ymax, tx, ty;
	private int[][] matrix;
	private int[][] matrixMoon = null;
	private int[][] matrixCar;
	
	protected void paintComponent(Graphics g1) {
		Graphics2D g = (Graphics2D) g1;
		
		street(g);
		lines(g);
		moon(g);
		car(g);
	}
	private void street(Graphics2D g) {
		//First part
		g.drawLine(mapX(-600), mapY(-500), mapX(-25), mapY(100));
		g.drawLine(mapX(560), mapY(-500), mapX(25), mapY(100));
		//Second part
		g.drawLine(mapX(-640), mapY(-500), mapX(-30), mapY(100));
		g.drawLine(mapX(600), mapY(-500), mapX(30), mapY(100));
	}
	
	private void lines(Graphics2D g) {
		//
		g.drawLine(mapX(18), mapY(-500), mapX(8), mapY(-250));
		g.drawLine(mapX(-18), mapY(-500), mapX(-8), mapY(-250));
		g.drawLine(mapX(8), mapY(-250), mapX(-8), mapY(-250));
		
		//
		g.drawLine(mapX(6), mapY(-100), mapX(5), mapY(-50));
		g.drawLine(mapX(-6), mapY(-100), mapX(-5), mapY(-50));
		g.drawLine(mapX(6), mapY(-100), mapX(-6), mapY(-100));
		g.drawLine(mapX(5), mapY(-50), mapX(-5), mapY(-50));
		
		//
		g.drawLine(mapX(5), mapY(0), mapX(4), mapY(25));
		g.drawLine(mapX(-5), mapY(0), mapX(-4), mapY(25));
		g.drawLine(mapX(5), mapY(0), mapX(-5), mapY(0));
		g.drawLine(mapX(4), mapY(25), mapX(-4), mapY(25));
		
		//
		g.drawLine(mapX(4), mapY(50), mapX(3), mapY(65));
		g.drawLine(mapX(-4), mapY(50), mapX(-3), mapY(65));
		g.drawLine(mapX(4), mapY(50), mapX(-4), mapY(50));
		g.drawLine(mapX(3), mapY(65), mapX(-3), mapY(65));
		
		//
		g.drawLine(mapX(3), mapY(80), mapX(2), mapY(90));
		g.drawLine(mapX(-3), mapY(80), mapX(-2), mapY(90));
		g.drawLine(mapX(3), mapY(80), mapX(-3), mapY(80));
		g.drawLine(mapX(2), mapY(90), mapX(-2), mapY(90));
		
		//----//
		g.drawLine(mapX(500), mapY(100), mapX(-500), mapY(100));
	}
	
	private void moon(Graphics2D g) {
		Ellipse2D circle = new Ellipse2D.Float(mapX(matrixMoon[0][0]), mapY(matrixMoon[1][0]), 100, 100);
		g.setColor(Color.LIGHT_GRAY);
		g.fill(circle);
	}
	
	private void car(Graphics2D g) {
		g.setColor(Color.RED);
		g.drawRect(mapX(matrixCar[0][0]), mapY(matrixCar[1][0]), matrixCar[0][1], matrixCar[1][1]);
	}
	
	public void defineMoon() {
		this.matrixMoon = new int[2][1];
		this.matrixMoon[0][0] = 400;
		this.matrixMoon[1][0] = 350;
	}

	public void defineCar() {
		this.matrixCar = new int[2][2];
		this.matrixCar[0][0] = 85;
		this.matrixCar[1][0] = -300;
		//Size
		this.matrixCar[0][1] = 105;
		this.matrixCar[1][1] = 100;
	}
	
	public void decreaseCar(double x1, double x2){ //This method is to change size of car
		this.matrixCar[0][1] = (int) (this.matrixCar[0][1] - x1);
		this.matrixCar[1][1] = (int) (this.matrixCar[1][1] -x2);
	}
	
	
	public void initTimer(int angulo, int timer, int carDistance) {
		new BasicThread(this,angulo, timer, carDistance).start();
	}

	public void translation(double d, int ty) {
		for (int i = 0; i < 1; i++) {
			this.matrixCar[0][i] += d;
			this.matrixCar[1][i] += ty;
		}
		repaint();
	}
	
	public void rotation(double d, int linha, int coluna) {
		int soma = 0;
		int[][] matriz = new int[linha][coluna];
		double[][] newMatriz = mountNewMatriz(d);
		for (int k = 0; k < linha; k++) {
			for (int i = 0; i < coluna; i++) {
				for (int j = 0; j < linha; j++) {
					soma += newMatriz[k][j] * matrixMoon[j][i];
				}
				matriz[k][i] = soma;
				soma = 0;
			}
		}
		matrixMoon = matriz;
		repaint();
	}
	
	public void scale(double ex, double ey) {
		double sum = 0;
		int[][] matrix = this.matrixCar;
		double[][] newMatriz = mountNewMatriz(ex, ey);
		for (int k = 0; k < 2; k++) {
			for (int i = 0; i < 1; i++) {
				for (int j = 0; j < 2; j++) {
					sum = sum + (newMatriz[j][k] * matrix[k][i]);
				}
				matrix[k][i] = (int) sum;
				sum = 0;
			}
		}
		this.matrixCar = matrix;
		repaint();
	}

	private double[][] mountNewMatriz(double angulo) {
		double[][] newMatriz = new double[2][2];
		final double radians = angulo * Math.PI / 180;
		newMatriz[0][0] = Math.cos(radians);
		newMatriz[0][1] = -Math.sin(radians);
		newMatriz[1][0] = Math.sin(radians);
		newMatriz[1][1] = Math.cos(radians);
		return newMatriz;
	}
	
	private double[][] mountNewMatriz(double ex, double ey) {
		double[][] newMatriz = new double[2][2];
		newMatriz[0][0] = ex;
		newMatriz[0][1] = 0;
		newMatriz[1][0] = 0;
		newMatriz[1][1] = ey;
		return newMatriz;
	}
	
	public void setYmax(int i) {
		this.Ymax = i;
	}

	public void setXmax(int i) {
		this.Xmax = i;
	}

	public void setYmin(int i) {
		this.Ymin = i;
	}

	public void setXmin(int i) {
		this.Xmin = i;
	}

	public void settx(int i) {
		this.tx = i;
	}

	public void settY(int i) {
		this.ty = i;
	}
	
	public void defineDots(int[][] matri) {
		this.matrix = matri;
		this.matrix[0][0] = 100;
		this.matrix[1][0] = 100;
		this.matrix[0][1] = 200;
		this.matrix[1][1] = 100;
		this.matrix[0][2] = 200;
		this.matrix[1][2] = 100;
		this.matrix[0][3] = 150;
		this.matrix[1][3] = 187;
		this.matrix[0][4] = 150;
		this.matrix[1][4] = 187;
		this.matrix[0][5] = 100;
		this.matrix[1][5] = 100;
	}
	
	public int mapX(float Xmundo) {

		return Math.round((Xmundo - Xmin) / (Xmax - Xmin) * tx);
	}

	public int mapY(float Ymundo) {

		return Math.round((1 - (Ymundo - Ymin) / (Ymax - Ymin)) * ty);
	}

}
