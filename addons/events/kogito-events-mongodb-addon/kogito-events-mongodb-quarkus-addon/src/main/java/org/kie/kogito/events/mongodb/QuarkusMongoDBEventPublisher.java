/*
 * Copyright 2021 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kie.kogito.events.mongodb;

import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoClient;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.kie.kogito.persistence.transaction.TransactionManager;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Optional;

@Singleton
public class QuarkusMongoDBEventPublisher extends MongoDBEventPublisher {

    @Inject
    MongoClient mongoClient;

    @Inject
    TransactionManager<ClientSession> transactionManager;

    @Inject
    @ConfigProperty(name = "kogito.events.processinstances.enabled")
    Optional<Boolean> processInstancesEvents;

    @Inject
    @ConfigProperty(name = "kogito.events.usertasks.enabled")
    Optional<Boolean> userTasksEvents;

    @Inject
    @ConfigProperty(name = "kogito.events.variables.enabled")
    Optional<Boolean> variablesEvents;

    @Inject
    @ConfigProperty(name = "kogito.events.database", defaultValue = "kogito-events")
    String eventsDatabaseName;

    @Inject
    @ConfigProperty(name = "kogito.events.processinstances.collection", defaultValue = "kogitoprocessinstancesevents")
    String processInstancesEventsCollection;

    @Inject
    @ConfigProperty(name = "kogito.events.usertasks.collection", defaultValue = "kogitousertaskinstancesevents")
    String userTasksEventsCollection;

    @Inject
    @ConfigProperty(name = "kogito.events.variables.collection", defaultValue = "kogitovariablesevents")
    String variablesEventsCollection;

    @PostConstruct
    public void setup() {
        super.configure();
    }

    @Override
    protected MongoClient mongoClient() {
        return this.mongoClient;
    }

    @Override
    protected TransactionManager<ClientSession> transactionManager() {
        return this.transactionManager;
    }

    @Override
    protected boolean processInstancesEvents() {
        return this.processInstancesEvents.orElse(true);
    }

    @Override
    protected boolean userTasksEvents() {
        return this.userTasksEvents.orElse(true);
    }

    @Override
    protected boolean variablesEvents() {
        return this.variablesEvents.orElse(true);
    }

    @Override
    protected String eventsDatabaseName() {
        return this.eventsDatabaseName;
    }

    @Override
    protected String processInstancesEventsCollection() {
        return this.processInstancesEventsCollection;
    }

    @Override
    protected String userTasksEventsCollection() {
        return this.userTasksEventsCollection;
    }

    @Override
    protected String variablesEventsCollection() {
        return this.variablesEventsCollection;
    }
}
