package visitor;

import java.io.PrintStream;

import util.Token;
import util.Location;
import tree.SuffixTree;

public class OutputVisitor implements SuffixTreeVisitor {
    private int appearances;
    private boolean redundants;
    private String ngram;
    private PrintStream printStream;

    public OutputVisitor(String ngram,
                         int appearances,
                         boolean redundants,
                         PrintStream printStream) {
	this.ngram = ngram;        
	this.appearances = appearances;
	this.redundants = redundants;
        this.printStream = printStream;
    }

    public OutputVisitor(String ngram, int appearances, boolean redundants) {
        this(ngram, appearances, redundants, System.out);
    }

    public OutputVisitor(String ngram) {
        this(ngram, 0, true);
    }

    public SuffixTreeVisitor spawn(String ngram) {
        return new OutputVisitor(this.ngram + ngram,
                                 appearances,
                                 redundants,
                                 printStream);
    }

    public void visit(SuffixTree node) {
        if (node.getLocations().size() >= appearances &&
            (redundants || !redundants && !node.isRedundant())) {
            for (Location location : node.getLocations()) {
                printStream.println(new Token(ngram, location));
            }
        }
    }
}
