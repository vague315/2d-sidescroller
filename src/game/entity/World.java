package game.entity;

import graphics.render.LayeredRenderer;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import physics.PhysicsEngine;

public class World {
	private static int WORLD_WIDTH=800, WORLD_HEIGHT=600;
	
	private List<AbstractEntity> entities;
	private Player player;
	
	private boolean drawWireframes;
	
	private Tile[][] tiles;
	
	public World(Player player){
		this.player = player;
		entities = new ArrayList<AbstractEntity>();
		drawWireframes = false;
		
		tiles = new Tile[WORLD_WIDTH/Tile.WIDTH][WORLD_HEIGHT/Tile.HEIGHT];
		for(int i=0; i<WORLD_WIDTH/Tile.WIDTH; i++){
			for(int j=0; j<WORLD_HEIGHT/Tile.HEIGHT; j++){
				float x = Tile.WIDTH*i + Tile.WIDTH/2;
				float y = Tile.HEIGHT*j + Tile.HEIGHT/2;

				TileType type = TileType.SKY;
				if(j==0){
					//TODO on the ground
					type=TileType.GROUND;
					//tiles[i][j] = new Tile(x,y,TileType.GROUND);
				}else{
					//tiles[i][j] = new Tile(x,y,TileType.SKY);	
				}
				
				if(j==3 && (i>=7 && i<=12)){
					type=TileType.PLATFORM;
				}
				tiles[i][j] = new Tile(x,y,type);
			}
		}
	}
	
	public void input(){
		player.input();
	}
	
	public void update(PhysicsEngine physics, float delta){
		player.update(physics, delta);
		
		
		int topTile = (int)(Math.floor((float)player.getHitbox().getTopBound()/Tile.HEIGHT));
		int botTile = (int)(Math.ceil((float)player.getHitbox().getBottomBound()/Tile.HEIGHT)) - 1;
		int leftTile = (int)(Math.floor((float)player.getHitbox().getLeftBound()/Tile.WIDTH));
		int rightTile = (int)(Math.ceil((float)player.getHitbox().getRightBound()/Tile.WIDTH)) - 1;
		
		System.out.println("left: " + leftTile + ", right: " + rightTile + ", bottom: " + botTile + ", top: " + topTile);
		
		for(int i=0; i<WORLD_WIDTH/Tile.WIDTH; i++){
			for(int j=0; j<WORLD_HEIGHT/Tile.HEIGHT; j++){
				tiles[i][j].checkCollision(false);
			}
		}
		
		for(int i=leftTile; i<=rightTile; ++i){
			for(int j=botTile; j<=topTile; ++j){
				Tile tile = tiles[i][j];
				tile.checkCollision(true); // for debug drawing purposes
				//TODO collision resolution
			}
		}
		
		//physics.checkPlayerTileCollision(player, tiles);
		//physics.checkCollision(player, entities, tiles);
//		entities.get(0).update(physics, delta);
	}
	
	public void render(LayeredRenderer renderer, float alpha){
		if(drawWireframes){
			debugRender(renderer, alpha);
		}else{
			renderWorld(renderer, alpha);
		}
	}
	
	private void renderWorld(LayeredRenderer renderer, float alpha){
		renderer.setActiveLayer(1);
		for(int i=0; i<WORLD_WIDTH/Tile.WIDTH; i++){
			for(int j=0; j<WORLD_HEIGHT/Tile.HEIGHT; j++){
				tiles[i][j].render(renderer);
			}
		}	
		
		renderer.setActiveLayer(2);		
		player.render(renderer, alpha);
		
		renderer.setActiveLayer(1);
		entities.forEach((e)->{
			e.render(renderer, alpha);
		});			
	}
	
	private void debugRender(LayeredRenderer renderer, float alpha){
		renderer.setActiveLayer(1);	
		
		for(int i=0; i<WORLD_WIDTH/Tile.WIDTH; i++){
			for(int j=0; j<WORLD_HEIGHT/Tile.HEIGHT; j++){
				tiles[i][j].debugDraw(renderer);
			}
		}
		
		player.debugRender(renderer, alpha);		
		entities.forEach((e)->{
			e.debugRender(renderer, alpha);
		});
	}
	
	public void addEntity(AbstractEntity e){
		entities.add(e);
	}
	
	public Player getPlayer(){
		return this.player;
	}
	
	public void toggleWireframes(){
		drawWireframes = !drawWireframes;
	}
	
}