package engine.physics;

import org.joml.Vector3f;

public class Raycast {
    private static int RECURSION_COUNT = 10;

    public static Vector3f toTerrain(Vector3f from, Vector3f direction, float range){
        Vector3f currentTerrainPoint;
        if (intersectionInRange(from, 0, range, direction)) {
            currentTerrainPoint = binarySearch(0, 0, range, direction, from);
        } else {
            currentTerrainPoint = null;
        }
        return currentTerrainPoint;
    }

    private static Vector3f binarySearch(int count, float start, float finish, Vector3f ray, Vector3f from) {
        float half = start + ((finish - start) / 2f);
        if (count >= RECURSION_COUNT) {
            Vector3f endPoint = getPointOnRay(from, ray, half);
            return endPoint;
        }
        if (intersectionInRange(from, start, half, ray)) {
            return binarySearch(count + 1, start, half, ray, from);
        } else {
            return binarySearch(count + 1, half, finish, ray, from);
        }
    }

    private static Vector3f getPointOnRay(Vector3f start, Vector3f ray, float distance) {
        Vector3f scaledRay = new Vector3f(ray.x * distance, ray.y * distance, ray.z * distance);
        return new Vector3f(start.x + scaledRay.x, start.y + scaledRay.y, start.z + scaledRay.z);
    }

    private static boolean intersectionInRange(Vector3f from, float start, float finish, Vector3f ray) {
        Vector3f startPoint = getPointOnRay(from, ray, start);
        Vector3f endPoint = getPointOnRay(from, ray, finish);
        if (!isUnderGround(startPoint) && isUnderGround(endPoint)) {
            return true;
        } else {
            return false;
        }
    }

    private static boolean isUnderGround(Vector3f testPoint) {
        float height = 0;
        if (testPoint.y < height) {
            return true;
        } else {
            return false;
        }
    }
}
