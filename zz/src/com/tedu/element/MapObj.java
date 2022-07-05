package com.tedu.element;

import java.awt.Graphics;

import javax.swing.ImageIcon;

public class MapObj extends ElementObj{

	@Override
	public void showElement(Graphics g) {
		// TODO Auto-generated method stub
		g.drawImage(this.getIcon().getImage(),
				this.getX(), this.getY(),
				this.getW(), this.getH(), null);
	}
	
	@Override	//如果可以传入 墙类型,x,y
	public ElementObj createElement(String str) {
		String []arr=str.split(",");
		ImageIcon icon=null;
		switch (arr[0]) {	//设置图片信息 图片还未加载到内存中
		case "GRASS": icon=new ImageIcon("image/wall/grass.png");break;
		case "BRICK":icon=new ImageIcon("image/wall/brick.png");break;
		case "RIVER":icon=new ImageIcon("image/wall/river.png");break;
		case "IRON":icon=new ImageIcon("image/wall/iron.png");break;
		}
		int x=Integer.parseInt(arr[1]);
		int y=Integer.parseInt(arr[2]);
		int w=icon.getIconWidth();
		int h=icon.getIconHeight();
		this.setH(h);
		this.setW(w);
		this.setX(x);
		this.setY(y);
		this.setIcon(icon);
		return this;
	}
}
