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
	     * ͷ���
	     * */
	    private Node head = null;
	    /*
	     * β���
	     * */
	    private Node tail = null;
	    /*
	     * ��С
	     * */
	    private int size = 0;
	    /*
	     * ��ʼ��Ϸʱ��
	     * ��ʼλ�ã�(20,30)
	     * ��ʼ�˶�����:Dir.L
	     * */
	    private Node n = new Node(20, 30, Dir.L);
	    /*
	     * Yard�Ķ������ԣ�
	     * */
	    private Yard y;
	    /*
	     * ���캯��
	     * */
	    public  snake(Yard y) {
	        /*
	         * ����ʼ����ͷ����β��㣬size��ʼ��Ϊ 1��
	         * */
	        head = n;
	        tail = n;
	        size = 1;
	        this.y = y;
	    }
	    /*
	     * �ڵ���
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


	    // ��β�ӣ�����������һ�������Ĵ��빦�����ƣ����ﲻ�ٷ���
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

	    // ��ͷ��
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
	     * �ƶ�������Ҫ���Ĳ��������˶���������һ���ڵ㣬��β����ȥһ���ڵ㣬���Ҽ���Ƿ��Ѿ�����
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
	         * ͷ�ڵ��������ĳһ���ڵ���ײ��Ҳ��־�Ž���
	         * */
	        for(Node n = head.next; n != null; n = n.next) {
	            if(head.row == n.row && head.col == n.col) {
	                y.stop();
	            }
	        }
	    }
	    /*
	     * ɾ��β�ڵ�
	     * */
	    private void deleteFromTail() {
	        if(size == 0) return;
	        tail = tail.prev;
	        tail.next = null;

	    }

	    public void eat(Egg e) {
	        /*
	         * boolean intersects(Rectangle r) 
	         *  ȷ���� Rectangle �Ƿ���ָ���� Rectangle �ཻ��
	         * ���ཻ,��ʾ���ǳԵ���һ���� �������ߵĳ��ȱ䳤�����ڳ���һ���㣬���Ҽ�5�֣�����ʲôҲ����
	         * */
	        if(this.getRect().intersects(e.getRect())) {
	            e.reAppear();
	            this.addToHead();
	            //���˼�5��
	            y.setScore(y.getScore() + 5);
	        }
	    }

	    private Rectangle getRect() {
	        /*
	         * ������һ�����Ӵ�С������
	         * */
	        return new Rectangle(Yard.BLOCK_SIZE * head.col, Yard.BLOCK_SIZE * head.row, head.w, head.h);
	    }
	    /*
	     * ���մӼ��̵İ����¼���Ȼ���ȡ��Ӧ�Ľ������
	     * */
	   

	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		 int key = e.getKeyCode();
	        switch(key) {
	        case KeyEvent.VK_LEFT  :
	            /*
	             * ������Ϊ���ʱ��ֻҪǰ���������ң�����ת��
	             * */
	            if(head.dir != Dir.R)
	                head.dir = Dir.L;
	            break;
	        case KeyEvent.VK_UP  :
	            /*
	             * ������Ϊ"��"��ֻҪǰ��������"��"���Ϳ���ת��
	             * */
	            if(head.dir != Dir.D)
	                head.dir = Dir.U;
	            break;
	        case KeyEvent.VK_RIGHT  :
	            /*
	             * ������Ϊ"��"��ʱ��ֻҪǰ��������"��",�Ϳ���ת��
	             * */
	            if(head.dir != Dir.L)
	                head.dir = Dir.R;
	            break;
	        case KeyEvent.VK_DOWN :
	            /*
	             * ������Ϊ"��"��ʱ��ֻҪǰ��������"��",�Ϳ���ת��
	             * */
	            if(head.dir != Dir.U)
	                head.dir = Dir.D;
	            break;
		
	}
	        }
	}
