package com.github.kylecharters;

import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;

public class Movement{
	public static final float MAX_SPEED = Math.min(Motor.A.getMaxSpeed(), Motor.B.getMaxSpeed());
	
	private NXTRegulatedMotor leftMotor = Motor.A;
	private NXTRegulatedMotor rightMotor = Motor.B;
	
	public enum Direction{
		LEFT,
		RIGHT,
		STRAIGHT
	}
	
	public Direction direction;
	public float offset;
	public float speed;
	public boolean forward;

	public Movement(Direction direction, float offset, float speed, boolean forward){
		this.direction = direction;
		this.offset = offset;
		this.speed = speed;
		this.forward = forward;
	}
	
	public void set(Direction direction, float offset){
		this.direction = direction;
		this.offset = offset;
	}
	
	public void turnStraight(float amount){
		if(offset < 1){
			offset = Math.min(1, offset + amount);
			update();
		}else{
			this.direction = Direction.STRAIGHT;
			update();
		}
	}
	
	public void reverse(){
		forward = !forward;
		update();
	}
	
	public void stop(){
		speed = 0;
		update();
	}
	
	public void resume(){
		speed = MAX_SPEED;
		update();
	}
	
	private void update(){
		updateLeftMotor();
		updateRightMotor();
	}
	
	private void updateLeftMotor(){
		if(direction == Direction.LEFT){
			float amount = (offset - 0.5f) * 2;
			
			leftMotor.setSpeed(amount * speed);
			
			if(!forward)
				amount = -amount;
			
			if(amount > 0){
				leftMotor.forward();
			}else{
				leftMotor.backward();
			}
		}else{
			leftMotor.setSpeed(speed);
			
			if(forward){
				leftMotor.forward();
			}else{
				leftMotor.backward();
			}
		}
	}
	

	private void updateRightMotor(){
		if(direction == Direction.RIGHT){
			float amount = (offset - 0.5f) * 2;
			
			rightMotor.setSpeed(amount * speed);
			
			if(!forward)
				amount = -amount;
			
			if(amount > 0){
				rightMotor.forward();
			}else{
				rightMotor.backward();
			}
		}else{
			rightMotor.setSpeed(speed);
			
			if(forward){
				rightMotor.forward();
			}else{
				rightMotor.backward();
			}
		}
	}
}