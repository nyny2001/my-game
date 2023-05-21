package com.taewon.mygallag;


import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import com.taewon.mygallag.sprites.AlienSprite;
import com.taewon.mygallag.sprites.Sprite;
import com.taewon.mygallag.sprites.StarshipSprite;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class SpaceInvadersView extends SurfaceView implements Runnable,
        SurfaceHolder.Callback {
    //SurfaceView 는 스레드를 이용해 강제로 화면에 그려주므로 View보다 빠르다.애니메이션, 영상 처리에 이용
    //SurfaceHolder.Callback Surface의 변화감지를 위해 필요. 지금처럼 SurfaceView와 거의 같이 사용한다.

    private static int MAX_ENEMY_COUNT = 10;
    private Context context;  // 안드로이드에서 기본으로 제공되는 클래스, 애플리케이션 현재상태와 리소스에 대한 액세스를 제공
    private int characterId;
    private SurfaceHolder ourHolder; //화면에 그리는데 View보다 빠르게 그려준다
    private Paint paint;
    public int screenW, screenH;
    private Rect src, dst; //사각형 그리는 클래스
    private ArrayList sprites = new ArrayList();
    private Sprite starship;
    private int score, currEnemyCount;
    private Thread gameThread = null;
    private volatile boolean running; //휘발성부울 함수
    private Canvas canvas;
    int mapBitmapY = 0;

//
    public SpaceInvadersView(Context context, int characterId, int x, int y) {
        //MainActivity, Intent(침략자), point x,y가 넘어온다
        super(context);
        this.context = context;
        this.characterId = characterId;
        ourHolder = getHolder();  //현재 SurfaceView를 리턴받는다.
        paint = new Paint();
        screenW = x;
        screenH = y;  //받아온 x, y
        src = new Rect();  //원본 사각형
        dst = new Rect();  //사본 사각형
        dst.set(0, 0, screenW, screenH); //시작 x,y와 끝 x,y
        startGame();
    }

    private void startGame() {
        sprites.clear();  //AllayList지우기
        initSprites();  //AllayList에 침략자 아이템들 추가하기
        score = 0;
    }


    
    public void endGame(){
        Log.e("GmaeOver", "GameOver");
        Intent intent = new Intent(context, ResultActivity.class);
        intent.putExtra("score", score);
        context.startActivity(intent);
        gameThread.stop();
    }
    public void removeSprite(Sprite sprite) { sprites.remove(sprite); }

    private void initSprites() {  //sprite초기화
        starship = new StarshipSprite(context, this, characterId, screenW / 2, screenH - 400, 1.5f);
                        //StartshipSprite생성 아이템들 생성
        sprites.add(starship); //AllayList에 추가
        spawnEnemy();
        spawnEnemy();
    }

    public void spawnEnemy() {   // 적의 이동 좌표와 적을 랜덤으로 생성
        Random r = new Random();
        int x = r.nextInt(300) + 100;
        int y = r.nextInt(300) + 100;
        //외계인 아이템
        Sprite alien = new AlienSprite(context, this, R.drawable.ship_0002, 100 + x, 100 + y);
        sprites.add(alien);
        currEnemyCount++;  //외계인 아이템 개수 증가
    }

    public ArrayList getSprites() {
        return sprites;
    }

    public void resume() {  //사용자가 만드는 resume()함수
        running = true;        // 무언가를 백그라운드로 돌려놓고 수행시키는게 Thread
        gameThread = new Thread(this);
        gameThread.start();
    }

    // Sprite 를 StarshipSprite로 형변환하여 리턴하기
    public StarshipSprite getPlayer() {  return (StarshipSprite) starship; }

    public int getScore() {  return score; }
    public void setScore(int score) { this.score = score;}
    public void setCurrEnemycount(int currEnemyCount) { this.currEnemyCount = currEnemyCount;}
    public int getCurrEnemyCount() { return currEnemyCount; }

    public void pause() {
        running = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
        }
    }

    //    public void removeSprite(Sprite sprite) {sprites.remove(sprite);}
//    private ArrayList getSprites(){return sprites;}
    @Override
    public void run() {     // gameThread 에서 실행됨, resume 메서드
        while (running) {
            Random r = new Random();  // isEnemySpawn 변수를 생성해서 랜덤으로 적을 생성
            boolean isEnemySpawn = r.nextInt(100) + 1 <
                    (getPlayer().speed + (int) (getPlayer().getPowerLevel() / 2));
            if (isEnemySpawn && currEnemyCount < MAX_ENEMY_COUNT) spawnEnemy();   //??? 추가

            for (int i = 0; i < sprites.size(); i++) {
                // sprites 리스트에 있는 각각의 스프라이트 객체에 대해 move메서드를 호출하여 움직이도록 함
                Sprite sprite = (Sprite) sprites.get(i);  //Arraylist에꺼 하나씩 가져와서 움직이기
                sprite.move();
            }

            for (int p = 0; p < sprites.size(); p++) {
                for (int s = p + 1; s < sprites.size(); s++) {
                    try {
                        Sprite me = (Sprite) sprites.get(p);
                        Sprite other = (Sprite) sprites.get(s);
                        //충돌체크
                        if (me.checkCollision(other)) {
                            me.handleCollision(other);
                            other.handleCollision(me);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            draw();  // draw 메서드를 호출하여 게임 화면을 그림
            try {
                Thread.sleep(10);  // 10초의 간격을 두고 스레드를 일시정지시키며, 루프의 실행 속도를 조절
            } catch (Exception e) {
            }
        }
    }

    public void draw(){
        if(ourHolder.getSurface().isValid()){
            canvas= ourHolder.lockCanvas();
            canvas.drawColor(Color.BLACK);
            mapBitmapY++;
            if(mapBitmapY<0) mapBitmapY = 0;
            paint.setColor(Color.BLUE);
            for(int i=0; i< sprites.size(); i++){
                Sprite sprite = (Sprite) sprites.get(i);
                sprite.draw(canvas, paint);
            }
            ourHolder.unlockCanvasAndPost(canvas);
        }
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) { startGame();  }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) { }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {   }

}