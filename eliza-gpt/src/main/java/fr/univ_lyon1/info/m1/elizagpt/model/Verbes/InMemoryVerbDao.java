package fr.univ_lyon1.info.m1.elizagpt.model.Verbes;

import java.util.ArrayList;
import java.util.List;

/**
 * A verb DAO that stores verbs in memory.
 */
public class InMemoryVerbDao implements VerbDao {
    private List<Verb> verbs = new ArrayList<>();

    /**
     * Constructor.
     */
    public InMemoryVerbDao() {
        // Initialiser avec des verbes par défaut
        verbs.add(new Verb("suis", "êtes"));
    }

    @Override
    public List<Verb> getAllVerbs() {
        return new ArrayList<>(verbs);
    }

    /**
     * Add a verb to the list.
     * 
     * @param verb
     */
    @Override
    public void addVerb(final Verb verb) {
        verbs.add(verb);
    }

    @Override
    public void removeVerb(final Verb verb) {
        verbs.remove(verb);
    }

    @Override
    public void updateVerb(final Verb oldVerb, final Verb newVerb) {
        int index = verbs.indexOf(oldVerb);
        if (index != -1) {
            verbs.set(index, newVerb);
        }
    }
}
