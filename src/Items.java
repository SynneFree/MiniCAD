import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * Description: the abstract class items
 * @author Veronica Tjan
 */
public abstract class Items 
{
	protected static final int POS_LEFT_TOP = 0;	
	protected static final int POS_LEFT_BOTTOM = 1;
	protected  static final int POS_RIGHT_TOP = 2;
	protected  static final int POS_RIGHT_BOTTOM = 3;
	
	public Graphics2D t_g2d;//current g2d
	public Color t_color;	//current color
	public int t_position;	//current position
	public boolean t_isChose;	//if current item is chosen
	public Rectangle2D t_boundRect;	//bounding outer rectangle
	public float t_stockWidth;	//stock width of current item
	public Stroke dash = new BasicStroke(1.0f,BasicStroke.CAP_BUTT,BasicStroke.JOIN_ROUND,3.5f,new float[]{15,10,},0f);
	
	/**
	 * Description	: the constructor of items
	 * @param g2d  	: the graphics2D object for showing
	 * @param color	: color of ellipse
	 * @param width	: display width of the item
	 * @param x1	: x of the upper left corner of the outside rectangle
	 * @param y1	: y of the upper left corner of the outside rectangle
	 * @param x2	: width of the outside rectangle
	 * @param y2	: height of the outside rectangle
	 */
	public Items(Graphics2D g2d, Color color, float width, double x1, double y1, double x2, double y2)
	{
		t_g2d = g2d;
		t_color = color;
		t_position = -1;
		t_stockWidth = width;
		t_isChose = false;
	}
	
	/**	
	 * Description	: change the Stock width
	 * @param choice: increase or decrease
	 */
	public void changeStoke(int choice)
	{
		if(choice == 1)
			t_stockWidth += 0.5f;
		else if(t_stockWidth >= 1f)
			t_stockWidth -= 0.5f;
	}
	
	/**
	 * Description	: scale current item
	 * @param choice: from center or border
	 */
	public void scale(int choice)
	{
		double detaX = t_boundRect.getWidth() * 0.1;
		double detaY = t_boundRect.getHeight() * 0.1;
		Point2D p1, p2;
		if(choice == 1)
		{
			p1 = new Point2D.Double(t_boundRect.getX() - detaX, t_boundRect.getY() - detaY);
			p2 = new Point2D.Double(t_boundRect.getX() + t_boundRect.getWidth() + detaX, t_boundRect.getY() + t_boundRect.getHeight() + detaY);
		}
		else
		{
			p1 = new Point2D.Double(t_boundRect.getX() + detaX, t_boundRect.getY() + detaY);
			p2 = new Point2D.Double(t_boundRect.getX() + t_boundRect.getWidth() - detaX, t_boundRect.getY() + t_boundRect.getHeight() - detaY);
		}
		System.out.println("ready to resize" );
		resize(p1, p2, true);
	}
	
	/**
	 * Description	: set drawing property
	 * @param g
	 */
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		Graphics2D tmp_g2d = t_g2d;
		t_g2d = (Graphics2D)g;
		t_g2d.setColor(t_color);
		t_g2d.setStroke(new BasicStroke(t_stockWidth));
		drawItems();
		if(t_isChose){
			t_g2d.setColor(Color.BLACK);
			t_g2d.setStroke(dash);  
			Rectangle2D dash_bound = new Rectangle2D.Double(t_boundRect.getX()-2,t_boundRect.getY()-2,t_boundRect.getWidth()+4,t_boundRect.getHeight()+4);
			t_g2d.draw(dash_bound);
		}
		t_g2d = tmp_g2d;
	}
	
	/**
	 * Description			: see if current item has the pressed point
	 * @param pressedPoint	: the pressed point
	 * @return				: true or false
	 */
	public boolean hasPoint(Point2D pressedPoint)
	{
		if(t_boundRect.contains(pressedPoint))
			return true;
		return false;
	}
	
	/**
	 * Description	: get pressed position of current item
	 * @param p		: the pressed point
	 * @return		: the corresponding position
	 */
	public int getPressedPosition(Point2D p)
	{
		Point2D center = new Point2D.Double(t_boundRect.getCenterX(), t_boundRect.getCenterY());
		if(p.getX() > center.getX() && p.getY() > center.getY())
			return POS_RIGHT_BOTTOM;
		else if(p.getX() > center.getX() && p.getY() < center.getY())
			return POS_RIGHT_TOP;
		else if(p.getX() < center.getX() && p.getY() > center.getY())
			return POS_LEFT_BOTTOM;
		else if(p.getX() < center.getX() && p.getY() < center.getY())
			return POS_LEFT_TOP;
		else 
			return -1;
	}

	/**
	 * Description	: get the fixed point of the opposite corner
	 * @param p		: the pressed point
	 * @return		: coordinate of the fixed point
	 */
	public Point2D getOppositePoint(Point2D p) {
		// TODO Auto-generated method stub
		t_position = getPressedPosition(p);
		if(t_position == POS_LEFT_TOP)
			return new Point2D.Double(t_boundRect.getX()+ t_boundRect.getWidth(), t_boundRect.getY() + t_boundRect.getHeight());
		else if(t_position == POS_LEFT_BOTTOM)
			return new Point2D.Double(t_boundRect.getX()+ t_boundRect.getWidth(), t_boundRect.getY());
		else if(t_position == POS_RIGHT_TOP)
			return new Point2D.Double(t_boundRect.getX(), t_boundRect.getY() + t_boundRect.getHeight());
		else if(t_position == POS_RIGHT_BOTTOM)
			return new Point2D.Double(t_boundRect.getX(), t_boundRect.getY());
		else 
			return null;
	}

	/**
	 * Description: draw a single item
	 */
	public abstract void drawItems();
	
	/**
	 * Description	: resize current item
	 * @param p1	: the position of the left top point
	 * @param p2	: the position of the right bottom point
	 * @param center: if the scale based on the center
	 */
	public abstract void resize(Point2D p1, Point2D p2, boolean center);
	/**
	 * Description		: move current item
	 * @param endPoint	: the endpoint of the movement
	 */
	public abstract void move(Point2D endPoint);
	
	/**
	 * Description	: get information from the text
	 * @return		: information of current item
	 */
	public abstract String getInfo();
	
	public void setChose()
	{
		t_isChose = true;
	}
	
	public void setNotChose()
	{
		t_position = -1;
		t_isChose = false;
	}
	
	public void setColor(Color c)
	{
		t_color = c;
	}
}

