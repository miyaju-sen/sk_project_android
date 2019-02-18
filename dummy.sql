/*users*/
/*------------------------------------------------------------------------------------------------------------------------------*/
INSERT INTO users(
    password
) VALUES (
    "aaaaaaaa"
);

/*pltos*/
/*------------------------------------------------------------------------------------------------------------------------------*/
INSERT INTO plots(
    title, 
    slogan,
    summary
) VALUES (
    'Karma',
    '『期待』は裏切られる。いとも簡単に、何の前触れもなく',
    'ターザック村では、人身御供の掟が定められていた。きっかけは二百年前。村に住むある男が、皆から愛されていた女神を裏切ったことが始まりである。
その村で生まれ育った少年、ブラフマー・スリジャン。彼は女神を裏切った男の血を継ぎ、生まれる前から生贄となることが決定されていた。

死の時期が確定されている人生。誰からも愛されることのない人生。無駄で、無意味で、無価値な人生。ブラフマーは、『生きている意味』が分からなくなっていた。
そんな彼に、よその町から移住してきた少年シヴァ・ヴィナッシュは言う。

「――俺のために生きてくれ、ブラフマー」

生贄の対象者である少年ブラフマーと、彼に寄り添う少年シヴァ。
そんな二人を待ち受けている運命とは――……'
);

INSERT INTO plots(
    title, 
    slogan,
    summary
) VALUES (
    'Karma2',
    '「世界なんてどうだっていい。私はただ、あの子たちを護りたい」',
    'Karmaの続編。

素性を偽り、ターザック村の一員として溶け込んだ少女アザミ。
彼女の目的はただ一つ。生贄の少年ブラフマーを、理不尽で残酷な運命から救うこと。
しかし、彼はすべてを諦めてしまった様子で、アザミは思わず苛立ちを乗せた言葉をぶつけてしまう。
彼を傷つけてしまったかと己の発言を悔いるアザミ。
そんな彼女の前に、一人の少年が現れた。
その髪は、雪のように白く。
その瞳は、空のように青い。
少年は告げた。肌を掠める空気よりも冷たい声で。
もう、邪魔はさせない――と。'
);

INSERT INTO plots(
    title, 
    slogan,
    summary
) VALUES (
    'Licht von Schlaf',
    '幸福の形を見つける物語',
    '悪夢の象徴『ナイトメア』と呼ばれる記憶喪失の青年が、自らを生み出したとされる『主』に会うため様々な『物語』を悪夢へと変えていくストーリー。'
);

/*stages*/
/*------------------------------------------------------------------------------------------------------------------------------*/
INSERT INTO stages(
    plot, 
    stage
) VALUES (
    1,
    '世界の中心にあるとされるターザック村が舞台となる。
かつてアディティーと呼ばれる大地の女神が住んでいたとされ、今もなお村人から信仰され続けている。
二百年前に起こったある事件を境に、人身御供の掟が定められるようになった。

この村には教育を行う施設というものが存在しておらず、村長自らが村の子供たちを自宅に招いて勉学を行わせている。
よそ者を拒み、周囲から孤立している。'
);

INSERT INTO stages(
    plot, 
    stage
) VALUES (
    3,
    '現実世界と空想世界と、その狭間を舞台に物語を描いている
・現実世界：我々のいる世界・場所。
・空想世界：『物語』の世界。
・狭間：上記二つの間に生じる空間。
本作品の中はすべて空想世界か狭間かの二つしかなく、例えば『空想世界を生み出す』という力はこちらからすれば『空想世界の中で空想世界を生み出している』という認識になる。

本編では、空想世界と空想世界との間にある狭間で物語が展開されていく。'
);

/*parlances*/
/*------------------------------------------------------------------------------------------------------------------------------*/
INSERT INTO parlances(
    plot, 
    name,
    explanation
) VALUES (
    1,
    '創造の一族',
    'ブラフマー神の子孫。
厳密に言えばヴィシュヌ神から二分したうちの一つ。

この一族に生まれた者はみな白髪碧眼。
長男、長女のみ「創造の力」を得て生まれる。
また、能力を持った者の子でなければ能力を得られない。

百年ほど前に長男(アディティーの夫)がディティーによって殺されてしまったため、現在能力は途絶えている。'
);

INSERT INTO parlances(
    plot, 
    name,
    explanation
) VALUES (
    1,
    '破壊の一族',
    'シヴァ神の子孫。
厳密に言えばヴィシュヌ神から二分したうちの一つ。

この一族に生まれた者はみな黒髪金眼、高確率で褐色肌。
ランダムで能力が宿り、能力を得られなかった者の子だからといって能力が宿らないことはない。

アディティーの一件により、家系事態が滅んだと噂されていた。
ディティーに殺された女には兄がいたため、その兄の子から続いていた。

※存在が知られてしまうと村人に咎められ、あるいはディティーに殺されてしまうと恐れ町へこっそり移住する。'
);

INSERT INTO parlances(
    plot, 
    name,
    explanation
) VALUES (
    1,
    '破壊の力',
    '破壊の一族にのみ宿る能力。

その名の通りあらゆるものを破壊する能力。人を傷つけることも。
その度合いは人それぞれ。必ず宿るとは限らない。'
);

INSERT INTO parlances(
    plot, 
    name,
    explanation
) VALUES (
    3,
    '虚像世界',
    'グレイが生み出した、チヒロが元居た世界に酷似している世界
チヒロの記憶・目線・価値観など、チヒロを主体にして造られてるため、実物とは異なる点が複数ある（→虚像）

＊＊生み出した目的＊＊
チヒロに力の実験をさせるため

篠田の死で世界を憎んだチヒロに宿ったのは『世界を書き換える力』
グレイはそれを使って篠田を救えばいいと
しかし原典に直接力を使ってしまうと、失敗したり納得のいかない結果となってしまう危険性がある（オリジナルを変更すると二度と元には戻らない）
それを防ぐため、実験用として虚像世界を用意した

イメージとしては、オリジナルでの失敗を防ぐために、編集用としてコピーを用意したって感じ

しかし、、、
　チヒロは結局、力を一度も使わず。自分が愛した篠田はもう死んだのだと告げて、自ら自我を捨てた。'
);

