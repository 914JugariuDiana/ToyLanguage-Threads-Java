package View;

import Controller.Controller;
import Domain.Exception.MyException;
import PrgState.PrgState;

public class View {
    Controller controller;
    public View(Controller c){
        controller = c;
    }
    public void inputPrg(PrgState prg){

    }

    public void executeProgram() throws MyException {
        controller.allStep();
    }
}
