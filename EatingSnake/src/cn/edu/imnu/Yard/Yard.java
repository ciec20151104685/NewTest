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

public class Yard extends Frame {
	/*
     * ��ͼ���߳�
     * */
    PaintThread paintThread = new PaintThread();
    private boolean gameOver = false; //��Ϸ�Ƿ����

    /**
     * ����
     */
    public static final int ROWS = 30;
    /*
     * ����
     * */
    public static final int COLS = 30;
    /*
     * ������С
     * */
    public static final int BLOCK_SIZE = 15;

    //������ʾ������
    private Font fontGameOver = new Font("����", Font.BOLD, 50);

    //����
    private int score = 0;
    /*
     * ��¼��ʼʱ���ʱ��
     * */
    private long beginTime=0;
    /*
     *ʵ����Snake��Egg�Ķ���
     * */
    Yard yark =this;
    snake s = new snake(yark);
    Egg e = new Egg();
    /*
     * ������ Image �Ǳ�ʾͼ��ͼ���������ĳ��ࡣ
     * �������ض���ƽ̨�ķ�ʽ��ȡͼ��
     * */
    Image offScreenImage = null;

    /*
     * �˺����Ĺ����ǣ����ô��ڵĴ�С��λ�á��ɼ����Լ�����¼��ͼ����¼���������˻�ͼ�߳�
     * */
    public void launch() {
        /*
         * ָ�����ڵ�λ�ã����ڵ����Ͻǵ�λ��Ϊ(90,10).����Ը����ڵ����λ��
         * */
        this.setLocation(90, 10);
        /*
         * �趨���ڵĴ�С
         * ���width��COLS*BLOCK_SIZE
         * �߶�hight:ROWS*BLOCK_SIZE
         * */
        this.setSize(COLS * BLOCK_SIZE, ROWS * BLOCK_SIZE);
        /*
         * Ϊ������Ӽ�����
         * */
        this.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
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
        Yard y=new Yard();
        y.beginTime=System.currentTimeMillis();
        y.launch();
    }
    /*
     * ������gameOver����Ϊtrue��ʹ����paint()�����н�ʹ���߳�ֹͣ
     * */
    public void stop() {
        gameOver = true;
    }

    @Override
    public void paint(Graphics g) {
        /*
         * ��ȡ��ͼ�������ĵ���ɫ
         * */
        Color c = g.getColor();
        /*
         * ָ��ͼ�������ĵ���ɫ
         * */
        g.setColor(Color.GRAY);
        /*
         * �õ�ǰ����ɫ�����ָ��������
         * */
        g.fillRect(0, 0, COLS * BLOCK_SIZE, ROWS * BLOCK_SIZE);
        /*
         * ��һ�ε�ָ����ɫΪ���ڻ�ɫ��������������ΪʲôҪ��һ�ε��趨������
         * ԭ�����ڣ������뽫��ͼ����ɫ��������ʾ�Ĳ�һ��
         * */
        g.setColor(Color.DARK_GRAY);
        //��������
        /*
         * drawLine(int x1, int y1, int x2, int y2) 
         *  �����Ĺ���Ϊ��
         * �ڴ�ͼ�������ĵ�����ϵ�У�ʹ�õ�ǰ��ɫ�ڵ� (x1, y1) �� (x2, y2) ֮�仭һ���ߡ�
         * ͨ�����������forѭ���ͻ���ͼ�λ������ϻ������
         * */
        for(int i=1; i<ROWS; i++) {
            g.drawLine(0, BLOCK_SIZE * i, COLS * BLOCK_SIZE, BLOCK_SIZE * i);
        }
        for(int i=1; i<COLS; i++) {
            g.drawLine(BLOCK_SIZE * i, 0, BLOCK_SIZE * i, BLOCK_SIZE * ROWS);
        }


        g.setColor(Color.YELLOW);//�趨��ɫ��Ϊ������ʾ������Ϣ��׼��
        /*
         * drawString(String str, int x, int y) 
         *ʹ�ô�ͼ�������ĵĵ�ǰ�������ɫ������ָ�� string �������ı���
         * */
        g.drawString("ʹ��˵����ʹ�÷�������Ʒ���F1--ֹͣ��F2--ֹͣ��ָ���F5--���¿�ʼ" , 10, 40);
        g.drawString("Ŀǰ����:" + score, 10, 60);
        g.drawString("�ӷֹ���ÿ��һ����5�֣����ͣ�" , 10, 80);
        g.drawString("�Ѿ�ʹ�õ�ʱ�䣺"+(System.currentTimeMillis()-beginTime)/1000 , 10, 100);
        /*
         * �����Ϸ�Ƿ����������Ϸ����ʱ������ʾ��Ϸ��game over�������ҽ�����ָ�����ʼ�����״̬������ֹ��ͼ�߳�
         * */
        if(gameOver) {
            g.setFont(fontGameOver);
            g.drawString("game over!", 90, 170);
            g.drawString("����һ�Σ��밴F5", 10, 250);
            g.drawString(" ", 200, 230);//???���������ڣ���

            paintThread.pause();
        }
        if(score>100) {
            g.drawString("�ð�������", 90, 170);
            g.drawString("���Ѿ�����"+score+",��������", 10, 230);


        }

        /*
         * ��ͼ�ν�������Ϊ�տ�ʼ����ɫ
         * */
        g.setColor(c);

        s.eat(e);
        e.draw(g);
        s.draw(g);


    }

    @Override
    public void update(Graphics g) {
        if(offScreenImage == null) {
            /*
             * public Image createImage(int width,
             *           int height)
             * ����һ������˫����ġ�������Ļ����Ƶ�ͼ��
             * */
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
            while(running) {//�߳̽�һֱ�������е��У�ֻ������Ϸ������ʱ��
                if(pause) continue; 
                else repaint();//���������������������ᾡ�����paint()���������ǵ���update()

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
            Yard yard = this;
            snake s = new snake(yard);
            gameOver = false;
            score=0;
            
        }

        public void gameOver() {
            running = false;
        }

    }
    /*
     * �˺����Ĺ���Ϊ����������Ƿ���F2�������£������������̣߳������¿�ʼ��Ϸ
     * */
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
            s.keyPressed(e);
        }

    }

    /**
     * �õ����õķ���
     * @return ����
     */

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