INSERT INTO parlances(
    plot, 
    name,
    explanation
) VALUES (
    3,
    '解放・自我について',
    '本来、物語の登場人物とは、作者の意のままに動かされ、自分の意思で勝手に動くということはない
決められた台詞を喋り、決められた行動通りにしか動けない
そしてそれを認識していない

それらを認識し、自分の意思で動き、話すようになった状態が『自我を得ている』ということになる
※性格や一人称などの設定には抗えない

つまり、「自分は物語の登場人物である」「自分という存在」と認識した際に、そのキャラクターは自我を得る。

自我を得るきっかけはグレイにもはっきりとは分かっておらず、
　恐らく、読者によって何度も『物語』が繰り返されるうちに、キャラクターに宿る想いが集約した結果なのではないか、とグレイは推測する

解放とは、作者が決めた筋書きに縛られない状態になることを指し、自我を得た瞬間に『物語』から解放される。
その際、自分が登場する『物語』の原典は必ず自分の手元にある。
自我を得ている間は、筋書き通りではなく自分の意思で動くことができる。

元いた『物語』の世界に還ることもできるが、その際は自我を得て解放されていた間の記憶を全て失うこととなる。
　イメージとしては、魂がイデア（狭間）から現世（物語）に堕ちるような

また、解放されている間も、グレイが『物語』を『物語』として認識している限り、対象となる『物語』は存在し続ける
　
　→キャラクターが解放されても、文章内からそのキャラクターの名前が消えるわけではない。
　　そのため物語の進行には一切問題ない

　例えば……
　　チヒロが解放されても、物語の中に『宮原千尋』という人物は文章上存在している
　　文章に『宮原千尋』と書かれていれば、当然読者はそれが『宮原千尋』なのだと認識する
　　明らかなミスによる矛盾でも発生してない限り、「いやそれは宮原千尋じゃない」と言い出す奴なんていない
　　文章に『宮原千尋』と書かれている限りそれが『宮原千尋』であり、それ以上でも以下でもない

　　グレイ「これが漫画なら話は変わってくるんだろうけどね」
　　※漫画の場合は具体的なイメージ（絵）があるため

要するに、グレイが『物語』を『物語』として認識することで『物語』が成り立つように、
　読者が『宮原千尋』を『宮原千尋』として認識することで物語上に『宮原千尋』は誕生する

グレイ「少し難しいか？　それなら、少しずれているかもしれないが、ドラマや舞台で考えてみろ」
　ドラマや舞台の場合、演じる役者（中身）が変わっても、演じている人物（外・設定）は変わらない
　見ている側からすれば明らかに別人なのに、舞台上では同一人物として扱われる（何も問題は発生しない）
　それと同じようなもの

「因みにチヒロの世界もまだ空想世界として存在しているが、ぼくからすればあの宮原千尋もお前も全く同じ人物に見えるぞ」
　※しかし、チヒロ自身からすれば自分とは別人に見える

チヒロ「……よく分かんないけど、要するに今のボクは肉体のない思念体のようなものってことかい？」
グレイ「その例えで理解できるのならそれで構わない。すべては認識次第だからな」


Ｑ.解放中に、自分と同じキャラクターが自我を得て解放されることはないのか
現時点では未観測だが、絶対に起こりえないとはいえない'
);

INSERT INTO parlances(
    plot, 
    name,
    explanation
) VALUES (
    3,
    '神・主・観測者について',
    '・主：『物語』の作者。正確には、登場人物にとっての作者（生みの親）。

例）
　朔夜にとっての主は、チヒロ
　チヒロにとっての主は、宮樹せん

-----------------------------

・神：主の主。登場人物から見た作者の主

例）
　チヒロから見た宮樹せんは主だが、朔夜から見た宮樹せんは神となる

-----------------------------

・観測者：観測する者。観測（認識）することによって、あらゆる事象・物質は初めて存在する。

誰もがこれに当てはまる

ただし、グレイやチヒロの場合は『物語』を『物語』として成り立たせるための役割であり、彼らが観測することで『物語』は初めて『物語』になる。
→『物語』という空想世界が生まれる

イメージとしては……
　・イルゼが小説を完成→グレイがそれを観測し、認識する→イルゼの小説が空想世界として生み出される
　・チヒロが『物語』を紡ぐ→チヒロがそれを観測し、認識する→虚像世界にチヒロの『物語』が反映される

メモ「　http://blog.oplan.co.jp/archives/384　」
　　「　https://ameblo.jp/u-oneness/entry-12337105382.html　」

-----------------------------'
);

INSERT INTO parlances(
    plot, 
    name,
    explanation
) VALUES (
    3,
    '認識',
    '世界は認識でできている

自分が「ここは現実だ」と認識していれば、そこは現実世界となるし、「ここは夢だ」と認識していれば、そこは夢の世界となる

例えばリヒトは、「自分は今、夢の中にいる」と認識している
認識した上で、夢の世界の中に留まり続けている


登場人物たちが自我を得るのも、その認識がずれてしまうため。
「自分は物語の住人だ」と理解――認識することで、自我を得て『物語』から強制的に解放される'
);

INSERT INTO parlances(
    plot, 
    name,
    explanation
) VALUES (
    3,
    'ナイトメア',
    'ナイトメアという物語があった
悪夢に飲み込まれ、自らも悪夢と化してしまった青年の青年のお話
イルゼとナハトは、その青年・メアの過去にリヒトの存在を埋め込もうとした
上手くいくかは分からない
それでも、何もしないよりはましだから

イルゼの力に賭けて、ナハトは干渉した。

イルゼが紡いだ作品の一つ
リヒトが眠りにつくよりも前に完成されていたが、誰にも読まれてない（読んでもらう人がいなかっただけ）

そのため、『ナイトメア』という空想世界は誕生しているものの、一ミリも進行していない（ここ重要）

※内容は別途ファイルにて

本編のメアとは違い、こちらのメアは最後に親の愛を受けることができ、救われている。'
);

