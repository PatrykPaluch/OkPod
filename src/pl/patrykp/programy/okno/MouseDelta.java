package pl.patrykp.programy.okno;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseEvent;

public class MouseDelta {
	
	private int x, y, dx, dy;
	
	public MouseDelta(){}
	
	public void click(MouseEvent e){
		click(e.getX(), e.getY());
	}
	
	/**
	 * @param x - X position on Frame
	 * @param y - Y position on Frame
	 */
	public void click(int x, int y){
		this.x = x; this.y= y;
	}
	
	/**
	 * @param x - X position on Screen
	 * @param y - Y position on Screen
	 */
	public void updateMove(int x, int y){
		this.dx = x-this.x;
		this.dy = y-this.y;
	}
	
	public void updateMove(MouseEvent e){
		updateMove(e.getXOnScreen(), e.getYOnScreen());
	}
	
	public void reset(){
		x=0; y=0;
		dx=0; dy=0;
	}
	
	public void setPosOnScrean(Component com){
		com.setLocation(dx, dy);
	}
	
	public int getX(){ return dx; }
	public int getY(){ return dy; }
	
	public Dimension get(){ return new Dimension(dx, dy); }
	
}
