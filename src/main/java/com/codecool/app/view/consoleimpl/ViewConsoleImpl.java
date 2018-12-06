package com.codecool.app.view.consoleimpl;

import com.codecool.app.view.View;

import java.util.NoSuchElementException;
import java.util.Scanner;

public abstract class ViewConsoleImpl implements View {
    private final String EMPTY_INPUT_ERROR = "Input is empty";

    @Override
    public void println(String text) {
        System.out.println(text);
    }

    @Override
    public String getInput() throws NoSuchElementException {
        Scanner scanner = new Scanner(System.in);
        String result = scanner.next().trim();

        if (result.length() < 1){
            throw new NoSuchElementException(EMPTY_INPUT_ERROR);
        }

        return result;
    }

    @Override
    public void printError(String err) {
        println(String.format(" !!! %s", err));
    }
}
