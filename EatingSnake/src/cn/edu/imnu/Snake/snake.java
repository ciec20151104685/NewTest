package cn.edu.imnu.Snake;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import cn.edu.imnu.Egg.Egg;
import cn.edu.imnu.Yard.Yard;


	public class snake {
		
		public enum Dir {
		    L, U, R, D
		}
	    /*n
	     * 头结点
	     * */
	    private Node head = null;
	    /*
	     * 尾结点
	     * */
	    private Node tail = null;
	    /*
	     * 大小
	     * */
	    private int size = 0;
	    /*
	     * 开始游戏时：
	     * 初始位置：(20,30)
	     * 初始运动方向:Dir.L
	     * */
	    private Node n = new Node(20, 30, Dir.L);
	    /*
	     * Yard的对象属性；
	     * */
	    private Yard y;
	    /*
	     * 构造函数
	     * */
	    public  snake(Yard y) {
	        /*
	         * 将初始结点给头结点和尾结点，size初始化为 1，
	         * */
	        head = n;
	        tail = n;
	        size = 1;
	        this.y = y;
	    }
	    /*
	     * 节点类
	     * */
	    private class Node {
	        int w = Yard.BLOCK_SIZE;
	        int h = Yard.BLOCK_SIZE;
	        int row , col;
	        Dir dir = Dir.L;
	        Node next = null;
	        Node prev = null;

	        Node(int row, int col, Dir dir) {
	            this.row = row;
	            this.col = col;
	            this.dir = dir;
	        }

	        void draw(Graphics g) {
	            Color c = g.getColor();
	            g.setColor(Color.BLACK);
	            g.fillRect(Yard.BLOCK_SIZE * col, Yard.BLOCK_SIZE * row, w, h);
	            g.setColor(c);
	        }
	    }


	    // 从尾加，代码与下面一个函数的代码功能相似，这里不再分析
	    public void addToTail() {
	        Node node = null;
	        switch(tail.dir) {
	        case L :
	            node = new Node(tail.row, tail.col + 1, tail.dir);
	            break;
	        case U :
	            node = new Node(tail.row + 1, tail.col, tail.dir);
	            break;
	        case R :
	            node = new Node(tail.row, tail.col - 1, tail.dir);
	            break;
	        case D :
	            node = new Node(tail.row - 1, tail.col, tail.dir);
	            break;
	        }
	        tail.next = node;
	        node.prev = tail;
	        tail = node;
	        size ++;
	    }

	    // 从头加
	    public void addToHead() {
	        Node node = null;
	        switch(head.dir) {
	        case L :
	            node = new Node(head.row, head.col - 1, head.dir);
	            break;
	        case U :
	            node = new Node(head.row - 1, head.col, head.dir);
	            break;
	        case R :
	            node = new Node(head.row, head.col + 1, head.dir);
	            break;
	        case D :
	            node = new Node(head.row + 1, head.col, head.dir);
	            break;
	        }

	        head.prev=node;
	        node.next=head;
	        head=node;
	        size ++;
	    }

	    public void draw(Graphics g) {
	        if(size <= 0) return;
	        move();
	        for(Node n = head; n != null; n = n.next) {
	            n.draw(g);
	        }
	    }
	    /*
	     * 移动过程所要做的操作：在运动方向增加一个节点，在尾部减去一个节点，并且检测是否已经死亡
	     * */
	    private void move() {
	        addToHead();
	        deleteFromTail();
	        checkDead();
	    }

	    private void checkDead() {
	        if(head.row < 2 || head.col < 0 || head.row > Yard.ROWS || head.col > Yard.COLS)  {
	            y.stop();
	        }
	        /*
	         * 头节点与身体的某一个节点相撞，也标志着结束
	         * */
	        for(Node n = head.next; n != null; n = n.next) {
	            if(head.row == n.row && head.col == n.col) {
	                y.stop();
	            }
	        }
	    }
	    /*
	     * 删除尾节点
	     * */
	    private void deleteFromTail() {
	        if(size == 0) return;
	        tail = tail.prev;
	        tail.next = null;

	    }

	    public void eat(Egg e) {
	        /*
	         * boolean intersects(Rectangle r) 
	         *  确定此 Rectangle 是否与指定的 Rectangle 相交。
	         * 若相交,表示我们吃到了一个点 ，则导致蛇的长度变长并且在出现一个点，并且加5分，否则什么也不做
	         * */
	        if(this.getRect().intersects(e.getRect())) {
	            e.reAppear();
	            this.addToHead();
	            //吃了加5分
	            y.setScore(y.getScore() + 5);
	        }
	    }

	    private Rectangle getRect() {
	        /*
	         * 构造了一个格子大小的区域
	         * */
	        return new Rectangle(Yard.BLOCK_SIZE * head.col, Yard.BLOCK_SIZE * head.row, head.w, head.h);
	    }
	    /*
	     * 接收从键盘的按键事件，然后采取相应的解决方案
	     * */
	   

	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		 int key = e.getKeyCode();
	        switch(key) {
	        case KeyEvent.VK_LEFT  :
	            /*
	             * 当按键为左的时候，只要前进方向不是右，即可转向
	             * */
	            if(head.dir != Dir.R)
	                head.dir = Dir.L;
	            break;
	        case KeyEvent.VK_UP  :
	            /*
	             * 当按键为"上"，只要前进方向不是"下"，就可以转向
	             * */
	            if(head.dir != Dir.D)
	                head.dir = Dir.U;
	            break;
	        case KeyEvent.VK_RIGHT  :
	            /*
	             * 当按键为"右"的时候，只要前进方向不是"左",就可以转向
	             * */
	            if(head.dir != Dir.L)
	                head.dir = Dir.R;
	            break;
	        case KeyEvent.VK_DOWN :
	            /*
	             * 当按键为"下"的时候，只要前进方向不是"上",就可以转向
	             * */
	            if(head.dir != Dir.U)
	                head.dir = Dir.D;
	            break;
		
	}
	        }
	}
