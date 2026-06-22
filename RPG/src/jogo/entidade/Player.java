package jogo.entidade;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import jogo.main.Game;

public class Player extends Entity {

	public boolean right;
	public boolean left;
	public boolean down;
	public boolean up;
	public double speed = 1.5;
	public int direita = 1;
	public int esquerda = 0;
	public int direcaoAtual = direita;
	public int movimentacao = 0;
	public int frames = 0;
	public int maxFrames = 5;
	public int index = 0;
	public int maxIndex = 3;
	public BufferedImage[] playerRight;
	public BufferedImage[] playerLeft;

	public Player(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		playerRight = new BufferedImage[4];
		playerLeft = new BufferedImage[4];
		for (int i = 0; i < 4; i++) {
			playerRight[i] = Game.sprite.getSprite(32 + (i * 16), 0, 16, 16);
		}
		for (int i = 0; i < 4; i++) {
			playerLeft[i] = Game.sprite.getSprite(80 - (i * 16), 16, 16, 16);
		}
		// TODO Auto-generated constructor stub
	}

	public void tick() {
		movimentacao = 0;
		if (right) {
			x += speed;
			movimentacao = 1;
			direcaoAtual = direita;

		}
		if (left) {
			x -= speed;
			movimentacao = 1;
			direcaoAtual = esquerda;
		}
//		if (down) {
//			y += speed;
//		}
//		if (up) {
//			y -= speed;
//		}
		if (movimentacao == 1) {
			frames++;
			if (frames == maxFrames) {
				index++;
				frames = 0;
				if (index > maxIndex) {
					index = 0;
				}
			}
		}
	}

	public void render(Graphics g) {
		if (direcaoAtual == direita && movimentacao == 1) {
			g.drawImage(playerRight[index], this.getX(), this.getY(), null);
		}
		if (direcaoAtual == esquerda && movimentacao == 1) {
			g.drawImage(playerLeft[index], this.getX(), this.getY(), null);
		}
		if (direcaoAtual == direita && movimentacao == 0) {
			g.drawImage(playerRight[0], this.getX(), this.getY(), null);
		}
		if (direcaoAtual == esquerda && movimentacao == 0) {
			g.drawImage(playerLeft[0], this.getX(), this.getY(), null);
		}
	}

}
