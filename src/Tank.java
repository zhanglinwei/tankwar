//Tank.java

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.Random;



public class Tank {
	private int x;
	private int y;
	private int size = 50;
	private int xSpeed = 10;
	private int ySpeed = 10;
	private boolean bU = false;
	private boolean bD = false;
	private boolean bL = false;
	private boolean bR = false;
	private Direction dir = Direction.STOP;
	private Direction fireDir = Direction.R;
	private boolean good = true;
	private static Random rand = new Random();
	private int moveStep = 0;
	private int moveSpeed = 5;
	private int fireStep = 0;
	private int fireSpeed = 100;
	private int turnStep = 0;
	private int turnSpeed = 100;
	private int oriX = 0;
	private int oriY = 0;
	private int superFireSize = 100;
	private int totalLife = 100;
	private int life = 0;
	private int lifeBarLength = 7;
	private int lifeBarWidth = 70;

	private Color goodColor = Color.RED;
	private Color enemyColor = Color.DARK_GRAY;
	private Color fireColor = Color.BLUE;
	private Color lifeBarColor = Color.BLUE;
	private TankClient tc = null;

	enum Direction {
		U, D, L, R, LU, RU, LD, RD, STOP
	};

	/**
	 * 构造方法
	 * @param x 坦克出现位置的x坐标
	 * @param y 坦克出现位置的y坐标
	 * @param good 坦克的类别
	 */

	public Tank(int x, int y, boolean good) {
		this.x = x;
		this.y = y;
		this.good = good;
		this.life = this.totalLife;
	}

	/**
	 * 构造方法
	 * @param x 坦克出现位置的x坐标
	 * @param y 坦克出现位置的y坐标
	 * @param good 坦克的类别
	 * @param tc 坦克所属主类的引用
	 */
	
	public Tank(int x, int y, boolean good, TankClient tc) {
		this(x, y, good);
		this.tc = tc;
		moveStep = rand.nextInt(moveSpeed);
		fireStep = rand.nextInt(fireSpeed);
		turnStep = rand.nextInt(turnSpeed);
	}

	/**
	 * 在屏幕上画出它自己
	 * @param g 图形类
	 */

	public void draw(Graphics g) {
		Color orgColor = g.getColor();
		if (good)
			g.setColor(goodColor);
		else {
			g.setColor(enemyColor);
			turnStep++;
			if (turnStep == turnSpeed) {
				Direction[] dirs = Direction.values();
				dir = dirs[rand.nextInt(dirs.length)];
				if (dir != Direction.STOP)
					fireDir = dir;
				turnStep = 0;
			}
			moveStep++;
			if (moveStep == moveSpeed) {
				move();
				moveStep = 0;
			}
			fireStep++;
			if (fireStep == fireSpeed) {
				fire();
				fireStep = 0;
			}
		}
		g.fillOval(x, y, size, size);

		g.setColor(fireColor);
		switch (fireDir) {
		case U:
			g.drawLine(x + size / 2, y + size / 2, x + size / 2, y);
			break;
		case D:
			g.drawLine(x + size / 2, y + size / 2, x + size / 2, y + size);
			break;
		case L:
			g.drawLine(x + size / 2, y + size / 2, x, y + size / 2);
			break;
		case R:
			g.drawLine(x + size / 2, y + size / 2, x + size, y + size / 2);
			break;
		case LU:
			g.drawLine(x + size / 2, y + size / 2, x, y);
			break;
		case RU:
			g.drawLine(x + size / 2, y + size / 2, x + size, y);
			break;
		case LD:
			g.drawLine(x + size / 2, y + size / 2, x, y + size);
			break;
		case RD:
			g.drawLine(x + size / 2, y + size / 2, x + size, y + size);
			break;
		}

		// lifeBarlifeBarLength
		g.setColor(lifeBarColor);
		g.drawRect(x + size / 2 - lifeBarWidth / 2, y - 5 - lifeBarLength,
				lifeBarWidth, lifeBarLength);
		g.setColor(Color.RED);
		g.fillRect(
				x + size / 2 - lifeBarWidth / 2 + 2,
				y - 5 - lifeBarLength + 2,
				(int) ((double) life / (double) totalLife * (double) (lifeBarWidth - 4)+1),
				lifeBarLength - 3);

		g.setColor(orgColor);
	}

