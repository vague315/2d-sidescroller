package game.entity;

import graphics.render.LayeredRenderer;
import math.Vector2f;

public interface Entity {
	
	public void input();
	public void update(float delta);
	public void render(LayeredRenderer renderer, float alpha);
	public void debugRender(LayeredRenderer renderer, float alpha);
	public void renderHitbox(LayeredRenderer renderer, float alpha);
	public boolean collidesWith(Entity entity);	
	
	public float getWidth();
	public float getHeight();
	public Vector2f getPosition();
}
