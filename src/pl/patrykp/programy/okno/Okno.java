// 100 lat Rati :P
package pl.patrykp.programy.okno;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Stroke;
import java.awt.Window;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.text.NumberFormat;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;

import pl.patrykp.programy.okno.utils.Utils;
import pl.patrykp.programy.okno.utils.XYMenuAcceptEvent;
import pl.patrykp.programy.okno.utils.XYMenuAcceptListener;

public class Okno implements MouseMotionListener, MouseListener {
	
	private Image aktImage;//TODO dodac obsloge gifow
	//TODO zwiekszanie okna z oryginalu obrazu
	private JPopupMenu popupMenu;
	private JFrame o;
	private MouseDelta md = new MouseDelta();
	private MouseDelta resMd = new MouseDelta();
	private JLabel tlo;
	private boolean resizeMode = false;
	private int resizeModeBorderSize = 30;//%
	
	public JFrame getJFrame(){ return o; }
	
	public Okno(){
		
		initAppMenu();
		
		o = new JFrame() {
			private static final long serialVersionUID = 1L;

			@Override
			public void paint(Graphics g) {
				super.paint(g);
				paintOnJFame(g);
			}
		};
		o.setType(Window.Type.UTILITY);
		o.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				stop();
			}
		});
		o.setUndecorated(true);
		o.setAlwaysOnTop(true);
		o.setSize(100, 100);
		o.setName("Okno");
		o.setBackground(new Color(0,0,0,0));
		o.getContentPane().setBackground(new Color(0,0,0,0));
		o.setVisible(true);
		
		
		o.getContentPane().addMouseMotionListener(this);
		o.getContentPane().addMouseListener(this);
		
		aktImage = Utils.defaultTexture.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
		tlo = new JLabel(new ImageIcon(aktImage));
		
		o.getContentPane().add(tlo, BorderLayout.CENTER);
		
		
		o.pack();
		o.setVisible(true);
	}
	
	public void paintOnJFame(Graphics gD) {
		if(resizeMode) {
			Graphics2D g = (Graphics2D) gD;
			Stroke old = g.getStroke();
			g.setStroke(new BasicStroke(resizeModeBorderSize));
			g.setColor(Color.WHITE);
			g.drawRect(0, 0, o.getContentPane().getWidth(), o.getContentPane().getHeight());
			g.setStroke(new BasicStroke(resizeModeBorderSize/2));
			g.setColor(Color.BLACK);
			g.drawRect(0, 0, o.getContentPane().getWidth(), o.getContentPane().getHeight());
			g.setStroke(old);
		}
	}
	
	
	private void initAppMenu(){
		JPopupMenu menu = new JPopupMenu();
		//o.add(menu);
		//Buttony
		JMenuItem open = new JMenuItem("Otworz");
		JMenuItem resize = new JMenuItem("Zmien rozmiar");
		JMenuItem resizeVal = new JMenuItem("ustaw rozmiar");
		JMenuItem setPos = new JMenuItem("Ustaw pozycje");
//		JMenuItem openNext = new JMenuItem("Dodaj slajd");
//		JMenuItem prevSlide = new JMenuItem("Poprzedni slajd");
//		JMenuItem nextSlide = new JMenuItem("Nastepny slajd");
		JMenuItem close = new JMenuItem("Zamknij to okno");
		
		//Dodawanie do menu
		menu.add(open);
		menu.addSeparator();
		menu.add(resize);
		menu.add(resizeVal);
		menu.add(setPos);
//		menu.add(openNext);
//		menu.addSeparator();
//		menu.add(prevSlide);
//		menu.add(nextSlide);
		menu.addSeparator();
		menu.add(close);
		
		//Eventy
		close.addActionListener((e)->stop());
		
		
		resizeVal.addActionListener( (e)->showXYMenu(
				(int)o.getSize().getWidth(), (int)o.getSize().getHeight(),
				(ev)->resize( ev.getOutX(), ev.getOutY() )
				) );
		
		setPos.addActionListener((e)->showXYMenu(
				o.getLocation().x, o.getLocation().y, 
				(ev)->o.setLocation( ev.getOutX(), ev.getOutY() )
				) );
		
		open.addActionListener( (e)->showOpenFileDialog() );
		
		resize.addActionListener( (e)-> setResizeMode(true) ); 
		//TODO eventy
		
		
		//koncowe ustawienie
		this.popupMenu = menu;
	}
	
	/**
	 * @param f - first window variable (x)
	 * @param s - second window variable (y)
	 * @param okButtonPressAction - on accept button press event
	 */
	void showXYMenu(int f, int s, XYMenuAcceptListener okButtonPressAction){
		JDialog d = new JDialog();
		d.getContentPane().setLayout(new GridLayout(3, 2));
		JLabel xL = new JLabel("X:");
		JLabel yL = new JLabel("Y:");
		NumberFormat nb =NumberFormat.getIntegerInstance();
		nb.setMaximumIntegerDigits(4);
		nb.setGroupingUsed(false);
		nb.setMinimumIntegerDigits(1);
		
		JFormattedTextField x = new JFormattedTextField(nb);
		JFormattedTextField y = new JFormattedTextField(nb);
//		JTextField x = new JTextField(o.getSize().width);
//		JTextField y = new JTextField(o.getSize().height);
		JButton okB = new JButton("Ok");
		JButton cancelB = new JButton("Anuluj");
	
		
		x.setValue(f);
		y.setValue(s);
		
		x.setColumns(4);
		y.setColumns(4);
//		KeyListener numberFieldKeyListener = new KeyAdapter() {
//			@Override
//			public void keyTyped(KeyEvent e) {
//				char c = e.getKeyChar();
//				if ( Character.isDigit(c) || c==KeyEvent.VK_SPACE || c==KeyEvent.VK_BACK_SPACE || c==KeyEvent.VK_DELETE ){
//					e.consume();
//				}
//			}
//		};
//		
//		x.addKeyListener(numberFieldKeyListener);
//		y.addKeyListener(numberFieldKeyListener);
//		
		d.getContentPane().add(xL);
		d.getContentPane().add(x);
		d.getContentPane().add(yL);
		d.getContentPane().add(y);
		d.getContentPane().add(cancelB);
		d.getContentPane().add(okB);
		d.setSize(150, 100);
		d.setLocationRelativeTo(null);
		d.setVisible(true);
		cancelB.addActionListener((e)->{d.dispose();}  );
		okB.addActionListener((e)->{ okButtonPressAction.actionPerformed(new XYMenuAcceptEvent( okB, Integer.valueOf(x.getText()), Integer.valueOf(y.getText()) ));});
	}
	
	void showOpenFileDialog(){
		//TODO: dodac menu wyboru pliku i ustawien okna, np.: czy zmienic rozmiar do wielkosc, skala wielkosci okna, proporcje, zahowanie proporcji obrazu, itp...
		JFileChooser jfc = new JFileChooser(); //TODO: zapamietywanie ostatniej scierzki np. w temp, albo chuj wie gdzie
		//TODO: Ustawienia typow plikow dla file choosera
		int odp = jfc.showOpenDialog(this.o);
		if (odp==JFileChooser.APPROVE_OPTION){
			File file = jfc.getSelectedFile();
			aktImage = Utils.loadBufferedImage(file, this.o.getContentPane().getWidth(), this.o.getContentPane().getHeight());
			tlo.setIcon(new ImageIcon(aktImage));	
		}
	}
	
	public void resize(int x, int y){
		o.setSize(x,y);
		aktImage = aktImage.getScaledInstance(x, y, Image.SCALE_SMOOTH);
		tlo.setIcon(new ImageIcon(aktImage));	
	}
	
	public void stop(){
		AppManager.getInstance().destroyWindow(this);
	}
	
	void _destroy(){
		if(o==null) return;
		o.dispose();
		o = null;
		md = null;
		tlo = null;
		popupMenu = null;
	}
	
	public void start(){
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if(resizeMode && SwingUtilities.isLeftMouseButton(e)) {
			//TODO wydzielic do klasy
			/*
			
			if(e.getX() > o.getContentPane().getWidth()-resizeModeBorderSize) {
				resMd.updateMoveOnScreen(e);
				o.setSize(o.getSize().width+resMd.getX(), o.getSize().height);
				System.out.println(resMd.getX());
			}
			else if(e.getX()<resizeModeBorderSize) {
				
			}
			return;*/
		}
		if(SwingUtilities.isLeftMouseButton(e)){
			md.updateMoveOnScreen(e);
			md.setPosOnScrean(o);
		}
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(SwingUtilities.isRightMouseButton(e)) {
			if (resizeMode) {
				setResizeMode(false);
				return;
			}
			popupMenu.show((Component)e.getSource(), e.getX(), e.getY());
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(SwingUtilities.isLeftMouseButton(e)) {
			md.click(e);
			//TODO klasa do tego
			//if(resizeMode) resMd.click(e);
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		md.reset();
		//resMd.reset();
	}
	
	@Override
	public boolean equals(Object obj) {
		return this == obj;
	}
	
	
	public void setResizeMode(boolean b) {
		if(this.resizeMode == b) return;
		this.resizeMode = b;
		o.repaint();
	}
	public boolean getResizeMode() {
		return this.resizeMode;
	}
	public void setResizeModeBorderSize(int size) {
		if(size<10 || size>Math.min(o.getContentPane().getWidth(), o.getContentPane().getHeight())/2-2 ) throw new IllegalArgumentException("Size must be >10 and <window size/2-2");
		this.resizeModeBorderSize = size;
		if(this.resizeMode) o.repaint();
	}
	public int getResizeModeBorderSize() {
		return this.resizeModeBorderSize;
	}
}
