import java.awt.Color;
import java.awt.FileDialog;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

import javax.swing.JOptionPane;

/**
 * Description: the controller of all items 
 * @author Veronica Tjan
 */
public class myItemController {	
	private LinkedList<Items> itemList = new LinkedList<>();	//list storing all items
	private myMainFrame mainFrame;	//current mainframe
	private Graphics2D t_g2d;		//current graphics2D item
	private float t_width = 1.5f;	//current item width
	private Color t_color = Color.black;	//current black
	
	public myItemController() {
		// TODO Auto-generated constructor stub
	}
	
	public void clear() {
		itemList.clear();
	}
	
	/**
	 * Description  : create a new item with corresponding type
	 * @param type	: type you want to create
	 * @param x1	: x of the upper left corner of the outside rectangle
	 * @param y1	: y of the upper left corner of the outside rectangle
	 * @param x2	: width of the outside rectangle
	 * @param y2	: height of the outside rectangle
	 * @return		: the created item
	 */
	public Items createItem(String type, double x1, double y1, double x2, double y2)
	{
		//create line
		if(type.equals("TYPE_LINE"))
		{
			Items line = new ItemLine(t_g2d, t_color, t_width, x1, y1, x2, y2);
			itemList.add(line);
			return line;
		}
		if(type.equals("TYPE_LINEC"))
		{
//			Items linec = new ItemLinec(t_g2d, t_color, t_width, x1, y1, x2, y2);
//			itemList.add(linec);
//			return linec;
		}
		//create rectangle
		else if(type.equals("TYPE_RECT"))
		{
			Items rect = new ItemRect(t_g2d, t_color, t_width, x1, y1, x2, y2);
			itemList.add(rect);
			return rect;
		}
		//create filled rectangle
		else if(type.equals("TYPE_RECTFILLED"))
		{
			Items rectfilled = new ItemRectFilled(t_g2d, t_color, t_width, x1, y1, x2, y2);
			itemList.add(rectfilled);
			return rectfilled;
		}
		//create circle
		else if(type.equals("TYPE_ELLIPSE"))
		{
			Items ellipse = new ItemEllipse(t_g2d, t_color, t_width, x1, y1, x2, y2);
			itemList.add(ellipse);
			return ellipse;
		}
		//create filled circle
		else if(type.equals("TYPE_ELLIPSEFILLED"))
		{
			Items ellipsefilled = new ItemEllipseFilled(t_g2d, t_color, t_width, x1, y1, x2, y2);
			itemList.add(ellipsefilled);
			return ellipsefilled;
		}
		//create roundrect
		else if(type.equals("TYPE_ROUNDRECT"))
		{
			Items roundrect = new ItemRoundRect(t_g2d, t_color, t_width, x1, y1, x2, y2);
			itemList.add(roundrect);
			return roundrect;
		}
		//create filled roundrect
		else if(type.equals("TYPE_ROUNDRECTFILLED"))
		{
			Items roundrectfilled = new ItemRoundRectFilled(t_g2d, t_color, t_width, x1, y1, x2, y2);
			itemList.add(roundrectfilled);
			return roundrectfilled;
		}
		//create polygon
		else if(type.equals("TYPE_POLYGON"))
		{
			Items polygon = new ItemPolygon(t_g2d, t_color, t_width, x1, y1, x2, y2);
			itemList.add(polygon);
			return polygon;
		}
		//create text
		else if(type.equals("TYPE_TEXT"))
		{
			Items text = new ItemText(t_g2d, null, t_color, t_width, x1, y1, x2, y2);
			itemList.add(text);
			return text;
		}
		return null;
	}

	/**
	 * Description  		: choose a new item who has the pressed point
	 * @param pressedPoint	: the pressed point
	 * @return 				: the chosen item 			
	 */
	public Items chooseItem(Point2D pressedPoint)
	{
		Items tmpItem = null;
		for(int i=0; i<itemList.size(); i++)
		{
			if(itemList.get(i).hasPoint(pressedPoint))
			{
				tmpItem = itemList.get(i);
				break;
			}
		}
		return tmpItem;
	}
	
