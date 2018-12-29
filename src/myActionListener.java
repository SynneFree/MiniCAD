import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;
import java.io.IOException;

import javax.swing.JColorChooser;
import javax.swing.JOptionPane;


/**
 * Description: the class implements MouseListener, MouseMotionListener, ActionListener, KeyListener
 * @author Veronica Tjan
 */
public class myActionListener implements MouseListener, MouseMotionListener, ActionListener, KeyListener{
	//Different states for create, scale and movement
	public enum Action{EMPTY, CREATEINIT, CREATE, SCALEINIT, GETPOSITION, SCALE, MOVEINIT, MOVECONFIM,  MOVE}
	private myItemController myItemController;	//controller for all items
	private myMainFrame mainFrame;	//current mainframe
	private Action currentAction;	//current action
	private Items currentItem;		//current item
	private String createType;		//creating type: line, circle, rectangle or text
	private Point2D createPoint;	//creating position
	private Point2D resizePoint;	//resizing point
	private static Point2D pressedPoint = new Point2D.Double();	//point of mouse pressing
	private Color color;			//paint color 
	private boolean pressedLeft;	//if last press is on the left

	
	/**
	 * Description: constructor of myActionListener
	 * @param tarController	: controller for all items
	 */
	public myActionListener(myItemController tarController) 
	{
		// TODO Auto-generated constructor stub
		myItemController = tarController;
		currentAction = Action.EMPTY;
	}
	
