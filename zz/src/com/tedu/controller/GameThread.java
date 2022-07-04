package com.tedu.controller;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.lang.model.element.Element;
import javax.swing.ImageIcon;

import com.tedu.element.ElementObj;
import com.tedu.element.Enemy;
import com.tedu.element.Play;
import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;
import com.tedu.manager.GameLoad;

/**
 * @说明 游戏的主线程，用于控制游戏加载，游戏关卡，游戏运动时自动化
 * 		游戏判定；游戏地图切换 资源释放和重新读取。。。
 * @author 86180
 * @继承 使用继承的方式实现多线程（一般建议使用接口实现）
 */
public class GameThread extends Thread{
	private ElementManager em;
	
	public GameThread() {
		em=ElementManager.getManager();
	}
	@Override
	public void run() { //游戏的run方法 主线程
		while(true) { //扩展，可以将true变为一个变量用于控制结束	
//		游戏开始前 读进度条，加载游戏资源（场景资源）
			gameLoad();
//		游戏进行时 游戏过程中
			gameRun();
//		游戏场景结束 游戏资源回收（场景资源）
			gameOver();			
			try {
				sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	/**
	 * 游戏的加载
	 */
	private void gameLoad() {
		// TODO Auto-generated method stub
		GameLoad.MapLoad(5);
		load();
	}
	/**
	 * 
	 * @说明 游戏进行时
	 * @任务说明 游戏过程中需要做的事情：1.自动化玩家的移动，碰撞，死亡
	 * 						   2.新元素的增加（NPC死亡后出现道具）
	 * 						   3.暂停等···
	 * 先实现主角的移动
	 */
	private long gameTime = 0L;
	private void gameRun(){
		long gameTime=0L; //给int类型就可以
		// TODO Auto-generated method stub
		while(true) { //预留扩展true可以变为变量，用于控制关卡结束
			Map<GameElement, List<ElementObj>> all = em.getGameElements();
			
			moveAndUpdate(all, gameTime); //游戏元素自动化方法
			
			ElementPK();
			
			gameTime++;//唯一的时间控制
			try {
				sleep(45); //差不多是1秒刷新24次
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}
	
	
	public void ElementPK() {
		List<ElementObj> enemys = em.getElementsBykey(GameElement.ENEMY);
		List<ElementObj> fires = em.getElementsBykey(GameElement.PLAYFIRE);
//		使用循环做一对一判定，如果为真则设置2个对象的死亡状态
		for(int i=0;i<enemys.size();i++) {
			ElementObj enemy=enemys.get(i);
			for(int j=0;j<fires.size();j++) {
				ElementObj fire=fires.get(j);
				if (enemy.pk(fire)) {
//					问题：如果是boss也一枪一个吗？ =》
//					将setlive(false)变为一个受攻击方法 可以传入另一个对象的攻击力
//					当受攻击方法力执行时，如果血量减为0再设置生存为false
					enemy.setLive(false);
					fire.setLive(false);
					break;
				}
			}
		}
	}
	
	
	// 游戏元素自动化方法
		public void moveAndUpdate(Map<GameElement, List<ElementObj>> all,long gameTime) {
//			GameElement.values(); //隐藏方法 返回值是一个数组,数组的顺序就是定义枚举的顺序
			for(GameElement ge:GameElement.values()) {
				List<ElementObj> list = all.get(ge);
//				编写这样直接操作集合数据的代码建议不要使用迭代器
//				for(int i=0;i<list.size();i++) {
				for(int i=list.size()-1;i>=0;i--) {
					ElementObj obj = list.get(i);//读取为基类
					if(!obj.isLive()) { //如果死亡
//						list.remove(i--);	//也可以使用这种方式保证每一个元素都消亡
//						启动一个死亡方法（方法中可以做事情例如：死亡动画，掉装备）
						obj.die();	//需要自行补充
						list.remove(i);
						continue;
					}
					obj.model(gameTime);; //调用每个类的自己的show方法完成自己的显示
				}
			}
		}
	
	
	/**
	 * 游戏切换关卡
	 */
	private void gameOver() {
		// TODO Auto-generated method stub
		
	}
	
	public void load() {
//		图片导入
		ImageIcon icon = new ImageIcon("image/tank/play1/player1_up.png");
		ElementObj obj = new Play(100,100,50,50,icon);	//实例化对象
//		将对象放入到元素管理器中
//		em.getElementsBykey(GameElement.PLAY).add(obj);
		em.addElement(obj,GameElement.PLAY); //直接添加

//		添加一个敌人类，仿照玩家类编写，注意：不需要时间 键盘监听
//		实现敌人的显示，同时实现最简单的敌人移动例如：坐标100，100 移动到 500，100然后调头
//		实现子弹的发射，和子弹移动，元素死亡
//		注意：只讲子弹的发射和死亡。思考：道具的掉落 是否和子弹的发射方式相近？
		
//		创建敌人
		for(int i=0;i<10;i++) {
			em.addElement(new Enemy().createElement(""),
										GameElement.ENEMY);
		}
	}

	
}

