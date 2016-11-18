package com.github.kylecharters;
import com.github.kylecharters.Movement.Direction;

import lejos.nxt.Button;
import lejos.nxt.ButtonListener;
import lejos.nxt.ColorSensor;
import lejos.nxt.ColorSensor.Color;
import lejos.nxt.SensorPort;
import lejos.nxt.Sound;

public class Main {
	
	public static final int DETECTION_COLOUR = Color.BLACK;
	
	private ColorSensor rightSensor, leftSensor;
	
	private Movement movement;
	
	public Main(){
		Sound.beep();
		rightSensor = new ColorSensor(SensorPort.S1, Color.WHITE);
		leftSensor = new ColorSensor(SensorPort.S2, Color.WHITE);
		
		System.out.println(Movement.MAX_SPEED);
		
		
		//try{Thread.sleep(5000);}catch(Exception e){e.printStackTrace();}
		
		while(true){
			checkDirection();
		}
	}
	
	private void checkDirection(){
		boolean leftSensorDetect = leftSensor.getColorID() == DETECTION_COLOUR;
		boolean rightSensorDetect = rightSensor.getColorID() == DETECTION_COLOUR;
		
		if(leftSensorDetect && rightSensorDetect){
			movement.direction = Direction.STRAIGHT;
			movement.speed = -Movement.MAX_SPEED;
		}else if(leftSensorDetect){
			movement.direction = Direction.RIGHT;
			movement.speed = Movement.MAX_SPEED;
		}else if(rightSensorDetect){
			movement.direction = Direction.LEFT;
			movement.speed = Movement.MAX_SPEED;
		}else{
			movement.direction = Direction.STRAIGHT;
			movement.speed = Movement.MAX_SPEED;
		}
	}
	
	public static void main(String[] args){
		Button.ESCAPE.addButtonListener(new ButtonListener() {
			public void buttonReleased(Button b) {System.exit(0);}
			public void buttonPressed(Button b) {System.exit(0);}
		});
		
		new Main();
	}
}
