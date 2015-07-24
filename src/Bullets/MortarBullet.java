package Bullets;

import java.awt.Color;
import java.awt.Graphics;

import Monsters.BasicMonster;
import Towers.Tower;

public class MortarBullet extends Bullet {

	static int defaultDamageWidth = 80;
	static int defaultdamageHeight = 80;

	/**
	 * constructor for bullet
	 * 
	 * @param towerz
	 * @param bulletSpeed
	 */
	public MortarBullet(Tower towerz, double startX, double startY, double dx,
			double dy, BasicMonster targetz) {
		super(towerz, startX, startY, dx, dy, defaultDamageWidth,
				defaultdamageHeight, targetz);

	}

	@Override
	public Color getBulletColor() {
		// TODO Auto-generated method stub
		return Color.GRAY;
	}

	public Color getColorOfRange() {
		float rbg = (float) .08;
		float red = (float) 0.01;
		float green = (float) .99;
		float blue = (float) .001;
		Color rangeColor = new Color(red, green, blue, rbg);
		return rangeColor;
	}

}
