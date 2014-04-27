package de.frankbeeh.productbacklogtimeline.service.visitor;

/**
 * Responsibility:
 * <ul>
 * <li>Define the interface for all strategies that compute velocity.
 * </ul>
 */
public interface ComputeVelocityStrategy {
    double computeVelocity(double velocityOfThisSprint, double historicVelocity, int numberOfClientsVisited);
}