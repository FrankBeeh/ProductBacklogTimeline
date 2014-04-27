package de.frankbeeh.productbacklogtimeline.service.visitor;

/**
 * Responsibility:
 * <ul>
 * <li>Compute the new <b>minimum</b> velocity.
 * </ul>
 */
public class ComputeMinimumVelocityStrategy implements ComputeVelocityStrategy {
    @Override
    public double computeVelocity(double velocityOfThisSprint, double historicVelocity, int numberOfClientsVisited) {
        return Math.min(velocityOfThisSprint, historicVelocity);
    }
}
