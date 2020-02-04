package snake;

import javax.swing.*;

/**贪吃蛇类
 * @author Kevin Guo
 * @date 2020-02-03
 * @time 22:40
 */
public class Snake {

    public static void main(String[] args){

        JFrame frame = new JFrame();
        frame.setBounds(280, 50, 1200, 922);
        frame.setTitle("Snake");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        SnakePanel panel = new SnakePanel();
        frame.add(panel);

        frame.setVisible(true);
    }
}
