package it.techknack.javafxcanvas;

public class PlayerWeapon extends BulletWeapon {

	public static final int INITIAL_Y = 512-115;
	
	public PlayerWeapon() {
		
		super("gbull.png", 10, Bullet.DIR_UP);
	}
}
