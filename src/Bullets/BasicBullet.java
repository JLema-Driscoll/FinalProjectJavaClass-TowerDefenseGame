package Bullets;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;

import Monsters.BasicMonster;
import Towers.Tower;

public class BasicBullet extends Bullet {

	static int defaultDamageWidth = 5;
	static int defaultdamageHeight = 5;

	public BasicBullet(Tower towerz, double startX, double startY, double dx,
			double dy, BasicMonster targetz) {
		super(towerz, startX, startY, dx, dy, defaultDamageWidth,
				defaultdamageHeight, targetz);
	}

	@Override
	public Color getBulletColor() {
		return Color.WHITE;
	}

	@Override
	public Color getColorOfRange() {
		// TODO Auto-generated method stub
		return null;
	}

}
