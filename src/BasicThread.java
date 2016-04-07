public class BasicThread extends Thread {
	private int distance;
	private Graphic graphic;
	private int angle;

	public BasicThread(Graphic graphic, int angle, int timer, int distance) {
		this.graphic = graphic;
		this.angle = angle;	
		this.distance = distance;
	}
@Override
	public void run() {
		int i = 0;
		int j = 0;
		while (i < angle || j < distance) {
			if (i < angle) {
				graphic.rotation(1.7, 2, 1);
				threadSleep(70);
				i++;
			}
			if (j < distance) {
				graphic.translation(0, 6);
			    graphic.translation(-0.0001, 0);
			    graphic.decreaseCar(1, 1);
				threadSleep(10);
				j++;
			}
		}
	}

	private void threadSleep(int time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
