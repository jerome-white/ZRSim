package util;

import java.lang.Iterable;
import java.util.Set;
import java.util.List;
import java.util.Iterator;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ForwardIndex {
    private class TermIterator implements Iterable<Token>, Iterator<Token> {
        Iterator<String> documentIterator;
        Iterator<Token> tokenIterator;

        public TermIterator() {
            documentIterator = index.keySet().iterator();
            tee();
        }

        public Iterator<Token> iterator() {
            return this;
        }

        private void tee() {
            tokenIterator = (documentIterator.hasNext()) ?
                index.get(documentIterator.next()).iterator() :
                Collections.emptyIterator();
        }

        public boolean hasNext() {
            return tokenIterator.hasNext();
        }

        public Token next() {
            Token token = tokenIterator.next();

            if (!hasNext()) {
                tee();
            }

            return token;
        }
    }

    private ConcurrentHashMap<String, ConcurrentLinkedQueue<Token>> index;

    public ForwardIndex() {
        index = new ConcurrentHashMap<String, ConcurrentLinkedQueue<Token>>();
    }

    public void add(String document, Token token) {
        index
            .computeIfAbsent(document, k -> new ConcurrentLinkedQueue<Token>())
            .add(token);
    }

    public void add(Token token) {
        add(token.getDocument(), token);
    }

    public Collection<Token> get(String document) {
        return index.get(document);
    }

    public Iterable<Token> termIterator() {
        return new TermIterator();
    }

    public Set<String> documents() {
        return index.keySet();
    }
}
