package com.tedu.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.tedu.element.ElementObj;

/**
 * @说明 本类是元素管理器，专门存储所有元素同时提供方法
 * 		给予视图或控制获取数据
 * @author 86180
 * @Q1:存储所有元素数据，如何存放？
 * @Q2:管理器是视图和控制要访问，则管理器只能有一个且为单例模式
 */
public class ElementManager {
	private List<Object> listMap;
	private List<Object> listPlayList;
	/**
	 * String 作为key匹配所有的元素 play->List<Object> listPlay
	 * 						   enemy->List<Object> listEnemy
	 * 枚举类型用于当作map的key用于获取资源
	 * List中元素的泛型应该是元素基类
	 * 所有元素都可以存放到map集合中，显示模块只需要获取到这个map
	 * 就可以显示所有的界面需要显示的元素（调用元素基类的showElement（））
	 */
	private Map<GameElement, List<ElementObj>> gameElements;
//	本方法一定不够用，因为还需要增加对象
	public Map<GameElement, List<ElementObj>> getGameElements() {
		return gameElements;
	}
//	添加元素（多半由加载器调用）
	public void addElement(ElementObj obj,GameElement ge) {
//		List<ElementObj> list = gameElements.get(ge);
//		list.add(obj);
		gameElements.get(ge).add(obj); //添加对象到集合中，按key值进行读取
	}
//	依据key返回list集合，取出某一类元素
	public List<ElementObj> getElementsBykey(GameElement ge){
		return gameElements.get(ge);
	}
	
	/**
	 * 单例模式：内存中有且只有一个实例
	 * 饿汉模式（恶汉）-一个是启动就是自动加载实例
	 * 饱汉模式-需要使用的时候才加载实例
	 * 
	 * 编写方式：
	 * 1.需要一个静态的属性（定义一个常量） 单例的引用
	 * 2.提供一个静态的方法（返回这个实例） return单例的引用
	 * 3.一般为防止其他人自己使用（类诗可以实例化的），所以会私有化构造方法
	 *  	防止-》ElementManager em=new ElementManager（）
	 */
	
	private static ElementManager EM=null; //引用
	
	//synchronized线程锁-》保证本方法执行中只有一个线程
	public static  synchronized ElementManager getManager() {
		if(EM == null) {	//控制判定
			EM=new ElementManager();			
		}		
		return EM;
	}
	private ElementManager() {	//私有化构造方法
		init();//实例化方法
	}
//	static { //饿汉实例化对象 //静态语句块是在类被加载的时候直接执行
//		EM=new ElementManager();	//只会执行一次
//	}
	/**
	 * 本方法是为将来可能出现的功能扩展，重写init方法准备的。
	 */
	public void init() {//实例化在这里完成
//		hashMap hash散列
		gameElements=new HashMap<GameElement, List<ElementObj>>();
//		将每种元素集合都放入到map中
//		gameElements.put(GameElement.PLAY,new ArrayList<ElementObj>());
//		gameElements.put(GameElement.MAPS,new ArrayList<ElementObj>());
//		gameElements.put(GameElement.ENEMY,new ArrayList<ElementObj>());
//		gameElements.put(GameElement.BOSS,new ArrayList<ElementObj>());
		for(GameElement ge:GameElement.values()) { //通过循环读取枚举类型的方式添加集合
			gameElements.put(ge, new ArrayList<ElementObj>());
		}
//		道具，子弹，爆炸效果，死亡效果···
	}
}
