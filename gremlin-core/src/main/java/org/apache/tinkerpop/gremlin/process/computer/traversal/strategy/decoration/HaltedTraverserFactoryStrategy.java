/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.tinkerpop.gremlin.process.computer.traversal.strategy.decoration;

import org.apache.tinkerpop.gremlin.process.computer.traversal.step.map.TraversalVertexProgramStep;
import org.apache.tinkerpop.gremlin.process.traversal.Traversal;
import org.apache.tinkerpop.gremlin.process.traversal.TraversalStrategy;
import org.apache.tinkerpop.gremlin.process.traversal.strategy.AbstractTraversalStrategy;
import org.apache.tinkerpop.gremlin.process.traversal.util.TraversalHelper;
import org.apache.tinkerpop.gremlin.structure.util.detached.DetachedFactory;
import org.apache.tinkerpop.gremlin.structure.util.reference.ReferenceFactory;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public final class HaltedTraverserFactoryStrategy extends AbstractTraversalStrategy<TraversalStrategy.DecorationStrategy> implements TraversalStrategy.DecorationStrategy {

    private final Class haltedTraverserFactory;

    private HaltedTraverserFactoryStrategy(final Class haltedTraverserFactory) {
        this.haltedTraverserFactory = haltedTraverserFactory;
    }

    public void apply(final Traversal.Admin<?, ?> traversal) {
        TraversalHelper.getStepsOfAssignableClass(TraversalVertexProgramStep.class, traversal)
                .forEach(step -> step.setHaltedTraverserFactory(this.haltedTraverserFactory));
    }

    public static HaltedTraverserFactoryStrategy detach() {
        return new HaltedTraverserFactoryStrategy(DetachedFactory.class);
    }

    public static HaltedTraverserFactoryStrategy reference() {
        return new HaltedTraverserFactoryStrategy(ReferenceFactory.class);
    }
}
