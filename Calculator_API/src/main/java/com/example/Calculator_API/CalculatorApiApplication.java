package com.example.Calculator_API;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;



@SpringBootApplication
@EnableWebMvc
@RestController
public class CalculatorApiApplication {
	@CrossOrigin("http://localhost:4200")
	@PostMapping ("/Calculator")
	public String linker(@RequestBody String expression)
	{
		// this function takes the request body from the http post request and pass it to the calc function which evaluate the expression
		String output ="";
		try{
			output = String.valueOf(calc(expression));
		}catch ( ArithmeticException e){
			output = "E";
		}

		return output;


	}
	public static double calc(String string) throws ArithmeticException{
		//this function evaluate the mathematical expression
		//initialize our variables
		double result = 0;
		String[] container = new String[2];
		double var1;
		double var2;
		String operator = "";

		//assertion of invalid sequences of operations
		if(string.contains("+*") || string.contains("*+")||string.contains("*/")||string.contains("/*")||string.contains("^/")
				|| string.contains("/^") || string.contains("^*") || string.contains("*^") || string.contains("/+") || string.contains("+/")
				|| string.contains("^+") || string.contains("+^") || string.contains("-*") || string.contains("-/") || string.contains("-^")
				|| string.contains("-*") || string.contains("**") || string.contains("//") || string.contains("++") || string.contains("--")
				|| string.contains("^^")){
			throw new ArithmeticException();
		}
		//splitting our expression based on the entered operation in it
		if(string.contains("+") && string.contains("-")){
			container = string.split("\\+");
			operator = "+";
		}else if(string.contains("*") && string.contains("-")){
			container = string.split("\\*");
			operator = "*";
		}else if(string.contains("/") && string.contains("-")){
			container = string.split("/");
			operator = "/";
		}else if(string.contains("^") && string.contains("-")){
			container = string.split("\\^");
			operator = "^";
		}else if(string.contains("+")){
			container = string.split("\\+");
			operator = "+";
		}else if(string.contains("-")){
			container = string.split("-");
			operator = "-";
		}else if(string.contains("*")){
			container = string.split("\\*");
			operator = "*";
		}else if(string.contains("/")){
			container = string.split("/");
			operator = "/";
		}else if(string.contains("^")){
			container = string.split("\\^");
			operator = "^";
		}

		//determine our two operands
		var1 = Double.parseDouble(container[0]);
		var2 = Double.parseDouble(container[1]);

		//perform the entered operation on the entered operands
		switch (operator) {
			case "+":
				result = var1 + var2;
				break;
			case "-":
				result = var1 - var2;
				break;
			case "*":
				result = var1 * var2;
				break;
			case "/":
				if(var2 == 0){
					throw new ArithmeticException();
				}else {
					result = var1 / var2;
				}

				break;
			case "^":
				if((var1 < 0) && (var2 == 0.5)){
					throw new ArithmeticException();
				}else {
					result = Math.pow(var1, var2);
				}

				break;

		}

		return result;
	}
	public static void main(String[] args) {
		SpringApplication.run(CalculatorApiApplication.class, args);
	}

}
