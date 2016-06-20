package it.techknack.javafxcanvas;

import java.util.Random;

public class EnemyWeapon extends BulletWeapon {

	public static final int INITIAL_Y = 120;

	public EnemyWeapon() {

		super("rbull.png", 10, Bullet.DIR_DW);
		setSpeed(10);
	}

}
