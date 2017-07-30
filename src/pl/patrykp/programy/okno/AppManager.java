package pl.patrykp.programy.okno;

import java.awt.AWTException;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import pl.patrykp.programy.okno.utils.Utils;

public class AppManager {

	private static final AppManager instance; 
	
	private ArrayList<Okno> okna = new ArrayList<Okno>();
	private boolean started = false;
	
	private TrayIcon tIc;
	
	static {
		instance = new AppManager();
	}
	
	private AppManager(){}
	
	public void start() {
		if(started) return;
		started = true;
		init();
	}
	
	private void init(){
		initTray();
	}
	
	private void initNoTray(){
		//TODO: zrobic init dla systemow nie wspierajacych try
		JOptionPane.showMessageDialog(null, "System nie wspiera Tray.", "Blad Tray", JOptionPane.ERROR_MESSAGE );
		System.exit(-1);
	}
	
	private void initTray(){
		if(!SystemTray.isSupported()){
			System.out.println("System nie wspiera tray");
			System.out.println("Laduje ustawienia bez systemowego tray");
			initNoTray();
			return;
		}
		SystemTray tra = SystemTray.getSystemTray();
		
		
		tIc =  new TrayIcon(Utils.defaultTexture);
		int width = tIc.getSize().width;
		if(Utils.defaultTexture.getWidth(null)!=width || Utils.defaultTexture.getHeight(null) != width){
			tIc = new TrayIcon(Utils.defaultTexture.getScaledInstance(width, width, BufferedImage.SCALE_SMOOTH));
		}
		
		try{
			tra.add(tIc);
		}catch(AWTException er){
			System.err.println("Blad dodawani trayIcon");
			er.printStackTrace();
			System.out.println("Laduje ustawienia bez systemowego tray");
			initNoTray();
			return;
		}
		//Menu
		PopupMenu menu = new PopupMenu();
		
		//Buttony
		MenuItem addNew = new MenuItem("Nowe okno");
		MenuItem showAll = new MenuItem("Poka¿ wszystkie");
		MenuItem closeAll = new MenuItem("Zamknij wszystkie okna");
		MenuItem wyjscie = new MenuItem("Zamknij program");
		
		//Dodawanie do menu
		menu.add(addNew);
		menu.add(showAll);
		menu.addSeparator();
		menu.add(closeAll);
		menu.addSeparator();
		menu.add(wyjscie);
		
		//Eventy
		addNew.addActionListener((e)->createWindow() );
		showAll.addActionListener((e)->{for(Okno o : okna){o.getJFrame().requestFocus();} } );
		closeAll.addActionListener((e)->destroyAllWindows() );
		wyjscie.addActionListener((e)->zabij(0));
		//Ustawienie menusa
		tIc.setPopupMenu(menu);
		tIc.setToolTip("Okno ");
	}
	
	public void zabij(int err){
		destroyAllWindows();
		SystemTray.getSystemTray().remove(tIc);
		System.exit(err);
	}
	
	public void destroyAllWindows(){
		Okno[] oknaToDestroy = okna.toArray(new Okno[okna.size()]);
		for(Okno o : oknaToDestroy) destroyWindow(o);
		oknaToDestroy = null;
	}
	
	public void destroyWindow(Okno ok){
		ok._destroy();
		okna.remove(ok);
	}
	
	public Okno createWindow(){
		Okno ok = new Okno();
		ok.start();
		okna.add(ok);
		return ok;
	}
	
	public static AppManager getInstance(){
		return instance;
	}
	public boolean started() {
		return started;
	}
	
	
}