INSERT INTO parlances(
    plot, 
    name,
    explanation
) VALUES (
    3,
    '本編（リヒト）の舞台について',
    '本来ならば、ナイトメアの物語はイルゼの力によって誕生した空想世界の中で展開されるもの
つまり、リヒトが目覚めるまでの流れもその世界の中で展開されるはず

しかし、本編は狭間とされる空間で展開される

それは、別の空想世界に存在する伯と繋げるため
→イルゼの力は生み出した作品から空想世界を誕生させる力。作品の数だけ、空想世界も生まれる。

ナハトの干渉によって、『悪夢を食べる物語』と『ナイトメア』とを繋げる必要性が生じた。
これによって、上記二つの物語を繋げるための狭間（他と完全に隔離された、二つの物語の間にのみ存在する空間）が自然発生した。

要するに、『空想世界と空想世界との狭間』とも言える場所
それが本編の舞台であり、双子はこの狭間の担当者

※実際の狭間とは現実世界と空想世界との間にある、グレイが観測を行うための空間
　しかし本編の舞台となる狭間は、イルゼが生み出した空想世界同士の間であり、ナハトの干渉を成立させるために自然発生した空間である


イヴァンは、ただ巻き込まれただけである
また、本来ならば本編での狭間はイルゼの空想世界の間に生じた空間であるため、チヒロの『物語』が存在するはずない
（空想世界（イルゼ）→狭間（本編）→空想世界（イルゼ・チヒロ）→狭間（チヒロ）→現実世界）
　→詳細は同欄の「双子について」


本編は、イルゼの物語『ナイトメア』にナハトが干渉したことで始まった物語だが、『ナイトメア』とは話の流れがかなり違う。
→双子がメアを強制的に目覚めさせたため、『ナイトメア』が実行されていないから。

結果として、メアはリヒトとして目覚めることになるが、内部ではイルゼたちの想像とは異なった形で話が進んでいた、ということにになる。

↓

『物語』は誰かに読まれることでしか進行しない
しかし、イルゼとナハトは干渉しただけで『ナイトメア』を再び読もうとはしなかった。
そのため、双子が何かアクションを起こしていなければ『ナイトメア』が実行されることは永遠になかった。

双子「バカダヨネ、モノガタリハ、ヨマナキャススマナイノニ」


Q.本来の流れと異なっているのに、それでナハトたちの目的は成立するのか

A.物語とは所詮、起承転結の四つの点と糸とでしか構成されているものでしかない。まるで机上で描かれた星座のように。
その起承転結が繋がってさえいれば、物語は成立する。

それに、そもそもナハトたちの目的は物語を変えることではなく、リヒトを目覚めさせること。
それさえ達成されていれば、彼らに物語がどう進んでいようがメアがどうなっていようが関係ない。

彼らに観測できないことは、彼らにとって存在しないも同然なのだから。'
);

INSERT INTO parlances(
    plot, 
    name,
    explanation
) VALUES (
    3,
    '双子について',
    '双子――シロナ・クロナについて

彼らはグレイと同等の存在


ナハトが干渉した『リヒトが目覚めるまでの物語』を成立させるために自然発生した狭間の案内人
彼らは狭間の発生と共に誕生し、イルゼの物語（空想世界）を観測するために配置された存在（かもしれない）


グレイ「おおかた、自然発生した狭間の担当として配置されたんだろう。主としても、観測者がいた方が色々と都合がいいからな」


しかし、同等と言っても流石にグレイのような全権限を与えられているわけではない
　→シロナとクロナに与えられた権限は読み取りと実行

＋＋＋＋＋

双子は読み取りの権限を利用して、チヒロ側からイヴァンを含む全ての『物語』を引っ張ってきた
そのため、本来チヒロ側の狭間で解放されるはずだったイヴァンは、イルゼ側の狭間で解放されることとなった

次に同じく読み取りの権限でメアを強制的に目覚めさせた。
イヴァンにメアのことを任せ、メアが伯と出会うように仕向けて。

最後に『ナイトメア』を実行するのと同時に、全ての成り行きを全員に見せつけた。
これによって、『物語』が急速に進行する。
　メアに『ナイトメアのメア』としての情報と、本編が始まるまでの流れとが入り込む

※メアが最初自分のことを分かっていなかったのは、『物語』を進行させないまま目覚めさせられたから。

＋＋＋＋＋

双子は遊び感覚で、まるでゲームを楽しむかのようにそうしただけ
特に深い意味はない

　→イヴァンの目的と、メアの力は相性が良いから。

何故、隔離された空間に位置する二人がチヒロの『物語』を把握していたのかは謎
観測者という存在がゆえに与えられた事前知識なのかもしれない

グレイ「それが主の望んだ展開、だったのかもしれないな」


また、二人とも幼子のように無邪気なのは、実際に誕生したばかりだからとも言える
（観測者とするには幼すぎた）

彼らに悪意など穢れた心は一切なく、ただ純粋に無垢に面白がって楽しんでいるだけ'
);

INSERT INTO parlances(
    plot, 
    name,
    explanation
) VALUES (
    3,
    'メアの目覚め（自我の獲得）について',
    '双子の力によって、メアは強制的に目覚めることとなり、自我を得た。

だがこの『メア』はあくまで『過去にリヒトを組み込む』ための依り代のようなものでしかなく、本来のメアとは異なる。
　いわば、初期設定だけを所持したキャラクター。『物語』が進行することで得る知識・記憶などは一切ない。
　→これが、本来の自我の獲得とは異なっているがゆえのデメリット

異例の状態だとしても、自我を得たのは事実。
『物語』の筋書きとは異なった発言・行動ができるようになっている

　→本編のメアの行動と、『ナイトメア』本来のメアの行動とは異なっている（『ナイトメア』の流れに沿っていない）

ちなみに初期設定にあるのは、父親からの虐待を受けていたことと、親からの愛情に飢えているという点
それゆえに、メアは愛というものに執着している


頭痛の原因は、強制的に自我を得たことによる障害

「解放・自我について」の項目にもあるように、本来は自分の存在を認識することで自我を得る。
しかし、メアは自分の存在を認識することなく、他人の手によって無理やり自我を得ることとなった。

そのため、メアは本当の意味で自分という存在を認識・理解できていない。
愛というものに触れるたび、どうして自分がこんなにも過剰に反応してしまうのかが分からず、頭痛を起こしてしまう。

　→一種の拒絶反応'
);

