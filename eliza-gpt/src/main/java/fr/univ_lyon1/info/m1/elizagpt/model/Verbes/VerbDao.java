package fr.univ_lyon1.info.m1.elizagpt.model.Verbes;


import java.util.List;

/**
 * Information about conjugation of a verb.
 */
public interface VerbDao {
    /**
     * Get all the verbs.
     * 
     * @return
     */
    List<Verb> getAllVerbs();
    /**
     * Add a verb to the list.
     * 
     * @param verb
     */
    void addVerb(Verb verb);
    /**
     * Remove a verb from the list.
     * 
     * @param verb
     */
    void removeVerb(Verb verb);
    /**
     * Update a verb in the list.
     * 
     * @param oldVerb
     * @param newVerb
     */
    void updateVerb(Verb oldVerb, Verb newVerb);
}
