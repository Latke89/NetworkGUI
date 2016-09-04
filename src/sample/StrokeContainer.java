package sample;

/**
 * Created by Brett on 9/4/16.
 */
public class StrokeContainer {

	double posX = 0;
	double posY = 0;
	int strokeSize = 0;

	public StrokeContainer(double posX, double posY, int strokeSize) {
		this.posX = posX;
		this.posY = posY;
		this.strokeSize = strokeSize;
	}

	public StrokeContainer() {
	}

	public double getPosX() {
		return posX;
	}

	public void setPosX(double posX) {
		this.posX = posX;
	}

	public double getPosY() {
		return posY;
	}

	public void setPosY(double posY) {
		this.posY = posY;
	}

	public int getStrokeSize() {
		return strokeSize;
	}

	public void setStrokeSize(int strokeSize) {
		this.strokeSize = strokeSize;
	}
}