INSERT INTO parlances(
    plot, 
    name,
    explanation
) VALUES (
    3,
    'グレイ・シロナ・クロナの顔に刻まれてるものについて',
    '権限を示している（参照：Linux）

読み取り……世界を読み込むこと。この権限があれば各世界間を自由に行き来できる（例：『物語』に入る力、『物語』から出る力）
書き込み……チヒロやナハトのように、『物語』を直接編集できる
実行……『物語』を実行すること。この権限があれば『物語』を動かすことができる


シロナ・クロナの「101」：読み取り、実行が可
　
グレイの「/」：最高権限rootを示してる'
);

INSERT INTO parlances(
    plot, 
    name,
    explanation
) VALUES (
    3,
    'グレイの力について',
    '基本的に何でもできる（『物語』に関する全権限を保有している）
神様同等の存在のようなもの

※逆に言えば現実世界への干渉はできない


＊＊＊
チヒロ「ところで、君は何者なんだい？」

グレイ「別に何者でもない……と言いたいところだが、現代物出身のチヒロからしたら不思議だろうな」
「そうだな……全ての物語の管理人、とでも認識してくれればいい」

「つまり、君は神さまってことかい？」

「そんな感じだ。まぁ、正確には神と同等、と言い表すべきだが」
「力については、作者が自分の物語を好き勝手に描けるのと同じだ。幸せも不幸せも全部作者の意思次第……そうだろう？」

「ふうん……君にかかればなんでもあり、ってことか」
＊＊＊'
);

INSERT INTO parlances(
    plot, 
    name,
    explanation
) VALUES (
    3,
    'チヒロの力『世界を書き換える力』『自動書記』について',
    'その名の通り、世界を書き換える力
ナハトの『干渉する力』とよく似ているが、ナハトの場合はあくまで『付け足し』である
それに対して、チヒロの力は『上書き』になる

篠田を奪った世界への憎悪から生まれた力


チヒロの力はあくまで『書き換える』だけであり、世界を創造したりシナリオを反映させたりなどはできない
しかしグレイによって特別に力を追加されている。それが『自動書記』

『自動書記』について...
　・最初に舞台となる世界観と登場人物、設定を用意する
　・用意した舞台の中でキャラクターが各々に動きだし、勝手に『物語』を紡ぎ出す
　・それが小説として形になる（自動で本に文字が浮かび上がる）

創作活動において、自分で作ったキャラクターが自分の中で勝手に動いて喋るときがある（妄想とかで）
それに似たような力

つまりチヒロは最初の『設計』的なものをすればいいだけで、あとは勝手にキャラクターたちが『物語』を生み出してくれる


その力で生まれたのが朔夜たち
　※グレイが用意した虚像世界に元からいたのは千尋と篠田

イメージとしては、
グレイが用意した世界の中で、チヒロのキャラクターたちが『物語』を紡いでいる
※『誰かを救いたかった話』だけ、力とは無関係で作られた物語


※最後のグレイのセリフ「でも、あんたの本当の力を使えば――」
'
);

INSERT INTO parlances(
    plot, 
    name,
    explanation
) VALUES (
    3,
    'ケーテの力「創造」について',
    '想像したものを創造する力
　想像したものを現実にリアルタイムで反映することができる


ケーテの場合は解放後に宿る力とは異なり、生まれつき持っていた特殊能力のようなもの
霊が見えるとか、心の声が聞こえるとか、そんな感じの類

力の使用にはしっかりとしたイメージを抱くことが重要で、絵や文字にすることでイメージが固まり、反映されやすい
イメージがあやふやであった場合、すぐ消えてしまったり上手く反映されなかったり（思ったようにならなかったり）する
※何でもかんでも生み出せるわけではない

例えば……
　・絵に描いた人物や生き物などが現実に現れる
　・文章に書いたことが現実になる（雨が降る、など）

※文章にする際、具体的にした方がより反映されやすい（雨が降る→今日の〇時から雨が降る。今から雨が降る。など）


因みに絵や文章にした場合、それらが燃えたり消えたりしてしまうと現実に反映されたものも消える
例えばリンゴの絵を描いて現実に反映させたとして、その絵を消したり燃やしたりすれば現実からもリンゴが消える

逆に言えば絵や文章が消えない限り対象物も消えないということで、例えばナハトは絵が消えない限り絶対に死なない
（死んだと思っても必ず絵の元で蘇る）

また、死に抗い生に縋る本能によって、自ら自分の絵を消すということはできない'
);

INSERT INTO parlances(
    plot, 
    name,
    explanation
) VALUES (
    3,
    '伯の力「悪夢払い」について',
    '他者の悪夢を食べて追い払う力
伯に悪夢を食べてもらった人は悪夢から解放される


本編でいう、キャラクターが物語からの解放時に得る力とは別物
獏の子孫と言われる一族が持つ特殊な能力で、伯はその一族の末裔である


力を使う際には負担がかかる
その負担の形は様々で、伯の場合は『食べた悪夢に自分が取り憑かれる』というもの

簡単に言えば、食べた悪夢を伯も見てしまう
その悪夢に流され、これが夢だと気が付けないまま悪夢に飲み込まれてしまうと永遠に目覚めなくなり、やがて死に至る（魂が悪夢に吸い込まれる）

悪夢に飲み込まれないためには強い意思が必要
少しでも心が弱ると、その隙を突かれあっという間に飲み込まれてしまう'
);

INSERT INTO parlances(
    plot, 
    name,
    explanation
) VALUES (
    3,
    'ナハトの力「干渉」について',
    '既存の作品に対して干渉する力

例えば、、、
　・風景画に炎を描き足せば、その場所がたちまち火事になってしまう
　・小説に補足分、裏設定のように文字を書き込めばその通りに反映される　など

ただし、チヒロの力とは違って『付け足す』ことしかできず、『上書き』はできない
そのため、既存物と付け足した要素との間に矛盾が生じれば反映されないし、既に描かれた（書かれた）ことを変更することはできない
　→辻褄を合わせる必要性がある'
);

