package game.entity;

import graphics.render.LayeredRenderer;
import math.Vector2f;
import physics.PhysicsEngine;

public interface Entity {
	
	public void input();
	public void update(PhysicsEngine physics, float delta);
	public void render(LayeredRenderer renderer, float alpha);
	public void debugRender(LayeredRenderer renderer, float alpha);
	public void renderHitbox(LayeredRenderer renderer, float alpha);
	public boolean collidesWith(Entity entity);	
	
	public float getWidth();
	public float getHeight();
	public Vector2f getPosition();
	public Hitbox getHitbox();
}
