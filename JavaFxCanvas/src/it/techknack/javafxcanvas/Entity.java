package it.techknack.javafxcanvas;

import javafx.scene.image.Image;

public class Entity {
	
	private int hp, startX, endX, yOffs;
	private boolean alive = true;
	
	private Image image;

	public Entity(String image) {
		
		this.setImage(image);
	}
	
	public Entity(String image, int hp) {
		
		this.setImage(image);
		this.setHp(hp);
	}

	public void setPosition(int startX, int endX, int yOffs) {
		
		this.setStartX(startX);
		this.setEndX(endX);
		this.setYOffs(yOffs);
	}
	
	public void dead() {
		
		setAlive(false);
		setPosition(-200,0,0);
		image.cancel();
	}
	
	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	public int getStartX() {
		
		return startX;
	}

	public void setStartX(int startX) {
		
		this.startX = startX;
	}

	public int getEndX() {
		
		return endX;
	}

	public void setEndX(int endX) {
		
		this.endX = endX;
	}

	public int getyOffs() {
		
		return yOffs;
	}

	public void setYOffs(int yOffs) {
		
		this.yOffs = yOffs;
	}

	public Image getImage() {
		
		return this.image;
	}
	
	public void setImage(String resource) {
		
		try {
			
			this.image = new Image(getClass().getResourceAsStream(resource));
			
		} catch(Exception e) {
			
		}
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}
}
