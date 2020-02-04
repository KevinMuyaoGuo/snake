package snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

/**贪吃蛇显示面板类
 * @author Kevin Guo
 * @date 2020-02-03
 * @time 22:48
 */
public class SnakePanel extends JPanel implements KeyListener, ActionListener {

    ImageIcon head = new ImageIcon("image/head.png");
    ImageIcon body = new ImageIcon("image/body.png");
    ImageIcon food = new ImageIcon("image/food.png");

    int[] snakex = new int[900];//蛇体方块x坐标
    int[] snakey = new int[900];//蛇体方块y坐标
    int len;

    Random rand = new Random();
    int foodx = rand.nextInt(30) * 30;//食物方块x坐标
    int foody = rand.nextInt(30) * 30;//食物方块y坐标

    int score;//分数
    int speed;//速度

    String direction;//方向：Right, Left, Up, Down

    boolean isStarted;//游戏是否开始
    boolean isFailed;//游戏是否失败

    Timer timer;

    public SnakePanel() {
        this.setFocusable(true);
        this.addKeyListener(this);
        isStarted=false;
        timer = new Timer(100, this);
        initialSetup();
        timer.start();
    }

    public void paint(Graphics g) {
        this.setBackground(Color.DARK_GRAY);
        g.fillRect(0, 0, 900, 900);

        //画蛇头
        head.paintIcon(this, g, snakex[0], snakey[0]);

        //画蛇体
        for (int i = 1; i < len; i++) {
            body.paintIcon(this, g, snakex[i], snakey[i]);
        }

        //游戏开始提示
        if (!isStarted) {
            g.setColor(Color.white);
            g.setFont(new Font("arial", Font.BOLD, 40));
            g.drawString("Press SPACE to start / pause", 180, 420);
        }

        //画食物
        food.paintIcon(this, g, foodx, foody);

        //游戏结束提示
        if (isFailed) {
            g.setColor(Color.white);
            g.setFont(new Font("arial", Font.BOLD, 40));
            g.drawString("Game over!  Press SPACE to restart", 125, 420);
        }


        g.setColor(Color.WHITE);
        g.setFont(new Font("arial", Font.BOLD, 30));
        //记分数
        g.drawString("Score: ",940,80);
        g.drawString(""+score,1080,80);
        //记长度
        g.drawString("Length: ",940,140);
        g.drawString(""+len,1080,140);
        //记速度
        g.drawString("Speed: ",940,200);
        g.drawString(""+speed,1080,200);

        //游戏提示信息
        g.setFont(new Font("arial", Font.PLAIN, 15));
        g.drawString("Tip:  snake can pass thru the wall",920,840);
        g.drawString("         and come from the other side.",920,860);

        //作者
        g.setFont(new Font("arial", Font.PLAIN, 10));
        g.setColor(Color.LIGHT_GRAY);
        g.drawString("made by Kevin GuoMY",1085,10);
    }

    public void initialSetup() {
        len = 3;
        direction = "R";
        score=0;
        speed=10;

        timer.setDelay(100);

        snakex[0] = 120;
        snakey[0] = 60;
        snakex[1] = 90;
        snakey[1] = 60;
        snakex[2] = 60;
        snakey[2] = 60;

        isFailed = false;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_SPACE:
                if (isFailed) {
                    initialSetup();
                }else{
                    isStarted = !isStarted;
                }
                //repaint();
                break;
            case KeyEvent.VK_RIGHT:
                if (direction != "L") {
                    direction = "R";
                }
                break;
            case KeyEvent.VK_LEFT:
                if (direction != "R") {
                    direction = "L";
                }
                break;
            case KeyEvent.VK_DOWN:
                if (direction != "U") {
                    direction = "D";
                }
                break;
            case KeyEvent.VK_UP:
                if (direction != "D") {
                    direction = "U";
                }
                break;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //再定一个Timer闹钟
        timer.start();

        //移动数据
        if (isStarted&&!isFailed) {
            //移动蛇体坐标
            for (int i = len; i > 0; i--) {
                snakex[i] = snakex[i - 1];
                snakey[i] = snakey[i - 1];
            }
            //移动蛇头坐标
            if (direction.equals("R")) {
                snakex[0] += 30;
                if (snakex[0] >= 900) {
                    snakex[0] = 0;
                }
            } else if (direction.equals("L")) {
                snakex[0] -= 30;
                if (snakex[0] < 0) {
                    snakex[0] = 870;
                }
            } else if (direction.equals("D")) {
                snakey[0] += 30;
                if (snakey[0] >= 900) {
                    snakey[0] = 0;
                }
            } else if (direction.equals("U")) {
                snakey[0] -= 30;
                if (snakey[0] < 0) {
                    snakey[0] = 870;
                }
            }
            //吃食物
            if (snakex[0] == foodx && snakey[0] == foody) {
                len++;
                if (len % 10 == 0) {
                    //延时递减，蛇速加快
                    timer.setDelay(timer.getDelay()-10);
                    speed+=10;
                }
                score+=100;
                foodx = rand.nextInt(30) * 30;
                foody = rand.nextInt(30) * 30;
                //以下循环保证新出现食物与蛇体不重合
                for (int i = 1; i < len&&foodx==snakex[i]&&foody==snakey[i]; i++) {
                    foodx = rand.nextInt(30) * 30;
                    foody = rand.nextInt(30) * 30;
                }
            }
            //蛇头碰蛇体？
            for (int i = 1; i < len; i++) {
                if (snakex[0] == snakex[i] && snakey[0] == snakey[i]) {
                    isFailed=true;
                }
            }
        }

        //repaint
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
