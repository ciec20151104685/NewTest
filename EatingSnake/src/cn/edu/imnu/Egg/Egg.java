package cn.edu.imnu.Egg;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

import cn.edu.imnu.Yard.Yard;

//����

public class Egg {
    int row, col;
    int w = Yard.BLOCK_SIZE;
    int h = Yard.BLOCK_SIZE;

    //��ʼλ�ò��������
    private static Random r = new Random();
    //��ɫ
    private Color color = Color.PINK;


    //�˶�ʱ��λ��
    public Egg(int row, int col) {
        this.row = row;
        this.col = col;
    }

    //��ʼʱ��λ��
    public Egg() {
        this(r.nextInt(Yard.ROWS-2) + 2, r.nextInt(Yard.COLS));
    }

    public void reAppear() {
        this.row = r.nextInt(Yard.ROWS-2) + 2;
        this.col = r.nextInt(Yard.COLS);
    }

    public Rectangle getRect() {
        return new Rectangle(Yard.BLOCK_SIZE * col, Yard.BLOCK_SIZE * row, w, h);
    }

    public void draw(Graphics g) {
        Color c = g.getColor();
        g.setColor(color);
        /*
         * public abstract void fillOval(int x,
                              int y,
                              int width,
                              int height)ʹ�õ�ǰ��ɫ������ָ�����ο����Բ��
         * */
        g.fillOval(Yard.BLOCK_SIZE * col, Yard.BLOCK_SIZE * row, w, h);
        g.setColor(c);
        if(color == Color.GREEN) color = Color.RED;
        else color = Color.GREEN;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

}