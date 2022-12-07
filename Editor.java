package boggle;

import java.util.Deque;
import java.util.EmptyStackException;
import java.util.LinkedList;
import java.util.Stack;

public class Editor {
    public Stack<BoggleGrid.Memento> History;
    public BoggleGrid bogglegrid;

    public Editor(BoggleGrid bogglegridToRetstore){
        this.History = new Stack<BoggleGrid.Memento>();
        this.bogglegrid = bogglegridToRetstore;
    }
    public void addMemento(){
        History.push(this.bogglegrid.takeSnapshot());
    }
    public BoggleGrid undo()
    {
        try{
            return this.bogglegrid.restore(History.pop());
        } catch(EmptyStackException e){
            System.out.println("Nothing to undo");
        }
        return bogglegrid;
    }
}
