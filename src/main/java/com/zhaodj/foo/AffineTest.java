package com.zhaodj.foo;

/**
 * Created by djzhao on 16-2-19.
 */
import java.applet.Applet;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.Random;


public class AffineTest extends Applet implements ItemListener{

    private Rectangle2D rect;

    private Checkbox rotateFirst;
    private Checkbox translateFirst;

    public void init()
    {
        setLayout(new BorderLayout());
        CheckboxGroup cbg = new CheckboxGroup();
        Panel p = new Panel();
        rotateFirst = new Checkbox("rotate, translate", cbg, true);
        rotateFirst.addItemListener(this);
        p.add(rotateFirst);
        translateFirst = new Checkbox("translate, rotate", cbg, false);
        translateFirst.addItemListener(this);
        p.add(translateFirst);
        add(p, BorderLayout.SOUTH);
        rect = new Rectangle2D.Float(10f, 10f, 1.0f, 1.0f);
    }

    public void paint(Graphics g) {
        paintTestB(g);
    }

    private void paintTestA(Graphics g){
        Graphics2D g2d = (Graphics2D)g;
        final AffineTransform identify = new AffineTransform();
        boolean rotate = rotateFirst.getState();
        Random r = new Random();
        final double oneRadian = Math.toRadians(1.0);
        for(double radians = 0.0; radians < Math.PI ; radians += oneRadian)
        {
            g2d.setTransform(identify);
            if(rotate)
            {
                g2d.translate(100, 100);
                g2d.rotate(radians);
            }
            else
            {
                g2d.rotate(radians);
                g2d.translate(100, 100);
            }
            g2d.scale(100, 100);
            g2d.setColor(new Color(r.nextInt()));
            g2d.fill(rect);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void paintTestB(Graphics g){
        System.out.println("=============================");
        Graphics2D g2d = (Graphics2D)g;
        Random r = new Random();

//        g2d.scale(100, 100);
//        g2d.setColor(new Color(r.nextInt()));
//        g2d.draw(rect);

        g2d.translate(0, 100);
        double prex = 0;
        for(int i=0;i<4;i++){
            AffineTransform fontAT = new AffineTransform();
            double shx = (Math.random() - 0.5D) * 0.6D;
            System.out.println(shx);
            g2d.transform(fontAT);
            boolean rotate = rotateFirst.getState();
            if(rotate){
                fontAT.setToShear(prex, 0.0D);
            }else{
                fontAT.setToShear(0.0D, prex);
            }
            g2d.transform(fontAT);
            if(rotate){
                fontAT.setToShear(shx, 0.0D);
            }else{
                fontAT.setToShear(0.0D, shx);
            }
            g2d.translate(50, 0);
            g2d.transform(fontAT);
            //g2d.setTransform(fontAT);
            g2d.setColor(new Color(r.nextInt()));
            g2d.draw(new Rectangle2D.Float(0, 0, 40f, 40f));
            prex = -shx;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void itemStateChanged(ItemEvent arg0) {
        // TODO Auto-generated method stub
        repaint();
    }

}