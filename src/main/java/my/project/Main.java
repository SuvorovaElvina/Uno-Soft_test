package my.project;

import my.project.number.Action;
import my.project.exception.NotFoundException;

public class Main {
    public static void main(String[] args) throws NotFoundException {
        Action action = new Action();
        action.process(args[0]);
    }
}
