/*
 * This file is part of the OWL API.
 *
 * The contents of this file are subject to the LGPL License, Version 3.0.
 *
 * Copyright (C) 2011, The University of Manchester
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0
 * in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 *
 * Copyright 2011, University of Manchester
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.semanticweb.owlapi.model;

import java.util.Set;

/** Represents a <a href=
 * "http://www.w3.org/TR/owl2-syntax/#Disjoint_Union_of_Class_Expressions"
 * >DisjointUnion</a> axiom in the OWL 2 Specification.
 * 
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group Date: 24-Oct-2006 */
public interface OWLDisjointUnionAxiom extends OWLClassAxiom {
    /** Gets the class which is equivalent to the disjoint union.
     * 
     * @return the class that is equivalent to a disjoint union of other
     *         classes. */
    OWLClass getOWLClass();

    /** Gets the class expressions which are operands of the disjoint union.
     * 
     * @return A {@code Set} containing the operands of the disjoint union, note
     *         that this <b>does not</b> include the {@code OWLClass} that is
     *         equivalent to the disjoint union. */
    Set<OWLClassExpression> getClassExpressions();

    /** Gets the part of this axiom that corresponds to an
     * {@code EquivalentClasses} axiom.
     * 
     * @return The equivalent classes axiom part of this axiom. This is
     *         essentially, {@code EquivalentClasses(CE, CEUnion)} where
     *         {@code CEUnion} is the union of the classes returned by the
     *         {@link #getClassExpressions()} method and {@code CE} is the class
     *         returned by the {@link #getOWLClass()} method. */
    OWLEquivalentClassesAxiom getOWLEquivalentClassesAxiom();

    /** Gets the part of this axiom that corresponds to an
     * {@code DisjointClasses} axiom.
     * 
     * @return The disjoint classes axiom part of this axiom. This is
     *         essentially, {@code DisjointClasses(CE1, ..., CEn)} where
     *         {@code CEi in CE1, ..., CEn} is contained in the classes returned
     *         by the {@link #getClassExpressions()} method. */
    OWLDisjointClassesAxiom getOWLDisjointClassesAxiom();

    @Override
    OWLDisjointUnionAxiom getAxiomWithoutAnnotations();
}