	private void move() {
		switch (dir) {
		case U:
			y -= ySpeed;
			break;
		case D:
			y += ySpeed;
			break;
		case L:
			x -= xSpeed;
			break;
		case R:
			x += xSpeed;
			break;
		case LU:
			x -= xSpeed;
			y -= ySpeed;
			break;
		case RU:
			x += xSpeed;
			y -= ySpeed;
			break;
		case LD:
			x -= xSpeed;
			y += ySpeed;
			break;
		case RD:
			x += xSpeed;
			y += ySpeed;
			break;
		case STOP:
			break;
		}
		boolean condition = x < 0 || x + size > TankClient.GAME_WIDTH
				|| y - 20 < 0 || y + size > TankClient.GAME_HEIGHT;
		for (int i = 0; i < tc.getWalls().size(); i++) {
			Wall wall = tc.getWalls().get(i);
			if (this.getRect().intersects(wall.getRect()))
				condition = true;
		}
		for (int i = 0; i < tc.getTanks().size(); i++) {
			Tank t = tc.getTanks().get(i);
			if (!this.equals(t) && this.getRect().intersects(t.getRect()))
				condition = true;
		}
		if (condition) {
			x = oriX;
			y = oriY;
			return;
		}
		oriX = x;
		oriY = y;
	}

	/**
	 * 键盘按下的处理
	 * @param key 键位值
	 */

	public void keyPressed(int key) {
		if (good) {
			switch (key) {
			case KeyEvent.VK_UP:
				bU = true;
				makeDir();
				move();
				break;
			case KeyEvent.VK_DOWN:
				bD = true;
				makeDir();
				move();
				break;
			case KeyEvent.VK_LEFT:
				bL = true;
				makeDir();
				move();
				break;
			case KeyEvent.VK_RIGHT:
				bR = true;
				makeDir();
				move();
				break;
			case KeyEvent.VK_CONTROL:
				fire();
				break;
			case KeyEvent.VK_A:
				bigFire();
				break;
			case KeyEvent.VK_X:
				xFire();
				break;
			}
		}
	}

	private void fire() {
		tc.getMissiles().add(
				new Missile(x + size / 2, y + size / 2, fireDir, this, tc));
	}

	private void bigFire() {
		tc.getMissiles().add(
				new Missile(x + size / 2, y + size / 2, fireDir, this, tc,
						superFireSize));
	}

	private void xFire() {
		Direction[] dirs = Direction.values();
		for (int i = 0; i < dirs.length - 1; i++)
			tc.getMissiles().add(
					new Missile(x + size / 2, y + size / 2, dirs[i], this, tc));
	}

	/**
	 * 按键释放的处理
	 * @param key 键位值
	 */

	public void keyReleased(int key) {
		if (good) {
			switch (key) {
			case KeyEvent.VK_UP:
				bU = false;
				makeDir();
				break;
			case KeyEvent.VK_DOWN:
				bD = false;
				makeDir();
				break;
			case KeyEvent.VK_LEFT:
				bL = false;
				makeDir();
				break;
			case KeyEvent.VK_RIGHT:
				bR = false;
				makeDir();
				break;
			}
		}
	}

	private void makeDir() {
		if (bU && !bD && !bL && !bR)
			fireDir = dir = Direction.U;
		if (!bU && bD && !bL && !bR)
			fireDir = dir = Direction.D;
		if (!bU && !bD && bL && !bR)
			fireDir = dir = Direction.L;
		if (!bU && !bD && !bL && bR)
			fireDir = dir = Direction.R;
		if (bU && !bD && bL && !bR)
			fireDir = dir = Direction.LU;
		if (bU && !bD && !bL && bR)
			fireDir = dir = Direction.RU;
		if (!bU && bD && bL && !bR)
			fireDir = dir = Direction.LD;
		if (!bU && bD && !bL && bR)
			fireDir = dir = Direction.RD;
	}

