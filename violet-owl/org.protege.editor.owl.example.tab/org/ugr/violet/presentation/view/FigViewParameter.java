/**
 * 
 */
package org.ugr.violet.presentation.view;

import java.awt.Color;

/**
 * @author anab
 *
 */
public class FigViewParameter {
	
	
	public static final int CIRCLE = 0;
	public static final int RECTANGLE = 1;
	public static final int RRECTANGLE = 2;
	public static final int DIAMOND = 3;
	public static final int ARROW = 4;
	public static final int DARROW = 5;
	public static final int LINE = 6;

	protected int shape;
	protected Color color;
	protected boolean line;
	protected int linewidth;
	protected Color linecolor;
	protected String owlentity;
	protected String owlentityType;
	
	public FigViewParameter(){
		super();
	}
	
	public boolean setColor(String color2){
		
		color = Color.white;
		
		return true;
	}
	
	public boolean setShape(String shape2){
		
		if (shape2.toLowerCase().equals("circle")){
			shape = CIRCLE;
		}
		else if (shape2.toLowerCase().equals("rectangle")){
			shape = RECTANGLE;
		} else if (shape2.toLowerCase().equals("rrectangle")){
			shape = RRECTANGLE;
		} else if (shape2.toLowerCase().equals("diamond")){
			shape = DIAMOND;
		} else if (shape2.toLowerCase().equals("arrow")){
			shape = ARROW;
		} else if (shape2.toLowerCase().equals("darrow")){
			shape = DARROW;
		} else if (shape2.toLowerCase().equals("line")){
			shape = LINE;
		}
		else
			return false;
		
		return true;
	}
	
	public boolean setLine(String lin){
		
		if (lin.equals("true") ){
			line = true;
		}
		else
			line = false;
		
		return true;
	}
	
	public boolean setLineColor(String color2){
		
		color = Color.black;
		
		return true;
	}
	
	public void setLineWidth(String width){
		linewidth = Integer.parseInt(width);
	}
	
	public void setOWLEntity(String entity){
		owlentity = entity;
	}
	
	public void setOWLEntityType(String type){
		owlentityType = type;
	}
	
	public String getOWLEntityName(){
		return owlentity;
	}
	
	// PRESENTATION *************************************************************
	
	public String shape2string(){
		
		switch(shape){
		case CIRCLE:
			return "Circle";
		case RECTANGLE:
			return "Rectangle";
		case RRECTANGLE:
			return "Rounded Rectangle";
		case DIAMOND:
			return "Diamond";
		case ARROW:
			return "Arrow";
		case DARROW: 
			return "Doble Arrow";
		case LINE: 
			return "Line";
		default:
			return "ERROR";
		}
	}
	
	@Override
	public String toString(){
		return owlentityType+ ":"+ owlentity +"[shape=" + shape2string() + ", color="+ color +"," +
				"line=" + line +", line width="+linewidth+", linecolor=" + linecolor + "]";
	}
}
