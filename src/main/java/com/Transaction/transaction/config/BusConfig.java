package com.Transaction.transaction.config;

import com.Transaction.transaction.entity.BusStop;
import com.Transaction.transaction.entity.Route12;
import com.Transaction.transaction.model.Graph;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class BusConfig {
    public void dijkstra(Graph graph, BusStop source) {
        source.setDistance(0);
        List<BusStop> unvisitedNodes = new ArrayList<>(graph.getBusStops());

        while (!unvisitedNodes.isEmpty()) {
            BusStop current = getClosestNode(unvisitedNodes);

            for (Route12 route : current.getDestinationRoutes()) {
                int tentativeDistance = current.getDistance() + route.getWeight();
                if (tentativeDistance < route.getDistance()) {
                    route.setDistance(tentativeDistance);
                }
            }

            current.setVisited(true);
            unvisitedNodes.remove(current);
        }
    }

    private BusStop getClosestNode(List<BusStop> nodes) {
        BusStop closest = null;
        int minDistance = Integer.MAX_VALUE;

        for (BusStop node : nodes) {
            System.out.println("Node: " + node.getId() + ", Visited: " + node.isVisited() + ", Distance: " + node.getDistance());
            if (!node.isVisited() && node.getDistance() < minDistance) {
                closest = node;
                minDistance = node.getDistance();
            }
        }

        return closest;
    }

}