	/**
	 * Description  		: choose a new item who has the pressed point
	 * @param pressedPoint	: chosen point
	 * @param leftPressed	: if left pressed, skip current item
	 * @return				: the chosen item 
	 */
	public Items rechooseItem(Point2D pressedPoint, boolean leftPressed)
	{
		Items tmpItem = chooseItem(pressedPoint);
		if(tmpItem != null)
		{
			itemList.remove(tmpItem);
			itemList.add(tmpItem);
		}
		if(leftPressed)
		{
			tmpItem = chooseItem(pressedPoint);
			if(tmpItem != null)
			{
				itemList.remove(tmpItem);
				itemList.add(tmpItem);
			}
		}
		return tmpItem;
	}
	
	/**
	 * Description  		: move an item from the list
	 * @param removedItem	: the removed item
	 */
	public void removeItem(Items removedItem)
	{
		for(Items t: itemList)
		{
			if(t == removedItem)
			{
				itemList.remove(t);
				break;
			}
		}
	}
	/**
	 * Description : remove the top item of the list
	 */
	public void removeTopItem()
	{
		itemList.remove(itemList.size() - 1);
	}
	/**
	 * Description  : set mainframe of current controller
	 * @param f		: the mainframe
	 */
	public void setMainFrame(myMainFrame f)
	{
		mainFrame = f;
		t_g2d = mainFrame.getCanvasGraphics2d();
	}
	/**
	 * @param c		: the setting color
	 */
	public void setColor(Color c)
	{
		t_color = c;
	}
	/**
	 * Description 	: paint all items in the list
	 * @param g		: the paint graphics item
	 */
	public void paintAll(Graphics g)
	{
		for(Items t: itemList)
			t.draw(g);
	}
	/**
	 * Description	: load items from a file
	 * @return		: if loading success
	 * @throws IOException
	 */
	public boolean loadItems() throws IOException
	{
		FileDialog openDia = new FileDialog(mainFrame, "Open", FileDialog.LOAD);
		openDia.setVisible(true);
		String dirPath = openDia.getDirectory();
		String fileName = openDia.getFile();
		System.out.println(dirPath + fileName);
		if (dirPath == null || fileName == null)
			return false;
		else
		{
			itemList.clear();
			String str = null;
			File file = new File(dirPath + fileName);
			BufferedReader bReader = new BufferedReader(new FileReader(file));
			while((str = bReader.readLine()) != null)
			{
				System.out.println(str);
				String [] arr = str.split(",");
				t_color = new Color(Integer.parseInt(arr[1]));
				t_width = Float.parseFloat(arr[2]);
				if(arr[0].equals("TYPE_TEXT"))
				{
					Items text = new ItemText(t_g2d, arr[3], t_color, Float.parseFloat(arr[2]),  Integer.parseInt(arr[4]), Integer.parseInt(arr[5]), Integer.parseInt(arr[6]), Integer.parseInt(arr[7]));
					itemList.add(text);
				}
				else
					createItem(arr[0], Integer.parseInt(arr[3]), Integer.parseInt(arr[4]), Integer.parseInt(arr[5]), Integer.parseInt(arr[6]));
 			}
			bReader.close();
		}
		return true;
	}
	/**
	 * Description	: save items into a file
	 * @throws IOException
	 */
	public void saveItems() throws IOException
	{
		if(itemList.size() == 0)
			JOptionPane.showMessageDialog(null, "Warning: the canvas is empty", "ERROR", JOptionPane.ERROR_MESSAGE);
		else
		{
			FileDialog saveDia = new FileDialog(mainFrame, "Save", FileDialog.SAVE);;
			saveDia.setVisible(true);
			String dirPath = saveDia.getDirectory();
			String fileName = saveDia.getFile();
			if (dirPath == null || fileName == null)
				return;
			else
			{
				File file = new File(dirPath + fileName);
				FileWriter fWriter = new FileWriter(file, false);
				BufferedWriter bWriter = new BufferedWriter(fWriter);
				for(Items t: itemList)
				{
					bWriter.write(t.getInfo());
				}
				bWriter.close();
				fWriter.close();
				
			}
		}
	}
	
}