INSERT INTO parlances(
    plot, 
    name,
    explanation
) VALUES (
    3,
    'イヴァンの力「救済」について',
    '母であるケフリンと妹であるイヴァナを救いたかった
そんな想いに対する皮肉のような力

文字通り、何かを救済する力で、その何かとは『物語』の登場人物に当てはまる
解放後のキャラクターに対しても有効らしく、メアが頭痛を起こした際にはこの力を使って苦痛を和らげている

メアの力とは反対に、不幸な『物語』をハッピーエンドへと向かわせることもできる
しかしそのハッピーエンドとは、その一冊の『物語』としての一時的なハッピーエンドに過ぎない
続編があった場合はそこまで干渉されないし、『物語』の救済＝主人公の救済として、主人公となる人物しか救われない

例えば。。。
　・続編がバッドエンドなら続編ごと救わない限り、続編での結末は何も変わらない
　・主人公が救われることで犠牲となる人物が発生する可能性があるが、どんな犠牲があろうと『物語』上では必ずハッピーエンドとして終結する


このことから、
　本編で、没となった話の葵がイヴァンによって救済され生き延びるが、没となってない本来の葵には何も影響しない
　元々「葵が死んで奏汰が生存する」か「奏汰が死んで葵が生存する」かの二択であった'
);

INSERT INTO parlances(
    plot, 
    name,
    explanation
) VALUES (
    3,
    'メアの力「悪夢」について',
    '『物語』を悪夢に変える力

ただし、メアに関しては解放後に宿った力というよりかは、彼自身に元々備わっている特殊能力のようなもの
悪夢の象徴たる存在であるが故の力

どんな『物語』も悪夢に飲み込む――バッドエンドに変えることができる
しかし、メア自身が展開や流れを決めることはできない

どんな経緯を辿ってバッドエンドに辿り着くかはメアにも分からない



メアが悪夢に変えた『物語』は、チヒロにの力でしか元に戻せない（グレイは基本的に何でもできるため省く）
イヴァンの力だと、彼のもまた展開を決めることができないため、また違った形でハッピーエンドとなる可能性が高い


この力の影響かして、他所の『物語』の登場人物でもメアを認識することができる（本来、登場人物は物語の筋書き通りにしか動けない）
正確には、認識させていると言った方が正しい'
);

INSERT INTO parlances(
    plot, 
    name,
    explanation
) VALUES (
    3,
    'イルゼの力『空想世界を生み出す力』について',
    'ケーテによって与えられた設定（力）で、完成した作品を空想世界として生み出す力。
例えば小説を書いて完成させた際には、その小説の中の世界が空間世界として誕生する。

しかし、イルゼは自分で生み出した世界に干渉することができず、この力が実際に働いているのかどうかの確認ができない。'
);

INSERT INTO parlances(
    plot, 
    name,
    explanation
) VALUES (
    3,
    'リヒトの力『夢へ惹き込む力』について',
    'ケーテに与えられた設定（力）で、その名の通り、夢の中へと惹き込む力。

ケーテは、「素敵な夢をみんなと共有してほしい」という想いで生み出した。
※しかしケーテの理想とは裏腹に、この力は結果として『夢に閉じ込める力』『夢に飲み込む力』として働いてしまった。

この力の対象者は自分を含んだ全ての人物。なお、起きているか寝ているかは問わない。
また、夢の種類も問わないため、悪夢の中に惹き込むことも可能である。
　
　→それが、本編での展開。

物心つく前にケーテが死んでしまっているため、リヒトはこの力を自覚していない。
それゆえに、無自覚で発動してしまい、自らを夢の中へと惹き込んでしまった。

惹き込んだ夢の中からの脱出方法は、夢の主が夢から目覚めること。
つまり、リヒトが目覚めない限りリヒトの夢の中に惹き込まれた人物は出てこられないこととなる。'
);

INSERT INTO parlances(
    plot, 
    name,
    explanation
) VALUES (
    3,
    'ケーテの日記',
    'ケーテの部屋にある机の引き出しに、鍵をかけた状態で保管してある

内容は、ナハトへの願い（私を忘れてほしい）が綴られたのを最後に途絶えている


日記を開くための鍵はケーテの部屋のどこかに隠されてる'
);

INSERT INTO parlances(
    plot, 
    name,
    explanation
) VALUES (
    3,
    'アルビノ・イヴァンたちの環境について',
    '「アルビノの差別について」
https://www.rotary.org/ja/rotary-helps-tanzanians-albinism

家族に不幸をもたらす、アルビノの身体の一部を手に入れると幸福になる

上記の迷信を振りまいた村の呪術師は、白人のケフリンやイヴァンを忌み嫌い、彼らを『忌々しき白い悪魔』と呼んだ

「我らが同族（イヴァンの父）は忌々しい白人（ケフリン）により殺された。奴は死んでも当然の悪人だ」
「ケフリンとさえ出会わなければ彼は死ななかった」


ケフリンの死後、村の住人に「アルビノの体の一部を持っていれば幸運になる、貧困からも救済される」と広め、それを真に受けた住人らはイヴァナを襲撃
同時に白人として生まれたイヴァンのことも殺害

このとき、呪術師からは「バラバラにして土に埋めろ」と言われていたが、アルビノの体の一部を手に入れて高揚していた住人らはイヴァンを放置

「馬鹿者！　ただ殺すだけではいかんと言ってであろう！」
「で、でもちゃんと殺したぜ？」

呪術師は、必ずイヴァンが復讐してくると言ったが、住人は誰も本気にしなかった


＊＊＊＊＊＊＊
焦る呪術師と共に家を見に行くと、イヴァンとイヴァナの死体はなくなっていた……とか？
「だ、大丈夫だって。何せ俺らはアルビノの肉を手に入れたんだ。心配することねぇって」
＊＊＊＊＊＊＊


その夜、イヴァンは蘇生
村に火をつけ、住人の手足を切り落としていった。
　→呪術師はこのことを恐れていた

全員を殺害後、イヴァンは家に帰り、イヴァナの亡骸と共に眠った'
);


