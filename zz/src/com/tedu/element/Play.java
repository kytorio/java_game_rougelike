package com.tedu.element;

import java.awt.Graphics;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;
import com.tedu.manager.GameLoad;

public class Play extends ElementObj{
	/**
	 * 移动属性：	
	 * 1.单属性 配合方向枚举类型使用；一次只能移动一个方向
	 * 2.双属性 上下 和 左右  配合boolean值使用 例如：true代表上 false为下 不动：？？
	 * 					 需要另外一个变量来确定是否按下方向键
	 * 				约定： 0代表不动 1代表上 2代表下
	 * 3.四属性 上下左右都可以 boolean配合使用 true 代表移动 false 不移动 同时按上和下：后按重置先按
	 * 说明：以上三种方式 代码编写和判定方式不一样
	 * 说明：游戏中非常多的判定，建议灵活使用判定属性；很多状态值也使用判定属性
	 * 	   多状态 可以使用map<泛型，boolean>；set<判定对象>判定对象中有时间
	 * 
	 * @问题 1.图片要读取到内存中：加载器 临时处理方式，手写编写存储到内存中
	 * 		2.什么时候进行修改图片（因为图片是在父类钟的属性存储）
	 * 		3.图片应该使用什么集合进行存储
	 */
	private boolean left=false;
	private boolean up=false;
	private boolean right=false;
	private boolean down=false;
	

//	变量专门用来记录当前主角面向的方向，默认为up
	private String fx="up";
	private boolean pkType=false;//攻击状态true 攻击 false停止
	
	public Play(int x, int y, int w, int h, ImageIcon icon) {
		super(x, y, w, h, icon);
		// TODO Auto-generated constructor stub

	}
	/**
	 * 面向对象中第1个思想:对象自己的事情自己做
	 */
	@Override
	public void showElement(Graphics g) {
		// TODO Auto-generated method stub
//		绘画图片
		g.drawImage(this.getIcon().getImage(),
				this.getX(), this.getY(),
				this.getW(), this.getH(), null);
	}
	
	/*
	 * @说明：重写方法：重写的要求：方法名称和参数类型序列必须和弗雷德一样
	 * @重点：监听的数据需要改变状态值
	 */
	//注解 通过反射机制，为类或者方法或者属性 添加的注释（相当于身份证判定）
	public void keyClick(boolean bl, int key) { //只有按下或者松开才会调用这个方法
		if(bl) {//按下
			switch (key) { //优化 监听会持续触发 若要置为双属性则把其他三个全置为false
			case 37:
				this.down=false;this.up=false;
				this.right=false;this.left=true; this.fx="left"; 
			break;
			case 38:
				this.right=false;this.left=false;
				this.down=false;this.up=true;this.fx="up";
			break;
			case 39:
				this.down=false;this.up=false;
				this.left=false;this.right=true;this.fx="right";
			break;
			case 40:
				this.right=false;this.left=false;
				this.up=false;this.down=true;this.fx="down";			
			break;
			case 32:
				this.pkType=true; //开启攻击状态
			}
		}else {
			switch (key) {
			case 37:this.left=false;break;
			case 38:this.up=false;break;
			case 39:this.right=false;break;
			case 40:this.down=false;break;
			case 32:this.pkType=false;break; //关闭攻击状态
			}
			
		}
		
	}
	
	@Override
	public void move() {
		if(this.left &&this.getX()>0) {
			this.setX(this.getX()-5);
		}
		if(this.up&&this.getY()>0) {
			this.setY(this.getY()-5);
		}
		if(this.right&&this.getX()<(900-this.getW())) {
			this.setX(this.getX()+5);
		}
		if(this.down&&this.getY()<(600-this.getH())) {
			this.setY(this.getY()+5);
		}
	}
	
	@Override
	protected void updateImage(long ... gameTime) {
		// TODO Auto-generated method stub
//		icon.getIconHeight() //得到图片的高度
//		如果高度为0则图片路径有问题
		this.setIcon(GameLoad.imgMap.get(fx));
	}
	
	
	/**
	 * @额外问题: 1.请问重写的方法的访问修饰符是否可以修改?
	 * 		  2.请问下面的add方法是否可以自动抛出异常?
	 * @重写规则:1.重写方法的方法名称和返回值 必须和父类的一样
	 * 		   2.重写的方法的传入参数类型序列,必须和父类的一样
	 * 		   3.重写的方法访问修饰符只能比父类的更加宽泛
	 * 				比方说:父类的方法是受保护的,但是现在需要在非子类调用
	 * 					 可以直接子类集成,重写并super.父类方法 public方法
	 * 		   4.重写的方法抛出的异常 不可以比父类更加宽泛	
	 * 子弹的添加 需要的是 发射者的坐标位置以及发射者的方向 如果你可以变换子弹(思考,怎么处理?)
	 */
	private long firetime = 0;
//	firetime 和传入的时间gametime进行比较,赋值等操作运算,控制子弹间隔
	@Override	//添加子弹
	public void add(long gameTime) {
		if(!this.pkType || gameTime < firetime) { //不发射状态直接return
			return;
		}
		firetime = gameTime+8;
//		this.pkType = false; 纯纯拼手速
//		super.add();
//		new PlayFire(); //够够在一个类需要坐比较多的工作 可以选择一种方式,使用小工厂
//		将构造对象的多个步骤进行封装成为一个方法,返回值直接是这个对象
//		传递一个固定格式 {X:3,Y:5,F:up} 固定二十
		ElementObj element = new PlayFire().createElement(this.toString());
		ElementManager.getManager().addElement(element, GameElement.PLAYFIRE);
		//如果要控制子弹速度等等...还需要代码编写
//		try {
//			Class<?> forName = Class.forName("com.tedu.....");
//			ElementObj element =  forName.newInstance().createElement("");
//		} catch (InstantiationException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} //以后框架学习中会碰到
////			会帮助你返回对象的实体并初始化数据
//		catch (ClassNotFoundException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
	}
	
	public String toString() {
		// {x:3,y:5,f:up} json格式
		return "x:"+this.changefireposition()[0]+",y:"+this.changefireposition()[1]+",f:"+this.fx;
	}
	
	//根据方向做子弹调整 保证子弹发射时就已经给予固定的轨迹.可以加上目标,修改json格式
	private int[] changefireposition() {
		int x=this.getX();
		int y=this.getY();
		switch (this.fx) {
		case "left": y+=20;break; //一般不会写20等数值,一般情况下可以使用图片大小参与运算
		case "right": x+=40;y+=20;break;
		case "up":x+=20;break;
		case "down":x+=20;y+=40;break;
		}		
		int[] position = {x,y};
		return position;
	}
}
