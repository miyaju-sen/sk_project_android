#就職作品 データベース[plot_editor]
#作成開始
DROP DATABASE IF EXISTS plot_editor;
CREATE DATABASE plot_editor CHARACTER SET utf8;
use plot_editor;

#M_性別テーブル
CREATE TABLE genders(
    no INT(8) NOT NULL AUTO_INCREMENT,  #主キーNo
    gender VARCHAR(100) NOT NULL, #性別
    PRIMARY KEY(no), 
    INDEX(no)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#T_作品テーブル
CREATE TABLE plots(
    no INT(8) NOT NULL AUTO_INCREMENT,  #作品No
    title VARCHAR(100) NOT NULL, #タイトル
    slogan VARCHAR(50), #キャッチコピー
    summary TEXT, #あらすじ
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, #作成日 
    deleted BOOLEAN NOT NULL DEFAULT FALSE, #削除フラグ
    PRIMARY KEY(no), 
    INDEX(no)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#T_世界観（舞台）テーブル
CREATE TABLE stages(
    no INT(8) NOT NULL AUTO_INCREMENT,  #主キーNo
    plot INT(8) NOT NULL, #作品No
    stage TEXT NOT NULL, #舞台説明 
    PRIMARY KEY(no), 
    INDEX(no),
    FOREIGN KEY (plot) REFERENCES plots(no)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#T_設定・用語テーブル
CREATE TABLE parlances(
    no INT(8) NOT NULL AUTO_INCREMENT,  #主キーNo
    plot INT(8) NOT NULL, #作品No
    name VARCHAR(100) NOT NULL, #設定項目・用語
    explanation TEXT, #説明
    deleted BOOLEAN NOT NULL DEFAULT FALSE, #削除フラグ
    PRIMARY KEY(no), 
    INDEX(no),
    FOREIGN KEY (plot) REFERENCES plots(no)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#T_登場人物テーブル
CREATE TABLE characters(
    no INT(8) NOT NULL AUTO_INCREMENT,  #登場人物No
    plot INT(8) NOT NULL, #作品No
    phonetic VARCHAR(512),  #フリガナ
    name VARCHAR(256) NOT NULL, #名前
    another VARCHAR(256), #別名
    image_path VARCHAR(260), #イメージ画像パス
    age VARCHAR(5), #年齢
    gender INT(8) NOT NULL, #性別
    birthday VARCHAR(10), #誕生日
    height INT(5), #身長
    weight INT(5), #体重
    first_person VARCHAR(10), #一人称
    second_person VARCHAR(10), #二人称
    belongs VARCHAR(100), #所属
    skill TEXT, #能力
    profile TEXT, #紹介文
    lived_process TEXT, #生い立ち
    personality TEXT, #性格
    appearance TEXT, #容姿
    other TEXT, #その他 
    deleted BOOLEAN NOT NULL DEFAULT FALSE, #削除フラグ
    PRIMARY KEY(no), 
    INDEX(no),
    FOREIGN KEY (plot) REFERENCES plots(no),
    FOREIGN KEY (gender) REFERENCES genders(no)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#T_起承転結テーブル
CREATE TABLE ideas(
    no INT(8) NOT NULL AUTO_INCREMENT,  #主キーNo
    plot INT(8) NOT NULL, #作品No
    note TEXT, #大まかな内容
    PRIMARY KEY(no), 
    INDEX(no),
    FOREIGN KEY (plot) REFERENCES plots(no)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#T_ストーリーテーブル
CREATE TABLE stories(
    no INT(8) NOT NULL AUTO_INCREMENT,  #主キーNo
    idea INT(8) NOT NULL, #起承転結No
    story TEXT, #ストーリー
    deleted BOOLEAN NOT NULL DEFAULT FALSE, #削除フラグ
    PRIMARY KEY(no), 
    INDEX(no),
    FOREIGN KEY (idea) REFERENCES ideas(no)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#T_メモテーブル
CREATE TABLE memos(
    no INT(8) NOT NULL AUTO_INCREMENT,  #メモNo
    plot INT(8) NOT NULL, #作品No
    note TEXT, #メモ内容
    deleted BOOLEAN NOT NULL DEFAULT FALSE, #削除フラグ
    PRIMARY KEY(no), 
    INDEX(no),
    FOREIGN KEY (plot) REFERENCES plots(no)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#T_メモ画像テーブル
CREATE TABLE memo_images(
    no INT(8) NOT NULL AUTO_INCREMENT,  #主キーNo
    memo INT(8) NOT NULL, #メモNo
    image_path VARCHAR(260), #メモ画像パス
    deleted BOOLEAN NOT NULL DEFAULT FALSE, #削除フラグ
    PRIMARY KEY(no), 
    INDEX(no),
    FOREIGN KEY (memo) REFERENCES memos(no)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#合計9テーブル
#終了