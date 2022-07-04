package com.tedu.manager;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.swing.ImageIcon;

import com.tedu.element.ElementObj;
import com.tedu.element.MapObj;

/**
 * @说明 加载器（工具：用户读取配置文件的工具）工具类，大多提供的是static方法
 * @author 86180
 *
 */

public class GameLoad {
//	得到资源管理器
	private static ElementManager em = ElementManager.getManager();
	
//	图片集合 使用map来进行存储 枚举类型配合移动（拓展）
	public static Map<String, ImageIcon> imgMap;	
	static {		
		imgMap=new HashMap<>();
		imgMap.put("left",new ImageIcon("image/tank/play1/player1_left.png"));
		imgMap.put("down",new ImageIcon("image/tank/play1/player1_down.png"));
		imgMap.put("right",new ImageIcon("image/tank/play1/player1_right.png"));
		imgMap.put("up",new ImageIcon("image/tank/play1/player1_up.png"));
		
//		Collections 用于集合排序的工具类 可以为所有的对象类型的记录进行排序
//			排序只能为Collection的子类
	}
	
//	用户读取文件的类
	private static Properties pro = new Properties();
	
	/**
	 * @说明 传入地图id有加载方法 依据文件规则自动生成地图文件名称，加载文件
	 * @param mapId 文件编号 文件id
	 */
	public static void MapLoad(int mapId) {
		String mapName="com/tedu/text/"+mapId+".map";	//获取文件路径
//		使用io流来获取文件对象 得到类加载器
		ClassLoader classLoader = GameLoad.class.getClassLoader();
		InputStream maps = classLoader.getResourceAsStream(mapName);
		if(maps == null) {
			System.out.println("配置文件读取异常，请重新安装");
			return;
		}
		
		try {
//			以后用的都是 xml 和json
			pro.load(maps);
//			可以直接动态获取所有的key，有key就可以直接获取value
			Enumeration<?> names = pro.propertyNames();
			while (names.hasMoreElements()) {	//获取是无序的
//				这样的迭代有一个问题：一次迭代一个元素。
				String key=names.nextElement().toString();
//				System.out.println(pro.getProperty(key));
//				就可以自动创建和加载我们的地图
				String[] arrs=pro.getProperty(key).split(";");
				for(int i=0;i<arrs.length;i++) {
					ElementObj element = new MapObj().createElement(key+","+arrs[i]);
					em.addElement(element, GameElement.MAPS);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
////	用于测试
//	public static void main(String[] args) {
//		MapLoad(5);
//	}
}
