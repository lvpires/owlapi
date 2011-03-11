package org.coode.owlapi.rdfxml.parser;

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.vocab.OWL2Datatype;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.RDF_FIRST;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.RDF_NIL;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.RDF_REST;

/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 10-Dec-2006<br><br>
 */
public class AbstractTripleHandler {

    private OWLRDFConsumer consumer;

    private ClassExpressionMatcher classExpressionMatcher = new ClassExpressionMatcher();

    private DataRangeMatcher dataRangeMatcher = new DataRangeMatcher();

    private IndividualMatcher individualMatcher = new IndividualMatcher();


    public AbstractTripleHandler(OWLRDFConsumer consumer) {
        this.consumer = consumer;
    }


    public OWLRDFConsumer getConsumer() {
        return consumer;
    }


    protected Set<OWLAnnotation> getPendingAnnotations() {
        return consumer.getPendingAnnotations();
    }

    protected void consumeTriple(IRI subject, IRI predicate, IRI object) {
        consumer.consumeTriple(subject, predicate, object);
    }

    protected void consumeTriple(IRI subject, IRI predicate, OWLLiteral object) {
        consumer.consumeTriple(subject, predicate, object);
    }



    protected boolean isStrict() {
        return consumer.getConfiguration().isStrict();
    }

    protected boolean isObjectPropertyOnly(IRI iri) {
        return consumer.isObjectPropertyOnly(iri);
    }

    protected boolean isDataPropertyOnly(IRI iri) {
        return consumer.isDataPropertyOnly(iri);
    }


    protected boolean isAnnotationPropertyOnly(IRI iri) {
        return consumer.isAnnotationPropertyOnly(iri);
    }

    protected void addAxiom(OWLAxiom axiom) {
        consumer.addAxiom(axiom);
    }


    protected OWLDataFactory getDataFactory() {
        return consumer.getDataFactory();
    }


    protected OWLClassExpression translateClassExpression(IRI IRI) {
        return consumer.translateClassExpression(IRI);
    }


    protected OWLObjectPropertyExpression translateObjectProperty(IRI IRI) {
        return consumer.translateObjectPropertyExpression(IRI);
    }


    protected OWLDataPropertyExpression translateDataProperty(IRI IRI) {
        return consumer.translateDataPropertyExpression(IRI);
    }


    protected OWLDataRange translateDataRange(IRI IRI) {
        return consumer.translateDataRange(IRI);
    }


