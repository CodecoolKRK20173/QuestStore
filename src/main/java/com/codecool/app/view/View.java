package com.codecool.app.view;

import java.util.NoSuchElementException;

public interface View {
    public void println(String text);
    public String getInput() throws NoSuchElementException;
    public void printError(String err);
}
