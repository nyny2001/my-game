<h1>my-game 프로젝트</h1>


<strong><h3>안드로이드 스튜디오로 개발한 my-game 프로젝트입니다.</h3></strong>
<br>
<strong><h2>게임 설명</h2></strong>

<p>게임에 사용할 우주선을 선택한 후 랜덤으로 생성되는 외계인을 공격하여 점수를 쌓는 슈팅게임입니다.</p>
<br>
<br>

<strong><h2>게임 기능</h2></strong>

    
    첫 화면 실행 시 게임에서 사용할 우주선 선택합니다.
    
    게임 도중 일시정지 기능이 있습니다.
    
    조이스틱을 활용하여 자유롭게 우주선을 이동하며 공격할 수 있습니다.
    
    게임 캐릭터는 기본 공격 기능과 필살기 기능이 있습니다.
    
    외계인을 처치할 때 랜덤으로 스피드 상승, 공격력 상승, 체력 회복 아이템이 나올 수 있습니다.
    
    외계인의 공격을 받아 체력을 모두 소진한 경우 처치한 외계인의 score와 함께 게임 종료 화면이 나옵니다.




    
<strong><h2>핵심 Java Class</h2></strong>
<ul>
    <h3><li>StartActivity</li></h3>
    <br/>
    <img src="https://github.com/nyny2001/my-game/assets/68697443/2803cf8a-5264-45ac-b2c9-fb86baebe8ec" alt="기초화면" style="width:300px;height: 600px;">
    <br/>
    <br/>
    <br/>
    
    AndroidManifest.xml에서 지정하여 게임의 제일 첫 화면을 출력하는 부분입니다. 
  
    첫 화면 동작 시 Media Player를 지정하여 첫 화면에 동작될 BGM을 지정하였습니다.
  
    ImageView 배열을 활용하여 ImageView에 우주선 이미지를 넣었습니다.
  
    ImageView에 선택한 이미지를 번호로 알기 위해 Index 변수를 지정하였습니다.
  
    사용자가 선택한 우주선을 화면 중간에 배치합니다.
  
    스타트 비티엔에 시작 버튼을 지정하였고 init 클래스 안에 onClick을 이용하여 StartActivity에서 
     MainActivity로 화면 전환하였고 전환하기 위해서 Intent를 사용했습니다.
</ul>

 
  <ul>
    <h3><li>MainActivity</li></h3>
     <br/>
    <img src="https://github.com/nyny2001/my-game/assets/68697443/fd09254e-7ab5-4ef4-ad89-e6b405d4302e" alt="기초화면" style="width:300px;height: 600px;">
     <br/>
    <br/>
    <br/>

  
    
    StartActivity에서 MainActivity로 넘어온 후 Activity 메인 화면이 실행됩니다.
  
    MainActivity의 기본 구조는 onResume을 사용하여 Activity 메인에 적용하는 음악을 동작시키며 SpaceInvadersView를 호출합니다.
    
  
    다시 Main으로 돌아와서 Main의 기본적인 기능 구조는 게임을 진행하면서 게임의 각 상황별 사운드를
    ArrayList를 이용하여 소리를 지정하였습니다.
    
    ArrayList에 상황별 지정된 소리는 장전, 체력 감소 소리, 사용자가 공격할 때 소리, 게임 아이템 소리 등이 있습니다.
    
    그 이외에 MainActivity에는 조이스틱 이동방향으로 비행기를 이동하게 하는 기능과 총알 버튼을 눌렀을 때 총알이 나가는 기능, 
    장전할 때 조이스틱 기능, 일시정지 기능, 필살기 기능(SpecialshotSprite) 등을 
    각 기능 별로 SetOnClickListener를 활용하여 실행되게 하였습니다.

    각각의 아이템들의 화면 SpaceInvadersView 클래스를 나타냅니다. 
  
    게임의 침략자의 위치 좌표, 아이템, 적의 이동 좌표와 적을 랜덤으로 생성, 외계인 아이템 개수 증가 등이 있습니다.
    
    이 클래스는 게임을 그리고 제어하기 위한 SurfaceView를 확장하고 있습니다.
  
    게임을 구현할 수 있도록 필요한 기능을 제공합니다.
  
    클래스를 정의하고 게임을 그리고 제어하기 위한 기능을 구현한 것입니다



  



<strong><h2>전체 Java Class 구성요소 </h2></strong>
<ul>
<br>

<p>PauseDialog : Dialog 클래스를 상속하며, 
    일시 정지 다이얼로그를 나타내는 커스텀 다이얼로그입니다.</p>
<p>ResultActivity : 클래스는 게임 종료 후 결과를 표시하는 액티비티입니다.</p>
<p>SpaceInvadersView : 게임을 구현할 수 있도록 필요한 기능을 제공합니다.<br>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    &nbsp;&nbsp;&nbsp;&nbsp;
                       클래스를 정의하고 게임을 그리고 제어하기 위한 기능을 구현한 것입니다.</p>

<br>
<h3><li>sprites</li></h3>
<p>AlienShotSprite : 해당 클래스는 외계인이 발사하는 총알을 나타내는 
                     스프라이트 객체를 생성합니다.</p>
<p>AlienSprite : 해당 클래스는 외계인 스프라이트를 생성하고 
                 초기화하는 역할을 합니다. </p>
<p>ShotSprite :  해당 클래스는 총알을 나타내는 스프라이트 객체를 생성하고
                 초기화하는 역할을 합니다.</p>
<p>Sprite : sprites에 포함된 각 클래스별 sprite에 모든 종목들이 가지는 
            기본 x, y 좌표 입니다</p>
<p>StarshipSprite : 해당 클래스는 아이템을 맞았을 때 각각의 아이템별 지정한 동작 상황을 
                    메인 액티비티에 전달 합니다.</p>
<p>SpecialshotSprite : 해당 클래스는 게임에서 필살기 사용 기능을
                       구현하는 클래스입니다.</p>


<br>
<h3><li>items</li></h3>
<p>HealitemSprite : 클래스는 Sprite 클래스를 상속하며, 
    게임에서 사용되는 치유 아이템을 나타내는 스프라이트 객체입니다.</p>

<p>PowerItemSprite : Sprite 클래스를 상속하며, 
    게임에서 사용되는 파워 아이템을 나타내는 스프라이트 객체입니다.</p>

<p>SpeedItemSprite :  해당 클래스는 속도 아이템 스프라이트를 생성하고 초기화하는 역할을 합니다. </p>
</ul>
    <br><br>
    <br><br>
    <strong>게임 플레이 영상은 Issues를 확인해 주시길 바랍니다.</strong>
<br><br><hr>
