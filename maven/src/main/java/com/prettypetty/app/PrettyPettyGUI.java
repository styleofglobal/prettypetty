package com.prettypetty.app;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JDesktopPane;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

@SuppressWarnings("serial")
public class PrettyPettyGUI extends JFrame implements ActionListener {
	JDesktopPane desktop;
	public PrettyPettyGUI(){
		super("Pretty Petty");
		
		//Make the big window be indented 50 pixels from each edge
        //of the screen.
        int inset = 20;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(inset, inset, screenSize.width - 222, screenSize.height - 152);

        //Set up the GUI.
        desktop = new JDesktopPane(); //a specialized layered pane
        setContentPane(desktop);
        setJMenuBar(createMenuBar());
        //Make dragging a little faster but perhaps uglier.
        desktop.setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if ("Quit".equals(e.getActionCommand()))
        { 
        	//quit
            quit();
        }

	}
	protected void quit() 
    {
    	JOptionPane.showMessageDialog(null,"Pretty Petty!");
        System.exit(0);
        
    }
	protected JMenuBar createMenuBar() 
    {
        JMenuBar menuBar = new JMenuBar();

        //Set up the Game menu.
        JMenu menu = new JMenu("Open");
        menu.setMnemonic(KeyEvent.VK_G);
        menuBar.add(menu);
        
		//Set up the Help menu.
        JMenu menu1 = new JMenu("Help");
        menu.setMnemonic(KeyEvent.VK_H);
        menuBar.add(menu1);
        
        //Set up the second menu item for Game menu.
        JMenuItem menuItem = new JMenuItem("Quit");
        menuItem.setMnemonic(KeyEvent.VK_Q);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.ALT_MASK));
        menuItem.setActionCommand("Quit");
        menuItem.addActionListener(this);
        menu.add(menuItem);
		
        return menuBar;
    }
	
	@SuppressWarnings("unused")
	static void createAndShowGUI() 
    {
        //Make sure we have nice window decorations.
        JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);
        //Create and set up the window.
        PrettyPettyGUI frame = new PrettyPettyGUI();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Display the window.
        frame.setVisible(true);
        JOptionPane.showMessageDialog(null, "Welcome to Pretty Petty SME suite");
    }
}
