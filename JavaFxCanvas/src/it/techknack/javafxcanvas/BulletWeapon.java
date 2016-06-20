package it.techknack.javafxcanvas;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class BulletWeapon {

	private int damage;
	
	private long lastShoot;
	private float coolDown;
	
	private Image image;
	
	private int speed = 6;
	private int direction;

	public BulletWeapon(String image, int damage, int direction) {

		this.damage = damage;
		this.setImage(image);
		
		this.lastShoot = System.nanoTime();
		this.coolDown = (13/60.0f) * 1000000000;
		this.direction = direction;
	}

	public boolean canShoot() {
		
		long now = System.nanoTime();
		
		if((now - lastShoot) >= coolDown) {
			
			this.lastShoot = now;
			return true;
		}
		else return false;
	}
	
	public Bullet createBullet(int initialX, int initialY) {
		
		return new Bullet(getImage(), initialX, initialY, this.direction, getSpeed());
	}

	public float getCoolDown() {
		return coolDown;
	}

	public void setCoolDown(float coolDown) {
		this.coolDown = coolDown;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	public int getDamage() {
		
		return damage;
	}

	public void setDamage(int damage) {
		
		this.damage = damage;
	}

	public Image getImage() {
		
		return image;
	}

	public void setImage(String resource) {
		
		this.image = new Image(getClass().getResourceAsStream(resource));
	}
}
