package Game;

import java.awt.Graphics;
import java.util.LinkedList;

public class Handler {

    LinkedList<GameObject> objects = new LinkedList<GameObject>();

    public void tick() {
        if (objects != null) {
            for (int i = 0; i < objects.size(); i++) {
                GameObject tempObject = objects.get(i);
                tempObject.tick();
            }
        }
    }

    public void render(Graphics g) {
        if (objects != null) {
            for (int i = 0; i < objects.size(); i++) {
                GameObject tempObject = objects.get(i);
                tempObject.render(g);
            }
        }
    }

    public void addObject(GameObject object) {
        objects.add(object);
    }

    public void removeObject(GameObject object) {
        objects.remove(object);
    }
}
