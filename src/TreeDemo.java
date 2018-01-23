import javax.imageio.metadata.IIOMetadataNode;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.UIManager;
 
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeSelectionModel;
import javax.swing.ImageIcon;

import org.w3c.dom.Node;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
 
import java.net.URL;
import java.io.IOException;
import java.awt.Dimension;
import java.awt.GridLayout;
 
public class TreeDemo extends JPanel
                      implements TreeSelectionListener {
    private JEditorPane htmlPane;
    private JTree tree;
    private URL helpURL;
    private static boolean DEBUG = false;
 
    //Optionally play with line styles.  Possible values are
    //"Angled" (the default), "Horizontal", and "None".
    private static boolean playWithLineStyle = false;
    private static String lineStyle = "None";
     
    //Optionally set the look and feel.
    private static boolean useSystemLookAndFeel = false;
 
    public TreeDemo(Node n) {///////////////////////////////////////////////////////////////
        super(new GridLayout(1,0));
 
        //Create the nodes.
        DefaultMutableTreeNode top=createNodes(n);
 
        //Create a tree that allows one selection at a time.
        tree = new JTree(top);
        tree.getSelectionModel().setSelectionMode
                (TreeSelectionModel.SINGLE_TREE_SELECTION);
        ImageIcon leafIcon= new ImageIcon(TreeDemo.class.getResource("middle.gif"));
        DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
        renderer.setClosedIcon(leafIcon);
        renderer.setOpenIcon(leafIcon);
        tree.setCellRenderer(renderer);
 
        //Listen for when the selection changes.
        tree.addTreeSelectionListener(this);
 
        if (playWithLineStyle) {
            System.out.println("line style = " + lineStyle);
            
        }
        tree.putClientProperty("JTree.lineStyle", lineStyle);
 
        //Create the scroll pane and add the tree to it. 
        JScrollPane treeView = new JScrollPane(tree);
 
        //Create the HTML viewing pane.
        htmlPane = new JEditorPane();
        htmlPane.setEditable(false);
        JScrollPane htmlView = new JScrollPane(htmlPane);
 
        //Add the scroll panes to a split pane.
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPane.setTopComponent(treeView);
        splitPane.setBottomComponent(htmlView);
 
        Dimension minimumSize = new Dimension(100, 50);
        htmlView.setMinimumSize(minimumSize);
        treeView.setMinimumSize(minimumSize);
        splitPane.setDividerLocation(100); 
        splitPane.setPreferredSize(new Dimension(500, 300));
 
        //Add the split pane to this panel.
        add(splitPane);
    }
 
    
    
    
	public static String inorder(DefaultMutableTreeNode n) {
		String key =(String) n.getUserObject();
		if(!Homework1.isOperator(key.charAt(0))){
			
			return key;
		}
		String x = inorder(n.getNextNode());
		String y = inorder(n.getNextNode().getNextSibling());
		return "("+x+key+y+")";
	}

	
	public static int  calculate (DefaultMutableTreeNode n) {
		String key =(String) n.getUserObject(); 

		int result=0;
		if(n.isLeaf()) {
			return Integer.valueOf(key);
		}
		int num2 = calculate(n.getNextNode());
		int num1 = calculate(n.getNextNode().getNextSibling());
		switch (key) {
    	case ("+"):	result = num1+num2;
    				break;
		case ("-"):	result = num2-num1;
					break;
    	case ("*"):	result = num2*num1;
					break;
    	case ("/"):	result = num2/num1;
					break;
    	}	
		return result;
		
	}
	
	

 

 
    private DefaultMutableTreeNode createNodes(Node n) {
    	char key = n.getNodeName().charAt(0);
    	DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(n.getNodeName());
    	if(Homework1.isOperator(key)) {
    		DefaultMutableTreeNode newdata = createNodes(n.getChildNodes().item(1));
    		newNode.add(newdata);
    		DefaultMutableTreeNode newdata1 = createNodes(n.getChildNodes().item(0));
    		newNode.add(newdata1);
    		
    	}
    		return newNode;	
    	
    }
         
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event dispatch thread.
     */
    private static void createAndShowGUI(Node n) {
        if (useSystemLookAndFeel) {
            try {
                UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                System.err.println("Couldn't use system look and feel.");
            }
        }
 
        //Create and set up the window.
        JFrame frame = new JFrame("Binary Tree Calculator");//name win
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        //Add content to the window.
        frame.add(new TreeDemo(n));
 
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
 
    public static void main(Node n) {
        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI(n);
            }
        });
    }




	@Override
	public void valueChanged(TreeSelectionEvent e) {
		// TODO Auto-generated method stub
		DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                tree.getLastSelectedPathComponent();
		String dataOut = inorder(node);
		if(!node.isLeaf()) {
			dataOut=dataOut.substring(1, dataOut.length()-1)+"="+calculate(node);
		}
		htmlPane.setText(dataOut);
		
	}
}