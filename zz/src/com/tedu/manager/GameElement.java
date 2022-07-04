package com.tedu.manager;

public enum GameElement {
	/**
	 * PLAY 玩家
	 * MAPS 地图
	 * ENEMY 敌人
	 * BOSS boss
	 */
	MAPS,PLAY,ENEMY,BOSS,PLAYFIRE,DIE; //枚举类型的顺序是声明的顺序
//	我们定义的枚举类型,在编译的时候,虚拟机会自动帮助生成class文件,并且会
//	加载很多的代码和方法
//	定义方法时不可用public
//	private GameElement() {
//		
//	}
//	private GameElement(int id) {
//		
//	}
}
