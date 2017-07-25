package pl.patrykp.programy.okno.utils;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Utils {
	
	public static final String defaultAssetsFolder = "res";
	
	private static String assetsFolder(String f){
		return defaultAssetsFolder + File.separatorChar + f;
	}
	
	public static final Image defaultTexture;
	static {
		defaultTexture = initStaticFinalImageIconVariable();
	}
	private static Image initStaticFinalImageIconVariable(){
		Image ic = Utils.loadBufferedImageInAssets("icon.png"); 
		if(ic==null){
			BufferedImage bi = new BufferedImage(16, 16, BufferedImage.TYPE_4BYTE_ABGR);
			bi.getGraphics().fillOval(0, 0, 15, 15);
			return bi;
		}
		return ic;
	}
	
	
	public static ImageIcon loadImageIcon(String file){
		return loadImageIcon(new File(file));
	}
	public static ImageIcon loadImageIconInAssets(String file){
		return loadImageIcon(new File(assetsFolder(file)));
	}
	public static ImageIcon loadImageIcon(File file){
		try {
			return new ImageIcon(ImageIO.read(file));
		} catch (IOException er) {
			return  null;
		}
	}
	
	public static ImageIcon loadImageIcon(String file, int w, int h){
		return loadImageIcon(new File(file), w, h);
	}
	public static ImageIcon loadImageIconInAssets(String file, int w, int h){
		return loadImageIcon(new File(assetsFolder(file)), w, h);
	}
	public static ImageIcon loadImageIcon(File file, int w, int h){
		try{
			return new ImageIcon( ImageIO.read(file).getScaledInstance(w, h, BufferedImage.SCALE_SMOOTH) );
		}catch(IOException er){
			return null;
		}
	}
	
	public static Image loadBufferedImage(String file){
		return loadBufferedImage(new File(file));
	}
	public static Image loadBufferedImageInAssets(String file){
		return loadBufferedImage(new File(assetsFolder(file)));
	}
	public static Image loadBufferedImage(File file){
		try{
			return ImageIO.read(file);
		}catch(IOException er){
			return null;
		}
	}
	
	public static Image loadBufferedImage(String file, int w, int h){
		return loadBufferedImage(new File(file), w, h);
	}
	public static Image loadBufferedImageInAssets(String file, int w, int h){
		return loadBufferedImage(new File(assetsFolder(file)), w, h);
	}
	public static Image loadBufferedImage(File file, int w, int h){
		try{
			return ImageIO.read(file).getScaledInstance(w, h, Image.SCALE_SMOOTH);
		}catch(IOException er){
			return null;
		}
	}
	
}