	public Rectangle getRect() {
		return new Rectangle(x, y, size, size);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getxSpeed() {
		return xSpeed;
	}

	public void setxSpeed(int xSpeed) {
		this.xSpeed = xSpeed;
	}

	public int getySpeed() {
		return ySpeed;
	}

	public void setySpeed(int ySpeed) {
		this.ySpeed = ySpeed;
	}

	public boolean isbU() {
		return bU;
	}

	public void setbU(boolean bU) {
		this.bU = bU;
	}

	public boolean isbD() {
		return bD;
	}

	public void setbD(boolean bD) {
		this.bD = bD;
	}

	public boolean isbL() {
		return bL;
	}

	public void setbL(boolean bL) {
		this.bL = bL;
	}

	public boolean isbR() {
		return bR;
	}

	public void setbR(boolean bR) {
		this.bR = bR;
	}

	public Direction getDir() {
		return dir;
	}

	public void setDir(Direction dir) {
		this.dir = dir;
	}

	public Direction getFireDir() {
		return fireDir;
	}

	public void setFireDir(Direction fireDir) {
		this.fireDir = fireDir;
	}

	public boolean isGood() {
		return good;
	}

	public void setGood(boolean good) {
		this.good = good;
	}

	public Color getGoodColor() {
		return goodColor;
	}

	public void setGoodColor(Color goodColor) {
		this.goodColor = goodColor;
	}

	public Color getEnemyColor() {
		return enemyColor;
	}

	public void setEnemyColor(Color enemyColor) {
		this.enemyColor = enemyColor;
	}

	public Color getFireColor() {
		return fireColor;
	}

	public void setFireColor(Color fireColor) {
		this.fireColor = fireColor;
	}

	public TankClient getTc() {
		return tc;
	}

	public void setTc(TankClient tc) {
		this.tc = tc;
	}

	public static Random getRand() {
		return rand;
	}

	public static void setRand(Random rand) {
		Tank.rand = rand;
	}

	public int getMoveStep() {
		return moveStep;
	}

	public void setMoveStep(int moveStep) {
		this.moveStep = moveStep;
	}

	public int getMoveSpeed() {
		return moveSpeed;
	}

	public void setMoveSpeed(int moveSpeed) {
		this.moveSpeed = moveSpeed;
	}

	public int getFireStep() {
		return fireStep;
	}

	public void setFireStep(int fireStep) {
		this.fireStep = fireStep;
	}

	public int getFireSpeed() {
		return fireSpeed;
	}

	public void setFireSpeed(int fireSpeed) {
		this.fireSpeed = fireSpeed;
	}

	public int getTurnStep() {
		return turnStep;
	}

	public void setTurnStep(int turnStep) {
		this.turnStep = turnStep;
	}

	public int getTurnSpeed() {
		return turnSpeed;
	}

	public void setTurnSpeed(int turnSpeed) {
		this.turnSpeed = turnSpeed;
	}

	public int getOriX() {
		return oriX;
	}

	public void setOriX(int oriX) {
		this.oriX = oriX;
	}

	public int getOriY() {
		return oriY;
	}

	public void setOriY(int oriY) {
		this.oriY = oriY;
	}

	public int getSuperFireSize() {
		return superFireSize;
	}

	public void setSuperFireSize(int superFireSize) {
		this.superFireSize = superFireSize;
	}

	public int getTotalLife() {
		return totalLife;
	}

	public void setTotalLife(int totalLife) {
		this.totalLife = totalLife;
	}

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		if(life>this.totalLife)
			life=this.totalLife;
		if(life<0)
			life=0;
		this.life = life;
	}
}
