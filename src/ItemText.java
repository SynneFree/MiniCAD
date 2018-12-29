import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JOptionPane;

/**
 * Description: the derived class circle
 * Extends: items 
 * @author Veronica Tjan
 */
public class ItemText extends Items {
	private String text;	//content of the text
	private Font t_font;	//font of the text
	private FontMetrics t_fontMetrics;	//font metrics of the text
	
	/**
	 * Description	: the constructor of text
	 * @param g2d  	: the graphics2D object for showing
	 * @param color	: color of ellipse
	 * @param width	: display width of the item
	 * @param x1	: x of the upper left corner of the outside rectangle
	 * @param y1	: y of the upper left corner of the outside rectangle
	 * @param x2	: width of the outside rectangle
	 * @param y2	: height of the outside rectangle
	 */
	public ItemText(Graphics2D g2d, String t, Color color, float width, double x1, double y1, double x2, double y2) {
		super(g2d, color, width, x1, y1, x2, y2);
		// TODO Auto-generated constructor stub
		t_font = new Font("Serif", Font.PLAIN, 20);
		if(t == null)
			text = JOptionPane.showInputDialog("Please input the text:");
		else
			text = t;
		t_fontMetrics = g2d.getFontMetrics(t_font);
		t_boundRect = new Rectangle2D.Double(x1, y1, t_fontMetrics.stringWidth(text),t_fontMetrics.getAscent());
		this.resize(new Point2D.Double(x1, y1), new Point2D.Double(x1 + x2, y1 + y2), false);
	}
	
	/* (non-Javadoc)
	 * @see Items#drawItems()
	 */
	@Override
	public void drawItems() {
		// TODO Auto-generated method stub
		t_g2d.setFont(t_font);
		t_fontMetrics = t_g2d.getFontMetrics(t_font);
		t_g2d.drawString(text, (int)(t_boundRect.getX()), (int)(t_boundRect.getY() + t_boundRect.getHeight()));
	}

	/* (non-Javadoc)
	 * @see Items#resize(java.awt.geom.Point2D, java.awt.geom.Point2D, boolean)
	 */
	@Override
	public void resize(Point2D p1, Point2D p2, boolean center) {
		// TODO Auto-generated method stub
		t_boundRect.setFrameFromDiagonal(p1, p2);
		t_font = t_font.deriveFont(1);
		t_fontMetrics = t_g2d.getFontMetrics(t_font);
		while(true){
			if(t_fontMetrics.stringWidth(text) <= t_boundRect.getWidth() && t_fontMetrics.getAscent() <= t_boundRect.getHeight()){
				t_font = t_font.deriveFont(t_font.getSize2D() + 1);
				t_fontMetrics = t_g2d.getFontMetrics(t_font);
			}
			else {
				t_font = t_font.deriveFont(t_font.getSize2D() - 1);
				t_fontMetrics = t_g2d.getFontMetrics(t_font);
				break;
			}
		}
	}

	/* (non-Javadoc)
	 * @see Items#move(java.awt.geom.Point2D)
	 */
	@Override
	public void move(Point2D endPoint) {
		// TODO Auto-generated method stub
		double moveX = endPoint.getX() - t_boundRect.getCenterX();
		double moveY = endPoint.getY() - t_boundRect.getCenterY();
		t_boundRect.setFrame(t_boundRect.getX() + moveX, t_boundRect.getY() + moveY,t_boundRect.getWidth(), t_boundRect.getHeight());
	}

	/* (non-Javadoc)
	 * @see Items#getInfo()
	 */
	@Override
	public String getInfo() {
		// TODO Auto-generated method stub
		return "TYPE_TEXT," + t_color.getRGB() +"," + t_stockWidth + "," + text + "," + (int)t_boundRect.getX() + "," + (int)t_boundRect.getY() + "," + (int)t_boundRect.getWidth() + "," + (int)t_boundRect.getHeight() + "\n";

	}
	

}
