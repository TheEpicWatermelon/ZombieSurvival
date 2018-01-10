import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * @author Izzy
 *
 */
public class Gui {
	JFrame window;
	JPanel testPanel;
	Map map;

	Gui(){
		// have to make an input or something like that and then Client.clientIn = input;
		window = new JFrame("Test tile map");
		window.setSize(1080,1080);
		window.setResizable(false);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		testPanel = new GamePanel();
		window.add(testPanel);
		window.pack();
		window.setVisible(true);
		
		
		
	}
	
	public class GamePanel extends JPanel{
		public GamePanel(){
			//Map map = new Map(400,300);
		}
		
		 public void paintComponent(Graphics g) {
		    
		        super.paintComponent(g); //required to ensure the panel is correctly redrawn
		        map.draw(g,100, 100);//changed
		        repaint();
				// TODO: handle exception
			}
		    
	}
	
}

