package com.epam.rd.qa.at;

public class Task {
	
	public static void main(String[] args) {
		for (int i = 1; i <= 9; i++) {
			int square = i * i;
			int cube = i * i * i;
			System.out.println(i + " " + square + " " + cube);
		}
	}

}
