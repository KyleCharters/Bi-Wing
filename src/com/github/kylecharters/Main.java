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

	public static final int DETECTION_COLOUR = Color.WHITE;
	public static final int DETECTION_COLOUR2 = Color.GREEN;
	
	private ColorSensor rightSensor, leftSensor;
	
	private Movement movement;
	
	public Main(){
		Sound.beep();
		
		rightSensor = new ColorSensor(SensorPort.S1, SensorConstants.WHITE);
		leftSensor = new ColorSensor(SensorPort.S4, SensorConstants.WHITE);
		
		movement = new Movement(Direction.STRAIGHT, 1, Movement.MAX_SPEED, true);
		
		Button.waitForAnyPress();
		
		for(int i = 0 ; i < 5 ; i++){
			Sound.beep();
			try{Thread.sleep(1000);}catch(Exception e){e.printStackTrace();}
		}
		
		Motor.C.rotate(-130);
		
		movement.update();
		
		try{Thread.sleep(300);}catch(Exception e){e.printStackTrace();}
		
		Motor.C.rotate(130);
		
		
		while(true){
			digestInputs();
		}
	}
	
	private void digestInputs(){
		boolean leftSensorDetect = leftSensor.getColorID() == DETECTION_COLOUR || leftSensor.getColorID() == DETECTION_COLOUR2;
		boolean rightSensorDetect = rightSensor.getColorID() == DETECTION_COLOUR || leftSensor.getColorID() == DETECTION_COLOUR2;
		
		if(leftSensorDetect || rightSensorDetect){
			movement.reverse();
			movement.set(Direction.STRAIGHT, 0);
			
			
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
				System.exit(0);
			}
		});
		
		new Main();
	}
}
