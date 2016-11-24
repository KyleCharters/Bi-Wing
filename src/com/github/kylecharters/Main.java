package com.github.kylecharters;

import com.github.kylecharters.Movement.Direction;

import lejos.nxt.Button;
import lejos.nxt.ButtonListener;
import lejos.nxt.ColorSensor;
import lejos.nxt.ColorSensor.Color;
import lejos.nxt.Motor;
import lejos.nxt.SensorConstants;
import lejos.nxt.SensorPort;
import lejos.nxt.Sound;

public class Main {
	
	public static final int DETECTION_COLOUR = Color.BLACK;
	
	private ColorSensor rightSensor, leftSensor;
	
	private Movement movement;
	
	public Main(){
		Sound.beep();
		rightSensor = new ColorSensor(SensorPort.S1, SensorConstants.WHITE);
		leftSensor = new ColorSensor(SensorPort.S4, SensorConstants.WHITE);
		
		Button.waitForAnyPress();
		
		for(int i = 0 ; i < 5 ; i++){
			if(i == 4){
				Sound.twoBeeps();
			}else{
				Sound.beep();
			}
			try{Thread.sleep(1000);}catch(Exception e){e.printStackTrace();}
		}
		
		Motor.C.rotate(-120);
		
		movement = new Movement(Direction.STRAIGHT, 1, Movement.MAX_SPEED, true);
		
		while(true){
			
			
			digestInputs();
		}
	}
	
	private void digestInputs(){
		boolean leftSensorDetect = leftSensor.getColorID() == DETECTION_COLOUR;
		boolean rightSensorDetect = rightSensor.getColorID() == DETECTION_COLOUR;
		
		if(leftSensorDetect || rightSensorDetect){
			movement.set(Direction.STRAIGHT, 0);
			movement.reverse();
			
			
			if(rightSensorDetect && leftSensorDetect){
				movement.set(Direction.LEFT, 0);
				
				try{Thread.sleep(1200);}catch(Exception e){e.printStackTrace();}
				movement.reverse();
				
				return;
			}else if(rightSensorDetect){
				movement.set(Direction.RIGHT, 0);
			}else if(leftSensorDetect){
				movement.set(Direction.LEFT, 0);
			}
			
			try{Thread.sleep(650);}catch(Exception e){e.printStackTrace();}
			movement.reverse();
			
			return;
		}
		movement.turnStraight(0.008f);
	}
	
	public static void main(String[] args){
		Button.ESCAPE.addButtonListener(new ButtonListener() {
			public void buttonReleased(Button b) {}
			public void buttonPressed(Button b) {
				Motor.C.rotate(120);
				System.exit(0);
			}
		});
		
		new Main();
	}
}
