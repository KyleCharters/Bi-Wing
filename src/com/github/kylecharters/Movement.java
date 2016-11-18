package com.github.kylecharters;

import lejos.nxt.Motor;

public class Movement{

	public static final double MAX_SPEED = Math.min(Motor.A.getMaxSpeed(), Motor.B.getMaxSpeed());
	
	public enum Direction{
		RIGHT,
		LEFT,
		STRAIGHT
	}
	
	public Direction direction;
	public double speed;
	public double offset;

	public Movement(Direction direction, double speed, double offset){
		this.direction = direction;
		this.speed = speed;
		this.offset = offset;
	}
	
	public void turn(Direction direction, double delta){
		this.direction = direction;
		this.offset += offset;
	}
	
	public double leftMotor(){
		return direction == Direction.STRAIGHT ? speed : direction == Direction.LEFT ? speed * offset : speed;
	}
	

	public double rightMotor(){
		return direction == Direction.STRAIGHT ? speed : direction == Direction.RIGHT ? speed * offset : speed;
	}
}