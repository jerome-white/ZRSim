package visitor;

import java.io.PrintStream;

import index.SuffixTree;
import util.entity.Posting;

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
        if (node.appearances() >= appearances &&
            (redundants || !redundants && !node.isRedundant())) {
            node.forEachLocation((document, offsets) -> {
                    for (Integer o : offsets) {
                        Posting posting = new Posting(document, ngram, o);
                        printStream.println(posting);
                    }
                });
        }
    }
}