/*memos*/
/*------------------------------------------------------------------------------------------------------------------------------*/
INSERT INTO characters(
    plot,
    phonetic,
    name,
    another,
    image_path,
    age,
    gender,
    birthday,
    height,
    weight,
    first_person,
    second_person,
    belongs,
    skill,
    profile,
    lived_process,
    personality,
    appearance,
    other
) VALUES (
    1,
    'しヴぁ・ヴぃなっしゅ',
    'シヴァ・ヴィナッシュ',
    '破壊神シヴァの生まれ変わり',
    '',
    '15',
    1,
    '1月25日',
    0,
    0,
    '俺',
    'あんた',
    'ターザック村',
    '破壊の力。あらゆるものを破壊し、砂に変えてしまう。',
    'よその町から移住してきた、黒髪金眼の少年。ヴィナッシュ家の長男で、破壊神シヴァの生まれ変わり。本人はそのことを知らない。',
    '父親を亡くし、母親からずっと虐待を受け続けてきた。学校でもいじめられ、ある日怒りによって相手を砂に変えてしまう。そのときのことがトラウマになって、若干の記憶障害を起こしている。',
    '素っ気ないように見えて、頼まれると断れないタイプ。困っている人を見ると放っておけない。しかし本当のところ自分が一番大事で、偽善の面がある。死を異様に恐れている。',
    '黒髪でサイドと前髪が長い。金色の瞳。',
    '無印と2とで微妙に性格が異なる。'
);

INSERT INTO characters(
    plot,
    phonetic,
    name,
    another,
    image_path,
    age,
    gender,
    birthday,
    height,
    weight,
    first_person,
    second_person,
    belongs,
    skill,
    profile,
    lived_process,
    personality,
    appearance,
    other
) VALUES (
    1,
    'ぶらふまー・すりじゃん',
    'ブラフマー・スリジャン',
    '創造神ブラフマーの生まれ変わり',
    '',
    '15',
    1,
    '1月25日',
    0,
    0,
    '僕',
    '君',
    'ターザック村',
    '創造の力。しかし遺伝によるものではなく、生まれ変わりとして宿っているもの。なお、本人に自覚はない。',
    '『裏切り者』として生贄に捧げられることが決定している少年。生きる意味を感じられず、ただ無気力に日々を過ごしている。',
    '生まれたときには既に生贄であることを決められていた。そのため生きる意味を感じられず、無気力に過ごしてきた。しかし、シヴァと出会うことで彼に生きる意味を見出すようになる。',
    '大人しく、無感情な子。しかし、それは生贄という理不尽な運命を背負わされていたがための性格で、本当は悪戯好きで無邪気なごく普通の少年。少し泣き虫でかなりのお人好し。自己嫌悪が激しく、なんでも自分のせいだと思い込む。',
    '白い短髪に、青い瞳',
    ''
);

INSERT INTO characters(
    plot,
    phonetic,
    name,
    another,
    image_path,
    age,
    gender,
    birthday,
    height,
    weight,
    first_person,
    second_person,
    belongs,
    skill,
    profile,
    lived_process,
    personality,
    appearance,
    other
) VALUES (
    3,
    'りひと・ふぉん・しゅらーふ',
    'リヒト・フォン・シュラーフ',
    '夢への逃避　リヒト',
    '',
    '13',
    1,
    '',
    0,
    0,
    'ぼく',
    'おまえ',
    '自分の夢の中',
    '夢に惹き込む力。ケーテによって与えられた設定で、その対象は自分をも含む。',
    'ケーテと血は繋がっておらず、ケーテの『創造』により生まれた子。しかし、まだ赤ん坊の頃にケーテが死んでしまったため、その事実を知らない。ナハトとよく似た顔立ちであることから、ケーテが無意識にナハトのことを思い出しながら創造した可能性が高い。',
    'ケーテを殺した、と父から恨まれており、物心つく前から虐待を受けていた。
    年を重ねるごとに酷くなり、体中に傷痕がある。
    優しくしてくれるイルゼには心を開き、助けを求めたことも。しかし、優しくしてくれても父の言うことにだけは絶対に逆らわない（助けてくれない）イルゼに失望し、「偽善者」と憎悪の念を抱くように。
    
    ちなみに虐待される理由については、父から投げつけられた「お前のせいでケーテは死んだ」という言葉で何となく察している。
    →「自分のせいで母は死んだ。だから父は自分のことを憎んでいる」
    殴られるのも蹴られるのも、全部自分が悪いのだと思い込むように。',
    '親の愛を受けられず、愛というものに酷く飢えている。また、家庭環境の影響で幼い頃から自分に自信がなく、根暗な性格。
    幼い頃は泣き虫だったが、今は感情が希薄になっており、泣くことはおろか表情一つ動かせなくなっている。
    人に対する恐怖心が強く、人見知りがかなり激しい。そのため友達もいない。
    また、恐怖心は強くとも人を信じやすいタイプで、かけられた言葉は良くも悪くも全部信じてしまう。
    愛されたがりで、特に父に愛されたいと強く願っている。',
    '金髪のおかっぱ（ぼさぼさ）で碧眼。',
    '力の作用なのか、明晰夢を見やすい体質である。
    夢の中は自由で楽しくて、何度も夢の中に浸っていた（無意識に力を使っていたことになる）
    本編にて、イルゼとナハトの計画により夢から引き戻された（メアの過去に組み込まれた）際には、酷く絶望した。
    　→リヒトにとってはずっと夢の中に沈んでいた方が幸せだった。
    以来、イルゼのことはもちろんのこと、ナハトのことも酷く恨むように。'
);

