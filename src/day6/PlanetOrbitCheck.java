package day6;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PlanetOrbitCheck {
    private Planet base;
    private List<Planet> path = null;
    private int distance;

    public PlanetOrbitCheck(Planet start, String startName, String endName) throws IOException {
        this.base = start;
        BufferedReader reader;
        try {
            reader = new BufferedReader(
                    new FileReader("src/day6/planets.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }

        String line;

        while ((line = reader.readLine()) != null) {
            checkPlanet(line.split("\\)"));
        }

        distance = 0;
        path = new ArrayList<>();
        path.add(base);
        findPath(path, startName);
        List<Planet> journey = new ArrayList<>(path);

        path = null;
        for (int i = journey.size() - 1; i >= 0; i--) {
            List<Planet> route = new ArrayList<>();
            route.add(journey.get(i));
            findPath(route, endName);

            if (path != null) {
                distance = journey.size() - i + path.size() - 4;
                break;
            }
        }
    }

    public int getDistance() {
        return distance;
    }

    private void checkPlanet(String[] planetDetails) {
        String planetParent = planetDetails[0];
        String planetName = planetDetails[1];
        Planet parent = base.getPlanet(planetParent);
        Planet child =  base.getPlanet(planetName);

        if (parent != null && child != null) {
            base.getChildren().remove(child);
            parent.addChild(child);
        } else if (parent != null) {
            parent.addChild(new Planet(planetName));
        } else if (child != null) {
            parent = new Planet(planetParent);
            base.getChildren().remove(child);
            parent.addChild(child);
            base.addChild(parent);
        } else {
            parent = new Planet(planetParent);
            child = new Planet(planetName);
            parent.addChild(child);
            base.addChild(parent);
        }

    }

    public int countPlanets(Planet parent, int depth) {
        int count = 0;

        count += parent.getChildren().size() * depth;
        for (Planet planet : parent.getChildren()) {
            count += countPlanets(planet, depth + 1);
        }

        return count;
    }

    public void findPath(List<Planet> currentPath, String destination) {
        Planet pointOfContact = currentPath.get(currentPath.size() - 1);
        List<Planet> path = pointOfContact.getChildren();

        if (pointOfContact.getName().equals(destination)) {
            this.path = currentPath;
        } else if (path.size() != 0) {
            for (Planet planet : pointOfContact.getChildren()) {
                path = new ArrayList<>(currentPath);
                path.add(planet);
                findPath(path, destination);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        Planet base = new Planet("YOLO");
        PlanetOrbitCheck planetCheck = new PlanetOrbitCheck(base, "YOU", "SAN");

        // System.out.println(base);
        System.out.println(planetCheck.countPlanets(base, 0));
        System.out.println(planetCheck.getDistance());
    }


    private static class Planet {
        private String name;
        private List<Planet> children;

        public Planet(String name) {
            this.name = name;
            children = new ArrayList<>();
        }

        public void addChild(Planet child) {
            children.add(child);
        }

        public String getName() {
            return name;
        }

        public Planet getPlanet(String name) {
            Planet temp;
            if (getName().equals(name)) {
                return this;
            }
            for (Planet planet : children) {
                temp = planet.getPlanet(name);
                if (temp != null) {
                    return temp;
                }
            }
            return null;
        }

        public List<Planet> getChildren() {
            return children;
        }

        @Override
        public String toString() {
            return "Planet: " + name;
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof Planet
                    && ((Planet) obj).getName().equals(getName());
        }

    }
}
