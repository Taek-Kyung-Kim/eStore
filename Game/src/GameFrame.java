import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
public class GameFrame extends JFrame {
       private GamePanel p = new GamePanel();
       public GameFrame(){
              setTitle("사격 게임");
              setContentPane(p);
              setSize(300, 300);
              setVisible(true);
              p.startGame();
              
       }
       
       
       
       class GamePanel extends JPanel {
              JLabel baseLabel;          
              JLabel targetLabel;
              JLabel bulletLabel;
              public GamePanel(){
                     setBackground(Color.YELLOW);
                     setLayout(null);
                     
                     ImageIcon icon = new ImageIcon("img/1.png");
                     targetLabel = new JLabel(icon);
                     targetLabel.setSize(icon.getIconWidth(),icon.getIconHeight());
                     targetLabel.setLocation(0,0);
                     add(targetLabel);
                     
                     
                     baseLabel = new JLabel();
                     baseLabel.setSize(40,40);
                     baseLabel.setOpaque(true);
                     baseLabel.setBackground(Color.BLACK);
                     
                     baseLabel.addMouseListener(new MouseAdapter(){
                           public void mouseClicked(MouseEvent e){
                                  JLabel la = (JLabel)e.getSource();
                                  la.requestFocus();
                                  
                           }
                     });
                     add(baseLabel);
                     
                     bulletLabel = new JLabel();
                     bulletLabel.setSize(10,10);
                     bulletLabel.setOpaque(true);
                     bulletLabel.setBackground(Color.RED);
                     add(bulletLabel);
              }
       
              public void startGame(){
                     baseLabel.requestFocus();
                     baseLabel.setLocation(this.getWidth()/2-20, this.getHeight()-40);
                     bulletLabel.setLocation(this.getWidth()/2-5, this.getHeight()-40-10);
                     TargetThread targetThread = new TargetThread(targetLabel);
                     targetThread.start();
                     
                     baseLabel.addKeyListener(new KeyAdapter() {
                           BulletThread bulletThread = null;
                           public void keyPressed(KeyEvent e) {
                                  if(e.getKeyChar() == '\n') {
                                         if(bulletThread == null || !bulletThread.isAlive()) {
                                         bulletThread = new BulletThread(bulletLabel, targetLabel, targetThread);
                                         bulletThread.start();
                                         }
                                  }
                           }
                     });
                     
              }
       class TargetThread extends Thread {
              JLabel targetLabel;
              public TargetThread(JLabel targetLabel){
                     this.targetLabel = targetLabel;
                     
              }
              public void run(){
                     
                           while(true){
                                  ImageIcon deathIcon = new ImageIcon("img/2.png");
                                  
                                  
                                  int x = targetLabel.getX() + 10;         
                                  int y = targetLabel.getY();
                                  if(x > GamePanel.this.getWidth() - targetLabel.getWidth())
                                         x = 0;
                                         targetLabel.setLocation(x, y);
                                         targetLabel.getParent().repaint();
                                         
                                  try {
                                         sleep(100);
                                  }catch(InterruptedException e){
                                  //내가 총 맞았어.
                                  
                                         Icon aliveIcon = targetLabel.getIcon();
                                         targetLabel.setIcon(deathIcon);
                                         targetLabel.getParent().repaint();
                                         try {
                                                sleep(500);
                                         } catch (InterruptedException e1) {}
                                         targetLabel.setLocation(0, 0);
                                         targetLabel.setIcon(aliveIcon);
                                                
                                         
                                  }
                           }
                     }
              }
       
              class BulletThread extends Thread {
                     TargetThread targetThread;
                     JLabel bulletLabel;
                     JLabel target;
                     public BulletThread(JLabel bulletLabel, JLabel target, TargetThread targetThread) {
                           this.bulletLabel = bulletLabel;
                           this.target = target;
                           this.targetThread = targetThread;
                     }
                     
                     public boolean hit(){
                      if(targetContains(bulletLabel.getX(), bulletLabel.getY()) ||
                                  targetContains(bulletLabel.getX(),
                                         bulletLabel.getY()+bulletLabel.getHeight()) ||
                                  targetContains(bulletLabel.getX()+bulletLabel.getWidth(),
                                                bulletLabel.getY()) ||
                                  targetContains(bulletLabel.getX()+bulletLabel.getWidth(),
                                                bulletLabel.getY()+bulletLabel.getWidth()))
                                  return true;
                           else
                                  return false;
                     
                     }
                     public boolean targetContains(int x, int y){
                           if(target.getX() < x && x <= target.getX()+target.getWidth()
                                  && target.getY() <= y && y  <= target.getY() + target.getHeight())
                                  return true;
                           else
                                  return false;
                     }
                     public void run(){
                           while(true){
                                  if(hit()) {
                                         //target.setLocation(0,0);
                                         //target.getParent().repaint();
                                         targetThread.interrupt();
                                         
                                         
                                         int x = bulletLabel.getX();
                                         int y = GamePanel.this.getHeight()-40-10;
                                         bulletLabel.setLocation(x , y);
                                         return; //Thread 종료
                                  }
                                  
                                  int x = bulletLabel.getX();
                                  int y = bulletLabel.getY() - 5;
                                  if(y < 0) {
                                         y = GamePanel.this.getHeight()-40-10;
                                  bulletLabel.setLocation(x , y);
                                  return;  //Thread 종료
                                  }
                                  
                                  bulletLabel.setLocation(x , y);
                                  bulletLabel.getParent().repaint();
                                  
                                  try {
                                         sleep(20);
                                  } catch (InterruptedException e) {}
                           
                           }
                     }
              }
       }
       public static void main(String[] args) {
              new GameFrame();
       }
       }
