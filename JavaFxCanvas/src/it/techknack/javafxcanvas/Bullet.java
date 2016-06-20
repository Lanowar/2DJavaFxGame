package it.techknack.javafxcanvas;

import javafx.scene.image.Image;

public class Bullet {

	private Image image;
	private int positionX;
	private int positionY;
	private int direction;
	private int speed;

	public static final int DIR_UP = 0;
	public static final int DIR_DW = 1;

	public Bullet(Image image, int positionX, int positionY, int direction, int speed) {

		this.image = image;
		this.positionX = positionX;
		this.positionY = positionY;
		this.direction = direction;
		this.speed = speed;
	}

	public int travel() {

		switch (direction) {

			case DIR_UP:
				positionY -= speed;
				break;
			case DIR_DW:
				positionY += speed;
				break;
		}
		
		return positionY;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public Image getImage() {

		return image;
	}

	public void setImage(Image image) {

		this.image = image;
	}

	public int getPositionX() {

		return positionX;
	}

	public void setPositionX(int positionX) {

		this.positionX = positionX;
	}

	public int getPositionY() {

		return positionY;
	}

	public void setPositionY(int positionY) {

		this.positionY = positionY;
	}
}
