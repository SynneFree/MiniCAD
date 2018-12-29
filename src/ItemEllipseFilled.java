import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

class ItemEllipseFilled extends Items{
private Ellipse2D ellipsefilled;

/**
 * Description	: the constructor of EllipseFilled
 * @param g2d  	: the graphics2D object for showing
 * @param color	: color of ellipse
 * @param width	: display width of the item
 * @param x1	: x of the upper left corner of the outside rectangle
 * @param y1	: y of the upper left corner of the outside rectangle
 * @param x2	: width of the outside rectangle
 * @param y2	: height of the outside rectangle
 */

 	public ItemEllipseFilled(Graphics2D g2d, Color color, float width, double x1, double y1, double x2, double y2) {
		super(g2d, color, width, x1, y1, x2, y2);
		//recalculate radius
		double d = Math.min(x2, y2);
		ellipsefilled = new Ellipse2D.Double(x1, y1, d, d);
		t_boundRect = ellipsefilled.getBounds2D();
	}

 	@Override
	public void move(Point2D endPoint) {
		// TODO Auto-generated method stub
		double moveX = endPoint.getX() - t_boundRect.getCenterX();
		double moveY = endPoint.getY() - t_boundRect.getCenterY();
		ellipsefilled.setFrame(ellipsefilled.getX() + moveX, ellipsefilled.getY() + moveY, ellipsefilled.getWidth(), ellipsefilled.getHeight());
		t_boundRect = ellipsefilled.getBounds2D();
	}

 	
	  @Override
	  public String getInfo() {
			// TODO Auto-generated method stub
			return "TYPE_ELLIPSEFILLED," + t_color.getRGB() + "," + t_stockWidth + "," + (int)ellipsefilled.getX() +"," + (int)ellipsefilled.getY() + "," + (int)ellipsefilled.getWidth() + "," + (int)ellipsefilled.getHeight() + "\n";
	  }
	  
	  	@Override
	  	public void resize(Point2D p1, Point2D p2, boolean center) {
			// TODO Auto-generated method stub
			double d = Math.min(Math.abs(p2.getX() - p1.getX()), Math.abs(p2.getY() - p1.getY()));
			t_position = getPressedPosition(p2);
			if(t_position == POS_LEFT_TOP)
				ellipsefilled.setFrameFromDiagonal(p1, new Point2D.Double(p1.getX() - d, p1.getY()-d));
			else if(t_position == POS_LEFT_BOTTOM)
				ellipsefilled.setFrameFromDiagonal(p1, new Point2D.Double(p1.getX() - d, p1.getY()+d));
			else if(t_position == POS_RIGHT_TOP)
				ellipsefilled.setFrameFromDiagonal(p1, new Point2D.Double(p1.getX() + d, p1.getY()-d));
			else if(t_position == POS_RIGHT_BOTTOM)
				ellipsefilled.setFrameFromDiagonal(p1, new Point2D.Double(p1.getX() + d, p1.getY()+d));
			t_boundRect = ellipsefilled.getBounds2D();
		}

  
  @Override
	public void drawItems() {
		// TODO Auto-generated method stub
	 t_g2d.fill(new java.awt.geom.Ellipse2D.Float((int)ellipsefilled.getX(), (int)ellipsefilled.getY() , (int)ellipsefilled.getWidth() , (int)ellipsefilled.getHeight() ));
//	 t_g2d.draw(ellipsefilled);
	}
}