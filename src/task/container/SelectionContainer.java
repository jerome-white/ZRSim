package task.container;

import task.TermSelector;
import index.SuffixTree;

public class SelectionContainer extends TaskContainer {
    private SuffixTree root;

    public SelectionContainer(SuffixTree root) {
        this.root = root;
    }

    public void accept(String t, SuffixTree u) {
        tasks.add(new TermSelector(root, u, t));
    }
}
