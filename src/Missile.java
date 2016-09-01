//Missile.java

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;



public class Missile {

	private int x = 0;
	private int y = 0;
	private int size = 7;
	private Color color = Color.BLACK;
	private int xSpeed = 15;
	private int ySpeed = 15;
	private Tank.Direction dir = Tank.Direction.STOP;
	private TankClient tc = null;
	private Tank t = null;
	private int attackHurt = 10;
	private int bigAttackHurt = 100;

	/**
	 * 构造函数
	 * @param x 子弹所在位置的x坐标
	 * @param y 子弹所在位置的y坐标
	 * @param dir 子弹飞出的方向
	 * @param t 发出子弹的坦克的引用
	 * @param tc 子弹所属主类的引用
	 */
	
	public Missile(int x, int y, Tank.Direction dir, Tank t, TankClient tc) {
		this.x = x - size / 2;
		this.y = y - size / 2;
		this.dir = dir;
		this.tc = tc;
		this.t = t;
	}

	/**
	 * 构造函数
	 * @param x 子弹所在位置的x坐标
	 * @param y 子弹所在位置的y坐标
	 * @param dir 子弹飞出的方向
	 * @param t 发出子弹的坦克的引用
	 * @param tc 子弹所属主类的引用
	 * @param size 子弹的大小
	 */
	
	public Missile(int x, int y, Tank.Direction dir, Tank t, TankClient tc,
			int size) {
		this.size = size;
		this.x = x - size / 2;
		this.y = y - size / 2;
		this.dir = dir;
		this.tc = tc;
		this.t = t;
		this.attackHurt = bigAttackHurt;
	}

	/**
	 * 在屏幕上画出他自己
	 * @param g 图形类
	 */
	
	public void draw(Graphics g) {
		Color orgColor = g.getColor();
		g.setColor(color);
		g.fillOval(x, y, size, size);
		g.setColor(orgColor);
		move();
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
		}
		if (x + size / 2 < 0 || y + size / 2 < 0
				|| x + size / 2 > TankClient.GAME_WIDTH
				|| y + size / 2 > TankClient.GAME_HEIGHT)
			tc.getMissiles().remove(this);
	}
	
	/**
	 * 判断是否撞到了坦克
	 * @param t 所要判断的坦克
	 * @return 是否撞到了坦克
	 */

	public boolean hitTank(Tank t) {
		if (this.getRect().intersects(t.getRect())) {
			if ((this.t.isGood() && !t.isGood())
					|| (!this.t.isGood() && t.isGood()))
				return true;
		}
		return false;
	}

	/**
	 * 判断是否撞到了墙
	 * @param w 所要判断的墙
	 * @return 是否撞到了墙
	 */
	
	public boolean hitWall(Wall w) {
		return this.getRect().intersects(w.getRect());
	}

	/**
	 * 得到表示子弹范围的矩形
	 * @return 表示子弹范围的矩形
	 */
	
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

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
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

	public Tank.Direction getDir() {
		return dir;
	}

	public void setDir(Tank.Direction dir) {
		this.dir = dir;
	}

	public TankClient getTc() {
		return tc;
	}

	public void setTc(TankClient tc) {
		this.tc = tc;
	}

	public Tank getT() {
		return t;
	}

	public void setT(Tank t) {
		this.t = t;
	}

	public int getAttackHurt() {
		return attackHurt;
	}

	public void setAttackHurt(int attackHurt) {
		this.attackHurt = attackHurt;
	}

}
