package com.zhaodj.foo.graphics;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Canvas;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.geom.AffineTransform;
import java.util.Random;

public class DrawDemo extends WindowAdapter {
    
    private Frame frame;
    private DrawCanvas draw;

    public DrawDemo() {
        frame = new Frame("绘图测试");
        draw=new DrawCanvas();
        frame.add(draw);
        frame.setSize(400, 400);
        frame.setVisible(true);
        Button button=new Button("刷新");
        button.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent arg0) {
                DrawDemo.this.refresh();
            }
        });
        frame.add(button,BorderLayout.SOUTH);
    }
    
    private void refresh(){
        draw.repaint();
    }

    public static void main(String[] args) {
        new DrawDemo();
    }

    public class DrawCanvas extends Canvas {
        /**
         * 
         */
        private static final long serialVersionUID = 1L;
        
        private Random random=new Random();

        public void paint(Graphics graphics) {
            Graphics2D g = (Graphics2D) graphics;
            Font myFont = new Font(Font.SERIF , Font.BOLD , 20);
            g.setFont(myFont);
            String txt="Hello";
            for(int i=0;i<txt.length();i++){
                AffineTransform trans = new AffineTransform();
                int deg=random.nextInt(45)+(random.nextInt(2)==0?0:315);
                trans.rotate(deg*3.14/180,g.getFont().getSize()*i+g.getFont().getSize()/2, g.getFont().getSize()/2);
                g.setTransform(trans);
                g.drawString(String.valueOf(txt.charAt(i)), myFont.getSize()*i, myFont.getSize());
            }
            g.dispose();
        }
    }

}