	/**
	 * Description: set mainframe 
	 * @param f	: current mainframe
	 */
	public void setMainFrame(myMainFrame f)
	{
		mainFrame = f;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		Component source = e.getComponent();
		pressedPoint.setLocation(e.getX(), e.getY());
		System.out.println("pressed point:" + pressedPoint);
		if(source instanceof myCanvas)
		{
			//press left button
			if(e.getButton() == MouseEvent.BUTTON1)
			{
				if(currentItem != null)
					currentItem.setNotChose();	
				
				//no instruction, reselect current item
				if(currentAction == Action.EMPTY)
				{
					currentItem = myItemController.chooseItem(pressedPoint);
					if(currentItem != null)
						currentItem.setChose();
				}
				//the first step in creation, set the first point, repaint
				else if(currentAction == Action.CREATEINIT)
				{
					if(createType.equals("TYPE_LINE"))
						currentItem = myItemController.createItem(createType, pressedPoint.getX(), pressedPoint.getY(), pressedPoint.getX(), pressedPoint.getY());
					else
						currentItem = myItemController.createItem(createType, pressedPoint.getX(), pressedPoint.getY(), 0, 0);
				
					currentItem.setChose();
					createPoint = new Point2D.Double(pressedPoint.getX(),pressedPoint.getY());
					currentAction = Action.CREATE;
				}
				//the second step of creation, end the creation, return to its first step
				else if(currentAction == Action.CREATE)
				{
					currentItem.resize(createPoint, pressedPoint,false);
					currentItem = null;
					currentAction = Action.CREATEINIT;
				}
				//the first step of movement, choose item
				else if(currentAction == Action.MOVEINIT)
				{
					currentItem = myItemController.chooseItem(pressedPoint);
					if(currentItem != null)
					{
						currentItem.setChose();
						currentAction = Action.MOVECONFIM;
					}
				}
				//the second step of movement, confirm item
				else if(currentAction == Action.MOVECONFIM)
				{
					Items tmp = myItemController.chooseItem(pressedPoint);
					if(tmp == currentItem)
					{
						currentItem.setChose();
						currentAction = Action.MOVE;
					}
					else
					{
						currentItem = null;
						currentAction = Action.MOVEINIT;
					}
				}
				//the third step of movement, end movement, return to its first step
				else if( currentAction == Action.MOVE)
				{
					currentItem.move(pressedPoint);
					currentItem = null;
					currentAction = Action.MOVEINIT;
				}
				//the first step of scale, choose item
				else if(currentAction == Action.SCALEINIT)
				{
					currentItem = myItemController.chooseItem(pressedPoint);
					if(currentItem != null)
					{
						currentItem.setChose();
						currentAction = Action.GETPOSITION;
					}
				}
				//the second step of scale, get the fixed corner
				else if(currentAction == Action.GETPOSITION)
				{
					currentItem.setChose();
					resizePoint = currentItem.getOppositePoint(pressedPoint);
					currentAction = Action.SCALE;
				}
				//the third step of scale, end scale, return to its first step
				else if(currentAction == Action.SCALE)
				{
					currentItem.resize(resizePoint, pressedPoint, false);
					currentItem = null;
					currentAction = Action.SCALEINIT;
				}
				pressedLeft = true;
				source.repaint();
			}
			else if(e.getButton() == MouseEvent.BUTTON3)
			{
				//cancel creation
				if(currentAction == Action.CREATE)
				{
					myItemController.removeTopItem();
					currentAction = Action.CREATEINIT;
				}
				//changing chosen item
				else if(currentAction == Action.EMPTY || currentAction == Action.MOVECONFIM || currentAction == Action.GETPOSITION)
				{
					currentItem.setNotChose();
					currentItem = myItemController.rechooseItem(pressedPoint, pressedLeft);
					if(currentItem != null)
						currentItem.setChose();
				}
				pressedLeft = false;
				source.repaint();
			}
			else {
				pressedLeft = false;
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseDragged(MouseEvent e) {}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		Component source = e.getComponent();
		Point2D movedPoint2d = new Point2D.Double(e.getX(), e.getY());
		if(source instanceof myCanvas) {
			//create item with movement
			if(currentAction == Action.CREATE)
			{
				currentItem.resize(createPoint,movedPoint2d,false);
				source.repaint();
			}
			//scale item with movement
			else if(currentAction == Action.SCALE)
			{
				currentItem.resize(resizePoint, movedPoint2d,false);
				source.repaint();
			}
			//move item with movement
			else if(currentAction == Action.MOVE)
			{
				currentItem.move(movedPoint2d);
				source.repaint();
			}
		}
		else{
			// pressed on other components, do nothing.
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		// TODO Auto-generated method stub
		//open a file
		if(e.getActionCommand() == "BTOPEN")
		{
			try {
				if(myItemController.loadItems() == true)
				{
					mainFrame.repaint();
				}
				else
					currentAction = Action.EMPTY;
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		//clear the page
		else if(e.getActionCommand() == "BTNEW")
		{
			if(currentItem!=null) {
				myItemController.removeItem(currentItem);
				currentItem = null;
				mainFrame.repaint();
			}else {
			myItemController.clear();
			mainFrame.repaint();
			}
			
		}
		//save a file
		else if(e.getActionCommand() == "BTSAVE")
		{
			try {
				myItemController.saveItems();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		//show about dialog
		else if(e.getActionCommand() == "MENU_ABOUT")
		{
			JOptionPane.showMessageDialog(null, "Author: Veronica Tjan", "About", JOptionPane.INFORMATION_MESSAGE);
		}
		//show help dialog
		else if(e.getActionCommand() == "MENU_HELP")
		{
			JOptionPane.showMessageDialog(null, " VK_UP : Magnify \n VK_DOWN : Shrink \n VK_LEFT : decrease the stock width \n "
					+ "VK_RIGHT: increase the stock width \n VK_R : remove chosen item \n", "Help", JOptionPane.INFORMATION_MESSAGE);
		}	
		//change color
		else if(e.getActionCommand() == "COMMAND_COLOR")
		{
			color = JColorChooser.showDialog((Component) source, "Choose Color", Color.BLACK);
			if(currentItem != null) 
				currentItem.setColor(color);
			else
				myItemController.setColor(color);				
			currentAction = Action.EMPTY;
		}
		else
		{
			if(currentItem != null)
				currentItem.setNotChose();
			//create a new item
			if(e.getActionCommand() == "TYPE_LINE" || e.getActionCommand() == "TYPE_RECT" || 
					  e.getActionCommand() == "TYPE_RECTFILLED"||e.getActionCommand() == "TYPE_ELLIPSEFILLED"||  
					  e.getActionCommand() == "TYPE_ELLIPSE" || e.getActionCommand() == "TYPE_TEXT" ||
					  e.getActionCommand() == "TYPE_ROUNDRECT" || e.getActionCommand() == "TYPE_ROUNDRECTFILLED" ||
					  e.getActionCommand() == "TYPE_POLYGON"|| e.getActionCommand() == "TYPE_LINEC" )
			{
				currentAction = Action.CREATEINIT;
				createType = e.getActionCommand();
			}
			//scale an item
			else if(e.getActionCommand() == "COMMAND_CHANGE_SIZE")
				currentAction = Action.SCALEINIT;
			//move an item
			else if(e.getActionCommand() == "COMMAND_MOVE")
				currentAction = Action.MOVEINIT;
			//chose an item
			else if(e.getActionCommand() == "BTCHOSE")
				currentAction = Action.EMPTY;
		}	
		mainFrame.repaint();
		mainFrame.requestCanvas();
	}
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		Component source = e.getComponent();
		//push up to magnify
		if(e.getKeyCode() == KeyEvent.VK_UP)
		{
			System.out.println("up pressed");
			if(currentItem.t_isChose)
				currentItem.scale(1);
			source.repaint();	
		}
		//push down to shrink
		else if(e.getKeyCode() == KeyEvent.VK_DOWN)
		{
			System.out.println("down pressed");
			if(currentItem.t_isChose)
				currentItem.scale(0);
			source.repaint();
		}
		//push left to reduce the width
		else if(e.getKeyCode() == KeyEvent.VK_LEFT)
		{
			System.out.println("left pressed");
			if(currentItem.t_isChose)
				currentItem.changeStoke(0);
			source.repaint();
		}
		//push right to increase the width
		else if(e.getKeyCode() ==  KeyEvent.VK_RIGHT)
		{
			System.out.println("right pressed");
			if(currentItem.t_isChose)
				currentItem.changeStoke(1);
			source.repaint();
		}
		//push R to remove the chosen item
		else if(e.getKeyCode() == KeyEvent.VK_R)
		{
			System.out.println("R pressed");
			if(currentItem!=null)
				myItemController.removeItem(currentItem);
			currentAction = Action.EMPTY;
			source.repaint();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void keyTyped(KeyEvent e) {}
	

}
