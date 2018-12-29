import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.RoundRectangle2D;
/**
 * Description: the derived class for roundrect angle
 * Extends: items 
 * @author Veronica Tjan
 */
public class ItemRoundRect extends Items{
	private RoundRectangle2D roundrect;	//private item roundrectangle
	
	/**
	 * Description	: the constructor of roundrectangle
	 * @param g2d  	: the graphics2D object for showing
	 * @param color	: color of ellipse
	 * @param width	: display width of the item
	 * @param x1	: x of the upper left corner of the outside roundrectangle
	 * @param y1	: y of the upper left corner of the outside roundrectangle
	 * @param x2	: width of the outside roundrectangle
	 * @param y2	: height of the outside roundrectangle
	 */
	public ItemRoundRect(Graphics2D g2d, Color color, float width, double x1, double y1, double x2, double y2) {
		super(g2d, color, width, x1, y1, x2, y2);
		// TODO Auto-generated constructor stub
		roundrect = new RoundRectangle2D.Double(x1, y1, x2, y2, 50.0, 50.0);
		t_boundRect = roundrect.getBounds2D();
	}

	/* (non-Javadoc)
	 * @see Items#drawItems()
	 */
	@Override
	public void drawItems() {
		// TODO Auto-generated method stub
		t_g2d.draw(roundrect);
	}

	/* (non-Javadoc)
	 * @see Items#resize(java.awt.geom.Point2D, java.awt.geom.Point2D, boolean)
	 */
	@Override
	public void resize(Point2D p1, Point2D p2, boolean center) {
		// TODO Auto-generated method stub
		roundrect.setFrameFromDiagonal(p1, p2);
		t_boundRect = roundrect.getBounds2D();
	}

	/* (non-Javadoc)
	 * @see Items#move(java.awt.geom.Point2D)
	 */
	@Override
	public void move(Point2D endPoint) {
		// TODO Auto-generated method stub
		double moveX = endPoint.getX() - t_boundRect.getCenterX();
		double moveY = endPoint.getY() - t_boundRect.getCenterY();
		roundrect.setFrame(roundrect.getX() + moveX, roundrect.getY() + moveY, roundrect.getWidth(), roundrect.getHeight());
		t_boundRect = roundrect.getBounds2D();
	}

	/* (non-Javadoc)
	 * @see Items#getInfo()
	 */
	@Override
	public String getInfo() {
		// TODO Auto-generated method stub
		return "TYPE_RECT," + t_color.getRGB() + "," + t_stockWidth + "," + (int)roundrect.getX() + "," + (int)roundrect.getY() + "," + (int)roundrect.getWidth() + "," + (int)roundrect.getHeight() + "\n";
	}

}