INSERT INTO characters(
    plot,
    phonetic,
    name,
    another,
    image_path,
    age,
    gender,
    birthday,
    height,
    weight,
    first_person,
    second_person,
    belongs,
    skill,
    profile,
    lived_process,
    personality,
    appearance,
    other
) VALUES (
    3,
    'ないとめあ',
    'ナイトメア',
    '悪夢の象徴　メア',
    '',
    '不明',
    1,
    '',
    0,
    0,
    'ボク',
    'オマエ',
    '物語『ナイトメア』',
    'あらゆる物語を悪夢（バッドエンド）に変える力。また力の都合上、物語内の登場人物への干渉も可能。',
    '悪夢の象徴とされる存在で、中世的な見た目をした美青年。楽しいこと、面白いことが大好きで、好奇心旺盛。時折頭痛を起こし、酷いときには失神してしまうことも。',
    'ある日突然、狭間に現れた。自分のことを何も分かっておらず、イヴァンの言葉だけを頼りにしている。',
    '楽しいこと、面白いことが大好きで、子供のように無邪気。自分の力でもがき苦しみ、泣きわめくキャラクターたちの様子を面白がっている。',
    '銀髪のおかっぱで、後ろ髪が腰ほどまで伸びている。赤い瞳。中世的な顔立ちで、美青年。',
    '愛されてるキャラクターに嫉妬するところがある。
    愛されてるのにそれをないがしろにしたり、更に求めようとするキャラクターには特に嫉妬し、激しい怒りを抱くことも。'
);

INSERT INTO characters(
    plot,
    phonetic,
    name,
    another,
    image_path,
    age,
    gender,
    birthday,
    height,
    weight,
    first_person,
    second_person,
    belongs,
    skill,
    profile,
    lived_process,
    personality,
    appearance,
    other
) VALUES (
    3,
    'なはと',
    'ナハト',
    '過去を失いし者　ナハト',
    '',
    '18',
    1,
    '',
    0,
    0,
    '俺',
    'あんた、貴方',
    '『空想世界』',
    '既存の作品に干渉する力を持つ。例えば、『風景画に炎を描き足せば、その場所がたちまち火事になってしまう』『小説に補足分、裏設定のように文字を書き込めばその通りに反映される』など。',
    '突然イルゼの前に現れた、記憶喪失の青年。覚えていたのは自分の名前だけで、奇しくもリヒトと対の意味になるものだった。',
    'ケーテが7歳のときに描いた絵で、ケーテにとって友達であり兄のような存在だった。生まれたばかりのときは10～13歳くらいの少年で、知能はおろか喋ることもできなかった。
ケーテから知識を得ながら、独自でも勉強し、ケーテと共に成長していった。
ケーテが16歳になってナハトの身長を超えた頃、ケーテが新しく描いた絵に姿を変え現在の青年の姿となる。
とある出来事により、名前以外の記憶を全て失ってしまう。',
    '大人しくて寡黙。感情の起伏が少なく、良くも悪くも冷静沈着。記憶を失っていながらイルゼにケーテの影を重ねており、無意識の内に惹かれている。彼女のためなら何でもしてあげたい様子。',
    '黒髪で、肩より少し長いくらいのストレート・灰色の瞳。リヒトとよく似た顔立ち。表情は滅多に動かず、どこか寂し気で憂いげな雰囲気を漂わせている。',
    ''
);

INSERT INTO characters(
    plot,
    phonetic,
    name,
    another,
    image_path,
    age,
    gender,
    birthday,
    height,
    weight,
    first_person,
    second_person,
    belongs,
    skill,
    profile,
    lived_process,
    personality,
    appearance,
    other
) VALUES (
    3,
    'いるぜ・ふぉん・しゅらーふ',
    'イルゼ・フォン・シュラーフ',
    '服従故の偽善　イルゼ',
    '',
    '19',
    2,
    '',
    0,
    0,
    '私',
    '貴方',
    '『空想世界』',
    '空想世界を生み出す力。ケーテより与えられたもの。詳細は設定・用語一覧にて。',
    'ケーテの娘で、リヒトの姉。親を含む周囲から愛されて育った、優しくて美しい女性。幼い頃から小説を書くことが趣味で、彼女の作品もまた周囲から愛されている。ただ一つ、『ナイトメア』という例外を除いて――。',
    'ケーテの力によって、4、5歳の姿で誕生した。当初は生まれたての赤ん坊も同然であったが、ケーテから様々な知識、設定を与えられ順調に育つ。その際、空想世界を生み出す力も授かった。
ケーテが死ぬ直前、彼女の「リヒトのことを守ってあげて」「お父様の言うことは必ず守りなさい」という発言が新たな設定として付与されたため、[リヒトのことは守るが父親の言うことには逆らわない]という矛盾した状態となり、結果として「偽善者」になってしまった。
',
    '優しくておっとりとした性格。周囲から慕われ愛されるような人物で、父からも気に入られている。',
    '金髪のロングヘア（胸辺り）・碧眼。ケーテとよく似ている。',
    '自殺を図ろうとしたことで昏睡状態となったリヒトを助けるため、
偶然出会ったナハトに協力を願う。それが身勝手なエゴであることにも気づかず、彼女は自作品の『ナイトメア』を利用し、リヒトを夢から目覚めさせようとする。'
);

INSERT INTO characters(
    plot,
    phonetic,
    name,
    another,
    image_path,
    age,
    gender,
    birthday,
    height,
    weight,
    first_person,
    second_person,
    belongs,
    skill,
    profile,
    lived_process,
    personality,
    appearance,
    other
) VALUES (
    3,
    'けーて・ふぉん・しゅらーふ',
    'ケーテ・フォン・シュラーフ',
    '本名：ケーテ・シュタイン',
    '',
    '不明',
    2,
    '',
    0,
    0,
    '私',
    '貴方',
    '『空想世界』',
    '創造の力。詳細は設定・用語一覧にて。',
    'イルゼとリヒトの母。二人をとても愛していたようだが、不幸にもリヒトがまだ赤ん坊の頃に身体を壊し他界してしまう。',
    '平凡な家庭に生まれる。
幼い頃から病弱で、外で遊ぶことができず家に引きこもってばかりいた。その上、学校（周囲）の子供たちからは「一緒にいてもつまらない」「なんか変な子」と避けられていた。
物心ついた頃から自然と力を扱っており、自分で描いた絵（ナハト含む）を友達としていた。そのため、寂しさを感じたことはない。

しかし、彼女の力は両親含む周囲の人々から気味悪がられ、「魔女だ」「魔女の子だ」と罵られるように。
そしてある日、両親がケーテを家ごと燃やしてしまおうと家に火をつけてしまう。
かろうじてケーテは逃げのびたが、絵や文章は全て燃えてしまい、彼女の友達は消えてしまった。ナハトだけは大事に地下で保管していたために無事なのだが、ここで離れ離れになってしまう。詳細は設定・用語一覧へ。
この日を境に、イルゼ誕生までの間、ケーテは力を使わなくなる。

逃げのびたものの行く宛てもなく彷徨っていたところを、ある貴族の男に拾われる。

幸いにも住まわせてもらうことになり、やがてその家の長男と身を結ぶ。
それがリヒト父。',
    '子供の頃は、明るくて好奇心旺盛だった。しかし、上記の一見以来すっかり心を塞いでしまい、またナハトのことが気がかりでいつも寂しそうな表情をするように。イルゼやリヒトが誕生してからは以前のような明るさを取り戻していた。',
    '腰まで伸びた金髪のロングヘア（少し癖っ毛）・碧眼',
    '病弱であるため、出産には身体が耐えられない。
昔から日記をつけるのが習慣となっており、中にはイルゼやリヒトへの設定内容も記述されている。この日記が二人の生命線とも言える。'
);

