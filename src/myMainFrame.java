import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JToolBar;

/**
 * Description: the mainFrame class of MiniCAD
 * Extends: JFrame
 * @author Veronica Tjan
 */
public class myMainFrame extends JFrame{
	private static final long serialVersionUID = 3659345350703127276L;
	private myActionListener myActionListener;
	private myItemController myItemController;
	private myCanvas canvas;
	private myToolBar toolBar;
	private myMenuBar menuBar;
	
	public myMainFrame(myActionListener actListener, myItemController tarController){
		//initialize variables and panels
		Toolkit toolKit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolKit.getScreenSize();
		myItemController = tarController;
		myActionListener =  actListener;
		canvas = new myCanvas(myActionListener, myItemController);	
		toolBar = new myToolBar(myActionListener);
		menuBar = new myMenuBar(myActionListener);
	
		//initialize layout MainFrame 
		this.setTitle("ZJU MiniCAD 2018");
		this.setMinimumSize(new Dimension(500, 500));
		this.setSize((int)screenSize.getWidth()/2, (int)screenSize.getHeight()/2);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout(5,5));
		this.setJMenuBar(menuBar);
		this.add(toolBar,BorderLayout.PAGE_START);
		this.add(canvas, BorderLayout.CENTER);	
		this.addKeyListener(actListener);
		this.setFocusable(true);
		this.setVisible(true);
	}
	
	public void repaintCanvas() {
		canvas.repaint();
	}
	
	public void requestCanvas()
	{
		canvas.requestFocus();
	}
	
	public Graphics2D getCanvasGraphics2d() {
		return (Graphics2D)canvas.getGraphics();
	} 
}

/**
 * Description: the menuBar class of MiniCAD
 * Extends: JMenuBar
 * @author Veronica Tjan
 */
class myMenuBar extends JMenuBar
{
	private static final long serialVersionUID = -6087080564688256949L;
	private JMenu fileMenu, operationMenu, helpMenu;
	private JMenuItem newFile, openFile, saveFile, aboutInfo, helpInfo;
	private JMenuItem createLine, createLinec, createRect, createRectFilled, createCircle, createCircleFilled, createRoundRect, createRoundRectFilled, createPolygon, createText, scaleItem, moveItem;
	private myActionListener myActionListener;
	
	public myMenuBar(myActionListener actionListener) {
		// TODO Auto-generated constructor stub
		myActionListener = actionListener;
		
		fileMenu = new JMenu("File");
		operationMenu = new JMenu("Operation");
		helpMenu = new JMenu("Help");

		newFile = new JMenuItem("New");
		newFile.setActionCommand("BTNEW");
		newFile.addActionListener(myActionListener);
		fileMenu.add(newFile);
		
		openFile = new JMenuItem("Open");
		openFile.setActionCommand("BTOPEN");
		openFile.addActionListener(myActionListener);
		fileMenu.add(openFile);
		
		saveFile = new JMenuItem("Save");
		saveFile.setActionCommand("BTSAVE");
		saveFile.addActionListener(myActionListener);
		fileMenu.add(saveFile);
		
		createLine = new JMenuItem("Create Line");
		createLine.setActionCommand("TYPE_LINE");
		createLine.addActionListener(myActionListener);
		operationMenu.add(createLine);
		
		createRect = new JMenuItem( "Create Rectangle");
		createRect.setActionCommand("TYPE_RECT");
		createRect.addActionListener(myActionListener);
		operationMenu.add(createRect);

		createRectFilled = new JMenuItem( "Create Filled Rectangle");
		createRect.setActionCommand("TYPE_RECTFILLED");
		createRect.addActionListener(myActionListener);
		operationMenu.add(createRectFilled);
		
		createCircle = new JMenuItem( "Create Circle");
		createCircle.setActionCommand("TYPE_ELLIPSE");
		createCircle.addActionListener(myActionListener);
		operationMenu.add(createCircle);

		createCircleFilled = new JMenuItem( "Create Filled Circle");
		createCircle.setActionCommand("TYPE_ELLIPSEFILLED");
		createCircle.addActionListener(myActionListener);
		operationMenu.add(createCircleFilled);
		
		createRoundRect = new JMenuItem( "Create Round Rectangle");
		createRoundRect.setActionCommand("TYPE_ROUNDRECT");
		createRoundRect.addActionListener(myActionListener);
		operationMenu.add(createRoundRect);

		createRoundRectFilled = new JMenuItem( "Create Filled Round Rectangle");
		createRoundRectFilled.setActionCommand("TYPE_ROUNDRECTFILLED");
		createRoundRectFilled.addActionListener(myActionListener);
		operationMenu.add(createRoundRectFilled);

		createPolygon = new JMenuItem( "Create Polygon");
		createPolygon.setActionCommand("TYPE_POLYGON");
		createPolygon.addActionListener(myActionListener);
		operationMenu.add(createPolygon);

		createText = new JMenuItem( "Create Text");
		createText.setActionCommand("TYPE_TEXT");
		createText.addActionListener(myActionListener);
		operationMenu.add(createText);
		
		operationMenu.addSeparator();
		
		scaleItem = new JMenuItem( "Scale item");
		scaleItem.setActionCommand("COMMAND_CHANGE_SIZE");
		scaleItem.addActionListener(myActionListener);
		operationMenu.add(scaleItem);
		
		moveItem = new JMenuItem("Move item");
		moveItem.setActionCommand("COMMAND_MOVE");
		moveItem.addActionListener(myActionListener);
		operationMenu.add(moveItem);
	
		aboutInfo = new JMenuItem("About");
		aboutInfo.setActionCommand("MENU_ABOUT");
		aboutInfo.addActionListener(myActionListener);
		helpMenu.add(aboutInfo);
		
		helpInfo = new JMenuItem("Help");
		helpInfo.setActionCommand("MENU_HELP");
		helpInfo.addActionListener(myActionListener);
		helpMenu.add(helpInfo);
		
		this.add(fileMenu);
		this.add(operationMenu);
		this.add(helpMenu);
		this.setPreferredSize(new Dimension(500, 32));
	}
}

