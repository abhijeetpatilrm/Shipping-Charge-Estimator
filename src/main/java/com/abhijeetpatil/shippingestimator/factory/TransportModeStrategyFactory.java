package com.abhijeetpatil.shippingestimator.factory;

import com.abhijeetpatil.shippingestimator.constants.TransportConstants;
import com.abhijeetpatil.shippingestimator.strategy.transport.AeroplaneStrategy;
import com.abhijeetpatil.shippingestimator.strategy.transport.MiniVanStrategy;
import com.abhijeetpatil.shippingestimator.strategy.transport.TransportModeStrategy;
import com.abhijeetpatil.shippingestimator.strategy.transport.TruckStrategy;
import org.springframework.stereotype.Component;

@Component
public class TransportModeStrategyFactory {

    private final MiniVanStrategy miniVanStrategy;
    private final TruckStrategy truckStrategy;
    private final AeroplaneStrategy aeroplaneStrategy;

    public TransportModeStrategyFactory(MiniVanStrategy miniVanStrategy, TruckStrategy truckStrategy, AeroplaneStrategy aeroplaneStrategy) {
        this.miniVanStrategy = miniVanStrategy;
        this.truckStrategy = truckStrategy;
        this.aeroplaneStrategy = aeroplaneStrategy;
    }

    public TransportModeStrategy getStrategy(double distanceInKm) {
        if (distanceInKm <= TransportConstants.MINI_VAN_MAX_DISTANCE) {
            return miniVanStrategy;
        } else if (distanceInKm <= TransportConstants.TRUCK_MAX_DISTANCE) {
            return truckStrategy;
        } else {
            return aeroplaneStrategy;
        }
    }
}
