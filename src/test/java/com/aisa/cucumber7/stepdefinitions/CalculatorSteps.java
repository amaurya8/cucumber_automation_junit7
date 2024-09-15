package com.aisa.cucumber7.stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.junit.jupiter.api.Assertions;


import java.util.logging.Logger;

public class CalculatorSteps {

    private static final Logger logger = Logger.getLogger(CalculatorSteps.class.getName());

    private int firstNumber;
    private int secondNumber;
    private int result;

    @Given("I have the first number {int}")
    public void i_have_the_first_number(int number1) {
        logger.info("Starting Given step: I have the first number " + number1);
        this.firstNumber = number1;
        logger.info("Finished Given step: I have the first number " + number1);
    }

    @Given("I have the second number {int}")
    public void i_have_the_second_number(int number2) {
        logger.info("Starting Given step: I have the second number " + number2);
        this.secondNumber = number2;
        logger.info("Finished Given step: I have the second number " + number2);
    }

    @When("I add the numbers")
    public void i_add_the_numbers() {
        logger.info("Starting When step: I add the numbers");
        this.result = firstNumber + secondNumber;
        logger.info("Finished When step: I add the numbers");
    }

    @When("I subtract the numbers")
    public void i_subtract_the_numbers() {
        logger.info("Starting When step: I subtract the numbers");
        this.result = firstNumber - secondNumber;
        logger.info("Finished When step: I subtract the numbers");
    }

    @When("I multiply the numbers")
    public void i_multiply_the_numbers() {
        logger.info("Starting When step: I multiply the numbers");
        this.result = firstNumber * secondNumber;
        logger.info("Finished When step: I multiply the numbers");
    }

    @Then("the result should be {int}")
    public void the_result_should_be(int expectedResult) {
        logger.info("Starting Then step: the result should be " + expectedResult);
        Assertions.assertEquals(expectedResult, result);
        logger.info("Finished Then step: the result should be " + expectedResult);
    }
}