/**
 * Description: the JToolBar class of MiniCAD
 * Extends: JToolBar
 * @author Veronica Tjan
 */
class myToolBar extends JToolBar
{
	private static final long serialVersionUID = 3743739626041084438L;
	private ImageIcon icLine, icLinec, icRect, icRect_filled, icCircle, icCircle_filled, icRoundRect, icRoundRect_filled, icPolygon, icText, icOpen, icNew, icSave, icChoose, icScale, icMove, icColor;
	private JButton  btLine, btLinec, btRect, btRect_filled, btCircle, btCircle_filled, btRoundRect, btRoundRect_filled, btPolygon, btText, btNew, btOpen, btSave, btChoose, btScale, btMove, btColor;
	private myActionListener myActionListener;
	
	public myToolBar(myActionListener actListener)
	{
		myActionListener = actListener;
		icChoose = new ImageIcon(new ImageIcon("icon/choose.png").getImage().getScaledInstance(25, 25, Image.SCALE_FAST)); 
		icNew = new ImageIcon(new ImageIcon("icon/cross.png").getImage().getScaledInstance(25, 25, Image.SCALE_FAST)); 
		icOpen = new ImageIcon(new ImageIcon("icon/open.png").getImage().getScaledInstance(25, 25, Image.SCALE_FAST)); 
		icSave = new ImageIcon(new ImageIcon("icon/save.png").getImage().getScaledInstance(25, 25, Image.SCALE_FAST)); 
		icScale =new ImageIcon(new ImageIcon("icon/scale.png").getImage().getScaledInstance(25, 25, Image.SCALE_FAST)); 
		icMove = new ImageIcon(new ImageIcon("icon/move.png").getImage().getScaledInstance(25, 25, Image.SCALE_FAST)); 
		icColor = new ImageIcon(new ImageIcon("icon/color.png").getImage().getScaledInstance(25, 25, Image.SCALE_FAST)); 
		icLine = new  ImageIcon(new ImageIcon("icon/line.png").getImage().getScaledInstance(25, 25, Image.SCALE_FAST));
		icLinec =  new  ImageIcon(new ImageIcon("icon/line_c.png").getImage().getScaledInstance(25, 25, Image.SCALE_FAST));
		icRect = new ImageIcon(new ImageIcon("icon/rect.png").getImage().getScaledInstance(25, 25, Image.SCALE_FAST)); 
		icRect_filled = new ImageIcon(new ImageIcon("icon/rect_filled.png").getImage().getScaledInstance(25, 25, Image.SCALE_FAST)); 
		icCircle = new ImageIcon(new ImageIcon("icon/circle.png").getImage().getScaledInstance(25, 25, Image.SCALE_FAST)); 
		icCircle_filled = new ImageIcon(new ImageIcon("icon/circle_filled.png").getImage().getScaledInstance(25, 25, Image.SCALE_FAST));
		icPolygon = new ImageIcon(new ImageIcon("icon/polygon.png").getImage().getScaledInstance(25, 25, Image.SCALE_FAST)); 
		icRoundRect = new ImageIcon(new ImageIcon("icon/roundrect.png").getImage().getScaledInstance(25, 25, Image.SCALE_FAST)); 
		icRoundRect_filled = new ImageIcon(new ImageIcon("icon/roundrect_filled.png").getImage().getScaledInstance(25, 25, Image.SCALE_FAST)); 
	    icText = new ImageIcon(new ImageIcon("icon/text.png").getImage().getScaledInstance(25, 25, Image.SCALE_FAST)); 
		
	    btChoose = new JButton(icChoose);
	    btChoose.setActionCommand("BTCHOSE");
	    btChoose.addActionListener(myActionListener);
	    
	    btNew = new JButton(icNew);
	    btNew.setActionCommand("BTNEW");
	    btNew.addActionListener(myActionListener);

		btOpen = new JButton(icOpen);
		btOpen.setActionCommand("BTOPEN");
		btOpen.addActionListener(myActionListener);
		
		btSave = new JButton(icSave);
		btSave.setActionCommand("BTSAVE");
		btSave.addActionListener(myActionListener);
		
		btLine = new JButton(icLine);
		btLine.setActionCommand("TYPE_LINE");
		btLine.addActionListener(myActionListener);
		
		//meh
		btLinec = new JButton(icLinec);
		btLinec.setActionCommand("TYPE_LINEC");
		btLinec.addActionListener(myActionListener);
		
		btRect = new JButton(icRect);
		btRect.setActionCommand("TYPE_RECT");
		btRect.addActionListener(myActionListener);
		
		//meh
		btRect_filled = new JButton(icRect_filled);
		btRect_filled.setActionCommand("TYPE_RECTFILLED");
		btRect_filled.addActionListener(myActionListener);
		
		btCircle = new JButton(icCircle);
		btCircle.setActionCommand("TYPE_ELLIPSE");
		btCircle.addActionListener(myActionListener);
		
		//meh
		btCircle_filled = new JButton(icCircle_filled);
		btCircle_filled.setActionCommand("TYPE_ELLIPSEFILLED");
		btCircle_filled.addActionListener(myActionListener);
		
		//meh
		btRoundRect = new JButton(icRoundRect);
		btRoundRect.setActionCommand("TYPE_ROUNDRECT");
		btRoundRect.addActionListener(myActionListener);
		
		//meh
		btRoundRect_filled = new JButton(icRoundRect_filled);
		btRoundRect_filled.setActionCommand("TYPE_ROUNDRECTFILLED");
		btRoundRect_filled.addActionListener(myActionListener);
		
		//meh
		btPolygon = new JButton(icPolygon);
		btPolygon.setActionCommand("TYPE_POLYGON");
		btPolygon.addActionListener(myActionListener);
		
		btText = new JButton(icText);
		btText.setActionCommand("TYPE_TEXT");
		btText.addActionListener(myActionListener);
		
		btScale = new JButton(icScale);
		btScale.setActionCommand("COMMAND_CHANGE_SIZE");
		btScale.addActionListener(myActionListener);
		
		btMove = new JButton(icMove);
		btMove.setActionCommand("COMMAND_MOVE");
		btMove.addActionListener(myActionListener);
		
		btColor = new JButton(icColor);
		btColor.setActionCommand("COMMAND_COLOR");
		btColor.addActionListener(myActionListener);
		
		this.add(btNew);
		this.add(btOpen);
		this.add(btSave);
		this.add(btChoose);
		this.add(btLine);
		this.add(btLinec);
		this.add(btRect);
		this.add(btRect_filled);
		this.add(btCircle);
		this.add(btCircle_filled);
		this.add(btRoundRect);
		this.add(btRoundRect_filled);
		this.add(btPolygon);
		this.add(btText);
		this.add(btScale);
		this.add(btMove);
		this.add(btColor);
		this.setAlignmentX(0); 
	}
}

/**
 * Description: the canvas for painting
 * Extends: JPanel 
 * @author Veronica Tjan
 */
class myCanvas extends JPanel
{
	private static final long serialVersionUID = -2355712177102116341L;
	private myActionListener myActionListener;
	private myItemController myItemController;
	
	public myCanvas(myActionListener actListener, myItemController tarController)
	{
		this.myActionListener = actListener;
		this.myItemController = tarController;
		this.setBackground(Color.WHITE);
		this.addMouseListener(myActionListener);
		this.addMouseMotionListener(myActionListener);
		this.addKeyListener(myActionListener);
	}

	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		this.myItemController.paintAll(g);
	}
}










