package local.hal.st32.android.ploteditor;

/**
 * 就職作品
 *
 * 取得した数値から性別を判断し返すクラス
 *
 * @author ohs60224
 */
public class Gender {
    /**
     * 性別を判断するメソッド
     *
     * @param genderNo 性別主キー
     * @return 性別（文字列）
     */
    public static String gender(String genderNo) {
        int no = Integer.parseInt(genderNo);

        String gender = "";
        switch (no) {
            case 1:
                gender = "男";
                break;
            case 2:
                gender = "女";
                break;
            case 3:
                gender = "両性";
                break;
            case 4:
                gender = "無性";
                break;
            case 5:
                gender = "不明";
                break;
        }

        return gender;
    }
}
