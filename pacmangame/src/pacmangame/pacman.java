package pacmangame;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class pacman extends JFrame
{
	gamepanel gp;

	pacman()
	{
		this.setTitle("팩맨");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		gp = new gamepanel();
		this.setContentPane(gp);

		this.setSize(720, 850);
		this.setVisible(true);
		this.setResizable(false);
	}

	class gamepanel extends JPanel implements Runnable, KeyListener
	{
		Thread th;
		// 맵
		int map[][] = { { 8, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 9, 8, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 9 },
				{ 4, 1, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4, 4, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4 },
				{ 4, 3, 8, 5, 5, 9, 3, 8, 5, 5, 5, 9, 3, 4, 4, 3, 8, 5, 5, 5, 9, 3, 8, 5, 5, 9, 3, 4 },
				{ 4, 3, 4, 0, 0, 4, 3, 4, 0, 0, 0, 4, 3, 4, 4, 3, 4, 0, 0, 0, 4, 3, 4, 0, 0, 4, 3, 4 },
				{ 4, 3, 6, 5, 5, 7, 3, 6, 5, 5, 5, 7, 3, 6, 7, 3, 6, 5, 5, 5, 7, 3, 6, 5, 5, 7, 3, 4 },
				{ 4, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4 },
				{ 4, 3, 8, 5, 5, 9, 3, 8, 9, 3, 8, 5, 5, 5, 5, 5, 5, 9, 3, 8, 9, 3, 8, 5, 5, 9, 3, 4 },
				{ 4, 3, 6, 5, 5, 7, 3, 4, 4, 3, 6, 5, 5, 9, 8, 5, 5, 7, 3, 4, 4, 3, 6, 5, 5, 7, 3, 4 },
				{ 4, 3, 3, 3, 3, 3, 3, 4, 4, 3, 3, 3, 3, 4, 4, 3, 3, 3, 3, 4, 4, 3, 3, 3, 3, 3, 3, 4 },
				{ 6, 5, 5, 5, 5, 9, 3, 4, 6, 5, 5, 9, 0, 4, 4, 0, 8, 5, 5, 7, 4, 3, 8, 5, 5, 5, 5, 7 },
				{ 0, 0, 0, 0, 0, 4, 3, 4, 8, 5, 5, 7, 0, 6, 7, 0, 6, 5, 5, 9, 4, 3, 4, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 4, 3, 4, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 4, 3, 4, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 4, 3, 4, 4, 0, 8, 5, 5, 0, 0, 5, 5, 9, 0, 4, 4, 3, 4, 0, 0, 0, 0, 0 },
				{ 5, 5, 5, 5, 5, 7, 3, 6, 7, 0, 4, 20, 21, 22, 23, 0, 0, 4, 0, 6, 7, 3, 6, 5, 5, 5, 5, 5 },
				{ 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0 },
				{ 5, 5, 5, 5, 5, 9, 3, 8, 9, 0, 4, 0, 0, 0, 0, 0, 0, 4, 0, 8, 9, 3, 8, 5, 5, 5, 5, 5 },
				{ 0, 0, 0, 0, 0, 4, 3, 4, 4, 0, 6, 5, 5, 5, 5, 5, 5, 7, 0, 4, 4, 3, 4, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 4, 3, 4, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 4, 3, 4, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 4, 3, 4, 4, 0, 8, 5, 5, 5, 5, 5, 5, 9, 0, 4, 4, 3, 4, 0, 0, 0, 0, 0 },
				{ 8, 5, 5, 5, 5, 7, 3, 6, 7, 0, 6, 5, 5, 9, 8, 5, 5, 7, 0, 6, 7, 3, 6, 5, 5, 5, 5, 9 },
				{ 4, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4, 4, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4 },
				{ 4, 3, 8, 5, 5, 9, 3, 8, 5, 5, 5, 9, 3, 4, 4, 3, 8, 5, 5, 5, 9, 3, 8, 5, 5, 9, 3, 4 },
				{ 4, 3, 6, 5, 9, 4, 3, 6, 5, 5, 5, 7, 3, 6, 7, 3, 6, 5, 5, 5, 7, 3, 4, 8, 5, 7, 3, 4 },
				{ 4, 3, 3, 3, 4, 4, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4, 4, 3, 3, 3, 4 },
				{ 6, 5, 9, 3, 4, 4, 3, 8, 9, 3, 8, 5, 5, 5, 5, 5, 5, 9, 3, 8, 9, 3, 4, 4, 3, 8, 5, 7 },
				{ 8, 5, 7, 3, 6, 7, 3, 4, 4, 3, 6, 5, 5, 9, 8, 5, 5, 7, 3, 4, 4, 3, 6, 7, 3, 6, 5, 9 },
				{ 4, 3, 3, 3, 3, 3, 3, 4, 4, 3, 3, 3, 3, 4, 4, 3, 3, 3, 3, 4, 4, 3, 3, 3, 3, 3, 3, 4 },
				{ 4, 3, 8, 5, 5, 5, 5, 7, 6, 5, 5, 9, 3, 4, 4, 3, 8, 5, 5, 7, 6, 5, 5, 5, 5, 9, 3, 4 },
				{ 4, 3, 6, 5, 5, 5, 5, 5, 5, 5, 5, 7, 3, 6, 7, 3, 6, 5, 5, 5, 5, 5, 5, 5, 5, 7, 3, 4 },
				{ 4, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4 },
				{ 6, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 7 } };

		// pacman = 1 food = 3 horizontalline = 5 lowerleft = 6 lowerright = 7
		// upperleft = 8 upperright = 9 verticalline = 4 blinkky = 20(11,15)
		// clyde = 21 lnky = 22 pinky = 23

		Image normalFood = new ImageIcon("data/maze/food/normalFood.png").getImage(); // 수평벽
		Image horizontalline = new ImageIcon("data/maze/horizontalLine.png").getImage(); // 수평벽
		Image lowerleft = new ImageIcon("data/maze/lowerLeft.png").getImage(); // 좌하단
		Image lowerright = new ImageIcon("data/maze/lowerRight.png").getImage(); // 우하단
		Image upperleft = new ImageIcon("data/maze/upperLeft.png").getImage(); // 좌상단
		Image upperright = new ImageIcon("data/maze/upperRight.png").getImage(); // 좌상단
		Image verticalline = new ImageIcon("data/maze/verticalLine.png").getImage(); // 수직벽

		// 오른쪽 움직임 배열
		Image right[] = { new ImageIcon("data/pacman/right/Pacman_right-0.png").getImage(),
				new ImageIcon("data/pacman/right/Pacman_right-1.png").getImage(),
				new ImageIcon("data/pacman/right/Pacman_right-2.png").getImage(),
				new ImageIcon("data/pacman/right/Pacman_right-3.png").getImage() };

		// 왼쪽 움직임 배열
		Image left[] = { new ImageIcon("data/pacman/left/Pacman_left-0.png").getImage(),
				new ImageIcon("data/pacman/left/Pacman_left-1.png").getImage(),
				new ImageIcon("data/pacman/left/Pacman_left-2.png").getImage(),
				new ImageIcon("data/pacman/left/Pacman_left-3.png").getImage() };

		// 아래 움직임 배열
		Image down[] = { new ImageIcon("data/pacman/down/Pacman_down-0.png").getImage(),
				new ImageIcon("data/pacman/down/Pacman_down-1.png").getImage(),
				new ImageIcon("data/pacman/down/Pacman_down-2.png").getImage(),
				new ImageIcon("data/pacman/down/Pacman_down-3.png").getImage() };

		// 위 움직임 배열
		Image up[] = { new ImageIcon("data/pacman/up/Pacman_up-0.png").getImage(),
				new ImageIcon("data/pacman/up/Pacman_up-1.png").getImage(),
				new ImageIcon("data/pacman/up/Pacman_up-2.png").getImage(),
				new ImageIcon("data/pacman/up/Pacman_up-3.png").getImage() };

		// 괴물
		Image blinky[] = { new ImageIcon("data/ghosts/Blinky/Blinky-0.png").getImage(),
				new ImageIcon("data/ghosts/Blinky/Blinky-1.png").getImage() };
		Image Clyde[] = { new ImageIcon("data/ghosts/Clyde/Clyde-0.png").getImage(),
				new ImageIcon("data/ghosts/Clyde/Clyde-1.png").getImage() };
		Image lnky[] = { new ImageIcon("data/ghosts/Inky/Inky-0.png").getImage(),
				new ImageIcon("data/ghosts/Inky/Inky-1.png").getImage() };
		Image Pinky[] = { new ImageIcon("data/ghosts/Pinky/Pinky-0.png").getImage(),
				new ImageIcon("data/ghosts/Pinky/Pinky-1.png").getImage() };

		int movecount = 0; // 움직임 카운트

		int score = 0; // 게임 점수

		int px = 1;
		int py = 1; // 팩맨 시작위치

		int monstercount = 0;

		// 괴물들 시작위치
		int bx = 11; // blinky
		int by = 13;
		int cx = 12; // clyde
		int cy = 13;
		int lx = 13; // linky
		int ly = 13;
		int pix = 14; // pinky
		int piy = 13;

		int pingcycle = 0;
		int pingcycle2 = 0;
		int pingbubble = 0; // 핑키가 동전먹은지 확인

		int clydecycle = 0;

		int lycycle = 0;
		int lycycle2 = 0;

		int blinkycycle = 0;

		// 팩맨 애니메이션 값 정해서 값에 따라 모습바꾸기
		int anipac = 11; // 오른쪽 11 왼쪽 12 아래 13 위 14

		JLabel gameover = new JLabel("GAME OVER!!");
		JLabel gamestart = new JLabel("PLEASE ENTER!!");
		JLabel gamewin = new JLabel("GAME WIN!!");

		int wincount = 0;

		int timer = 150; // 스레드시간
		int monsterorder; // 몬스터나가는순서

		int pinkytempfood = 0; // 괴물이 음식을 먹었는지 확인
		int lytempfood = 0;
		int clydetempfood = 0;
		int blinkytempfood = 0;

		public gamepanel()
		{
			this.setLayout(null);

			// 게임오버 패널
			gameover.setForeground(Color.white);
			gameover.setFont(new Font("Gothic", 30, 30));
			gameover.setLocation(250, 420);
			gameover.setSize(200, 30);
			gameover.setOpaque(false);
			gameover.setVisible(false);

			gamewin.setForeground(Color.white);
			gamewin.setFont(new Font("Gothic", 30, 30));
			gamewin.setLocation(250, 420);
			gamewin.setSize(200, 30);
			gamewin.setOpaque(false);
			gamewin.setVisible(false);

			gamestart.setForeground(Color.white);
			gamestart.setFont(new Font("Gothic", 25, 25));
			gamestart.setLocation(250, 420);
			gamestart.setSize(250, 30);
			gamestart.setOpaque(false);
			gamestart.setVisible(true);

			this.add(gamestart);
			this.add(gameover);
			this.add(gamewin);

			this.addKeyListener(this);
			this.setFocusable(true);
		}

		public void paintComponent(Graphics g)
		{
			g.setColor(Color.black);
			g.fillRect(0, 0, 710, 900);

			g.setFont(new Font("gothic", 30, 30));
			g.setColor(Color.white);
			g.drawString("SCORE " + score, 10, 800);
			g.drawString("STRAT ENTER, 1 LIFE ", 370, 800);

			for (int i = 0; i < map.length; i++)
			{
				for (int j = 0; j < map[0].length; j++)
				{
					switch (map[i][j]) {
						case 1: // 팩맨 움직이기
							if (anipac == 11)
								g.drawImage(right[movecount], j * 25, i * 25, this);
							else if (anipac == 12)
								g.drawImage(left[movecount], j * 25, i * 25, this);
							else if (anipac == 13)
								g.drawImage(down[movecount], j * 25, i * 25, this);
							else if (anipac == 14)
								g.drawImage(up[movecount], j * 25, i * 25, this);
							break;
						case 3: // 음식
							g.drawImage(normalFood, j * 25, i * 25, this);
							break;
						case 4: // 수직벽
							g.drawImage(verticalline, j * 25, i * 25, this);
							break;
						case 5: // 수평벽
							g.drawImage(horizontalline, j * 25, i * 25, this);
							break;
						case 6: // 좌하단
							g.drawImage(lowerleft, j * 25, i * 25, this);
							break;
						case 7: // 우하단
							g.drawImage(lowerright, j * 25, i * 25, this);
							break;
						case 8: // 좌상단
							g.drawImage(upperleft, j * 25, i * 25, this);
							break;
						case 9: // 우상단
							g.drawImage(upperright, j * 25, i * 25, this);
							break;
						case 20:
							g.drawImage(blinky[monstercount], j * 25, i * 25, this);
							break;
						case 21:
							g.drawImage(Clyde[monstercount], j * 25, i * 25, this);
							break;
						case 22:
							g.drawImage(lnky[monstercount], j * 25, i * 25, this);
							break;
						case 23:
							g.drawImage(Pinky[monstercount], j * 25, i * 25, this);
							break;

					}
				}
			}

			repaint();
		}

		public void run()
		{
			monsterorder = timer; // 몬스터 나가는 순서
			while (true)
			{
				try
				{
					Thread.sleep(timer);

					// 팩맨 움직임변화 배열이 4여서 3까지가면 0으로 돌아오기
					movecount++;
					if (movecount == 3)
					{
						movecount = 0;
					}

					monsterorder += timer;

					// 괴물 움직임변화 쓰레드
					if (monstercount == 0)
						monstercount = 1;
					else if (monstercount == 1)
					{
						monstercount = 0;
					}

					// 괴물 흩어져서 구역 돌기
					// 몬스터 나가는 순서
					// 시작하자마자 나가서 1초마다 한마리씩
					if (monsterorder >= 250)
					{
						pingkyscatter();
					}
					if (monsterorder >= 1000)
					{
						lyscatter();
					}
					if (monsterorder >= 2000)
					{
						clydescatter();
					}
					if (monsterorder >= 3000)
					{
						blinkyscatter();
					}

					if (timer >= 75)
						timer = timer - (monsterorder / 30000);

					// 괴물 추적하기

					gamewin(); // 이겼는지 검사

				} catch (InterruptedException e)
				{

				}
				repaint();
			}
		}

		public void keyPressed(KeyEvent arg0)
		{
			// 엔터치면 게임시작
			if (arg0.getKeyCode() == KeyEvent.VK_ENTER)
			{
				gamestart.setVisible(false);
				th = new Thread(this);
				th.start();
			}

			if (arg0.getKeyCode() == KeyEvent.VK_RIGHT)
			{
				// 바닥이 동전 일 때랑 그냥 바닥일 경우만 이동가능
				// 동전먹으면 10점
				if (map[py][px + 1] == 3)
				{
					score += 10;
					anipac = 11; // 애니메이션 번호 정하기
					map[py][px] = 0; // 원래있던 자리 그냥바닥
					map[py][px + 1] = 1; // x값증가하여 오른쪽 이동
					px++;
				} else if (map[py][px + 1] == 0)
				{
					anipac = 11; // 애니메이션 번호 정하기
					map[py][px] = 0; // 원래있던 자리 그냥바닥
					map[py][px + 1] = 1; // x값증가하여 오른쪽 이동
					px++;
				}
				// 괴물과 닿았을 경우 게임오버
				else if (map[py][px + 1] == 20 || map[py][px + 1] == 21 || map[py][px + 1] == 22
						|| map[py][px + 1] == 23)
				{
					gameover.setVisible(true);
				}

			} else if (arg0.getKeyCode() == KeyEvent.VK_LEFT)
			{
				if (map[py][px - 1] == 3)
				{
					score += 10; // 동전먹으면 10점
					anipac = 12;
					map[py][px] = 0; // 원래있던 자리 그냥바닥
					map[py][px - 1] = 1; // x값 감소하여 왼쪽 이동
					px--;
				} else if (map[py][px - 1] == 0)
				{
					anipac = 12;
					map[py][px] = 0; // 원래있던 자리 그냥바닥
					map[py][px - 1] = 1; // x값 감소하여 왼쪽 이동
					px--;
				} else if (map[py][px - 1] == 20 || map[py][px - 1] == 21 || map[py][px - 1] == 22
						|| map[py][px - 1] == 23)
				{
					gameover.setVisible(true);
				}
			} else if (arg0.getKeyCode() == KeyEvent.VK_UP)
			{
				if (map[py - 1][px] == 3)
				{
					score += 10; // 동전먹으면 10점
					anipac = 14;
					map[py][px] = 0; // 원래있던 자리 그냥바닥
					map[py - 1][px] = 1; // y값 감소하여 위로 이동
					py--;
				} else if (map[py - 1][px] == 0)
				{
					anipac = 14;
					map[py][px] = 0; // 원래있던 자리 그냥바닥
					map[py - 1][px] = 1; // y값 감소하여 위로 이동
					py--;
				} else if (map[py - 1][px] == 20 || map[py - 1][px] == 21 || map[py - 1][px] == 22
						|| map[py - 1][px] == 23)
				{
					gameover.setVisible(true);
				}
			} else if (arg0.getKeyCode() == KeyEvent.VK_DOWN)
			{
				if (map[py + 1][px] == 3)
				{
					score += 10; // 동전먹으면 10점
					anipac = 13;
					map[py][px] = 0; // 원래있던 자리 그냥바닥
					map[py + 1][px] = 1; // y값 증가하여 아래로 이동
					py++;
				} else if (map[py + 1][px] == 0)
				{
					anipac = 13;
					map[py][px] = 0; // 원래있던 자리 그냥바닥
					map[py + 1][px] = 1; // y값 증가하여 아래로 이동
					py++;
				} else if (map[py + 1][px] == 20 || map[py + 1][px] == 21 || map[py + 1][px] == 22
						|| map[py + 1][px] == 23)
				{
					gameover.setVisible(true);
				}
			}
		}

		@Override
		public void keyReleased(KeyEvent arg0)
		{
			// TODO Auto-generated method stub

		}

		@Override
		public void keyTyped(KeyEvent arg0)
		{
			// TODO Auto-generated method stub

		}

		// 괴물 움직이기
		public void pingkyscatter()
		{
			// 핑키 움직이기
			// 위
			if (pingcycle == 0 && map[piy - 1][pix] == 3 || pingcycle == 0 && map[piy - 1][pix] == 0
					|| map[piy - 1][pix] == 1)
			{
				// 음식 있는지 확인하는 코드
				if (pinkytempfood == 0)
				{
					map[piy][pix] = 0; // 원래있던 자리 그냥바닥
				} else
				{
					map[piy][pix] = 3; // 원래 있던 자리 음식바닥
				}
				if (map[piy - 1][pix] == 3)
				{
					pinkytempfood = 1;
				} else if (map[piy - 1][pix] == 0)
				{
					pinkytempfood = 0;
				}

				if (map[piy - 1][pix] == 1)
					gameover.setVisible(true);
				map[piy - 1][pix] = 23; // y값 감소하여 위로 이동
				piy--;
				pingcycle2 = 0;
			}
			// 왼쪽
			else if (pingcycle2 == 0 && map[piy][pix - 1] == 3 || pingcycle2 == 0 && map[piy][pix - 1] == 0
					|| map[piy][pix - 1] == 1)
			{
				if (pinkytempfood == 0)
				{
					map[piy][pix] = 0; // 원래있던 자리 그냥바닥
				} else
				{
					map[piy][pix] = 3; // 원래 있던 자리 음식바닥
				}
				if (map[piy][pix - 1] == 3)
				{
					pinkytempfood = 1;
				} else if (map[piy][pix - 1] == 0)
				{
					pinkytempfood = 0;
				}

				if (map[piy][pix - 1] == 1)
					gameover.setVisible(true);
				map[piy][pix - 1] = 23; // y값 감소하여 위로 이동
				pix--;
				pingcycle = 0;
			}
			// 아래
			else if (map[piy + 1][pix] == 3 || map[piy + 1][pix] == 0 || map[piy + 1][pix] == 1)
			{
				if (pinkytempfood == 0)
				{
					map[piy][pix] = 0; // 원래있던 자리 그냥바닥
				} else
				{
					map[piy][pix] = 3; // 원래 있던 자리 음식바닥
				}
				if (map[piy + 1][pix] == 3)
				{
					pinkytempfood = 1;
				} else if (map[piy + 1][pix] == 0)
				{
					pinkytempfood = 0;
				}
				if (map[piy + 1][pix] == 1)
					gameover.setVisible(true);

				map[piy + 1][pix] = 23; // y값 감소하여 위로 이동
				piy++;
				pingcycle = 1;
				pingcycle2 = 1;
			}
			// 오른쪽
			else if (map[piy][pix + 1] == 3 || map[piy][pix + 1] == 0 || map[piy][pix + 1] == 1)
			{
				if (pinkytempfood == 0)
				{
					map[piy][pix] = 0; // 원래있던 자리 그냥바닥
				} else
				{
					map[piy][pix] = 3; // 원래 있던 자리 음식바닥
				}
				if (map[piy][pix + 1] == 3)
				{
					pinkytempfood = 1;
				} else if (map[piy][pix + 1] == 0)
				{
					pinkytempfood = 0;
				}
				if (map[piy][pix + 1] == 1)
					gameover.setVisible(true);
				map[piy][pix + 1] = 23; // y값 감소하여 위로 이동
				pix++;
				pingcycle2 = 1;
				pingcycle = 0;
			}
		}

		public void lyscatter()
		{
			// 링키 움직이기
			// 위
			if (lycycle == 0 && map[ly - 1][lx] == 3 || lycycle == 0 && map[ly - 1][lx] == 0 || map[ly - 1][lx] == 1)
			{
				if (lytempfood == 0)
				{
					map[ly][lx] = 0; // 원래있던 자리 그냥바닥
				} else
				{
					map[ly][lx] = 3; // 원래 있던 자리 음식바닥
				}
				if (map[ly - 1][lx] == 3)
				{
					lytempfood = 1;
				} else if (map[ly - 1][lx] == 0)
				{
					lytempfood = 0;
				}

				if (map[ly - 1][lx] == 1)
					gameover.setVisible(true);

				map[ly - 1][lx] = 22; // y값 감소하여 위로 이동
				ly--;
				lycycle2 = 0;
			}
			// 오른쪽
			else if (lycycle2 == 0 && map[ly][lx + 1] == 3 || lycycle2 == 0 && map[ly][lx + 1] == 0
					|| map[ly][lx + 1] == 1)
			{
				if (lytempfood == 0)
				{
					map[ly][lx] = 0; // 원래있던 자리 그냥바닥
				} else
				{
					map[ly][lx] = 3; // 원래 있던 자리 음식바닥
				}
				if (map[ly][lx + 1] == 3)
				{
					lytempfood = 1;
				} else if (map[ly][lx + 1] == 0)
				{
					lytempfood = 0;
				}

				if (map[ly][lx + 1] == 1)
					gameover.setVisible(true);

				map[ly][lx + 1] = 22; // y값 감소하여 위로 이동
				lx++;
				lycycle = 0;
			}
			// 아래
			else if (map[ly + 1][lx] == 3 || map[ly + 1][lx] == 0 || map[ly + 1][lx] == 1)
			{
				if (lytempfood == 0)
				{
					map[ly][lx] = 0; // 원래있던 자리 그냥바닥
				} else
				{
					map[ly][lx] = 3; // 원래 있던 자리 음식바닥
				}
				if (map[ly + 1][lx] == 3)
				{
					lytempfood = 1;
				} else if (map[ly + 1][lx] == 0)
				{
					lytempfood = 0;
				}
				if (map[ly + 1][lx] == 1)
					gameover.setVisible(true);

				map[ly + 1][lx] = 22; // y값 감소하여 위로 이동
				ly++;
				lycycle = 1;
				lycycle2 = 1;
			}
			// 왼쪽
			else if (map[ly][lx - 1] == 3 || map[ly][lx - 1] == 0 || map[ly][lx - 1] == 1)
			{
				if (lytempfood == 0)
				{
					map[ly][lx] = 0; // 원래있던 자리 그냥바닥
				} else
				{
					map[ly][lx] = 3; // 원래 있던 자리 음식바닥
				}
				if (map[ly][lx - 1] == 3)
				{
					lytempfood = 1;
				} else if (map[ly][lx - 1] == 0)
				{
					lytempfood = 0;
				}
				if (map[ly][lx - 1] == 1)
					gameover.setVisible(true);

				map[ly][lx - 1] = 22; // y값 감소하여 위로 이동
				lx--;
				lycycle = 0;
				lycycle2 = 1;
			}
		}

		public void clydescatter()
		{
			if (clydecycle == 0) // 밟으면 오른쪽
			{
				map[cy][cx] = 0; // 원래있던 자리 그냥바닥
				map[cy][cx + 1] = 21; // y값 감소하여 위로 이동
				cx++;
			}
			if (cy == 13 && cx == 14 || clydecycle == 1 || map[cy - 1][cx] == 1) // 밟으면
																					// 위로
			{
				if (clydetempfood == 0)
				{
					map[cy][cx] = 0; // 원래있던 자리 그냥바닥
				} else
				{
					map[cy][cx] = 3; // 원래 있던 자리 음식바닥
				}
				if (map[cy - 1][cx] == 3)
				{
					clydetempfood = 1;
				} else if (map[cy - 1][cx] == 0)
				{
					clydetempfood = 0;
				}

				if (map[cy - 1][cx] == 1)
					gameover.setVisible(true);

				map[cy - 1][cx] = 21; // y값 감소하여 위로 이동
				cy--;
				clydecycle = 1;
			}
			if (cy == 11 && cx == 14 || clydecycle == 2 || map[cy][cx + 1] == 1) // 밟으면
																					// 오른쪽
			{
				if (clydetempfood == 0)
				{
					map[cy][cx] = 0; // 원래있던 자리 그냥바닥
				} else
				{
					map[cy][cx] = 3; // 원래 있던 자리 음식바닥
				}
				if (map[cy][cx + 1] == 3)
				{
					clydetempfood = 1;
				} else if (map[cy][cx + 1] == 0)
				{
					clydetempfood = 0;
				}
				if (map[cy][cx + 1] == 1)
					gameover.setVisible(true);

				map[cy][cx + 1] = 21; // y값 감소하여 위로 이동
				cx++;
				clydecycle = 2;
			}
			if (cy == 11 && cx == 18 || clydecycle == 3 || map[cy + 1][cx] == 1) // 밟으면
																					// 아래
			{
				if (clydetempfood == 0)
				{
					map[cy][cx] = 0; // 원래있던 자리 그냥바닥
				} else
				{
					map[cy][cx] = 3; // 원래 있던 자리 음식바닥
				}
				if (map[cy + 1][cx] == 3)
				{
					clydetempfood = 1;
				} else if (map[cy + 1][cx] == 0)
				{
					clydetempfood = 0;
				}
				if (map[cy + 1][cx] == 1)
					gameover.setVisible(true);
				map[cy + 1][cx] = 21; // y값 감소하여 위로 이동
				cy++;
				clydecycle = 3;
			}
			if (cy == 20 && cx == 18 || clydecycle == 4 || map[cy][cx + 1] == 1) // 밟으면
																					// 오른쪽
			{
				if (clydetempfood == 0)
				{
					map[cy][cx] = 0; // 원래있던 자리 그냥바닥
				} else
				{
					map[cy][cx] = 3; // 원래 있던 자리 음식바닥
				}
				if (map[cy][cx + 1] == 3)
				{
					clydetempfood = 1;
				} else if (map[cy][cx + 1] == 0)
				{
					clydetempfood = 0;
				}
				if (map[cy][cx + 1] == 1)
					gameover.setVisible(true);

				map[cy][cx + 1] = 21; // y값 감소하여 위로 이동
				cx++;
				clydecycle = 4;
			}
			if (cy == 20 && cx == 21 || clydecycle == 5 || map[cy + 1][cx] == 1) // 밟으면
																					// 아래
			{
				if (clydetempfood == 0)
				{
					map[cy][cx] = 0; // 원래있던 자리 그냥바닥
				} else
				{
					map[cy][cx] = 3; // 원래 있던 자리 음식바닥
				}
				if (map[cy + 1][cx] == 3)
				{
					clydetempfood = 1;
				} else if (map[cy + 1][cx] == 0)
				{
					clydetempfood = 0;
				}
				if (map[cy + 1][cx] == 1)
					gameover.setVisible(true);

				map[cy + 1][cx] = 21; // y값 감소하여 위로 이동
				cy++;
				clydecycle = 5;
			}
			if (cy == 26 && cx == 21 || clydecycle == 6 || map[cy][cx + 1] == 1) // 밟으면
																					// 오른쪽
			{
				if (clydetempfood == 0)
				{
					map[cy][cx] = 0; // 원래있던 자리 그냥바닥
				} else
				{
					map[cy][cx] = 3; // 원래 있던 자리 음식바닥
				}
				if (map[cy][cx + 1] == 3)
				{
					clydetempfood = 1;
				} else if (map[cy][cx + 1] == 0)
				{
					clydetempfood = 0;
				}
				if (map[cy][cx + 1] == 1)
					gameover.setVisible(true);

				map[cy][cx + 1] = 21; // y값 감소하여 위로 이동
				cx++;
				clydecycle = 6;
			}
			if (cy == 26 && cx == 26 || clydecycle == 7 || map[cy + 1][cx] == 1) // 밟으면
																					// 아래
			{
				if (clydetempfood == 0)
				{
					map[cy][cx] = 0; // 원래있던 자리 그냥바닥
				} else
				{
					map[cy][cx] = 3; // 원래 있던 자리 음식바닥
				}
				if (map[cy + 1][cx] == 3)
				{
					clydetempfood = 1;
				} else if (map[cy + 1][cx] == 0)
				{
					clydetempfood = 0;
				}
				if (map[cy + 1][cx] == 1)
					gameover.setVisible(true);

				map[cy + 1][cx] = 21; // y값 감소하여 위로 이동
				cy++;
				clydecycle = 7;
			}
			if (cy == 29 && cx == 26 || clydecycle == 8 || map[cy][cx - 1] == 1) // 밟으면
																					// 왼쪽
			{
				if (clydetempfood == 0)
				{
					map[cy][cx] = 0; // 원래있던 자리 그냥바닥
				} else
				{
					map[cy][cx] = 3; // 원래 있던 자리 음식바닥
				}
				if (map[cy][cx - 1] == 3)
				{
					clydetempfood = 1;
				} else if (map[cy][cx - 1] == 0)
				{
					clydetempfood = 0;
				}
				if (map[cy][cx - 1] == 1)
					gameover.setVisible(true);

				map[cy][cx - 1] = 21; // y값 감소하여 위로 이동
				cx--;
				clydecycle = 8;
			}
			if (cy == 29 && cx == 15 || clydecycle == 9 || map[cy - 1][cx] == 1) // 밟으면
																					// 위로
			{
				if (clydetempfood == 0)
				{
					map[cy][cx] = 0; // 원래있던 자리 그냥바닥
				} else
				{
					map[cy][cx] = 3; // 원래 있던 자리 음식바닥
				}
				if (map[cy - 1][cx] == 3)
				{
					clydetempfood = 1;
				} else if (map[cy - 1][cx] == 0)
				{
					clydetempfood = 0;
				}
				if (map[cy - 1][cx] == 1)
					gameover.setVisible(true);
				map[cy - 1][cx] = 21; // y값 감소하여 위로 이동
				cy--;
				clydecycle = 9;
			}
			if (cy == 26 && cx == 15 || clydecycle == 10 || map[cy][cx + 1] == 1) // 밟으면
																					// 오른쪽
			{
				if (clydetempfood == 0)
				{
					map[cy][cx] = 0; // 원래있던 자리 그냥바닥
				} else
				{
					map[cy][cx] = 3; // 원래 있던 자리 음식바닥
				}
				if (map[cy][cx + 1] == 3)
				{
					clydetempfood = 1;
				} else if (map[cy][cx + 1] == 0)
				{
					clydetempfood = 0;
				}

				if (map[cy][cx + 1] == 1)
					gameover.setVisible(true);
				map[cy][cx + 1] = 21; // y값 감소하여 위로 이동
				cx++;
				clydecycle = 10;
			}
			if (cy == 26 && cx == 18 || clydecycle == 11 || map[cy - 1][cx] == 1) // 밟으면
																					// 위
			{
				if (clydetempfood == 0)
				{
					map[cy][cx] = 0; // 원래있던 자리 그냥바닥
				} else
				{
					map[cy][cx] = 3; // 원래 있던 자리 음식바닥
				}
				if (map[cy - 1][cx] == 3)
				{
					clydetempfood = 1;
				} else if (map[cy - 1][cx] == 0)
				{
					clydetempfood = 0;
				}
				if (map[cy - 1][cx] == 1)
					gameover.setVisible(true);
				map[cy - 1][cx] = 21; // y값 감소하여 위로 이동
				cy--;
				clydecycle = 11;
			}
			if (cy == 23 && cx == 18 || clydecycle == 12 || map[cy][cx - 1] == 1) // 밟으면
																					// 왼쪽
			{
				if (clydetempfood == 0)
				{
					map[cy][cx] = 0; // 원래있던 자리 그냥바닥
				} else
				{
					map[cy][cx] = 3; // 원래 있던 자리 음식바닥
				}
				if (map[cy][cx - 1] == 3)
				{
					clydetempfood = 1;
				} else if (map[cy][cx - 1] == 0)
				{
					clydetempfood = 0;
				}
				if (map[cy][cx - 1] == 1)
					gameover.setVisible(true);

				map[cy][cx - 1] = 21; // y값 감소하여 위로 이동
				cx--;
				clydecycle = 12;
			}
			if (cy == 23 && cx == 15 || clydecycle == 13 || map[cy - 1][cx] == 1) // 밟으면
																					// 위
			{
				if (clydetempfood == 0)
				{
					map[cy][cx] = 0; // 원래있던 자리 그냥바닥
				} else
				{
					map[cy][cx] = 3; // 원래 있던 자리 음식바닥
				}
				if (map[cy - 1][cx] == 3)
				{
					clydetempfood = 1;
				} else if (map[cy - 1][cx] == 0)
				{
					clydetempfood = 0;
				}

				if (map[cy - 1][cx] == 1)
					gameover.setVisible(true);

				map[cy - 1][cx] = 21; // y값 감소하여 위로 이동
				cy--;
				clydecycle = 13;
			}
			if (cy == 20 && cx == 15 || clydecycle == 14 || map[cy][cx + 1] == 1) // 밟으면
																					// 오른쪽
			{
				if (clydetempfood == 0)
				{
					map[cy][cx] = 0; // 원래있던 자리 그냥바닥
				} else
				{
					map[cy][cx] = 3; // 원래 있던 자리 음식바닥
				}
				if (map[cy][cx + 1] == 3)
				{
					clydetempfood = 1;
				} else if (map[cy][cx + 1] == 0)
				{
					clydetempfood = 0;
				}
				if (map[cy][cx + 1] == 1)
					gameover.setVisible(true);

				map[cy][cx + 1] = 21; // y값 감소하여 위로 이동
				cx++;
				clydecycle = 14;
			}
		}

		public void blinkyscatter()
		{
			if (blinkycycle == 0) // 밟으면 오른쪽
			{
				map[by][bx] = 0; // 원래있던 자리 그냥바닥
				map[by][bx + 1] = 20; // y값 감소하여 위로 이동
				bx++;
			}
			if (by == 13 && bx == 13 || blinkycycle == 1 || map[by - 1][bx] == 1) // 밟으면
																					// 위로
			{
				if (blinkytempfood == 0)
				{
					map[by][bx] = 0; // 원래있던 자리 그냥바닥
				} else
				{
					map[by][bx] = 3; // 원래 있던 자리 음식바닥
				}
				if (map[by - 1][bx] == 3)
				{
					blinkytempfood = 1;
				} else if (map[by - 1][bx] == 0)
				{
					blinkytempfood = 0;
				}
				if (map[by - 1][bx] == 1)
					gameover.setVisible(true);

				map[by - 1][bx] = 20; // y값 감소하여 위로 이동
				by--;
				blinkycycle = 1;
			}
			if (by == 11 && bx == 13 || blinkycycle == 2 || map[by][bx - 1] == 1) // 밟으면
																					// 왼쪽
			{
				if (blinkytempfood == 0)
				{
					map[by][bx] = 0; // 원래있던 자리 그냥바닥
				} else
				{
					map[by][bx] = 3; // 원래 있던 자리 음식바닥
				}
				if (map[by][bx - 1] == 3)
				{
					blinkytempfood = 1;
				} else if (map[by][bx - 1] == 0)
				{
					blinkytempfood = 0;
				}
				if (map[by][bx - 1] == 1)
					gameover.setVisible(true);

				map[by][bx - 1] = 20; // y값 감소하여 위로 이동
				bx--;
				blinkycycle = 2;
			}
			if (by == 11 && bx == 9 || blinkycycle == 3 || map[by + 1][bx] == 1) // 밟으면
																					// 아래
			{
				if (blinkytempfood == 0)
				{
					map[by][bx] = 0; // 원래있던 자리 그냥바닥
				} else
				{
					map[by][bx] = 3; // 원래 있던 자리 음식바닥
				}
				if (map[by + 1][bx] == 3)
				{
					blinkytempfood = 1;
				} else if (map[by + 1][bx] == 0)
				{
					blinkytempfood = 0;
				}
				if (map[by + 1][bx] == 1)
					gameover.setVisible(true);

				map[by + 1][bx] = 20; // y값 감소하여 위로 이동
				by++;
				blinkycycle = 3;
			}
			if (by == 20 && bx == 9 || blinkycycle == 4 || map[by][bx + 1] == 1) // 밟으면
																					// 오른쪽
			{
				if (blinkytempfood == 0)
				{
					map[by][bx] = 0; // 원래있던 자리 그냥바닥
				} else
				{
					map[by][bx] = 3; // 원래 있던 자리 음식바닥
				}
				if (map[by][bx + 1] == 3)
				{
					blinkytempfood = 1;
				} else if (map[by][bx + 1] == 0)
				{
					blinkytempfood = 0;
				}
				if (map[by][bx + 1] == 1)
					gameover.setVisible(true);

				map[by][bx + 1] = 20; // y값 감소하여 위로 이동
				bx++;
				blinkycycle = 4;
			}
			if (by == 20 && bx == 12 || blinkycycle == 5 || map[by + 1][bx] == 1) // 밟으면
																					// 아래
			{
				if (blinkytempfood == 0)
				{
					map[by][bx] = 0; // 원래있던 자리 그냥바닥
				} else
				{
					map[by][bx] = 3; // 원래 있던 자리 음식바닥
				}
				if (map[by + 1][bx] == 3)
				{
					blinkytempfood = 1;
				} else if (map[by + 1][bx] == 0)
				{
					blinkytempfood = 0;
				}
				if (map[by + 1][bx] == 1)
					gameover.setVisible(true);

				map[by + 1][bx] = 20; // y값 감소하여 위로 이동
				by++;
				blinkycycle = 5;
			}
			if (by == 23 && bx == 12 || blinkycycle == 6 || map[by][bx - 1] == 1) // 밟으면
																					// 왼쪽
			{
				if (blinkytempfood == 0)
				{
					map[by][bx] = 0; // 원래있던 자리 그냥바닥
				} else
				{
					map[by][bx] = 3; // 원래 있던 자리 음식바닥
				}
				if (map[by][bx - 1] == 3)
				{
					blinkytempfood = 1;
				} else if (map[by][bx - 1] == 0)
				{
					blinkytempfood = 0;
				}
				if (map[by][bx - 1] == 1)
					gameover.setVisible(true);
				map[by][bx - 1] = 20; // y값 감소하여 위로 이동
				bx--;
				blinkycycle = 6;
			}
			if (by == 23 && bx == 9 || blinkycycle == 7 || map[by + 1][bx] == 1) // 밟으면
																					// 아래
			{
				if (blinkytempfood == 0)
				{
					map[by][bx] = 0; // 원래있던 자리 그냥바닥
				} else
				{
					map[by][bx] = 3; // 원래 있던 자리 음식바닥
				}
				if (map[by + 1][bx] == 3)
				{
					blinkytempfood = 1;
				} else if (map[by + 1][bx] == 0)
				{
					blinkytempfood = 0;
				}

				if (map[by + 1][bx] == 1)
					gameover.setVisible(true);

				map[by + 1][bx] = 20; // y값 감소하여 위로 이동
				by++;
				blinkycycle = 7;
			}
			if (by == 26 && bx == 9 || blinkycycle == 8 || map[by][bx + 1] == 1) // 밟으면
																					// 오른쪽
			{
				if (blinkytempfood == 0)
				{
					map[by][bx] = 0; // 원래있던 자리 그냥바닥
				} else
				{
					map[by][bx] = 3; // 원래 있던 자리 음식바닥
				}
				if (map[by][bx + 1] == 3)
				{
					blinkytempfood = 1;
				} else if (map[by][bx + 1] == 0)
				{
					blinkytempfood = 0;
				}
				if (map[by][bx + 1] == 1)
					gameover.setVisible(true);

				map[by][bx + 1] = 20; // y값 감소하여 위로 이동
				bx++;
				blinkycycle = 8;
			}
			if (by == 26 && bx == 12 || blinkycycle == 9 || map[by + 1][bx] == 1) // 밟으면
																					// 아래
			{
				if (blinkytempfood == 0)
				{
					map[by][bx] = 0; // 원래있던 자리 그냥바닥
				} else
				{
					map[by][bx] = 3; // 원래 있던 자리 음식바닥
				}
				if (map[by + 1][bx] == 3)
				{
					blinkytempfood = 1;
				} else if (map[by + 1][bx] == 0)
				{
					blinkytempfood = 0;
				}

				if (map[by + 1][bx] == 1)
					gameover.setVisible(true);

				map[by + 1][bx] = 20; // y값 감소하여 위로 이동
				by++;
				blinkycycle = 9;
			}
			if (by == 29 && bx == 12 || blinkycycle == 10 || map[by][bx - 1] == 1) // 밟으면
																					// 왼쪽
			{
				if (blinkytempfood == 0)
				{
					map[by][bx] = 0; // 원래있던 자리 그냥바닥
				} else
				{
					map[by][bx] = 3; // 원래 있던 자리 음식바닥
				}
				if (map[by][bx - 1] == 3)
				{
					blinkytempfood = 1;
				} else if (map[by][bx - 1] == 0)
				{
					blinkytempfood = 0;
				}
				if (map[by][bx - 1] == 1)
					gameover.setVisible(true);

				map[by][bx - 1] = 20; // y값 감소하여 위로 이동
				bx--;
				blinkycycle = 10;
			}
			if (by == 29 && bx == 1 || blinkycycle == 11 || map[by - 1][bx] == 1) // 밟으면
																					// 위
			{
				if (blinkytempfood == 0)
				{
					map[by][bx] = 0; // 원래있던 자리 그냥바닥
				} else
				{
					map[by][bx] = 3; // 원래 있던 자리 음식바닥
				}
				if (map[by - 1][bx] == 3)
				{
					blinkytempfood = 1;
				} else if (map[by - 1][bx] == 0)
				{
					blinkytempfood = 0;
				}

				if (map[by - 1][bx] == 1)
					gameover.setVisible(true);

				map[by - 1][bx] = 20; // y값 감소하여 위로 이동
				by--;
				blinkycycle = 11;
			}
			if (by == 26 && bx == 1 || blinkycycle == 12 || map[by][bx + 1] == 1) // 밟으면
																					// 오른쪽
			{
				if (blinkytempfood == 0)
				{
					map[by][bx] = 0; // 원래있던 자리 그냥바닥
				} else
				{
					map[by][bx] = 3; // 원래 있던 자리 음식바닥
				}
				if (map[by][bx + 1] == 3)
				{
					blinkytempfood = 1;
				} else if (map[by][bx + 1] == 0)
				{
					blinkytempfood = 0;
				}
				if (map[by][bx + 1] == 1)
					gameover.setVisible(true);

				map[by][bx + 1] = 20; // y값 감소하여 위로 이동
				bx++;
				blinkycycle = 12;
			}
			if (by == 26 && bx == 6 || blinkycycle == 13 || map[by - 1][bx] == 1) // 밟으면
																					// 위
			{
				if (blinkytempfood == 0)
				{
					map[by][bx] = 0; // 원래있던 자리 그냥바닥
				} else
				{
					map[by][bx] = 3; // 원래 있던 자리 음식바닥
				}
				if (map[by - 1][bx] == 3)
				{
					blinkytempfood = 1;
				} else if (map[by - 1][bx] == 0)
				{
					blinkytempfood = 0;
				}

				if (map[by - 1][bx] == 1)
					gameover.setVisible(true);

				map[by - 1][bx] = 20; // y값 감소하여 위로 이동
				by--;
				blinkycycle = 13;
			}
			if (by == 23 && bx == 6 || blinkycycle == 14 || map[by][bx + 1] == 1) // 밟으면
																					// 오른쪽
			{
				if (blinkytempfood == 0)
				{
					map[by][bx] = 0; // 원래있던 자리 그냥바닥
				} else
				{
					map[by][bx] = 3; // 원래 있던 자리 음식바닥
				}
				if (map[by][bx + 1] == 3)
				{
					blinkytempfood = 1;
				} else if (map[by][bx + 1] == 0)
				{
					blinkytempfood = 0;
				}
				if (map[by][bx + 1] == 1)
					gameover.setVisible(true);

				map[by][bx + 1] = 20; // y값 감소하여 위로 이동
				bx++;
				blinkycycle = 14;
			}
		}

		public void gamewin()
		{
			wincount = 0;

			for (int i = 0; i < map.length; i++)
			{
				for (int j = 0; j < map[0].length; j++)
				{
					if (map[i][j] == 3)
					{
						wincount++;
					}
				}
			}

			if (wincount == 0)
			{
				gamewin.setVisible(true);
			}
		}

		public void update(Graphics g)
		{
			paintComponent(g);
		}
	}

	public static void main(String[] args)
	{
		new pacman();
	}
}
