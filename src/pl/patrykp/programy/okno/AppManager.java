package pl.patrykp.programy.okno;

import java.awt.AWTException;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class AppManager {

	private static final AppManager instance; 
	
	private ArrayList<Okno> okna = new ArrayList<Okno>();
	
	
	private TrayIcon tIc;
	
	static {
		instance = new AppManager();
		instance.init();
	}
	
	private AppManager(){}
	
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
		
		try {
			tIc = new TrayIcon(ImageIO.read(new File("plik.jpg")));
		}catch(IOException er){
			System.err.println("Blad ladowania trayIcon");
			er.printStackTrace();
			System.out.println("Laduje ustawienia bez systemowego tray");
			initNoTray();
			return;
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
		MenuItem wyjscie = new MenuItem("Zamknij");
		MenuItem addNew = new MenuItem("Nowe okno");
		
		//Dodawanie do menu
		menu.add(wyjscie);
		menu.add(addNew);
		
		//Eventy
		wyjscie.addActionListener((e)->zabij(0));
		addNew.addActionListener((e)->createWindow() );
		//Ustawienie menusa
		tIc.setPopupMenu(menu);
		tIc.setToolTip("Okno ");
	}
	
	public void zabij(int err){
		Okno[] oknaToDestroy = okna.toArray(new Okno[okna.size()]);
		for(Okno o : oknaToDestroy) destroyWindow(o);
		oknaToDestroy = null;
		
		SystemTray.getSystemTray().remove(tIc);
		System.exit(err);
	}
	
	
	public void destroyWindow(Okno ok){
		ok._destroy();
		okna.remove(ok);
	}
	
	public void createWindow(){
		Okno ok = new Okno();
		ok.start();
		okna.add(ok);
	}
	
	public static AppManager getInstance(){
		return instance;
	}
	
	
}
