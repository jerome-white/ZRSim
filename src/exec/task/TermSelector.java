package exec.task;

import java.util.concurrent.Callable;

import util.SuffixTree;
import visitor.MarkRedundantVisitor;

public class TermSelector implements Callable<String> {
    private String ngram;
    private SuffixTree root;

    public TermSelector(SuffixTree root, String ngram) {
        this.root = root;
        this.ngram = ngram;
    }

    public String call() {
        root.accept(new MarkRedundantVisitor(ngram, root));

        return ngram;
    }
}
