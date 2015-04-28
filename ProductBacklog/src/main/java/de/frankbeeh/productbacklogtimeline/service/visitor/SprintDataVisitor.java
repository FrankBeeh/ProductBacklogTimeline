package de.frankbeeh.productbacklogtimeline.service.visitor;

import de.frankbeeh.productbacklogtimeline.domain.SprintData;

public interface SprintDataVisitor {

    public abstract void reset();

    public abstract void visit(SprintData sprintData);

}