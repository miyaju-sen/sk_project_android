package local.hal.st32.android.ploteditor;

import android.util.Log;

import java.util.HashMap;

/**
 * 就職作品
 *
 * CharacterEditActivity専用
 * 戻るボタン押下時に、変更された項目があればtrue、なければfalseを返すクラス
 * TODO:新規登録時はできないのでなんとかする
 *
 * @author ohs60224
 */
public class CharacterIsChanged {
    private HashMap<String, String> _character;
    private boolean _changed;

    /**
     * コンストラクタ（空）
     */
    public CharacterIsChanged() {
        this._character = new HashMap<>();
        this._changed = false;
    }

    /**
     * コンストラクタ
     *
     * @param character 登場人物の情報
     */
    public CharacterIsChanged(HashMap<String, String> character) {
        this._character = new HashMap<>( character );
        this._changed = false;
    }

    /**
     * 画像
     * @param image 画像ファイル名
     */
    public void setImage(String image) {
        if(!this._character.get("image_path").equals(image)) {
            this._changed = true;
        }
        Log.e("画像", image);
    }

    /**
     * 名前
     * @param name 名前
     */
    public void setName(String name) {
        if(!this._character.get("name").equals(name)) {
            this._changed = true;
        }
        Log.e("名前", name);
    }

    /**
     * フリガナ
     * @param phonetic フリガナ
     */
    public void setPhonetic(String phonetic) {
        if(!this._character.get("phonetic").equals(phonetic)) {
            this._changed = true;
        }
        Log.e("フリガナ", phonetic);
    }

    /**
     * 別名
     * @param another 別名
     */
    public void setAnother(String another) {
        if(!this._character.get("another").equals(another)) {
            this._changed = true;
        }
    }

    /**
     * 年齢
     * @param age ラジオボタンのタグ
     */
    public  void setAge(String age) {
        if(!this._character.get("age").equals(age)) {
            this._changed = true;
        }
        Log.e("年齢", age);
    }

    /**
     * 性別
     * @param gender ラジオボタンのタグ
     */
    public void setGender(String gender) {
        if(!this._character.get("gender").equals(gender)) {
            this._changed = true;
        }
        Log.e("性別", gender);
    }

    /**
     * 誕生日
     * @param birthday 誕生日
     */
    public void setBirthday(String birthday) {
        if(!this._character.get("birthday").equals(birthday)) {
            this._changed = true;
        }
        Log.e("誕生日", birthday);
    }

    /**
     * 身長
     * @param height 身長
     */
    public void setHeight(String height) {
        if(!this._character.get("height").equals(height)) {
            this._changed = true;
        }
        Log.e("身長", height);
    }

    /**
     * 身長
     * @param height 身長
     */
    public void setHeight(int height) {
        if(!this._character.get("height").equals( Integer.toString(height) )) {
            this._changed = true;
        }
    }

    /**
     * 体重
     * @param weight 体重
     */
    public void setWeight(String weight) {
        if(!this._character.get("weight").equals(weight)) {
            this._changed = true;
        }
        Log.e("体重", weight);
    }

    /**
     * 体重
     * @param weight 体重
     */
    public void setWeight(int weight) {
        if(!this._character.get("weight").equals( Integer.toString(weight) )) {
            this._changed = true;
        }
    }

    /**
     * 一人称
     * @param firstPerson 一人称
     */
    public void setFirstPerson(String firstPerson) {
        if(!this._character.get("first_person").equals(firstPerson)) {
            this._changed = true;
        }
        Log.e("一人称", firstPerson);
    }

    /**
     * 二人称
     * @param secondPerson 二人称
     */
    public void setSecondPerson(String secondPerson) {
        if(!this._character.get("second_person").equals(secondPerson)) {
            this._changed = true;
        }
        Log.e("二人称", secondPerson);
    }

    /**
     * 所属
     * @param belongs 所属
     */
    public void setBelongs(String belongs) {
        if(!this._character.get("belongs").equals(belongs)) {
            this._changed = true;
        }
        Log.e("所属", belongs);
    }

    /**
     * 能力
     * @param skill 能力
     */
    public void setSkill(String skill) {
        if(!this._character.get("skill").equals(skill)) {
            this._changed = true;
        }
        Log.e("能力", skill);
    }

    /**
     * 紹介文
     * @param profile 紹介文
     */
    public void setProfile(String profile) {
        if(!this._character.get("profile").equals(profile)) {
            this._changed = true;
        }
        Log.e("紹介文", profile);
    }

    /**
     * 生い立ち
     * @param livedProcess 生い立ち
     */
    public void setLivedProcess(String livedProcess) {
        if(!this._character.get("lived_process").equals(livedProcess)) {
            this._changed = true;
        }
        Log.e("生い立ち", livedProcess);
    }

    /**
     * 性格
     * @param personality 性格
     */
    public void setPersonality(String personality) {
        if(!this._character.get("personality").equals(personality)) {
            this._changed = true;
        }
        Log.e("性格", personality);
    }

    /**
     * 容姿
     * @param appearance
     */
    public void setAppearance(String appearance) {
        if(!this._character.get("appearance").equals(appearance)) {
            this._changed = true;
        }
        Log.e("容姿", appearance);
    }

    /**
     * その他
     * @param other
     */
    public void setOther(String other) {
        if(!this._character.get("other").equals(other)) {
            this._changed = true;
        }
        Log.e("その他", other);
    }

    /**
     * 変更された項目があるか否か
     * @return 変更されていればtrue、されていなければfalse
     */
    public boolean isChanged() {
        return _changed;
    }
}
