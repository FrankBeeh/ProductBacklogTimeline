package de.frankbeeh.productbacklogtimeline.service.visitor;

import de.frankbeeh.productbacklogtimeline.data.SprintData;

public abstract class SprintDataVisitor {
    public abstract void visit(SprintData sprintData);
}
