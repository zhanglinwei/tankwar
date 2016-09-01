//Wall.java

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;


public class Wall {
	private int x = 0;
	private int y = 0;
	private int width = 0;
	private int height = 0;
	private Color color = Color.BLACK;

	/**
	 * 构造方法
	 * @param x 墙出现的x坐标
	 * @param y 墙出现的y坐标
	 * @param width 墙的宽度
	 * @param height 墙的高度
	 */
	
	public Wall(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	/**
	 * 构造方法
	 * @param x 墙出现的x坐标
	 * @param y 墙出现的y坐标
	 * @param width 墙的宽度
	 * @param height 墙的高度
	 * @param color 墙的颜色
	 */
	
	public Wall(int x, int y, int width, int height, Color color) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.color = color;
	}

	/**
	 * 在屏幕上画出它自己
	 * @param g 图形类
	 */
	
	public void draw(Graphics g) {
		Color orgColor = g.getColor();
		g.setColor(color);
		g.fillRect(x, y, width, height);
		g.setColor(orgColor);
	}
	
	/**
	 * 得到代表所占范围的矩形
	 * @return 代表他所占范围的矩形
	 */

	public Rectangle getRect() {
		return new Rectangle(x, y, width, height);
	}
}
