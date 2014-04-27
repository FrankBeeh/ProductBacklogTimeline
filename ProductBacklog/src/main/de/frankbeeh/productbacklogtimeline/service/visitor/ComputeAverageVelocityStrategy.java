package de.frankbeeh.productbacklogtimeline.service.visitor;

/**
 * Responsibility:
 * <ul>
 * <li>Compute the new <b>average</b> velocity.
 * </ul>
 */
public class ComputeAverageVelocityStrategy implements ComputeVelocityStrategy {
    @Override
    public double computeVelocity(double velocityOfThisSprint, double historicVelocity, int numberOfClientsVisited) {
        return (historicVelocity * (numberOfClientsVisited - 1) + velocityOfThisSprint) / numberOfClientsVisited;
    }
}
