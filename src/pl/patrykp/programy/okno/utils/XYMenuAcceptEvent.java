package pl.patrykp.programy.okno.utils;

import java.awt.event.ActionEvent;

public class XYMenuAcceptEvent{
	private Object source;
	private int outX;
	private int outY;
	
	public XYMenuAcceptEvent(Object source, int x, int y){
		this.source = source;
		this.outX = x;
		this.outY = y;
	}
	
	
	public Object getSource(){
		return source;
	}
	public int getOutX() {
		return outX;
	}
	public int getOutY() {
		return outY;
	}
	
	public void setOutX(int outX) {
		this.outX = outX;
	}
	public void setOutY(int outY) {
		this.outY = outY;
	}
	public void setSource(Object source) {
		this.source = source;
	}
}
