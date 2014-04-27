package de.frankbeeh.productbacklogtimeline.service.visitor;

/**
 * Responsibility:
 * <ul>
 * <li>Compute the new <b>maximum</b> velocity.
 * </ul>
 */
public class ComputeMaximumVelocityStrategy implements ComputeVelocityStrategy {
    @Override
    public double computeVelocity(double velocityOfThisSprint, double historicVelocity, int numberOfClientsVisited) {
        return Math.max(velocityOfThisSprint, historicVelocity);
    }
}
