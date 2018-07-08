package cn.edu.imnu.Yard;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import cn.edu.imnu.Egg.Egg;
import cn.edu.imnu.Snake.snake;
import cn.edu.imnu.StartBody.StartBody;
//awt Abstract Window Toolkit)
public class Yard extends Frame {
	/*
     * ��ͼ���߳�
     * */
    PaintThread paintThread = new PaintThread();
    private boolean gameOver = false; //��Ϸ�Ƿ����
    private boolean beganGame = gameOver;

    public static final int ROWS = 30;

    public static final int COLS = 30;
   
    public static final int BLOCK_SIZE = 25;

  
    private Font fontGameOver = new Font("����", Font.BOLD, 60);

   
    private int score = 0;
  
    private long beginTime=0;
  
    Yard yard =this;
    snake s = new snake(yard);
    Egg e = new Egg();
    //����һ�� offScreenImage �Ļ���ͼ�� 
    Image offScreenImage = null;

    /* �˺����Ĺ����ǣ����ô��ڵĴ�С��λ�á��ɼ����Լ�����¼��ͼ����¼���������˻�ͼ�߳� */
    public void launch() {
      
        this.setLocation(90, 10);
      
        this.setSize(COLS * BLOCK_SIZE, ROWS * BLOCK_SIZE);
     //����������մ����¼����������ӿ�
        this.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e/*���ڽ���*/) {
                System.exit(0);
            }

        });
        /*
         * ���������ÿɼ�
         * */
        this.setVisible(true);
        /*
         * Ϊ������Ӽ����¼�
         * */
        this.addKeyListener(new KeyMonitor());
        new Thread(paintThread).start();
      
        
    }

    public static void main(String[] args) {
//    	  Yard y=new Yard();
//          y.beginTime=System.currentTimeMillis();
//          y.launch();
    }
    
    public Yard () {
    	beginTime=System.currentTimeMillis();
    	launch();
    }
    /*
     * ������gameOver����Ϊtrue��ʹ����paint()�����н�ʹ���߳�ֹͣ
     * */
    public void stop() {
        gameOver = true;
    }

    @Override
    public void paint(Graphics g/*huabi*/) {  
        Color c = g.getColor(); 
        g.setColor(Color.GRAY);     
        g.fillRect(0, 0, COLS * BLOCK_SIZE, ROWS * BLOCK_SIZE);
        g.setColor(Color.DARK_GRAY);
        //��������
        /*drawLine(int x1, int y1, int x2, int y2)  */
        for(int i=1; i<ROWS; i++) {
            g.drawLine(0, BLOCK_SIZE * i, COLS * BLOCK_SIZE, BLOCK_SIZE * i);
        }
        for(int i=1; i<COLS; i++) {
            g.drawLine(BLOCK_SIZE * i, 0, BLOCK_SIZE * i, BLOCK_SIZE * ROWS);
        }
        g.setColor(Color.YELLOW);//�趨��ɫ��Ϊ������ʾ������Ϣ��׼��
        g.drawString("ʹ��˵����ʹ�÷�������Ʒ���F1--ֹͣ��F2--ֹͣ��ָ���F5--���¿�ʼ" , 10, 50);
        g.drawString("Ŀǰ����:" + score, 10, 70);
        g.drawString("�ӷֹ���ÿ��һ����5�֣����ͣ�" , 10, 90);
        g.drawString("�Ѿ�ʹ�õ�ʱ�䣺"+(System.currentTimeMillis()-beginTime)/1000 , 10, 110);
        if(gameOver) {
            g.setFont(fontGameOver);
            g.drawString("game over!", 90, 170);
            g.drawString("����һ�Σ��밴F5", 10, 250);
            g.drawString(" ", 200, 230);
            paintThread.pause();
        }
        if(score>5) {
            g.drawString("ţ�ƣ�����", 90, 170);
            g.drawString("���Ѿ�����"+score+",��������", 10, 230);


        }
        if(!beganGame) {
        	 Font f=new Font(null,Font.PLAIN,40);
        	 g.setFont(f);
            g.drawString("��ʼ��Ϸ", 400, 500);
            g.drawString("�밴F3", 400, 600);
            paintThread.pause();
            


        }
        g.setColor(c);
        s.eat(e);
        e.draw(g);
        s.draw(g);

    }

    @Override
    public void update(Graphics g) {
        if(offScreenImage == null) {
            /*�����������ڴ��д���һ�� offScreenImage �Ļ���ͼ�� */
            offScreenImage = this.createImage(COLS * BLOCK_SIZE, ROWS * BLOCK_SIZE);
        }
        /*
         * getGraphics() 
         *���������Ʊ���ͼ��off-screen image��ʹ�õ�ͼ�������ġ�
         * */
        Graphics gOff = offScreenImage.getGraphics();
        paint(gOff);
        /*
         * drawImage(Image img, int x, int y, ImageObserver observer) 
         *      ����ָ��ͼ���е�ǰ���õ�ͼ��
         * */
        g.drawImage(offScreenImage, 0, 0,  null);
    }

    private class PaintThread implements Runnable {
        private boolean running = true;
        private boolean pause = false;
        public void run() {
            while(running) {
                if(pause) continue; 
                else repaint();//�ػ�
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public void pause() {
            this.pause = true;
        }

        public void reStart() {
        	this.pause = false;
            s = new snake(Yard.this);//�ػ�
            gameOver = false;
            
        }

        public void gameOver() {
            running = false;
        }

    }
 
    private class KeyMonitor extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            if(key == KeyEvent.VK_F5) {
                paintThread.reStart();//���¿�ʼ��Ϸ
            }
            else if(key==KeyEvent.VK_F1){
                paintThread.pause=true;//��ͣ
            }
            else if(key==KeyEvent.VK_F2){
                paintThread.pause=false;//����ͣ�лָ�
            }
            else if(key==KeyEvent.VK_F3){
                paintThread.pause=false;//����ͣ�лָ�
                beganGame=true;
            }
            s.keyPressed(e);
        }

    }

    public int getScore() {
        return score;
    }

    /**
     * �������õķ���
     * @param score ����
     */

    public void setScore(int score) {
        this.score = score;
    }

}


