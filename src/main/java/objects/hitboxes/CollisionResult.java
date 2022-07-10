package objects.hitboxes;

import java.util.List;

public record CollisionResult(List<Collider> collider, boolean collisionX, boolean collisionY) {
}
