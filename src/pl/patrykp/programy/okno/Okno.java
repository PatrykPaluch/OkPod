package pl.patrykp.programy.okno;

import java.awt.AWTError;
import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.Window;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;

public class Okno implements MouseMotionListener, MouseListener {

	private JPopupMenu popupMenu;
	private JFrame o;
	private MouseDelta md = new MouseDelta();
	private JLabel tlo;
	
	public Okno(){
		
		initAppMenu();
		
		o = new JFrame();
		o.setType(Window.Type.POPUP);
		o.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				AppManager.getInstance().zabij(0);
			}
		});
		o.setSize(100, 100);
		//o.setUndecorated(true);
		o.setAlwaysOnTop(true);
		o.setName("Okno");
		o.getContentPane().setBackground(new Color(0,0,0,0));
		o.setVisible(true);
		


		o.getContentPane().addMouseMotionListener(this);
		o.getContentPane().addMouseListener(this);
		
		tlo = new JLabel();
		o.getContentPane().add(tlo, BorderLayout.CENTER);
		
		
		o.pack();
		o.setVisible(true);
	}
	
	
	
	
	private void initAppMenu(){
		JPopupMenu menu = new JPopupMenu();
		//o.add(menu);
		//Buttony
		JMenuItem open = new JMenuItem("Otworz");
		JMenuItem openNext = new JMenuItem("Dodaj slajd");
		JMenuItem prevSlide = new JMenuItem("Poprzedni slajd");
		JMenuItem nextSlide = new JMenuItem("Nastepny slajd");
		JMenuItem close = new JMenuItem("Zamknij to okno");
		JMenuItem exit = new JMenuItem("Wyjscie");
		
		//Dodawanie do menu
		menu.add(open);
		menu.add(openNext);
		menu.addSeparator();
		menu.add(prevSlide);
		menu.add(nextSlide);
		menu.addSeparator();
		menu.add(close);
		menu.addSeparator();
		menu.add(exit);
		
		//Eventy
		
		//TODO eventy
		
		//koncowe ustawienie
		this.popupMenu = menu;
	}
	
	
	public void start(){
		try{
			BufferedImage icon = ImageIO.read(new File("plik.jpg"));
			//TODO: usunac, tylko test
			tlo.setHorizontalAlignment(JLabel.CENTER);
			tlo.setVerticalAlignment(JLabel.CENTER);
			
			Image img = icon.getScaledInstance(tlo.getWidth(), tlo.getHeight(), BufferedImage.SCALE_SMOOTH);
			tlo.setIcon(new ImageIcon(img));
		}catch (IOException er) {
			
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if(SwingUtilities.isLeftMouseButton(e)){
			md.updateMove(e);
			md.setPosOnScrean(o);//TODO dziwne przesuniecie
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(SwingUtilities.isRightMouseButton(e)) {
			popupMenu.show((Component)e.getSource(), e.getX(), e.getY());
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(SwingUtilities.isLeftMouseButton(e)) md.click(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}
	
	
}
