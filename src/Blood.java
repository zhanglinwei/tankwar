
//Blood.java

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;


public class Blood {
	private int x = 0;
	private int y = 0;
	private int blood = 15;
	private int size = 10;

	private Color color=Color.RED;

	/**
	 * 构造方法
	 * @param x 血块出现的x坐标
	 * @param y 血块出现的y坐标
	 */
	
	public Blood(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * 构造方法
	 * @param x 血块出现的x坐标
	 * @param y 血块出现的y坐标
	 * @param blood 一个血块回复的生命值
	 */
	
	public Blood(int x, int y, int blood) {
		this(x, y);
		this.blood = blood;
	}
	
	/**
	 * 判断是否和坦克相撞
	 * @param t 与之相撞的坦克
	 * @return	是否相撞
	 */
	
	public boolean hitTank(Tank t) {
		return this.getRect().intersects(t.getRect());
	}
	
	/**
	 * 在屏幕上画出他自己
	 * @param g 图形类
	 */
	
	void draw(Graphics g) {
		Color orgColor = g.getColor();
		g.setColor(color);
		g.fillOval(x, y, size, size);
		g.setColor(orgColor);
	}
	
	/**
	 * 得到血块所代表的矩形
	 * @return 血块代表的矩形
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

	public int getBlood() {
		return blood;
	}

	public void setBlood(int blood) {
		this.blood = blood;
	}
	
	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
}