    protected OWLIndividual translateIndividual(IRI IRI) {
        return consumer.translateIndividual(IRI);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    protected boolean isAnonymous(IRI node) {
        return consumer.isAnonymousNode(node);
    }

    protected boolean isResourcePresent(IRI mainNode, OWLRDFVocabulary predicate) {
        return consumer.getResourceObject(mainNode, predicate, false) != null;
    }

    protected boolean isLiteralPresent(IRI mainNode, OWLRDFVocabulary predicate) {
        return consumer.getLiteralObject(mainNode, predicate, false) != null;
    }

    protected boolean isRestrictionStrict(IRI node) {
        return consumer.isRestriction(node);
    }

    protected boolean isRestrictionLax(IRI node) {
        return consumer.isRestriction(node);
    }

    protected boolean isNonNegativeIntegerStrict(IRI mainNode, OWLRDFVocabulary predicate) {
        OWLLiteral literal = consumer.getLiteralObject(mainNode, predicate, false);
        if (literal == null) {
            return false;
        }
        OWLDatatype datatype = literal.getDatatype();
        OWL2Datatype nni = OWL2Datatype.XSD_NON_NEGATIVE_INTEGER;
        return datatype.getIRI().equals(nni.getIRI()) && nni.isInLexicalSpace(literal.getLiteral());
    }

    protected boolean isNonNegativeIntegerLax(IRI mainNode, OWLRDFVocabulary predicate) {
        OWLLiteral literal = consumer.getLiteralObject(mainNode, predicate, false);
        if (literal == null) {
            return false;
        }
        return OWL2Datatype.XSD_INTEGER.isInLexicalSpace(literal.getLiteral().trim());
    }

    protected int translateInteger(IRI mainNode, OWLRDFVocabulary predicate) {
        OWLLiteral literal = consumer.getLiteralObject(mainNode, predicate, true);
        if (literal == null) {
            return 0;
        }
        try {
            return Integer.parseInt(literal.getLiteral().trim());
        }
        catch (NumberFormatException e) {
            return 0;
        }
    }

    protected boolean isClassExpressionStrict(IRI node) {
        return consumer.isClassExpression(node) && !consumer.isDataRange(node);
    }

    protected boolean isClassExpressionStrict(IRI mainNode, OWLRDFVocabulary predicate) {
        IRI object = consumer.getResourceObject(mainNode, predicate, false);
        return object != null && isClassExpressionStrict(object);
    }

    protected boolean isClassExpressionLax(IRI mainNode) {
        return consumer.isClassExpression(mainNode) || consumer.isParsedAllTriples() && !consumer.isDataRange(mainNode);
    }

    protected boolean isClassExpressionLax(IRI mainNode, OWLRDFVocabulary predicate) {
        IRI object = consumer.getResourceObject(mainNode, predicate, false);
        return object != null && isClassExpressionLax(object);
    }


    protected boolean isObjectPropertyStrict(IRI node) {
        return consumer.isObjectPropertyOnly(node);
    }

    protected boolean isObjectPropertyStrict(IRI mainNode, OWLRDFVocabulary predicate) {
        IRI object = consumer.getResourceObject(mainNode, predicate, false);
        return object != null && isObjectPropertyStrict(object);
    }

    protected boolean isObjectPropertyLax(IRI node) {
        return consumer.isObjectProperty(node);
    }

    protected boolean isObjectPropertyLax(IRI mainNode, OWLRDFVocabulary predicate) {
        IRI object = consumer.getResourceObject(mainNode, predicate, false);
        return object != null && isObjectPropertyLax(object);
    }

    protected boolean isDataPropertyStrict(IRI node) {
        return consumer.isDataPropertyOnly(node);
    }

    protected boolean isDataPropertyStrict(IRI mainNode, OWLRDFVocabulary predicate) {
        IRI object = consumer.getResourceObject(mainNode, predicate, false);
        return object != null && isDataPropertyStrict(object);
    }

    protected boolean isDataPropertyLax(IRI node) {
        return consumer.isDataProperty(node);
    }

    protected boolean isDataPropertyLax(IRI mainNode, OWLRDFVocabulary predicate) {
        IRI object = consumer.getResourceObject(mainNode, predicate, false);
        return object != null && isDataPropertyLax(object);
    }


    protected boolean isDataRangeStrict(IRI node) {
        return consumer.isDataRange(node) && !consumer.isClassExpression(node);
    }

    protected boolean isDataRangeStrict(IRI mainNode, OWLRDFVocabulary predicate) {
        IRI object = consumer.getResourceObject(mainNode, predicate, false);
        return isDataRangeStrict(object);
    }

    protected boolean isDataRangeLax(IRI node) {
        return consumer.isParsedAllTriples() && !consumer.isDataRange(node);
    }

    protected boolean isDataRangeLax(IRI mainNode, OWLRDFVocabulary predicate) {
        IRI object = consumer.getResourceObject(mainNode, predicate, false);
        return object != null && isDataRangeLax(mainNode);
    }


    protected boolean isClassExpressionListStrict(IRI mainNode, int minSize) {
        return isResourceListStrict(mainNode, classExpressionMatcher, minSize);
    }

    protected boolean isDataRangeListStrict(IRI mainNode, int minSize) {
        return isResourceListStrict(mainNode, dataRangeMatcher, minSize);
    }

    protected boolean isIndividualListStrict(IRI mainNode, int minSize) {
        return isResourceListStrict(mainNode, individualMatcher, minSize);
    }

    protected boolean isResourceListStrict(IRI mainNode, TypeMatcher typeMatcher, int minSize) {
        if (mainNode == null) {
            return false;
        }
        IRI currentListNode = mainNode;
        Set<IRI> visitedListNodes = new HashSet<IRI>();
        int size = 0;
        while (true) {
            IRI firstObject = consumer.getResourceObject(currentListNode, RDF_FIRST, false);
            if (firstObject == null) {
                return false;
            }
            if (!typeMatcher.isTypeStrict(firstObject)) {
                // Something in the list that is not of the required type
                return false;
            }
            else {
                size++;
            }
            IRI restObject = consumer.getResourceObject(currentListNode, RDF_REST, false);
            if (visitedListNodes.contains(restObject)) {
                // Cycle - Non-terminating
                return false;
            }
            if (restObject == null) {
                // Not terminated properly
                return false;
            }
            if (restObject.equals(RDF_NIL.getIRI())) {
                // Terminated properly
                return size >= minSize;
            }
            // Carry on
            visitedListNodes.add(restObject);
            currentListNode = restObject;
        }
    }


    private interface TypeMatcher {

        boolean isTypeStrict(IRI node);
    }

    private class ClassExpressionMatcher implements TypeMatcher {

        public boolean isTypeStrict(IRI node) {
            return isClassExpressionStrict(node);
        }
    }

    private class DataRangeMatcher implements TypeMatcher {

        public boolean isTypeStrict(IRI node) {
            return isDataRangeStrict(node);
        }
    }

    private class IndividualMatcher implements TypeMatcher {

        public boolean isTypeStrict(IRI node) {
            return true;
        }
    }
}