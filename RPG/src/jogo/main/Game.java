package jogo.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import jogo.entidade.Entity;
import jogo.entidade.Player;
import jogo.grafico.Spritsheet;

public class Game extends Canvas implements Runnable, KeyListener {

	private static final long serialVersionUID = 1L;
	public JFrame jframe;
	private static int width = 160; // altura
	private static int heigth = 120; // tamanho
	private static int scale = 3; // multiplica tudo por 3
	private Thread thread;
	private boolean isRunning = true;
	private BufferedImage fundo;
	private List<Entity> entidades;
	public static Spritsheet sprite;
	private Player player;

	public Game() {
		addKeyListener(this);
		this.setPreferredSize(new Dimension(width * scale, heigth * scale));
		initFrame();
		fundo = new BufferedImage(width, heigth, BufferedImage.TYPE_INT_RGB);
		entidades = new ArrayList<Entity>();
		sprite = new Spritsheet("/spritesheet.png");
		player = new Player(0, 0, 16, 16, sprite.getSprite(32, 0, 16, 16));
		entidades.add(player);

	}

	public void initFrame() {
		jframe = new JFrame("RPGb ");
		jframe.add(this);
		jframe.setResizable(false);
		jframe.pack();
		jframe.setLocationRelativeTo(null);
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setVisible(true);
	}

	public synchronized void start() {
		thread = new Thread(this);
		isRunning = true;
		thread.start();
	}

	public synchronized void stop() {

		isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	public void tick() {
		for (int i = 0; i < entidades.size(); i++) {
			Entity entidade = entidades.get(i);
			entidade.tick();

		}
	}

	public void render() {

		BufferStrategy buffer = this.getBufferStrategy();
		if (buffer == null) {
			this.createBufferStrategy(3);
			return;
		}

		Graphics g = fundo.getGraphics();
		g.setColor(new Color(20, 20, 20));
		g.fillRect(0, 0, width, heigth);

		for (int i = 0; i < entidades.size(); i++) {
			Entity entidade = entidades.get(i);
			entidade.render(g);

		}

		g = buffer.getDrawGraphics();
		g.drawImage(fundo, 0, 0, width * scale, heigth * scale, null);
		buffer.show();

	}

	@Override
	public void run() {
		long lastTime = System.nanoTime();
		double amountOfTick = 60.0f;
		double ms = 1000000000 / amountOfTick;
		double delta = 0;
		int frames = 0;
		double timer = System.currentTimeMillis();
		while (isRunning) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ms;
			lastTime = now;
			if (delta > 1) {
				tick();
				render();
				frames++;
				delta--;
			}
			if (System.currentTimeMillis() - timer >= 1000) {

				System.out.println("FPS: " + frames);
				frames = 0;
				timer += 1000;
			}
		}
		stop();

	}

	public static void main(String[] args) {
		Game game = new Game();
		game.start();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_D) {
			player.right = true;
		} else if (e.getKeyCode() == KeyEvent.VK_A) {
			player.left = true;
		} else if (e.getKeyCode() == KeyEvent.VK_W) {
			player.up = true;
		} else if (e.getKeyCode() == KeyEvent.VK_S) {
			player.down = true;
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode() == KeyEvent.VK_D) {
			player.right = false;
		} else if (e.getKeyCode() == KeyEvent.VK_A) {
			player.left = false;
		} else if (e.getKeyCode() == KeyEvent.VK_W) {
			player.up = false;
		} else if (e.getKeyCode() == KeyEvent.VK_S) {
			player.down = false;
		}
	}

}
