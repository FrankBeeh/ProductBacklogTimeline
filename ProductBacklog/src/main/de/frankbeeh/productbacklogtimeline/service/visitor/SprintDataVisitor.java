package de.frankbeeh.productbacklogtimeline.service.visitor;

import de.frankbeeh.productbacklogtimeline.data.SprintData;

public interface SprintDataVisitor {

    public abstract void reset();

    public abstract void visit(SprintData sprintData);

}