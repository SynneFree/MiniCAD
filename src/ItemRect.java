import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
/**
 * Description: the derived class for rectangle
 * Extends: items 
 * @author Veronica Tjan
 */
public class ItemRect extends Items{
	private Rectangle2D rect;	//private item rectangle
	
	/**
	 * Description	: the constructor of rectangle
	 * @param g2d  	: the graphics2D object for showing
	 * @param color	: color of ellipse
	 * @param width	: display width of the item
	 * @param x1	: x of the upper left corner of the outside rectangle
	 * @param y1	: y of the upper left corner of the outside rectangle
	 * @param x2	: width of the outside rectangle
	 * @param y2	: height of the outside rectangle
	 */
	public ItemRect(Graphics2D g2d, Color color, float width, double x1, double y1, double x2, double y2) {
		super(g2d, color, width, x1, y1, x2, y2);
		// TODO Auto-generated constructor stub
		rect = new Rectangle2D.Double(x1, y1, x2, y2);
		t_boundRect = rect.getBounds2D();
	}

	/* (non-Javadoc)
	 * @see Items#drawItems()
	 */
	@Override
	public void drawItems() {
		// TODO Auto-generated method stub
		t_g2d.draw(rect);
	}

	/* (non-Javadoc)
	 * @see Items#resize(java.awt.geom.Point2D, java.awt.geom.Point2D, boolean)
	 */
	@Override
	public void resize(Point2D p1, Point2D p2, boolean center) {
		// TODO Auto-generated method stub
		rect.setFrameFromDiagonal(p1, p2);
		t_boundRect = rect.getBounds2D();
	}

	/* (non-Javadoc)
	 * @see Items#move(java.awt.geom.Point2D)
	 */
	@Override
	public void move(Point2D endPoint) {
		// TODO Auto-generated method stub
		double moveX = endPoint.getX() - t_boundRect.getCenterX();
		double moveY = endPoint.getY() - t_boundRect.getCenterY();
		rect.setFrame(rect.getX() + moveX, rect.getY() + moveY, rect.getWidth(), rect.getHeight());
		t_boundRect = rect.getBounds2D();
	}

	/* (non-Javadoc)
	 * @see Items#getInfo()
	 */
	@Override
	public String getInfo() {
		// TODO Auto-generated method stub
		return "TYPE_RECT," + t_color.getRGB() + "," + t_stockWidth + "," + (int)rect.getX() + "," + (int)rect.getY() + "," + (int)rect.getWidth() + "," + (int)rect.getHeight() + "\n";
	}

}
