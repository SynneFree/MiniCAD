import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

/**
 * Description: the derived class circle
 * Extends: items 
 * @author Ziheng Duan
 */
public class ItemEllipse extends Items{
	//private item ellipse
	private Ellipse2D ellipse;
	
	/**
	 * Description	: the constructor of Ellipse
	 * @param g2d  	: the graphics2D object for showing
	 * @param color	: color of ellipse
	 * @param width	: display width of the item
	 * @param x1	: x of the upper left corner of the outside rectangle
	 * @param y1	: y of the upper left corner of the outside rectangle
	 * @param x2	: width of the outside rectangle
	 * @param y2	: height of the outside rectangle
	 */
	public ItemEllipse(Graphics2D g2d, Color color, float width, double x1, double y1, double x2, double y2) {
		super(g2d, color, width, x1, y1, x2, y2);
		// TODO Auto-generated constructor stub
		//recalculate radius
		double d = Math.min(x2, y2);
		ellipse = new Ellipse2D.Double(x1, y1, d, d);
		t_boundRect = ellipse.getBounds2D();
	}

	/* (non-Javadoc)
	 * @see Items#drawItems()
	 */
	@Override
	public void drawItems() {
		// TODO Auto-generated method stub
		t_g2d.draw(ellipse);
	}

	/* (non-Javadoc)
	 * @see Items#move(java.awt.geom.Point2D)
	 */
	@Override
	public void move(Point2D endPoint) {
		// TODO Auto-generated method stub
		double moveX = endPoint.getX() - t_boundRect.getCenterX();
		double moveY = endPoint.getY() - t_boundRect.getCenterY();
		ellipse.setFrame(ellipse.getX() + moveX, ellipse.getY() + moveY, ellipse.getWidth(), ellipse.getHeight());
		t_boundRect = ellipse.getBounds2D();
	}

	/* (non-Javadoc)
	 * @see Items#getInfo()
	 */
	@Override
	public String getInfo() {
		// TODO Auto-generated method stub
		return "TYPE_ELLIPSE," + t_color.getRGB() + "," + t_stockWidth + "," + (int)ellipse.getX() +"," + (int)ellipse.getY() + "," + (int)ellipse.getWidth() + "," + (int)ellipse.getHeight() + "\n";
	}

	/* (non-Javadoc)
	 * @see Items#resize(java.awt.geom.Point2D, java.awt.geom.Point2D, boolean)
	 */
	@Override
	public void resize(Point2D p1, Point2D p2, boolean center) {
		// TODO Auto-generated method stub
		double d = Math.min(Math.abs(p2.getX() - p1.getX()), Math.abs(p2.getY() - p1.getY()));
		t_position = getPressedPosition(p2);
		if(t_position == POS_LEFT_TOP)
			ellipse.setFrameFromDiagonal(p1, new Point2D.Double(p1.getX() - d, p1.getY()-d));
		else if(t_position == POS_LEFT_BOTTOM)
			ellipse.setFrameFromDiagonal(p1, new Point2D.Double(p1.getX() - d, p1.getY()+d));
		else if(t_position == POS_RIGHT_TOP)
			ellipse.setFrameFromDiagonal(p1, new Point2D.Double(p1.getX() + d, p1.getY()-d));
		else if(t_position == POS_RIGHT_BOTTOM)
			ellipse.setFrameFromDiagonal(p1, new Point2D.Double(p1.getX() + d, p1.getY()+d));
		t_boundRect = ellipse.getBounds2D();
	}


}
