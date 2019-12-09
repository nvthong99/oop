package titleGame.entities;

import java.awt.Graphics;
import java.util.ArrayList;

import titleGame.Handler;
import titleGame.entities.creatures.Enemy;
import titleGame.entities.creatures.Player;

public class EntityManager {
	
	private Handler handler;
	private Player player;
	private ArrayList<Entity> entities;
	
	public EntityManager(Handler handler, Player player) {
		this.handler = handler;
		this.player = player;
		entities = new ArrayList<Entity>();
		entities.add(player);
	}
	
	public void tick() {
		for(int i = 0; i < entities.size(); i++) {
			entities.get(i).tick();
			if(!entities.get(i).isActive()) {
				entities.remove(i);
			}
		}
	}
	
	public void render(Graphics g) {
		for(Entity e : entities) {
			e.render(g);
		}
	}
	
	public void addEntity(Entity e) {
		entities.add(e);
	}

	//SETTERS AND GETTERS
	
	public Handler getHandler() {
		return handler;
	}

	public ArrayList<Entity> getEntities() {
		return entities;
	}

	public void setEntities(ArrayList<Entity> entities) {
		this.entities = entities;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
	public ArrayList<Enemy> getEnemy() {
		ArrayList<Enemy> ene = new ArrayList<Enemy>();
		for(int i=0;i<entities.size();i++)
			if(entities.get(i) instanceof Enemy) {
				 ene.add((Enemy) entities.get(i));
			}
		return ene;
	}
	
}