INSERT INTO characters(
    plot,
    phonetic,
    name,
    another,
    image_path,
    age,
    gender,
    birthday,
    height,
    weight,
    first_person,
    second_person,
    belongs,
    skill,
    profile,
    lived_process,
    personality,
    appearance,
    other
) VALUES (
    3,
    'しろな・くろな',
    'シロナ・クロナ',
    '『物語』の案内人　シロナ＆クロナ',
    '',
    '不明',
    5,
    '',
    0,
    0,
    '自分の名前',
    'オマエ',
    '『空想世界（リヒトやイルゼがいる世界）』と『空想世界（イルゼの物語の世界）』との『狭間』で、チヒロがいる『狭間』とは完全に隔離された空間。',
    'クロナは『物語に入る力』
シロナは『物語から出る力』
二人共首から大きなカギをぶらさげており、それを使って『物語』を行き来する。',
    'メアと行動を共にする不思議な双子。カタコト口調で話し、ケタケタと無邪気に笑う様子が特徴的。',
    '今作の舞台となる『狭間』の自然発生と共に誕生した観測者たち。どのようにして誕生したのかは不明であるが、ある程度の権限を保有しているあたり観測者であることは間違いない様子。',
    '二人共幼い子供のように無邪気で、悪意は常にゼロ。面白くて楽しいことが大好き。',
    'クロナは黒い、シロナは白い服装をしている。
二人共大きめのフードを目深に被っており、顔は良く見えない。口が見える程度。身長は小学生低学年ほどしかない。
クロナは右目の下に、シロナは左目の下に「101」と刻まれている。',
    'グレイと同等（正確には少し下）の存在。
詳細は設定・用語一覧の「双子について」'
);

/*ideas*/
/*------------------------------------------------------------------------------------------------------------------------------*/
INSERT INTO ideas(
    plot,
    idea,
    note
) VALUES (
    1,
    1,
    'よそ者の少年シヴァが村へと移住してくる。ブラフマーは最初興味を示していなかったが、シヴァの姓はかつて滅んだはずのヴィナッシュであることを知り、驚愕する。'
);

INSERT INTO ideas(
    plot,
    idea,
    note
) VALUES (
    1,
    2,
    '余計な関わりを避けようとするブラフマーに対して、何故かシヴァはしつこく話しかけてくる。そんな彼に段々と惹かれていき、ブラフマーは生きる意味を見出しかけていた。'
);

INSERT INTO ideas(
    plot,
    idea,
    note
) VALUES (
    1,
    3,
    "シヴァが移住してきてから三年。二人はすっかり仲の良い友人となっていた。だがある日、もうすぐ儀式の日が迫っていることからちょっとした喧嘩をしてしまう。傷つけてしまったかもしれないと落ち込むブラフマーの前に、もう二度と会えるはずがないと思っていたある人物が現れる。それは、七年前に一度だけ会った、アザミ色の少女だった。"
);

INSERT INTO ideas(
    plot,
    idea,
    note
) VALUES (
    1,
    4,
    'アザミ色の少女から衝撃的な事実を聞いたブラフマーは、慌てて儀式の場へと駆け出す。しかし、そこにはもう、黒曜のナイフを手にした村長と怯えた様子のシヴァがいて。やめてくれと叫ぶブラフマーの思いに反して、黒曜のナイフは振り落とされた。'
);

INSERT INTO ideas(
    plot,
    idea,
    note
) VALUES (
    3,
    1,
    'プロローグ・自分らしく生きる物語。
    プロローグではイルゼとナハトが『ナイトメア』に干渉しようとするシーンと、メアが狭間に現れたときのシーンを綴る。自分らしく生きる物語は、朔夜と怜奈の物語。
    メアが登場するのは最後だけ。'
);

INSERT INTO ideas(
    plot,
    idea,
    note
) VALUES (
    3,
    2,
    '・二章「身勝手な二人の物語」：小萌と陸の話で、双子が初登場。
    
・三章「家族の絆を確かめる物語」：拓斗の話で、姉という存在にメアが引っ掛かりを覚える。

・四章「共依存する物語」：りんやよの話で、メアが一番激昂する話。ここでイヴァンが登場する。

・五章「葵の物語（キャッチコピー未定）」：ここでメアの目的が明かされる。'
);

INSERT INTO ideas(
    plot,
    idea,
    note
) VALUES (
    3,
    3,
    '悪夢を食べる物語の中で伯と出会い、メアが激しい頭痛を起こす。
ここで双子が伯・イヴァン・メアの全員に、リヒトのことやイルゼとナハトがしようとしていることを見せる（リヒトの存在が明かされる）'
);

INSERT INTO ideas(
    plot,
    idea,
    note
) VALUES (
    3,
    4,
    '夢から醒める物語。全てを知ってしまったメアが、伯の力によって悪夢からリヒトとして目を覚ます。
伯は力を使い果たして倒れ、狭間の中でイヴァンだけが一人取り残される。'
);

/*stories*/
/*------------------------------------------------------------------------------------------------------------------------------*/
-- INSERT INTO stories(
--     idea,
--     title,
--     story
-- ) VALUES (

-- );

/*memos*/
/*------------------------------------------------------------------------------------------------------------------------------*/
