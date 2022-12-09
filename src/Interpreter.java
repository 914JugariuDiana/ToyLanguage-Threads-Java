import Domain.Expression.*;
import Domain.IStmt.*;
import Domain.Type.BoolType;
import Domain.Type.IntType;
import Domain.Type.RefType;
import Domain.Type.StringType;
import Domain.Value.BoolValue;
import Domain.Value.IntValue;
import Domain.Value.StringValue;
import View.Command.ExitCommand;
import View.Command.RunExample;
import View.TextMenu;

public class Interpreter {
    public static void main(String[] args){
        IStmt ex1 = new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignStmt("v", new ValueExpression(new IntValue(2))),
                        new PrintStmt(new VariableExpression("v"))));
        IStmt ex2 = new CompStmt( new VarDeclStmt("a",new IntType()), new CompStmt(new VarDeclStmt("b",new IntType()),
                new CompStmt(new AssignStmt("a", new ArithmeticExpression(new ValueExpression(new IntValue(2)),
                        new ArithmeticExpression(new ValueExpression(new IntValue(3)), new ValueExpression(new IntValue(5)), 3), 1)),
                        new CompStmt(new AssignStmt("b",new ArithmeticExpression(new VariableExpression("a"), new ValueExpression(
                                new IntValue(1)), 1)), new PrintStmt(new VariableExpression("b"))))));
        IStmt ex3 = new CompStmt(new VarDeclStmt("a",new BoolType()), new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignStmt("a", new ValueExpression(new BoolValue(true))), new CompStmt(new IfStmt(new VariableExpression("a"),
                        new AssignStmt("v",new ValueExpression(new IntValue(2))), new AssignStmt("v", new ValueExpression(new IntValue(3)))),
                        new PrintStmt(new VariableExpression("v"))))));
        IStmt ex5 = new CompStmt(new VarDeclStmt("a",new BoolType()), new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignStmt("a", new ValueExpression(new BoolValue(true))), new CompStmt(new IfStmt(new RelationalExpression(
                        new ValueExpression(new IntValue(3)), new ValueExpression(new IntValue(4)), "<"),
                        new AssignStmt("v",new ValueExpression(new IntValue(2))), new AssignStmt("v", new ValueExpression(new IntValue(3)))),
                        new PrintStmt(new VariableExpression("v"))))));
        IStmt ex4 = new CompStmt(new VarDeclStmt("varf", new StringType()), new CompStmt(new AssignStmt("varf", new ValueExpression(new StringValue("test.in"))),
                new CompStmt(new OpenRFileStmt(new VariableExpression("varf")), new CompStmt(new VarDeclStmt("varc", new IntType()), new CompStmt(
                        new ReadFileStmt(new VariableExpression("varf"), "varc"), new CompStmt(new PrintStmt(new VariableExpression("varc")), new CompStmt(
                        new ReadFileStmt(new VariableExpression("varf"), "varc"), new CompStmt(new PrintStmt(new VariableExpression("varc")), new CloseRFileStmt(
                        new VariableExpression("varf"))))))))));

        IStmt ex6 = new CompStmt(new VarDeclStmt("v", new IntType()), new CompStmt(new AssignStmt("v", new ValueExpression(new IntValue(4))), new CompStmt(
                new WhileStmt(new RelationalExpression(new VariableExpression("v"), new ValueExpression(new IntValue(0)), ">"), new CompStmt(new PrintStmt(
                        new VariableExpression("v")), new AssignStmt("v", new ArithmeticExpression(new VariableExpression("v"), new ValueExpression(new IntValue(1)), 2)))),
                new PrintStmt(new VariableExpression("v")))));

        IStmt ex7 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())), new CompStmt(new HeapAllocationStmt("v", new ValueExpression(new IntValue(20))),
                new CompStmt(new PrintStmt(new HeapReadingExpression(new VariableExpression("v"))), new CompStmt(new HeapWritingStmt("v", new ValueExpression(
                        new IntValue(30))), new PrintStmt(new ArithmeticExpression(new HeapReadingExpression(new VariableExpression("v")), new ValueExpression(
                        new IntValue(5)), 1))))));
        IStmt ex8 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())), new CompStmt(new HeapAllocationStmt("v", new ValueExpression(new IntValue(20))),
                new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))), new CompStmt(new HeapAllocationStmt("a", new VariableExpression("v")),
                        new CompStmt(new HeapAllocationStmt("v", new ValueExpression(new IntValue(30))), new PrintStmt(new HeapReadingExpression( new HeapReadingExpression(
                                new VariableExpression("a")))))))));
        //int v; Ref int a; v=10;new(a,22);fork(wH(a,30);v=32;print(v);print(rH(a)));print(v);print(rH(a))
        IStmt ex9 = new CompStmt(new VarDeclStmt("v", new IntType()), new CompStmt(new VarDeclStmt("a", new RefType(new IntType())), new CompStmt(new AssignStmt("v",
                new ValueExpression(new IntValue(10))), new CompStmt(new HeapAllocationStmt("a", new ValueExpression(new IntValue(22))), new CompStmt(
                new ForkStmt(new CompStmt(new HeapWritingStmt("a", new ValueExpression(new IntValue(30))), new CompStmt(new AssignStmt("v", new ValueExpression(
                        new IntValue(32))), new CompStmt(new PrintStmt(new VariableExpression("v")), new PrintStmt(new HeapReadingExpression(new VariableExpression("a"))))))),
                new CompStmt(new PrintStmt(new VariableExpression("v")), new PrintStmt(new HeapReadingExpression(new VariableExpression("a")))))))));


        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));
        menu.addCommand(new RunExample("1", ex1.toString(), ex1, "log1.out"));
        menu.addCommand(new RunExample("2", ex2.toString(), ex2, "log2.out"));
        menu.addCommand(new RunExample("3", ex3.toString(), ex3, "log3.out"));
        menu.addCommand(new RunExample("4", ex4.toString(), ex4, "log4.out"));
        menu.addCommand(new RunExample("5", ex5.toString(), ex5, "log5.out"));
        menu.addCommand(new RunExample("6", ex6.toString(), ex6, "log6.out"));
        menu.addCommand(new RunExample("7", ex7.toString(), ex7, "log7.out"));
        menu.addCommand(new RunExample("8", ex8.toString(), ex8, "log8.out"));
        menu.addCommand(new RunExample("9", ex9.toString(), ex9, "log9.out"));
        menu.show();
    }
}
