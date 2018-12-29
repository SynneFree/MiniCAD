/**
 * Description:
 * This is a homework for JAVA class. You could create 
 * line, circle, rectangle or text inside this MiniCAD.
 * You can store items and reload it later, this class
 *  is an entry to total program.
 * @author Veronica Tjan
 */
public class MiniCAD {
	
	public static void main(String[] args) {
		//initialize the event listener, the target controller and mainframe
		myItemController tarController = new myItemController();
		myActionListener actListener =  new myActionListener(tarController);
		myMainFrame mainFrame = new myMainFrame(actListener,tarController);
		actListener.setMainFrame(mainFrame);
		tarController.setMainFrame(mainFrame);
		return;
	}

}
