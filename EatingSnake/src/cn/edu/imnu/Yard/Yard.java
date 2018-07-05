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
//awt Abstract Window Toolkit)
public class Yard extends Frame {
	/*
     * 画图的线程
     * */
    PaintThread paintThread = new PaintThread();
    private boolean gameOver = false; //游戏是否结束

    /**
     * 行数
     */
    public static final int ROWS = 40;
    /*
     * 列数
     * */
    public static final int COLS = 40;
    /*
     * 活动区域大小
     * */
    public static final int BLOCK_SIZE = 25;

    //设置显示字属性
    private Font fontGameOver = new Font("宋体", Font.BOLD, 60);

    //分数
    private int score = 0;
    /*
     * 记录开始时候的时间
     * */
    private long beginTime=0;
    /*
     *实例化Snake和Egg的对象
     * */
    Yard yard =this;
    snake s = new snake(yard);
    Egg e = new Egg();
    //创建一个 offScreenImage 的缓冲图像 
    Image offScreenImage = null;

    /*
     * 此函数的功能是：设置窗口的大小、位置、可见，以及点击事件和键盘事件，最后开启了绘图线程
     * */
    public void launch() {
        /*
         * 指定窗口的位置，窗口的左上角的位置为(90,10).是相对父窗口的相对位置
         * */
        this.setLocation(90, 10);
        /*
         * 设定窗口的大小
         * 宽度width：COLS*BLOCK_SIZE
         * 高度hight:ROWS*BLOCK_SIZE
         * */
        this.setSize(COLS * BLOCK_SIZE, ROWS * BLOCK_SIZE);
     //监视器或接收窗口事件的侦听器接口
        this.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e/*窗口焦点*/) {
                System.exit(0);
            }

        });
        /*
         * 将窗口设置可见
         * */
        this.setVisible(true);
        /*
         * 为窗口添加键盘事件
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
     * 将变量gameOver设置为true，使得在paint()函数中将使得线程停止
     * */
    public void stop() {
        gameOver = true;
    }

    @Override
    public void paint(Graphics g) {
        /*
         * 获取此图形上下文的颜色
         * */
        Color c = g.getColor();
        /*
         * 指定图形上下文的颜色
         * */
        g.setColor(Color.GRAY);
        /*
         * 用当前的颜色来填充指定的区域
         * */
        g.fillRect(0, 0, COLS * BLOCK_SIZE, ROWS * BLOCK_SIZE);
        /*
         * 再一次的指定颜色为：黑灰色？？？？？？？为什么要再一次的设定？？？
         * 原因在于：我们想将绘图的颜色与文字显示的不一样
         * */
        g.setColor(Color.DARK_GRAY);
        //画出横线
        /*
         * drawLine(int x1, int y1, int x2, int y2) 
         *  函数的功能为：
         * 在此图形上下文的坐标系中，使用当前颜色在点 (x1, y1) 和 (x2, y2) 之间画一条线。
         * 通过下面的两个for循环就会在图形化对象上画出表格
         * */
        for(int i=1; i<ROWS; i++) {
            g.drawLine(0, BLOCK_SIZE * i, COLS * BLOCK_SIZE, BLOCK_SIZE * i);
        }
        for(int i=1; i<COLS; i++) {
            g.drawLine(BLOCK_SIZE * i, 0, BLOCK_SIZE * i, BLOCK_SIZE * ROWS);
        }
        g.setColor(Color.YELLOW);//设定颜色，为下面显示文字信息做准备
        g.drawString("使用说明：使用方向键控制方向，F1--停止，F2--停止后恢复，F5--重新开始" , 10, 50);
        g.drawString("目前分数:" + score, 10, 70);
        g.drawString("加分规则：每吃一个加5分，加油！" , 10, 90);
        g.drawString("已经使用的时间："+(System.currentTimeMillis()-beginTime)/1000 , 10, 110);
        if(gameOver) {
            g.setFont(fontGameOver);
            g.drawString("game over!", 90, 170);
            g.drawString("在玩一次，请按F5", 10, 250);
            g.drawString(" ", 200, 230);
            paintThread.pause();
        }
        if(score>5) {
            g.drawString("牛逼！！！", 90, 170);
            g.drawString("你已经超过"+score+",继续加油", 10, 230);


        }
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
             * 创建一幅用于双缓冲的、可在屏幕外绘制的图像
             * */
            offScreenImage = this.createImage(COLS * BLOCK_SIZE, ROWS * BLOCK_SIZE);
        }
        /*
         * getGraphics() 
         *创建供绘制闭屏图像（off-screen image）使用的图形上下文。
         * */
        Graphics gOff = offScreenImage.getGraphics();
        paint(gOff);
        /*
         * drawImage(Image img, int x, int y, ImageObserver observer) 
         *      绘制指定图像中当前可用的图像。
         * */
        g.drawImage(offScreenImage, 0, 0,  null);
    }

    private class PaintThread implements Runnable {
        private boolean running = true;
        private boolean pause = false;
        public void run() {
            while(running) {//线程将一直处于运行当中，只有在游戏结束的时候
                if(pause) continue; 
                else repaint();//如果组件是轻量级组件，则会尽快调用paint()方法或者是调用update()

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
            s = new snake(Yard.this);//重画
            gameOver = false;
            
        }

        public void gameOver() {
            running = false;
        }

    }
    /*
     * 此函数的功能为：检测我们是否按下F2，若按下，则重新启动线程，即重新开始游戏
     * */
    private class KeyMonitor extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            if(key == KeyEvent.VK_F5) {
                paintThread.reStart();//重新开始游戏
            }
            else if(key==KeyEvent.VK_F1){
                paintThread.pause=true;//暂停
            }
            else if(key==KeyEvent.VK_F2){
                paintThread.pause=false;//从暂停中恢复
            }
            s.keyPressed(e);
        }

    }

    /**
     * 拿到所得的分数
     * @return 分数
     */

    public int getScore() {
        return score;
    }

    /**
     * 设置所得的分数
     * @param score 分数
     */

    public void setScore(int score) {
        this.score = score;
    }

}


