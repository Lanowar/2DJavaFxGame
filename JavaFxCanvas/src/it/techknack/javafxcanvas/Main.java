package it.techknack.javafxcanvas;

import java.util.ArrayList;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Main extends Application {

	private Entity player, shield, enemy;
	private BulletWeapon pw, ew;

	private ArrayList<Bullet> bulletList = new ArrayList<Bullet>();

	private boolean goLeft, goRight, shooting;
	private int plX = 256 - 63;
	private int enX = 256 - 95;

	public static void main(String[] args) {

		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {

		Group root = new Group();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("JavaFxCanvas Test 0.1a");

		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent e) {

				switch (e.getCode()) {

				case LEFT:
					goLeft = true;
					break;
				case RIGHT:
					goRight = true;
					break;
				case UP:
					shooting = true;
					break;
				case A:
					goLeft = true;
					break;
				case D:
					goRight = true;
					break;
				case W:
					shooting = true;
					break;

				}
			}
		});

		scene.setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent e) {

				switch (e.getCode()) {

				case ESCAPE:
					System.exit(0);
					break;
				case LEFT:
					goLeft = false;
					break;
				case RIGHT:
					goRight = false;
					break;
				case UP:
					shooting = false;
					break;
				case A:
					goLeft = false;
					break;
				case D:
					goRight = false;
					break;
				case W:
					shooting = false;
					break;

				}
			}
		});

		Canvas canvas = new Canvas(512, 512);
		root.getChildren().add(canvas);

		player = new Player();
		shield = new Shield();
		enemy = new Enemy();

		Image bg = new Image(getClass().getResourceAsStream("bg.png"));

		Image sh = shield.getImage() == null ? null : shield.getImage();
		Image pl = player.getImage() == null ? null : player.getImage();
		Image en = enemy.getImage() == null ? null : enemy.getImage();

		GraphicsContext gc = canvas.getGraphicsContext2D();

		pw = new PlayerWeapon();
		ew = new EnemyWeapon();

		final long startNanoTimePlayerWeapon = System.nanoTime();
		final long startNanoTimeEnemyWeapon = System.nanoTime();

		new AnimationTimer() {

			public void handle(long currentNanoTime) {

				double tPlayerWeapon = (currentNanoTime - startNanoTimePlayerWeapon) / 1000000000.0;
				double tEnemyWeapon = (currentNanoTime - startNanoTimeEnemyWeapon) / 1000000000.0;

				int x = 0;

				// shield
				if (shield.isAlive())
					x = (int) ((256 - 99) * (Math.sin(tPlayerWeapon) + 1));

				// enemy
				if (enemy.isAlive())
					enX = (enX > plX) ? enX - 2 : (enX < plX) ? enX + 2 : enX;

				// player
				if (player.isAlive()) {
					if (goLeft)
						plX = (plX <= 0) ? plX : plX - 4;
					if (goRight)
						plX = (plX >= 512 - 125) ? plX : plX + 4;
				}

				gc.drawImage(bg, 0, 0);

				if (shield.isAlive())
					gc.drawImage(sh, x, 512 - 115 - 93);
				else
					sh.cancel();

				if (player.isAlive())
					gc.drawImage(pl, plX, 512 - 115);
				else
					pl.cancel();

				if (enemy.isAlive())
					gc.drawImage(en, enX, 0);
				else
					en.cancel();

				// Print on screen data
				gc.setFont(Font.font("Arial", FontWeight.BOLD, 12));
				if (enemy.isAlive()) {

					gc.setFill(Color.RED);
					gc.fillText("HP: " + enemy.getHp(), 20, 20);
				}

				if (player.isAlive()) {

					gc.setFill(Color.GREEN);
					gc.fillText("HP: " + player.getHp(), 450, 492);
				}

				if (shield.isAlive()) {

					gc.setFill(Color.WHITE);
					gc.fillText("" + shield.getHp(), x + 30, 512 - 115 - 80);
				}

				// Linea bersagli
				player.setPosition(plX, plX + 125, 512 - 100);

				if (shield.isAlive())
					shield.setPosition(x, x + 198, 512 - 115 - 60);

				enemy.setPosition(enX, enX + 118, 90);

				// Sparo
				if (ew.canShoot() && enemy.isAlive() && player.isAlive()) {

					Random randCD = new Random();

					Bullet bullet = ew.createBullet(enX + 60, EnemyWeapon.INITIAL_Y);
					ew.setCoolDown((randCD.nextInt(60) / 60.0f) * 1000000000);

					bulletList.add(bullet);
				}

				if (shooting && pw.canShoot() && player.isAlive() && enemy.isAlive()) {

					Bullet bullet = pw.createBullet(plX + 60, PlayerWeapon.INITIAL_Y);
					bulletList.add(bullet);
				}

				// Movimento proiettili
				for (int i = 0; i < bulletList.size(); i++) {

					Bullet bullet = bulletList.get(i);

					bullet.travel();

					gc.drawImage(bullet.getImage(), bullet.getPositionX(), bullet.getPositionY());

					if (bullet.getPositionY() >= 0 && bullet.getPositionY() <= 512) {

						// Bersaglio colpito?
						if (bullet.getPositionX() >= player.getStartX() && bullet.getPositionX() < player.getEndX()
								&& bullet.getPositionY() >= player.getyOffs()) {

							// Player colpito!
							bulletList.remove(i);
							player.setHp(player.getHp() - ew.getDamage());
						}

						else if (bullet.getPositionX() >= enemy.getStartX() && bullet.getPositionX() < enemy.getEndX()
								&& bullet.getPositionY() <= enemy.getyOffs()) {

							// Nemico colpito!
							bulletList.remove(i);
							enemy.setHp(enemy.getHp() - pw.getDamage());
						}

						else if (bullet.getPositionX() >= shield.getStartX() && bullet.getPositionX() < shield.getEndX()
								&& bullet.getPositionY() <= shield.getyOffs() + 20
								&& bullet.getPositionY() >= shield.getyOffs() - 20 && shield.isAlive()) {

							// Scudo colpito!
							if (bullet.getDirection() == Bullet.DIR_DW) {

								shield.setHp(shield.getHp() - ew.getDamage());
							} else

								shield.setHp(shield.getHp() - pw.getDamage());
							bulletList.remove(i);
						}

						// Controlla se ha ucciso scudo
						if (shield.getHp() <= 0)
							shield.dead();

						// Controlla se ha ucciso pl/en
						if (player.getHp() <= 0) {

							player.dead();

							gc.setFont(Font.font("Arial", FontWeight.BOLD, 48));

							gc.setFill(Color.RED);
							gc.fillText("Hai perso!", 50, 200);
						}
						if (enemy.getHp() <= 0) {

							enemy.dead();

							gc.setFont(Font.font("Arial", FontWeight.BOLD, 48));

							gc.setFill(Color.GREEN);
							gc.fillText("Hai vinto!", 50, 200);
						}

					} else
						bulletList.remove(i);
				}
			}

		}.start();

		stage.show();
	}

}
